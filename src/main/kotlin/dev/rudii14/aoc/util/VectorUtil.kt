package dev.rudii14.aoc.util

import dev.rudii14.aoc.util.data.Point2
import dev.rudii14.aoc.util.data.Point3
import dev.rudii14.aoc.util.data.Vector2
import dev.rudii14.aoc.util.data.Vector3

val TOP_LEFT = Vector2(-1, -1)
val TOP = Vector2(0, -1)
val TOP_RIGHT = Vector2(1, -1)
val LEFT = Vector2(-1, 0)
val RIGHT = Vector2(1, 0)
val BOTTOM_LEFT = Vector2(-1, 1)
val BOTTOM = Vector2(0, 1)
val BOTTOM_RIGHT = Vector2(1, 1)

val ALL_AXIS_DIRECTION = listOf(
    TOP_LEFT, TOP, TOP_RIGHT,
    LEFT, RIGHT,
    BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT
)

val FOUR_DIRECTION = listOf(
    TOP, RIGHT, BOTTOM, LEFT
)
val FOUR_DIRECTION_MAP = mapOf(
    TOP to 0, RIGHT to 1, BOTTOM to 2, LEFT to 3
)

fun Vector2.withinBound(size: Int): Boolean {
    return withinBound(size, size)
}

fun Vector2.withinBound(xSize: Int, ySize: Int): Boolean {
    return x >= 0 && y >= 0 && x < xSize && y < ySize
}

fun Vector2.withinBound(pos: Vector2): Boolean {
    return withinBound(pos.x.toInt(), pos.y.toInt())
}

fun Pair<Int, Int>.toVec2(): Vector2 = Vector2(first, second)

fun toVec2(p: Point2, q: Point2) = (q - p).let { Vector2(it.x, it.y) }

fun toVec3(p: Point3, q: Point3) = (q - p).let { Vector3(it.x, it.y, it.z) }

fun Int.toVec2(): Vector2 = Vector2(this, this)

fun cross(a: Vector2, b: Vector2): Double {
    return (a.x * b.y) - (a.y * b.x)
}

