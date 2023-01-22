package com.springbook.learningtest.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.CoreMatchers.nullValue;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.junit.matchers.JUnitMatchers.either;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "junit.xml")
public class JUnitTestSpring {

	@Autowired
	ApplicationContext context;
	
	static JUnitTestSpring testObject;
	static Set<JUnitTestSpring> testObjects = new HashSet<JUnitTestSpring>();
	static ApplicationContext contextObject = null;

	@Test
	public void test1() {

		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;

		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
		
		assertThat(contextObject == null || contextObject == this.context, is(true));
		contextObject = this.context;
		
		System.out.println("test1 obj : " + this);
		System.out.println("test1 ctx : " + contextObject);
	}

	@Test
	public void test2() {

		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;

		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
		
		assertTrue(contextObject == null || contextObject == this.context);
		contextObject = this.context;
		
		System.out.println("test2 obj : " + this);
		System.out.println("test2 ctx : " + contextObject);
	}

	@Test
	public void test3() {

		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;

		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
		
		assertThat(contextObject, either(is(nullValue())).or(is(this.context)));
		contextObject = this.context;
		
		System.out.println("test3 obj : " + this);
		System.out.println("test3 ctx : " + contextObject);

	}

	@After
	public void confirm() {
		for (JUnitTestSpring jUnitTest : testObjects) {
			System.out.println("set size : " + testObjects.size());
			System.out.println("after : " + jUnitTest);
			System.out.println("=================");
		}
	}
}
