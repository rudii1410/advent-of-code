package dev.rudii14.aoc.util.data

data class Point2(val x: Int, val y: Int) {
    operator fun minus(otherPoint2: Point2) = Point2(x - otherPoint2.x, y - otherPoint2.y)
}
