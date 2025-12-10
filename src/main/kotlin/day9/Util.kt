package day9

/**
 * Find the three segments of two potentially overlapping lists.
 */

fun <T> List<T>.overlap (other: List<T>): Triple<List<T>, List<T>, List<T>> {
    val overlap = intersect (other).toList ()
    val first = buildList {
        this@overlap.forEach {
            if (! overlap.contains (it)) {
                add (it)
            }
        }
    }
    val last = buildList {
        other.forEach {
            if (! overlap.contains (it)) {
                add (it)
            }
        }
    }
    return Triple (first, overlap, last)
}

fun main () {
    val a = listOf (1, 2, 3)
    val b = listOf (2, 3, 4)
    println (a.overlap (b))
    return
}

// EOF