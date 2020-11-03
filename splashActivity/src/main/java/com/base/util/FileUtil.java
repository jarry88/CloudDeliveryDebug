package com.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

/**
 * 处理文件的工具类
 * @author leon
 *
 */
public class FileUtil {
	/**
	 * 从文件路径中解析出文件名
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileNameFromPath(String path) {
		int divideIndex = path.lastIndexOf("/");
		return path.substring(divideIndex);
	}
	
	
	public static void copyStream(InputStream input, OutputStream output)
			throws IOException {

		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			try {
				output.write(buffer, 0, bytesRead);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 得到cache文件夹路径
	 */
	public String getCacheFolder(Activity act) {
		return act.getCacheDir().getPath();
	}
	
	/**
	 * 得到package文件夹路径
	 */
	public String getPackageFolder(Activity act) {
		return act.getFilesDir().getPath();
	}
	
	/**
	 * 得到SD卡目录
	 */
	public static String getSDFloder(Context c) {
		 if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
		            || !Environment.isExternalStorageRemovable()) {  
		        return c.getExternalCacheDir().getPath();  
		    } else {  
		        return c.getCacheDir().getPath();  
		    }  
	}
}
