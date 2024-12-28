package problem.unfinished

import util.BOTTOM
import util.FOUR_DIRECTION
import util.Grid
import util.LEFT
import util.MutableGrid
import util.Polygon
import util.RIGHT
import util.Runner
import util.TOP
import util.Vector2
import util.get
import util.set
import util.withinBound


private typealias ShapeGroup = Pair<Char, Vector2>

private fun mapShape(
    map: Grid<Char>,
    start: Vector2,
    size: Int,
    toFind: Char,
    groupMarking: MutableGrid<ShapeGroup?>
): Set<Vector2> {
    val queue = ArrayDeque<Vector2>()
        .also { it.add(start) }
    val visited = mutableSetOf<Vector2>()

    while(queue.isNotEmpty()) {
        val pos = queue.removeFirst()

        if (!pos.withinBound(size)) continue
        if (pos in visited) continue
        if (map.get(pos) != toFind) continue

        visited.add(pos)
        groupMarking.set(pos, toFind to start)
        FOUR_DIRECTION
            .map { pos + it }
            .also(queue::addAll)
    }

    return visited
}
private fun Map<ShapeGroup, Set<Pair<Vector2, Vector2>>>.print(
    title: String
): Map<ShapeGroup, Set<Pair<Vector2, Vector2>>> {
    return also { println(title) }
        .onEach { (t, u) -> println("$t -> $u") }
}
private fun part01(input: List<String>): Int {
    val size = input.size
    val groupMarking = MutableList(size) { MutableList<ShapeGroup?>(size) { null } }
    val mapping = mutableMapOf<ShapeGroup, Set<Vector2>>()

    val map = input.map { it.toList() }
    // Map all the shape
    input.forEachIndexed { y, line ->
        line.forEachIndexed xAxis@ { x, c ->
            val pos = Vector2(x, y)
            if (groupMarking[y][x] != null) return@xAxis
            mapping[c to pos] = mapShape(map, pos, size, c, groupMarking)
        }
    }

    // Expand shape coordinates
    val expansions = listOf(
        Vector2(0, 0) to RIGHT,
        RIGHT to RIGHT + BOTTOM,
        RIGHT + BOTTOM to RIGHT + BOTTOM + LEFT,
        RIGHT + BOTTOM + LEFT to RIGHT + BOTTOM + LEFT + TOP
    )
    val directions = listOf(
        RIGHT, BOTTOM, LEFT, TOP
    )
    val asd = mapping
//        .also { println("Initial") }
//        .onEach { (t, u) -> println("$t -> $u") }
        .mapValues { pos ->
            pos.value
                .map { p -> expansions.map { p + it.first to p + it.second } }
                .flatten()
                .toSet()
        }
//        .print("After expansion:")
        .mapValues { pos ->
            val queue = ArrayDeque<Pair<Pair<Vector2, Vector2>, Int>>()
                .also { it.add(pos.value.first() to 0) }
            val visited = mutableSetOf<Pair<Vector2, Vector2>>()

            while (queue.isNotEmpty()) {
                queue.removeFirst()
                    .also { visited.add(it.first) }
                    .let { q ->
                        val (step, dir) = q.copy(
                            second = (q.second - 1).let { if (it < 0) 4 + it else it }
                        )

                        for (i in 0..3) {
                            val newDir = (dir + i) % 4
                            val validQ = (step.second to step.second + directions[newDir])
                                .also { if (it == pos.value.first()) queue.clear() }
                                .takeIf { it in pos.value }
                                ?.takeIf { it !in visited }

                            if (validQ != null) {
                                visited.add(validQ)
                                queue.add(validQ to newDir)
                                break
                            }
                        }
                    }
            }
            visited
        }
//        .print("After outer-line traversal:")
        .mapValues { pos ->
            val polygon = pos.value
                .map { it.first }
                .let { Polygon(it) }
            pos.value to polygon
        }
//        .also { println("After polygon op") }
//        .onEach { (t, u) -> println("$t -> $u") }
//        .onEach {
//            println("Region ${it.key.first}, price: ${mapping.getOrDefault(it.key, emptySet()).size} * ${it.value.size} = ${mapping.getOrDefault(it.key, emptySet()).size * it.value.size}")
//        }

    for (i in asd) {
        for (j in asd) {
            if (i == j) continue
            println("testing ${j.key} and ${i.key}")
            i.value.second
                .isInside(j.value.second)
                .takeIf { it }
                ?.let { println("${j.key} is inside ${i.key}") }
        }
    }

    return 0
}

private fun part02(input: List<String>): Int {
    return 0
}

fun main() {
    Runner.get(::part01)
        .test("day12_test.in", 140)
        .test("day12_test2.in", 772)
        .test("day12_test3.in", 1930)
        .run("day12.in")

    Runner.get(::part02)
        .test("day12_test.in", 0)
        .run("day12.in")
}
