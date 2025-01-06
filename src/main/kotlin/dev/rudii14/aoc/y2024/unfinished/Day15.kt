package dev.rudii14.aoc.y2024.unfinished

import dev.rudii14.aoc.util.FOUR_DIRECTION
import dev.rudii14.aoc.util.Grid
import dev.rudii14.aoc.util.MutableGrid
import dev.rudii14.aoc.util.Runner
import util.Vector2
import dev.rudii14.aoc.util.get

private fun traverse(
    boxes: MutableMap<Vector2, MutableMap<Vector2, Int>>,
    map: Grid<Char>,
    pos: Vector2,
    dir: Vector2
): Int {
    if (map.get(pos) != 'O') return 0

    val result = 1 + traverse(boxes, map, pos + dir, dir)
    if (!boxes.contains(pos)) boxes[pos] = FOUR_DIRECTION.associateWith { 0 }.toMutableMap()
    boxes[pos]!![dir] = result
    return result
}
private fun List<List<Char>>.scan(): Pair<Vector2, Map<Vector2, Map<Vector2, Int>>> {
    val boxes = mutableMapOf<Vector2, MutableMap<Vector2, Int>>()
    var robotPos = Vector2.ZERO

    for (y in indices) {
        for (x in indices) {
            val pos = Vector2(x, y)
            if (this[y][x] == '@') robotPos = pos
            else if (this[y][x] == 'O') {
                FOUR_DIRECTION.forEach { dir ->
                    traverse(boxes, this, pos, dir)
                }
            }
        }
    }

    return robotPos to boxes
}
private fun move(
    map: MutableGrid<Char>,
    boxes: MutableMap<Vector2, MutableMap<Vector2, Int>>,
    pos: Vector2,
    direction: Vector2
): Vector2 {
    val next = (pos + direction)
        .let { map.get(it) }

    if (next == '#') return pos
    if (next == '.') return pos + direction

    val size = boxes[pos + direction]?.get(direction)!!


    return pos
}
private fun part01(input: String): Int {
    val sections = input.split("\n\n")
    val map = sections.first()
        .split("\n")
        .map { it.toMutableList() }
        .toMutableList()
    val movement = sections.last()
        .replace("\n", "")
    val (robotPos, box) = map.scan()


//    movement.forEach { c ->
//        move
//    }

    return 0
}

private fun part02(input: List<String>): Int {
    return 0
}

fun main() {
    Runner.get(::part01)
        .test("day15_test.in", 2028)
        .run("day15.in")

    Runner.get(::part02)
        .test("day15_test.in", 0)
        .run("day15.in")
}
