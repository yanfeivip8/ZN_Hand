package com.github.flyyan.fragment;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.github.flyyan.R;
import com.github.flyyan.adapter.ReposCommitsAdapter;
import com.github.flyyan.http.bean.HttpParams;
import com.github.flyyan.http.bean.RepoCommitItem;
import com.github.flyyan.http.tool.HtmlParserTool;
import com.github.flyyan.http.tool.HttpUtil;

@SuppressLint("ValidFragment")
public class CommitFragment extends Fragment {

	private View view;
	private ListView lvCommits;

	private ReposCommitsAdapter adapter;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				adapter = new ReposCommitsAdapter(commitItems, getActivity());
				lvCommits.setAdapter(adapter);
				break;

			default:
				break;
			}
		};
	};

	private List<RepoCommitItem> commitItems;
	private String url;

	@SuppressLint("ValidFragment")
	public CommitFragment(String url) {
		this.url = url;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 开启线程获取数据
		super.onCreate(savedInstanceState);
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpClient httpClient = new DefaultHttpClient();

				HttpParams params = new HttpParams();

				params.setUrl(url);
				params.setReferer("https://www.github.com");

				HttpResponse response = HttpUtil.get(httpClient, params);
				if (response.getStatusLine().getStatusCode() == 200) {

					try {
						// 获取commits的请求路径
						String content = EntityUtils.toString(response
								.getEntity());
						String path = HtmlParserTool.getCommitsPath(content);
						params.setUrl("https://www.github.com" + path);
						params.setReferer("https://www.github.com");
						response = HttpUtil.get(httpClient, params);
						if (response.getStatusLine().getStatusCode() == 200) {
							content = EntityUtils.toString(response.getEntity());
							commitItems = HtmlParserTool
									.parseHtml2ReposCommits(content);
							if (commitItems != null && commitItems.size() > 0)
								handler.sendEmptyMessage(0);
						}
					} catch (Exception e) {
					}
				} else {
					Toast.makeText(CommitFragment.this.getActivity(), "联网失败",
							Toast.LENGTH_LONG).show();
				}
			}
		}).start();
		;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.repos_commits, container, false);
		lvCommits = (ListView) view.findViewById(R.id.lv_repos_commits);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
