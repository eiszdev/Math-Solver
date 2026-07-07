package com.eiszdev.math_engine.algebra

import com.eiszdev.math_engine.utils.toLinear

class EquationSolver {

    fun solve(eq: Equation, variable: String): Double {
        val left = eq.left.toLinear(variable)
        val right = eq.right.toLinear(variable)

        val a = left.coeff - right.coeff
        val b = left.constant - right.constant

        if (a == 0.0) error("No or infinite solutions")

        return -b / a
    }

    fun solveFrom(input: String, variable: String = "x"): Double {
        val tokens = Tokenizer().tokenize(input)
        val parser = Parser(tokens)
        val eq = parser.parseEquation()
        return solve(eq, variable)
    }

}
