//TODO
//package dev.rudii14.aoc.y2022
//
//import kotlinx.serialization.json.*
//import dev.rudii14.aoc.util.Runner
//import java.util.LinkedList
//
//fun main() {
//    fun String.toJsonArray(): JsonArray = Json.parseToJsonElement(this).jsonArray
//
//    fun checkSignal(left: LinkedList<JsonElement>, right: LinkedList<JsonElement>): Boolean? {
//        while (left.isNotEmpty() && right.isNotEmpty()) {
//            val l = left.remove()
//            val r = right.remove()
//
//            if (l is JsonArray) {
//                val res = checkSignal(
//                    LinkedList(l.jsonArray),
//                    LinkedList(if (r is JsonArray) r.jsonArray else JsonArray(listOf(r)))
//                )
//                if (res != null) return res
//            } else if (r is JsonArray) {
//                val res = checkSignal(LinkedList(JsonArray(listOf(l))), LinkedList(r.jsonArray))
//                if (res != null) return res
//            } else {
//                if (l.jsonPrimitive.int == r.jsonPrimitive.int) continue
//                return l.jsonPrimitive.int < r.jsonPrimitive.int
//            }
//        }
//
//        if (left.size == 0 && right.size > 0) return true
//        if (left.size > 0) return false
//
//        return null
//    }
//
//    /* Part 1 */
//    fun part1(input: String): Int {
//        return input.split("\n\n").mapIndexed { i, p ->
//            p.split("\n").let {
//                if (checkSignal(LinkedList(it[0].toJsonArray()), LinkedList(it[1].toJsonArray())) == true) i + 1
//                else 0
//            }
//        }.sum()
//    }
//    Runner.runAll(::part1, 13)
//
//
//    /* Part 2 */
//    fun part2(input: String): Int {
//        return input.replace("\n\n", "\n")
//            .split("\n")
//            .toMutableList()
//            .apply {
//                add("[[2]]")
//                add("[[6]]")
//            }
//            .sortedWith { o1, o2 ->
//                when (checkSignal(LinkedList(o1.toJsonArray()), LinkedList(o2.toJsonArray()))) {
//                    true -> -1
//                    false -> 1
//                    else -> 0
//                }
//            }
//            .let {
//                (it.indexOf("[[2]]") + 1) * (it.indexOf("[[6]]") + 1)
//            }
//    }
//    Runner.runAll(::part2, 140)
//}
