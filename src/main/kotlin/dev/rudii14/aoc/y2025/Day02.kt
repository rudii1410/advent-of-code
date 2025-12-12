package dev.rudii14.aoc.y2025

import dev.rudii14.aoc.util.Runner
import dev.rudii14.aoc.util.sum
import java.math.BigInteger
import kotlin.math.pow

private fun String.parseLine(): Pair<String, String> {
    return this.split("-").let { it.first() to it.last() }
}

private fun partOnePreProcessInput(data: Pair<String, String>): Pair<Long, Long> {
    val newStarting = if (data.first.length % 2 == 1) (10.0).pow(data.first.length).toLong() else data.first.toLong()
    val newEnding = if (data.second.length % 2 == 1) (10.0).pow(data.second.length - 1).toLong() else data.second.toLong()
    return newStarting to newEnding
}

private fun part01(input: String): String {
    return input.split(",")
        .mapNotNull { inp ->
            val (start, end) = inp.parseLine()
                .let(::partOnePreProcessInput)

            return@mapNotNull calculate(start, end, 2)
        }
        .sum()
        .toString()
}

private fun part02(input: String): String {
    return input.split(",")
        .mapNotNull { inp ->
            val (start, end) = inp.parseLine()
            return@mapNotNull calculate(start.toLong(), end.toLong(), end.length)
        }
        .sum()
        .toString()
}

private fun calculate(start: Long, end: Long, maxChunk: Int): BigInteger? {
    if (start == end) return null

    val result = mutableSetOf<BigInteger>()
    for (i in start..end) {
        val tmp = i.toString()
        for (j in 2..maxChunk) {
            if (tmp.length < j) break
            val isRepeated = tmp.chunked(tmp.length / j).toSet().size == 1
            if (isRepeated) {
                result.add(i.toBigInteger())
                break
            }
        }
    }
    return result.sum()
}

fun main() {
    Runner.get(::part01)
        .test("day02_test.in", "1227775554")
        .run("day02.in")

    Runner.get(::part02)
        .test("day02_test.in", "4174379265")
        .run("day02.in")
}
