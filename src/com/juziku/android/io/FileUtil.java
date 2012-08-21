package com.juziku.android.io;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;

import android.util.Log;

/**
 * 文件处理类
 * @author Administrator
 *
 */
public class FileUtil {
	private static final String TAG = FileUtil.class.getSimpleName();
	public static final int BUFFER_SIZE = 1024 * 10;
	
	/**
	 * 复制文件操作
	 * @param srcName      源文件名
	 * @param targetName   目标文件名
	 * @return             返回目标文件的大小
	 */
	public static long copyFile(String srcName, String targetName){
		FileChannel in = null;
		FileChannel out = null;
		long result = 0;
		try {
			in = new FileInputStream(srcName).getChannel();
			out = new FileOutputStream(targetName).getChannel();
			
			result = in.transferTo(0, in.size(), out);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}finally{
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    return result;
	}
	
	
	/**
	 * 保存文件
	 * @param directory 目录
	 * @param fileName  文件名
	 * @param content   字节
	 * @return          true:表示保存成功    false:表示保存失败
	 */
	public static boolean saveFile(String directory, String fileName, byte[] content){
		boolean flag = false;
		OutputStream os = null;
		try {
			//目录如果不存在，则创建
			File file = new File(directory);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
			}
			
			os = new BufferedOutputStream(new FileOutputStream(	directory + File.separator + fileName));
			os.write(content);
			
			flag = true;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage());
				}
			}
		}
		return flag;
	}
	
	/**
	 * 保存文件
	 * @param directory 目录
	 * @param fileName  文件名
	 * @param is        输入流
	 * @return          true:表示保存成功    false:表示保存失败
	 */
	public static boolean saveFile(String directory, String fileName, InputStream is){
		return saveFile(directory, fileName, readInputStream(is));
	}
	
	
	/**
	 * 把输入流转换成字符串
	 * @param is    输入流
	 * @return      字符串
	 */
	public static String inputStreamToString(InputStream is) {
		String str = null;
		try {
			byte[] b = readInputStream(is);
			str = new String(b, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
		}
		return str;
	}
	
	/**
	 * 把输入流转换成字节数组
	 * @param is 输入流
	 * @return  字节数组
	 */
	public static byte[] readInputStream(InputStream is){
		ByteArrayOutputStream outStream = null;
		try {
			outStream = new ByteArrayOutputStream();
			
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while( (len=is.read(buffer)) != -1 ){
				outStream.write(buffer, 0, len);
			}
			outStream.flush();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		
		return outStream.toByteArray();
	}

}
