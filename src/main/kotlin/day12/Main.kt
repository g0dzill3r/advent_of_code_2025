package day12

import util.InputUtil

val DAY = 12
val SAMPLE = false

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)
    val puzzle = Puzzle.parse (input)

    // part1

    var part1 = 0
    puzzle.regions.forEach { region ->
        var required = 0
        region.pieces.forEachIndexed { i, piece ->
            required += piece * puzzle.pieces[i].stars
        }
        if (region.area >= required) {
            part1++
        }
    }
    println (part1)
    return
}

// EOF