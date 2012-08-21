package com.juziku.android.http;

import java.io.InputStream;

import com.juziku.android.io.FileUtil;

import android.content.Context;
import android.util.Log;

public class DownLoadUtil {
	private static final String TAG = DownLoadUtil.class.getSimpleName();
	
	public static InputStream inputStreamFromURL(Context context, String url) {
		InputStream is = null;
		try {
			is = context.getAssets().open(url);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return is;
	}
	
	
	public static byte[] byteFromURL(Context context, String url) {
		byte[] b = null;
		try {
			InputStream is = context.getAssets().open(url);
			b = FileUtil.readInputStream(is);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return b;
	}
	
	
	
	public static String stringFromURL(Context context, String url) {
		String str = null;
		try {
			InputStream is = context.getAssets().open(url);
			str = FileUtil.inputStreamToString(is);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return str;
	}
}
