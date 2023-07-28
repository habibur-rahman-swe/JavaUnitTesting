package com.calculator;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HamcrestDemo {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	void firstHamcrestTest() {
		String actualString = "some value";
		String expectedString = "some value";
		
		assertThat(actualString, equalTo(expectedString));
	}
	
	
	@Test
	void secondHamcrestTest() {
		var list = new ArrayList<>(Arrays.asList("a", "b", "c"));
	
		assertThat(list, hasItem(anyOf(equalTo("a"), equalTo("b"), equalTo("c"))));
		
		assertTrue(list.contains("a") || list.contains("b") || list.contains("b"));
		
	}
	
	
}
