package problem.unfinished

import util.BOTTOM
import util.FOUR_DIRECTION
import util.Grid
import util.LEFT
import util.MutableGrid
import util.RIGHT
import util.Runner
import util.TOP
import util.Vector2
import util.get
import util.set
import util.withinBound

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return this.copy(
        first = first + other.first,
        second = second + other.second
    )
}

private fun traverse(
    pos: Vector2,
    size: Int,
    toFind: Char,
    ch: Grid<Char>,
    connected: MutableGrid<Vector2?>
): Int {
    val queue = ArrayDeque<Vector2>()
        .also { it.add(pos) }
    val visited = mutableSetOf<Vector2>()

    var plot = 0
    var perimeter = 0
    while (queue.isNotEmpty()) {
        val v = queue.removeFirst()
        if (v in visited) continue
        if (!v.withinBound(size) || ch.get(v) != toFind) {
            perimeter += 1
            continue
        }

        connected.set(v, pos)
        visited.add(v)
        plot += 1
        queue.addAll(FOUR_DIRECTION.map { v + it })
    }
    return plot * perimeter
}

private fun part01(input: List<String>): Int {
    val size = input.size
    val map = input.map { it.toList() }
    val connected = MutableList(size) { MutableList<Vector2?>(size) { null } }
    return map.mapIndexed { y, line ->
        line.mapIndexedNotNull { x, c ->
            if (connected[y][x] != null) return@mapIndexedNotNull null
            traverse(Vector2(x, y), size, c, map, connected)
        }.sum()
    }.sum()
}

//private val directions = listOf(
//    LEFT, BOTTOM, RIGHT, BOTTOM
//)
private val pairs = listOf(
    TOP to LEFT, TOP to RIGHT,
    BOTTOM to LEFT, BOTTOM to RIGHT
)
private fun traverse2(
    pos: Vector2,
    size: Int,
    toFind: Char,
    ch: Grid<Char>,
    connected: MutableGrid<Vector2?>
): Int {
    val queue = ArrayDeque<Vector2>()
        .also { it.add(pos) }
    val visited = mutableSetOf<Vector2>()

    var plot = 0
    var corners = 0
    while (queue.isNotEmpty()) {
        val v = queue.removeFirst()
        if (v in visited) continue
        if (!v.withinBound(size) || ch.get(v) != toFind) {
            continue
        }

        corners += pairs.fold(0) { acc, p ->
            val first = (v + p.first).takeIf { it.withinBound(size) }?.let { ch.get(it) } ?: '-'
            val second = (v + p.second).takeIf { it.withinBound(size) }?.let { ch.get(it) } ?: '?'
            acc + if (first != toFind && second != toFind) 1 else 0
        }
        println(corners)
        connected.set(v, pos)
        visited.add(v)
        plot += 1
        queue.addAll(FOUR_DIRECTION.map { v + it })
    }
    println("$plot $corners")
    return plot * corners
}
private fun part02(input: List<String>): Int {
    val size = input.size
    val map = input.map { it.toList() }
    val connected = MutableList(size) { MutableList<Vector2?>(size) { null } }
    return map.mapIndexed { y, line ->
        line.mapIndexedNotNull { x, c ->
            if (connected[y][x] != null) return@mapIndexedNotNull null
            traverse2(Vector2(x, y), size, c, map, connected)
        }.sum()
    }.sum()
}

fun main() {
    Runner.get(::part01)
        .test("day12_test.in", 1930)
        .run("day12.in")

    Runner.get(::part02)
        .test("day12_test.in", 1206)
        .run("day12.in")
}
