@file:Suppress("UNCHECKED_CAST")

package util

import java.io.File
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.findAnnotation
import kotlin.system.measureTimeMillis

object Runner {
    fun <IN, OUT> run(vararg logics: (IN) -> OUT) {
        logics.forEach(::run)
    }

    fun <IN, OUT> run(logic: (IN) -> OUT) {
        val function = (logic as KFunction<*>).extractInfo()

        function.testSuites.mapIndexed { index, (inputPath, output) ->
            val testResult = run(
                inputPath = inputPath,
                paramType = function.paramType,
                logic = logic
            )

            try {
                check(testResult == output)
                println("${function.name} (test #${index + 1}): pass")
                true
            } catch (e: IllegalStateException) {
                println("${function.name} (test #${index + 1}): fail. Expected: ${output}, found: $testResult")
                false
            }
        }.none { !it }
            .takeIf { it } ?: return

        var result: OUT?
        val executionTime = measureTimeMillis {
            result = run(
                inputPath = function.problemPath,
                paramType = function.paramType,
                logic = logic
            )
        }
        println("${function.name} result: $result. Execution time: $executionTime")
    }

    private fun <IN, OUT> run(
        inputPath: String,
        paramType: KType,
        logic: (IN) -> OUT
    ): OUT {
        val input = File(getDirPath(), inputPath).let {
            when (paramType) {
                Type.ListString.value -> it.readLines()
                Type.Str.value -> it.readText()
                else -> throw IllegalStateException("")
            }
        }

        return logic(input as IN)
    }

    private fun getDirPath() = "src/main/resources/input"

    private fun KFunction<*>.extractInfo(): FunctionInfo {
        val testSuites = findAnnotation<TestSuites>()
            ?: throw IllegalStateException("$name must contain @TestSuites annotation")
        check(testSuites.testInputFiles.size == testSuites.testExpectedOutput.size) {
            "@TestSuites input & output must match. " +
                    "Input size: ${testSuites.testInputFiles.size}, output size: ${testSuites.testExpectedOutput.size}"
        }
        val fileName = javaClass.name.split("$").first().split(".")
        return FunctionInfo(
            day = fileName[1].dropLast(2).drop(3),
            name = name,
            paramType = parameters.first().type,
            testSuites = testSuites.testInputFiles.mapIndexed { index, s ->
                s to testSuites.testExpectedOutput[index]
            },
            problemPath = testSuites.problemFiles
        )
    }

    data class FunctionInfo(
        val day: String,
        val name: String,
        val paramType: KType,
        val testSuites: List<Pair<String, String>>,
        val problemPath: String
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
