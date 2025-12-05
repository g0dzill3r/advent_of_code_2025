package day5

import util.InputUtil

val DAY = 5;
val SAMPLE = false;

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE);
    val dataset = Dataset.parse (input)

    // Part 1

    val part1 = dataset.ingredients.count {
        dataset.isFresh (it)
    }
    println (part1)

    // Part 2

    val acc = mutableListOf<LongRange> ()
    dataset.fresh.forEach { range ->
        val removed = acc.filter {
            it overlaps range
        }
        if (removed.isNotEmpty ()) {
            acc.removeAll (removed)
            var agg = range
            removed.forEach {
                agg = agg union it
            }
            acc.add (agg)
        } else {
            acc.add (range)
        }
    }

    val part2 = acc.foldRight (0L, { i, acc -> acc + i.last - i.first + 1 })
    println (part2)
    return
}

data class Dataset (
    val fresh: List<LongRange>,
    val ingredients: List<Long>
) {
    fun isFresh (el: Long): Boolean {
        return fresh.find {
            it.contains (el)
        } != null
    }

    companion object {
        fun parse (str: String): Dataset {
            val (a, b) = str.split ("\n\n")
            val fresh = a.split ("\n").map {
                val (c, d) = it.split("-")
                LongRange (c.toLong (), d.toLong ())
            }
            val ingredients = b.split ("\n").map { it.toLong() }
            return Dataset (fresh, ingredients)
        }
    }
}

// EOF
