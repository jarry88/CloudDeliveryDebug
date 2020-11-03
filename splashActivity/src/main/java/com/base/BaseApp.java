package com.base;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.base.conf.BaseINI;
import com.base.util.LogWrapper;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;

public class BaseApp extends Application {
	private static BaseApp instance;
	private static List<Activity> mActivityList = new ArrayList<Activity>();

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
//		
//		//初始化目录
//		File soundDir = new File(BaseINI.FILE_PATH.AUDIO);
//		if(!soundDir.exists()) {
//			LogWrapper.e(BaseApp.class, "语音文件夹目录不存在，开始创建目录");
//			soundDir.mkdirs();
//			LogWrapper.e(BaseApp.class, "语音文件夹目录创建成功!");
//		} 
//		
//		File collectDir = new File(BaseINI.FILE_PATH.IMAGE);
//		if(!collectDir.exists()) {
//			LogWrapper.e(BaseApp.class, "图片文件夹目录不存在，开始创建目录");
//			collectDir.mkdirs();
//			LogWrapper.e(BaseApp.class, "图片文件夹目录创建成功!");
//		} 
	}
	public static  BaseApp getInstance() {
			if(instance == null) {
				instance = new BaseApp();
			}
			return instance;
	}
	
	/**
	 * 移除一个Activity
	 * 
	 * @param activity
	 */
	public void remove(Activity activity) {
		mActivityList.remove(activity);
	}

	/**
	 * 添加一个Activity
	 * 
	 * @param activity
	 */
	public void add(Activity activity) {
		mActivityList.add(activity);
	}

	/**
	 * 退出程序
	 */
	public static void finishProgram() {

		for (Activity activity : mActivityList) {
			activity.finish();
		}

		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}
	
	/**
	 * 清除历史
	 */
	public static void clearActivity() {
		for(Activity activity:mActivityList) {
			activity.finish();
		}
	}
}
