import util.runner

fun main() {
    fun List<Int>.withinRange(other: List<Int>): Boolean = this[0] <= other[0] && this[1] >= other[1]
    fun Int.inRange(range: List<Int>): Boolean = this >= range[0] && this <= range[1]
    fun List<Int>.isOverlapping(other: List<Int>): Boolean =
        this[0].inRange(other) || this[1].inRange(other) || withinRange(other)
    fun String.split(): List<List<Int>> = this.split(",").map { s -> s.split("-").map { it.toInt() } }

    /* Part 1 */
    fun part1(input: List<String>): Any {
        return input.map { p ->
            p.split().let {
                return@map if (it[0].withinRange(it[1]) || it[1].withinRange(it[0])) 1 else 0
            }
        }.sum()
    }
    runner(2, ::part1)


    /* Part 2 */
    fun part2(input: List<String>): Int {
        return input.map { p ->
            p.split().let {
                return@map if (it[0].isOverlapping(it[1]) || it[1].isOverlapping(it[0])) 1 else 0
            }
        }.sum()
    }
    runner(4, ::part2)
}
