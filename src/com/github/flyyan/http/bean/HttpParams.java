package com.github.flyyan.http.bean;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpParams {

	private String url ;
	private String referer ;
	
	private Map<String ,String > params = new HashMap<String, String>();

	private List<String> paramsName = new ArrayList<String>();
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public List<String> getParamsName() {
		return paramsName;
	}

	public void setParamsName(List<String> paramsName) {
		this.paramsName = paramsName;
	}
	
	
}
