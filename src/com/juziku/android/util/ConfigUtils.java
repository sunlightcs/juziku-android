package com.juziku.android.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.util.Log;

/**
 * 读取配置文件
 * @author steven
 *
 */
public class ConfigUtils {
	private static final String TAG = ConfigUtils.class.getSimpleName();

	public static Properties getConfigFile(Context context, String fileName){
		InputStream is = null;
		Properties prop = null;
		try {
			is = context.getAssets().open(fileName);
			prop = new Properties();
			prop.loadFromXML(is);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage());
				}
			}
		}
		
		return prop;
	}
	
	
}
