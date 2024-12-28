package util

import java.awt.geom.Line2D
// testing (O, 0.0,0.0) and (X, 1.0,1.0)
class RayCasting(private val start: Vector2 = (-1).toVec2()) {
    fun test(point: Vector2, polygon: Polygon): Boolean {
        // Eliminate polygon that not even in the bounding box
        if (!point.isInside(polygon.bounds)) return false

//        return true
        return polygon.points
            .windowed(2)
            .let { it + listOf(listOf(polygon.points.last(), polygon.points.first())) }
            .fold(0) { acc, i ->
                if (isIntersect(point, i.first(), i.last())) acc + 1 else acc
            }
            .also { println(it) }
            .let { it % 2 != 0 }
    }

    private fun isIntersect(test: Vector2, p1: Vector2, p2: Vector2): Boolean {
        return Line2D.linesIntersect(
            start.x, start.y,
            test.x, test.y,
            p1.x, p1.y,
            p2.x, p2.y
        )
    }
}