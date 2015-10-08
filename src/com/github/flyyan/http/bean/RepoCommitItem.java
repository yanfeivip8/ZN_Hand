package com.github.flyyan.http.bean;

import android.graphics.Bitmap;

public class RepoCommitItem {

	private String updateChangeLog ;
	
	private String authorName ;
	
	private String updateTime ;
	
	private String icon ;

	private Bitmap bmIcon ;
	public Bitmap getBmIcon() {
		return bmIcon;
	}

	public void setBmIcon(Bitmap bmIcon) {
		this.bmIcon = bmIcon;
	}

	public String getUpdateChangeLog() {
		return updateChangeLog;
	}

	public void setUpdateChangeLog(String updateChangeLog) {
		this.updateChangeLog = updateChangeLog;
	}


	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}


	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
