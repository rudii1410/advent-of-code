package dev.rudii14.aoc.util

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import kotlinx.coroutines.runBlocking
import java.io.File
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.system.measureNanoTime

class Runner <I, O> (
    private val solution: (input: I) -> O,
) {
    private val testCase: MutableList<Pair<String, O>> = mutableListOf()
    private val functionInfo: FunctionInfo

    init {
        functionInfo = (solution as KFunction<*>).extractInfo()
    }

    fun test(inputPath: String, expectedOutput: O): Runner<I, O> {
        testCase.add(inputPath to expectedOutput)
        return this
    }

    fun run(inputPath: String) {
        testCase
            .mapIndexed { index, (inputPath, output) ->
                val testResult = run(
                    inputPath = inputPath,
                    paramType = functionInfo.paramType
                )

                try {
                    check(testResult == output)
                    println("${functionInfo.name} (test #${index + 1}): pass")
                    true
                } catch (e: IllegalStateException) {
                    println("${functionInfo.name} (test #${index + 1}): fail. Expected: ${output}, found: $testResult")
                    false
                }
            }
            .none { !it }
            .takeIf { it } ?: return

        var result: O?
        val executionTime = measureNanoTime {
            result = run(
                inputPath = inputPath,
                paramType = functionInfo.paramType
            )
        }
        println("${functionInfo.name} result: $result. Execution time: $executionTime ns")
    }

    // TODO: continue working on this
    fun run() = runBlocking {
        // check if input file is exist
        val inputPath = getInputDirectory()
        // Fetch input
        val inputUrl = "https://adventofcode.com/${functionInfo.year}/day/${functionInfo.day.toInt()}/input"
        val client = HttpClient(CIO)
        val response = client.request(inputUrl) {
            method = HttpMethod.Get
        }
        if (response.status.value !in 200..299) {
            return@runBlocking
        }
        // create files
        val path = getInputDirectory()
        // run as usual
    }

    @Suppress("UNCHECKED_CAST")
    private fun run(inputPath: String, paramType: KType): O {
        return File(INPUT_DIR + "/${functionInfo.year}", inputPath)
            .let {
                when (paramType) {
                    Type.ListString.value -> it.readLines()
                    Type.Str.value -> it.readText()
                    else -> throw IllegalStateException("")
                }
            }
            .let { it as I }
            .let(solution)
    }

    private fun KFunction<*>.extractInfo(): FunctionInfo {
        val fileName = javaClass.name.split("$").first().split(".")
        return FunctionInfo(
            day = fileName[1].dropLast(2).drop(3),
            name = name,
            paramType = parameters.first().type,
            year = javaClass.packageName.split(".").last()
        )
    }

    private fun getInputDirectory(): String {
        val directoryPath = Path.of(INPUT_DIR + "/${functionInfo.year}")
        if (!directoryPath.exists()) {
            directoryPath.createDirectories()
        }

        return directoryPath.toString()
    }

    companion object {
        fun <I, O> get(logic: (I) -> O) = Runner(logic)

        private const val INPUT_DIR = "src/main/resources/input"
    }
}

private data class FunctionInfo(
    val day: String,
    val year: String,
    val name: String,
    val paramType: KType,
)

private enum class Type(val value: KType) {
    ListString(
        String::class
            .createType()
            .let(KTypeProjection::invariant)
            .let(::listOf)
            .let(List::class::createType)
    ),
    Str(String::class.createType())
}
