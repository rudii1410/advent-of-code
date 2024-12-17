package problem

import util.*
import java.util.*

private fun List<String>.scanPoints(): Pair<Vector2, Vector2> {
    return foldIndexed(Vector2.ZERO to Vector2.ZERO) { y, accY, line ->
        line.foldIndexed(accY) { x, accX, c ->
            when (c) {
                'S' -> accX.copy(first = Vector2(x, y))
                'E' -> accX.copy(second = Vector2(x, y))
                else -> accX
            }
        }
    }
}

private data class Info(
    val pos: Vector2,
    val direction: Vector2,
    val visited: Set<Vector2>,
    val dist: Int
)
private fun traverse(start: Vector2, end: Vector2, map: List<String>): Pair<Int, Set<Vector2>> {
    val queue = ArrayDeque<Info>()
        .also { it.add(Info(start, RIGHT, setOf(start),0)) }
    val distances = mutableMapOf<Vector2, Int>()
        .withDefault { Int.MAX_VALUE }
        .also { it[start] = 0 }
    val bestPath = mutableMapOf<Pair<Vector2, Int>, Set<Vector2>>()
        .withDefault { setOf(start) }
    while (queue.isNotEmpty()) {
        val (pos, dir, visited, dist) = queue.poll()

        if (pos == end) {
            bestPath[pos to dist] = bestPath.getValue(pos to dist) + visited
            continue
        }

        FOUR_DIRECTION
            .mapNotNull {
                val newPos = pos + it
                val totalDist = dist + 1 + if (dir == it) 0 else 1000

                if (map[newPos.y][newPos.x] == '#') return@mapNotNull null
                if (newPos in visited) return@mapNotNull null
                if (totalDist > distances.getValue(newPos)) return@mapNotNull null

                distances[newPos] = totalDist
                Info(newPos, it, visited + newPos, totalDist)
            }
            .also(queue::addAll)
    }

    return distances.getValue(end)
        .let { it to bestPath.getValue(end to it) }
}
private fun part01(input: List<String>): Int {
    val (start, end) = input.scanPoints()
    return traverse(start, end, input).first
}

private fun part02(input: List<String>): Int {
    val (start, end) = input.scanPoints()
    return listOf(start to end, end to start)
        .fold(emptySet<Vector2>()) { acc, p -> acc + traverse(p.first, p.second, input).second }
        .size
}

fun main() {
    Runner.get(::part01)
        .test("day16_test.in", 7036)
        .test("day16_test2.in", 11048)
        .run("day16.in")

    Runner.get(::part02)
        .test("day16_test.in", 45)
        .test("day16_test2.in", 64)
        .run("day16.in")
}
