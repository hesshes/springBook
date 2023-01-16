package com.springbook.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {

	private int count = 0;
	private ConnectionMaker realConnectionMaker;

	public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
		this.realConnectionMaker = realConnectionMaker;
	}

	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		this.count++;
		return realConnectionMaker.makeConnection();
	}
	
	public int getCounter() {
		return this.count;
	}

}
