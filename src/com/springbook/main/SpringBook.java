package com.springbook.main;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.springbook.dao.ConnectionMaker;
import com.springbook.dao.CountingConnectionMaker;
import com.springbook.dao.CountingDaoFactory;
import com.springbook.dao.DConnectionMaker;
import com.springbook.dao.DaoFactory;
import com.springbook.dao.UserDao;
import com.springbook.domain.User;

/* XML 설정 전까지 사용했던 MAIN
public class SpringBook {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		
		 * //step1 ~ step5 전까지 UserDao 테스트 //UserDao dao = new UserDao();
		 

		
		 * //step6 : 관계설정 분리 적용중 테스트 //ConnectionMaker connectionMaker = new
		 * DConnectionMaker(); //UserDao dao = new UserDao(connectionMaker);
		 

		
		 * //step6 : 관계설정 분리 적용한 코드 UserDao dao = new DaoFactory().userDao();
		 

		ApplicationContext ctx = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = ctx.getBean("userDao", UserDao.class);

		User user = new User();
		user.setId("setMethod2");
		user.setName("modifier2");
		user.setPassword("spring");

		dao.add(user);

		System.out.println(user.getId() + " 등록 성공 ");

		User user2 = dao.get("setMethod");
		System.out.println("user2 ID : " + user2.getId());
		
		
		 * DaoFactory df = new DaoFactory(); UserDao dao2 = df.userDao(); UserDao dao3 =
		 * df.userDao();
		 * 
		 * System.out.println("직접 객체 생성 한 dao 객체"); System.out.println(dao2 + " dao2");
		 * System.out.println(dao3 + " dao3"); System.out.println(dao2 == dao3);
		 * 
		 * UserDao dao4 = ctx.getBean("userDao", UserDao.class); UserDao dao5 =
		 * ctx.getBean("userDao", UserDao.class);
		 * 
		 * // 스프링의 싱글톤 패턴은 디자인 패턴에서 나오는 싱글톤 패턴과 비슷한 개념이지만, 그 구현 방법이 확연히 다름
		 * System.out.println("스프링 컨텍스트를 사용해서 만든 dao 객체"); System.out.println(dao +
		 * " dao"); System.out.println(dao4 + " dao4"); System.out.println(dao5 +
		 * " dao5"); System.out.println((dao == dao4));
		 * 
		 * AnnotationConfigApplicationContext countCtx = new
		 * AnnotationConfigApplicationContext(CountingDaoFactory.class);
		 * 
		 * UserDao countDao = countCtx.getBean("userDao", UserDao.class); UserDao
		 * countDao2 = countCtx.getBean("userDao", UserDao.class); UserDao countDao3 =
		 * countCtx.getBean("userDao", UserDao.class); UserDao countDao4 =
		 * countCtx.getBean("userDao", UserDao.class);
		 * 
		 * CountingConnectionMaker ccm = countCtx.getBean("connectionMaker",
		 * CountingConnectionMaker.class); System.out.println("countDao : " + countDao);
		 * System.out.println("Connection counter: " + ccm.getCounter());
		 
	}
	*/

public class SpringBook {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		// XML 정보로 실행할 때
		ApplicationContext ctx = new GenericXmlApplicationContext("/com/springbook/dao/applicationContext.xml");

		// @Configuration으로 실행할 때
		//ApplicationContext ctx = new AnnotationConfigApplicationContext(DaoFactory.class);

		/*
		 * UserDao.class의 상대위치에서 daoContext.xml을 찾는다. 즉, 쉽게 말해 UserDao.class가 존재하는 클래스
		 * 패스에서 해당 파일을 찾음
		 * 
		 * ApplicationContext ctx = new ClassPathXmlApplicationContext("daoContext.xml",
		 * UserDao.class);
		 * 
		 */

		UserDao dao = ctx.getBean("userDao", UserDao.class);

		User user = new User();
		user.setId("xmlDataSource");
		user.setName("xmldb");
		user.setPassword("spring");

		dao.add(user);

		System.out.println(user.getId() + " 등록 성공 ");

		User user2 = dao.get("datasource");
		System.out.println("user2 ID : " + user2.getId());
	}

}
