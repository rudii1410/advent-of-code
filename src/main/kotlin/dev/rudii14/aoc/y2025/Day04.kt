package dev.rudii14.aoc.y2025

import dev.rudii14.aoc.util.ALL_AXIS_DIRECTION
import dev.rudii14.aoc.util.MutableGrid
import dev.rudii14.aoc.util.Runner
import dev.rudii14.aoc.util.data.Point2
import dev.rudii14.aoc.util.convexHull
import dev.rudii14.aoc.util.withinBound
import dev.rudii14.aoc.util.data.Vector2

private fun List<String>.prepareData() : MutableGrid<Char> {
    return map { it.toMutableList() }.toMutableList()
}

private fun part01(input: List<String>): Int {
    val grid = input.prepareData()
    val result = mutableSetOf<Vector2>()
    grid.forEachIndexed yAxis@ { y, line ->
        line.forEachIndexed xAxis@ { x, char ->
            if (char == '.') return@xAxis

            val currentPos = Vector2(x, y)
            ALL_AXIS_DIRECTION
                .map { dir ->
                    val newPos = currentPos + dir
                    if (!newPos.withinBound(grid.size)) return@map 0
                    if (grid[newPos.y.toInt()][newPos.x.toInt()] == '.') return@map 0
                    return@map 1
                }
                .sum()
                .let { if (it < 4) result.add(currentPos) }
        }
    }
    return result.size
}

private fun part02(input: List<String>): Int {
    val grid = input.map { it.toList() }
    val rollLocations = grid.mapIndexed yAxis@ { y, line ->
        line.mapIndexedNotNull xAxis@ { x, char ->
            if (char == '.') return@xAxis null
            else Point2(x, y)
        }
    }.flatten()
    val removedLocations = mutableSetOf<Point2>()
    while (true) {
        val newLocation = rollLocations - removedLocations
        val polygon = convexHull(newLocation, false)
        polygon
            .mapNotNull { pos ->
                ALL_AXIS_DIRECTION
                    .map { dir ->
                        val newPos = Vector2(pos.x, pos.y) + dir
                        if (!newPos.withinBound(grid.size)) return@map 0

                        return@map if (Point2(newPos.x.toInt(), newPos.y.toInt()) in newLocation) 1 else 0
                    }
                    .sum()
                    .let {
                        if (it < 4) {
                            removedLocations.add(pos)
                            return@let 1
                        }
                        0
                    }
            }
            .sum()
            .let { if (it == 0) break }
    }

    return removedLocations.size
}

fun main() {
    Runner.get(::part01)
        .test("day04_test.in", 13)
        .run("day04.in")

    Runner.get(::part02)
        .test("day04_test.in", 43)
        .run("day04.in")
}