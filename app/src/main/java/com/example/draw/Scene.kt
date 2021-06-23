package com.example.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.view.View

class Scene(context: Context) : View(context) {

    override fun onDraw(canvas: Canvas?) {

        canvas?.let {
                c -> c.drawColor(Color.LTGRAY)


        }
        invalidate()
    }
}