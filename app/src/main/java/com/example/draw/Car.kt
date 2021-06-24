package com.example.draw

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import kotlin.math.atan2


class Car {
    private var _x: Float = .0f
    val x: Float get() = _x
    private var _y: Float = .0f
    val y: Float get() = _y
    private var _angle: Float = .0f
    val angle: Float get() = _angle

    private var animatorX: Animator? = null
    private var animatorY: Animator? = null
    private var animatorAngle: Animator? = null

    fun run(xDest: Float, yDest: Float) {
        if (animatorX?.isRunning == true || animatorY?.isRunning == true) return

        animatorAngle = ValueAnimator.ofFloat(angle, calcAngle(yDest, xDest)).apply {
            duration = 500
            interpolator = LinearInterpolator()
            addUpdateListener {
                _angle = it.animatedValue as Float
            }
            start()
        }

        animatorX = ValueAnimator.ofFloat(x, xDest).apply {
            duration = 1000

            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                _x = it.animatedValue as Float
            }
            start()
        }

        animatorY = ValueAnimator.ofFloat(y, yDest).apply {
            duration = 1000

            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                _y = it.animatedValue as Float
            }
            start()
        }

    }

    private fun calcAngle(yDest: Float, xDest: Float): Float {
        val angle = ((atan2(yDest - y, xDest - x) + 2 * Math.PI) * 180 / Math.PI % 360).toFloat()
        return if (angle in 0.0..180.0)
            angle
        else
            angle - 360


    }

    fun isRun() = (animatorX?.isRunning == true || animatorY?.isRunning == true)
    fun isAngleChange() = animatorAngle?.isRunning == true
}