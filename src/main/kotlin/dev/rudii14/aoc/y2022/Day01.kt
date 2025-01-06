package dev.rudii14.aoc.y2022

import dev.rudii14.aoc.util.Runner

private fun String.calculate(n: Int): Int {
    return split("\n\n")
        .map { cal ->
            cal.split("\n")
                .sumOf { it.toInt() }
        }
        .sorted()
        .takeLast(n)
        .sum()
}

private fun part1(input: String): Int {
    return input.calculate(1)
}

private fun part2(input: String): Int {
    return input.calculate(3)
}


fun main() {
    Runner.get(::part1)
        .test("day01_test.in", 24000)
        .run("day01.in")

    Runner.get(::part2)
        .test("day01_test.in", 45000)
        .run("day01.in")
}
