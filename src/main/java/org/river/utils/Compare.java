package org.river.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.scene.image.Image;

public class Compare {
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
	
	public static boolean isSame(String a, String b) {
		if (a.length() == b.length()) {
			for (int i = 0; i < a.length(); i++)
				if (a.charAt(i) != b.charAt(i))
					return false;
			return true; 
		}
		else
			return false;
	}
	public static boolean isSame(Image a, Image b) {
		if (a == null || b == null)
			return false;
		if (a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight())
			return false;
		else
			for (int i = 0; i < a.getWidth(); i++)
				for (int j = 0; j < a.getHeight(); j++)
					if (!a.getPixelReader().getColor(i, j).equals(b.getPixelReader().getColor(i, j)))
						return false;
		return true;
	}
}
