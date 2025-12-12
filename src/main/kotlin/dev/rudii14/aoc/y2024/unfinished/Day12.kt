package dev.rudii14.aoc.y2024.unfinished

import dev.rudii14.aoc.util.Runner

private fun part01(input: List<String>): Int {
    return 0
}

private fun part02(input: List<String>): Int {
    return 0
}

fun main() {
    Runner.get(::part01)
        .test("day12_test.in", 140)
        .test("day12_test2.in", 772)
        .test("day12_test3.in", 1930)
        .run("day12.in")

    Runner.get(::part02)
        .test("day12_test.in", 0)
        .run("day12.in")
}
