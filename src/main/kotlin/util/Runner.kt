package util

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.reflect.KFunction
import kotlin.system.measureTimeMillis

object Runner {
    private fun getDirPath(year: String) = "src/main/resources/input/year_$year"
    private fun getTestInputName(day: String, x: Int) = "Day${day}_test_$x.in"
    private fun getInputName(day: String) = "Day$day.in"

    private fun initTestFiles(year: String, day: String, testCtr: Int) {
        val path = getDirPath(year)
        Files.createDirectories(Path.of(path))
        for(i in 0 until testCtr) {
            File(path, getTestInputName(day, i + 1)).createNewFile()
        }
        File(path, getInputName(day)).createNewFile()
    }

    private fun readInput(year: String, name: String) = File(getDirPath(year), name).readLines()

    fun <T> run(logic: (List<String>) -> T, vararg expectedTestResult: T) {
        val (year, day, functionName) = (logic as KFunction<*>).let {
            val fileName = it.javaClass.name.split("$").first().split(".")
            return@let listOf(
                fileName[0].split("_")[1],
                fileName[1].dropLast(2).drop(3),
                it.name
            )
        }

        initTestFiles(year, day, expectedTestResult.size)

        for (i in expectedTestResult.indices) {
            val testResult = logic(readInput(year, getTestInputName(day, i + 1)))
            try {
                check(testResult == expectedTestResult[i])
            } catch (e: IllegalStateException) {
                println("$functionName (#${i + 1}): test fail. Expected: $expectedTestResult, found: $testResult\n")
                return
            }
        }

        val time = measureTimeMillis {
            println("$functionName: ${logic(readInput(year, getInputName(day)))}")
        }
        println("Execution time: $time ms\n")
    }
}