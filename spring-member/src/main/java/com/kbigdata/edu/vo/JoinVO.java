package com.kbigdata.edu.vo;

public class JoinVO {
	private String id;
	private String pw;
	private String username;
	

	
	public JoinVO() {
		
	}

	public JoinVO(String id, String pw, String name) {
		this.id=id;
		this.pw=pw;
		this.username = name;
	}
	
	public String getUserName() {
		return username;
	}

	public void setUserName(String name) {
		this.username = name;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
}