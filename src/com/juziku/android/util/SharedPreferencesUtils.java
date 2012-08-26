package com.juziku.android.util;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
	private final static String DEFAULT_NAME = "juziku-android";
	private SharedPreferences settings = null;
	
	public SharedPreferencesUtils(Context context){
		settings = context.getSharedPreferences(DEFAULT_NAME, Activity.MODE_PRIVATE);
	}
	
	public SharedPreferencesUtils(Context context, String name){
		settings = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
	}
	
	public boolean putBoolean(String key, boolean value){
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}
	
	public boolean putFloat(String key, float value){
		SharedPreferences.Editor editor = settings.edit();
		editor.putFloat(key, value);
		return editor.commit();
	}
	
	
	public boolean putInt(String key, int value){
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		return editor.commit();
	}
	
	public boolean putLong(String key, long value){
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong(key, value);
		return editor.commit();
	}
	
	public boolean putString(String key, String value){
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		return editor.commit();
	}
	
	public boolean getBoolean(String key, boolean defValue){
		return settings.getBoolean(key, defValue);
	}
	
	public float getFloat(String key, float defValue){
		return settings.getFloat(key, defValue);
	}
	
	public int getInt(String key, int defValue){
		return settings.getInt(key, defValue);
	}
	
	public String getString(String key, String defValue){
		return settings.getString(key, defValue);
	}
	
	public Map<String, ?> getAll(){
		return settings.getAll();
	}
	
	public boolean contains(String key){
		return settings.contains(key);
	}
	
}
