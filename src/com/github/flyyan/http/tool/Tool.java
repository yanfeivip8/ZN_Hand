package com.github.flyyan.http.tool;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.github.flyyan.http.bean.HttpParams;

public class Tool {

	/** 获取验证码 */
	public final static String GET_VALIDATE_CODE = "validatecode";
	/** 用户登录 */
	public final static String LOGIN = "login";
	/** 获取成绩 */
	public final static String GET_GRADE = "getGrade";
	
	public final static String GET_COURSE = "getCourse";

	
	/** 配置文件的路径 */
	private static String path = Tool.class.getClassLoader()
			.getResource("com/jsoup/config/httpConfig_github.xml").getPath();;

	
	static{
		try {
			path = URLDecoder.decode(path, "UTF-8");
			System.out.println(path);
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	/**
	 * 根据请求的方式获取请求的路径和参数信息
	 * 
	 * @param type
	 * @return
	 * @throws DocumentException
	 */
	public static HttpParams getHttpParamsByType(String type)
			throws DocumentException {
		// sax解析器
		SAXReader reader = new SAXReader();
		
		Document document = reader.read(new File(path));
		// 获取根元素
		Element rootElement = document.getRootElement();

		/*
		 * 遍历根元素的直接子元素，找到类型与传入的类型相同的元素
		 */
		Iterator<Element> it = rootElement.elementIterator();
		Element e = null;
		while (it.hasNext()) {
			e = it.next();
			if (e.attributeValue("name").equals(type)) {
				// 找到则跳出循环，此时e即为找到的元素
				break;
			}
		}

		/*
		 * 遍历找到的目标元素的子元素，封装为HttpParams对象
		 */
		it = e.elementIterator();
		HttpParams params = new HttpParams();
		while (it.hasNext()) {
			Element e1 = it.next();
			String name = e1.getName();
			// 逐一封装
			if ("url".equalsIgnoreCase(name)) {
				params.setUrl(e1.getTextTrim());
			} else if ("referer".equalsIgnoreCase(name)) {
				params.setReferer(e1.getTextTrim());
			} else if ("params".equalsIgnoreCase(name)) {
				Iterator<Element> i = e1.elementIterator();
				while (i.hasNext()) {
					params.getParamsName().add(i.next().getTextTrim());
				}
			}
		}
		return params;
	}

	/**
	 * 根据文件流将验证码图片保存在本地，
	 * 
	 * @param is
	 */
	public static void writeCodeImage(InputStream is, String path) {

		File f = new File(path);

		if (f.exists()) {
			f.delete();
		}
		// 打印路径
		// 将图片流写入图片文件
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] buff = new byte[1024];

		int len = 0;
		// 写入\
		try {
			while ((len = is.read(buff)) > 0) {
				fos.write(buff, 0, len);
			}
			System.out.println("-------------");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
