package problem

import util.ALL_AXIS_DIRECTION
import util.Grid
import util.Runner
import util.Vector2
import util.get
import util.withinBound

private val xmas = listOf('X', 'M', 'A', 'S')

private fun traverse(
    data: Grid<Char>,
    pos: Vector2,
    direction: Vector2,
    size: Int,
    toFind: Int,
): Int {
    if (!pos.withinBound(size)) return 0
    if (data.get(pos) != xmas[toFind]) return 0

    if (xmas[toFind] == 'S') return 1

    return traverse(data, pos + direction, direction, size, toFind + 1)
}
private fun part01(input: List<String>): Int {
    val data = input.map { it.toCharArray().toList() }
    val size = data.size
    return data.mapIndexed { y, line ->
        line.mapIndexedNotNull { x, c ->
            if (c != 'X') return@mapIndexedNotNull null
            ALL_AXIS_DIRECTION.sumOf { traverse(data, Vector2(x, y), it, size, 0) }
        }.sum()
    }.sum()
}

private fun List<List<Char>>.isOppositeValid(x: Int, y: Int): Boolean {
    val mapping = mapOf(
        (-1 to -1) to (1 to 1),
        (1 to -1) to (-1 to 1)
    )
    val cMapping = mapOf(
        'M' to 'S',
        'S' to 'M'
    )
    return mapping
        .map { (l, r) ->
            cMapping[this[y + l.second][x + l.first]] == this[y + r.second][x + r.first]
        }
        .all { it }
}
private fun part02(input: List<String>): Int {
    val data = input.map { it.toCharArray().toList() }
    val size = data.size
    return data.mapIndexed { y, line ->
        line.mapIndexed xAxis@ { x, c ->
            if (x < 1 || x >= size - 1 || y < 1 || y >= size - 1) return@xAxis 0
            if (c != 'A') return@xAxis 0

            if (data.isOppositeValid(x, y)) 1 else 0
        }.sum()
    }.sum()
}

fun main() {
    Runner.get(::part01)
        .test("day4_test1.in", 18)
        .run("day4.in")

    Runner.get(::part02)
        .test("day4_test1.in", 9)
        .run("day4.in")
}
