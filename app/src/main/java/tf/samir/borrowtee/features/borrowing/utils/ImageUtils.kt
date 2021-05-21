package tf.samir.borrowtee.features.borrowing.utils

import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Environment
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@Throws(IOException::class)
fun ContextWrapper.createImageFile(): File {
    // Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
}

fun File?.createBitmap(): Bitmap? {
    return BitmapFactory.decodeFile(this?.absolutePath)
}

@Throws(IOException::class)
fun fixImageOrientation(imagePath: String, source: Bitmap): Bitmap {
    var sourceCopy = source
    val ei = ExifInterface(imagePath)
    when (ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
        ExifInterface.ORIENTATION_ROTATE_90 -> sourceCopy = rotateImageByAngle(sourceCopy, 90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> sourceCopy = rotateImageByAngle(sourceCopy, 180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> sourceCopy = rotateImageByAngle(sourceCopy, 270f)
    }
    return sourceCopy
}

fun rotateImageByAngle(source: Bitmap, angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
}

fun Bitmap.resizeBitmap(): Bitmap {
    var source = this
    val heightOrg = source.height
    val heightNew = 800
    if (heightNew < heightOrg) {
        val widthOrg = source.width
        val widthNew = heightNew * widthOrg / heightOrg
        val matrix = Matrix()
        matrix.postScale(widthNew.toFloat() / widthOrg, heightNew.toFloat() / heightOrg)
        source = Bitmap.createBitmap(source, 0, 0, widthOrg, heightOrg, matrix, false)
    }
    return source
}

fun compressBitmap(bitmap: Bitmap, outputFile: File, compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, quality: Int = 100) {
    bitmap.compress(compressFormat, quality, FileOutputStream(outputFile))
}