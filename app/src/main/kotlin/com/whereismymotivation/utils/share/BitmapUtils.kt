package com.whereismymotivation.utils.share

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.whereismymotivation.R

object BitmapUtils {

    private const val FONT = "roboto_regular"
    private const val SHADOW_COLOR = "#0336FF"

    fun getTextAsBitmap(
        context: Context,
        tagName: String,
        text: String,
        author: String,
        watermark: String
    ): Bitmap {

        val width = 1000f
        val height = 1000f

        val textAreaWidth = 800f
        val textAreaHeight = 400f

        val bitmap = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)

        val gradient = LinearGradient(
            0f, 0f, 0f, height,
            ContextCompat.getColor(context, R.color.gradient_start_color),
            ContextCompat.getColor(context, R.color.gradient_end_color),
            Shader.TileMode.MIRROR
        )

        val paintCanvas = Paint()
        paintCanvas.shader = gradient
        paintCanvas.isAntiAlias = true
        canvas.drawPaint(paintCanvas)

        val dx = (width - textAreaWidth) / 2

        val dy = (height - textAreaHeight) / 2

        canvas.translate(dx, dy)

        val layout = getStaticLayout(
            textAreaWidth.toInt(),
            textAreaHeight.toInt(),
            text
        )

        layout.draw(canvas)

        canvas.translate(-dx, -dy)

        drawTagText(canvas, tagName, 40f, dy)

        drawWatermarkText(canvas, watermark, 50f)

        if (author.isNotBlank()) {
            drawAuthorText(
                canvas,
                author,
                28f,
                dx + textAreaWidth,
                dy + textAreaHeight
            )
        }

        return bitmap

    }

    private fun getStaticLayout(width: Int, height: Int, text: String): StaticLayout {

        val paint = TextPaint()
        paint.isAntiAlias = true
        paint.color = Color.WHITE
        paint.typeface = Typeface.create(FONT, Typeface.NORMAL)
        paint.textAlign = Paint.Align.LEFT

        var textSize = 250f
        var readyForWidth = true

        do {
            paint.textSize = textSize

            val list = text.split(" ")
            for (s in list) {
                val m = paint.measureText(s)
                if (m > width) {
                    textSize -= 5
                    readyForWidth = false
                    break
                } else {
                    readyForWidth = true
                }
            }
        } while (!readyForWidth)

        var layout: StaticLayout
        var layoutHeight: Int
        var readyForHeight: Boolean

        do {
            paint.textSize = textSize
            layout = StaticLayout(text, paint, width, Layout.Alignment.ALIGN_CENTER, 1f, 2f, true)
            layoutHeight = layout.height
            if (layoutHeight > height) {
                textSize -= 5
                readyForHeight = false
            } else {
                readyForHeight = true
            }
        } while (!readyForHeight)

        return layout
    }

    private fun drawTagText(canvas: Canvas, text: String, textSize: Float, mainTextTop: Float) {

        val paint = TextPaint()
        paint.color = Color.WHITE
        paint.typeface = Typeface.create(FONT, Typeface.NORMAL)
        paint.textSize = textSize
        paint.isAntiAlias = true
        paint.textAlign = Paint.Align.LEFT

        val rect = Rect()
        paint.getTextBounds(text, 0, text.length, rect)

        val x = canvas.width / 2f - rect.width() / 2f - rect.left
        val y = mainTextTop - rect.height() - rect.bottom

        canvas.drawText(text, x, y, paint)

    }

    private fun drawAuthorText(
        canvas: Canvas,
        text: String,
        textSize: Float,
        mainTextRight: Float,
        mainTextBottom: Float
    ) {

        val paint = TextPaint()
        paint.color = Color.WHITE
        paint.typeface = Typeface.create(FONT, Typeface.NORMAL)
        paint.textSize = textSize
        paint.isAntiAlias = true
        paint.textAlign = Paint.Align.LEFT

        val rect = Rect()
        paint.getTextBounds(text, 0, text.length, rect)

        val x = mainTextRight - rect.width() - rect.left

        val y = mainTextBottom + 12

        canvas.drawText(text, x, y, paint)

    }

    private fun drawWatermarkText(
        canvas: Canvas,
        text: String,
        textSize: Float,
        textColor: Int = Color.WHITE,
        shadowColor: Int = Color.parseColor(SHADOW_COLOR)
    ) {

        val paint = TextPaint()
        paint.color = textColor
        paint.typeface = Typeface.create(FONT, Typeface.NORMAL)
        paint.textSize = textSize
        paint.isAntiAlias = true
        paint.textAlign = Paint.Align.LEFT
        paint.setShadowLayer(20f, 0f, 0f, shadowColor)

        val rect = Rect()
        paint.getTextBounds(text, 0, text.length, rect)

        val x = canvas.width / 2f - rect.width() / 2f - rect.left
        val y = (canvas.height - rect.height() / 2).toFloat()

        canvas.drawText(text, x, y, paint)

    }

    private fun getBitmapOfViewDimension(view: View): Bitmap =
        view.run {
            if (measuredWidth == 0) {
                layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )

                measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                layout(0, 0, measuredWidth, measuredHeight)
            }

            Bitmap.createBitmap(
                measuredWidth,
                measuredHeight,
                Bitmap.Config.ARGB_8888
            )
        }

    fun getBitmapFromView(view: View): Bitmap =
        view.run {
            val bitmap = getBitmapOfViewDimension(view)
            val canvas = Canvas(bitmap)
            layout(left, top, right, bottom)
            draw(canvas)
            bitmap
        }

    fun getBitmapFromViewWaterMarked(
        view: View,
        watermark: String,
        textColor: Int = Color.WHITE,
        shadowColor: Int = Color.parseColor(SHADOW_COLOR)
    ): Bitmap =
        view.run {
            val bitmap = getBitmapOfViewDimension(view)
            val canvas = Canvas(bitmap)
            layout(left, top, right, bottom)
            draw(canvas)
            drawWatermarkText(
                canvas,
                watermark,
                (bitmap.width / 25).toFloat(),
                textColor,
                shadowColor
            )
            bitmap
        }

    fun scaleBitmapForNotification(originalBitmap: Bitmap): Bitmap {
        val aspectRatio = 1.79f
        val width = originalBitmap.width
        val height = (width / aspectRatio).toInt()

        var left = 0
        var top = 0

        if (height > originalBitmap.height) {
            left = (width - originalBitmap.width) / 2
            top = (height - originalBitmap.height) / 2
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawColor(Color.TRANSPARENT)
        canvas.drawBitmap(originalBitmap, left.toFloat(), top.toFloat(), paint)
        originalBitmap.recycle()
        return bitmap
    }
}

