package day8

import util.InputUtil

val DAY = 8;

//val COUNT = 10;
//val SAMPLE = true;

val COUNT = 1000;
val SAMPLE = false;

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)
    val puzzle = Circuits (input)

    // part1

    val part1 = puzzle.part1 ()
    println ("part1=$part1")

    // part2

    val part2 = puzzle.part2 ()
    println ("part2=$part2")

    return
}

data class Circuits (val input: String) {
    val points = input.split ("\n").map {
        val factors = it.split (",").map {
            it.toLong ()
        }
        Point (factors[0], factors[1], factors[2])
    }

    /**
     * Connect the 10 closest junction boxes.
     */

    fun part1 (): Long {
        val process = distances.iterator ()
        var connected = 0
        while (connected < COUNT) {
            val (p0, p1) = process.next ()
            if (! p0.isConnected (p1)) {
                p0.connect (p1)
            }
            connected ++
        }
        val circuits = getCircuits().map { it.size }.sortedDescending()
        return circuits.subList (0, 3).foldRight (1L) { i, acc -> acc * i }
    }

    /**
     * Keep connecting unconnected junction boxes until we have a single,
     * fully-connected circuit.
     */

    fun part2 (): Long {
        val process = distances.iterator ()
        while (process.hasNext ()) {
            val (p0, p1) = process.next ()
            p0.connect (p1)
            if (p0.gather ().size == points.size) {
                return p0.x * p1.x
            }
        }
        return -1L
    }

    fun getCircuits (): Set<Set<Point>> {
        val circuits = mutableSetOf<Set<Point>> ()

        points.forEach {
            val set = buildList {
                add (it)
                addAll (it.gather ())
            }.sortedBy { it.id }.toSet ()
            circuits.add (set)
        }

        return circuits
    }

    /**
     * Calculate the distances between all the points and
     * sort them by increasing distance
     */

    val distances: List<Edge>
        get () {
            val list = buildList {
                for (i in 0 until points.size - 1) {
                    for (j in i + 1 until points.size) {
                        val p0 = points[i]
                        val p1 = points[j]
                        add (Edge (p0, p1, p0.distance (p1)))
                    }
                }
            }
            return list.sortedBy { it.distance }
        }

    fun mermaid () {
        println ("flowchart")
        println ("%% nodes")
        points.forEach {
            println ("  P${it.id}")
        }

        println ("%% edges")
        points.forEach { point->
            point.connections.forEach {
                println ("  P${point.id} --> P${it.id}")
            }
        }
        return
    }
}


data class Edge (val p0: Point, val p1: Point, val distance: Long)

data class Point (val x: Long, val y: Long, val z: Long) {
    val connections = mutableSetOf<Point>()

    val id: String
        get () = "$x-$y-$z"

    fun connect(other: Point) {
        connections.add(other)
        other.connections.add(this)
        return
    }

    fun gather(already: MutableSet<Point> = mutableSetOf()): Set<Point> {
        connections.forEach {
            if (!already.contains(it)) {
                already.add (this)
                already.add (it)
                it.gather(already)
            }
        }
        return already
    }

    fun isConnected(other: Point): Boolean = gather().contains(other)

    fun distance(other: Point): Long {
        return (x - other.x).squared() +
                (y - other.y).squared() +
                (z - other.z).squared()
    }
}

fun Long.squared (): Long = this * this

// EOF