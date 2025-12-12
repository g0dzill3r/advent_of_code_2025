package day12

import util.InputUtil

val DAY = 12
val SAMPLE = false

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)
    val puzzle = Puzzle.parse (input)

    println(puzzle.pieces)

    // part1

    var part1 = 0
    puzzle.regions.forEach { region ->
        var required = 0
        println (region)
        region.pieces.forEachIndexed { i, piece ->
            required += piece * puzzle.pieces[i].stars
        }
        println (region.toString () + " - " +required + " " + region.area)
        if (region.area >= required) {
            part1++
        }
    }
    println (part1)
    return
}

// EOF