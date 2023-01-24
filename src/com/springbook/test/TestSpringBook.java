
package com.springbook.test;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.springbook.dao.JdbcContext;
import com.springbook.dao.UserDao;
import com.springbook.domain.User;

/*
public class TestSpringBook {

	@Test
	public void addAndGet() throws SQLException {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		UserDao dao = ctx.getBean("userDao", UserDao.class);

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		
		 * User DAO 생성자 추가 예제 전 코드 User user = new User(); user.setId("junit01");
		 * user.setName("junit01"); user.setPassword("junit01"); dao.add(user);
		 

		User user1 = new User("junit", "junit", "junit");
		User user2 = new User("junit2", "junit2", "junit2");

		dao.add(user1);
		dao.add(user2);

		assertThat(dao.getCount(), is(2));

		
		 * User user2 = dao.get("junit01");
		 * 
		 * assertThat(user2.getName(), is(user.getName()));
		 * assertThat(user2.getPassword(), is(user.getPassword()));
		 

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

*/
/*
@RunWith(SpringJUnit4ClassRunner.class)

 * @ContextConfiguration(locations = "/applicationContext.xml")
 * 
 * @DirtiesContext
 
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class TestSpringBook {

	
	 * 픽스처 테스트에서 정보 저장에 사용되는 객체나 인스턴스
	 
	 private UserDao dao; 

	private User user1;
	private User user2;
	private User user3;

	
	 * @Autowired private ApplicationContext ctx;
	 

	@Autowired
	UserDao dao;

	
	 * @Autowired SimpleDriverDataSource dataSource;
	 

	@Autowired
	DataSource dataSource;

	@Before
	public void setUp() {
		
		 * ApplicationContext ctx = new
		 * ClassPathXmlApplicationContext("applicationContext.xml");
		 

		
		 * System.out.println("Context obj : " + this.ctx);
		 * System.out.println("Text Obj : " + this);
		 
		 this.dao = ctx.getBean("userDao", UserDao.class); 

		 수동 DI 하는 코드
		 * DataSource dataSource = new
		 * SingleConnectionDataSource("jdbc:oracle:thin:@localhost:1521:xe", "hesshes",
		 * "hesshes", true); dao.setDataSource(dataSource);
		 
		
		this.user1 = new User("junit", "junit", "junit");
		this.user2 = new User("junit2", "junit2", "junit2");
		this.user3 = new User("junit3", "junit3", "junit3");

	}

	@Test
	public void addAndGet() throws SQLException {
		
		 * ApplicationContext ctx = new
		 * ClassPathXmlApplicationContext("applicationContext.xml");
		 * 
		 * UserDao dao = ctx.getBean("userDao", UserDao.class);
		 
		User user3 = new User("junitddd", "dddd", "dddd");
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		
		 * User DAO 생성자 추가 예제 전 코드 User user = new User(); user.setId("junit01");
		 * user.setName("junit01"); user.setPassword("junit01"); dao.add(user);
		 

		
		 * before 로 옮겨짐 User user1 = new User("junit", "junit", "junit"); User user2 =
		 * new User("junit2", "junit2", "junit2");
		 

		dao.add(this.user1);
		dao.add(this.user2);
		dao.add(this.user3);
		dao.add(user3);
		assertThat(dao.getCount(), is(4));

		
		 * User user2 = dao.get("junit01");
		 * 
		 * assertThat(user2.getName(), is(user.getName()));
		 * assertThat(user2.getPassword(), is(user.getPassword()));
		 

		User userGet1 = dao.get(this.user1.getId());
		assertThat(userGet1.getName(), is(this.user1.getName()));
		assertThat(userGet1.getPassword(), is(this.user1.getPassword()));

		User userGet2 = dao.get(this.user2.getId());
		assertThat(userGet2.getName(), is(this.user2.getName()));
		assertThat(userGet2.getPassword(), is(this.user2.getPassword()));

		User userGet3 = dao.get(user3.getId());
		assertThat(userGet3.getName(), is(user3.getName()));
		assertThat(userGet3.getPassword(), is(user3.getPassword()));

		User userGet4 = dao.get(this.user3.getId());
		assertThat(userGet4.getName(), is(this.user3.getName()));
		assertThat(userGet4.getPassword(), is(this.user3.getPassword()));
	}

	@Test
	public void count() throws SQLException {

		
		 * ApplicationContext ctx = new
		 * ClassPathXmlApplicationContext("applicationContext.xml");
		 * 
		 * UserDao dao = ctx.getBean("userDao", UserDao.class);
		 

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

		
		 * ApplicationContext ctx = new
		 * ClassPathXmlApplicationContext("applicationContext.xml"); UserDao dao =
		 * ctx.getBean("userDao", UserDao.class);
		 

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.get("unknown_id");
	}
}

*/

public class TestSpringBook {

	UserDao dao;

	private User user1;
	private User user2;
	private User user3;

	@Before
	public void setUp() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
		this.dao = ctx.getBean("userDao", UserDao.class);

		this.user1 = new User("junit", "junit", "junit");
		this.user2 = new User("junit2", "junit2", "junit2");
		this.user3 = new User("junit3", "junit3", "junit3");

	}

	@Test
	public void addAndGet() throws SQLException {
		User user3 = new User("junitddd", "dddd", "dddd");
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.add(this.user1);
		dao.add(this.user2);
		dao.add(this.user3);
		dao.add(user3);
		assertThat(dao.getCount(), is(4));

		User userGet1 = dao.get(this.user1.getId());
		assertThat(userGet1.getName(), is(this.user1.getName()));
		assertThat(userGet1.getPassword(), is(this.user1.getPassword()));

		User userGet2 = dao.get(this.user2.getId());
		assertThat(userGet2.getName(), is(this.user2.getName()));
		assertThat(userGet2.getPassword(), is(this.user2.getPassword()));

		User userGet3 = dao.get(user3.getId());
		assertThat(userGet3.getName(), is(user3.getName()));
		assertThat(userGet3.getPassword(), is(user3.getPassword()));

		User userGet4 = dao.get(this.user3.getId());
		assertThat(userGet4.getName(), is(this.user3.getName()));
		assertThat(userGet4.getPassword(), is(this.user3.getPassword()));
	}

	@Test
	public void count() throws SQLException {

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

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.get("unknown_id");
	}
}
