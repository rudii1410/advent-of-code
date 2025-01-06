package dev.rudii14.aoc.y2022

import dev.rudii14.aoc.util.Runner

private fun String.parse(): Pair<MutableMap<Int, MutableList<String>>, List<Pair<Int, Pair<Int, Int>>>> {
    val (rawStacks, rawQuery) = this.split("\n\n")

    val stacks = rawStacks.split("\n")
        .map { p ->
            p.chunked(4)
                .map {
                    it.replace(" ", "")
                        .replace("[", "")
                        .replace("]", "")
                }
        }
        .let { it.take(it.size - 1).reversed() to it.last() }
        .let { (raw, labels) ->
            labels.mapIndexed { idx, s ->
                s.toInt() to raw.mapNotNull { c -> c[idx].takeIf { it.isNotEmpty() } }.toMutableList()
            }.toMap()
        }
        .toMutableMap()

    val query = rawQuery.split("\n")
        .map { l ->
            l.split(" ")
                .let { it[1].toInt() to (it[3].toInt() to it[5].toInt()) }
        }
    return stacks to query
}

/* Part 1 */
private fun part1(input: String): String {
    val (stacks, query) = input.parse()
    query.forEach { (count, loc) ->
        val (from, to) = loc
        repeat(count) { stacks.getValue(to).add(stacks.getValue(from).removeLast()) }
    }
    return stacks.map { it.value.last() }.joinToString("")
}


/* Part 2 */
private fun part2(input: String): String {
    val (stacks, query) = input.parse()
    query.forEach { (count, loc) ->
        val (from, to) = loc
        (1..count)
            .map { stacks.getValue(from).removeLast() }
            .reversed()
            .let { stacks.getValue(to).addAll(it) }
    }
    return stacks.map { it.value.last() }.joinToString("")
}

fun main() {
    Runner.get(::part1)
        .test("day05_test.in", "CMZ")
        .run("day05.in")

    Runner.get(::part2)
        .test("day05_test.in", "MCD")
        .run("day05.in")
}
