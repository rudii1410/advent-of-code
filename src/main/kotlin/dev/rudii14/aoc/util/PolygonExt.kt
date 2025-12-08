package dev.rudii14.aoc.util

import dev.rudii14.aoc.util.data.Point2
import dev.rudii14.aoc.util.math.distance2D
import kotlin.math.abs
import kotlin.math.atan2

fun convexHull(points: List<Point2>, onlyOuterPoint: Boolean = true): Polygon {
    if (points.size < 3) return points

    val pivot = points.minWithOrNull(compareBy<Point2> { it.y }.thenBy { it.x }) ?: return emptyList()

    val sortedPoints = points
        .minusElement(pivot)
        .sortedWith { v1, v2 ->
            val o = orientation(pivot, v1, v2)
            if (o == Orientation.COLLINEAR) {
                distance2D(pivot, v1) compareTo distance2D(pivot, v2)
            } else {
                val dV1 = toVec2(v1, pivot)
                val dV2 = toVec2(v2, pivot)
                (atan2(dV1.y, dV1.x) - atan2(dV2.y, dV2.x)) compareTo 0.0
            }
        }

    val hull = mutableListOf(
        pivot,
        sortedPoints[0],
        sortedPoints[1]
    )

    val orientationCheck = listOfNotNull(
        Orientation.CCW,
        if (!onlyOuterPoint) Orientation.CW else null,
    )
    for (i in 2 until sortedPoints.size) {
        while (true) {
            if (hull.size < 2) break
            if (orientation(hull[hull.size - 2], hull.last(), sortedPoints[i]) in orientationCheck) break

            hull.removeLast()
        }
        hull.add(sortedPoints[i])
    }

    return hull
}

fun ccw(p: Point2, q: Point2, r: Point2): Boolean {
    return cross(toVec2(p, q), toVec2(p, r)) > 0.0
}

fun collinear(p: Point2, q: Point2, r: Point2): Boolean {
    return abs(cross(toVec2(p, q), toVec2(p, r))) < 0.0
}

enum class Orientation {
    COLLINEAR, CCW, CW
}

fun orientation(p: Point2, q: Point2, r: Point2): Orientation {
    return when {
        collinear(p, q, r) -> Orientation.COLLINEAR
        ccw(p, q, r) -> Orientation.CCW
        else -> Orientation.CW
    }
}