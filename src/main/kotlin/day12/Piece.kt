package day12

import java.util.regex.Pattern

class Piece (
    val index: Int,
    val grid: Array<Array<Boolean>>,
    val width: Int = grid[0].size,
    val height: Int = grid.size
) {
    val area = width * height
    val stars by lazy {
        var total = 0
        grid.forEach {
            it.forEach { el ->
                if (el) {
                    total ++
                }
            }
        }
        total
    }

    fun visit (func: (Int, Int, Boolean) -> Unit) {
        for (row in 0 until height) {
            for (col in 0 until width) {
                func (row, col, grid[row][col])
            }
        }
        return
    }

    override fun toString (): String {
        return buildString {
            visit { row, col, i ->
                append (if (i) '#' else '.')
                if (col == width - 1) {
                    append ('\n')
                }
            }
        }
    }

    companion object {
        val HEADER = Pattern.compile ("^(\\d+):$")

        fun parse (rows: List<String>): Piece {
            val matcher = HEADER.matcher (rows[0])
            if (! matcher.matches()) {
                throw IllegalArgumentException ("Invalid input: ${rows[0]}")
            }
            val index = matcher.group(1).toInt()
            val rest = rows.subList (1, rows.size)
            val grid = rest.map {
                it.map { it == '#' }.toTypedArray()
            }.toTypedArray()
            return Piece (index, grid)
        }
    }
}

// EOF