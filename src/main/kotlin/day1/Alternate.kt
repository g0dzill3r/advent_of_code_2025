package day1

import util.InputUtil

/**
 * A cleaner OO version of the solution.
 */

fun main () {
    val input = InputUtil.getInput (DAY, SAMPLE);
    val els = input.split ("\n").map { Turn.parse (it) }

    // Part 1

    val safe1 = Safe { cur, done ->
        cur == 0 && done
    }
    safe1.turn (els)
    println (safe1)

    // Part 2

    val safe2 = Safe { cur, done ->
        cur == 0
    }
    safe2.turn (els)
    println (safe2)
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

    fun turn (recs: List<Turn>) {
        recs.forEach { turn (it) }
        return
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