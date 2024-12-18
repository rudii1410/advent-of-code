package problem

import util.FOUR_DIRECTION
import util.Runner
import util.Vector2
import util.toVec2
import util.withinBound
import kotlin.math.floor

private fun List<Vector2>.drawMap(size: Int): List<List<Char>> {
    return List(size) { y ->
        List(size) { x ->
            if (Vector2(x, y) in this) '#' else '.'
        }
    }
}
private fun List<List<Char>>.traverse(start: Vector2, end: Vector2): Int {
    val queue = ArrayDeque<Pair<Vector2, Int>>()
        .also { it.add(start to 0) }
    val visited = mutableSetOf<Vector2>()
        .also { it.add(start) }

    while (queue.isNotEmpty()) {
        val (pos, step) = queue.removeFirst()

        if (pos == end) return step

        FOUR_DIRECTION
            .mapNotNull {
                val newPos = pos + it
                if (!newPos.withinBound(size)) return@mapNotNull null
                if (this[newPos.y][newPos.x] == '#') return@mapNotNull null
                if (newPos in visited) return@mapNotNull null

                visited.add(newPos)
                newPos to step + 1
            }
            .also(queue::addAll)
    }

    return 0
}
private fun List<String>.readInput(): List<Vector2> {
    return map { line ->
        line.split(",")
            .let { Vector2(it.first().toInt(), it.last().toInt()) }
    }
}
private fun List<Vector2>.calculateStep(gridSize: Int): Int {
    return drawMap(gridSize)
        .traverse(Vector2.ZERO, (gridSize - 1).toVec2())
}
private fun part01(input: List<String>): Int {
    return input.readInput()
        .take(1024)
        .calculateStep(71)
}

private fun part02(input: List<String>): String {
    val gridSize = 71
    val fallenMemory = input.readInput()

    var low = 0
    var high = fallenMemory.size - 1
    while (high >= low) {
        val mid = low + floor((high - low).toDouble() / 2).toInt()

        val isConnected = fallenMemory.take(mid)
            .calculateStep(gridSize)
            .let { it > 0 }

        if (isConnected) {
            low = mid + 1
        } else {
            val isPrevConnected = fallenMemory.take(mid - 1)
                .calculateStep(gridSize)
                .let { it > 0 }

            if (isPrevConnected) return fallenMemory[mid - 1].toString()
            else high = mid - 1
        }
    }

    return ""
}

fun main() {
    Runner.get(::part01)
//        .test("day18_test.in", 22)
        .run("day18.in")

    Runner.get(::part02)
//        .test("day18_test.in", "6,1")
        .run("day18.in")
}
