package com.springbook.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * step7: context 적용 전
public class DaoFactory {
	 step6 : 오브젝트 팩토리 활용전 코드
	 * public UserDao userDao() { ConnectionMaker connectionMaker = new
	 * DConnectionMaker(); UserDao userDao = new UserDao(connectionMaker); return
	 * userDao; }
	 
	
	public UserDao userDao(ConnectionMaker connectionMaker) {
		return new UserDao(connectionMaker());
	}
	
	// 중복 제거를 위한 코드
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
}
*/

/* step 7 : DI, DL 주입 예제 코드 
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
