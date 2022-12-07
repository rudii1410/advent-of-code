import util.runner
import kotlin.math.min

sealed class Node {
    data class Dir(val root: Dir?, val name: String, var totalSize: Int = 0, val child: MutableList<Node>) : Node()
    data class File(val name: String, val size: Int) : Node()
}

fun main() {
    fun aTraverse(dir: Node.Dir): Int {
        return dir.child.sumOf { if (it is Node.Dir) aTraverse(it) else 0 } +
                (dir.totalSize.takeIf { it <= 100_000 } ?: 0)
    }

    fun bTraverse(dir: Node.Dir, target: Int): Int {
        if (dir.totalSize < target) return Int.MAX_VALUE
        return min(
            dir.totalSize,
            dir.child.minOfOrNull {
                if (it is Node.Dir) bTraverse(it, target) else Int.MAX_VALUE
            } ?: Int.MAX_VALUE
        )
    }

    fun updateSizeToRoot(dir: Node.Dir?, size: Int) {
        dir?.run {
            totalSize += size
            updateSizeToRoot(root, size)
        }
    }

    fun getRoot(dir: Node.Dir?): Node.Dir {
        return if (dir?.root == null) dir!! else getRoot(dir.root)
    }

    fun generateTree(input: List<String>): Node.Dir {
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
    fun part1(input: List<String>): Int {
        return aTraverse(generateTree(input))
    }
    runner(95437, ::part1)


    /* Part 2 */
    fun part2(input: List<String>): Int {
        return generateTree(input).let {
            bTraverse(it, it.totalSize - 40_000_000)
        }
    }
    runner(24933642, ::part2)
}
