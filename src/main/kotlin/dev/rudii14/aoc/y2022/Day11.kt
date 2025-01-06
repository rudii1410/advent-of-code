package dev.rudii14.aoc.y2022

import dev.rudii14.aoc.util.Runner
import java.util.LinkedList

fun main() {
    fun String.splitLast() = this.split(" ").last()

    fun runOps(charOperator: String): (Long, Long) -> Long {
        return when(charOperator) {
            "+" -> { a, b -> a + b }
            "-" -> { a, b -> a - b }
            "/" -> { a, b -> a / b }
            "*" -> { a, b -> a * b }
            else -> { _, _ -> 0 }
        }
    }

    fun parseRemainder(m: List<LinkedList<Long>>, i: Int, cmd: List<String>) {
        m[i].addAll(
            cmd[1].split(": ")[1]
                .split(", ")
                .map { it.toLong() }
        )
    }

    fun computeOps(cmd: List<String>, v: Long): Long {
        return cmd[2].split("= ")[1]
            .replace("old", "$v")
            .split(" ")
            .let { runOps(it[1]).invoke(it[0].toLong(), it[2].toLong()) }
    }

    fun destMonkey(cmd: List<String>, res: Long): Int {
        return if (res % cmd[3].splitLast().toLong() == 0L)
            cmd[4].splitLast().toInt()
        else
            cmd[5].splitLast().toInt()
    }

    /* Part 1 */
    fun part1(input: String): Long {
        val monkeys: List<LinkedList<Long>>
        val ctr: MutableList<Long>
        val ops: MutableList<(Long) -> Unit> = mutableListOf()

        input.split("\n\n")
            .apply {
                monkeys = List(size) { LinkedList() }
                ctr = MutableList(size) { 0 }
            }
            .forEachIndexed { i, r ->
                val cmd = r.split("\n").map { it.trim() }
                parseRemainder(monkeys, i, cmd)
                ops.add(i) { v: Long ->
                    ctr[i]++
                    (computeOps(cmd, v) / 3).also {
                        monkeys[destMonkey(cmd, it)].add(it)
                    }
                }
            }


        repeat(20) {
            monkeys.forEachIndexed { i, _ ->
                while (monkeys[i].isNotEmpty()) {
                    ops[i].invoke(monkeys[i].remove())
                }
            }
        }

        return ctr.apply { sort() }.takeLast(2).let { it[0] * it[1] }
    }
    Runner.get(::part1)
        .test("day11_test.in", 10605)
        .run("day11.in")


    /* Part 2 */
    fun part2(input: String): Long {
        val monkeys: List<LinkedList<Long>>
        val ctr: MutableList<Long>
        val ops: MutableList<(Long, Int) -> Unit> = mutableListOf()
        var lcm = 1

        input.split("\n\n")
            .apply {
                monkeys = List(size) { LinkedList() }
                ctr = MutableList(size) { 0 }
            }
            .forEachIndexed { i, r ->
                val cmd = r.split("\n").map { it.trim() }
                parseRemainder(monkeys, i, cmd)
                lcm *= cmd[3].splitLast().toInt()
                ops.add(i) { v: Long, lcm: Int ->
                    ctr[i]++
                    (computeOps(cmd, v) % lcm).also {
                        monkeys[destMonkey(cmd, it)].add(it)
                    }
                }
            }

        repeat(10_000) {
            monkeys.forEachIndexed { i, _ ->
                while (monkeys[i].isNotEmpty()) {
                    ops[i].invoke(monkeys[i].remove(), lcm)
                }
            }
        }

        return ctr.apply { sort() }.takeLast(2).let { it[0] * it[1] }
    }
    Runner.get(::part2)
        .test("day11_test.in", 2713310158)
        .run("day11.in")
}
