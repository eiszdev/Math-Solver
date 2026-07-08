package com.eiszdev.math_engine.algebra

import com.eiszdev.math_engine.MathEngineException
import com.eiszdev.math_engine.MathError.InvalidNumber
import com.eiszdev.math_engine.MathError.ParseError
import com.eiszdev.math_engine.algebra.Token.Ident
import com.eiszdev.math_engine.algebra.Token.LParen
import com.eiszdev.math_engine.algebra.Token.Number
import com.eiszdev.math_engine.algebra.Token.Op
import com.eiszdev.math_engine.algebra.Token.RParen

class Tokenizer {

    fun tokenize(input: String): List<Token> =
        insertImplicitMultiplication(tokenizeRaw(input))


    fun tokenizePriv(input: String): List<Token> {
        val tokens = mutableListOf<Token>()
        val currentNumber = StringBuilder()
        val currentIdentifier = StringBuilder()
        for (char in input.trim()) {
            if (char.isDigit() || char == '.') {
                currentNumber.append(char)
            } else {
                if (currentNumber.isNotEmpty()) {
                    val lastToken = if (tokens.size > 0) tokens[tokens.size-1] else null
                    if (lastToken is Ident || lastToken is RParen) {
                        tokens.add(Op('*'))
                    }
                    tokens.add(Number(parseNumber(currentNumber.toString())))
                    currentNumber.clear()
                }
                if (char.isLetter()) {
                    currentIdentifier.append(char)
                } else {
                    if (currentIdentifier.isNotEmpty()) {
                        val lastToken = if (tokens.size > 0) tokens[tokens.size-1] else null
                        if (lastToken is Number || lastToken is RParen) {
                            tokens.add(Op('*'))
                        }
                        tokens.add(Ident(currentIdentifier.toString()))
                        currentIdentifier.clear()
                    }
                    when (char) {
                        '+', '-', '*', '/', '=' -> tokens.add(Op(char))
                        '(' -> tokens.add(LParen)
                        ')' -> tokens.add(RParen)
                    }
                }

            }
        }

        if (currentNumber.isNotEmpty()) {
            tokens.add(Number(parseNumber(currentNumber.toString())))
        }

        if (currentIdentifier.isNotEmpty()) {
            tokens.add(Ident(currentIdentifier.toString()))
        }

        return tokens
    }

    fun tokenizeRaw(input: String): List<Token> {
        val tokens = mutableListOf<Token>()
        var i = 0

        while (i < input.length) {
            val c = input[i]

            when {
                c.isWhitespace() -> i++

                c.isDigit() || c == '.' -> {
                    val start = i
                    while (i < input.length && (input[i].isDigit() || input[i] == '.')) i++
                    tokens.add(Number(parseNumber(input.substring(start, i))))
                }

                c.isLetter() -> {
                    val start = i
                    while (i < input.length && input[i].isLetter()) i++
                    tokens.add(Ident(input.substring(start, i)))
                }

                c in "+-*/=" -> {
                    tokens.add(Op(c))
                    i++
                }

                c == '(' -> {
                    tokens.add(LParen)
                    i++
                }

                c == ')' -> {
                    tokens.add(RParen)
                    i++
                }

                else -> throw MathEngineException(ParseError("Invalid char: $c"))
            }
        }

        return tokens
    }

    private fun parseNumber(value: String): Double =
        value.toDoubleOrNull()
            ?: throw MathEngineException(InvalidNumber(value))

    fun insertImplicitMultiplication(tokens: List<Token>): List<Token> {
        val result = mutableListOf<Token>()

        for (i in tokens.indices) {
            result.add(tokens[i])

            if (i == tokens.lastIndex) continue

            val a = tokens[i]
            val b = tokens[i + 1]

            if (needsMul(a, b)) {
                result.add(Op('*'))
            }
        }

        return result
    }

    private fun needsMul(a: Token, b: Token): Boolean =
        (a is Number || a is Ident || a is RParen) &&
                (b is Number || b is Ident || b is LParen)


}
