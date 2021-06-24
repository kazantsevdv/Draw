package com.example.draw

import kotlin.random.Random

class GenerateTrack(val x: Int, val y: Int) {
    private val track: MutableList<Point> = arrayListOf()
    private var index = 0
    private var _isEnd = false
    val isEnd
        get() = _isEnd

    init {
        for (i in 0..numberpoint) {
            track.add(
                Point(
                    Random.nextInt(if (i == 0) 0 else track[i - 1].x, x / numberpoint * i + 1),
                    Random.nextInt(if (i == 0) 0 else track[i - 1].y, y / numberpoint * i + 1)
                )
            )
        }
    }

    fun getNextPoint(): Point {
        if (index < track.size) {

            val point = track[index]
            index++
            return point

        } else {
            _isEnd = true
            return Point(x, y)
        }

    }

    companion object {
        private val numberpoint = 5
    }

    class Point(val x: Int, val y: Int)
}