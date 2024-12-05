package problem

import util.Runner

private val xmas = listOf('X', 'M', 'A', 'S')

private fun traverse(
    data: List<List<Char>>,
    x: Int,
    y: Int,
    direction: Pair<Int, Int>,
    xSize: Int,
    ySize: Int,
    toFind: Int,
): Int {
    if (x < 0 || x >= xSize || y < 0 || y >= ySize) return 0
    if (toFind > 3) return 0
    if (data[y][x] != xmas[toFind]) return 0

    if (xmas[toFind] == 'S') return 1

    return traverse(data, x + direction.first, y + direction.second, direction, xSize, ySize, toFind + 1)
}
private fun part01(input: List<String>): Int {
    val data = input.map { it.toCharArray().toList() }
    val size = data.size
    val direction = listOf(
        (-1 to -1), (0 to -1), (1 to -1),
        (-1 to 0), (1 to 0),
        (-1 to 1), (0 to 1), (1 to 1)
    )
    return data.mapIndexed { y, line ->
        line.mapIndexedNotNull { x, c ->
            if (c != 'X') return@mapIndexedNotNull null
            direction.sumOf { traverse(data, x, y, it, size, size, 0) }
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
    val xSize = data.first().size
    val ySize = data.size
    return data.mapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if (x < 1 || x >= xSize - 1 || y < 1 || y >= ySize - 1) 0
            else if (c == 'A' && data.isOppositeValid(x, y)) 1
            else 0
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
