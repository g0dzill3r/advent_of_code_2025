package day6

import util.toLongs
import java.util.regex.Pattern

data class Problems2 (
    val problems: List<Problem>
) {
    companion object {
        fun parse (input: String): Problems2 {
            val rotated = input.rotated
            val groups = group (rotated)
            val list = buildList<Problem> {
                groups.forEach { group ->
                    val op = Operation.parse (group[group.length - 1])
                    val factors = group.substring (0, group.length - 1)
                        .trim ()
                        .split (Pattern.compile ("\\s+"))
                        .toLongs ()
                    add (Problem (factors, op))
                }
            }
            return Problems2 (list)
        }

        private fun group (string: String): List<String> {
            val rows = string.split ("\n").iterator()
            return buildList {
                val list = mutableListOf<String> ()
                while (rows.hasNext ()) {
                    val row = rows.next ()
                    if (row.trim ().isEmpty ()) {
                        add (list.joinToString (""))
                        list.clear ()
                    } else {
                        list.add (row)
                    }
                }
            }
        }
    }
}

val String.rotated: String
    get () {
        val parsed = split ("\n")
        val rows = parsed.size
        val cols = parsed.map { it.length }.max ()
        return buildString {
            for (col in cols - 1 downTo 0) {
                for (row in 0 until rows) {
                    try {
                        append(parsed[row][col])
                    } catch (e: Exception) {
                        append (' ')
                    }
                }
                append ("\n")
            }
        }
    }

// EOF