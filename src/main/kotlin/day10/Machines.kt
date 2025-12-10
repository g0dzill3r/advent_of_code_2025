package day10

import java.util.regex.Pattern

/**
 * The list of machines in the puzzle.
 */

data class Machines (val machines: List<Machine>) {
    companion object {
        fun parse (input: String): Machines {
            val machines = input.split ("\n").map {
                Machine.parse (it)
            }
            return Machines (machines)
        }
    }
}

/**
 * A single machine.
 */

data class Machine (
    val expected: List<Boolean>,
    val buttons: List<List<Int>>,
    val joltages: List<Int>,
    var state: MutableList<Boolean> = expected.map { false }.toMutableList ()
) {
    fun reset () {
        state = expected.map { false }.toMutableList()
        return
    }

    fun press (button: Int, times: Int) {
        if (times % 2 == 1) {
            buttons[button].forEach {
                state[it] = ! state[it]
            }
        }
        return
    }

    fun minPresses (): Int {
        var count = 0
        while (true) {
            val possible = permute (count, buttons.size)
            possible.forEach { buttons ->
                reset ()
                buttons.forEachIndexed { i, times ->
                    press (i, times)
                }
                if (state == expected) {
                    return count
                }
            }
            count ++
        }
        // NOT REACHED
    }

    companion object {
        private val pattern = Pattern.compile ("(\\[[\\.#]+\\]) (\\(.+\\)) (\\{\\d+(?:,\\d+)*\\})")

        fun parse (input: String): Machine {
            val matcher = pattern.matcher(input)
            if (! matcher.matches()) {
                throw IllegalArgumentException ("Invalid input.")
            }
            val buttons = matcher.group (1).let {
                it.substring (1, it.length - 1).map {
                    when (it) {
                        '.' -> false
                        '#' -> true
                        else -> throw IllegalArgumentException ("Invalid input: $it")
                    }
                }
            }
            val presses = matcher.group (2).let {
                it.split (' ').map {
                    it.substring (1, it.length - 1).split ( ',').map {
                        it.toInt ()
                    }
                }
            }
            val joltages = matcher.group (3).let {
                it.substring (1, it.length - 1).split (",").map { it.toInt () }
            }
            return Machine (buttons, presses, joltages)
        }
    }
}

fun main () {
    println (Machine.parse ("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}"))
//    println (Machine.parse ("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}"))
}

// EOF