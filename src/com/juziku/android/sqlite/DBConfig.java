package com.juziku.android.sqlite;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import android.content.Context;
import android.util.Log;

import com.juziku.android.util.ConfigUtils;
import com.juziku.android.util.StringUtils;

/**
 * 数据库配置信息
 * @author Administrator
 *
 */
public class DBConfig {
	private static final String TAG = DBConfig.class.getSimpleName();
	
	public static String DATABASE_NAME;
	public static int DATABASE_VERSION;
	public static boolean DATABASE_UPDATE_STRATEGY;
	public static Set<Class<?>> models = new HashSet<Class<?>>();
	
	
	public DBConfig(Context context){
		Properties properties = ConfigUtils.getConfigFile(context);
		DATABASE_NAME = properties.getProperty("database_name");
		String version = properties.getProperty("database_version");
		if(StringUtils.isNotEmpty(version))
			DATABASE_VERSION = Integer.parseInt(version);
		
		//数据库更新策略  true：全部删除后再增加   false：只更新增加的表或列
		String updateStrategy = properties.getProperty("database_update_strategy");
		if(StringUtils.isNotEmpty(updateStrategy)){
			DATABASE_UPDATE_STRATEGY = Boolean.parseBoolean(updateStrategy);
		}else{
			DATABASE_UPDATE_STRATEGY = false;
		}
		
		String beans = properties.getProperty("models");
		if(StringUtils.isNotEmpty(beans)){
			String []arrayModel = beans.split(";");
			try {
				for(String model : arrayModel){
					models.add(Class.forName(model.trim()));
				}
			} catch (ClassNotFoundException e) {
				Log.e(TAG, e.getMessage());
			}
			
		}
			
	}	
	
}
