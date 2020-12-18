package tf.samir.borrowtee.features.main.utils

import android.graphics.Canvas
import android.graphics.RectF

interface ButtonItem {

    fun onClick(x: Float, y: Float): Boolean
    fun onDraw(canvas: Canvas, rectF: RectF, position: Int)
}