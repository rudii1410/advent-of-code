package problem

import util.FOUR_DIRECTION
import util.Runner
import util.Vector2
import util.withinBound

private fun List<String>.prepareData(): Pair<Vector2, MutableList<MutableList<Char>>> {
    var loc = Vector2(0, 0)
    return mapIndexed { y, line ->
        line.toMutableList()
            .also {
                it.indexOf('^')
                    .takeIf { x -> x >= 0 }
                    ?.let { x -> loc = Vector2(x, y) }
            }
    }.let { loc to it.toMutableList()}
}

private fun Int.turn(): Int {
    return (this + 1) % 4
}

private fun part01(input: List<String>): Int {
    var loc: Vector2
    val parsedMap = input.prepareData()
        .let {
            loc = it.first
            it.second
        }

    val visited = mutableSetOf<Vector2>()
    var dir = 0
    while (true) {
        visited.add(loc)
        (loc + FOUR_DIRECTION[dir])
            .takeIf { it.withinBound(parsedMap.size) }
            ?.let { parsedMap[it.y][it.x] }
            ?.let { if (it == '#') dir = dir.turn() }
            ?: break

        loc += FOUR_DIRECTION[dir]
    }
    return visited.size
}

private fun part02(input: List<String>): Int {
    val (start, parsedMap) = input.prepareData()
    val possibleBlocker = parsedMap.mapIndexed { y, line ->
        line.mapIndexedNotNull { x, c ->
            if (c == '#') null
            else Vector2(x, y)
        }
    }.flatten()
    
    return possibleBlocker.count { c ->
        parsedMap[c.y][c.x] = '#'

        var loc = start
        var dir = 0
        val visited = mutableMapOf<Pair<Vector2, Int>, Boolean>()
        var found = false
        while (!found) {
            visited[loc to dir] = true

            val ch = (loc + FOUR_DIRECTION[dir])
                .takeIf { it.withinBound(parsedMap.size) }
                ?.let { parsedMap[it.y][it.x] }
                ?: break

            if (ch == '#') dir = dir.turn()
            else loc += FOUR_DIRECTION[dir]

            if (visited[loc to dir] == true) {
                found = true
            }
        }

        parsedMap[c.y][c.x] = '.'
        found
    }
}

fun main() {
    Runner.get(::part01)
        .test("day06_test.in", 41)
        .run("day06.in")

    Runner.get(::part02)
        .test("day06_test.in", 6)
        .run("day06.in")
}