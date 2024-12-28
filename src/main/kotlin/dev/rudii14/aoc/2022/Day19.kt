package year_2022

import util.Runner

fun main() {
    val size = 100

    /* Part 1 */
    /**
     * ore = 0, clay = 1, obsidian = 2, geode = 3
     */
    fun parseRecipe(s: String): List<Int> {
        fun List<String>.parse(idx: Int, stone: String): Int {
            return this.getOrNull(idx)?.trim()?.split(" ")?.let { s ->
                s[0].takeIf { s[1] == stone } ?: "0"
            }?.toInt() ?: 0
        }
        return s.substringAfter("costs").split("and").let { r ->
            listOf(r.parse(0, "ore"), r.parse(1, "clay"), r.parse(2, "obsidian"), r.parse(3, "geode"))
        }
    }
    fun part1(input: List<String>): Int {
        val config = List(size) { List(size) { List(size) { MutableList(size) { 0 } } } }
        input.map { row ->
            val d = row.split(": ", ".")
            println(parseRecipe(d[1]))
            println(parseRecipe(d[2]))
            println(parseRecipe(d[3]))
            println(parseRecipe(d[4]))
            println()
        }
        return input.size
    }
    Runner.run(::part1, 33)


    /* Part 2 */
    fun part2(input: List<String>): Int {
        return input.size
    }
    Runner.run(::part2, 0)
}
