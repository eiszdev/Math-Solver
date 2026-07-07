package com.eiszdev.math_engine

import com.eiszdev.math_engine.algebra.Token.Ident
import com.eiszdev.math_engine.algebra.Token.Number
import com.eiszdev.math_engine.algebra.Token.Op

fun givenTokens() = listOf(Number(2.0), Op('*'), Ident("x"), Op('+'), Number(4.0), Op('='), Number(10.0))