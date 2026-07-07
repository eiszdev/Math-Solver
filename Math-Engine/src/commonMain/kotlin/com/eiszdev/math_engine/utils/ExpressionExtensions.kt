package com.eiszdev.math_engine.utils

import com.eiszdev.math_engine.algebra.Add
import com.eiszdev.math_engine.algebra.Div
import com.eiszdev.math_engine.algebra.Expression
import com.eiszdev.math_engine.algebra.Linear
import com.eiszdev.math_engine.algebra.Mul
import com.eiszdev.math_engine.algebra.Neg
import com.eiszdev.math_engine.algebra.Num
import com.eiszdev.math_engine.algebra.Sub
import com.eiszdev.math_engine.algebra.Var

fun Expression.wrap(child: Expression): String =
    if (child.precedence() < this.precedence())
        "(${child.pretty()})"
    else
        child.pretty()

fun Expression.toLinear(variable: String): Linear =
    when (this) {
        is Num -> Linear(0.0, value)
        is Var ->
            if (name == variable) Linear(1.0, 0.0)
            else error("Unknown variable $name")

        is Add -> {
            val a = left.toLinear(variable)
            val b = right.toLinear(variable)
            Linear(a.coeff + b.coeff, a.constant + b.constant)
        }

        is Sub -> {
            val a = left.toLinear(variable)
            val b = right.toLinear(variable)
            Linear(a.coeff - b.coeff, a.constant - b.constant)
        }

        is Mul -> {
            val a = left.toLinear(variable)
            val b = right.toLinear(variable)

            when {
                a.coeff != 0.0 && b.coeff != 0.0 ->
                    error("Non-linear term: x*x")

                a.coeff != 0.0 ->
                    Linear(a.coeff * b.constant, a.constant * b.constant)

                b.coeff != 0.0 ->
                    Linear(b.coeff * a.constant, b.constant * a.constant)

                else ->
                    Linear(0.0, a.constant * b.constant)
            }
        }

        is Div -> {
            val a = left.toLinear(variable)
            val b = right.toLinear(variable)

            if (b.coeff != 0.0) error("Division by variable")

            Linear(a.coeff / b.constant, a.constant / b.constant)
        }

        is Neg -> {
            val a = expr.toLinear(variable)
            Linear(-a.coeff, -a.constant)
        }
    }
