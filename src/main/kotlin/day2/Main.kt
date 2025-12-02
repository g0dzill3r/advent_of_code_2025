package day2

import util.InputUtil

val DAY = 2;
val SAMPLE = false;

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE);
    val ranges = input.split (",")
    val els = ranges.map {
        it.split ("-").map {
            it.toLong()
        }
    }

    // part 1

    var part1 = 0L
    els.forEach { (a, b) ->
        for (i in a .. b) {
            if (! isValid (i)) {
                part1 += i
            }
        }
    }
    println (part1)

    // part 2

    var part2 = 0L
    els.forEach { (a, b) ->
        for (i in a .. b) {
            if (! isValid2 (i)) {
                part2 += i
            }
        }
    }
    println (part2)
    return
}



// EOF