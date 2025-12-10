package day10

import com.etherfirma.util.repeat

/**
 * Generates all possible permutations of a vector (with limits) and
 * maintains a minimum sum for each generated vector.
 */

fun permute2 (limits: List<Int>, min: Int): Sequence<List<Int>> {
    return sequence {
        permute (limits, min)
    }
}

private suspend fun SequenceScope<List<Int>>.permute (limits: List<Int>, min: Int, acc: List<Int> = listOf ()) {
    if (limits.isEmpty()) {
        if (acc.sum () >= min) {
            yield (acc)
        }
    } else {
        val limit = limits[0]
        val rest = limits.subList (1, limits.size)
        for (i in 0 .. limit) {
            val add = buildList {
                addAll (acc)
                add (i)
            }
            permute (rest, min, add)
        }
    }
}

fun main () {
    val limits = listOf (3, 2, 3)
    val min = 2
    val els = permute2 (limits, min).iterator ()
    els.forEach {
        println (it)
    }
    return

}

// EOF