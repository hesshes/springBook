package com.springbook.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.springbook.template.BufferedReaderCallBack;
import com.springbook.template.LineCallBack;

public class Calculator {

	/*
	 * 템플릿 미 적용 코드 public Integer calcSum(String filePath) throws IOException {
	 * 
	 * BufferedReader br = null;
	 * 
	 * try { br = new BufferedReader(new FileReader(filePath)); Integer sum = 0;
	 * String line = null; while ((line = br.readLine()) != null) { sum +=
	 * Integer.valueOf(line); } return sum;
	 * 
	 * } catch (IOException e) {
	 * 
	 * System.out.println(e.getMessage()); throw e;
	 * 
	 * } finally { if (br != null) { try { br.close(); } catch (IOException e) {
	 * System.out.println(e.getMessage()); } } } }
	 */

	/*
	 * 템플릿 코드 적용 1차 public Integer calcSum(String filePath) throws IOException {
	 * 
	 * BufferedReaderCallBack sumCallBack = new BufferedReaderCallBack() {
	 * 
	 * public Integer doSomethingWithReader(BufferedReader br) throws IOException {
	 * Integer sum = 0; String line = null; while ((line = br.readLine()) != null) {
	 * sum += Integer.valueOf(line); } return sum; } };
	 * 
	 * return fileReadTemplate(filePath, sumCallBack); }
	 * 
	 * public Integer calcMultiply(String filePath) throws IOException {
	 * 
	 * BufferedReaderCallBack multiplyCallBack = new BufferedReaderCallBack() {
	 * 
	 * public Integer doSomethingWithReader(BufferedReader br) throws IOException {
	 * Integer multiply = 1; String line = null; while ((line = br.readLine()) !=
	 * null) { multiply += Integer.valueOf(line); } return multiply; } };
	 * 
	 * return fileReadTemplate(filePath, multiplyCallBack); }
	 * 
	 * public Integer fileReadTemplate(String filePath, BufferedReaderCallBack
	 * callBack) throws IOException {
	 * 
	 * BufferedReader br = null; try { br = new BufferedReader(new
	 * FileReader(filePath)); int ret = callBack.doSomethingWithReader(br); return
	 * ret;
	 * 
	 * } catch (IOException e) { System.out.println(e.getMessage()); throw e; }
	 * finally { if (br != null) { try { br.close(); } catch (IOException e) {
	 * System.out.println(e.getMessage()); } } } }
	 */

	public Integer calcSum(String filePath) throws IOException {

		LineCallBack sumCallBack = new LineCallBack() {
			public Integer doSomethingWithLine(String line, Integer value) {
				return value + Integer.valueOf(line);
			}
		};

		return lineReadTemplate(filePath, sumCallBack, 0);
	}

	public Integer calcMultiply(String filePath) throws IOException {

		LineCallBack multiplyCallBack = new LineCallBack() {

			public Integer doSomethingWithLine(String line, Integer value) {
				return value * Integer.valueOf(line);
			}
		};
		return lineReadTemplate(filePath, multiplyCallBack, 1);
	}

	public Integer lineReadTemplate(String filePath, LineCallBack callBack, int initVal) throws IOException {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
			Integer res = initVal;
			String line = null;
			while ((line = br.readLine()) != null) {
				res = callBack.doSomethingWithLine(line, res);
			}
			return res;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
