package cz.kutner.comicsdb.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target


class PicassoImageGetter(val textView: TextView) :
    Html.ImageGetter {

    override fun getDrawable(source: String): Drawable {

        val drawable = BitmapDrawablePlaceHolder()

        Picasso.get()
            .load(source)
            .placeholder(
                IconicsDrawable(textView.context).icon(MaterialDesignIconic.Icon.gmi_image_o).sizeDp(
                    40
                )
            )
            .error(
                IconicsDrawable(textView.context).icon(MaterialDesignIconic.Icon.gmi_broken_image).sizeDp(
                    40
                )
            )
            .into(drawable)

        return drawable
    }

    private inner class BitmapDrawablePlaceHolder : BitmapDrawable(), Target {

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
            val width = Math.min(textView.width, drawable!!.intrinsicWidth)
            val height = (width.toFloat() / defaultProportion).toInt()

            if (bounds.right != textView.width || bounds.bottom != height) {

                setBounds(0, 0, textView.width, height) //set to full width

                val halfOfPlaceHolderWidth = (bounds.right.toFloat() / 2f).toInt()
                val halfOfImageWidth = (width.toFloat() / 2f).toInt()

                drawable!!.setBounds(
                    halfOfPlaceHolderWidth - halfOfImageWidth, //centering an image
                    0,
                    halfOfPlaceHolderWidth + halfOfImageWidth,
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