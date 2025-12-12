package dev.rudii14.aoc.y2025

import dev.rudii14.aoc.util.Runner
import dev.rudii14.aoc.util.data.Point2
import java.util.PriorityQueue

private data class Tree(
    val width: Int,
    val height: Int,
    val area: String,
    val presentConfig: List<Int>
)
private data class Data(val shape: List<List<Point2>>, val trees: List<Tree>) 

private fun List<String>.convertShapeToPoints(): List<Point2> {
    return mapIndexedNotNull { y, line ->
        line.mapIndexedNotNull { x, c ->
            if (c == '#') Point2(x, y)
            else null
        }
    }.flatten()
}

private fun List<Point2>.rotate(direction: Int): List<Point2> {
    // -1: left, 1: right
    val center = Point2(1, 1)
    return map { p ->
        Point2(
            x = center.x - (direction * (p.y - center.x)),
            y = center.y + (direction * (p.x - center.y))
        )
    }
}

private fun String.convertRawArea(): String {
    val (width, height) = this.split("x").map { it.toInt() }
    return ".".repeat(width * height)
}

private fun String.parseInput(): Data {
    val sections = split("\n\n")
    val presents = mutableListOf<List<Point2>>()
    val trees = mutableListOf<Tree>()
    
    sections.forEach { s ->
        val lines = s.split("\n")
        if (lines.first().matches("\\d:".toRegex())) {
            lines.subList(1, lines.size)
                .convertShapeToPoints()
                .also { presents.add(it) }
        } else {
            lines.map {  l ->
                val s = l.split(": ")
                val config = s[1].split(" ")
                    .mapIndexed { idx, v -> List(v.toInt()) { idx } }
                    .flatten()

                val (width, height) = s.first().split("x").map { it.toInt() }
                val tree = Tree(
                    width = width,
                    height = height,
                    area = ".".repeat(width * height),
                    config
                )
                trees.add(tree)
            }
        }
    }
    
    return Data(presents, trees)
}

private fun List<Point2>.print() {
    for (y in 0 until 3) {
        for (x in 0 until 3) {
            if (contains(Point2(x, y))) print("#")
            else print(".")
        }
        println()
    }
}

private fun String.areaPrint(width: Int) {
    this.chunked(width).forEach { chunk -> println(chunk) }
}

private fun String.flipOn(idx: Int): String {
    return substring(0, idx) + "#" + substring(idx + 1)
}

private fun String.isOn(idx: Int): Boolean = this[idx] == '#'

private fun String.fitPresent(shape: List<Point2>, start: Int, width: Int, height: Int): String? {
    var newArea = this
    val startP = Point2(start / height, start % height)
    for (s in shape) {
        val p = startP + s
        if (p.x >= width || p.y >= height) return null
        val idx = (width * p.y) + (p.x)
        if (idx >= length) return null
        if (newArea.isOn(idx)) return null
        newArea = newArea.flipOn(idx)
    }
    return newArea
}

private fun List<Point2>.getAllPossibleRotation(): List<List<Point2>> {
    val rotateOne = rotate(1)
    return listOf(
        rotate(-1),
        this,
        rotateOne,
        rotateOne.rotate(1)
    )
}

private fun getKey(area: String, presentIdx: Int, startIdx: Int): Pair<String, Pair<Int, Int>> {
    return area to (startIdx to presentIdx)
}

private fun String.nextEmptySpot(): Int? {
    for (i in indices) {
        if (!isOn(i)) return i
    }
    return null
}
private fun getRemainingLength(shapes: List<List<Point2>>, presents: List<Int>): Int {
    return presents.sumOf { shapes[it].size }
}

