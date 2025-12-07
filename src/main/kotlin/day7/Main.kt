package day7

import util.InputUtil


val DAY = 7;
val SAMPLE = false;

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE);
    val puzzle = Puzzle (input)

    // part1

    val part1 = run {
        puzzle.run ()
    }
    println ("part1=$part1")

    // part2

    val part2 = run {
        puzzle.run2 ()
    }
    println ("part2=$part2")
    return
}


class Puzzle (str: String) {
    val data = str.split ("\n").map {
        it.map { Thing.parse (it) }
    }
    val rows = data.size
    val lastRow = rows - 1
    val cols = data[0].size
    val start = findStart ()

    private var beams = listOf (start)

    fun run (): Int {
        var split = 0

        while (beams.isNotEmpty()) {
            split += step ()
        }
        return split
    }

    fun step (): Int {
        val update = mutableListOf<Point> ()
        val already = mutableListOf<Point> ()
        var split = 0

        beams.forEach {
            val next = it.step
            if (isValid (next)) {
                if (get (next) == Thing.SPLIT) {
                    if (! already.contains (next)) {
                        for (el in next.split) {
                            if (isValid (el)) {
                                if (! update.contains (el)) {
                                    update.add (el)
                                }
                            }
                        }
                        split ++
                    }
                    already.add (next)
                } else {
                    update.add(next)
                }
            }
        }
        beams = update
        return split
    }

    private fun reset () {
        beams = listOf (start)
        return
    }

    fun run2 (): Long {
        var beams = listOf (Points (start.row, start.col))

        // Iterate until we're at the last row

        while (beams[0].row != lastRow) {
            val update = mutableListOf<Points> ()
            beams.forEach {
                val next = it.step
                if (get (next) == Thing.SPLIT) {
                    val split = next.split
                    for (el in split) {
                        if (isValid (el)) {
                            val found = update.find {
                                it.row == el.row && it.col == el.col
                            }
                            if (found != null) {
                                found.increment (el.count)
                            } else {
                                update.add (el)
                            }
                        }
                    }
                } else {
                    update.add (next)
                }
            }
            beams = update
        }

        // Return the total number of timelines that resulted

        return beams.foldRight (0L) { i, acc -> acc + i.count }
    }

    private fun findStart (): Point {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if (get (row, col) == Thing.START) {
                     return Point (row, col)
                }
            }
        }
        throw Exception ("Start not found.")
    }

    fun get (point: Point): Thing = get (point.row, point.col)
    fun get (point: Points): Thing = get (point.row, point.col)
    fun get (row: Int, col: Int): Thing {
        return data[row][col]
    }

    private fun isValid (point: Point) = isValid (point.row, point.col)
    private fun isValid (point: Points) = isValid (point.row, point.col)
    private fun isValid (row: Int, col: Int) = row >= 0 && col >= 0 && row < rows && col < cols

    override fun toString (): String {
        return buildString {
            for (row in 0 until rows) {
                for (col in 0 until cols) {
                    if (beams.contains (Point (row, col))) {
                        append ('|')
                    } else {
                        append (get (row, col).c)
                    }
                }
                append ("\n")
            }
        }
    }
}

data class Point (val row: Int, val col: Int) {
    val step: Point get() = Point (row + 1, col)

    val split: List<Point>
        get () = listOf (Point (row, col - 1), Point (row, col + 1))
}

data class Points (val row: Int, val col: Int, var count: Long = 1) {
    fun increment (i: Long) {
        count += i
        return
    }

    val step: Points get() = Points (row + 1, col, count)

    val split: List<Points>
        get () = listOf (Points (row, col - 1, count), Points (row, col + 1, count))
}

enum class Thing (val c: Char) {
    EMPTY ('.'),
    START ('S'),
    SPLIT ('^');

    companion object {
        private val map = entries.associateBy { it.c }
        fun parse (c: Char) = map[c] ?: throw Exception ("Unrecognized symbol: $c")
    }
}