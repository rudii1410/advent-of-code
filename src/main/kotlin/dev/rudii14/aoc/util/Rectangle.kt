package dev.rudii14.aoc.util

import dev.rudii14.aoc.util.data.Point2
import dev.rudii14.aoc.util.data.Vector2

/**
 *   x--------*
 *   |        |
 *   |        |
 *   *--------y
 */
data class Rectangle(
    val x: Point2,
    val y: Point2,
) {
    fun isInside(point: Vector2): Boolean {
        return point.x >= x.x && point.x <= y.x && point.y >= x.y && point.y <= y.y
    }

    override fun toString(): String {
        return "(x:$x - y:$y)"
    }
}
