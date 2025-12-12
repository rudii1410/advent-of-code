package dev.rudii14.aoc.y2025

import dev.rudii14.aoc.util.Runner
import dev.rudii14.aoc.util.sum
import java.math.BigInteger

private fun String.processLine(): List<BigInteger> {
    return split(" ")
        .mapNotNull { if (it.isBlank()) null else it.toBigInteger() }
}

private fun calculate(data: List<BigInteger>, operation: Char): BigInteger {
    return when (operation) {
        '+' -> data.sum()
        '*' -> data.fold(BigInteger.ONE) { acc, element -> acc * element }
        else -> throw IllegalArgumentException("Invalid operation: $operation")
    }
}

private fun part01(input: List<String>): BigInteger {
    val numbers = input.dropLast(1)
        .map { it.processLine() }
    val operators = input.last().split(" ").mapNotNull { it.ifBlank { null } }
    val vSize = numbers.size
    val hSize = numbers.first().size
    
    return (0..<hSize)
        .mapIndexed { h, _ ->
            (0..<vSize)
                .map { v -> numbers[v][h] }
                .let { calculate(it, operators[h].first()) }
        }.sum()
}


private fun part02(input: List<String>): BigInteger {
    val vSize = input.size
    val hSize = input.fold(Int.MIN_VALUE) { acc, str -> maxOf(acc, str.length)}

    return ((hSize - 1) downTo 0)
        .mapNotNull { h ->
            (0..<vSize)
                .mapNotNull { v -> if (input[v][h] == ' ') null else input[v][h] }
                .joinToString("")
        }
        .fold(mutableListOf(mutableListOf<String>())) { acc, item ->
            if (item.isBlank()) {
                acc.add(mutableListOf())
            } else {
                acc.last().add(item)
            }
            acc
        }
        .map { p ->
            val last = p.last()
            val numbers = (p.dropLast(1) + last.dropLast(1)).map { BigInteger(it) }
            calculate(numbers, last.last())
        }
        .sum()
}

fun main() {
    Runner.get(::part01)
        .test("day06_test.in", BigInteger("4277556"))
        .run("day06.in")

    Runner.get(::part02)
        .test("day06_test.in", BigInteger("3263827"))
        .run("day06.in")
}