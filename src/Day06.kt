import util.runner

fun main() {
    fun findFirstMarker(str: String, size: Int): Int {
        return str.toList().windowed(size).indexOfFirst { it.toSet().size == size } + size
    }

    /* Part 1 */
    fun part1(input: List<String>): Int {
        return findFirstMarker(input[0], 4)
    }
    runner(7, ::part1)


    /* Part 2 */
    fun part2(input: List<String>): Int {
        return findFirstMarker(input[0], 14)
    }
    runner(19, ::part2)
}
