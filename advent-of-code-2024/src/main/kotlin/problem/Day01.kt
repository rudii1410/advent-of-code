package problem

import util.Runner
import util.TestSuites
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

@TestSuites(
    testInputFiles = ["day1_test.in"],
    testExpectedOutput = ["11"],
    problemFiles = "day1.in"
)
private fun part01(input: List<String>): String {
    return input.prepareData()
        .let { it.first.sorted() to it.second.sorted() }
        .let { it.first.mapIndexed { index, s -> abs(s - it.second[index]) } }
        .sum()
        .toString()
}

@TestSuites(
    testInputFiles = ["day1_test.in"],
    testExpectedOutput = ["31"],
    "day1.in"
)
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
    Runner.run(::part01, ::part02)
}