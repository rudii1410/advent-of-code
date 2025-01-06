package dev.rudii14.aoc.y2022

import dev.rudii14.aoc.util.Runner

private fun getCharScore(ch: Char) = if (ch.isUpperCase()) (ch - 'A' + 1) + 26 else (ch - 'a' + 1)

private fun Map<Char, Int>.calculate(minimum: Int): Int {
    return this.map { if (it.value == minimum) getCharScore(it.key) else 0 }.sum()
}

private fun part1(input: List<String>): Int {
    return input.fold(0) { acc, i ->
        i.chunked(i.length / 2)
            .map { rs -> rs.toList().distinct() }
            .flatten()
            .groupingBy { it }
            .eachCount()
            .calculate(2)
            .let { acc + it }
    }
}

private fun part2(input: List<String>): Int {
    return input.chunked(3)
        .fold(0) { acc, group ->
            group.map { it.toList().distinct() }
                .flatten()
                .groupingBy { it }
                .eachCount()
                .calculate(3)
                .let { acc + it }
        }
}

fun main() {
    Runner.get(::part1)
        .test("day03_test.in", 157)
        .run("day03.in")

    Runner.get(::part2)
        .test("day03_test.in", 70)
        .run("day03.in")
}
