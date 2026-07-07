package com.eiszdev.math_engine.algebra

import com.eiszdev.math_engine.utils.wrap

sealed interface Expression {
    fun pretty(): String
    fun precedence(): Int
}

data class Num(val value: Double) : Expression {
    override fun pretty() = value.toString()
    override fun precedence() = 3
}
data class Var(val name: String) : Expression {
    override fun pretty() = name
    override fun precedence() = 3
}

data class Add(val left: Expression, val right: Expression) : Expression {
    override fun pretty() = "${this.wrap(left)} + ${this.wrap(right)}"
    override fun precedence() = 1
}
data class Sub(val left: Expression, val right: Expression) : Expression {
    override fun pretty() = "${this.wrap(left)} - ${this.wrap(right)}"
    override fun precedence() = 1
}
data class Mul(val left: Expression, val right: Expression) : Expression {
    override fun pretty() = "${this.wrap(left)} * ${this.wrap(right)}"
    override fun precedence() = 2
}
data class Div(val left: Expression, val right: Expression) : Expression {
    override fun pretty() = "${this.wrap(left)} / ${this.wrap(right)}"
    override fun precedence() = 2
}
data class Neg(val expr: Expression) : Expression {
    override fun pretty() = if (expr is Num || expr is Var) "-${expr.pretty()}" else "-(${expr.pretty()})"
    override fun precedence() = 3
}
