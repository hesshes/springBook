package com.springbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

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

/*
public class UserDao {

	private ConnectionMaker connectionMaker;

	
	 * step 5: 관계설정 분리전 public UserDao() { connectionMaker = new DConnectionMaker();
	 * }
	 

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
*/

/* step x : singtone 코드 포함된 객체

public class UserDao {

private static UserDao INSTANCE;

private ConnectionMaker connectionMaker;

private UserDao(ConnectionMaker connectionMaker) {
	this.connectionMaker = connectionMaker;
}

public static synchronized UserDao getInstance() {
	if (INSTANCE == null)
		INSTANCE = new UserDao(null);
	return INSTANCE;
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
*/

/* step 8 : DI, DL 
public class UserDao {

	private ConnectionMaker connectionMaker;

	private Connection c;
	private User user;

	public UserDao() {

	}

	public UserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;

	}

	public void add(User user) throws ClassNotFoundException, SQLException {

		this.c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeLargeUpdate();

		ps.close();

		c.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {

		this.c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();

		rs.next();

		this.user = new User();
		this.user.setId(rs.getString("id"));
		this.user.setName(rs.getString("name"));
		this.user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return this.user;
	}
}
*/

/* step 9 : 수정자 메소드 DI 방식
public class UserDao {
	
	private ConnectionMaker connectionMaker;
	
	private Connection c;
	private User user;
	
	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}
	
	public void add(User user) throws ClassNotFoundException, SQLException {
		
		this.c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeLargeUpdate();
		
		ps.close();
		
		c.close();
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		
		this.c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		
		this.user = new User();
		this.user.setId(rs.getString("id"));
		this.user.setName(rs.getString("name"));
		this.user.setPassword(rs.getString("password"));
		
		rs.close();
		ps.close();
		c.close();
		
		return this.user;
	}
	*/

public class UserDao {

	private DataSource dataSource;

	private Connection c;
	private User user;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void add(User user) throws SQLException {

		this.c = dataSource.getConnection();

		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeLargeUpdate();

		ps.close();

		c.close();
	}

	public User get(String id) throws SQLException {

		this.c = dataSource.getConnection();

		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();

		rs.next();

		this.user = new User();
		this.user.setId(rs.getString("id"));
		this.user.setName(rs.getString("name"));
		this.user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return this.user;
	}

	public void deleteAll() throws SQLException {
		Connection c = dataSource.getConnection();

		PreparedStatement ps = c.prepareStatement("delete from users");

		ps.executeUpdate();

		ps.close();
		c.close();
	}

	public int getCount() throws SQLException {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("select count(*) from users");
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		
		rs.close();
		ps.close();
		c.close();
		
		return count;
		
	}
}
