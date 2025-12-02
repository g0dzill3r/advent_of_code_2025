package day2

import java.util.regex.Pattern

private val pattern1 = Pattern.compile ("^(\\d+)\\1$")

fun isValid1b (i: Long): Boolean = !pattern1.matcher (i.toString()).matches()

private val pattern2 = Pattern.compile ("^(\\d+)\\1+$")

fun isValid2b (i: Long): Boolean = !pattern2.matcher (i.toString()).matches()

// EOF
