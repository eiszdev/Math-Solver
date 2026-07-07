package com.eiszdev.math_engine.algebra

data class Equation(
    val left: Expression,
    val right: Expression
) {
    fun pretty() = "${left.pretty()} = ${right.pretty()}"
}
