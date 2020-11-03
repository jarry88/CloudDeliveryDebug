package com.base.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.base.util.LogWrapper;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 附带计时功能的TextView
 * @author leon
 *
 */
public class CounttingTextView extends TextView {
	
    private Handler stepTimeHandler;
    private Runnable mTicker;
    long startTime = 0; //计时开始时间
    
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public CounttingTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 开始计时
	 */
	public void startCount() {
		stepTimeHandler = new Handler();
		if(startTime == -1) { //是否是新记录
			startTime = System.currentTimeMillis();
		}
        mTicker = new Runnable() {
            public void run() {
                String content = showTimeCount(System.currentTimeMillis() - startTime);
    				setText(content);

                long now = SystemClock.uptimeMillis();
                long next = now + (1000 - now % 1000);
                stepTimeHandler.postAtTime(mTicker, next);
            }
        };
        //启动计时线程，定时更新
        mTicker.run();
	}
	
	public void stopCount() {
		stepTimeHandler.removeCallbacks(mTicker);
	}
	
	/**
	 * 时间毫秒数转时间字符串
	 * @param time
	 * @return
	 */
	 /**
	  * 秒数转字符串,00-00
	  * @return
	  */
    public String showTimeCount(long time) {
        if(time >= 360000000 || time== 0) {
            return "00:00:00";
        } 
        String timeCount = "";
        long hourc = time/3600000;
        String hour = "0" + hourc;
        hour = hour.substring(hour.length()-2, hour.length());
        
        long minuec = (time-hourc*3600000)/(60000);
        String minue = "0" + minuec;
        minue = minue.substring(minue.length()-2, minue.length());
        
        long secc = (time-hourc*3600000-minuec*60000)/1000;
        String sec = "0" + secc;
        sec = sec.substring(sec.length()-2, sec.length());
        timeCount = hour + ":" + minue + ":" + sec;
        return timeCount;
    }
	
	/**
	 * 
	 * @param secMills
	 * @return
	 */
	public static String date2CNStr(long secMills) {
		Date date = new Date(secMills);
		String str = new SimpleDateFormat("yyyy年MM月dd日").format(date);
		return str;
	}
}
