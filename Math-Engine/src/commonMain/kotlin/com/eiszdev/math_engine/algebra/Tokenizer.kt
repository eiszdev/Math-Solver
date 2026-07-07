package com.eiszdev.math_engine.algebra

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
                    if (lastToken is Token.Ident || lastToken is Token.RParen) {
                        tokens.add(Token.Op('*'))
                    }
                    tokens.add(Token.Number(currentNumber.toString().toDouble()))
                    currentNumber.clear()
                }
                if (char.isLetter()) {
                    currentIdentifier.append(char)
                } else {
                    if (currentIdentifier.isNotEmpty()) {
                        val lastToken = if (tokens.size > 0) tokens[tokens.size-1] else null
                        if (lastToken is Token.Number || lastToken is Token.RParen) {
                            tokens.add(Token.Op('*'))
                        }
                        tokens.add(Token.Ident(currentIdentifier.toString()))
                        currentIdentifier.clear()
                    }
                    when (char) {
                        '+', '-', '*', '/', '=' -> tokens.add(Token.Op(char))
                        '(' -> tokens.add(Token.LParen)
                        ')' -> tokens.add(Token.RParen)
                    }
                }

            }
        }

        if (currentNumber.isNotEmpty()) {
            tokens.add(Token.Number(currentNumber.toString().toDouble()))
        }

        if (currentIdentifier.isNotEmpty()) {
            tokens.add(Token.Ident(currentIdentifier.toString()))
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
                    tokens.add(Token.Number(input.substring(start, i).toDouble()))
                }

                c.isLetter() -> {
                    val start = i
                    while (i < input.length && input[i].isLetter()) i++
                    tokens.add(Token.Ident(input.substring(start, i)))
                }

                c in "+-*/=" -> {
                    tokens.add(Token.Op(c))
                    i++
                }

                c == '(' -> {
                    tokens.add(Token.LParen)
                    i++
                }

                c == ')' -> {
                    tokens.add(Token.RParen)
                    i++
                }

                else -> throw IllegalArgumentException("Invalid char: $c")
            }
        }

        return tokens
    }

    fun insertImplicitMultiplication(tokens: List<Token>): List<Token> {
        val result = mutableListOf<Token>()

        for (i in tokens.indices) {
            result.add(tokens[i])

            if (i == tokens.lastIndex) continue

            val a = tokens[i]
            val b = tokens[i + 1]

            if (needsMul(a, b)) {
                result.add(Token.Op('*'))
            }
        }

        return result
    }

    private fun needsMul(a: Token, b: Token): Boolean =
        (a is Token.Number || a is Token.Ident || a is Token.RParen) &&
                (b is Token.Number || b is Token.Ident || b is Token.LParen)


}