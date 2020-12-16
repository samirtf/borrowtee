package tf.samir.borrowtee.features.main.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class IconButtonItem(
    private val context: Context,
    private val imageResId: Int,
    private val color: Int,
    private val listener: ItemClickListener
): ButtonItem {
    private var position: Int = 0
    private var clickRegion: RectF? = null

    override fun onClick(x: Float, y: Float): Boolean {
        if(clickRegion != null && clickRegion!!.contains(x, y)) {
            listener.onClick(position)
            return true
        }
        return false
    }

    override fun onDraw(canvas: Canvas, rectF: RectF) {
        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = this@IconButtonItem.color
            setShadowLayer(0.6f, 0.0f, 5.0f, Color.argb(100, 128, 128, 128))
        }
        canvas.drawRect(rectF, bgPaint)

        val drawable = ContextCompat.getDrawable(context, imageResId)
        val bitmap = drawableToBitmap(drawable)
        canvas.drawBitmap(
            bitmap,
            (rectF.left + rectF.right) / 2 - (bitmap.width / 2),
            (rectF.top + rectF.bottom) / 2 - (bitmap.height / 2),
            bgPaint
        )

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