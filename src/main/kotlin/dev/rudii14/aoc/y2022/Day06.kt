package dev.rudii14.aoc.y2022

import dev.rudii14.aoc.util.Runner

private fun findFirstMarker(str: String, size: Int): Int {
    return str.toList().windowed(size).indexOfFirst { it.toSet().size == size } + size
}

/* Part 1 */
private fun part1(input: List<String>): Int {
    return findFirstMarker(input[0], 4)
}


/* Part 2 */
private fun part2(input: List<String>): Int {
    return findFirstMarker(input[0], 14)
}
fun main() {
    Runner.get(::part1)
        .test("day06_test.in", 7)
        .run("day06.in")

    Runner.get(::part2)
        .test("day06_test.in", 19)
        .run("day06.in")
}
