package year_2022

import util.Pos
import util.Runner
import util.wrap
import java.util.LinkedList
import java.util.Queue

fun main() {
    fun <T> List<List<T>>.get(p: Pos): T = this[p.v][p.h]
    fun <T> List<MutableList<T>>.set(p: Pos, data: T) {
        this[p.v][p.h] = data
    }

    fun canClimb(tmpCurrentHeight: Char, destHeight: Char): Boolean {
        val currentHeight = if (tmpCurrentHeight == 'E') 'z' else tmpCurrentHeight
        return destHeight == 'S' || (destHeight in currentHeight - 1..currentHeight) || currentHeight <= destHeight
    }

    fun traverse(map: List<List<Char>>, start: Pos, target: Char): Int {
        val q: Queue<Triple<Pos, Char, Int>> = LinkedList()
        val vLen = map.size
        val hLen = map.first().size
        val visited = List(vLen) { MutableList(hLen) { false } }

        q.add(Triple(start, ' ', 0))

        while(q.isNotEmpty()) {
            val (pos, lastChar, score) = q.remove()

            if (pos.v !in 0 until vLen || pos.h !in 0 until hLen) continue
            if (visited.get(pos)) continue
            if (map.get(pos) != 'E' && !canClimb(lastChar, map.get(pos))) continue
            if (map.get(pos) == target) return score

            visited.set(pos, true)

            wrap(map.get(pos), score + 1) { a, b ->
                q.add(Triple(pos.left(), a, b))
                q.add(Triple(pos.top(), a, b))
                q.add(Triple(pos.right(), a, b))
                q.add(Triple(pos.bottom(), a, b))
            }
        }

        return -1
    }

    fun prepareMap(input: String):  Pair<List<List<Char>>, Pos> {
        var end = Pos.ZERO
        val map = input.split("\n").map {
            it.toList()
        }
        for (i in map.indices) {
            end = Pos(i, map[i].indexOf('E'))
            if (end.h != -1) break
        }

        return Pair(map, end)
    }

    /* Part 1 */
    fun part1(input: String): Int {
        val (map, end) = prepareMap(input)
        return traverse(map, end, 'S')
    }
    Runner.runAll(::part1, 31)


    /* Part 2 */
    fun part2(input: String): Int {
        val (map, end) = prepareMap(input)
        return traverse(map, end, 'a')
    }
    Runner.runAll(::part2, 29)
}
