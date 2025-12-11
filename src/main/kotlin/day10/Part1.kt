package day10

import util.InputUtil

val DAY = 10
val SAMPLE = true

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)
    val puzzle = Machines.parse (input)

    // part1

    val part1 = run {
        var total = 0
        puzzle.machines.forEach {
            println (it)
            total += it.minPresses ()
        }
        total
    }
    println ("part1=$part1")

    // part2

//    val part2 = run {
//        var total = 0
//        puzzle.machines.forEachIndexed { index, machine ->
//            println ("Machine $index of ${puzzle.machines.size}")
//            total += machine.minJoltages()
//        }
//        total
//    }
//    println ("part2=$part2")


    return
}

// EOF