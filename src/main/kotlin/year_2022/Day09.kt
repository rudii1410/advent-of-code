package year_2022

import util.Runner

fun main() {
    fun Pair<Int, Int>.move(move: Pair<Int, Int>): Pair<Int, Int> {
        return copy(first = first + move.first, second = second + move.second)
    }
    fun getDirection(a: Int, b: Int) = if (a > b) 1 else if (a < b) -1 else 0

    val zero = Pair(0, 0)
    val moveMap = mapOf(
        "L" to Pair(-1, 0),
        "R" to Pair(1, 0),
        "U" to Pair(0, -1),
        "D" to Pair(0, 1)
    )

    fun calculate(input: List<String>, len: Int): Int {
        val visited = mutableMapOf<Pair<Int, Int>, Boolean>()
        val rope = MutableList(len) { zero }

        return input.sumOf { str ->
            val row = str.split(" ")
            List(row[1].toInt()) { _ ->
                rope[0] = rope[0].move(moveMap.getOrDefault(row[0], zero))

                for (i in 1 until rope.size) {
                    if (rope[i - 1].first in (rope[i].first - 1)..(rope[i].first + 1) &&
                        rope[i - 1].second in (rope[i].second - 1)..(rope[i].second + 1)) break

                    rope[i] = rope[i].let {
                        it.move(
                            Pair(
                                getDirection(rope[i - 1].first, it.first),
                                getDirection(rope[i - 1].second, it.second)
                            )
                        )
                    }
                }

                if (!visited.getOrDefault(rope[len - 1], false)) {
                    visited[rope[len - 1]] = true
                    1
                } else 0
            }.sum()
        }
    }

    /* Part 1 */
    fun part1(input: List<String>): Int {
        return calculate(input, 2)
    }
    Runner.run(::part1, 88, 13)


    /* Part 2 */
    fun part2(input: List<String>): Int {
        return calculate(input, 10)
    }
    Runner.run(::part2, 36)
}
