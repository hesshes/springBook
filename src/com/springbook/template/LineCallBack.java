package com.springbook.template;

public interface LineCallBack<T> {
	Integer doSomethingWithLine(String line, Integer value);
	T doSomethingWithLine(String line, T value);
}
