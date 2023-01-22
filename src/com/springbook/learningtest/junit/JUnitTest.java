package com.springbook.learningtest.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Test;

public class JUnitTest {
	static JUnitTest testObject;
	static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();

	@Test
	public void test1() {

		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;

		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
		System.out.println("test1 : " + this);
	}

	@Test
	public void test2() {

		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;

		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
		System.out.println("test2 : " + this);
	}

	@Test
	public void test3() {

		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;

		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
		System.out.println("test3 : " + this);

	}

	@After
	public void confirm() {
		for (JUnitTest jUnitTest : testObjects) {
			System.out.println("set size : " + testObjects.size());
			System.out.println("after : " + jUnitTest);
			System.out.println("=================");
		}
	}
}
