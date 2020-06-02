package org.river.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.scene.image.Image;

public class SQLUtils {
	public static int countSQL(Connection con, String table) {
		try {
			String sqlUpdata = "select COUNT(*) from " + table;
			PreparedStatement stat = con.prepareStatement(sqlUpdata);
			ResultSet rs = stat.executeQuery();
			while (rs.next())
				return rs.getInt(1);
		}
		catch (Exception e) {
			;
		}
		return -1;
	}

	public static void setInt(byte[] buffer, int offset, int number) {
		buffer[3 + offset] = (byte) (number & 0xFF);   
		buffer[2 + offset] = (byte) ((number >> 8) & 0xFF);   
		buffer[1 + offset] = (byte) ((number >> 16) & 0xFF);   
		buffer[0 + offset] = (byte) ((number >> 24) & 0xFF);
	}
	
	public static int fetchInt(byte[] buffer, int offset) {
		int out = 0;
		for (int i = 0; i < 4; i++) {
	        int shift = (4 - 1 - i) * 8;
	        out += (buffer[i + offset] & 0x000000FF) << shift;
	    }
		return out;
	}

}
