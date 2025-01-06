package dev.rudii14.aoc.y2022

import dev.rudii14.aoc.util.Runner

private fun Int.inRange(range: List<Int>): Boolean = this in range[0]..range[1]
private fun List<Int>.withinRange(other: List<Int>): Boolean = get(0).inRange(other) && get(1).inRange(other)
private fun List<Int>.isOverlapping(other: List<Int>): Boolean =
    get(0).inRange(other) || get(1).inRange(other) || withinRange(other)
private fun String.split(): List<List<Int>> = this.split(",")
    .map { s ->
        s.split("-").map { it.toInt() }
    }

private fun part1(input: List<String>): Int {
    return input.map { p ->
        p.split().let {
            return@map if (it[0].withinRange(it[1]) || it[1].withinRange(it[0])) 1 else 0
        }
    }.sum()
}

private fun part2(input: List<String>): Int {
    return input.map { p ->
        p.split().let {
            return@map if (it[0].isOverlapping(it[1]) || it[1].isOverlapping(it[0])) 1 else 0
        }
    }.sum()
}

fun main() {
    Runner.get(::part1)
        .test("day04_test.in", 2)
        .run("day04.in")

    Runner.get(::part2)
        .test("day04_test.in", 4)
        .run("day04.in")
}
