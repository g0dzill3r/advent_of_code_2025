package day12

import java.util.regex.Pattern

class Region (
    val width: Int,
    val height: Int,
    val pieces: List<Int>
) {
    val area = width * height

    override fun toString (): String {
        return String.format ("%dx%d: %s", width, height, pieces)
    }

    companion object {
        val HEADER = Pattern.compile ("^(\\d+)x(\\d+): (\\d+(?: \\d+)*)$")

        fun parse(row: String): Region {
            val matcher = HEADER.matcher (row)
            if (! matcher.matches()) {
                throw IllegalArgumentException ("Invalid input: ${row}}")
            }
            val width = matcher.group(1).toInt()
            val height = matcher.group(2).toInt()
            val pieces = matcher.group (3).split (" ").map { it.toInt() }
            return Region (width, height, pieces)
        }
    }
}

// EOF