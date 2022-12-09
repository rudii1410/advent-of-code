package year_2022

import util.Runner

fun main() {
    fun findFirstMarker(str: String, size: Int): Int {
        return str.toList().windowed(size).indexOfFirst { it.toSet().size == size } + size
    }

    /* Part 1 */
    fun part1(input: List<String>): Int {
        return findFirstMarker(input[0], 4)
    }
    Runner.run(::part1, 7)


    /* Part 2 */
    fun part2(input: List<String>): Int {
        return findFirstMarker(input[0], 14)
    }
    Runner.run(::part2, 19)
}
