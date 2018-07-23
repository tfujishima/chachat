package jp.gmo.ojt.chachat.form;

import java.io.Serializable;

import jp.gmo.ojt.chachat.session.SessionUser;

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
