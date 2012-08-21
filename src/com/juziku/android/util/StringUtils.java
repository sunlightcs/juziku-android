package com.juziku.android.util;


public class StringUtils {
	
	public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

	public static boolean isNotEmpty(CharSequence cs) {
        return !StringUtils.isEmpty(cs);
    }
	
	public static String trim(String str) {
        return str == null ? null : str.trim();
    }
	
}
