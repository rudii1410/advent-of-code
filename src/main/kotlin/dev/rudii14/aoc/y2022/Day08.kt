package dev.rudii14.aoc.y2022

import dev.rudii14.aoc.util.Runner

private object Direction {
    val LEFT = 0
    val TOP = 1
    val RIGHT = 2
    val BOTTOM = 3
}

/* Part 1 */
private fun findTallerTree(tree: List<String>, part: Int, currentTree: Int, v: Int, h: Int, maxV: Int, maxH: Int): Boolean {
    if (v == 0 || h == 0 || v == maxV - 1 || h == maxH - 1) return false

    return when (part) {
        Direction.LEFT -> {
            if (tree[v][h - 1].digitToInt() < currentTree)
                findTallerTree(tree, part, currentTree, v, h - 1, maxV, maxH)
            else true
        }
        Direction.TOP -> {
            if (tree[v - 1][h].digitToInt() < currentTree)
                findTallerTree(tree, part, currentTree, v - 1, h, maxV, maxH)
            else true
        }
        Direction.RIGHT -> {
            if (tree[v][h + 1].digitToInt() < currentTree)
                findTallerTree(tree, part, currentTree, v, h + 1, maxV, maxH)
            else true
        }
        else -> {
            if (tree[v + 1][h].digitToInt() < currentTree)
                findTallerTree(tree, part, currentTree, v + 1, h, maxV, maxH)
            else true
        }
    }
}

private fun traversePart(tree: List<String>, part: Int, currentTree: Int, v: Int, h: Int, maxV: Int, maxH: Int): Int {
    if (v == 0 || h == 0 || v == maxV - 1 || h == maxH - 1) return 0

    return when (part) {
        Direction.LEFT -> {
            1 + if (tree[v][h - 1].digitToInt() < currentTree) {
                traversePart(tree, part, currentTree, v, h - 1, maxV, maxH)
            } else 0
        }
        Direction.TOP -> {
            1 + if (tree[v - 1][h].digitToInt() < currentTree) {
                traversePart(tree, part, currentTree, v - 1, h, maxV, maxH)
            } else 0
        }
        Direction.RIGHT -> {
            1 + if (tree[v][h + 1].digitToInt() < currentTree) {
                traversePart(tree, part, currentTree, v, h + 1, maxV, maxH)
            } else 0
        }
        else -> {
            1 + if (tree[v + 1][h].digitToInt() < currentTree) {
                traversePart(tree, part, currentTree, v + 1, h, maxV, maxH)
            } else 0
        }
    }
}

private fun part1(tree: List<String>): Int {
    val maxV = tree.size
    val maxH = tree[0].length
    return (maxV * maxH) - tree.mapIndexed { v, row ->
        row.mapIndexed { h, c ->
            findTallerTree(tree, Direction.LEFT, c.digitToInt(), v, h, maxV, maxH) &&
                    findTallerTree(tree, Direction.TOP, c.digitToInt(), v, h, maxV, maxH) &&
                    findTallerTree(tree, Direction.RIGHT, c.digitToInt(), v, h, maxV, maxH) &&
                    findTallerTree(tree, Direction.BOTTOM, c.digitToInt(), v, h, maxV, maxH)
        }.count { it }
    }.sum()
}
//    Runner.run(::part1, 21)

/* Part 2 */

private fun part2(tree: List<String>): Int {
    val maxV = tree.size
    val maxH = tree[0].length
    return tree.mapIndexed { v, row ->
        row.mapIndexed { h, c ->
            traversePart(tree, Direction.LEFT, c.digitToInt(), v, h, maxV, maxH) *
                    traversePart(tree, Direction.TOP, c.digitToInt(), v, h, maxV, maxH) *
                    traversePart(tree, Direction.RIGHT, c.digitToInt(), v, h, maxV, maxH) *
                    traversePart(tree, Direction.BOTTOM, c.digitToInt(), v, h, maxV, maxH)
        }.max()
    }.max()
}

fun main() {
    Runner.get(::part1)
        .test("day08_test.in", 21)
        .run("day08.in")

    Runner.get(::part2)
        .test("day08_test.in", 8)
        .run("day08.in")
}
