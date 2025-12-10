package day9

import util.InputUtil

val DAY = 9
val SAMPLE = false

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)
    val puzzle = Puzzle (input)

    // part1

    val part1 = puzzle.part1 ()
    println ("part1=$part1")
    return
}

class Puzzle (val input: String) {
    val points = input.split ("\n").map {
        val (a, b) = it.split(",").map { it.toLong () }
        Point (a, b)
    }.sortedWith { a, b ->
        when {
            a.row == b.row -> (a.col - b.col).toInt ()
            else -> (a.row - b.row).toInt ()
        }
    }

    fun part1 (): Long {
        return rectangles().map { (a, b) -> a.rectangle (b).size }.max()
    }

    fun rectangles (): List<Pair<Point, Point>> {
        return buildList {
            for (i in 0 until points.size - 1) {
                val corner = points[i]
                for (j in i + 1 until points.size) {
                    val other = points[j]
                    if (other.row >= corner.row && other.col >= corner.col) {
                        add (corner to other)
                    }
                }
            }
        }
    }
}

data class Point (val row: Long, val col: Long) {
    fun rectangle (other: Point): Rectangle = Rectangle (this, other)
}

data class Rectangle (val start: Point, val end: Point) {
    val size: Long
        get () = (end.col - start.col + 1) * (end.row - start.row + 1)

    fun contains (other: Point): Boolean {
        return other.row > start.row
                && other.row < end.row
                && other.col > start.col
                && other.col < end.col
    }
}

// EOF