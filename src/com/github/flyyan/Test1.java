package com.github.flyyan;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.test.AndroidTestCase;
import android.util.Log;

import com.github.flyyan.http.bean.HttpParams;
import com.github.flyyan.http.bean.RepoItem;
import com.github.flyyan.http.tool.HtmlParserTool;
import com.github.flyyan.http.tool.HttpUtil;

public class Test1 extends AndroidTestCase {

	public void testName() throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpParams params = new HttpParams();
		params.setUrl("https://github.com/search?utf8=%E2%9C%93&q=123");
		params.setReferer("https://www.github.com");
		HttpResponse response = HttpUtil.get(httpClient, params);
		String content = EntityUtils.toString(response.getEntity());

		Log.i("github", "" + response.getStatusLine().getStatusCode());
		Log.i("github", "" + content);
		
		
		List<RepoItem> repos = HtmlParserTool.parseHtml2Repos(content);
		Log.i("github", "" + repos.size());
		
		assertEquals(false, 200==response.getStatusLine().getStatusCode());
		
	}
}
