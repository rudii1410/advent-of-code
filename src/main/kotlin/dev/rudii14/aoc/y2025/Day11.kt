package dev.rudii14.aoc.y2025

import dev.rudii14.aoc.util.Runner
import java.math.BigInteger

private fun traverse(
    memo: MutableMap<Pair<Int, Int>, Long>,
    edges: List<List<Int>>,
    start: Int,
    end: Int
): Long {
    if (memo.containsKey(start to end)) return memo.getValue(start to end)
    if (start == end) return 1
    
    return edges[start].sumOf { 
        traverse(
            memo,
            edges,
            it,
            end
        ).also { r -> memo[it to end] = r } 
    }
}

private fun List<String>.parseData(): Pair<Set<String>, List<List<Int>>> {
    val dict = mutableSetOf<String>()
    val edges = associate { line ->
        val node = line.split(" ")
        val from = node.first().dropLast(1).also { dict.add(it) }
        val to = node.drop(1).also { k -> k.forEach { dict.add(it) } }
        
        from to to
    }
    val newEdges = dict.map { v ->
        if (v == "out") emptyList()
        else edges.getValue(v).map { dict.indexOf(it) }
    }

    return dict.toSet() to newEdges
}

private fun part01(input: List<String>): Long {
    val (dict, edges) = input.parseData()
    return traverse(mutableMapOf(), edges, dict.indexOf("you"), dict.indexOf("out"))
}

private fun part02(input: List<String>): BigInteger {
    val (dict, edges) = input.parseData()
    val path = listOf(
        listOf("svr", "fft", "dac", "out"),
        listOf("svr", "dac", "fft", "out")
    )
    return path.sumOf { p -> 
        val memo = mutableMapOf<Pair<Int, Int>, Long>()
        p.windowed(2)
            .fold(BigInteger.ONE) { acc, i ->
                acc * traverse(memo, edges, dict.indexOf(i[0]), dict.indexOf(i[1])).toBigInteger() 
            }
    }
}

fun main() {
    Runner.get(::part01)
        .test("day11_test.in", 5)
        .run("day11.in")

    Runner.get(::part02)
        .test("day11_test_2.in", BigInteger.valueOf(2))
        .run("day11.in")
}
