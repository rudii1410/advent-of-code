package problem

import util.Runner
import kotlin.math.abs

private fun List<String>.prepareData(): Pair<List<Int>, List<Int>> {
    return map { line ->
        line.split("   ")
            .let { it[0].toInt() to it[1].toInt() }
    }
        .unzip()
}

private fun <T> List<T>.distinctAndCount(): Map<T, Int> {
    return groupingBy { it }.eachCount()
}

private fun part01(input: List<String>): String {
    return input.prepareData()
        .let { it.first.sorted() to it.second.sorted() }
        .let { it.first.mapIndexed { idx, id -> abs(id - it.second[idx]) } }
        .sum()
        .toString()
}

private fun part02(input: List<String>): String {
    return input.prepareData()
        .let { it.first.distinctAndCount() to it.second.distinctAndCount() }
        .let {
            it.first.map { a ->
                a.key * a.value * it.second.getOrDefault(a.key, 0)
            }
        }
        .sum()
        .toString()
}

fun main() {
    Runner.get(::part01)
        .test("day1_test.in", "11")
        .run("day1.in")

    Runner.get(::part02)
        .test("day1_test.in", "31")
        .run("day1.in")
}
