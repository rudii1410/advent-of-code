package dev.rudii14.aoc.util.math

import dev.rudii14.aoc.util.data.Point2
import dev.rudii14.aoc.util.data.Point3
import dev.rudii14.aoc.util.toVec2
import dev.rudii14.aoc.util.toVec3
import kotlin.math.hypot

fun distance2D(p1: Point2, p2: Point2): Double {
    return toVec2(p1, p2).let { hypot(it.x, it.y) }
}

fun distance3D(p1: Point3, p2: Point3): Double {
    return toVec3(p1, p2).let { hypot(hypot(it.x, it.y), it.z) }
}