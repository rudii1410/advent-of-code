//TODO
//package dev.rudii14.aoc.y2022
//
//import util.Pos2
//import dev.rudii14.aoc.util.Runner
//import util.wrap
//
//fun main() {
//    fun drawStone(map: List<MutableList<Char>>, h: Pos2, v: Pos2) {
//        for (i in h.x .. h.y) {
//            for (j in v.x .. v.y) map[i][j] = '#'
//        }
//    }
//
//    fun scanAndNormalizeMap(
//        input: List<String>
//    ): Triple<List<MutableList<Char>>, Pos2, Pos2> {
//        val map: List<MutableList<Char>>
//        var rangeV = Pos2(Int.MAX_VALUE, Int.MIN_VALUE)
//        var rangeH = Pos2(Int.MAX_VALUE, Int.MIN_VALUE)
//
//        fun getBump(): Int = (rangeV.y + 2) - (500 - rangeH.x)
//
//        input.map { row ->
//            row.split(" -> ").windowed(2).map {
//                val start = it[0].split(",").map { s -> s.toInt() }
//                val end = it[1].split(",").map { e -> e.toInt() }
//
//                wrap(
//                    listOf(start[0], end[0]).sorted().let { x -> Pos2(x[0], x[1]) },
//                    listOf(start[1], end[1]).sorted().let { x -> Pos2(x[0], x[1]) }
//                ) { hVal, vVal ->
//                    rangeV = Pos2(rangeV.x.coerceAtMost(vVal.x), rangeV.y.coerceAtLeast(vVal.y))
//                    rangeH = Pos2(rangeH.x.coerceAtMost(hVal.x), rangeH.y.coerceAtLeast(hVal.y))
//                    listOf(vVal, hVal)
//                }
//            }
//        }.apply {
//            val newVRange = rangeV.y + 2
//            map = List(1 + newVRange) {
//                MutableList(1 + (2 * (newVRange))) { '.' }
//            }
//            drawStone(map, Pos2(newVRange, newVRange), Pos2(0, (2 * newVRange)))
//        }.map { row ->
//            row.map { hv ->
//                wrap(getBump(), rangeH.x) { a, b ->
//                    listOf(hv[0], Pos2(a + hv[1].x - b, a + hv[1].y - b))
//                }
//            }
//        }.forEach { row ->
//            row.forEach { hv ->
//                drawStone(map, hv[0], hv[1])
//            }
//        }
//        return Triple(map, rangeV, Pos2(getBump(), getBump() + (rangeH.y - rangeH.x)))
//    }
//
//    fun printMap(map: List<MutableList<Char>>, it: Int, sandDrop: Int? = null) {
//        println("iteration : $it")
//        sandDrop?.let { map[0][it] = '+' }
//        map.forEach { r ->
//            r.forEach { print(if (it == '#') '█' else if (it == '.') ' ' else it) }
//            println()
//        }
//        println()
//    }
//
//    /* Part 1 */
//    fun part1(input: List<String>): Int {
//        var result = 0
//        val (map, vRange, hRange) = scanAndNormalizeMap(input)
//
//        val start = Pos2(0, 2 + vRange.y)
//        var sandPos = start
//        while (true) {
//            val bot = sandPos.getBottom()
//            if (bot.x > vRange.y) break
//            sandPos = if (map[bot.x][bot.y] == '.') {
//                bot
//            } else {
//                val left = bot.getLeft()
//                val right = bot.getRight()
//
//                if (left.y < hRange.x || right.y >= hRange.y) break
//
//                if (map[left.x][left.y] == '.') left
//                else if (map[right.x][right.y] == '.') right
//                else {
//                    result++
//                    map[sandPos.x][sandPos.y] = 'o'
//                    start
//                }
//            }
//        }
//        return result
//    }
////    Runner.run(::part1, 24)
//
//
//    /* Part 2 */
//    fun part2(input: List<String>): Int {
//        var result = 0
//        val (map, vRange, _) = scanAndNormalizeMap(input)
//
//        val start = Pos2(0, 2 + vRange.y)
//        var sandPos = start
//        while (true) {
//            val bot = sandPos.getBottom()
//            sandPos = if (map[bot.x][bot.y] == '.') {
//                bot
//            } else {
//                val left = bot.getLeft()
//                val right = bot.getRight()
//
//                if (map[left.x][left.y] == '.') left
//                else if (map[right.x][right.y] == '.') right
//                else {
//                    if (map[sandPos.x][sandPos.y] == 'o') break
//                    result++
//                    map[sandPos.x][sandPos.y] = 'o'
//                    start
//                }
//            }
//        }
//
//        return result
//    }
////    Runner.run(::part2, 93)
//}
