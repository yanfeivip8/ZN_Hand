package com.github.flyyan.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.flyyan.R;
import com.github.flyyan.http.bean.RepoCommitItem;

public class ReposCommitsAdapter extends BaseAdapter{

	private List<RepoCommitItem> commits ;
	private Context context ;
	public ReposCommitsAdapter(List<RepoCommitItem> commits , Context context){
		this.commits = commits ;
		this.context  = context ;
	}
	@Override
	public int getCount() {
		return commits.size();
	}

	@Override
	public Object getItem(int position) {
		return commits.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder= null ;
		if(convertView != null){
			holder = (ViewHolder) convertView.getTag();
		}else{
			holder = new ViewHolder();
		}
		
		View view = View.inflate(context, R.layout.repos_commits_item, null);
		holder.ivIcon = (ImageView) view.findViewById(R.id.iv_repos_commits_item_icon);
		holder.tvAuthor = (TextView) view.findViewById(R.id.tv_repos_commits_item_author);
		holder.tvDate = (TextView) view.findViewById(R.id.tv_repos_commits_item_time);
		holder.tvLog = (TextView) view.findViewById(R.id.tv_repos_commits_update_changelog);
		
		RepoCommitItem item = commits.get(position);
		
		Bitmap bm = item.getBmIcon() ;
		if(bm == null){
			holder.ivIcon.setImageResource(R.drawable.resps_icon);
		}else{
			holder.ivIcon.setImageBitmap(bm);
		}
		holder.tvAuthor.setText(item.getAuthorName());
		holder.tvDate.setText(item.getUpdateTime());
		holder.tvLog.setText(item.getUpdateChangeLog());
		view.setTag(holder);
		return view;
	}
	
	class ViewHolder{
		ImageView ivIcon ;
		TextView tvLog ;
		TextView tvAuthor ;
		TextView tvDate ;
	}

}
