package year_2022

import util.Runner

fun main() {
    /* Part 1 */
    fun checkSignalStr(cycle: Int, x: Int): Int {
        return if ((cycle - 20) % 40 == 0) {
            x * cycle
        } else 0
    }

    fun part1(input: List<String>): Int {
        var cycle = 0
        var x = 1

        return input.sumOf {
            if (it == "noop") checkSignalStr(++cycle, x)
            else {
                checkSignalStr(++cycle, x) + it.split(" ")[1].toInt().let { num ->
                    x += num
                    checkSignalStr(++cycle, x)
                }
            }
        }
    }
    Runner.run(::part1, 13360)


    /* Part 2 */
    fun getPixelState(c: Int, x: Int): String {
        return if (c % 40 in x - 1..x + 1) "#" else "."
    }
    fun part2(input: List<String>) {
        var cycle = 0
        var x = 1

        input.joinToString("") {
            if (it == "noop") getPixelState(cycle++, x)
            else {
                getPixelState(cycle++, x) + getPixelState(cycle++, x).let { str ->
                    x += it.split(" ")[1].toInt()
                    str
                }
            }
        }.chunked(40).forEach {
            println(it)
        }
    }
    Runner.run(::part2)
}
