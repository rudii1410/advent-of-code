package year_2022

import util.Runner
import java.util.Stack
import kotlin.math.min


data class Node1(val label: String, val rate: Int, val child: MutableList<Node1>)

fun main() {
    /* Part 1 */
    fun part1(input: List<String>): Int {
        val map = mutableMapOf<String, Pair<Int, List<String>>>()
        val visited = mutableMapOf<String, Boolean>()
        val valve = mutableMapOf<String, Boolean>()
        input.forEach { row ->
            row.split("; ", "=", "valve").let { d ->
//                println(d[3].split(", ").map { it.replace("s ", "").trim() })
//                println()
                val idx = d[0].split(" ")[1]
                val w = d[1].toInt()
                map[idx] = Pair(w, d[3].split(", ").map { it.replace("s ", "").trim() })
                visited[idx] = false
                valve[idx] = w == 0
            }
        }
        val stack = Stack<String>().apply { add("AA") }
        var minutes = 1
        var result = 0
        var tmp = 0

        while(stack.isNotEmpty() && minutes <= 6) {
            val v = stack.peek()
            println("valve: $v, minute: $minutes, isValveOpen: ${valve[v]}")
            println(stack)
            if (valve[v] == false && map[v]!!.first > 0) {
                minutes += 1
                valve[v] = true
                result += tmp
                continue
            }
            if (visited[v] == true) {
                stack.pop()
                continue
            }
            visited[v] = true

            tmp += map[v]!!.first

            println("$tmp - $result")
            stack.pop()
            map[v]!!.second.reversed().forEach { stack.push(it) }
            minutes += 1
            println("next: ${stack.peek()}")
        }

        println(result)
        return result
    }
    Runner.run(::part1, 1651)


    /* Part 2 */
    fun part2(input: List<String>): Int {
        return input.size
    }
    Runner.run(::part2, 0)
}
