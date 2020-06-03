package org.river.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnect {
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public DBConnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = 
			DriverManager.getConnection("jdbc:mysql://localhost:3306/ntu_restaurant_rating_system?serverTimezone=UTC"
					, "root", "root");
			st = con.createStatement();
		}catch (Exception e) {}
	}
	
	public Connection getConnect() {
		return con;
	}
	
	public Statement getStatement() {
		return st;
	}
}
