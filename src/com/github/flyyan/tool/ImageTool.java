package com.github.flyyan.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public final class ImageTool {

	public static Bitmap getImage(String path)  {
		byte[] data = null ;
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET"); // �������󷽷�ΪGET
			conn.setReadTimeout(5 * 1000); // ���������ʱʱ��Ϊ5��
			InputStream inputStream = conn.getInputStream(); // ͨ�����������ͼƬ����
			data = readInputStream(inputStream);
		} catch (Exception e) {
			return null ;
		}

		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length); // ����λͼ
		return bitmap;

	}

	public static byte[] readInputStream(InputStream inputStream)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();

	}
}
