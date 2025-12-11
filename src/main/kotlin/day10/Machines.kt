package day10

import java.util.regex.Pattern
import kotlin.math.min

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
    var state: MutableList<Boolean> = expected.map { false }.toMutableList (),
    var voltages: MutableList<Int> = joltages.map { 0 }.toMutableList()
) {
    private fun reset () {
        state = expected.map { false }.toMutableList()
        return
    }

    private fun resetJoltages () {
        voltages = joltages.map { 0 }.toMutableList()
        return
    }

    private fun press (button: Int, times: Int) {
        if (times % 2 == 1) {
            buttons[button].forEach {
                state[it] = ! state[it]
            }
        }
        return
    }

    private fun pressJoltages (button: Int, times: Int) {
        if (times > 0) {
            buttons[button].forEach {
                voltages [it] += times
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

    /**
     * Calculate the minimum number of button presses to yield the expected joltage setting.
     */

    fun minJoltages (): Int {

        // Let's figure out the minimum number of buttons that need to be pressed

        val minPresses = joltages.min ()
        println ("minPresses=$minPresses")

        // Let's figure out the maximum number of times each button could be pressed

        val maxPresses = buildList {
            buttons.forEachIndexed { i, buttons ->
                val max = buttons.map {
                    joltages [it]
                }
                add (max.max ())
            }
        }
        println ("maxPresses=$maxPresses")

        var min = Integer.MAX_VALUE
        val possible = permute2 (maxPresses, minPresses)
        possible.forEach { buttons ->
            resetJoltages ()
            buttons.forEachIndexed { i, times ->
                pressJoltages (i, times)
            }
            if (joltages == voltages) {
                min = min (min, buttons.sum ())
            }
        }
        return min
    }

    /**
     * Figure out the possible number of times that each button could be pressed without
     * exceeding one of the joltages.
     */

    val maxPresses: List<Int>
        get () {
            return buttons.mapIndexed { i, buttons ->
                val min = buttons.map {
                    joltages[it]
                }
                min.min()
            }
        }

    /**
     * Solver for part2.
     */

//    fun solver (match: MutableList<List<Int>> = mutableListOf ()): List<List<Int>> {
//
//    }

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
                }.sortedByDescending { it.size }
            }
            val joltages = matcher.group (3).let {
                it.substring (1, it.length - 1).split (",").map { it.toInt () }
            }
            return Machine (buttons, presses, joltages)
        }
    }
}

fun main () {
//    val machine = Machine.parse ("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}")
    val machine = Machine.parse ("[.#...#...#] (3,5,7,8) (0,3,4) (0,1,2,3,4,7,9) (0,1,3,4,6,7,9) (1,4,5,6,8) (0,1,6,9) (0,2,3,4,5,7,8,9) (1,2,5,6,7,9) (0,2,3,5,6,7,8,9) (0,2,3,4,5,7,8) {46,36,54,60,41,78,47,75,59,57}")
    println ("""
        buttons=${machine.buttons}
        joltages=${machine.joltages}
    """.trimIndent())
    println (machine.maxPresses)
    return
}

// EOF