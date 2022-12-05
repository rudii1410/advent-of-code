import util.runner
import java.util.*

fun main() {
    fun MutableMap<Int, Stack<String>>.getHead(): String {
        return this.map {
            it.value.peek()
                .replace("[", "")
                .replace("]", "")
        }.joinToString("")
    }
    fun MutableMap<Int, Stack<String>>.getOrPut(idx: Int): Stack<String> = this.getOrPut(idx) { Stack() }

    fun parse(input: List<String>, crateStacks: MutableMap<Int, Stack<String>>, onQuery: (Int, Int, Int) -> Unit) {
        var isQueryMode = false
        val tmpLine = mutableListOf<List<String>>()
        input.forEach {  line ->
            if (line.replace(" ", "").toIntOrNull() != null) return@forEach
            if (line.isEmpty()) {
                isQueryMode = true
                tmpLine.reversed().forEach { iS ->
                    iS.forEachIndexed { i, s ->
                        crateStacks.getOrPut(i).apply { if (s.isNotEmpty()) add(s) }
                    }
                }
                return@forEach
            }

            if (!isQueryMode) {
                tmpLine.add(line.chunked(4).map { it.replace(" ", "") })
                return@forEach
            }

            val (n, from, to) = line.split(" ")
                .run { listOf(this[1], this[3], this[5]) }
                .map { it.toInt() }
            onQuery(n, from, to)
        }
    }

    /* Part 1 */
    fun part1(input: List<String>): String {
        return mutableMapOf<Int, Stack<String>>().apply {
            parse(input, this) { n, from, to ->
                repeat(n) { this.getOrPut(to - 1).add(this[from - 1]?.pop()) }
            }
        }.getHead()
    }
    runner("CMZ", ::part1)


    /* Part 2 */
    fun part2(input: List<String>): String {
        return mutableMapOf<Int, Stack<String>>().apply {
            parse(input, this) { n, from, to ->
                val tmp = Stack<String>()
                repeat(n) { tmp.add(this[from - 1]?.pop()) }
                while(tmp.isNotEmpty()) this.getOrPut(to - 1).add(tmp.pop())
            }
        }.getHead()
    }
    runner("MCD", ::part2)
}
