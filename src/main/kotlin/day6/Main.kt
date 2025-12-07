package day6

import util.InputUtil
import java.util.*
import java.util.regex.Pattern

val DAY = 6;
val SAMPLE = false;

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE);

    // part1

    val part1 = run {
        val problems = Problems.parse (input)
        problems.problems.foldRight (0L) { it, acc ->
            acc + it.value
        }
    }
    println (part1)

    // part2

    val part2 = run {
        val problems = Problems2.parse (input)
        problems.problems.foldRight (0L) { it, acc ->
            acc + it.value
        }
    }
    println (part2)
    return
}

data class Problems (
    val problems: List<Problem>
) {
    companion object {
        fun parse (str: String): Problems {
            val rows = str.split ("\n").map {
                buildList {
                    it.trim().split(Pattern.compile("\\s+")).forEach { col ->
                        add (col)
                    }
                }
            }
            val factors = rows.subList (0, rows.size - 1).map {
                ArrayDeque (it.map { it.toLong () })
            }
            val operations = ArrayDeque<String> (rows[rows.size - 1])

            val problems = buildList {
                while (operations.isNotEmpty()) {
                    val f = buildList<Long> {
                        factors.forEach {
                            add (it.pop ())
                        }
                    }
                    val o = operations.pop ()[0]
                    add (Problem (f, Operation.parse (o)))
                }
            }

            return Problems (problems)
        }
    }
}

enum class Operation {
    ADD, MULTIPLY;

    companion object {
        fun parse (c: Char) = when (c) {
            '+' -> ADD
            '*' -> MULTIPLY
            else -> throw Exception()
        }
    }
}

data class Problem (
    val factors: List<Long>,
    val operation: Operation
) {
    val value: Long
        get() = when (operation) {
            Operation.ADD -> factors.sum()
            Operation.MULTIPLY -> factors.product ()
        }
}

fun List<Long>.product (): Long = foldRight (1L) { i, acc -> acc * i }

// EOF