private fun solve(
    memo: MutableMap<Pair<String, Pair<Int, Int>>, String?>,
    present: Int,
    startIdx: Int,
    currentPresents: List<Int>,
    space: String,
    freeSpace: Int,
    width: Int,
    height: Int,
    shape: List<List<Point2>>,
    depth: Int,
): String? {
    if (startIdx >= space.length - 9) return null
    if (space.isOn(startIdx)) return null
    if (getRemainingLength(shape, currentPresents + present) > freeSpace) return null
    val key = getKey(space, present, startIdx)
    if (memo.containsKey(key)) return memo.getValue(key)
    
    var result: String?
    // First loop, check all possible shape rotations
    for (loop in shape[present].getAllPossibleRotation()) {
        val newArea = space.fitPresent(loop, startIdx, width, height)

        if (newArea == null) {
            // Current rotation didnt work, we try:
            //   1. shifting index to the right.
            for (idx in startIdx + 1 until space.length - 9) {
                result = solve(memo, present, idx, currentPresents, space, freeSpace, width, height, shape, depth + 1)
                    .also { memo[getKey(space, present, idx)] = it }
                
                if (result != null) return result
            }
            
            // 2. if above didnt work, we try other present, same index
            for (p in currentPresents) {
                if (p == present) continue
                val newPresents = (currentPresents - p) + present
                result = solve(memo, p, startIdx, newPresents, space, freeSpace, width, height, shape, depth + 1)
                    .also { memo[getKey(space, p, startIdx)] = it }

                if (result != null) return result
            }
        } else {
            println("present left: $currentPresents")
            newArea.areaPrint(width)
            println()
            
            // Current rotation work, so we do:
            //   1. if no present left, that mean we already place all the present. calculation ends here
            if (currentPresents.isEmpty()) return newArea
            
            //   2. if there are present left, we move to next present and shift index to next empty spot
            val remainingSpace = freeSpace - shape[present].size
            for (p in currentPresents) {
                val newPresents = currentPresents - p
                val nextSpot = newArea.nextEmptySpot() ?: return null 
                result = solve(memo, p, nextSpot, newPresents, newArea, remainingSpace, width, height, shape, depth + 1)
                    .also { memo[getKey(newArea, p, nextSpot)] = it }

                if (result != null) return result
            }
        }
    }
    
    return null
}

private data class QQ(
    val present: Int,
    val startIdx: Int,
    val currentPresents: List<Int>,
    val space: String,
    val freeSpace: Int,
)
private fun solve(tree: Tree, shapes: List<List<Point2>>): String? {
    val pqSorting = compareBy<QQ> { it.currentPresents.size }.thenByDescending { it.freeSpace }
    val queue = PriorityQueue(pqSorting)
    val memo = mutableMapOf<Pair<String, Pair<Int, Int>>, Boolean>()

    tree.presentConfig.forEach {
        queue.add(
            QQ(
                it,
                0,
                tree.presentConfig - it,
                tree.area,
                tree.area.length
            )
        )
    }
    while (queue.isNotEmpty()) {
        val q = queue.poll()

        val key = getKey(q.space, q.present, q.startIdx)
        if (memo.getOrDefault(key, false)) continue
        memo[key] = true
        if (q.startIdx + 9 >= q.space.length) continue
        if (q.space.isOn(q.startIdx)) continue
        if (getRemainingLength(shapes, q.currentPresents + q.present) > q.freeSpace) continue
        
        // First loop, check all possible shape rotations
        for (loop in shapes[q.present].getAllPossibleRotation()) {
            val newArea = q.space.fitPresent(loop, q.startIdx, tree.width, tree.height)

            if (newArea == null) {
                // Current rotation didn't work, we try:
                //   1. shifting index to the right.
                for (idx in q.startIdx + 1 until q.space.length - 9) {
                    queue.add(q.copy(startIdx = idx))
                }

                // 2. if above didnt work, we try other present, same index
                for (p in q.currentPresents) {
                    if (p == q.present) continue
                    val newPresents = (q.currentPresents - p) + q.present
                    queue.add(
                        q.copy(present = p, currentPresents = newPresents)
                    )
                }
                
            } else {
//                println("present left: ${q.currentPresents}")
//                newArea.areaPrint(tree.width)
//                println()

                // Current rotation work, so we do:
                //   1. if no present left, that mean we already place all the present. calculation ends here
                if (q.currentPresents.isEmpty()) {
                    newArea.areaPrint(tree.width)
                    println()
                    return newArea
                }

                //   2. if there are present left, we move to next present and shift index to next empty spot
                val remainingSpace = q.freeSpace - shapes[q.present].size
                val nextSpot = newArea.nextEmptySpot() ?: return null
                for (p in q.currentPresents) {
                    val newPresents = q.currentPresents - p
                    queue.add(
                        QQ(
                            p,
                            nextSpot,
                            newPresents,
                            newArea,
                            remainingSpace
                        )
                    )
                }
            }
        }
    }
    
    return null
}

private fun part01(input: String): Int {
    val data = input.parseInput()
    return data.trees.sumOf { t ->
        println("${(t.width / 3) * (t.height / 3)} ${t.presentConfig.size} - ${(t.width / 3) * (t.height / 3) >= t.presentConfig.size}")
        if ((t.width / 3) * (t.height / 3) < t.presentConfig.size) return@sumOf 0
//        println("calculating $t")
        1
//        if (solve(t, data.shape) != null) 1 else 0
    }
}

private fun part02(input: List<String>): Int {
    return 0
}

fun main() {
    Runner.get(::part01)
//        .test("day12_test.in", 2)
        .run("day12.in")

    Runner.get(::part02)
        .test("day12_test.in", 0)
        .run("day12.in")
}

