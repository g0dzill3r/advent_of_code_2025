package day9

import util.InputUtil
import kotlin.math.max

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)
    val puzzle = Grid (input)

    // part2

    val part2 = puzzle.part2 ()
    println ("part2=$part2")
    return
}

class Grid (input: String) {
    val points = input.split("\n").map {
        val (col, row) = it.split(",").map { it.toLong () }
        Point (row, col)
    }
    val lines = buildList {
        for (i in 0 until points.size - 1) {
            add (Line (points[i], points[i + 1]))
        }
        add (Line (points[points.size - 1], points[0]))
    }
    val horizontal = lines.filter {
        it.p0.row == it.p1.row
    }.map {
        if (it.p0.col > it.p1.col) {
            Line (it.p1, it.p0)
        } else {
            it
        }
    }
    val vertical = lines.filter {
        it.p0.col == it.p1.col
    }.map {
        if (it.p0.row > it.p1.row) {
            Line (it.p1, it.p0)
        } else {
            it
        }
    }
    val maxRow = points.map { it.row }.max()
    val maxCol = points.map { it.col }.max()

    fun part2 (): Long {
        var res = -1L
        val rectangles = rectangles ()
        println (rectangles.map { it.size }.sorted ())
        rectangles.forEachIndexed { i, rect ->
            if (rect.size > res) {
                println ("$i of ${rectangles.size}: ${rect}")
                if (isComplete (rect)) {
                    res = max (res, rect.size)
                }
            }
        }
        return res
    }

    fun isComplete (rect: Rectangle): Boolean {
        for (row in rect.start.row .. rect.end.row) {
            for (col in rect.start.col .. rect.end.col) {
                if (!isInternal(row, col)) {
                    return false
                }
            }
        }
//        for (row in rect.start.row .. rect.end.row) {
//            if (! isInternal(row, rect.end.col)) {
//                return false
//            }
//        }
//        for (col in rect.start.col .. rect.end.col) {
//            if (! isInternal(rect.start.row, col)) {
//                return false
//            }
//            if (! isInternal(rect.end.row, col)) {
//                return false
//            }
//        }

        return true
    }

    fun rectangles (): List<Rectangle> {
        val local = points.sortedWith { a, b ->
            when {
                a.row == b.row -> (a.col - b.col).toInt ()
                else -> (a.row - b.row).toInt ()
            }
        }
        return buildList {
            for (i in 0 until local.size - 1) {
                val corner = local[i]
                for (j in i + 1 until local.size) {
                    val other = local[j]
                    if (other.row >= corner.row && other.col >= corner.col) {
                        add (Rectangle (corner, other))
                    }
                }
            }
        }
    }

    fun isInternal (row: Long, col: Long): Boolean {
        val maybeH = horizontal.filter {
            it.p0.row == row && col >= it.p0.col && col <= it.p1.col
        }
        if (maybeH.isNotEmpty()) {
            return true
        } else {
            val maybeV = vertical.filter {
                it.p0.col == col && row >= it.p0.row && row <= it.p1.row
            }
            if (maybeV.isNotEmpty()) {
                return true
            } else {
                val intersect = vertical.filter {
                    row >= it.p0.row && row < it.p1.row
                }.filter {
                    col <= it.p0.col
                }
                val count = intersect.count ()
                return count % 2 == 1
            }
        }
    }

    override fun toString (): String {
        return buildString {
            append ("  01234567890\n")
            for (row in 0 .. maxRow + 1) {
                append (String.format ("%-2d", row))
                for (col in 0 .. maxCol + 1) {
                    if (isInternal(row, col)) {
                        append ('#')
                    } else {
                        append('.')
                    }
                }
                append ("\n")
            }
        }
    }
}

data class Line (val p0: Point, val p1: Point)

// EOF