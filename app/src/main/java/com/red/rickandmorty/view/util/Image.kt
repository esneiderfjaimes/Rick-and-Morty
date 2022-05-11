package com.red.rickandmorty.view.util

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.createBitmap
import com.red.rickandmorty.R
import kotlin.math.min

// possible solution to apply transformations on coil error or placeholder
object ImageDefault {

    private var drawable: Drawable? = null

    fun getDrawable(image: ImageView): Drawable {
        drawable?.let { return it }

        loadDrawable(image).let {
            drawable = it
            return it
        }
    }

    private fun loadDrawable(image: ImageView): Drawable {
        val icon: Bitmap = BitmapFactory.decodeResource(image.resources, R.mipmap.image_default)
        val transform = circleCropTransformation(icon)
        return BitmapDrawable(image.resources, transform)
    }
}

fun circleCropTransformation(input: Bitmap): Bitmap {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)

    val minSize = min(input.width, input.height)
    val radius = minSize / 2f
    val output = createBitmap(minSize, minSize, Bitmap.Config.ARGB_8888)
    output.applyCanvas {
        drawCircle(radius, radius, radius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        drawBitmap(input, radius - input.width / 2f, radius - input.height / 2f, paint)
    }

    return output
}