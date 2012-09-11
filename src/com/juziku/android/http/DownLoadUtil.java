package com.juziku.android.http;

import java.io.InputStream;
import java.net.URL;

import com.juziku.android.io.FileUtil;

import android.content.Context;
import android.util.Log;

/**
 * 
 * @author steven
 *
 */
public class DownLoadUtil {
	private static final String TAG = DownLoadUtil.class.getSimpleName();
	
	public static InputStream inputStreamFromURL(Context context, String url) {
		InputStream is = null;
		try {
			URL imageUrl = new URL(url);
			is = imageUrl.openStream();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return is;
	}
	
	
	public static byte[] byteFromURL(Context context, String url) {
		byte[] b = null;
		try {
			InputStream is = inputStreamFromURL(context, url);
			b = FileUtil.readInputStream(is);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return b;
	}
	
	
	
	public static String stringFromURL(Context context, String url) {
		String str = null;
		try {
			InputStream is = inputStreamFromURL(context, url);
			str = FileUtil.inputStreamToString(is);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return str;
	}
}
