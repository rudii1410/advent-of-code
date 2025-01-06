package dev.rudii14.aoc.y2024

import dev.rudii14.aoc.util.FOUR_DIRECTION
import dev.rudii14.aoc.util.MutableGrid
import dev.rudii14.aoc.util.Runner
import util.Vector2
import dev.rudii14.aoc.util.createMutableGrid
import dev.rudii14.aoc.util.get
import dev.rudii14.aoc.util.set
import dev.rudii14.aoc.util.withinBound

typealias AntennasMap = MutableGrid<MutableList<MutableList<Vector2>>>

private fun AntennasMap.drawLine(start: Vector2, dirIdx: Int) {
    val direction = FOUR_DIRECTION[dirIdx]
    var loc = start + direction
    while(loc.withinBound(size)) {
        this.get(loc)[dirIdx].add(start)
        loc += direction
    }
}
private fun <T> List<Vector2>.product(other: List<Vector2>, cb: (Pair<Vector2, Vector2>) -> T): List<T> {
    return this.map { l ->
        other.map { r -> cb(l to r) }
    }.flatten()
}
private fun AntennasMap.compute(calculate: (Pair<Vector2, Vector2>, MutableGrid<Boolean>) -> Int): Int {
    val dirCombination = listOf(
        0 to 2, 1 to 3, 0 to 3, 0 to 1, 2 to 3, 2 to 1
    )
    val overlaps = createMutableGrid(size) { false }
    return sumOf { line ->
        line.sumOf { direction ->
            dirCombination.sumOf {
                direction[it.first]
                    .product(direction[it.second]) { p ->
                        calculate(p, overlaps)
                    }
                    .sum()
            }
        }
    }
}
private fun List<String>.prepareData(): Pair<MutableMap<Vector2, Char>, AntennasMap> {
    val antennas = mutableMapOf<Vector2, Char>()
    val antennasLine = createMutableGrid(size) { MutableList(4) { mutableListOf<Vector2>() } }
    forEachIndexed yAxis@ { y, line ->
        line.forEachIndexed xAxis@ { x, c ->
            if (c == '.') return@xAxis

            Vector2(x, y)
                .also { antennas[it] = c }
                .also { v ->
                    repeat(3) { antennasLine.drawLine(v, it) }
                }
        }
    }

    return antennas to antennasLine
}

private fun part01(input: List<String>): Int {
    val size = input.size
    val (antennas, antennasLine) = input.prepareData()

    fun calculate(p: Pair<Vector2, Vector2>, overlaps: MutableGrid<Boolean>): Int {
        if (antennas[p.first] != antennas[p.second]) return 0
        return (p.first - p.second)
            .let { listOf(p.first + it, p.second - it) }
            .count { x ->
                x.takeIf { it.withinBound(size) && !overlaps.get(it) }
                    ?.also { overlaps.set(it, true) }
                    .let { it != null }
            }
    }
    return antennasLine.compute(::calculate)
}

private fun part02(input: List<String>): Int {
    val size = input.size
    val (antennas, antennasLine) = input.prepareData()

    fun calculate(p: Pair<Vector2, Vector2>, overlaps: MutableGrid<Boolean>): Int {
        if (antennas[p.first] != antennas[p.second]) return 0
        return (p.first - p.second)
            .let { listOf(p.first to it, p.second to it * -1) }
            .sumOf {
                var res = 0
                var start = it.first
                while (start.withinBound(size)) {
                    if (!overlaps.get(start)) {
                        overlaps.set(start, true)
                        res += 1
                    }
                    start += it.second
                }
                res
            }
    }
    return antennasLine.compute(::calculate)
}

fun main() {
    Runner.get(::part01)
        .test("day08_test.in", 14)
        .run("day08.in")

    Runner.get(::part02)
        .test("day08_test.in", 34)
        .run("day08.in")
}
