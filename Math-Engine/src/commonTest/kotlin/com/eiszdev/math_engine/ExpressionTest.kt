package com.eiszdev.math_engine

import com.eiszdev.math_engine.algebra.Equation
import com.eiszdev.math_engine.algebra.Add
import com.eiszdev.math_engine.algebra.Mul
import com.eiszdev.math_engine.algebra.Neg
import com.eiszdev.math_engine.algebra.Num
import com.eiszdev.math_engine.algebra.Token
import com.eiszdev.math_engine.algebra.Var
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.DefaultAsserter.assertNotEquals
import kotlin.test.Test

class ExpressionTest {
    @Test
    fun `pretty prints equation`() {
        val eq = Equation(
            Add(
                Mul(Num(2.0), Var("x")),
                Num(4.0)
            ),
            Num(10.0)
        )

        val result = eq.pretty()
        assertEquals(null, "2.0 * x + 4.0 = 10.0", result)
    }

    @Test
    fun `pretty prints expression with precedence`() {
        val expr = Mul(
            Add(Var("x"), Num(1.0)),
            Num(3.0)
        )

        assertEquals(null, "(x + 1.0) * 3.0", expr.pretty())
    }

    @Test
    fun `pretty prints expression`() {
        val expr = Add(
            Var("x"),
            Mul(Num(1.0), Num(3.0))
        )

        assertEquals(null, "x + 1.0 * 3.0", expr.pretty())

    }

    @Test
    fun `pretty prints negative coefficient`() {
        val expr = Equation(
            left = Add(left = Neg(Mul(left = Num(2.0), right = Var("x"))), right = Num(2.0)),
            right = Num(5.0)
        )

        kotlin.test.assertEquals("-(2.0 * x) + 2.0 = 5.0", expr.pretty())
    }

    @Test
    fun `expressions with same structure are equal`() {
        val a = Add(Num(1.0), Var("x"))
        val b = Add(Num(1.0), Var("x"))

        assertEquals(null, a, b)
    }

    @Test
    fun `nested expressions are equal`() {
        val a = Add(
            Mul(Num(2.0), Var("x")),
            Num(4.0)
        )

        val b = Add(
            Mul(Num(2.0), Var("x")),
            Num(4.0)
        )

        assertEquals(null, a, b)
    }

    @Test
    fun `different structure is not equal`() {
        val a = Add(Mul(Num(2.0), Var("x")), Num(4.0))
        val b = Mul(Num(2.0), Add(Var("x"), Num(4.0)))

        assertEquals(null, "2.0 * x + 4.0", a.pretty())
        assertEquals(null, "2.0 * (x + 4.0)", b.pretty())
        assertNotEquals(null, a, b)
    }

    @Test
    fun `pretty prints nested expression`() {
        val expr = Mul(
            Add(Var("x"), Num(1.0)),
            Num(3.0)
        )

        assertEquals(null, "(x + 1.0) * 3.0", expr.pretty())
    }

    @Test
    fun `equation preserves left and right`() {
        val eq = Equation(Var("x"), Num(5.0))

        assertEquals(null, "x = 5.0", eq.pretty())
    }

    @Test
    fun `expressions are immutable`() {
        val original = Add(Var("x"), Num(1.0))
        val copy = original.copy(right = Num(2.0))

        assertEquals(null, "x + 1.0", original.pretty())
        assertEquals(null, "x + 2.0", copy.pretty())
    }

}
