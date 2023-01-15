package com.springbook.main;

import java.sql.SQLException;

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
		
		UserDao dao = new DaoFactory().userDao();
		
		User user = new User();
		user.setId("bookspring");
		user.setName("������2");
		user.setPassword("spring");

		dao.add(user);

		System.out.println(user.getId() + " ��� ���� ");

	}
}
