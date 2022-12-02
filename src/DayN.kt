fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Dayn_test")
    check(part1(testInput) == 45000)

    val input = readInput("Dayn")
    println(part1(input))
    println(part2(input))
}
