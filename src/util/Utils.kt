package util

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.reflect.KFunction

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

fun <T> runner(expectedTestResult: T, logic: (List<String>) -> T) {
    val (day, functionName) = (logic as KFunction<*>).let {
        return@let listOf(
            it.javaClass.name.split("$").first().dropLast(2), it.name
        )
    }

    val testResult = logic(readInput("${day}_test"))
    try {
        check(testResult == expectedTestResult)
        println("$functionName: ${logic(readInput(day))}")
    } catch (e: IllegalStateException) {
        println("$functionName: test fail. Expected: $expectedTestResult, found: $testResult")
    }
}
