package tf.samir.borrowtee.features.main.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import tf.samir.borrowtee.BuildConfig

class TextAndIconButtonItem(
    private val context: Context,
    private val text: String,
    private val textSize: Int,
    private val imageResId: Int,
    private val color: Int,
    private val listener: ItemClickListener
): ButtonItem {
    private var position: Int = 0
    private var clickRegion: RectF? = null

    init {
        if (BuildConfig.DEBUG && imageResId <= 0) {
            error("Assertion failed: image resource id must be greater then zero.")
        }
    }

    override fun onClick(x: Float, y: Float): Boolean {
        if(clickRegion != null && clickRegion!!.contains(x, y)) {
            listener.onClick(position)
            return true
        }
        return false
    }

    override fun onDraw(canvas: Canvas, rectF: RectF, position: Int) {
        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = this@TextAndIconButtonItem.color
            setShadowLayer(0.6f, 0.0f, 5.0f, Color.argb(100, 128, 128, 128))
        }
        canvas.drawRect(rectF, bgPaint)

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textSize = this@TextAndIconButtonItem.textSize.toFloat()
        }

        val rect = Rect()
        val canvasHeight = rectF.height()
        val canvasWidth = rectF.width()
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.getTextBounds(text, 0, text.length, rect)
        val x: Float
        val y: Float
        val drawable = ContextCompat.getDrawable(context, imageResId)
        val bitmap = drawableToBitmap(drawable)
        x = canvasWidth / 2f - rect.width() / 2f - rect.left.toFloat()
        y = canvasHeight / 2f + rect.height() / 2f - rect.bottom.toFloat()
        canvas.drawText(text, rectF.left + x, rectF.top + y + y / 4, textPaint)
        canvas.drawBitmap(
            bitmap,
            (rectF.left + rectF.right) / 2 - (bitmap.width / 2),
            (rectF.top + rectF.bottom) / 2 - (bitmap.height / 2) - y / 4,
            textPaint
        )

        clickRegion = rectF
        this.position = position
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