package day10

import com.etherfirma.util.repeat

fun permute2 (count: Int, possible: Int): Sequence<List<Int>> {
    var list = buildList {
        possible.repeat {
            add (0)
        }
    }
    return sequence {
        yield (list)
    }
}

fun main () {
    val els = permute2 (3, 5).iterator ()
    els.forEach {
        println (it)
    }
    return

}

// EOF