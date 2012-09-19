package com.juziku.android.io;

import java.io.File;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.juziku.android.http.DownLoadUtil;

/**
 * 异步加载图片
 *
 */
public class AsyncImageLoader {
	private String filePath = null;
	
	/**
	 * 异步下载图片
	 * 
	 * @param imageFolder   图片存放的目录
	 * @param imageUrl      图片的URL地址
	 */
	public void loadDrawable(final String imageFolder, final String imageUrl, final ImageCallback imageCallback) {
		final String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
		if(SDCardUtil.isSDCardExisted()){
			filePath = SDCardUtil.SD_PATH;
		}
		filePath +=  File.separator + imageFolder + File.separator + fileName;
		File file = new File(filePath);
		
		class ImageHandler extends Handler{ 
			public ImageHandler(Looper looper){
	            super(looper);
	        }
			@Override
			public void handleMessage(Message msg) {
				imageCallback.imageLoaded((Drawable) msg.obj, filePath);
			}
		}
		
		if(file.exists()){
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			Drawable drawable = new BitmapDrawable(bitmap);
			
			ImageHandler imageHandler = new ImageHandler(Looper.getMainLooper());
			Message message = imageHandler.obtainMessage(0, drawable);
			imageHandler.sendMessage(message);
			
			return;
		}
		
		new Thread(new Runnable() {
			public void run() {
				InputStream is = DownLoadUtil.inputStreamFromURL(imageUrl);
				String path = null;
				if(is != null){
					path = SDCardUtil.saveFileToSDCard(imageFolder, fileName, is);
					
					Bitmap bitmap = BitmapFactory.decodeFile(path);
					Drawable drawable = new BitmapDrawable(bitmap);
					ImageHandler imageHandler = new ImageHandler(Looper.getMainLooper());
					Message message = imageHandler.obtainMessage(0, drawable);
					imageHandler.sendMessage(message);
				}
			}
		}).start();
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String filePath);
	}
}
