package dev.rudii14.aoc.util

import dev.rudii14.aoc.util.data.Point2
import dev.rudii14.aoc.util.math.distance2D
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.sqrt

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

fun onSegment(p: Point2, q: Point2, r: Point2): Boolean {
    return (q.x <= maxOf(p.x, r.x) && q.x >= minOf(p.x, r.x) &&
            q.y <= maxOf(p.y, r.y) && q.y >= minOf(p.y, r.y))
}

fun isRectInsidePolygon(rect: List<Point2>, points: Polygon): Boolean {
    val shouldPrint = rect == listOf(Point2(x=2, y=3), Point2(x=9, y=3), Point2(x=9, y=5), Point2(x=2, y=5))
    // 1. Check if all four corners are inside the polygon
    for (corner in rect) {
        if (!corner.inPolygon(points)) {
            if (shouldPrint) {
                println(points)
                println("not in corner $corner")
            }
            return false
        }
    }
    if (shouldPrint) println("all corner good")

    // 2. Check if any edge of the rectangle intersects any edge of the polygon
    val rectEdges = (rect + listOf(rect.first())).windowed(2)
    for ((p1Rect, q1Rect) in rectEdges) {
        for (i in 0 until points.size) {
            val p2Poly = points[i]
            val q2Poly = points[(i + 1) % points.size]

            if (doIntersect(p1Rect, q1Rect, p2Poly, q2Poly)) {
                if (shouldPrint) println("Rectangle edge (${p1Rect.x}, ${p1Rect.y}) to (${q1Rect.x}, ${q1Rect.y}) intersects polygon edge.")
                return false
            }
        }
    }

    // If both checks pass, the rectangle is inside the polygon
    return true
}

fun Point2.inPolygon(polygon: List<Point2>): Boolean {
    val numVertices = polygon.size
    var intersections = 0

    // Loop through each edge in the polygon
    for (i in 0 until numVertices) {
        val p1 = polygon[i]
        val p2 = polygon[(i + 1) % numVertices]

        if (onSegment(p1, p2, this)) return true
        
        // Check if the ray intersects the edge
        if (((p1.y > this.y) != (p2.y > this.y)) &&
            (this.x < (p2.x - p1.x) * (this.y - p1.y) / (p2.y - p1.y) + p1.x)) {
            intersections++
        }
    }

    return intersections % 2 == 1
}

fun doIntersect(p1: Point2, q1: Point2, p2: Point2, q2: Point2): Boolean {
    // Find the four orientations needed for general and special cases
    val o1 = orientation(p1, q1, p2)
    val o2 = orientation(p1, q1, q2)
    val o3 = orientation(p2, q2, p1)
    val o4 = orientation(p2, q2, q1)

    // General case
    if (o1 != o2 && o3 != o4) {
        return true
    }

    // Special cases (collinear points)
    // p1, q1 and p2 are collinear and p2 lies on segment p1q1
    if (o1 == Orientation.COLLINEAR && onSegment(p1, p2, q1)) return true
    // p1, q1 and q2 are collinear and q2 lies on segment p1q1
    if (o2 == Orientation.COLLINEAR && onSegment(p1, q2, q1)) return true
    // p2, q2 and p1 are collinear and p1 lies on segment p2q2
    if (o3 == Orientation.COLLINEAR && onSegment(p2, p1, q2)) return true
    // p2, q2 and q1 are collinear and q1 lies on segment p2q2
    if (o4 == Orientation.COLLINEAR && onSegment(p2, q1, q2)) return true

    return false // Doesn't fall in any of the above cases
}

fun angle(a: Point2, o: Point2, b: Point2): Double {
    val oa = toVec2(o, a)
    val ob = toVec2(o, b)
    return acos(oa.dot(ob) / sqrt(oa.dot(oa) * ob.dot(ob)))
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
