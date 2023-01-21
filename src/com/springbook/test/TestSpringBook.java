package com.springbook.test;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.springbook.dao.UserDao;
import com.springbook.domain.User;

public class TestSpringBook {

	@Test
	public void addAndGet() throws SQLException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		UserDao dao = ctx.getBean("userDao", UserDao.class);

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		/*
		 * User DAO 생성자 추가 예제 전 코드 User user = new User(); user.setId("junit01");
		 * user.setName("junit01"); user.setPassword("junit01"); dao.add(user);
		 */

		User user1 = new User("junit", "junit", "junit");
		User user2 = new User("junit2", "junit2", "junit2");

		dao.add(user1);
		dao.add(user2);

		assertThat(dao.getCount(), is(2));

		/*
		 * User user2 = dao.get("junit01");
		 * 
		 * assertThat(user2.getName(), is(user.getName()));
		 * assertThat(user2.getPassword(), is(user.getPassword()));
		 */

		User userGet1 = dao.get(user1.getId());
		assertThat(userGet1.getName(), is(user1.getName()));
		assertThat(userGet1.getPassword(), is(user1.getPassword()));

		User userGet2 = dao.get(user2.getId());
		assertThat(userGet2.getName(), is(user2.getName()));
		assertThat(userGet2.getPassword(), is(user2.getPassword()));
	}

	@Test
	public void count() throws SQLException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		UserDao dao = ctx.getBean("userDao", UserDao.class);

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		User user1 = new User("test1", "test1", "test1");
		User user2 = new User("test2", "test2", "test2");
		User user3 = new User("test3", "test3", "test3");

		dao.add(user1);
		assertThat(dao.getCount(), is(1));

		dao.add(user2);
		assertThat(dao.getCount(), is(2));

		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {

		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDao dao = ctx.getBean("userDao", UserDao.class);

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknown_id");
	}
}
