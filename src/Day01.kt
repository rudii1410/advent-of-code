import util.Config
import util.runner
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

    runner(Config(1, 24000), ::part1)
    runner(Config(2, 45000), ::part2)
}
