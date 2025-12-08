package dev.rudii14.aoc.util

import java.math.BigInteger

fun Collection<BigInteger>.sum(): BigInteger {
    return fold(BigInteger.ZERO) { acc, value -> acc + value }
}
