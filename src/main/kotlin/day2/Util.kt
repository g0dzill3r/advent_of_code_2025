package day2

fun isValid1a (i: Long): Boolean {
    val s = i.toString()
    if (s.length % 2 == 1) {
        return true
    }
    val half = s.substring (0, s.length / 2)
    return s != "$half$half"
}

fun isValid2a (i: Long): Boolean {
    val s = i.toString()
    val max = s.length / 2
    for (j in 0 until max) {
        val frag = s.substring (0, j + 1)
        val repeated = frag.repeat (s.length / frag.length)
        if (s == repeated) {
            return false
        }
    }
    return true
}

fun String.repeat (i: Int): String {
    return buildString {
        for (j in 0 until i) {
            append (this@repeat)
        }
    }
}

// EOF