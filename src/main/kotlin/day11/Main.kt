package day11

import util.InputUtil

val DAY = 11
val SAMPLE = false

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)
    val puzzle = Puzzle.parse (input)

    // part1

    val part1 = puzzle.find ("you", "out")
    println ("part1=${part1.size}")

    // do they all go to out?

    val goesOut = puzzle.goesOut ()
    when (goesOut.values.toSet ().size) {
        1 -> println ("All go out.")
        2 -> println ("Not all go out")
        else -> throw Exception ()
    }

    // part2

    val a = puzzle.count ("svr", "dac")
    val b = puzzle.count ("svr", "fft")
    val c = puzzle.count ("dac", "fft")
    val d = puzzle.count ("fft", "dac")
    val e = puzzle.count ("fft", "out")
    val f = puzzle.count ("dac", "out")

    val part2 = a * c * e + b * d * f
    println ("part2=$part2")
    return
}

data class Puzzle (val rows: List<Device>) {
    val devices = rows.associateBy { it.name }
    fun get (name: String) = devices[name] as Device

    fun find (from: String, to: String, path: List<String> = listOf (), found: MutableList<List<String>> = mutableListOf ()): List<List<String>> {
        val next = buildList {
            addAll (path)
            add (from)
        }
        val device = get (from)
        device.devices.forEach {
            if (it == to) {
                found.add (path)
            } else {
                find (it, to, next, found)
            }
        }
        return found
    }

    fun goesOut (): MutableMap<String, Boolean> {
        val result = mutableMapOf<String, Boolean> ()
        rows.forEach {
            goesOut (it.name, result)
        }
        return result
    }

    private fun goesOut (from: String, found: MutableMap<String, Boolean>): Boolean {
        var result = false
        if (found.containsKey (from)) {
            return found[from] as Boolean
        }
        val device = get (from)
        if (device.devices.contains ("out")) {
            result = true
        } else {
            device.devices.forEach {
                result = result or goesOut(it, found)
            }
        }
        found[from] = result
        return result
    }

    fun count (from: String, to: String, cache: MutableMap<String, Long> = mutableMapOf ()): Long {
        return if (cache.containsKey (from)) {
            cache[from] as Long
        } else {
            var total = 0L
            val device = get (from)
            device.devices.forEach {
                when (it) {
                    to -> total ++
                    "out" -> Unit
                    else -> total += count (it, to, cache)
                }
            }
            cache[from] = total
            total
        }
    }

    companion object {
        fun parse (input: String): Puzzle {
            val devices = input.split ("\n").map {
                Device.parse (it)
            }
            return Puzzle (devices)
        }
    }

}
data class Device (val name: String, val devices: List<String>) {
    val outputs = mutableListOf<Device> ()

    fun patch (map: Map<String, Device>) {
        devices.forEach {
            outputs.add (map[it] !!)
        }
        return
    }

    companion object {
        fun parse(input: String): Device {
            val (name, rest) = input.split (": ")
            val devices = rest.split (" ")
            return Device (name, devices)
        }
    }
}

// EOF