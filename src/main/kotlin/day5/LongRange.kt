package day5

infix fun LongRange.overlaps (other: LongRange): Boolean {
    return when {
        first > other.last -> false
        last < other.first -> false
        first in other -> true
        last in other -> true
        other.first in this -> true
        other.last in this -> true
        else -> false
    }
}

private fun min (a: Long, b: Long) = if (a < b) a else b
private fun max (a: Long, b: Long) = if (a > b) a else b

infix fun LongRange.union (other: LongRange): LongRange {
    if (this overlaps other) {
        return min (first, other.first) .. max (last, other.last)
    } else {
        throw Exception ("Ranges cannot be merged: $this !overlaps $other")
    }
}

fun main() {
    fun check (a: LongRange, b: LongRange, expect: Boolean) {
        val found = a overlaps b
        if (found != expect) {
            throw Exception ("expected $a overlaps $b to be $found")
        }
        return
    }

    // Test overlaps

    check (0..1L, 2L..3L, false)
    check (2L..3L, 0L..1L, false)
    check (0L..10L, 1L..2L, true)
    check (1L..2L, 0L..10L, true)
    check (0L..5L, 3L..8L, true)
    check (6L..10L, 5L..8L, true)

    // Test union

    println (0L..5L union 3L..10L)
    return
}

// EOF