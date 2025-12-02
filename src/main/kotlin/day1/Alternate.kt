package day1

import util.InputUtil

/**
 * A cleaner OO version of the solution.
 */

fun main () {
    val input = InputUtil.getInput (DAY, SAMPLE);
    val els = input.split ("\n").map { Turn.parse (it) }

    // Part 1

    val part1 = run {
        val safe = Safe { cur, done ->
            cur == 0 && done
        }
        safe.turn (els)
    }
    println (part1)

    // Part 2

    val part2 = run {
        val safe = Safe { cur, _ ->
            cur == 0
        }
        safe.turn (els)
    }
    println (part2)
    return
}

/**
 * The safe object.
 */

class Safe (
    val func: (Int, Boolean) -> Boolean
) {
    var cur: Int = 50
    var count: Int = 0

    fun turn (recs: List<Turn>): Int {
        recs.forEach { turn (it) }
        return count
    }

    fun turn (rec: Turn) {
        for (i in 0 until rec.count) {
            when (rec.direction) {
                Direction.LEFT -> cur --
                Direction.RIGHT -> cur++
            }
            if (cur < 0) {
                cur += POSITIONS
            } else if (cur >= POSITIONS) {
                cur -= POSITIONS
            }
            if (func (cur, i == rec.count - 1)) {
                count ++
            }
        }
        return
    }

    override fun toString () = "count=$count"

    companion object {
        val POSITIONS = 100
    }
}

// EOF