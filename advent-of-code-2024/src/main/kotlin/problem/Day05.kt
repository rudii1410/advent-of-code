package problem

import util.Runner

private data class Node(
    val left: List<Int> = emptyList(),
    val right: List<Int> = emptyList()
) {
    fun merge(other: Node): Node {
        return Node(
            left = left + other.left,
            right = right + other.right
        )
    }
}

private fun <T> List<T>.middleValue(): T {
    return this[(size / 2) + (size % 2) - 1]
}

private fun List<String>.prepareData(): Pair<Map<Int, Node>, List<String>> {
    fun parse(str: String): List<Pair<Int, Node>> {
        return str.split("|")
            .map { it.toInt() }
            .let {
                listOf(
                    it.first() to Node(right = listOf(it.last())),
                    it.last() to Node(left = listOf(it.first()))
                )
            }
    }
    fun merge(data: Map.Entry<Int, List<Pair<Int, Node>>>): Pair<Int, Node> {
        return data.value
            .fold(Node()) { acc, pair -> acc.merge(pair.second) }
            .let { v -> data.key to v }
    }

    return indexOf("")
        .let { subList(0, it) to subList(it + 1, size) }
        .let { (rules, updates) ->
            rules
                .map(::parse)
                .flatten()
                .groupBy { it.first }
                .map(::merge)
                .toMap()
                .let { it to updates }
        }
}

private fun List<String>.isSorted(mapping: Map<Int, Node>): Boolean {
    return mapIndexed { idx, data ->
        if (idx == lastIndex) return@mapIndexed true

        mapping[data.toInt()]
            ?.let { d ->
                val right = subList(idx + 1, size)
                    .all { d.right.contains(it.toInt()) }
                val left = if (idx > 0) {
                    subList(0, idx)
                        .all { d.left.contains(it.toInt()) }
                } else {
                    true
                }

                left && right
            } ?: false
    }.all { it }
}

private fun part01(input: List<String>): Int {
    val (mapping, updates) = input.prepareData()

    return updates.sumOf { line ->
        line.split(",")
            .let { l ->
                l.middleValue()
                    .takeIf { l.isSorted(mapping) }
            }
            .let { it?.toInt() ?: 0 }
    }
}

private fun part02(input: List<String>): Int {
    val (mapping, updates) = input.prepareData()

    fun sort(left: String, right: String): Int {
        return mapping[left.toInt()]
            ?.let {
                if (it.left.contains(right.toInt())) -1
                else if (it.right.contains(right.toInt())) 1
                else 0
            } ?: 0
    }

    return updates
        .mapNotNull { line ->
            line.split(",")
                .let { l -> l.takeIf { l.isSorted(mapping).not() } }
        }
        .sumOf {
            it.sortedWith(::sort)
                .middleValue()
                .toInt()
        }
}

fun main() {
    Runner.get(::part01)
        .test("day05_test.in", 143)
        .run("day05.in")

    Runner.get(::part02)
        .test("day05_test.in", 123)
        .run("day05.in")
}
