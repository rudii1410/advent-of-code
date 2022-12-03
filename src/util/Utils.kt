package util

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to util.md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun <T> runner(config: Config<T>, logic: (List<String>) -> T) {
    val day = logic::class.java.name.split("$")[0].dropLast(2)
    val testInput = readInput("${day}_test")
    val input = readInput(day)

    val label = "Part ${config.part}"
    val testResult = logic(testInput)
    val result = logic(input)
    try {
        check(testResult == config.expectedTestResult)
        println("$label: $result")
    } catch (e: IllegalStateException) {
        println("$label: test fail. Expected: ${config.expectedTestResult}, found: $testResult")
    }
}
