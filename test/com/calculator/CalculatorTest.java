package com.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class CalculatorTest {

	@Test
	void test() {
		var calculator = new Calculator();
		
		
		int actual = calculator.add(2, 3);
		
		assertEquals(6 , actual);
		
	}

}
