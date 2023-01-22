package com.springbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.springbook.domain.User;

public abstract class AddStatement implements StatementStrategy {
	User user;

	public AddStatement(User user) {
		this.user = user;
	}

	public PreparedStatement makepreparedStatement(Connection c) throws SQLException {
		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
		ps.setNString(1, user.getId());
		ps.setNString(2, user.getName());
		ps.setNString(3, user.getPassword());
		return ps;
	}

}
