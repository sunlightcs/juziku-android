package com.juziku.android.test;

import com.juziku.android.sqlite.GeneratedId;
import com.juziku.android.sqlite.PrimaryKey;

public class User {
	@GeneratedId
	@PrimaryKey
	private int id;
	private String name;
	private String email;
	private int sex;
	private int birthday;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getBirthday() {
		return birthday;
	}
	public void setBirthday(int birthday) {
		this.birthday = birthday;
	}
	
	
}
