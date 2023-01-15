package com.springbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.springbook.domain.User;

/*
 * public class UserDao {
 * 
 * 
 * // step1 : SoC (Separation of Concerns) 적용하지 않은 코드
 * 
 * public void add(User user) throws ClassNotFoundException, SQLException {
 * 
 * Class.forName("oracle.jdbc.OracleDriver"); Connection c =
 * DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hesshes",
 * "hesshes");
 * 
 * PreparedStatement ps =
 * c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
 * ps.setString(1, user.getId()); ps.setString(2, user.getName());
 * ps.setString(3, user.getPassword());
 * 
 * ps.executeLargeUpdate();
 * 
 * ps.close();
 * 
 * c.close(); }
 * 
 * 
 * public User get(String id) throws ClassNotFoundException, SQLException {
 * Class.forName("oracle.jdbc.OracleDriver"); Connection c =
 * DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hesshes",
 * "hesshes");
 * 
 * PreparedStatement ps =
 * c.prepareStatement("select * from users where id = ?"); ps.setString(1, id);
 * 
 * ResultSet rs = ps.executeQuery();
 * 
 * rs.next();
 * 
 * User user = new User(); user.setId(rs.getString("id"));
 * user.setName(rs.getString("name"));
 * user.setPassword(rs.getString("password"));
 * 
 * rs.close(); ps.close(); c.close();
 * 
 * return user; }
 * 
 * 
 *
 **************************************************************************
 *
 * step2 :  SoC (Separation of Concerns) 적용한 코드
 * step2에서 DB 연결을 공통 관심사로 분리
 * 
 * public void add(User user) throws ClassNotFoundException, SQLException {
 * 
 * Connection c = getConnection();
 * 
 * PreparedStatement ps =
 * c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
 * ps.setString(1, user.getId()); ps.setString(2, user.getName());
 * ps.setString(3, user.getPassword());
 * 
 * ps.executeLargeUpdate();
 * 
 * ps.close();
 * 
 * c.close(); }
 * 
 * public User get(String id) throws ClassNotFoundException, SQLException {
 * 
 * Connection c = getConnection();
 * 
 * PreparedStatement ps =
 * c.prepareStatement("select * from users where id = ?"); ps.setString(1, id);
 * 
 * ResultSet rs = ps.executeQuery();
 * 
 * rs.next();
 * 
 * User user = new User(); user.setId(rs.getString("id"));
 * user.setName(rs.getString("name"));
 * user.setPassword(rs.getString("password"));
 * 
 * rs.close(); ps.close(); c.close();
 * 
 * return user; }
 * 
 * private Connection getConnection() throws ClassNotFoundException,
 * SQLException {
 * 
 * Class.forName("oracle.jdbc.OracleDriver"); Connection c =
 * DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hesshes",
 * "hesshes"); return c; } }
 */

/*step3 : refactoring 개념 적용
 * 
 * public abstract class UserDao {
	public void add(User user) throws ClassNotFoundException, SQLException {

		Connection c = getConnection();

		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeLargeUpdate();

		ps.close();

		c.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {

		Connection c = getConnection();

		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();

		rs.next();

		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return user;
	}
	
	public abstract Connection getConnection() throws ClassNotFoundException, SQLException;

}*/

/*
 * step4 : 특정 클래스 별도 생성
 * 
public class UserDao {

	private SimpleConnectionMaker simpleConnectionMaker;

	public UserDao() {
		simpleConnectionMaker = new SimpleConnectionMaker();
	}

	public void add(User user) throws ClassNotFoundException, SQLException {

		Connection c = simpleConnectionMaker.makeNewConnection();

		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeLargeUpdate();

		ps.close();

		c.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {

		Connection c = simpleConnectionMaker.makeNewConnection();

		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();

		rs.next();

		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return user;
	}
}*/

public class UserDao {

	private ConnectionMaker connectionMaker;

	/*
	 * step 5: 관계설정 분리전 public UserDao() { connectionMaker = new DConnectionMaker();
	 * }
	 */

	public UserDao() {
	}

	public UserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public void add(User user) throws ClassNotFoundException, SQLException {

		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeLargeUpdate();

		ps.close();

		c.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {

		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();

		rs.next();

		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return user;
	}
}
