package com.eiszdev.math_engine

import com.eiszdev.math_engine.algebra.Equation
import com.eiszdev.math_engine.algebra.EquationSolver
import com.eiszdev.math_engine.algebra.Parser
import com.eiszdev.math_engine.algebra.Tokenizer

object MathEngine {
    fun solve(input: String, variable: String = "x"): SolveResult {
        return try {
            val tokens = Tokenizer().tokenize(input)
            val equation = Parser(tokens).parseEquation()
            val answer = EquationSolver().solve(equation, variable)
            SolveResult.Success(answer, variable, equation)
        } catch (exception: MathEngineException) {
            SolveResult.Failure(exception.error)
        }
    }
}

sealed interface SolveResult {
    data class Success(
        val answer: Double,
        val variable: String,
        val equation: Equation
    ) : SolveResult

    data class Failure(
        val error: MathError
    ) : SolveResult
}

sealed interface MathError {
    data class ParseError(val message: String) : MathError
    data class InvalidNumber(val value: String) : MathError
    data class UnknownVariable(val name: String) : MathError
    data object NonLinearTerm : MathError
    data object DivisionByVariable : MathError
    data object DivisionByZero : MathError
    data object NoSolution : MathError
    data object InfiniteSolutions : MathError
}

internal class MathEngineException(
    val error: MathError
) : RuntimeException(error.toString())
