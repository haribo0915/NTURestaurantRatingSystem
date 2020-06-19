package org.river.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.river.exceptions.*;

public class DBConnect {
	private static Connection con = null;
	
	public DBConnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = 
			DriverManager.getConnection("jdbc:mysql://localhost:3306/ntu_restaurant_rating_system?serverTimezone=UTC"
					, "root", "root");
		}catch (Exception e) {}
	}
	
	public static Connection getConnect() {
		try {
			if (con == null) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ntu_restaurant_rating_system?serverTimezone=UTC"
								, "root", "root");
				return con;
			}
			else
				return con;
		} catch (Exception e) {}
		return null;
	}
	
}
