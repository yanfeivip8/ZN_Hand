package com.github.flyyan.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.flyyan.R;
import com.github.flyyan.http.bean.RepoItem;

public class ReposAdapter extends BaseAdapter {

	private List<RepoItem> repos;
	private Context context;

	private LayoutInflater inflater;

	public ReposAdapter(List<RepoItem> repos, Context context) {
		this.repos = repos;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return repos.size();
	}

	@Override
	public Object getItem(int position) {
		return repos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = inflater.inflate(R.layout.repos_item, null);
		ViewHolder holder = null ;
		if (convertView != null)
			holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
		}
		holder.tvName = (TextView) view.findViewById(R.id.tv_repos_item_name);
		holder.tvDesc = (TextView) view.findViewById(R.id.tv_repos_item_desc);
		holder.tvLanguage = (TextView) view
				.findViewById(R.id.tv_repos_item_language);
		holder.tvStarts = (TextView) view
				.findViewById(R.id.tv_repos_item_starts);
		holder.tvForks = (TextView) view.findViewById(R.id.tv_repos_item_forks);

		holder.tvName.setText(repos.get(position).getPath());
		holder.tvDesc.setText(repos.get(position).getDescription());
		holder.tvLanguage.setText(repos.get(position).getLanguage());
		holder.tvStarts.setText(repos.get(position).getStargazers());
		holder.tvForks.setText(repos.get(position).getForks());

		view.setTag(holder);

		return view;
	}

	class ViewHolder {
		TextView tvDesc;
		TextView tvLanguage;
		TextView tvStarts;
		TextView tvForks;
		TextView tvName;
	}

}
