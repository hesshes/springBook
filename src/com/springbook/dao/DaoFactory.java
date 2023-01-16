package com.springbook.dao;

import java.sql.Driver;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

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
 * @Configuration						 				=	<beans>  : @Configuration ������̼��� <beans> �±��� ����	
 * @Bean												=	<bean>	 : @Bean ������̼��� <bean> �±��� ���� 
 * @Bean public ConnectionMaker connectionMaker() {}	=	<bean id="connectionMaker"> : connectionMaker �޼���� bean �±��� id �Ӽ�
 * new DConnectionMaker()								=   <bean class = "com.springbook.dao.DConnectionMaker"> : �����Ǵ� ��ü Ŭ����
 * 
 * userDao.setConnectionMaker(connectionMaker())		=	setConnectionMaker() �޼ҵ� �̸����� �� �±׸� �и�
 *         set											=	<property>	:	property �±׸� �ǹ��ϸ�, property �±״� <bean>�� ���� �±׷� �������踦 �ο��� �� ����� 															  
 * 			  ConnectionMaker							=	<property name="connectionMaker"  ... />	:	set�� ������ �Լ� �̸��� name ������Ƽ�� ������ ���
 *							  connectionMaker()			=	<property name="connectionMaker" ref="connectionMaker" />	:  connectionMaker() �Ķ����        
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
