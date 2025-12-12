package dev.rudii14.aoc.y2025

import dev.rudii14.aoc.util.Runner
import dev.rudii14.aoc.util.sum
import java.math.BigInteger

private fun part01(input: List<String>): BigInteger {
    return calculate(input, 2)
}

private fun part02(input: List<String>): BigInteger {
    return calculate(input, 12)
}

private fun calculate(input: List<String>, size: Int): BigInteger {
    return input.map { row ->
        val stacks = mutableListOf<Char>()
        var removedCounter = row.length - size
        for (c in row) {
            if (stacks.size > size && removedCounter == 0) break

            while (removedCounter > 0 && stacks.isNotEmpty() && stacks.last() < c) {
                stacks.removeLast()
                removedCounter--
            }
            stacks.add(c)
        }
        stacks.take(size)
            .joinToString(separator = "")
            .let { BigInteger(it) }
    }.sum()
}

fun main() {
    Runner.get(::part01)
        .test("day03_test.in", BigInteger("357"))
        .run("day03.in")

    Runner.get(::part02)
        .test("day03_test.in", BigInteger("3121910778619"))
        .run("day03.in")
}