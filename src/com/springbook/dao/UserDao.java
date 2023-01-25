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
 * step2 : SoC (Separation of Concerns) 적용한 코드 step2에서 DB 연결을 공통 관심사로 분리
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
 * step3 : refactoring 개념 적용
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
 * step4 : 특정 클래스 별도 생성
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
 * step 5: 관계설정 분리전 public UserDao() { connectionMaker = new DConnectionMaker();
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
 * step x : singtone 코드 포함된 객체
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
 * step 9 : 수정자 메소드 DI 방식 public class UserDao {
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
 * 2장 테스트 까지 사용함 public class UserDao {
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
 * 예외처리 하기 전 코드 rs.next();
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
 * 템플릿 메소드 적용한 코드 public class UserDao {
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
 * 예외처리 하기 전 코드 rs.next();
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
 * 전략패턴 적용한 코드 public class UserDao {
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
 * 로컬 클래스 적용한 코드 public void add(final User user) throws SQLException { class
 * AddStatementLocal implements StatementStrategy {
 * 
 * 내부 클래스에서 외부 정보를 받아 쓰기 위하여 주석 처리 외부 변수로 받아쓰면서 add(User user)의 파라메터를 final로 수정
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
 * 익명 클래스 사용한 코드 1
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
 * JdbcContext 내부로 executeSql 함수 옮기기 전 코드 public void deleteAll() throws
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
		 * 콜백 객체로 사용할 경우 this.jdbcTemplate.update(new PreparedStatementCreator() {
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