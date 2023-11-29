package year_2022

import util.Pos2
import util.Runner
import util.Triangle
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

fun main() {
    /* Part 1 */
    fun part1(input: List<String>): Int {
        var xRange = Pair(Int.MAX_VALUE, Int.MIN_VALUE)
        val rowPos = 2_000_000

        input.map { r ->
            r.split('=', ',', ':')
                .mapNotNull { it.toIntOrNull() }
                .chunked(2)
                .map { Pos2(it[0], it[1]) }
                .let {
                    val delta = abs(it[0].x - it[1].x) + abs(it[0].y - it[1].y)
                    val y = delta - abs(it[0].y - rowPos)
                    xRange = Pair(
                        min(xRange.first, it[0].x - y),
                        max(xRange.second, it[0].x + y)
                    )
                }
        }
        return xRange.second - xRange.first
    }
    Runner.run(::part1)


    /* Part 2 */
    fun createTriangles(signal: Pos2, size: Int): List<Triangle> {
        return listOf(
            Triangle(Pos2(signal.x - size, signal.y), Pos2(signal.x, signal.y - size), Pos2(signal.x + size, signal.y)),
            Triangle(Pos2(signal.x - size, signal.y), Pos2(signal.x, signal.y + size), Pos2(signal.x + size, signal.y)),
        )
    }
    fun part2(input: List<String>): Int {
        val maxSize = 4_000_000

        val a = input.map { r ->
            r.split('=', ',', ':')
                .mapNotNull { it.toIntOrNull() }
                .chunked(2)
                .map { Pos2(it[0], it[1]) }
                .let {
                    createTriangles(it[0], abs(it[0].x - it[1].x) + abs(it[0].y - it[1].y))
                }
        }

        val size = a.size
        var foundPos: Pos2? = null
        for (x in 1000000 .. maxSize) {
            for (y in 1000000 .. maxSize) {
                var tmp = 0
                val pos = Pos2(x, y)
                for (signal in a) {
                    for (t in signal) {
                        if (!t.isInside(pos)) tmp++
                    }
                }
                if (size * 2 == tmp) {
                    println(pos)
                    foundPos = pos
//                    break
                }
            }
//            if (foundPos != null) break
        }

        println(foundPos)
        return foundPos?.let { (it.x * 4_000_000) + it.y } ?: 0
    }
    Runner.run(::part2, 24)
}
