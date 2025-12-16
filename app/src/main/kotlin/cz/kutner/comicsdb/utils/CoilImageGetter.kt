package cz.kutner.comicsdb.utils

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import coil3.asDrawable
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import coil3.target.Target
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.materialdesigniconic.MaterialDesignIconic
import com.mikepenz.iconics.utils.sizeDp

class CoilImageGetter(private val textView: TextView) : Html.ImageGetter {

    private val imageLoader = textView.context.imageLoader

    override fun getDrawable(source: String): Drawable {
        val placeholderDrawable = IconicsDrawable(textView.context, MaterialDesignIconic.Icon.gmi_image_o).apply {
            sizeDp = 64
        }
        val errorDrawable = IconicsDrawable(textView.context, MaterialDesignIconic.Icon.gmi_broken_image).apply {
            sizeDp = 64
        }

        val drawable = DrawablePlaceholder(textView.resources, placeholderDrawable)

        val request = ImageRequest.Builder(textView.context)
            .data(source)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .target(drawable)
            .build()

        imageLoader.enqueue(request)

        return drawable
    }

    private inner class DrawablePlaceholder(
        private val resources: Resources,
        initialDrawable: Drawable
    ) : BitmapDrawable(resources, null as android.graphics.Bitmap?), Target {

        private var drawable: Drawable? = initialDrawable
            set(value) {
                if (value != null) {
                    field = value
                    checkBounds()
                }
            }

        init {
            drawable = initialDrawable
            checkBounds()
        }

        override fun draw(canvas: Canvas) {
            drawable?.let {
                checkBounds()
                it.draw(canvas)
            }
        }

        private fun checkBounds() {
            val d = drawable ?: return
            val defaultProportion = d.intrinsicWidth.toFloat() / d.intrinsicHeight.toFloat()
            val width = minOf(textView.width.takeIf { it > 0 } ?: d.intrinsicWidth, d.intrinsicWidth)
            val height = (width.toFloat() / defaultProportion).toInt()

            if (bounds.right != textView.width || bounds.bottom != height) {
                val textViewWidth = textView.width.takeIf { it > 0 } ?: width
                setBounds(0, 0, textViewWidth, height)
                d.setBounds(0, 0, width, height)
                textView.text = textView.text // refresh text
            }
        }

        override fun onStart(placeholder: coil3.Image?) {
            placeholder?.asDrawable(resources)?.let { drawable = it }
        }

        override fun onError(error: coil3.Image?) {
            error?.asDrawable(resources)?.let { drawable = it }
        }

        override fun onSuccess(result: coil3.Image) {
            drawable = result.asDrawable(resources)
        }
    }
}
