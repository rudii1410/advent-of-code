package problem

import util.Runner

private fun List<String>.prepareData(): List<Int> {
    return windowed(2)
        .map { it.first().toInt() - it.last().toInt() }
}

private fun isSafe(list: List<Int>): Boolean {
    return list.all { it in -3..-1 } || list.all { it in 1..3 }
}

private fun part01(input: List<String>): Int {
    return input.count { line ->
        line.split(" ")
            .prepareData()
            .let(::isSafe)
    }
}

private fun part02(input: List<String>): Int {
    return input.count { line ->
        val raw = line.split(" ")

        raw.prepareData()
            .let(::isSafe)
            .let { if (it) return@count true }

        for (i in raw.indices) {
            raw.filterIndexed { idx, _ -> i != idx }
                .prepareData()
                .let(::isSafe)
                .let { if (it) return@count true }
        }

        false
    }
}

fun main() {
    Runner.get(::part01)
        .test("day2_test.in", 2)
        .run("day2.in")

    Runner.get(::part02)
        .test("day2_test.in", 4)
        .test("day2_test_2.in", 10)
        .run("day2.in")
}