package com.springbook.template;

/*
 * public interface LineCallBack { Integer doSomethingWithLine(String line,
 * Integer value); }
 */

public interface LineCallBack<T> {
	T doSomethingWithLine(String line, T value);
}