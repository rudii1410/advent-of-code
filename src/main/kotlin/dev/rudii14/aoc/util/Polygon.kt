package util

import java.awt.geom.Line2D

class Polygon (val points: List<Vector2>) {
    val bounds by lazy { calculateMinMaxBounds() }

    fun isInside(other: Polygon): Boolean {
        // Eliminate polygon that not even in the bounding box
        val rayCastTest = RayCasting()
        return points
            .fold(true) { acc, i ->
                val result = rayCastTest.test(i, other)
                println("$i against ${other.points}. result: $result")
                acc && result
            }
    }

    private fun calculateMinMaxBounds(): Rectangle {
        var min = Int.MAX_VALUE.toVec2()
        var max = Int.MIN_VALUE.toVec2()

        for (point in points) {
            if (point.x < min.x) min = min.copy(x = point.x)
            if (point.y < min.y) min = min.copy(y = point.y)
            if (point.x > max.x) max = max.copy(x = point.x)
            if (point.y > max.y) max = max.copy(y = point.y)
        }

        return Rectangle(min, max)
    }

    override fun toString(): String {
        return "points: ${points.joinToString("|")}, bounds: $bounds"
    }
}
