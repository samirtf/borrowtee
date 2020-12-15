package tf.samir.borrowtee.features.main.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class ItemButton(
    private val context: Context,
    private val text: String,
    private val textSize: Int,
    private val imageResId: Int,
    private val color: Int,
    private val listener: ItemClickListener
) {
    private var position: Int = 0
    private var clickRegion: RectF? = null

    fun onClick(x: Float, y: Float): Boolean {
        if(clickRegion != null && clickRegion!!.contains(x, y)) {
            listener.onClick(position)
            return true
        }
        return false
    }

    fun onDraw(canvas: Canvas, rectF: RectF) {
        val paint = Paint().apply {
            color = this@ItemButton.color
            canvas.drawRect(rectF, this)

            // Text
            color = Color.WHITE
            textSize = this@ItemButton.textSize.toFloat()
        }

        val rect = Rect()
        val canvasHeight = rectF.height()
        val canvasWidth = rectF.width()
        paint.textAlign = Paint.Align.LEFT
        paint.getTextBounds(text, 0, text.length, rect)
        val x: Float
        val y: Float
        if (imageResId == 0) {
            x = canvasWidth/2f - rect.width()/2f-rect.left.toFloat()
            y = canvasHeight/2f + rect.height()/2f - rect.bottom.toFloat()
            canvas.drawText(text, rectF.left + x, rectF.top + y, paint)
        } else {
            val drawable = ContextCompat.getDrawable(context, imageResId)
            val bitmap = drawableToBitmap(drawable)
            canvas.drawBitmap(bitmap, (rectF.left+rectF.right)/2, (rectF.top+rectF.bottom)/2, paint)

        }

    }

    private fun drawableToBitmap(drawable: Drawable?): Bitmap {
        if (drawable is BitmapDrawable) return drawable.bitmap
        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}