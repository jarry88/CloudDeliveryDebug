package com.lw.clouddelivery.ui;


import java.io.File;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import androidx.core.app.NotificationCompat;

import com.base.BaseApp;
import com.base.R;
import com.base.conf.BaseINI;
import com.base.util.LogWrapper;
import com.lw.clouddelivery.util.MySPTool;

/**
 * 启动欢迎页面，支持在线请求及本地默认图片
 * @tip 启动时，检索本地是否存在以当前日期为名称的图片，有就加载，没有则使用默认图片
 * @author leon
 */
public class SplashActivity extends Activity {

	private Animation splashAni;
	
	/** Notification管理 */
	public NotificationManager mNotificationManager;
	/** Notification构造器 */
	NotificationCompat.Builder mBuilder;
	/** Notification的ID */
	int notifyId = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	     this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,                 
	    		 	WindowManager.LayoutParams.FLAG_FULLSCREEN);
	     
		ImageView splash_iv = new ImageView(this);
		splash_iv.setScaleType(ScaleType.CENTER_CROP);
		setContentView(splash_iv);
		splashAni = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.splash_set);
		splashAni.setFillAfter(true);
		splashAni.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent i = new Intent();
				if(MySPTool.getUid(SplashActivity.this).equals("")) {
					i.setClass(SplashActivity.this, LoginActivity.class);
				} else {
					i.setClass(SplashActivity.this, HomePageActivity.class);
				}
				startActivity(i);
				SplashActivity.this.finish();
			}
		});
		BaseApp.getInstance().add(this);
		splash_iv.startAnimation(splashAni);
		
		File picFilterDir = new File(BaseINI.FILE_PATH.PIC_FILTER);
		if(!picFilterDir.exists()) {
			LogWrapper.e(SplashActivity.class, "滤镜临时图片文件夹目录不存在，开始创建目录");
			picFilterDir.mkdirs();
			LogWrapper.e(SplashActivity.class, "滤镜临时图片目录创建成功!");
		} 
		File nomediaFilter = new File(BaseINI.FILE_PATH.PIC_FILTER + BaseINI.NOMEDIA);
		if(!nomediaFilter.exists()) {
			LogWrapper.e(SplashActivity.class, "为Filter目录添加.nomedia文件，开始创建文件");
			nomediaFilter.mkdirs();
			LogWrapper.e(SplashActivity.class, "滤镜临时图片目录中的.nomedia文件创建成功!");
		}
		showCzNotify();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		BaseApp.getInstance().remove(this);
	}
	/** 显示常驻通知栏 */
	public void showCzNotify(){
//		Notification mNotification = new Notification();//为了兼容问题，不用该方法，所以都采用BUILD方式建立
//		Notification mNotification  = new Notification.Builder(this).getNotification();//这种方式已经过时
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//		//PendingIntent 跳转动作
//		PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, getIntent(), 0);  
//		Intent intent=new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
//		ComponentName cName = new ComponentName("com.android.phone","com.android.phone.Settings");
//		intent.setComponent(cName);
//		startActivity(intent);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, getIntent(), PendingIntent.FLAG_UPDATE_CURRENT); 
		mBuilder.setSmallIcon(R.drawable.ic_launcher)
				.setTicker("壹步达提示")
				.setContentTitle("接收新订单")
				.setContentText("请保持程序在后台运行")
				.setContentIntent(pendingIntent);
		Notification mNotification = mBuilder.build();
		//设置通知  消息  图标  
		mNotification.icon = R.drawable.ic_launcher;
		//在通知栏上点击此通知后自动清除此通知
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;//FLAG_ONGOING_EVENT 在顶部常驻，可以调用下面的清除方法去除  FLAG_AUTO_CANCEL  点击和清理可以去调
		//设置显示通知时的默认的发声、震动、Light效果  
		mNotification.defaults = Notification.DEFAULT_VIBRATE;
		//设置发出消息的内容
		mNotification.tickerText = "壹步达提示";
		//设置发出通知的时间  
		mNotification.when=System.currentTimeMillis(); 
//		mNotification.flags = Notification.FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知
//		mNotification.setLatestEventInfo(this, "常驻测试", "使用cancel()方法才可以把我去掉哦", null); //设置详细的信息  ,这个方法现在已经不用了 
		mNotificationManager.notify(notifyId, mNotification);
	}
	/**
	 * @获取默认的pendingIntent,为了防止2.3及以下版本报错
	 * @flags属性:  
	 * 在顶部常驻:Notification.FLAG_ONGOING_EVENT  
	 * 点击去除： Notification.FLAG_AUTO_CANCEL 
	 */
	public PendingIntent getDefalutIntent(int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
		return pendingIntent;
	}
}
