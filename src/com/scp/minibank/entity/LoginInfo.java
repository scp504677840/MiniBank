package com.scp.minibank.entity;

import org.litepal.crud.DataSupport;

public class LoginInfo extends DataSupport {

	private int login_id;// ����ID
	private String login_name;// �û���
	private String login_pwd;// ����
	private String login_nickname;// �ǳ�
	private int login_icon;// ͷ��

	public int getLogin_id() {
		return login_id;
	}

	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getLogin_pwd() {
		return login_pwd;
	}

	public void setLogin_pwd(String login_pwd) {
		this.login_pwd = login_pwd;
	}

	public String getLogin_nickname() {
		return login_nickname;
	}

	public void setLogin_nickname(String login_nickname) {
		this.login_nickname = login_nickname;
	}

	public int getLogin_icon() {
		return login_icon;
	}

	public void setLogin_icon(int login_icon) {
		this.login_icon = login_icon;
	}

}
