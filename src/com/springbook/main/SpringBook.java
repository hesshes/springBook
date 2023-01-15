package com.springbook.main;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springbook.dao.ConnectionMaker;
import com.springbook.dao.DConnectionMaker;
import com.springbook.dao.DaoFactory;
import com.springbook.dao.UserDao;
import com.springbook.domain.User;

public class SpringBook {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		/*
		 * //step1 ~ step5 ������ UserDao �׽�Ʈ //UserDao dao = new UserDao();
		 */
		
		/*
		 * //step6 : ���輳�� �и� ������ �׽�Ʈ //ConnectionMaker connectionMaker = new
		 * DConnectionMaker(); //UserDao dao = new UserDao(connectionMaker);
		 */		
		
		/* 
		 * //step6 : ���輳�� �и� ������ �ڵ�
		 * UserDao dao = new DaoFactory().userDao();
		 */	
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = ctx.getBean("userDao", UserDao.class);
		
		/*
		 * User user = new User(); user.setId("bookspring"); user.setName("������2");
		 * user.setPassword("spring");
		 * 
		 * dao.add(user);
		 * 
		 * System.out.println(user.getId() + " ��� ���� ");
		 */
		
		DaoFactory df = new DaoFactory();
		UserDao dao2 = df.userDao();
		UserDao dao3 = df.userDao();
		
		System.out.println("���� ��ü ���� �� dao ��ü");
		System.out.println(dao2 + " dao2");
		System.out.println(dao3 + " dao3");
		System.out.println(dao2==dao3);
		
		UserDao dao4 = ctx.getBean("userDao", UserDao.class);
		UserDao dao5 = ctx.getBean("userDao", UserDao.class);
		
		// �������� �̱��� ������ ������ ���Ͽ��� ������ �̱��� ���ϰ� ����� ����������, �� ���� ����� Ȯ���� �ٸ�
		System.out.println("������ ���ؽ�Ʈ�� ����ؼ� ���� dao ��ü");
		System.out.println(dao + " dao");
		System.out.println(dao4 + " dao4");
		System.out.println(dao5 + " dao5");		
		System.out.println((dao == dao4));
		

	}
}
