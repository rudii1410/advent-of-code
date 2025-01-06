package dev.rudii14.aoc.util

import util.Vector2

/**
 *   x--------*
 *   |        |
 *   |        |
 *   *--------y
 */
data class Rectangle(
    val x: Vector2,
    val y: Vector2,
) {
    fun isInside(point: Vector2): Boolean {
        return point.x >= x.x && point.x <= y.x && point.y >= x.y && point.y <= y.y
    }

    override fun toString(): String {
        return "(x:$x - y:$y)"
    }
}
