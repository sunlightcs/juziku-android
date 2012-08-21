package com.juziku.android.test;

import com.juziku.android.sqlite.SqliteTemplate;

import android.content.Context;

public class UserDao extends SqliteTemplate<User>{

	public UserDao(Context context) {
		super(context);
	}
	
}
