package com.springbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import com.springbook.domain.User;

public class JdbcContext {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = this.dataSource.getConnection();

			ps = stmt.makePreparedStatement(c);

			ps.executeUpdate();
		} catch (Exception e) {

			throw e;

		} finally {

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}

			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
				}

			}
		}

	}

	public User selectWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User getUser = null;

		try {
			c = this.dataSource.getConnection();

			ps = stmt.makePreparedStatement(c);
			rs = ps.executeQuery();

			while (rs.next()) {
				getUser = new User();
				getUser.setId(rs.getString("id"));
				getUser.setName(rs.getString("name"));
				getUser.setPassword(rs.getString("password"));
			}

			if (getUser == null)
				throw new EmptyResultDataAccessException(1);
			return getUser;

		} catch (Exception e) {

			throw e;

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {

				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}

			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
				}

			}
		}
	}

	public int getCountStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;

		try {
			c = this.dataSource.getConnection();

			ps = stmt.makePreparedStatement(c);
			rs = ps.executeQuery();

			if (rs.next())
				count = rs.getInt(1);

			return count;

		} catch (Exception e) {

			throw e;

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {

				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}

			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
				}

			}
		}
	}

	public void executeSql(final String query) throws SQLException {
		workWithStatementStrategy(new StatementStrategy() {
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				return c.prepareStatement(query);
			}
		});
	}
}
