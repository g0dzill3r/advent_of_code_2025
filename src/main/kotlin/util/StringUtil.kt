package util

fun List<String>.toLongs (): List<Long> = map { it.toLong() }
fun List<String>.toInts (): List<Int> = map { it.toInt() }

// EOF