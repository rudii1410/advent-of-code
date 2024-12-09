package problem

import util.Runner

private fun String.prepareData(): List<MutableList<String>> {
    return mapIndexedNotNull { idx, c ->
        c.digitToInt()
            .takeIf { it > 0 }
            ?.let { MutableList(it) { if (idx % 2 == 1) "." else "${idx / 2}" } }
    }
}

private fun List<String>.calculate(): Long {
    return mapIndexed { index, c ->
        (if (c == ".") "0" else c)
            .toLong()
            .let { it * index }
    }.sum()
}

private fun part01(input: String): Long {
    val blocks = input.prepareData()
        .flatten()
        .toMutableList()

    var l = 0
    var r = blocks.lastIndex
    while (l < r) {
        if (blocks[l] == "." && blocks[r] != ".") {
            blocks[l] = blocks[r]
            blocks[r] = "."
        }

        if (blocks[l] != ".") l += 1
        if (blocks[r] == ".") r -= 1
    }
    return blocks.calculate()
}

private fun part02(input: String): Long {
    val blocks = input.prepareData()
        .toMutableList()

    for (l in blocks.indices) {
        if (blocks[l].first() != ".") continue
        var target = blocks[l].size
        var r = blocks.lastIndex
        while (l < r && target > 0) {
            val rSize = blocks[r].size
            if (rSize > target || blocks[r].first() == ".") {
                r -= 1
                continue
            }

            val lSize = blocks[l].size
            for (x in blocks[r].indices) {
                blocks[l][lSize - target] = blocks[r][x]
                blocks[r][x] = "."
                target -= 1
            }

            r -= 1
        }
    }
    return blocks.flatten()
        .calculate()
}

fun main() {
    Runner.get(::part01)
        .test("day09_test.in", 1928)
        .run("day09.in")

    Runner.get(::part02)
        .test("day09_test.in", 2858)
        .run("day09.in")
}
