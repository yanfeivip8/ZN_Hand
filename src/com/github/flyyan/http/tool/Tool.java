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

	/** ��ȡ��֤�� */
	public final static String GET_VALIDATE_CODE = "validatecode";
	/** �û���¼ */
	public final static String LOGIN = "login";
	/** ��ȡ�ɼ� */
	public final static String GET_GRADE = "getGrade";
	
	public final static String GET_COURSE = "getCourse";

	
	/** �����ļ���·�� */
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
	 * ��������ķ�ʽ��ȡ�����·���Ͳ�����Ϣ
	 * 
	 * @param type
	 * @return
	 * @throws DocumentException
	 */
	public static HttpParams getHttpParamsByType(String type)
			throws DocumentException {
		// sax������
		SAXReader reader = new SAXReader();
		
		Document document = reader.read(new File(path));
		// ��ȡ��Ԫ��
		Element rootElement = document.getRootElement();

		/*
		 * ������Ԫ�ص�ֱ����Ԫ�أ��ҵ������봫���������ͬ��Ԫ��
		 */
		Iterator<Element> it = rootElement.elementIterator();
		Element e = null;
		while (it.hasNext()) {
			e = it.next();
			if (e.attributeValue("name").equals(type)) {
				// �ҵ�������ѭ������ʱe��Ϊ�ҵ���Ԫ��
				break;
			}
		}

		/*
		 * �����ҵ���Ŀ��Ԫ�ص���Ԫ�أ���װΪHttpParams����
		 */
		it = e.elementIterator();
		HttpParams params = new HttpParams();
		while (it.hasNext()) {
			Element e1 = it.next();
			String name = e1.getName();
			// ��һ��װ
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
	 * �����ļ�������֤��ͼƬ�����ڱ��أ�
	 * 
	 * @param is
	 */
	public static void writeCodeImage(InputStream is, String path) {

		File f = new File(path);

		if (f.exists()) {
			f.delete();
		}
		// ��ӡ·��
		// ��ͼƬ��д��ͼƬ�ļ�
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] buff = new byte[1024];

		int len = 0;
		// д��\
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
