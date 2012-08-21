package com.juziku.android.json;

import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.juziku.android.http.DownLoadUtil;
import com.juziku.android.io.FileUtil;


/**
 * 解析JSON
 *
 */
public class JsonParse {
	private static final String TAG = JsonParse.class.getSimpleName();
	
	/**
	 * 根据URL地址，获取List列表
	 * 
	 * @param classz   类字节码 如:Object.class
	 * @param url      url地址
	 * @return         返回List列表
	 */
	public static List<?> getListFromNetwork(Class<?> clazz, Context context, String url) {
		List<?> list = null;

		try {
			String json = DownLoadUtil.stringFromURL(context, url);
			list = JSON.parseArray(json, clazz);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		
		return list;
	}
	
	
	/**
	 * 根据InputStream，获取List列表
	 * 
	 * @param clazz    类字节码 如:Object.class
	 * @param url      url地址
	 * @return         返回List列表
	 */
	public static List<?> getListFromInputStream(Class<?> clazz, Context context, InputStream is) {
		List<?> list = null;
		try {
			String json = FileUtil.inputStreamToString(is);
			list = JSON.parseArray(json, clazz);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}

		return list;
	}

	
	
	
	/**
	 * 根据URL地址，获取Object
	 * 
	 * @param classz   类字节码 如:Object.class
	 * @param url      url地址
	 * 
	 * @return 返回Object
	 */
	public static Object getObjectFromNetwork(Class<?> clazz, Context context, String url) {
		Object object = null;
		
		try {
			String json = DownLoadUtil.stringFromURL(context, url);
			object = JSON.parseObject(json, clazz);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}

		return object;
	}
	
	
	/**
	 * 根据InputStream，获取Object
	 * 
	 * @param classz   类字节码 如:Object.class
	 * @param url      url地址
	 * 
	 * @return 返回Object
	 */
	public static Object getObjectFromInputStream(Class<?> clazz, Context context, InputStream is) {
		Object object = null;
		
		try {
			String json = FileUtil.inputStreamToString(is);
			object = JSON.parseObject(json, clazz);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}

		return object;
	}
	
}
