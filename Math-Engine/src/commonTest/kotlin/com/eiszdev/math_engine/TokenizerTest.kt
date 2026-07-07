package com.eiszdev.math_engine

import com.eiszdev.math_engine.algebra.Token.Ident
import com.eiszdev.math_engine.algebra.Token.LParen
import com.eiszdev.math_engine.algebra.Token.Number
import com.eiszdev.math_engine.algebra.Token.Op
import com.eiszdev.math_engine.algebra.Token.RParen
import com.eiszdev.math_engine.algebra.Tokenizer
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test

class TokenizerTest {
    @Test
    fun `tokenize flat string`() {
        val equation = "2x+4=10"

        val result = Tokenizer().tokenize(equation)
        val expectedTokens = listOf(Number(2.0), Op('*'), Ident("x"), Op('+'), Number(4.0), Op('='), Number(10.0))

        assertEquals(null, expectedTokens, result)
    }

    @Test
    fun `tokenize nested string`() {
        val equation = "(2x+4)/5=10"

        val result = Tokenizer().tokenize(equation)
        val expectedTokens = listOf(LParen, Number(2.0), Op('*'), Ident("x"), Op('+'), Number(4.0), RParen, Op('/'), Number(5.0), Op('='), Number(10.0))

        assertEquals(null, expectedTokens, result)
    }

    @Test
    fun `tokenize long identifier string`() {
        val equation = "(2abc+4)/5=10"

        val result = Tokenizer().tokenize(equation)
        val expectedTokens = listOf(LParen, Number(2.0), Op('*'), Ident("abc"), Op('+'), Number(4.0), RParen, Op('/'), Number(5.0), Op('='), Number(10.0))

        assertEquals(null, expectedTokens, result)
    }

    @Test
    fun `tokenize string with spaces`() {
        val equation = "(2x + 4) / 5 = 10"

        val result = Tokenizer().tokenize(equation)
        val expectedTokens = listOf(LParen, Number(2.0), Op('*'), Ident("x"), Op('+'), Number(4.0), RParen, Op('/'), Number(5.0), Op('='), Number(10.0))

        assertEquals(null, expectedTokens, result)
    }

    @Test
    fun `tokenize string with decimal`() {
        val equation = "(2.5x + 4.73) / 5.2 = 10.25"

        val result = Tokenizer().tokenize(equation)
        val expectedTokens = listOf(LParen, Number(2.5), Op('*'), Ident("x"), Op('+'), Number(4.73), RParen, Op('/'), Number(5.2), Op('='), Number(10.25))

        assertEquals(null, expectedTokens, result)
    }

    @Test
    fun `tokenize implicit multiplication string`() {
        val equation = "(2x+4)5=10"

        val result = Tokenizer().tokenize(equation)
        val expectedTokens = listOf(LParen, Number(2.0), Op('*'), Ident("x"), Op('+'), Number(4.0), RParen, Op('*'), Number(5.0), Op('='), Number(10.0))

        assertEquals(null, expectedTokens, result)
    }
}