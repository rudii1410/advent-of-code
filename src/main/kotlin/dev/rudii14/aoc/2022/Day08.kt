package year_2022

import util.Runner

fun main() {
    val LEFT = 0
    val TOP = 1
    val RIGHT = 2
    val BOTTOM = 3

    /* Part 1 */
    fun findTallerTree(tree: List<String>, part: Int, currentTree: Int, v: Int, h: Int, maxV: Int, maxH: Int): Boolean {
        if (v == 0 || h == 0 || v == maxV - 1 || h == maxH - 1) return false

        return when (part) {
            LEFT -> {
                if (tree[v][h - 1].digitToInt() < currentTree)
                    findTallerTree(tree, part, currentTree, v, h - 1, maxV, maxH)
                else true
            }
            TOP -> {
                if (tree[v - 1][h].digitToInt() < currentTree)
                    findTallerTree(tree, part, currentTree, v - 1, h, maxV, maxH)
                else true
            }
            RIGHT -> {
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
    fun part1(tree: List<String>): Int {
        val maxV = tree.size
        val maxH = tree[0].length
        return (maxV * maxH) - tree.mapIndexed { v, row ->
            row.mapIndexed { h, c ->
                findTallerTree(tree, LEFT, c.digitToInt(), v, h, maxV, maxH) &&
                    findTallerTree(tree, TOP, c.digitToInt(), v, h, maxV, maxH) &&
                    findTallerTree(tree, RIGHT, c.digitToInt(), v, h, maxV, maxH) &&
                    findTallerTree(tree, BOTTOM, c.digitToInt(), v, h, maxV, maxH)
            }.count { it }
        }.sum()
    }
    Runner.run(::part1, 21)

    /* Part 2 */
    fun traversePart(tree: List<String>, part: Int, currentTree: Int, v: Int, h: Int, maxV: Int, maxH: Int): Int {
        if (v == 0 || h == 0 || v == maxV - 1 || h == maxH - 1) return 0

        return when (part) {
            LEFT -> {
                1 + if (tree[v][h - 1].digitToInt() < currentTree) {
                    traversePart(tree, part, currentTree, v, h - 1, maxV, maxH)
                } else 0
            }
            TOP -> {
                1 + if (tree[v - 1][h].digitToInt() < currentTree) {
                    traversePart(tree, part, currentTree, v - 1, h, maxV, maxH)
                } else 0
            }
            RIGHT -> {
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
    fun part2(tree: List<String>): Int {
        val maxV = tree.size
        val maxH = tree[0].length
        return tree.mapIndexed { v, row ->
            row.mapIndexed { h, c ->
                traversePart(tree, LEFT, c.digitToInt(), v, h, maxV, maxH) *
                    traversePart(tree, TOP, c.digitToInt(), v, h, maxV, maxH) *
                    traversePart(tree, RIGHT, c.digitToInt(), v, h, maxV, maxH) *
                    traversePart(tree, BOTTOM, c.digitToInt(), v, h, maxV, maxH)
            }.max()
        }.max()
    }
    Runner.run(::part2, 8)
}
