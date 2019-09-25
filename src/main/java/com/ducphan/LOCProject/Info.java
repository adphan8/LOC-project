package com.ducphan.LOCProject;

import java.io.File;

public class Info {
	File input;
	String id;
	String password;
	String url;
	String startDate;
	String endDate;
	
	public Info(String id, String password, String url, String startDate, String endDate, File input) {
		super();
		this.id = id;
		this.password = password;
		this.url = url;
		this.startDate = startDate;
		this.endDate = endDate;
		this.input = input;
	}

	public File getInput() {
		return input;
	}

	public void setInput(File input) {
		this.input = input;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	
	
	
}
