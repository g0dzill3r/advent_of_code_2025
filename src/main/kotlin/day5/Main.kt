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

    val total = acc.foldRight (0L, { i, acc -> acc + i.last - i.start + 1 })
    println (total)
    return
}

data class Dataset (
    val fresh: List<LongRange>,
    val ingredients: List<Long>
) {
    fun isFresh (el: Long): Boolean {
        for (check in fresh) {
            if (check.contains (el)) {
                return true
            }
        }
        return false
    }

    companion object {
        fun parse (str: String): Dataset {
            val (a, b) = str.split ("\n\n")
            val fresh = a.split ("\n").map {
                val (a, b) = it.split("-")
                LongRange (a.toLong (), b.toLong ())
            }
            val ingredients = b.split ("\n").map { it.toLong() }
            return Dataset (fresh, ingredients)
        }
    }
}

// EOF
