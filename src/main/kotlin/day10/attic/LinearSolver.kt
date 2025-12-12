package day10.attic

import day10.Machine
import org.ojalgo.concurrent.Parallelism
import org.ojalgo.optimisation.ExpressionsBasedModel
import org.ojalgo.optimisation.Optimisation
import org.ojalgo.optimisation.integer.IntegerSolver
import org.ojalgo.optimisation.integer.IntegerStrategy
import org.ojalgo.optimisation.integer.NodeKey.MIN_OBJECTIVE
import org.ojalgo.type.context.NumberContext


/**
 * Use linear programming (simplex solver) to solve for the lowest number of clicks
 * to achieve the desired result.
 */

fun Machine.simplex (): Int {
    val model = ExpressionsBasedModel()
    model.options.integer(IntegerStrategy.DEFAULT);

    val configuredStrategy: IntegerStrategy =
        IntegerStrategy.DEFAULT.withParallelism(Parallelism.FOUR).withPriorityDefinitions(
            MIN_OBJECTIVE
        )
    model.options.integer(configuredStrategy)
    model.options.progress(IntegerSolver::class.java)
    model.options.integer(IntegerStrategy.DEFAULT.withGapTolerance(NumberContext.of(0)));

    // Set the objective function

    val variables = buttons.indices.map { i ->
        model.addVariable ("x$i").lower (0).weight (1)
    }

    // Set the constraints

    for (i in joltages.indices) {
        val c = model.addExpression("c$i").level (joltages[i])
        buttons.forEachIndexed { button, j ->
            if (j.contains (i)) {
                c.set (variables[button], 1)
            }
        }
    }

    val result = model.minimise()
    val clicks = buildList {
        buttons.indices.map { i ->
            add (result [i.toLong ()].toInt ())
        }
    }

//    BasicLogger.debug (result)
//    println (clicks)
    verify (clicks)

    return when (result.state) {
        Optimisation.State.OPTIMAL,
        Optimisation.State.DISTINCT,
            -> {
            result.value.toInt()
        }
        else -> throw IllegalArgumentException ("Unexpected state: $result")
    }
}

fun Machine.verify (clicks: List<Int>) {
    val voltages = MutableList (joltages.size) { 0 }
    clicks.forEachIndexed { index, times ->
        buttons[index].forEach { j ->
            voltages[j] += times
        }
    }
    if (voltages!= joltages) {
        println ("$voltages != $joltages")
//        throw IllegalStateException ()
    }

    return
}

fun main () {
//    val machine = Machine.parse ("[.#...#...#] (3,5,7,8) (0,3,4) (0,1,2,3,4,7,9) (0,1,3,4,6,7,9) (1,4,5,6,8) (0,1,6,9) (0,2,3,4,5,7,8,9) (1,2,5,6,7,9) (0,2,3,5,6,7,8,9) (0,2,3,4,5,7,8) {46,36,54,60,41,78,47,75,59,57}")
//    val machine = Machine.parse ("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}")
//    val machine = Machine.parse ("[.###.] (1,2,4) (0,2,4) (0,1,3,4) (2,4) {23,23,40,3,43}")
    val machine =
        Machine.parse("[...####.#.] (1,3,5,9) (0,1,2,3,5,6,7,8,9) (1,2,4,8) (4,6,8,9) (1,2,7) (1,2,7,9) (2,6) (9) (0,2,3,6,7,8,9) (0,1,4,5,6,7) (2,3,5,6,7,8) (2,3,4,5,7,8,9) (4,5,8) {18,56,78,48,75,58,55,52,83,73}")
    println (machine.simplex ())
    return
}

// EOF