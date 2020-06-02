package org.river.utils;

import javafx.scene.image.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class ImageUtils {
	public static Image BlobToImage(Blob b) throws Exception {
		if (b == null)
			return null;
		
		int w = SQLUtils.fetchInt(b.getBytes(1, 4), 0);
		int h = SQLUtils.fetchInt(b.getBytes(1, 4), 4);
		byte[] data = b.getBytes(9, w*h*4);
		
		WritableImage output = new WritableImage(w, h);
		PixelWriter pw = output.getPixelWriter();
		pw.setPixels(0, 0, w, h, PixelFormat.getByteBgraInstance(), data, 8, w*4);
		
		return output;
	}
	
	public static Image ByteArrayToImage(byte[] data) throws Exception {
		if (data == null)
			return null;
		
		int w = SQLUtils.fetchInt(data, 0);
		int h = SQLUtils.fetchInt(data, 4);
		System.out.println("w: " + w + ", h: " + h);
		
		WritableImage output = new WritableImage(w, h);
		PixelWriter pw = output.getPixelWriter();
		pw.setPixels(0, 0, w, h, PixelFormat.getByteBgraInstance(), data, 8, w*4);
		
		return output;
	}
	
	public static InputStream ImageToInputStream(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		byte[] buffer = new byte[8 + w * h * 4];
		
		SQLUtils.setInt(buffer, 0, w);
		SQLUtils.setInt(buffer, 4, h);
		
		img.getPixelReader().getPixels
			(0, 0, w, h, PixelFormat.getByteBgraInstance(), buffer, 8, w*4);

		return new ByteArrayInputStream(buffer);
	}
	
	public static byte [] ImageToByteArray(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		byte[] buffer = new byte[8 + w * h * 4];
		
		SQLUtils.setInt(buffer, 0, w);
		SQLUtils.setInt(buffer, 4, h);
		
		img.getPixelReader().getPixels
			(0, 0, w, h, PixelFormat.getByteBgraInstance(), buffer, 8, w*4);
		return buffer;
	}
	
	public static boolean isSame(Image a, Image b) {
		if (a == null || b == null)
			return false;
		else if (a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight())
			return false;
		else
			for (int i = 0; i < a.getWidth(); i++)
				for (int j = 0; j < a.getHeight(); j++)
					if (!a.getPixelReader().getColor(i, j).equals(b.getPixelReader().getColor(i, j)))
						return false;
		return true;
	}
}