package problem

import util.FOUR_DIRECTION
import util.Runner
import util.Vector2

private fun List<String>.prepareInput(): List<List<Int>> {
    return map { line ->
        line.toList()
            .map { it.digitToInt() }
    }
}

private fun traverse(
    memo: MutableList<MutableList<List<Vector2>>>,
    toFind: Int,
    pos: Vector2,
    size: Int,
    map: List<List<Int>>
): List<Vector2> {
    if (pos.x < 0 || pos.x >= size || pos.y < 0 || pos.y >= size) return emptyList()
    if (toFind != map[pos.y][pos.x]) return emptyList()
    if (memo[pos.y][pos.x].isNotEmpty()) return memo[pos.y][pos.x]
    if (toFind == 9) return listOf(pos)

    FOUR_DIRECTION
        .map { traverse(memo, toFind + 1, pos + it, size, map) }
        .flatten()
        .also { memo[pos.y][pos.x] += it }

    return memo[pos.y][pos.x]
}

private fun part01(input: List<String>): Int {
    val size = input.size
    val memo = MutableList(size) { MutableList(size) { listOf<Vector2>() } }
    val map = input.prepareInput()
    return map.mapIndexed { y, line ->
        line.mapIndexed xAxis@ { x, i ->
            if (i != 0) return@xAxis emptyList()
            return@xAxis traverse(memo, 0, Vector2(x, y), size, map).distinct()
        }.flatten().size
    }.sum()
}

private fun part02(input: List<String>): Int {
    val size = input.size
    val memo = MutableList(size) { MutableList(size) { listOf<Vector2>() } }
    val map = input.prepareInput()
    return map.mapIndexed { y, line ->
        line.mapIndexed xAxis@ { x, i ->
            if (i != 0) return@xAxis emptyList()
            return@xAxis traverse(memo, 0, Vector2(x, y), size, map)
        }.flatten().size
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
