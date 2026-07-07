package com.eiszdev.math_engine.algebra

class Parser(private val tokens: List<Token>) {
    private var pos = 0

    fun parseEquation(): Equation {
        val left = parseExpression(0)

        val token = consume()
        if (token !is Token.Op || token.char != '=') {
            error("Expected '='")
        }

        val right = parseExpression(0)

        if (pos < tokens.size) {
            error("Unexpected token: ${tokens[pos]}")
        }

        return Equation(left, right)
    }

    private fun precedence(op: Char): Int =
        when (op) {
            '+', '-' -> 1
            '*', '/' -> 2
            '=' -> -1
            else -> error("Unknown operator $op")
        }

    private fun parseExpression(minPrecedence: Int): Expression {
        var left = parsePrimary()
        while (true) {
            val op = peekOp() ?: break
            val prec = precedence(op)
            if (prec < minPrecedence) break
            consume()
            val right = parseExpression(prec+1)
            left = when (op) {
                '+' -> Add(left, right)
                '-' -> Sub(left, right)
                '*' -> Mul(left, right)
                '/' -> Div(left, right)
                else -> error("Unknown operator $op")
            }
        }
        return left
    }

    private fun parsePrimary(): Expression {
        return when (val token = consume()) {
            is Token.Number -> Num(token.value)
            is Token.Ident -> Var(token.name)
            is Token.Op -> {
                if (token.char == '-') {
                    Neg(parsePrimary())
                }
                 else error("Unexpected token: $token")
            }

            Token.LParen -> {
                val expr = parseExpression(0)
                expect<Token.RParen>()
                expr
            }

            else -> error("Unexpected token: $token")
        }
    }

    private fun peek(): Token? =
        tokens.getOrNull(pos)

    private fun consume(): Token =
        tokens.getOrNull(pos++) ?: error("Unexpected end of input")

    private fun peekOp(): Char? =
        (peek() as? Token.Op)?.char

    private inline fun <reified T : Token> expect() {
        val token = consume()
        if (token !is T) error("Expected ${T::class.simpleName}, got $token")
    }
}
