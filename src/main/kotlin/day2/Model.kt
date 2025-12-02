package day2

enum class X {
    A, B;

    companion object {
        fun parse (str: String): X {
            return when (str) {
                "A" -> X.A
                "B" -> X.B
                else -> throw IllegalArgumentException("Invalid input: $str")
            }
        }
    }
}

fun main () {
    println (X.parse ("A"))
    return
}

// EOF