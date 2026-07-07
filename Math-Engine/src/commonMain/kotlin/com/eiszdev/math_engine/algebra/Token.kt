package com.eiszdev.math_engine.algebra

sealed interface Token {
    data class Number(val value: Double) : Token
    data class Ident(val name: String) : Token
    data class Op(val char: Char) : Token
    data object LParen : Token
    data object RParen : Token
}
