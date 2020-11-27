package cz.kutner.comicsdb.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.materialdesigniconic.MaterialDesignIconic
import com.mikepenz.iconics.utils.sizeDp
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlin.math.min


class PicassoImageGetter(val textView: TextView) :
        Html.ImageGetter {

    override fun getDrawable(source: String): Drawable {

        val drawable = BitmapDrawablePlaceHolder(
                textView.context.resources,
                IconicsDrawable(textView.context, MaterialDesignIconic.Icon.gmi_image_o).apply {
                    sizeDp = 64
                }.toBitmap()
        )

        Picasso.get()
                .load(source)
                .placeholder(
                        IconicsDrawable(textView.context, MaterialDesignIconic.Icon.gmi_image_o).apply {
                            sizeDp = 64
                        }
                )
                .error(
                        IconicsDrawable(textView.context, MaterialDesignIconic.Icon.gmi_broken_image).apply {
                            sizeDp = 64
                        }
                )
                .into(drawable)

        return drawable
    }

    private inner class BitmapDrawablePlaceHolder(resources: Resources, bitmap: Bitmap) :
            BitmapDrawable(resources, bitmap), Target {

        private var drawable: Drawable? = null
            set(value) {
                if (value != null) {
                    field = value
                    checkBounds()
                }
            }

        override fun draw(canvas: Canvas) {
            if (drawable != null) {
                checkBounds()
                drawable!!.draw(canvas)
            }
        }

        private fun checkBounds() {
            val defaultProportion =
                    drawable!!.intrinsicWidth.toFloat() / drawable!!.intrinsicHeight.toFloat()
            val width = min(textView.width, drawable!!.intrinsicWidth)
            val height = (width.toFloat() / defaultProportion).toInt()

            if (bounds.right != textView.width || bounds.bottom != height) {

                setBounds(0, 0, textView.width, height) //set to full width

                drawable!!.setBounds(
                        0,
                        0,
                        width,
                        height
                )

                textView.text = textView.text //refresh text
            }
        }

        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            drawable = BitmapDrawable(textView.context.resources, bitmap)
        }

        override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {
            drawable = errorDrawable
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable) {
            drawable = placeHolderDrawable

        }
    }
}