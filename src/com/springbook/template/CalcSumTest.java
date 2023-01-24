package com.springbook.template;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.springbook.domain.Calculator;

public class CalcSumTest {
	
	/* ���ø� �ڵ� ���� ��
	 * @Test public void sumOfNumbers() throws IOException {
	 * 
	 * Calculator calculator = new Calculator(); int sum =
	 * calculator.calcSum(getClass().getResource("/numbers.txt").getPath());
	 * assertThat(sum, is(10)); }
	 */
	
	Calculator calculator;
	String numFilePath;
	
	@Before
	public void setUp() {
		this.calculator = new Calculator();
		this.numFilePath = getClass().getResource("/numbers.txt").getPath();
	}
	
	/* template , callback  �ڵ� �κ�
	 * @Test public void sumOfNumbers() throws IOException {
	 * assertThat(calculator.calcSum(this.numFilePath), is(10)); }
	 * 
	 * @Test public void multiplyOfNumbers() throws IOException {
	 * assertThat(calculator.calcMultiply(this.numFilePath), is(10)); }
	 */
}

	