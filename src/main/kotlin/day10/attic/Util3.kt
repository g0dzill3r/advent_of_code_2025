package day10.attic

import day10.Machine
import org.ojalgo.netio.BasicLogger
import org.ojalgo.optimisation.ExpressionsBasedModel
import org.ojalgo.optimisation.Optimisation


/**
 * A third attempt at solving part2 in a performance fashion.
 *
 * https://online-optimizer.appspot.com/?model=builtin:default.mod
 *
 * var x0 >= 0;
 * var x1 >= 0;
 * var x2 >= 0;
 * var x3 >= 0;
 * var x4 >= 0;
 * var x5 >= 0;
 *
 * minimize z:     x0+x1+x2+x3+x4+x5;
 *
 * subject to c0: x4+x5    == 3;
 * subject to c1: x1+x5    == 5;
 * subject to c2: x2+x3+x4 == 4;
 * subject to c3: x0+x1+x3 == 7;
 *
 * end;
 *
 */

fun main () {
    val machine =
        Machine.parse("[.#...#...#] (3,5,7,8) (0,3,4) (0,1,2,3,4,7,9) (0,1,3,4,6,7,9) (1,4,5,6,8) (0,1,6,9) (0,2,3,4,5,7,8,9) (1,2,5,6,7,9) (0,2,3,5,6,7,8,9) (0,2,3,4,5,7,8) {46,36,54,60,41,78,47,75,59,57}")

    // Try a solver

    val model = ExpressionsBasedModel()
    val x0 = model.addVariable("x0").lower (0).weight (1)
    val x1 = model.addVariable("x1").lower (0).weight (1)
    val x2 = model.addVariable("x2").lower (0).weight (1)
    val x3 = model.addVariable("x3").lower (0).weight (1)
    val x4 = model.addVariable("x4").lower (0).weight (1)
    val x5 = model.addVariable("x5").lower (0).weight (1)


    println (model.variables)

    // The objective function Z = 2*X1 + 3*X2 is defined by the variable weights

    val c0 = model.addExpression("c0")
        .set (x4, 1)
        .set (x5, 1)
        .level (3)

    val c1 = model.addExpression("c1")
        .set (x1, 1)
        .set (x5, 1)
        .level (5)

    val c2 = model.addExpression("c2")
        .set (x2, 1)
        .set (x3, 1)
        .set (x4, 1)
        .level (4)

    val c3 = model.addExpression("c4")
        .set (x0, 1)
        .set (x1, 1)
        .set (x3, 1)
        .level (7)

    val result = model.minimise()

    BasicLogger.debug (model.getVariables ())
    println ("EX")
    BasicLogger.debug (model.getExpressions())
    BasicLogger.debug (model)
    BasicLogger.debug (result)

    // 5. Print the results
    if (result.state == Optimisation.State.OPTIMAL) {
        println("Solution found:")
        println("Objective Value (Max Profit): " + result.value)
        println("X1: " + result[0])
        println("X2: " + result[1])
    } else {
        println("Problem state: " + result.state)
    }


    return
}

// EOF