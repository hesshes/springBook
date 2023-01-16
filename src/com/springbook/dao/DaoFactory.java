package com.springbook.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * step7: context ���� ��
public class DaoFactory {
	 step6 : ������Ʈ ���丮 Ȱ���� �ڵ�
	 * public UserDao userDao() { ConnectionMaker connectionMaker = new
	 * DConnectionMaker(); UserDao userDao = new UserDao(connectionMaker); return
	 * userDao; }
	 
	
	public UserDao userDao(ConnectionMaker connectionMaker) {
		return new UserDao(connectionMaker());
	}
	
	// �ߺ� ���Ÿ� ���� �ڵ�
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
}
*/

/* step 7 : DI, DL ���� ���� �ڵ� 
@Configuration
public class DaoFactory {

	public UserDao userDao() {
		return new UserDao();
	}

	@Bean
	public UserDao userDao(ConnectionMaker connectionMaker) {
		return new UserDao(connectionMaker());
	}

	@Bean
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
}
*/
/* */
@Configuration
public class DaoFactory {

	@Bean
	public UserDao userDao() { 
		UserDao userDao = new UserDao();
		userDao.setConnectionMaker(connectionMaker());
		return userDao;
	}
	
	/*
	 * @Bean public UserDao userDao(ConnectionMaker connectionMaker) { return new
	 * UserDao(connectionMaker()); }
	 */
	
	@Bean
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
}
