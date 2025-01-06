package dev.rudii14.aoc.y2024

import dev.rudii14.aoc.util.Runner

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

    return blocks
        .mapIndexed { l, data ->
            if (data.first() != ".") return@mapIndexed data

            var target = data.size
            var r = blocks.size
            while (l < r && target > 0) {
                r -= 1
                if (blocks[r].size > target || blocks[r].first() == ".") continue

                val lSize = data.size
                for (x in blocks[r].indices) {
                    data[lSize - target] = blocks[r][x]
                    blocks[r][x] = "."
                    target -= 1
                }
            }
            data
        }
        .flatten()
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
