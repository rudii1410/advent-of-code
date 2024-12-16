package problem

import util.Runner
import util.Vector2

private fun String.parseLine(): Pair<Vector2, Vector2> {
    return split(" ")
        .map {
            it.split("=")
                .last()
        }
        .map { c ->
            c.split(",")
                .let { Vector2(it.first().toInt(), it.last().toInt()) }
        }
        .let { it.first() to it.last() }
}
private fun Vector2.toQuadrant(): List<Pair<Vector2, Vector2>> {
    val xMid = x / 2
    val yMid = y / 2
    return listOf(
        Vector2(0, 0) to Vector2(xMid - 1, yMid - 1),
        Vector2(xMid + 1, 0) to Vector2(x - 1, yMid - 1),
        Vector2(xMid + 1, yMid + 1) to Vector2(x - 1, y - 1),
        Vector2(0, yMid + 1) to Vector2(xMid - 1, y - 1)
    )
}
private fun Vector2.checkQuadrantGroup(group: List<Pair<Vector2, Vector2>>): Int {
    return group.indexOfFirst {
        x >= it.first.x && y >= it.first.y && x <= it.second.x && y <= it.second.y
    }
}
private fun part01(input: List<String>): Int {
    val size = Vector2(101, 103)
    val round = 100
    val canvas = MutableList(size.y) { MutableList(size.x) { 0 } }
    val groups = size.toQuadrant()
    input.forEach { line ->
        val (pos, vel) = line.parseLine()
        val x = ((pos.x + (vel.x * round)) % size.x)
            .let { if (it < 0) size.x + it else it }
        val y = ((pos.y + (vel.y * round)) % size.y)
            .let { if (it < 0) size.y + it else it }

        canvas[y][x] = canvas[y][x] + 1
    }
    val answers = MutableList(4) { 0 }
    canvas.forEachIndexed { y, line ->
        line.forEachIndexed xAxis@ { x, i ->
            if (i == 0) return@xAxis
            Vector2(x, y)
                .checkQuadrantGroup(groups)
                .takeIf { it >= 0 }
                ?.let { answers[it] = answers[it] + i }
        }
    }
    return answers.fold(1) { ans, i -> ans * i}
}

private fun part02(input: List<String>): Int {
    val size = Vector2(101, 103)
    var round = 1

    var maxX = 0
    var shouldPrint = false
    while (true) {
        val canvas = MutableList(size.y) { MutableList(size.x) { '.' } }
        input.forEach { line ->
            val (pos, vel) = line.parseLine()
            val x = ((pos.x + (vel.x * round)) % size.x)
                .let { if (it < 0) size.x + it else it }
            val y = ((pos.y + (vel.y * round)) % size.y)
                .let { if (it < 0) size.y + it else it }

            canvas[y][x] = '#'
        }

        canvas.forEach {
            it.joinToString("")
                .replace(".", "")
                .count()
                .let { r ->
                    if (r >= maxX) {
                        maxX = r
                        shouldPrint = true
                    }
                }
        }

        if (shouldPrint) {
            println("Round: $round")
            shouldPrint = false
            canvas.forEach { println(it.joinToString("")) }
            val pin = readln() // TODO: Fix this
        }
        round += 1
    }

    return 0
}

fun main() {
    Runner.get(::part01)
//        .test("day14_test.in", 12)
        .run("day14.in")

    Runner.get(::part02)
//        .test("day14_test.in", 0)
        .run("day14.in")
}
