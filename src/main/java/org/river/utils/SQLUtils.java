package org.river.utils;

import org.river.entities.Restaurant;
import org.river.exceptions.ResourceNotFoundException;
import org.river.models.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

	// TODO 別放UTILS謝謝 BY架構之王
	public static Restaurant queryRestaurant(int id) throws ResourceNotFoundException {
			DBConnect DBC = new DBConnect();
			Connection con = DBC.getConnect();
			Restaurant out = null;

			try {
				String sqlQuery = "select * from restaurant where id=?";
				PreparedStatement stat = con.prepareStatement(sqlQuery);
				stat.setInt(1, id);
				ResultSet rs = stat.executeQuery();

				while (rs.next())
					out = new Restaurant(id, rs.getInt("area_id"),
							rs.getInt("food_category_id"),	rs.getString("name"),
							rs.getString("description"), rs.getString("image"),
							rs.getString("address"));
			} catch (Exception e) {

			}

			if (out == null)
				throw new ResourceNotFoundException("queryRestaurant error: utils");
			return out;
	}

}
