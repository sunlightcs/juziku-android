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
	 * @return            保存的路径
	 */
	public static String saveFileToSDCard(String directory, String fileName,
			byte[] content) {
		StringBuilder path = new StringBuilder();
		if(isSDCardExisted()){
			path.append(SD_PATH);
		}
		path.append(File.separator).append(directory);
		
		return  FileUtil.saveFile(path.toString(), fileName, content);
	}
	
	
	/**
	 * 保存文件到SD
	 * 
	 * @param directory   目录
	 * @param fileName    文件名
	 * @param is          输入流
	 * @return            保存的路径
	 */
	public static String saveFileToSDCard(String directory, String fileName,
			InputStream is) {
		StringBuilder path = new StringBuilder();
		if(isSDCardExisted()){
			path.append(SD_PATH);
		}
		path.append(File.separator).append(directory);
		
		return FileUtil.saveFile(path.toString(), fileName, is);
	}
	
	
	/**
	 * 保存文件到SD
	 * 
	 * @param directory   目录
	 * @param url         地址
	 * @return            保存的路径
	 */
	public static String saveFileToSDCard(String directory, String url) {
		StringBuilder path = new StringBuilder();
		if(isSDCardExisted()){
			path.append(SD_PATH);
		}
		path.append(File.separator).append(directory);
		
		return FileUtil.saveFile(path.toString(), url);
	}
}
