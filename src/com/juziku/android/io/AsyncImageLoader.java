package com.juziku.android.io;

import java.io.File;
import java.io.InputStream;

import android.content.Context;
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
	
	private Context context;
	public AsyncImageLoader(Context context) {
		this.context = context;
	}
	
	/**
	 * 异步下载图片
	 * 
	 * @param imageFolder   图片存放的目录
	 * @param imageUrl      图片的URL地址
	 */
	public Drawable loadDrawable(final String imageFolder, final String imageUrl, final ImageCallback imageCallback) {
		final String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
		String filePath = SDCardUtil.SD_PATH + File.separator + imageFolder + File.separator + fileName;
		File file = new File(filePath);
		if(file.exists()){
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			Drawable drawable = new BitmapDrawable(bitmap);
			return drawable;
		}

		class ImageHandler extends Handler{ 
			public ImageHandler(Looper looper){
	            super(looper);
	        }
			@Override
			public void handleMessage(Message msg) {
				imageCallback.imageLoaded((Drawable) msg.obj);
			}
		}
		
		new Thread(new Runnable() {
			public void run() {
				InputStream is = DownLoadUtil.inputStreamFromURL(context, imageUrl);
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
		return null;
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable);
	}
}
