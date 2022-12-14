package year_2022

import util.Pos2
import util.Runner
import util.wrap
import java.util.LinkedList
import java.util.Queue

fun main() {
    fun <T> List<List<T>>.get(p: Pos2): T = this[p.x][p.y]
    fun <T> List<MutableList<T>>.set(p: Pos2, data: T) {
        this[p.x][p.y] = data
    }

    fun canClimb(tmpCurrentHeight: Char, destHeight: Char): Boolean {
        val currentHeight = if (tmpCurrentHeight == 'E') 'z' else tmpCurrentHeight
        return destHeight == 'S' || (destHeight in currentHeight - 1..currentHeight) || currentHeight <= destHeight
    }

    fun traverse(map: List<List<Char>>, start: Pos2, target: Char): Int {
        val q: Queue<Triple<Pos2, Char, Int>> = LinkedList()
        val vLen = map.size
        val hLen = map.first().size
        val visited = List(vLen) { MutableList(hLen) { false } }

        q.add(Triple(start, ' ', 0))

        while(q.isNotEmpty()) {
            val (pos, lastChar, score) = q.remove()

            if (pos.x !in 0 until vLen || pos.y !in 0 until hLen) continue
            if (visited.get(pos)) continue
            if (map.get(pos) != 'E' && !canClimb(lastChar, map.get(pos))) continue
            if (map.get(pos) == target) return score

            visited.set(pos, true)

            wrap(map.get(pos), score + 1) { a, b ->
                q.add(Triple(pos.getLeft(), a, b))
                q.add(Triple(pos.getTop(), a, b))
                q.add(Triple(pos.getRight(), a, b))
                q.add(Triple(pos.getBottom(), a, b))
            }
        }

        return -1
    }

    fun prepareMap(input: String):  Pair<List<List<Char>>, Pos2> {
        var end = Pos2.ZERO
        val map = input.split("\n").map {
            it.toList()
        }
        for (i in map.indices) {
            end = Pos2(i, map[i].indexOf('E'))
            if (end.y != -1) break
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
