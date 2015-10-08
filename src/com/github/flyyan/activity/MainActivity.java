package com.github.flyyan.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.flyyan.R;
import com.github.flyyan.adapter.ReposAdapter;
import com.github.flyyan.http.bean.HttpParams;
import com.github.flyyan.http.bean.RepoItem;
import com.github.flyyan.http.tool.HtmlParserTool;
import com.github.flyyan.http.tool.HttpUtil;
import com.github.flyyan.view.AutoListView;
import com.github.flyyan.view.AutoListView.OnLoadListener;

public class MainActivity extends Activity implements OnClickListener,
		OnLoadListener, OnItemClickListener {

	private AutoListView llRepos;
	private EditText etSearch;
	private Button btSearch;

	List<RepoItem> repos = new ArrayList<RepoItem>();

	private ReposAdapter adapter;

	private String searchText;
	/** 当前页号 */
	private int currentPageNum = 0;
	/** 总页数 */
	private int totalPageNum = 0;
	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				adapter = new ReposAdapter(repos, MainActivity.this);
				llRepos.setAdapter(adapter);
				break;
			case 1:
				adapter.notifyDataSetChanged();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		llRepos = (AutoListView) findViewById(R.id.ll_main_repos);
		btSearch = (Button) findViewById(R.id.bt_main_seach);
		etSearch = (EditText) findViewById(R.id.et_main_search);

		btSearch.setOnClickListener(this);

		llRepos.setOnLoadListener(this);
		llRepos.setOnItemClickListener(this);
	}

	
	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		intent.setClass(this, ReposDetailActivity.class);
		intent.putExtra("url", "http://github.com"+repos.get(position).getPath());
		
		startActivity(intent);
	}
	
	@Override
	public void onClick(View v) {

		searchText = etSearch.getText().toString();

		if (TextUtils.isEmpty(searchText)) {
			Toast.makeText(this, "请输入查询的内容", Toast.LENGTH_LONG).show();
			return;
		}

		if (!isNetworkAvailable()) {

			Toast.makeText(this, "请连接网络", Toast.LENGTH_LONG).show();
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {

				HttpClient httpClient = new DefaultHttpClient();

				HttpParams params = new HttpParams();

				params.setUrl("https://github.com/search?utf8=%E2%9C%93&q="
						+ searchText);
				params.setReferer("https://www.github.com");

				String content = "";
				HttpResponse response = HttpUtil.get(httpClient, params);
				try {
					content = EntityUtils.toString(response.getEntity());
				} catch (Exception e1) {
				}

				repos = HtmlParserTool.parseHtml2Repos(content);
				totalPageNum = HtmlParserTool.getPageNumber(content);
				currentPageNum = 1;
				myHandler.sendEmptyMessage(0);

			}
		}).start();

	}

	@Override
	public void onLoad() {
		if (!isNetworkAvailable()) {

			Toast.makeText(this, "请连接网络", Toast.LENGTH_LONG).show();
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {

				HttpClient httpClient = new DefaultHttpClient();

				HttpParams params = new HttpParams();
				params.setUrl("https://github.com/search?utf8=%E2%9C%93&ref=searchresults&type=Repositories&q="
						+ searchText + "&p=" + (currentPageNum + 1));
				params.setReferer("https://www.github.com");
				if (currentPageNum == totalPageNum)
					llRepos.setLoadEnable(false);
				String content = "";
				HttpResponse response = HttpUtil.get(httpClient, params);
				if (response.getStatusLine().getStatusCode() != 200) {
					Toast.makeText(MainActivity.this, "查询出错!",
							Toast.LENGTH_LONG).show();
				}
				try {
					content = EntityUtils.toString(response.getEntity());
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				List<RepoItem> reposNew = HtmlParserTool
						.parseHtml2Repos(content);

				for (RepoItem repoItem : reposNew) {
					repos.add(repoItem);
				}
				myHandler.sendEmptyMessage(1);
				currentPageNum++;
			}
		}).start();
	}

	/**
	 * 判断是否联网
	 * 
	 * @return
	 */
	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
		} else {
			return cm.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}

}
