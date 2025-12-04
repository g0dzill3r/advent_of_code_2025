package day3

import util.InputUtil

val DAY = 3;
val SAMPLE = false;

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE);
    val banks = Banks.parse (input)

    val process: (Int) -> Long = { pick ->
        var total = 0L
        banks.banks.forEach { bank ->
            total += bank.largest2 (pick)
        }
        println (total)
        total
    }

    // part 1
    process (2)

    // part 2
    process (12)
    return
}

data class Banks (
    val banks: List<Bank>
) {
    companion object {
        fun parse (str: String): Banks {
            return Banks (
                str.split ("\n").map {
                    Bank.parse (it)
                }
            )
        }
    }
}

data class Bank (
    val voltages: List<Int>
) {
    fun largest (): Int {
        val max = voltages.subList(0, voltages.size - 1).max ()
        val i = voltages.indexOf (max)
        val next = voltages.subList (i + 1, voltages.size).max ()
        return "$max$next".toInt()
    }

    fun largest2 (pick: Int): Long {
        var result = 0L
        var start = 0

        for (i in 0 until pick) {
            val possible = voltages.subList (start, voltages.size - pick + i + 1)
            val j = possible.max ()
            start += possible.indexOf (j) + 1
            result = result * 10 + j
        }

        return result
    }

    companion object {
        fun parse (str: String): Bank {
            return Bank (
                buildList {
                    str.toCharArray().forEach {
                        add (it.toString ().toInt())
                    }
                }
            )
        }
    }
}

// EOF