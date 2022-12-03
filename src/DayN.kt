import util.Config
import util.runner

fun main() {
    /* Part 1 */
    fun part1(input: List<String>): Int {
        return input.size
    }
    runner(Config(1, 0), ::part1)


    /* Part 2 */
    fun part2(input: List<String>): Int {
        return input.size
    }
    runner(Config(2, 0), ::part2)
}
