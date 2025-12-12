package dev.rudii14.aoc.y2025

import dev.rudii14.aoc.util.Runner
import kotlin.math.abs

private fun String.parseData(): Pair<Int, Int> {
    return first().let { if (it == 'L') -1 else 1 } to drop(1).toInt()
}

private fun Int.normalizePosition(): Int {
    return if (this > 99) {
        this % 100
    } else if (this < 0) {
        this + 100
    } else {
        this
    }
}

private fun part01(input: List<String>): Int {
    val result = input.fold(50 to 0) { acc, next ->
        val (direction, value) = next.parseData()

        val position = acc.first + (direction * (value % 100))
        val nRotations = acc.second + if (position.normalizePosition() == 0) 1 else 0

        position.normalizePosition() to nRotations
    }
    return result.second
}

private fun part02(input: List<String>): Int {
    val result = input.fold(50 to 0) { acc, next ->
        val (direction, value) = next.parseData()

        val position = acc.first + (direction * (value % 100))
        val nRotations = (acc.second + abs(value / 100))
            .let { it + if (position == 0) 1 else 0 }
            .let { it + if (position !in (0..99) && acc.first != 0) 1 else 0 }

        position.normalizePosition() to nRotations
    }
    return result.second
}

fun main() {
    Runner.get(::part01)
        .test("day01_test.in", 3)
        .run("day01.in")

    Runner.get(::part02)
        .test("day01_test.in", 6)
        .run("day01.in")
}