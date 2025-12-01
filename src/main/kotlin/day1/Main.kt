package day1

import util.InputUtil
import kotlin.math.sign

val DAY = 1;
val SAMPLE = false;

fun main () {
    val input = InputUtil.getInput (DAY, SAMPLE);
    val turns = input.split ("\n").map { Turn.parse (it) }

    // Part 1

    var cur = 50
    var count = 0

    turns.forEach {
        when (it.direction) {
            Direction.LEFT -> {
                cur -= it.count
                if (cur < 0) {
                    cur %= 100
                }
            }

            Direction.RIGHT -> {
                cur += it.count
                if (cur > 99) {
                    cur %= 100
                }
            }
        }
        if (cur == 0) {
            count ++
        }
    }
    println ("part1=$count")

    // Part 2

    cur = 50
    count = 0

    turns.forEach {
        for (i in 0 until it.count) {
            when (it.direction) {
                Direction.LEFT -> {
                    cur --
                    if (cur < 0) {
                        cur += 100
                    }
                }

                Direction.RIGHT -> {
                    cur ++
                    if (cur > 0) {
                        cur %= 100
                    }
                }
            }
            if (cur == 0) {
                count ++
            }
        }
    }
    println ("part2=$count")
    return
}

enum class Direction {
    LEFT, RIGHT;

    companion object {
        fun parse (c: Char): Direction {
            return when (c) {
                'L' -> LEFT
                'R' -> RIGHT
                else -> throw IllegalArgumentException("Invalid input")
            }
        }
    }
}

data class Turn (
    val direction: Direction,
    val count: Int
) {
    companion object {
        fun parse (str: String): Turn {
            return Turn (
                Direction.parse (str[0]),
                str.substring (1, str.length).toInt ())
        }
    }
}

fun Int.sign (): Int = when {
    this < 0 -> -1
    this > 0 -> 1
    else -> 0
}

// EOF