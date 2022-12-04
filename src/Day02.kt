import util.runner

fun main() {
    fun part1(input: List<String>): Int {
        val shapeScore = mapOf(
            'X' to 1,
            'Y' to 2,
            'Z' to 3
        )
        val gameScore = mapOf(
            "A Y" to 6,
            "B Z" to 6,
            "C X" to 6,
            "A X" to 3,
            "B Y" to 3,
            "C Z" to 3
        )
        return input.sumOf { (shapeScore[it[2]] ?: 0) + (gameScore[it] ?: 0) }
    }

    fun part2(input: List<String>): Int {
        val resultScore = mapOf(
            'X' to 0,
            'Y' to 3,
            'Z' to 6
        )
        val gameScore = mapOf(
            "A Y" to 1,
            "A Z" to 2,
            "A X" to 3,

            "B X" to 1,
            "B Y" to 2,
            "B Z" to 3,

            "C Z" to 1,
            "C X" to 2,
            "C Y" to 3
        )
        return input.sumOf { (resultScore[it[2]] ?: 0) + (gameScore[it] ?: 0) }
    }

    runner(15, ::part1)
    runner(12, ::part2)
}
