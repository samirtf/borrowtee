package tf.samir.borrowtee.features.main.utils

import android.graphics.*

class TextButtonItem(
    private val text: String,
    private val textSize: Int,
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

    override fun onDraw(canvas: Canvas, rectF: RectF, position: Int) {
        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = this@TextButtonItem.color
            setShadowLayer(0.6f, 0.0f, 5.0f, Color.argb(100, 128, 128, 128))
        }
        canvas.drawRect(rectF, bgPaint)

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textSize = this@TextButtonItem.textSize.toFloat()
        }

        val rect = Rect()
        val canvasHeight = rectF.height()
        val canvasWidth = rectF.width()
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.getTextBounds(text, 0, text.length, rect)

        val x: Float = canvasWidth/2f - rect.width()/2f-rect.left.toFloat()
        val y: Float = canvasHeight/2f + rect.height()/2f - rect.bottom.toFloat()
        canvas.drawText(text, rectF.left + x, rectF.top + y, textPaint)

        clickRegion = rectF
        this.position = position
    }
}