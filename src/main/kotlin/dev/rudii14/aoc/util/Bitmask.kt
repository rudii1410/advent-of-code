package dev.rudii14.aoc.util

fun Int.turnBitOn(i: Int): Int {
    return this or (1 shl i)
}

fun Int.turnBitOff(i: Int): Int {
    return this and (1 shl i).inv()
}

fun Int.toggleBit(i: Int): Int {
    return this xor (1 shl i)
}

fun Int.isBitOn(i: Int): Boolean {
    return (this and (i shl 1)) != 0
}
