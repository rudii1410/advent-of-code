package util

import kotlin.math.abs

data class Triangle(
    val x: Pos2,
    val y: Pos2,
    val z: Pos2
) {
    fun area(a: Pos2, b: Pos2, c: Pos2 ): Double {
        return abs((a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y)) / 2.0)
    }

    fun isInside(pos: Pos2): Boolean {
        return area(x, y, z) == area(pos, y, z) + area(x, pos, z) + area(x, y, pos)
    }

    fun isInside(x: Int, y: Int): Boolean = isInside(Pos2(x, y))
}

fun main() {
    println(Triangle(Pos2(-1, -1), Pos2(-3, -5), Pos2(-5, -1)).isInside(-1, 1))
}
