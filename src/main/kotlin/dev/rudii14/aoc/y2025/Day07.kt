package dev.rudii14.aoc.y2025

import dev.rudii14.aoc.util.Grid
import dev.rudii14.aoc.util.Runner
import dev.rudii14.aoc.util.data.Point2
import dev.rudii14.aoc.util.get
import java.math.BigInteger

private val DOWN = listOf(Point2(0, 1))
private val SIDES = listOf(Point2(-1, 0), Point2(1, 0))

private fun traverse(
    grid: Grid<Char>,
    visited: MutableMap<Point2, Boolean>,
    weight: MutableMap<Point2, BigInteger>,
    pos: Point2,
    size: Point2
): BigInteger {
    if (!pos.withinBound(size.x, size.y)) return BigInteger.ZERO
    if (visited.getOrDefault(pos, false)) return weight.getOrDefault(pos, BigInteger.ZERO)
    if (pos.y == size.y - 1) return BigInteger.ONE

    val ch = grid.get(pos)
    val result = if (ch == '^') {
        visited[pos] = true
        SIDES.sumOf { traverse(grid, visited, weight, pos + it, size) }
    } else {
        DOWN.sumOf { traverse(grid, visited, weight, pos + it, size) }
    }
    
    if (ch != '.') weight[pos] = result
    return result
}

private fun List<String>.solve(): Pair<Int, BigInteger> {
    val start = Point2(first().indexOf('S'), 0)
    val size = Point2(first().length, size)

    return (mutableMapOf<Point2, Boolean>() to mutableMapOf<Point2, BigInteger>())
        .also { (visited, weight) ->
            traverse(
                map { it.toList() },
                visited,
                weight,
                start,
                size
            )
        }
        .let { (visited, weight) -> visited.size to weight[start]!! }
}

private fun part01(input: List<String>): Int {
    val (visited, _) = input.solve()
    return visited
}

private fun part02(input: List<String>): BigInteger {
    val (_, weight) = input.solve()
    return weight
}

fun main() {
    Runner.get(::part01)
        .test("day07_test.in", 21)
        .run("day07.in")

    Runner.get(::part02)
        .test("day07_test.in", BigInteger.valueOf(40))
        .run("day07.in")
}