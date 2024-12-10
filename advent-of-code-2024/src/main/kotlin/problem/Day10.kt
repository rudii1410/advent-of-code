package problem

import util.Runner

private data class Vector2(val x: Int, val y: Int) {
    operator fun plus(other: Vector2): Vector2 {
        return Vector2(x = x + other.x, y = y + other.y)
    }
}

private fun List<String>.prepareInput(): List<List<Int>> {
    return map { line ->
        line.toList()
            .map { it.digitToInt() }
    }
}
private val directions = listOf(
    Vector2(1, 0),
    Vector2(-1, 0),
    Vector2(0, 1),
    Vector2(0, -1)
)

private fun traverse(
    visited: MutableList<MutableList<MutableMap<Vector2, Boolean>>>,
    toFind: Int,
    pos: Vector2,
    start: Vector2,
    size: Int,
    map: List<List<Int>>
): Int {
    if (pos.x < 0 || pos.x >= size || pos.y < 0 || pos.y >= size) return 0
    if (toFind != map[pos.y][pos.x]) return 0
    if (visited[pos.y][pos.x][start] == true) return 0
    if (toFind == 9) {
        visited[pos.y][pos.x][start] = true
        return 1
    }

    return directions
        .sumOf { traverse(visited, toFind + 1, pos + it, start, size, map) }
}
private fun part01(input: List<String>): Int {
    val size = input.size
    val memo = MutableList(size) { MutableList(size) { mutableMapOf<Vector2, Boolean>() } }
    val map = input.prepareInput()
    return map.mapIndexed { y, line ->
        line.mapIndexed xAxis@ { x, i ->
            if (i != 0) return@xAxis 0
            return@xAxis Vector2(x, y)
                .let { traverse(memo, 0, it, it, size, map) }
        }.sum()
    }.sum()
}

private fun traverse2(
    memo: MutableList<MutableList<Int>>,
    toFind: Int,
    pos: Vector2,
    size: Int,
    map: List<List<Int>>
): Int {
    if (pos.x < 0 || pos.x >= size || pos.y < 0 || pos.y >= size) return 0
    if (toFind != map[pos.y][pos.x]) return 0
    if (memo[pos.y][pos.x] > 0) return memo[pos.y][pos.x]
    if (toFind == 9) return 1

    val result = directions
        .sumOf { traverse2(memo, toFind + 1, pos + it, size, map) }

    if (result > 0) memo[pos.y][pos.x] += result
    return memo[pos.y][pos.x]
}
private fun part02(input: List<String>): Int {
    val size = input.size
    val memo = MutableList(size) { MutableList(size) { 0 } }
    val map = input.prepareInput()
    return map.mapIndexed { y, line ->
        line.mapIndexed xAxis@ { x, i ->
            if (i != 0) return@xAxis 0
            return@xAxis traverse2(memo, 0, Vector2(x, y), size, map)
        }.sum()
    }.sum()
}

fun main() {
    Runner.get(::part01)
        .test("day10_test.in", 36)
        .run("day10.in")

    Runner.get(::part02)
        .test("day10_test.in", 81)
        .run("day10.in")
}