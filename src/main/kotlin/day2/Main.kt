package day2

import util.InputUtil

val DAY = 2;
val SAMPLE = false;

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE);
    val ranges = input.split (",")
    val els = ranges.map {
        it.split ("-").map {
            it.toLong()
        }
    }

    val process = fun (pairs: List<List<Long>>, func: (Long) -> Boolean ) {
        var total = 0L
        pairs.forEach { (a, b) ->
            for (i in a .. b) {
                if (! func (i)) {
                    total += i
                }
            }
        }
        println (total)
    }

    process (els, ::isValid)
    process (els, ::isValid2)
    return
}



// EOF