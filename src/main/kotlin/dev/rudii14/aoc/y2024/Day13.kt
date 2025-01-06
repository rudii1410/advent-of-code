package dev.rudii14.aoc.y2024

import dev.rudii14.aoc.util.Runner
import java.math.BigInteger

private data class Vec2(val x: BigInteger, val y: BigInteger)
private data class Action(
    val buttonA: Vec2,
    val buttonB: Vec2,
    val price: Vec2
)
private fun Pair<BigInteger, BigInteger>.toVec2(): Vec2 = Vec2(this.first, this.second)

private fun parseAction(raw: String): Action {
    return raw.split("\n")
        .map { line ->
            line.split(": ")
                .last()
                .split(", ")
                .map { it.removeRange(0..1) }
                .let { it[0].toBigInteger() to it[1].toBigInteger() }
        }
        .let {
            Action(
                buttonA = it[0].toVec2(),
                buttonB = it[1].toVec2(),
                price = it[2].toVec2()
            )
        }
}

private fun calculate(action: Action): BigInteger? {
    val three = 3.toBigInteger()
    val (buttonA, buttonB, price) = action
    return ((buttonA.y * price.x) - (buttonA.x * price.y) to (buttonA.y * buttonB.x) - (buttonA.x * buttonB.y))
        .takeIf { c -> c.first % c.second == BigInteger.ZERO }
        ?.let { c -> c.first / c.second }
        ?.let { y -> (price.x - (buttonB.x * y) to buttonA.x) to y }
        ?.takeIf { xc -> xc.first.first % xc.first.second == BigInteger.ZERO }
        ?.let { xy -> xy.first.first / xy.first.second to xy.second }
        ?.let { a -> (a.first * three) + (a.second) }
}
private fun part01(input: String): BigInteger {
    return input.split("\n\n")
        .map(::parseAction)
        .mapNotNull(::calculate)
        .fold(BigInteger.ZERO) { acc, i -> acc + i }
}

private fun part02(input: String): BigInteger {
    val additional = "10000000000000".toBigInteger()
    return input.split("\n\n")
        .map(::parseAction)
        .map {
            it.copy(price = Vec2(it.price.x + additional, it.price.y + additional))
        }
        .mapNotNull(::calculate)
        .fold(BigInteger.ZERO) { acc, i -> acc + i }
}

fun main() {
    Runner.get(::part01)
        .test("day13_test.in", 480.toBigInteger())
        .run("day13.in")

    Runner.get(::part02)
        .run("day13.in")
}
