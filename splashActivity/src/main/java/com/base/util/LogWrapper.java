package com.base.util;

import com.base.conf.BaseINI;
import android.util.Log;

public class LogWrapper {

	public static void e(Class clazz,String msg) {
		if(BaseINI.DEBUG) {
			Log.e(clazz.getSimpleName(),msg);
		}
	}
	public static void i(Class clazz,String msg) {
		if(BaseINI.DEBUG) {
			Log.i(clazz.getSimpleName(),msg);
		}
	}
	public static void d(Class clazz,String msg) {
		if(BaseINI.DEBUG) {
			Log.d(clazz.getSimpleName(),msg);
		}
	}
	public static void w(Class clazz,String msg) {
		if(BaseINI.DEBUG) {
			Log.w(clazz.getSimpleName(),msg);
		}
	}
}
