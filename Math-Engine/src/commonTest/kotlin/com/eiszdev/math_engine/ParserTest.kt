package com.eiszdev.math_engine

import com.eiszdev.math_engine.algebra.Add
import com.eiszdev.math_engine.algebra.Div
import com.eiszdev.math_engine.algebra.Equation
import com.eiszdev.math_engine.algebra.Mul
import com.eiszdev.math_engine.algebra.Neg
import com.eiszdev.math_engine.algebra.Num
import com.eiszdev.math_engine.algebra.Parser
import com.eiszdev.math_engine.algebra.Sub
import com.eiszdev.math_engine.algebra.Token.Ident
import com.eiszdev.math_engine.algebra.Token.LParen
import com.eiszdev.math_engine.algebra.Token.Number
import com.eiszdev.math_engine.algebra.Token.Op
import com.eiszdev.math_engine.algebra.Token.RParen
import com.eiszdev.math_engine.algebra.Var
import kotlin.test.Test
import kotlin.test.assertEquals

class ParserTest {
    @Test
    fun `parse linear equation tokens`() {
        // 2x+4 = 10
        val tokens = listOf(Number(2.0), Op('*'), Ident("x"), Op('+'), Number(4.0), Op('='), Number(10.0))
        val parser = Parser(tokens)
        val parsedEquation = parser.parseEquation()
        val expectedEquation = Equation(left = Add(left = Mul(Num(2.0), Var("x")), right = Num(4.0)), right = Num(10.0))

        assertEquals(parsedEquation, expectedEquation)
    }

    @Test
    fun `parse nested equation tokens`() {
        // (2x+4)/5=10
        val tokens = listOf(LParen, Number(2.0), Op('*'), Ident("x"), Op('+'), Number(4.0), RParen, Op('/'), Number(5.0), Op('='), Number(10.0))
        val parser = Parser(tokens)
        val parsedEquation = parser.parseEquation()
        val expectedEquation = Equation(left = Div(left = Add(left = Mul(left = Num(2.0), right = Var("x")), right = Num(4.0)), right = Num(5.0)), right = Num(10.0))

        assertEquals(parsedEquation, expectedEquation)
    }

    @Test
    fun `parse nested equation without parentheses`() {
        // 2x+4/5=10
        val tokens = listOf(Number(2.0), Op('*'), Ident("x"), Op('+'), Number(4.0), Op('/'), Number(5.0), Op('='), Number(10.0))
        val parser = Parser(tokens)
        val parsedEquation = parser.parseEquation()
        val expectedEquation = Equation(left = Add(left = Mul(left = Num(2.0), right = Var("x")), right = Div(left = Num(4.0), right = Num(5.0))), right = Num(10.0))

        assertEquals(parsedEquation, expectedEquation)
    }

    @Test
    fun `parse equation with decimal tokens`() {
        //(2.5x + 4.73) / 5.2 = 10.25
        val tokens = listOf(LParen, Number(2.5), Op('*'), Ident("x"), Op('+'), Number(4.73), RParen, Op('/'), Number(5.2), Op('='), Number(10.25))
        val parser = Parser(tokens)
        val parsedEquation = parser.parseEquation()
        val expectedEquation = Equation(left = Div(left = Add(left = Mul(left = Num(2.5), right = Var("x")), right = Num(4.73)), right = Num(5.2)), right = Num(10.25))

        assertEquals(parsedEquation, expectedEquation)
    }

    @Test
    fun `parse equation with multiple operators tokens`() {
        //2+4x/6-2x=10
        val tokens = listOf(LParen, Number(2.5), Op('*'), Ident("x"), Op('+'), Number(4.73), RParen, Op('/'), Number(5.2), Op('='), Number(10.25))
        val parser = Parser(tokens)
        val parsedEquation = parser.parseEquation()
        val expectedEquation = Equation(left = Div(left = Add(left = Mul(left = Num(2.5), right = Var("x")), right = Num(4.73)), right = Num(5.2)), right = Num(10.25))

        assertEquals(parsedEquation, expectedEquation)
    }

    @Test
    fun `parse operators with higher precedence`() {
        // 2+3*4=14
        val tokens = listOf(Number(2.0), Op('+'), Number(3.0), Op('*'), Number(4.0), Op('='), Number(14.0))
        val parser = Parser(tokens)
        val parsedEquation = parser.parseEquation()
        val expectedEquation = Equation(left = Add(left = Num(2.0), right = Mul(left = Num(3.0), right = Num(4.0))), right = Num(14.0))

        assertEquals(parsedEquation, expectedEquation)
    }

    @Test
    fun `parse operators with higher precedence v2`() {
        // 5-4/2=3
        val tokens = listOf(Number(5.0), Op('-'), Number(4.0), Op('/'), Number(2.0), Op('='), Number(3.0))
        val parser = Parser(tokens)
        val parsedEquation = parser.parseEquation()
        val expectedEquation = Equation(left = Sub(left = Num(5.0), right = Div(left = Num(4.0), right = Num(2.0))), right = Num(3.0))

        assertEquals(parsedEquation, expectedEquation)
    }

    @Test
    fun `parse operators with lower precedence`() {
        // 2*3+4=10
        val tokens = listOf(Number(2.0), Op('*'), Number(3.0), Op('+'), Number(4.0), Op('='), Number(10.0))
        val parser = Parser(tokens)
        val parsedEquation = parser.parseEquation()
        val expectedEquation = Equation(left = Add(left = Mul(left = Num(2.0), right = Num(3.0)), right = Num(4.0)), right = Num(10.0))

        assertEquals(parsedEquation, expectedEquation)
    }

    @Test
    fun `parse equation with negative coefficient`() {
        val tokens = listOf(Op('-'), Ident("x"), Op('+'), Number(2.0), Op('='), Number(5.0))
        val parser = Parser(tokens)
        val parsedEquation = parser.parseEquation()
        val expectedEquation = Equation(left = Add(left = Neg(Var("x")), right = Num(2.0)), right = Num(5.0))

        assertEquals(parsedEquation, expectedEquation)
    }
    
}
