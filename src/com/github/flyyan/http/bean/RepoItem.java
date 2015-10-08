package com.github.flyyan.http.bean;

public class RepoItem {

	private String  path ; 
	private String normalName ;
	private String emName ;
	private String description;

	private String Stargazers ;
	private String Forks ;
	private String language ;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getNormalName() {
		return normalName;
	}
	public void setNormalName(String normalName) {
		this.normalName = normalName;
	}
	public String getEmName() {
		return emName;
	}
	public void setEmName(String emName) {
		this.emName = emName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getStargazers() {
		return Stargazers;
	}
	public void setStargazers(String stargazers) {
		Stargazers = stargazers;
	}
	public String getForks() {
		return Forks;
	}
	public void setForks(String forks) {
		Forks = forks;
	}
	
}
