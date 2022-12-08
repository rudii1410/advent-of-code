package year_2022

import util.Runner

fun main() {
    fun Int.inRange(range: List<Int>): Boolean = this in range[0]..range[1]
    fun List<Int>.withinRange(other: List<Int>): Boolean = get(0).inRange(other) && get(1).inRange(other)
    fun List<Int>.isOverlapping(other: List<Int>): Boolean =
        get(0).inRange(other) || get(1).inRange(other) || withinRange(other)
    fun String.split(): List<List<Int>> = this.split(",").map { s ->
        s.split("-").map { it.toInt() }
    }

    /* Part 1 */
    fun part1(input: List<String>): Int {
        return input.map { p ->
            p.split().let {
                return@map if (it[0].withinRange(it[1]) || it[1].withinRange(it[0])) 1 else 0
            }
        }.sum()
    }
    Runner.run(2, ::part1)


    /* Part 2 */
    fun part2(input: List<String>): Int {
        return input.map { p ->
            p.split().let {
                return@map if (it[0].isOverlapping(it[1]) || it[1].isOverlapping(it[0])) 1 else 0
            }
        }.sum()
    }
    Runner.run(4, ::part2)
}
