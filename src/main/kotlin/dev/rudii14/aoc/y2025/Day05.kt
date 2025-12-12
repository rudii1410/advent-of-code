package dev.rudii14.aoc.y2025

import dev.rudii14.aoc.util.Runner
import dev.rudii14.aoc.util.sum
import java.math.BigInteger

data class Range(val from: BigInteger, val to: BigInteger) : Comparable<Range> {
    override fun compareTo(other: Range): Int {
        return from compareTo other.from
    }

    fun isOverlap(other: Range): Boolean {
        return maxOf(from, other.from) <= minOf(to, other.to)
    }

    fun mergeOverlap(other: Range): Range {
        if (!isOverlap(other)) return this

        return Range(from = minOf(from, other.from), to = maxOf(to, other.to))
    }
}

private fun List<String>.processInput(): Pair<List<Range>, List<BigInteger>> {
    var passedDivider = false
    val ranges = mutableListOf<Range>()
    val ids = mutableListOf<BigInteger>()

    for (i in indices) {
        if (this[i] == "") {
            passedDivider = true
            continue
        }

        if (!passedDivider) {
            this[i].split("-")
                .map { BigInteger(it) }
                .let { ranges.add(Range(it[0], it[1])) }
        } else {
            ids.add(BigInteger(this[i]))
        }
    }

    return ranges.toList().sortedBy { it.from } to ids
}

private fun part01(input: List<String>): Int {
    val (bst, ids) = input.processInput()

    return ids.map { id ->
        if (bst.find { id in (it.from..it.to) } != null) 1 else 0
    }.sum()
}

private fun part02(input: List<String>): BigInteger {
    val (bst, _) = input.processInput()
    val newList = mutableListOf<Range>()
    bst.forEachIndexed { i, bst1 ->
        if (i == 0) {
            newList.add(bst1)
            return@forEachIndexed
        }

        val lastIdx = newList.lastIndex
        if (bst1.isOverlap(newList[lastIdx])) {
            newList[lastIdx] = newList[lastIdx].mergeOverlap(bst1)
        } else {
            newList.add(bst1)
        }
    }

    return newList.map { BigInteger.ONE + (it.to - it.from) }.sum()
}

fun main() {
    Runner.get(::part01)
        .test("day05_test.in", 3)
        .run("day05.in")

    Runner.get(::part02)
        .test("day05_test.in", BigInteger.valueOf(14))
        .run("day05.in")
}