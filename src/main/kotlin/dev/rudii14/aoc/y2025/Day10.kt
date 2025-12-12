package dev.rudii14.aoc.y2025

import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver
import dev.rudii14.aoc.util.Runner
import dev.rudii14.aoc.util.turnBitOn
import org.apache.commons.math3.optim.MaxIter
import org.apache.commons.math3.optim.linear.LinearConstraint
import org.apache.commons.math3.optim.linear.LinearConstraintSet
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction
import org.apache.commons.math3.optim.linear.NonNegativeConstraint
import org.apache.commons.math3.optim.linear.Relationship
import org.apache.commons.math3.optim.linear.SimplexSolver
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType
import kotlin.collections.sumOf
import kotlin.math.roundToLong

data class Manual(
    val lightState: Int,
    val buttons: Map<String, Int>,
    val joltage: List<Int>,
)

private fun String.removeBracket(): String {
    return substring(1, length - 1)
}

private fun List<String>.parseInput(): List<Manual> {
    return map { line ->
        val data = line.split(" ")
        val joltage = data.last()
            .removeBracket()
            .split(",")
            .map { it.toInt() }
        val lightSize = joltage.size

        Manual(
            data.first()
                .removeBracket()
                .map { if (it == '#') '1' else '0' }
                .joinToString("")
                .toInt(2),
            data.subList(1, data.size - 1)
                .associateWith { b -> 
                    b.removeBracket()
                        .split(",")
                        .map { it.toInt() }
                        .fold(0) { acc, b -> acc.turnBitOn(lightSize - 1 - b) }
                },
            joltage
        )
    }
}

private fun Int.toggleLight(button: Int): Int {
    return this xor button
}

private fun List<String>.check(other: List<String>): Boolean {
    return this.foldIndexed(false) { idx, acc, s -> acc || s == other[idx] }
}

private data class Q(
    val lightState: Int,
    val button: Pair<String, Int>,
    val step: Int,
    val buttonSoFar: List<String>,
)
private fun part01(input: List<String>): Int {
    val manuals = input.parseInput()
    
    return manuals.map { manual ->
        val queue = ArrayDeque<Q>()
        val buttons = manual.buttons.values

        buttons.forEach { button ->
            queue.add(Q(manual.lightState, button.toString() to button, 1, listOf(button.toString())))
        }
        while (queue.isNotEmpty()) {
            val q = queue.removeFirst()
            val newLightState = q.lightState.toggleLight(q.button.second)
            
            if (newLightState == 0) return@map q.step

            buttons
                .forEach { b ->
                    val key = b.toString()
                    if (key == q.button.first) return@forEach
                    queue.add(Q(newLightState, key to b, q.step + 1, q.buttonSoFar + key))
                }
        }
        0
    }.sum()
}

private fun part02(input: List<String>): Long {
    return input.parseInput().sumOf { manual ->
        val keys = manual.buttons.keys
        val objectiveFunction = LinearObjectiveFunction(
            DoubleArray(keys.size) { 1.0 },
            0.0
        )
        val constraints = arrayListOf<LinearConstraint>()
        manual.joltage.forEachIndexed { idx, j ->
            val c = keys.map { if (it.contains("$idx")) 1.0 else 0.0 }.toDoubleArray()
            constraints.add(LinearConstraint(c, Relationship.EQ, j.toDouble()))
        }
        keys.forEachIndexed { idx, _ ->
            val nonNegativeCoefficients = DoubleArray(keys.size)
            nonNegativeCoefficients[idx] = 1.0

            constraints.add(LinearConstraint(nonNegativeCoefficients, Relationship.GEQ, 0.toDouble()))
        }
        
        val answer = SimplexSolver(1e-12, 100)
            .optimize(
                MaxIter(1000),
                objectiveFunction,
                LinearConstraintSet(constraints),
                GoalType.MINIMIZE,
                NonNegativeConstraint(false)
            )
        
        answer.point.sumOf { it.roundToLong() }
    }
}

private fun part02_2(input: List<String>): Long {
    Loader.loadNativeLibraries()

    return input.parseInput().sumOf { manual ->
        val solver = MPSolver.createSolver("SCIP")

        val keys = manual.buttons.keys
        val variables = keys.map {
            solver.makeIntVar(0.0, 200.0, "x$it")
        }

        manual.joltage.forEachIndexed { idx, j ->
            val constraint = solver.makeConstraint(j.toDouble(), j.toDouble())
            keys.forEachIndexed { varIdx, key ->
                if (!key.contains("$idx")) return@forEachIndexed
                constraint.setCoefficient(variables[varIdx], 1.0)
            }
        }

        val objective = solver.objective()
        variables.forEach { objective.setCoefficient(it, 1.0) }
        objective.setMinimization()
        solver.solve()
        objective.value().roundToLong()
    }
}

fun main() {
    Runner.get(::part01)
        .test("day10_test.in", 7)
        .run("day10.in")

    Runner.get(::part02)
        .test("day10_test.in", 33)
        .run("day10.in")

    Runner.get(::part02_2)
        .test("day10_test.in", 33)
        .run("day10.in")
}
