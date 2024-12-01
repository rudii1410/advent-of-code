@file:Suppress("UNCHECKED_CAST")

package util

import kotlinx.coroutines.*
import java.io.File
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.findAnnotation
import kotlin.system.measureTimeMillis

object Runner {
    fun <IN, OUT> run(logic: suspend (IN) -> OUT) = runBlocking {
        val function = (logic as KFunction<*>).extractInfo()

        function.testSuites.forEachIndexed { index, (inputPath, output) ->
            val input = File(getDirPath(), inputPath).let {
                when (function.paramType) {
                    Type.ListString.value -> it.readLines()
                    Type.Str.value -> it.readText()
                    else -> throw IllegalStateException("")
                }
            }
            val executionTime = measureTimeMillis {
                val testResult = logic(input as IN)
                try {
                    check(testResult == output)
                    println("${function.name} (#${index + 1}): test passed")
                } catch (e: IllegalStateException) {
                    println("${function.name} (#${index + 1}): test fail. Expected: ${output}, found: $testResult")
                }
            }
            println("${function.name} (#${index + 1}): Execution time: $executionTime\n")
        }
    }

    private fun getDirPath() = "src/main/resources/input"

    private fun KFunction<*>.extractInfo(): FunctionInfo {
        val testSuites = findAnnotation<TestSuites>()
            ?: throw IllegalStateException("$name must contain @TestSuites annotation")
        check(testSuites.input.size == testSuites.output.size) {
            "@TestSuites input & output must match. " +
                    "Input size: ${testSuites.input.size}, output size: ${testSuites.output.size}"
        }
        val fileName = javaClass.name.split("$").first().split(".")
        return FunctionInfo(
            day = fileName[1].dropLast(2).drop(3),
            name = name,
            paramType = parameters.first().type,
            testSuites = testSuites.input.mapIndexed { index, s ->
                s to testSuites.output[index]
            }
        )
    }

    data class FunctionInfo(
        val day: String,
        val name: String,
        val paramType: KType,
        val testSuites: List<Pair<String, String>>
    )

    enum class Type(val value: KType) {
        ListString(
            String::class
                .createType()
                .let(KTypeProjection::invariant)
                .let(::listOf)
                .let(List::class::createType)
        ),
        Str(String::class.createType())
    }
}
