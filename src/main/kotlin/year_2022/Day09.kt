package year_2022

import util.Runner

fun main() {
    fun Pair<Int, Int>.stillAround(other: Pair<Int, Int>): Boolean {
        return (other.first >= this.first - 1 && other.first <= this.first + 1) &&
                (other.second >= this.second - 1 && other.second <= this.second + 1)
    }
    fun Pair<Int, Int>.moveCloser(other: Pair<Int, Int>): Pair<Int, Int> {
        return copy(
            first = first + if (other.first > first) 1 else if (other.first < first) -1 else 0,
            second = second + if (other.second > second) 1 else if (other.second < second) -1 else 0
        )
    }

    val hMove = mapOf("L" to -1, "R" to 1)
    val vMove = mapOf("U" to -1, "D" to 1)

    /* Part 1 */
    fun part1(input: List<String>): Int {
        val visited = mutableMapOf<Pair<Int, Int>, Boolean>()
        var headPos = Pair(0, 0)
        var tailPos = headPos
        var result = 1

        input.forEach {
            val row = it.split(" ")
            repeat(row[1].toInt()) {
                headPos = headPos.copy(
                    first = headPos.first + hMove.getOrDefault(row[0], 0),
                    second = headPos.second + vMove.getOrDefault(row[0], 0)
                )
                if (tailPos.stillAround(headPos)) return@repeat

                tailPos = tailPos.moveCloser(headPos)
                if (!visited.getOrDefault(tailPos, false)) {
                    result += 1
                    visited[tailPos] = true
                }
            }
        }

        return result
    }
    Runner.run(::part1, 88, 13)


    /* Part 2 */
    fun part2(input: List<String>): Int {
        val visited = mutableMapOf<Pair<Int, Int>, Boolean>()
        var headPos = Pair(0, 0)
        val rope = MutableList(9) { Pair(0, 0) }
        var result = 0

        input.forEach {
            val row = it.split(" ")
            repeat(row[1].toInt()) {
                headPos = headPos.copy(
                    first = headPos.first + hMove.getOrDefault(row[0], 0),
                    second = headPos.second + vMove.getOrDefault(row[0], 0)
                )

                var tmp = headPos
                for (i in 0 until rope.size) {
                    if (rope[i].stillAround(tmp)) break
                    rope[i] = rope[i].moveCloser(tmp)
                    tmp = rope[i]
                }

                if (!visited.getOrDefault(rope[8], false)) {
                    result += 1
                    visited[rope[8]] = true
                }
            }
        }
        return result
    }
    Runner.run(::part2, 36)
}
