package dev.rudii14.aoc.y2022

import dev.rudii14.aoc.util.Runner
import kotlin.math.min

private sealed class Node {
    data class Dir(
        val root: Dir?,
        val name: String,
        var totalSize: Int = 0,
        val child: MutableList<Node>
    ) : Node()
    data class File(val name: String, val size: Int) : Node()
}

private fun aTraverse(dir: Node.Dir): Int {
    return dir.child.sumOf { if (it is Node.Dir) aTraverse(it) else 0 } +
            (dir.totalSize.takeIf { it <= 100_000 } ?: 0)
}

private fun bTraverse(dir: Node.Dir, target: Int): Int {
    if (dir.totalSize < target) return Int.MAX_VALUE
    return min(
        dir.totalSize,
        dir.child.minOfOrNull {
            if (it is Node.Dir) bTraverse(it, target) else Int.MAX_VALUE
        } ?: Int.MAX_VALUE
    )
}

private fun updateSizeToRoot(dir: Node.Dir?, size: Int) {
    dir?.run {
        totalSize += size
        updateSizeToRoot(root, size)
    }
}

private fun getRoot(dir: Node.Dir?): Node.Dir {
    return if (dir?.root == null) dir!! else getRoot(dir.root)
}

private fun generateTree(input: List<String>): Node.Dir {
    var currentDir: Node.Dir? = null
    input.forEach { line ->
        if (line.startsWith("$")) {
            currentDir = when (val cmd = line.removeRange(0, 2)) {
                "cd /" -> Node.Dir(root = null, name = "/", child = mutableListOf())
                "cd .." -> currentDir?.root ?: currentDir
                "ls" -> currentDir
                else -> {
                    (currentDir?.child?.find { it is Node.Dir && it.name == cmd.split(" ")[1] } ?: currentDir) as Node.Dir
                }
            }
        } else {
            val row = line.split(" ")
            currentDir?.child?.add(
                if (row[0] == "dir") Node.Dir(root = currentDir, name = row[1], child = mutableListOf())
                else {
                    val size = row[0].toInt()
                    updateSizeToRoot(currentDir, size)
                    Node.File(name = row[1], size = size)
                }
            )
        }
    }

    return getRoot(currentDir)
}

/* Part 1 */
private fun part1(input: List<String>): Int {
    return aTraverse(generateTree(input))
}
//    Runner.run(::part1, 95437)


/* Part 2 */
private fun part2(input: List<String>): Int {
    return generateTree(input).let {
        bTraverse(it, it.totalSize - 40_000_000)
    }
}
//    Runner.run(::part2, 24933642)

fun main() {
    Runner.get(::part1)
        .test("day07_test.in", 95437)
        .run("day07.in")

    Runner.get(::part2)
        .test("day07_test.in", 24933642)
        .run("day07.in")
}
