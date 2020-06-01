package org.river.utils;

import javafx.scene.image.*;
import java.sql.*;
import java.util.*;
import java.io.*;


public class ImageTransform {
	public static Image BlobToImage(Blob b) throws Exception {
		if (b == null)
			return null;
		byte[] data = b.getBytes(1,(int) b.length());
		Image img = new Image(new ByteArrayInputStream(data));
		return img;
	}
	
	public static InputStream ImageToInputStream(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		byte[] buffer = new byte[w * h * 4];
		
		img.getPixelReader().getPixels
			(0, 0, w, h, PixelFormat.getByteBgraInstance(), buffer, 0, w*4);

		return new ByteArrayInputStream(buffer);
	}
}