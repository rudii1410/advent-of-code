package problem

import util.FOUR_DIRECTION
import util.Runner
import util.Vector2
import util.withinBound

private fun MutableList<MutableList<MutableList<MutableList<Vector2>>>>.drawLine(start: Vector2, dirIdx: Int) {
    val direction = FOUR_DIRECTION[dirIdx]
    var loc = start + direction
    while(loc.withinBound(size)) {
        this[loc.y][loc.x][dirIdx].add(start)
        loc += direction
    }
}
private fun <T> List<Vector2>.product(other: List<Vector2>, cb: (Pair<Vector2, Vector2>) -> T): List<T> {
    return this.map { l ->
        other.map { r -> cb(l to r) }
    }.flatten()
}
private fun List<String>.prepareData(): Pair<MutableMap<Vector2, Char>, MutableList<MutableList<MutableList<MutableList<Vector2>>>>> {
    val antennas = mutableMapOf<Vector2, Char>()
    val antennasLine = MutableList(size) { MutableList(size) { MutableList(4) { mutableListOf<Vector2>() } } }
    forEachIndexed yAxis@ { y, line ->
        line.forEachIndexed xAxis@ { x, c ->
            if (c == '.') return@xAxis

            Vector2(x, y)
                .also { antennas[it] = c }
                .also {
                    for (i in 0..3) antennasLine.drawLine(it, i)
                }
        }
    }

    return antennas to antennasLine
}

private fun part01(input: List<String>): Int {
    val size = input.size
    val (antennas, antennasLine) = input.prepareData()

    val overlaps = MutableList(size) { MutableList(size) { false } }
    fun calculate(p: Pair<Vector2, Vector2>): Int {
        if (antennas[p.first] != antennas[p.second]) return 0
        val diff = (p.first - p.second)
        var result = 0
        result += (p.first + diff)
            .takeIf { it.withinBound(size) && !overlaps[it.y][it.x] }
            ?.also { overlaps[it.y][it.x] = true }
            ?.let { 1 } ?: 0
        result += (p.second - diff)
            .takeIf { it.withinBound(size) && !overlaps[it.y][it.x] }
            ?.also { overlaps[it.y][it.x] = true }
            ?.let { 1 } ?: 0

        return result
    }
    var result = 0
    val dirCombination = listOf(
        0 to 2, 1 to 3, 0 to 3, 0 to 1, 2 to 3, 2 to 1
    )
    antennasLine.forEach yAxis@ { line ->
        line.forEach xAxis@ { direction ->
            result += dirCombination.sumOf {
                direction[it.first]
                    .product(direction[it.second], ::calculate)
                    .sum()
            }
        }
    }
    return result
}

private fun part02(input: List<String>): Int {
    val size = input.size
    val (antennas, antennasLine) = input.prepareData()

    val overlaps = MutableList(size) { MutableList(size) { false } }
    fun calculate(p: Pair<Vector2, Vector2>): Int {
        if (antennas[p.first] != antennas[p.second]) return 0
        val diff = (p.first - p.second)
        var result = 0
        var start = p.first + diff
        while (start.withinBound(size)) {
            if (overlaps[start.y][start.x]) {
                start += diff
                continue
            }
            overlaps[start.y][start.x] = true
            result += 1
            start += diff
        }
        start = p.second
        while (start.withinBound(size)) {
            if (overlaps[start.y][start.x]) {
                start -= diff
                continue
            }
            overlaps[start.y][start.x] = true
            result += 1
            start -= diff
        }

        return result
    }
    var result = 0
    val dirCombination = listOf(
        0 to 2, 1 to 3, 0 to 3, 0 to 1, 2 to 3, 2 to 1
    )
    antennasLine.forEach yAxis@ { line ->
        line.forEach xAxis@ { direction ->
            result += dirCombination.sumOf {
                direction[it.first]
                    .product(direction[it.second], ::calculate)
                    .sum()
            }
        }
    }
    return result
}

fun main() {
    Runner.get(::part01)
        .test("day08_test.in", 14)
        .run("day08.in")

    Runner.get(::part02)
        .test("day08_test.in", 34)
        .run("day08.in")
}