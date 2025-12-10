package day10


fun zeroList (possible: Int): List<List<Int>> {
    return buildList {
        add (buildList {
            for (i in 0 until possible) {
                add (0)
            }
        })
    }
}

fun permute (count: Int, possible: Int, base: List<List<Int>> = zeroList (possible)): List<List<Int>> {
    return when (count) {
        0 -> base
        else -> {
            val next = buildList {
                base.forEach {
                    for (i in 0 until possible) {
                        val copy = it.toMutableList()
                        for (j in 0 until possible) {
                            if (i == j) {
                                copy [j]++
                            }
                        }
                        add (copy)
                    }
                }
            }
            permute (count - 1, possible, next)
        }
    }
}

fun main () {
    val els = permute (3, 5)
    els.forEach {
        println (it)
    }
    return
}

// EOF