package com.vanhack.forum.restapi;

public abstract class AppRequest {
	
	private String loginNickname;
	
	private String loginPassword;

	public String getLoginNickname() {
		return loginNickname;
	}

	public void setLoginNickname(String loginNickname) {
		this.loginNickname = loginNickname;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
}
