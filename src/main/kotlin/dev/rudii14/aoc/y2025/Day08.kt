package dev.rudii14.aoc.y2025

import dev.rudii14.aoc.util.Runner
import dev.rudii14.aoc.util.data.UnionFind
import dev.rudii14.aoc.util.data.Point3
import dev.rudii14.aoc.util.math.distance3D

data class Edge(val from: Point3, val to: Point3, val weight: Double): Comparable<Edge> {
    override fun compareTo(other: Edge): Int {
        return weight compareTo other.weight
    }
}

private fun List<String>.parseInput(): Pair<Set<Point3>, List<Edge>> {
    val vertices = map { line ->
        line.split(",")
            .map { it.toInt() }
            .let { Point3.from(it) }
    }

    val edges = mutableListOf<Edge>()
    for (i in 0 until vertices.size) {
        for (j in i until vertices.size) {
            if (i == j) continue

            val weight = distance3D(vertices[i], vertices[j])
            edges.add(Edge(vertices[i], vertices[j], weight))
        }
    }
    
    return vertices.toSet() to edges.sorted()
}

private fun part01(input: List<String>): Int {
    val (vertices, edges) = input.parseInput()
    val ufds = UnionFind(vertices)
    val target = 1000

    for (i in 0 until target) {
        val edge = edges[i]
        if (ufds.isSameSet(edge.from, edge.to)) continue
        ufds.union(edge.from, edge.to)
    }

    val connectedVertices = mutableMapOf<Point3, Int>()
    vertices.forEach {
        val parent = ufds.findRoot(it)
        if (it == parent) return@forEach
        connectedVertices[parent] = connectedVertices.getOrDefault(parent, 1) + 1
    }
    return connectedVertices
        .toList()
        .map { it.second }
        .sortedDescending()
        .take(3)
        .reduce { a, b -> a * b }
}

private fun part02(input: List<String>): Int {
    val (vertices, edges) = input.parseInput()
    val ufds = UnionFind(vertices)
    
    val lastMerged = edges.fold(edges.first()) { acc, edge ->
        if (ufds.isSameSet(edge.from, edge.to)) return@fold acc
        ufds.union(edge.from, edge.to)
        edge
    }
    return lastMerged.from.x * lastMerged.to.x
}

fun main() {
    Runner.get(::part01)
//        .test("day08_test.in", 40)
        .run("day08.in")

    Runner.get(::part02)
        .test("day08_test.in", 25272)
        .run("day08.in")
}