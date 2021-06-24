package com.example.draw

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.BitmapFactory.decodeResource
import android.view.MotionEvent
import android.view.View


class Scene(context: Context) : View(context) {
    private var touching: Boolean = false
    private val car = Car()
    private val mat: Matrix = Matrix()
    private val bitmapSource = decodeResource(resources, R.drawable.car)
    private var bitmap = Bitmap.createBitmap(
        bitmapSource,
        0,
        0,
        bitmapSource.width,
        bitmapSource.height,
        mat,
        true
    )

    private var rotateBitmap: Bitmap = bitmap.rotate(0f)
    private val track by lazy {
        GenerateTrack(
            width - rotateBitmap.width,
            height - rotateBitmap.height*2
        )
    }

    private var nextPoint = GenerateTrack.Point(0, 0)

    override fun onDraw(canvas: Canvas?) {
        if (touching) {
            if (!car.isRun() && !track.isEnd) {
                nextPoint = track.getNextPoint()
                car.run(nextPoint.x.toFloat(), nextPoint.y.toFloat())
            }
        }

        canvas?.let { c ->
            c.save()
            c.drawColor(Color.LTGRAY)
            c.translate(0f, height.toFloat())
            c.scale(1f, -1f)

            if (car.isAngleChange()) {
                rotateBitmap = bitmap.rotate(car.angle)
            }
            c.drawBitmap(rotateBitmap, car.x, car.y, null)
            c.restore()
        }
        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val rect = Rect(car.x.toInt(), car.y.toInt(), rotateBitmap.width, rotateBitmap.height)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (rect.contains(x.toInt(), (height - y.toInt())))
                    touching = true
            }
        }
        return true
    }
}


fun Bitmap.rotate(degrees: Float): Bitmap {
    val mat = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, mat, true)
}