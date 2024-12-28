package problem.unfinished

import util.Runner
import kotlin.math.pow

private fun parseRegister(input: String): List<Int> {
    return input.split("\n")
        .map {
            it.split(": ")
                .last()
                .toInt()
        }
}
private fun List<Int>.comboOperand(value: Int): Int {
    return when(value) {
        in 0..3 -> value
        else -> this[value - 4]
    }
}
private interface Operations {
    fun calculate(register: MutableList<Int>, input: Int, prints: List<Int>): Int
}
private val adv = object : Operations {
    override fun calculate(register: MutableList<Int>, input: Int, prints: List<Int>): Int {
        val denom = (2).toDouble()
            .pow(register.comboOperand(input))
            .toInt()

        return register[0] / denom
    }
}
private val bxl = object : Operations {
    override fun calculate(register: MutableList<Int>, input: Int, prints: List<Int>): Int {
        return (register[1] xor input)
            .also { register[1] = it }
    }
}
private val bst = object : Operations {
    override fun calculate(register: MutableList<Int>, input: Int, prints: List<Int>): Int {
        return (input % 8)
            .also { register[1] = it }
    }
}
//private val jnz = object : Operations {
//    override fun calculate(register: MutableList<Int>, input: Int, prints: List<Int>): Int {
//        if (register[0] == 0) return input
//
//    }
//}

private fun parseProgram(input: String): List<Int> {
    return input.split(": ")
        .last()
        .split(",")
        .map { it.toInt() }
}
private fun part01(input: String): String {
    val (register, program) = input
        .split("\n\n")
        .let { parseRegister(it.first()) to parseProgram(it.last()) }


    return ""
}

private fun part02(input: List<String>): Int {
    return 0
}

fun main() {
    Runner.get(::part01)
        .test("day17_test.in", "4,6,3,5,6,3,5,2,1,0")
        .run("day17.in")

    Runner.get(::part02)
        .test("day17_test.in", 0)
        .run("day17.in")
}
