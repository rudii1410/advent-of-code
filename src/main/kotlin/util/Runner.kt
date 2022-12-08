package util

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.reflect.KFunction
import kotlin.system.measureTimeMillis

object Runner {
    private fun getDirPath(year: String) = "src/main/resources/input/year_$year"
    private fun getTestInputName(day: String) = "Day${day}_test.in"
    private fun getInputName(day: String) = "Day$day.in"

    private fun initTestFiles(year: String, day: String) {
        val path = getDirPath(year)
        Files.createDirectories(Path.of(path))
        File(path, getTestInputName(day)).createNewFile()
        File(path, getInputName(day)).createNewFile()
    }

    private fun readInput(year: String, name: String) = File(getDirPath(year), name).readLines()

    fun <T> run(expectedTestResult: T, logic: (List<String>) -> T) {
        val (year, day, functionName) = (logic as KFunction<*>).let {
            val fileName = it.javaClass.name.split("$").first().split(".")
            return@let listOf(
                fileName[0].split("_")[1],
                fileName[1].dropLast(2).drop(3),
                it.name
            )
        }

        initTestFiles(year, day)

        val testResult = logic(readInput(year, getTestInputName(day)))
        try {
            check(testResult == expectedTestResult)
        } catch (e: IllegalStateException) {
            println("$functionName: test fail. Expected: $expectedTestResult, found: $testResult\n")
            return
        }

        val time = measureTimeMillis {
            println("$functionName: ${logic(readInput(year, getInputName(day)))}")
        }
        println("Execution time: $time ms\n")
    }
}