package day3

fun main () {
    val b = Bank (listOf (2,3,4,2,3,4,2,3,4,2,3,4,2,7,8))
    println (b.largest2(12))
    println (maximize (b.voltages, 12))
    return
}

/**
 * A functional approach to the maximizer.
 */

fun maximize (list: List<Int>, pick: Int, acc: Long = 0L): Long {
    if (pick == 0) {
        return acc
    }
    val max = list.subList (0, list.size - pick + 1).max ()
    return maximize (list.subList (list.indexOf (max) + 1, list.size), pick - 1, acc * 10 + max)
}

// EOF
