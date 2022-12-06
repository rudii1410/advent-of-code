import util.runner

fun main() {
    fun findFirstMarker(str: String, size: Int): Int {
        var result = -1
        for (i in (size - 1) until str.length - 1) {
            str.substring(i - (size - 1), i + 1).toList().distinct().takeIf { it.size == size }?.let { result = i + 1 }
            if (result > 0) break
        }
        return result
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
