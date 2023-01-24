package com.springbook.test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;

import org.junit.Test;

import com.springbook.domain.Calculator;

public class CalcSumTest {
	
	@Test
	public void sumOfNumbers() throws IOException {

		Calculator calculator = new Calculator();
		int sum = calculator.calcSum(getClass().getResource("/numbers.txt").getPath());
		assertThat(sum, is(10));
	}
}
