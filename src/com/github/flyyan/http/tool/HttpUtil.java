package com.github.flyyan.http.tool;


import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import com.github.flyyan.http.bean.HttpParams;

public final class HttpUtil {

	/**
	 * 
	 * 发送post请求
	 */
	public static HttpResponse post(HttpClient client,
			HttpParams params) throws Exception {
		HttpGet getMethod = new HttpGet(params.getUrl());  

		return client.execute(getMethod);

	}

	public static HttpResponse get(HttpClient client, HttpParams params) {
		HttpGet get = new HttpGet(params.getUrl());
		// 设置请求头
		get.addHeader("Referer", params.getReferer());
		// 发送请求
		try {
			return client.execute(get);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}
}
