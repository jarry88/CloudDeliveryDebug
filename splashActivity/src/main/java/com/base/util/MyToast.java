package com.base.util;

import com.base.BaseApp;
import com.base.conf.BaseINI;

import android.content.Context;
import android.widget.Toast;

/**
 * 自定义Toast
 */
public class MyToast extends Toast {

	public MyToast(Context context) {
		super(context);
	}
	
	public static void showText(Context c,String text) {
		makeText(c, text, 400).show();
	}
	
	public static void showText(Context c,int textres) {
		makeText(c, c.getString(textres), 400).show();
	}
	
//	/**
//	 *  debug用toast
//	 * @param textres
//	 */
//	public static void showTextW(String text) {
//		if(BaseINI.DEBUG) {
//			Context c = BaseApp.getInstance().getApplicationContext();
//			makeText(c, text, 10).show();
//		}
//	}
//	/**
//	 *  debug用toast
//	 * @param textres
//	 */
//	public static void showTextW(int textres) {
//		if(BaseINI.DEBUG) {
//			Context c = BaseApp.getInstance().getApplicationContext();
//			makeText(c,  c.getString(textres), 10).show();
//		}
//	}
}
