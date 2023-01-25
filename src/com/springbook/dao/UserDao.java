package com.springbook.dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.springbook.domain.User;

/*
 * 
 * public class UserDao {
 * 
 * 
 * // step1 : SoC (Separation of Concerns) �������� ���� �ڵ�
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
 * step2 : SoC (Separation of Concerns) ������ �ڵ� step2���� DB ������ ���� ���ɻ�� �и�
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
 * 
 * 
 * 
 * step3 : refactoring ���� ����
 * 
 * public abstract class UserDao { public void add(User user) throws
 * ClassNotFoundException, SQLException {
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
 * public abstract Connection getConnection() throws ClassNotFoundException,
 * SQLException;
 * 
 * }
 * 
 * 
 * 
 * step4 : Ư�� Ŭ���� ���� ����
 * 
 * public class UserDao {
 * 
 * private SimpleConnectionMaker simpleConnectionMaker;
 * 
 * public UserDao() { simpleConnectionMaker = new SimpleConnectionMaker(); }
 * 
 * public void add(User user) throws ClassNotFoundException, SQLException {
 * 
 * Connection c = simpleConnectionMaker.makeNewConnection();
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
 * Connection c = simpleConnectionMaker.makeNewConnection();
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
 * return user; } }
 * 
 * 
 * 
 * public class UserDao {
 * 
 * private ConnectionMaker connectionMaker;
 * 
 * 
 * step 5: ���輳�� �и��� public UserDao() { connectionMaker = new DConnectionMaker();
 * }
 * 
 * 
 * public UserDao() { }
 * 
 * public UserDao(ConnectionMaker connectionMaker) { this.connectionMaker =
 * connectionMaker; }
 * 
 * public void add(User user) throws ClassNotFoundException, SQLException {
 * 
 * Connection c = connectionMaker.makeConnection();
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
 * Connection c = connectionMaker.makeConnection();
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
 * return user; } }
 * 
 * 
 * 
 * step x : singtone �ڵ� ���Ե� ��ü
 * 
 * public class UserDao {
 * 
 * private static UserDao INSTANCE;
 * 
 * private ConnectionMaker connectionMaker;
 * 
 * private UserDao(ConnectionMaker connectionMaker) { this.connectionMaker =
 * connectionMaker; }
 * 
 * public static synchronized UserDao getInstance() { if (INSTANCE == null)
 * INSTANCE = new UserDao(null); return INSTANCE; }
 * 
 * public void add(User user) throws ClassNotFoundException, SQLException {
 * 
 * Connection c = connectionMaker.makeConnection();
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
 * Connection c = connectionMaker.makeConnection();
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
 * return user; } }
 * 
 * 
 * 
 * step 8 : DI, DL public class UserDao {
 * 
 * private ConnectionMaker connectionMaker;
 * 
 * private Connection c; private User user;
 * 
 * public UserDao() {
 * 
 * }
 * 
 * public UserDao(ConnectionMaker connectionMaker) { this.connectionMaker =
 * connectionMaker;
 * 
 * }
 * 
 * public void add(User user) throws ClassNotFoundException, SQLException {
 * 
 * this.c = connectionMaker.makeConnection();
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
 * this.c = connectionMaker.makeConnection();
 * 
 * PreparedStatement ps =
 * c.prepareStatement("select * from users where id = ?"); ps.setString(1, id);
 * 
 * ResultSet rs = ps.executeQuery();
 * 
 * rs.next();
 * 
 * this.user = new User(); this.user.setId(rs.getString("id"));
 * this.user.setName(rs.getString("name"));
 * this.user.setPassword(rs.getString("password"));
 * 
 * rs.close(); ps.close(); c.close();
 * 
 * return this.user; } }
 * 
 * 
 * 
 * step 9 : ������ �޼ҵ� DI ��� public class UserDao {
 * 
 * private ConnectionMaker connectionMaker;
 * 
 * private Connection c; private User user;
 * 
 * public void setConnectionMaker(ConnectionMaker connectionMaker) {
 * this.connectionMaker = connectionMaker; }
 * 
 * public void add(User user) throws ClassNotFoundException, SQLException {
 * 
 * this.c = connectionMaker.makeConnection();
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
 * this.c = connectionMaker.makeConnection();
 * 
 * PreparedStatement ps =
 * c.prepareStatement("select * from users where id = ?"); ps.setString(1, id);
 * 
 * ResultSet rs = ps.executeQuery();
 * 
 * rs.next();
 * 
 * this.user = new User(); this.user.setId(rs.getString("id"));
 * this.user.setName(rs.getString("name"));
 * this.user.setPassword(rs.getString("password"));
 * 
 * rs.close(); ps.close(); c.close();
 * 
 * return this.user; }
 * 
 * 
 * 
 * 2�� �׽�Ʈ ���� ����� public class UserDao {
 * 
 * private DataSource dataSource;
 * 
 * private Connection c;
 * 
 * public void setDataSource(DataSource dataSource) { this.dataSource =
 * dataSource; }
 * 
 * public void add(User user) throws SQLException {
 * 
 * this.c = dataSource.getConnection();
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
 * public User get(String id) throws SQLException {
 * 
 * this.c = dataSource.getConnection();
 * 
 * PreparedStatement ps =
 * c.prepareStatement("select * from users where id = ?"); ps.setString(1, id);
 * 
 * ResultSet rs = ps.executeQuery();
 * 
 * ����ó�� �ϱ� �� �ڵ� rs.next();
 * 
 * this.user = new User(); this.user.setId(rs.getString("id"));
 * this.user.setName(rs.getString("name"));
 * this.user.setPassword(rs.getString("password"));
 * 
 * User user = null;
 * 
 * if(rs.next()) { user = new User(); user.setId(rs.getString("id"));
 * user.setName(rs.getString("name"));
 * user.setPassword(rs.getString("password")); }
 * 
 * rs.close(); ps.close(); c.close();
 * 
 * if (user == null) throw new EmptyResultDataAccessException(1);
 * 
 * return user; }
 * 
 * public void deleteAll() throws SQLException { Connection c =
 * dataSource.getConnection();
 * 
 * PreparedStatement ps = c.prepareStatement("delete from users");
 * 
 * ps.executeUpdate();
 * 
 * ps.close(); c.close(); }
 * 
 * public int getCount() throws SQLException { Connection c =
 * dataSource.getConnection();
 * 
 * PreparedStatement ps = c.prepareStatement("select count(*) from users");
 * 
 * ResultSet rs = ps.executeQuery(); rs.next(); int count = rs.getInt(1);
 * 
 * rs.close(); ps.close(); c.close();
 * 
 * return count;
 * 
 * } }
 * 
 * 
 * 
 * ���ø� �޼ҵ� ������ �ڵ� public class UserDao {
 * 
 * private DataSource dataSource;
 * 
 * private Connection c;
 * 
 * public void setDataSource(DataSource dataSource) { this.dataSource =
 * dataSource; }
 * 
 * public void add(User user) throws SQLException {
 * 
 * this.c = dataSource.getConnection();
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
 * public User get(String id) throws SQLException {
 * 
 * this.c = dataSource.getConnection();
 * 
 * PreparedStatement ps =
 * c.prepareStatement("select * from users where id = ?"); ps.setString(1, id);
 * 
 * ResultSet rs = ps.executeQuery();
 * 
 * 
 * ����ó�� �ϱ� �� �ڵ� rs.next();
 * 
 * this.user = new User(); this.user.setId(rs.getString("id"));
 * this.user.setName(rs.getString("name"));
 * this.user.setPassword(rs.getString("password"));
 * 
 * User user = null;
 * 
 * if (rs.next()) { user = new User(); user.setId(rs.getString("id"));
 * user.setName(rs.getString("name"));
 * user.setPassword(rs.getString("password")); }
 * 
 * rs.close(); ps.close(); c.close();
 * 
 * if (user == null) throw new EmptyResultDataAccessException(1);
 * 
 * return user; }
 * 
 * public void deleteAll() throws SQLException {
 * 
 * Connection c = null; PreparedStatement ps = null;
 * 
 * try { c = dataSource.getConnection(); ps =
 * c.prepareStatement("delete from users"); ps = makeStatement(c);
 * ps.executeUpdate(); } catch (SQLException e) { throw e; } finally { if (ps !=
 * null) { try { ps.close(); } catch (SQLException e) { } } if (c != null) { try
 * { c.close(); } catch (SQLException e) { } } }
 * 
 * ps.close(); c.close(); }
 * 
 * public int getCount() throws SQLException {
 * 
 * Connection c = null; PreparedStatement ps = null; ResultSet rs = null;
 * 
 * try { c = dataSource.getConnection(); ps =
 * c.prepareStatement("select count(*) from users");
 * 
 * rs = ps.executeQuery(); rs.next(); return rs.getInt(1);
 * 
 * } catch (SQLException e) { throw e; } finally { if (rs != null) { try {
 * rs.close(); } catch (SQLException e) { } } if (ps != null) { try {
 * ps.close(); } catch (SQLException e) { } } if (c != null) { try { c.close();
 * } catch (SQLException e) { } } } }
 * 
 * private PreparedStatement makeStatement(Connection c) throws SQLException {
 * PreparedStatement ps; ps = c.prepareStatement("delete from users"); return
 * ps; } }
 * 
 * 
 * 
 * �������� ������ �ڵ� public class UserDao {
 * 
 * private DataSource dataSource;
 * 
 * private Connection c;
 * 
 * public void setDataSource(DataSource dataSource) { this.dataSource =
 * dataSource; }
 * 
 * public void add(User user) throws SQLException { StatementStrategy st = new
 * AddStatement(user); jdbcContextWithStatementStrategy(st);
 * 
 * }
 * 
 * public User get(String id) throws SQLException {
 * 
 * this.c = dataSource.getConnection();
 * 
 * PreparedStatement ps =
 * c.prepareStatement("select * from users where id = ?"); ps.setString(1, id);
 * 
 * ResultSet rs = ps.executeQuery(); User user = null;
 * 
 * if (rs.next()) { user = new User(); user.setId(rs.getString("id"));
 * user.setName(rs.getString("name"));
 * user.setPassword(rs.getString("password")); }
 * 
 * rs.close(); ps.close(); c.close();
 * 
 * if (user == null) throw new EmptyResultDataAccessException(1);
 * 
 * return user; }
 * 
 * public void deleteAll() throws SQLException { StatementStrategy st = new
 * DeleteAllStatement(); jdbcContextWithStatementStrategy(st); }
 * 
 * public int getCount() throws SQLException {
 * 
 * Connection c = null; PreparedStatement ps = null; ResultSet rs = null;
 * 
 * try { c = dataSource.getConnection(); ps =
 * c.prepareStatement("select count(*) from users");
 * 
 * rs = ps.executeQuery(); rs.next(); return rs.getInt(1);
 * 
 * } catch (SQLException e) { throw e; } finally { if (rs != null) { try {
 * rs.close(); } catch (SQLException e) { } } if (ps != null) { try {
 * ps.close(); } catch (SQLException e) { } } if (c != null) { try { c.close();
 * } catch (SQLException e) { } } } }
 * 
 * private PreparedStatement makeStatement(Connection c) throws SQLException {
 * PreparedStatement ps; ps = c.prepareStatement("delete from users"); return
 * ps; }
 * 
 * public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws
 * SQLException {
 * 
 * Connection c = null; PreparedStatement ps = null;
 * 
 * try { c = dataSource.getConnection();
 * 
 * ps = stmt.makepreparedStatement(c);
 * 
 * ps.executeUpdate();
 * 
 * } catch (SQLException e) { throw e; } finally { if (ps != null) { try {
 * ps.close(); } catch (SQLException e) { } } if (c != null) { try { c.close();
 * } catch (SQLException e) { } } } } }
 * 
 * 
 * 
 * public class UserDao {
 * 
 * private DataSource dataSource;
 * 
 * private Connection c;
 * 
 * public void setDataSource(DataSource dataSource) { this.dataSource =
 * dataSource; }
 * 
 * 
 * ���� Ŭ���� ������ �ڵ� public void add(final User user) throws SQLException { class
 * AddStatementLocal implements StatementStrategy {
 * 
 * ���� Ŭ�������� �ܺ� ������ �޾� ���� ���Ͽ� �ּ� ó�� �ܺ� ������ �޾ƾ��鼭 add(User user)�� �Ķ���͸� final�� ����
 * User user;
 * 
 * public AddStatementLocal(User user) { this.user = user; }
 * 
 * public PreparedStatement makePreparedStatement(Connection c) throws
 * SQLException { PreparedStatement ps =
 * c.prepareStatement("insert into users(id, name, password) values (?,?,?)");
 * ps.setString(1, user.getId()); ps.setString(2, user.getName());
 * ps.setString(3, user.getPassword()); return ps; } }
 * 
 * StatementStrategy st = new AddStatementLocal(user); StatementStrategy st =
 * new AddStatementLocal(); jdbcContextWithStatementStrategy(st);
 * 
 * }
 * 
 * 
 * public void add(final User user) throws SQLException {
 * 
 * �͸� Ŭ���� ����� �ڵ� 1
 * 
 * StatementStrategy st = new StatementStrategy() {
 * 
 * @Override public PreparedStatement makePreparedStatement(Connection c) throws
 * SQLException { PreparedStatement ps =
 * c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
 * ps.setString(1, user.getId()); ps.setString(2, user.getName());
 * ps.setString(3, user.getPassword()); return ps; } };
 * 
 * jdbcContextWithStatementStrategy(st);
 * 
 * 
 * jdbcContextWithStatementStrategy(new StatementStrategy() {
 * 
 * @Override public PreparedStatement makePreparedStatement(Connection c) throws
 * SQLException { PreparedStatement ps =
 * c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
 * ps.setString(1, user.getId()); ps.setString(2, user.getName());
 * ps.setString(3, user.getPassword()); return ps; } }); }
 * 
 * public User get(String id) throws SQLException {
 * 
 * this.c = dataSource.getConnection();
 * 
 * PreparedStatement ps =
 * c.prepareStatement("select * from users where id = ?"); ps.setString(1, id);
 * 
 * ResultSet rs = ps.executeQuery(); User user = null;
 * 
 * if (rs.next()) { user = new User(); user.setId(rs.getString("id"));
 * user.setName(rs.getString("name"));
 * user.setPassword(rs.getString("password")); }
 * 
 * rs.close(); ps.close(); c.close();
 * 
 * if (user == null) throw new EmptyResultDataAccessException(1);
 * 
 * return user; }
 * 
 * public void deleteAll() throws SQLException {
 * 
 * StatementStrategy st = new DeleteAllStatement();
 * jdbcContextWithStatementStrategy(st);
 * 
 * jdbcContextWithStatementStrategy(new StatementStrategy() {
 * 
 * @Override public PreparedStatement makePreparedStatement(Connection c) throws
 * SQLException { // TODO Auto-generated method stub return
 * c.prepareStatement("delete from users"); } }); }
 * 
 * public int getCount() throws SQLException {
 * 
 * Connection c = null; PreparedStatement ps = null; ResultSet rs = null;
 * 
 * try { c = dataSource.getConnection(); ps =
 * c.prepareStatement("select count(*) from users");
 * 
 * rs = ps.executeQuery(); rs.next(); return rs.getInt(1);
 * 
 * } catch (SQLException e) { throw e; } finally { if (rs != null) { try {
 * rs.close(); } catch (SQLException e) { } } if (ps != null) { try {
 * ps.close(); } catch (SQLException e) { } } if (c != null) { try { c.close();
 * } catch (SQLException e) { } } } }
 * 
 * private PreparedStatement makeStatement(Connection c) throws SQLException {
 * PreparedStatement ps; ps = c.prepareStatement("delete from users"); return
 * ps; }
 * 
 * public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws
 * SQLException {
 * 
 * Connection c = null; PreparedStatement ps = null;
 * 
 * try { c = dataSource.getConnection();
 * 
 * ps = stmt.makePreparedStatement(c);
 * 
 * ps.executeUpdate();
 * 
 * } catch (SQLException e) { throw e; } finally { if (ps != null) { try {
 * ps.close(); } catch (SQLException e) { } } if (c != null) { try { c.close();
 * } catch (SQLException e) { } } } } }
 * 
 * 
 * public class UserDao {
 * 
 * private DataSource dataSource;
 * 
 * private JdbcContext jdbcContext;
 * 
 * public void setDataSource(DataSource dataSource) {
 * 
 * this.jdbcContext = new JdbcContext();
 * 
 * this.jdbcContext.setDataSource(dataSource);
 * 
 * this.dataSource = dataSource;
 * 
 * }
 * 
 * public void setJdbcContext(JdbcContext jdbcContext) { this.jdbcContext =
 * jdbcContext; }
 * 
 * public void add(final User user) throws SQLException {
 * 
 * this.jdbcContext.workWithStatementStrategy(new StatementStrategy() { public
 * PreparedStatement makePreparedStatement(Connection c) throws SQLException {
 * PreparedStatement ps =
 * c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
 * ps.setString(1, user.getId()); ps.setString(2, user.getName());
 * ps.setString(3, user.getPassword()); return ps; } });
 * 
 * }
 * 
 * 
 * public void deleteAll() throws SQLException {
 * this.jdbcContext.workWithStatementStrategy(new StatementStrategy() { public
 * PreparedStatement makePreparedStatement(Connection c) throws SQLException {
 * return c.prepareStatement("delete from users"); } });
 * 
 * }
 * 
 * 
 * JdbcContext ���η� executeSql �Լ� �ű�� �� �ڵ� public void deleteAll() throws
 * SQLException { executeSql("delete from users"); }
 * 
 * 
 * public void deleteAll() throws SQLException {
 * this.jdbcContext.executeSql("delete from users"); }
 * 
 * public User get(String id) throws SQLException { return
 * this.jdbcContext.selectWithStatementStrategy(new StatementStrategy() { public
 * PreparedStatement makePreparedStatement(Connection c) throws SQLException {
 * PreparedStatement ps =
 * c.prepareStatement("select * from users where id = ?"); ps.setString(1, id);
 * return ps; } });
 * 
 * }
 * 
 * public int getCount() throws SQLException { return
 * this.jdbcContext.getCountStatementStrategy(new StatementStrategy() { public
 * PreparedStatement makePreparedStatement(Connection c) throws SQLException {
 * PreparedStatement ps = c.prepareStatement("select count(*) from users");
 * return ps; } }); }
 * 
 * private void executeSql(final String query) throws SQLException {
 * this.jdbcContext.workWithStatementStrategy(new StatementStrategy() { public
 * PreparedStatement makePreparedStatement(Connection c) throws SQLException {
 * return c.prepareStatement(query); } }); } }
 */

public class UserDao {

	private JdbcTemplate jdbcTemplate;

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);

		this.dataSource = dataSource;

	}

	public void deleteAll() throws SQLException {

		/*
		 * �ݹ� ��ü�� ����� ��� this.jdbcTemplate.update(new PreparedStatementCreator() {
		 * public PreparedStatement createPreparedStatement(Connection con) throws
		 * SQLException { return con.prepareStatement("delete from users"); } });
		 */

		this.jdbcTemplate.update("delete from users");
	}

	public void add(User user) throws SQLException {

		this.jdbcTemplate.update("insert into users(id, name, password) values (?,?,?)", user.getId(), user.getName(),
				user.getPassword());
	}

	public User get(String id) {
		return this.jdbcTemplate.queryForObject("select * from users where id= ?", new Object[] { id },
				new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setId(rs.getString("id"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						return user;
					}
				});
	}

	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id", new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getNString("id"));
				user.setName(rs.getNString("name"));
				user.setPassword(rs.getNString("Password"));
				return user;
			}
		});
	}

	public int getCount() {
		return this.jdbcTemplate.query(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement("select count(*) from users");
			}
		}, new ResultSetExtractor<Integer>() {
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return rs.getInt(1);
			}
		});

	}

}