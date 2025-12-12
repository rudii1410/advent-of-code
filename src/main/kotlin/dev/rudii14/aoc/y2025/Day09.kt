package dev.rudii14.aoc.y2025

import dev.rudii14.aoc.util.Runner
import dev.rudii14.aoc.util.convexHull
import dev.rudii14.aoc.util.data.Point2
import java.awt.Polygon
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D
import kotlin.math.abs

private fun Point2.toPoint2D(): Point2D = Point2D.Double(x.toDouble(), y.toDouble())

private fun part01(input: List<String>): Long {
    val outerPoints = input
        .map { line ->
            line.split(",")
                .let { Point2(it[0].toInt(), it[1].toInt()) }
        }
        .let(::convexHull)
    
    var maxArea = 0L
    for (i in outerPoints.indices) {
        for (j in i until outerPoints.size) {
            if (i == j) continue
            
            (outerPoints[i] - outerPoints[j])
                .let { (abs(it.x) + 1).toLong() * (abs(it.y) + 1).toLong() }
                .also { maxArea = maxOf(maxArea, it) }
        }
    }
    return maxArea
}

private fun part02(input: List<String>): Long {
    val polygon = Polygon()
    val indices = input.map { line ->
        line.split(",")
            .let { Point2(it[0].toInt(), it[1].toInt()) }
            .also { polygon.addPoint(it.x, it.y) }
    }

    var maxArea = 0L
    for (i in indices.indices) {
        for (j in i until indices.size) {
            if (i == j) continue

            val rect = Rectangle2D.Double()
                .also { it.setFrameFromDiagonal(indices[i].toPoint2D(), indices[j].toPoint2D()) }
            if (polygon.contains(rect)) {
                val area = ((rect.width + 1) * (rect.height + 1)).toLong()
                maxArea = maxOf(maxArea, area)
            }
        }
    }
    
    return maxArea
}

fun main() {
    Runner.get(::part01)
        .test("day09_test.in", 50)
        .run("day09.in")

    Runner.get(::part02)
        .test("day09_test.in", 24)
        .run("day09.in")
}
