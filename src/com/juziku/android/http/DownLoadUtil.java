package com.juziku.android.http;

import java.io.InputStream;
import java.net.URL;

import android.util.Log;

import com.juziku.android.io.FileUtil;

/**
 * 
 * @author steven
 *
 */
public class DownLoadUtil {
	private static final String TAG = DownLoadUtil.class.getSimpleName();
	
	public static InputStream inputStreamFromURL(String url) {
		InputStream is = null;
		try {
			URL imageUrl = new URL(url);
			is = imageUrl.openStream();
		} catch (Exception e) {
			Log.e(TAG, "URL地址不存在，请检查URL地址。  " + e.getMessage());
		}
		return is;
	}
	
	
	public static byte[] byteFromURL(String url) {
		byte[] b = null;
		try {
			InputStream is = inputStreamFromURL(url);
			b = FileUtil.readInputStream(is);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return b;
	}
	
	
	
	public static String stringFromURL(String url) {
		String str = null;
		try {
			InputStream is = inputStreamFromURL(url);
			str = FileUtil.inputStreamToString(is);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return str;
	}
}
