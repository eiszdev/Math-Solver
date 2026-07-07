package com.eiszdev.math_engine

import com.eiszdev.math_engine.algebra.EquationSolver
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class EquationSolverTest {

    private val equationSolver = EquationSolver()

    @Test
    fun `x plus constant`() {
        val result = equationSolver.solveFrom("x + 3 = 7")
        assertEquals(4.0, result)
    }

    @Test
    fun `simple multiplication`() {
        val result = equationSolver.solveFrom("2x = 10")
        assertEquals(5.0, result)
    }

    @Test
    fun `both sides have x`() {
        val result = equationSolver.solveFrom("3x - 4 = x + 6")
        assertEquals(5.0, result)
    }

    @Test
    fun `with parentheses`() {
        val result = equationSolver.solveFrom("2(x + 1) = 10")
        assertEquals(4.0, result)
    }

    @Test
    fun `with division`() {
        val result = equationSolver.solveFrom("(3x + 6) / 3 = 10")
        assertEquals(8.0, result)
    }

    @Test
    fun `negative coefficient`() {
        val result = equationSolver.solveFrom("-x + 2 = 5")
        assertEquals(-3.0, result)
    }

    @Test
    fun `negative numeric coefficient`() {
        val result = equationSolver.solveFrom("-2x + 2 = 10")
        assertEquals(-4.0, result)
    }

    @Test
    fun `negative grouped expression`() {
        val result = equationSolver.solveFrom("-(x + 2) = 5")
        assertEquals(-7.0, result)
    }

    @Test
    fun `fraction result`() {
        val result = equationSolver.solveFrom("2x + 1 = 0")
        assertEquals(-0.5, result)
    }

    @Test
    fun `no solution`() {
        assertFails {
            equationSolver.solveFrom("2x + 3 = 2x + 5")
        }
    }
}
