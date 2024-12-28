package problem

import util.Runner

private fun String.eval(): Int {
    return drop(4)
        .dropLast(1)
        .split(",")
        .let { p -> p[0].toInt() * p[1].toInt() }
}

private fun part01(input: String): Int {
    return "mul\\(\\d+,\\d+\\)".toRegex()
        .findAll(input)
        .fold(0) { acc, s -> acc + s.value.eval() }
}

private fun part02(input: String): Int {
    return "mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)".toRegex()
        .findAll(input)
        .fold(0 to true) { acc, s ->
            when(s.value) {
                "don't()" -> acc.copy(second = false)
                "do()" -> acc.copy(second = true)
                else -> {
                    if (!acc.second) return@fold acc
                    (acc.first + s.value.eval()) to true
                }
            }
        }
        .first
}

fun main() {
    Runner.get(::part01)
        .test("day3_test1.in", 161)
        .run("day3.in")

    Runner.get(::part02)
        .test("day3_test1.in", 48)
        .run("day3.in")
}
