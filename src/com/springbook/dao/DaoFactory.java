package com.springbook.dao;

import java.sql.Driver;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

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
/*
@Configuration
public class DaoFactory {

	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setConnectionMaker(connectionMaker());
		return userDao;
	}

	
	 * @Bean public UserDao userDao(ConnectionMaker connectionMaker) { return new
	 * UserDao(connectionMaker()); }
	 

	@Bean
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}

}
*/

/*
 * @Configuration public class DaoFactory {
 * 
 * @Bean public UserDao userDao() { UserDao userDao = new UserDao();
 * userDao.setConnectionMaker(connectionMaker()); return userDao; }
 * 
 * 
 * @Bean public UserDao userDao(ConnectionMaker connectionMaker) { return new
 * UserDao(connectionMaker()); }
 * 
 * 
 * @Bean public ConnectionMaker connectionMaker() { return new
 * DConnectionMaker(); }
 * 
 * @Configuration						 				=	<beans>  : @Configuration 어노테이션은 <beans> 태그의 역할	
 * @Bean												=	<bean>	 : @Bean 어노테이션은 <bean> 태그의 역할 
 * @Bean public ConnectionMaker connectionMaker() {}	=	<bean id="connectionMaker"> : connectionMaker 메서드는 bean 태그의 id 속성
 * new DConnectionMaker()								=   <bean class = "com.springbook.dao.DConnectionMaker"> : 생성되는 객체 클래스
 * 
 * userDao.setConnectionMaker(connectionMaker())		=	setConnectionMaker() 메소드 이름에서 각 태그를 분리
 *         set											=	<property>	:	property 태그를 의미하며, property 태그는 <bean>의 하위 태그로 의존관계를 부여할 때 사용함 															  
 * 			  ConnectionMaker							=	<property name="connectionMaker"  ... />	:	set을 제외한 함수 이름은 name 프로퍼티의 값으로 사용
 *							  connectionMaker()			=	<property name="connectionMaker" ref="connectionMaker" />	:  connectionMaker() 파라미터        
 */

@Configuration
public class DaoFactory {

	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setDataSource(dataSource());
		return userDao;
	}

	/*
	 * @Bean public UserDao userDao(ConnectionMaker connectionMaker) { return new
	 * UserDao(connectionMaker()); }
	 */

	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

		dataSource.setDriverClass(Driver.class);
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		dataSource.setUsername("hesshes");
		dataSource.setPassword("hesshes");

		return dataSource;
	}

}
