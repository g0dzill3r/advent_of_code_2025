package day10

import util.InputUtil

val DAY = 10
val SAMPLE = false

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)
    val puzzle = Machines.parse (input)

    // part1

    val part1 = run {
        var total = 0
        puzzle.machines.forEach {
            total += it.minPresses ()
        }
        total
    }
    println ("part1=$part1")

    return
}

// EOF