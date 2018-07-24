package jp.gmo.ojt.chachat.form;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LoginForm implements Serializable {
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
