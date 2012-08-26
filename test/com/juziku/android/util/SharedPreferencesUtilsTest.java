package com.juziku.android.util;

import android.test.AndroidTestCase;

public class SharedPreferencesUtilsTest extends AndroidTestCase {

	public void testPutString() {
		SharedPreferencesUtils share = new SharedPreferencesUtils(getContext());
		boolean flag = share.putString("name", "测试");
		assertTrue(flag);
	}

	public void testGetString() {
		SharedPreferencesUtils share = new SharedPreferencesUtils(getContext());
		String name = share.getString("name", null);
		assertNotNull(name);
	}

}
