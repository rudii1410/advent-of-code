package problem

import kotlinx.coroutines.*
import util.Runner

private fun String.prepareData(): Pair<Long, List<Long>> {
    return split(": ")
        .let { (result, seq) ->
            seq.split(" ")
                .map { it.toLong() }
                .let { result.toLong() to it }
        }
}

private fun traverse(result: Long, idx: Int, availableOp: List<String>, size: Int, data: List<Long>, target: Long): Boolean {
    if (result == target && idx == size) return true
    if (result < target && idx >= size) return false
    if (result > target) return false

    for (i in availableOp) {
        if (i == "*" && traverse(result * data[idx], idx + 1, availableOp, size, data, target)) return true
        if (i == "+" && traverse(result + data[idx], idx + 1, availableOp, size, data, target)) return true
        if (i == "||" && traverse("$result${data[idx]}".toLong(), idx + 1, availableOp, size, data, target)) return true
    }
    return false
}

private fun part01(input: List<String>): Long {
    return input.sumOf { line ->
        val (target, seq) = line.prepareData()
        val isEqual = traverse(seq.first(), 1, listOf("+", "*"), seq.size, seq, target)
        if (isEqual) target else 0L
    }
}

private fun part02(input: List<String>): Long {
    val ops = listOf("+", "*", "||")
    return input.sumOf { line ->
        val (target, seq) = line.prepareData()
        val isEqual = traverse(seq.first(), 1, ops, seq.size, seq, target) ||
                traverse("${seq[0]}${seq[1]}".toLong(), 2, ops, seq.size, seq, target)
        if (isEqual) target else 0L
    }
}

fun main() {
    runBlocking {
        listOf(
            async {
                Runner.get(::part01)
                    .test("day07_test.in", 3749)
                    .run("day07.in")
            },
            async {
                Runner.get(::part02)
                    .test("day07_test.in", 11387)
                    .run("day07.in")
            }
        ).awaitAll()
    }
}