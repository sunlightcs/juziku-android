package com.juziku.android.io;

import java.io.File;
import java.io.InputStream;

import android.os.Environment;

/**
 * SD卡处理类
 * @author Administrator
 *
 */
public class SDCardUtil {
	//SD路径
	public final static String SD_PATH = Environment.getExternalStorageDirectory().getPath();
	
	/**
	 * 判断SD卡是否存在
	 * 
	 * @return  true:存在   false:不存在
	 */
	public static boolean isSDCardExisted() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 保存文件到SD
	 * 
	 * @param directory   目录
	 * @param fileName    文件名
	 * @param content     字节
	 * @return            true:保存成功   false:保存失败
	 */
	public static boolean saveFileToSDCard(String directory, String fileName,
			byte[] content) {
		String path = SD_PATH + File.separator + directory;
		
		boolean flag = FileUtil.saveFile(path, fileName, content);
		
		return flag;
	}
	
	
	/**
	 * 保存文件到SD
	 * 
	 * @param directory   目录
	 * @param fileName    文件名
	 * @param is          输入流
	 * @return            true:保存成功   false:保存失败
	 */
	public static boolean saveFileToSDCard(String directory, String fileName,
			InputStream is) {
		String path = SD_PATH + File.separator + directory;
		
		boolean flag = FileUtil.saveFile(path, fileName, is);
		
		return flag;
	}
}
