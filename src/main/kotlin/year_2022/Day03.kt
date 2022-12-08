package year_2022

import util.Runner

fun main() {
    fun getCharScore(ch: Char) = if (ch.isUpperCase()) (ch - 'A' + 1) + 26 else (ch - 'a' + 1)
    fun MutableMap<Char, Int>.addScore(key: Char) {
        this[key] = this.getOrDefault(key, 0) + 1
    }
    fun MutableMap<Char, Int>.calculate(minimum: Int): Int {
        return this.map { if (it.value == minimum) getCharScore(it.key) else 0 }.sum()
    }
    fun String.distinctForEach(cb: (Char) -> Unit) = this.toCharArray().distinct().forEach(cb)

    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { i ->
            val dict = mutableMapOf<Char, Int>()
            i.chunked(i.length / 2).forEach { ruckSack ->
                ruckSack.distinctForEach { dict.addScore(it) }
            }
            result += dict.calculate(2)
        }
        return result
    }
    Runner.run(157, ::part1)

    fun part2(input: List<String>): Int {
        var result = 0
        input.chunked(3).forEach { group ->
            val dict = mutableMapOf<Char, Int>()
            group.forEach { r ->
                r.distinctForEach { dict.addScore(it) }
            }
            result += dict.calculate(3)
        }
        return result
    }
    Runner.run(70, ::part2)
}
