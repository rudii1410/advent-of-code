import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        var maxCal = -1
        var tmp = 0
        input.forEach {
            if (it.isEmpty()) {
                maxCal = max(maxCal, tmp)
                tmp = 0
            } else {
                tmp += it.toInt()
            }
        }
        return maxCal
    }

    fun part2(input: List<String>): Int {
        val results = mutableListOf<Int>()
        var tmp = 0
        input.forEach {
            if (it.isEmpty()) {
                results.add(tmp)
                tmp = 0
            } else {
                tmp += it.toInt()
            }
        }
        results.add(tmp)
        val top3 = results.apply {
            sort()
        }.takeLast(3).sum()

        return top3
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
