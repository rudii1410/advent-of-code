package problem

import util.Runner
import java.math.BigInteger

private fun calculate(stone: String, idx: Int, size: Int, memo: MutableMap<Pair<String, Int>, BigInteger>): BigInteger {
    memo[stone to idx]?.let { return it }

    val changes = if (stone == "0") {
        listOf("1")
    } else if (stone.length % 2 == 0) {
        stone
            .chunked(stone.length / 2)
            .let { (l, r) ->
                r.indexOfFirst { it != '0' }
                    .let {
                        if (it < 0) "0"
                        else r.substring(it)
                    }
                    .let { listOf(l, it) }
            }
    } else {
        listOf("${(stone.toLong() * 2024)}")
    }

    val result = if (idx == size) {
        changes.size.toBigInteger()
    } else {
        changes.sumOf { calculate(it, idx + 1, size, memo) }
    }

    return result.also { memo[stone to idx] = it }
}

private fun part01(input: List<String>): BigInteger {
    val memo = mutableMapOf<Pair<String, Int>, BigInteger>()
    return input.first()
        .split(" ")
        .sumOf { calculate(it, 1, 25, memo) }
}

private fun part02(input: List<String>): BigInteger {
    val memo = mutableMapOf<Pair<String, Int>, BigInteger>()
    return input.first()
        .split(" ")
        .sumOf { calculate(it, 1, 75, memo) }
}

fun main() {
    Runner.get(::part01)
        .test("day11_test.in", (55312).toBigInteger())
        .run("day11.in")

    Runner.get(::part02)
        .run("day11.in")
}
