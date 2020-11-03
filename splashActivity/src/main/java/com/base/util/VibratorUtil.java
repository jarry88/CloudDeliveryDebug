package com.base.util;

import android.content.Context;
import android.os.Vibrator;

public class VibratorUtil {

	private Context mContext;
	private Vibrator mVibrator;
	public VibratorUtil(Context c) {
		this.mContext = c;
		/* 
         * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到 
         * */  
		mVibrator = (Vibrator)mContext.getSystemService(Context.VIBRATOR_SERVICE);  
	}
	
	/**
	 * @param pattern // 停止 开启 停止 开启  
	 */
	public void start(long[] pattern) {
        mVibrator.vibrate(pattern,2);   
	}
	public void stop() {
		mVibrator.cancel();  
	}
}
