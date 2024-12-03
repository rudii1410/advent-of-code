package problem

import util.Runner
import util.TestSuites

private fun String.evaluateMul(): Int {
    return drop(4)
        .dropLast(1)
        .split(",")
        .let { p -> p[0].toInt() * p[1].toInt() }
}

@TestSuites(
    testInputFiles = ["day3_test1.in"],
    testExpectedOutput = ["161"],
    problemFiles = "day3.in"
)
private fun part01(input: String): String {
    val regex = "mul\\(\\d+,\\d+\\)".toRegex()
    return regex.findAll(input)
        .map {
            it.groupValues
                .first()
                .evaluateMul()
        }
        .toList()
        .sum()
        .toString()
}

@TestSuites(
    testInputFiles = ["day3_test1.in"],
    testExpectedOutput = ["48"],
    problemFiles = "day3.in"
)
private fun part02(input: String): String {
    val regex = "mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)".toRegex()
    return regex.findAll(input)
        .map { it.groupValues.first() }
        .fold(0 to true) { acc: Pair<Int, Boolean>, s: String ->
            when {
                s.startsWith("don't") -> acc.first to false
                s.startsWith("do") -> acc.first to true
                s.startsWith("mul") && acc.second -> {
                    s.evaluateMul()
                        .let { acc.first + it to true }
                }
                else -> acc
            }
        }.first.toString()
}

fun main() {
    Runner.run(::part01, ::part02)
}