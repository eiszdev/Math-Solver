package com.eiszdev.math_engine

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MathEngineTest {
    @Test
    fun `solve returns success result`() {
        val result = MathEngine.solve("2x + 1 = 5")

        assertTrue(result is SolveResult.Success)
        assertEquals(2.0, result.answer)
        assertEquals("x", result.variable)
    }

    @Test
    fun `solve returns no solution error`() {
        val result = MathEngine.solve("2x + 3 = 2x + 5")

        assertEquals(SolveResult.Failure(MathError.NoSolution), result)
    }

    @Test
    fun `solve returns infinite solutions error`() {
        val result = MathEngine.solve("2x + 3 = 2x + 3")

        assertEquals(SolveResult.Failure(MathError.InfiniteSolutions), result)
    }

    @Test
    fun `solve returns division by zero error`() {
        val result = MathEngine.solve("x / 0 = 1")

        assertEquals(SolveResult.Failure(MathError.DivisionByZero), result)
    }

    @Test
    fun `solve returns unknown variable error`() {
        val result = MathEngine.solve("y + 1 = 2")

        assertEquals(SolveResult.Failure(MathError.UnknownVariable("y")), result)
    }

    @Test
    fun `solve returns non linear term error`() {
        val result = MathEngine.solve("x * x = 4")

        assertEquals(SolveResult.Failure(MathError.NonLinearTerm), result)
    }

    @Test
    fun `solve returns parse error`() {
        val result = MathEngine.solve("x + = 2")

        assertTrue(result is SolveResult.Failure)
        assertTrue(result.error is MathError.ParseError)
    }

    @Test
    fun `solve returns invalid number error`() {
        val result = MathEngine.solve("1..2x = 4")

        assertEquals(SolveResult.Failure(MathError.InvalidNumber("1..2")), result)
    }
}
