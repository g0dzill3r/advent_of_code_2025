package day4

import util.InputUtil

val DAY = 4;
val SAMPLE = false;

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE);
    val playfield = Playfield.parse (input)

    // part 1

    println (playfield.accessible.size)

    // part 2

    var count = 0
    while (true) {
        val accessible = playfield.accessible
        if (accessible.isEmpty()) {
            break
        }
        count += accessible.size
        playfield.remove (accessible)
    }
    println (count)
    return
}

enum class Thing (val symbol: Char){
    EMPTY ('.'),
    ROLL ('@');

    companion object {
        fun parse (c: Char): Thing {
            return when (c) {
                EMPTY.symbol -> Thing.EMPTY
                ROLL.symbol -> Thing.ROLL
                else -> throw IllegalArgumentException("Unknown Thing: $c")
            }
        }
    }
}

enum class Direction (val dr: Int, val dc: Int) {
    NW (-1, -1),
    N (-1, 0),
    NE (-1, 1),
    W (0, -1),
    E (0, 1),
    SW (1, -1),
    S (1, 0),
    SE (1, 1)
}

class Playfield (val grid: List<MutableList<Thing>>) {
    val rows = grid.size;
    var cols = grid[0].size

    private fun isValid (row: Int, col: Int): Boolean {
        return row >= 0 && col >= 0 && row < rows && col < cols
    }

    fun get (row: Int, col: Int): Thing {
        return grid[row][col]
    }

    private fun set (row: Int, col: Int, thing: Thing) {
        grid[row][col] = thing
        return
    }

    fun adjacent (row: Int, col: Int): Int {
        var total = 0
        Direction.entries.forEach {
            val row = row + it.dr
            val col = col + it.dc
            if (isValid (row, col) && get (row, col) == Thing.ROLL) {
                total ++
            }
        }
        return total
    }

    fun visit (func: (Int, Int, Thing) -> Unit) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                func (row, col, get (row, col))
            }
        }
    }

    fun remove (rolls: List<Position>) {
        rolls.forEach {
            remove (it)
        }
        return
    }

    fun remove (pos: Position) {
        val (row, col) = pos
        if (get (row, col) != Thing.ROLL) {
            throw Exception ("No roll at ($row, $col).")
        }
        set (row, col, Thing.EMPTY)
        return
    }

    val accessible: List<Position>
        get () {
            return buildList {
                visit { row, col, thing ->
                    if (thing == Thing.ROLL && adjacent (row, col) < 4) {
                        add (Position (row, col))
                    }
                }
            }
        }

    companion object {
        fun parse (str: String): Playfield {
            val rows = str.split ("\n")
            val mapped = rows.map {
                it.map { c ->
                    Thing.parse (c)
                }.toMutableList()
            }
            return Playfield (mapped)
        }
    }
}

data class Position (
    val row: Int,
    val col: Int
)

// EOF
