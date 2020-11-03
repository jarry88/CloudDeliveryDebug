package com.lw.clouddelivery.util.polling;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.base.util.LogWrapper;
import com.lw.clouddelivery.CloudDeliveryAPP;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Order;
import com.lw.clouddelivery.ui.HomePageActivity;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.MySPTool;
import com.lw.clouddelivery.util.UIHelper;
public class PollingService extends Service {

	public static final String ACTION = "com.lw.clouddelivery.getorder";
	
	private Notification mNotification;
	private NotificationManager mManager;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
//		showStartNotification(this);
		initNotifiManager();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		Log.e("PollingService", "onStart");
			// 开启定时轮询服务
		DCUtil.requestQDOrderList(getApplicationContext()
				, null,null, MySPTool.getLon(getApplicationContext())
				, MySPTool.getLat(getApplicationContext()), new Handler() {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
							// 开启定时轮询服务
							PollingUtils.startPollingService(PollingService.this, 7, PollingService.class, PollingService.ACTION);
						}
						ArrayList<Order> orderList = (ArrayList<Order>) msg.obj;
						if(orderList != null && orderList.size()>0) {
							for(int i=0;i<orderList.size();i++) {
								if(orderList.get(i).getIsnew() == 0) {
									if(CloudDeliveryAPP.tempList.contains(orderList.get(i).getId())){
										
										continue;
									}else{
										UIHelper.showQiangDanDialog(getApplicationContext(),orderList.get(i),true,false);
										showNotification();
										return ;
									}
								}
								LogWrapper.e(PollingService.class, "轮训到订单数据，但最近一条无效");
							}
						}
						
					}
		});
	}

	private void initNotifiManager() {
		mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		int icon = R.drawable.icon;
		mNotification = new Notification();
		mNotification.icon = icon;
		mNotification.tickerText = "新消息";
//		mNotification.defaults |= Notification.DEFAULT_SOUND;
//		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;//FLAG_ONGOING_EVENT 在顶部常驻，可以调用下面的清除方法去除  FLAG_AUTO_CANCEL  点击和清理可以去调
		//设置显示通知时的默认的发声、震动、Light效果  
		mNotification.defaults = Notification.DEFAULT_VIBRATE;
	}

	@SuppressLint("NewApi")
	private void showNotification() {
		mNotification.when = System.currentTimeMillis();
		//Navigator to the new activity when click the notification title
		Intent i = new Intent(this, HomePageActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i,
				Intent.FLAG_ACTIVITY_NEW_TASK);
//		mNotification.setLatestEventInfo(this,getResources().getString(R.string.app_name), "发现一个新订单，赶快来抢!", pendingIntent);
		mNotification = new Notification.Builder(this).setAutoCancel(true)  
	                .setContentTitle(getResources().getString(R.string.app_name)).setContentText("发现一个新订单，赶快来抢!")  
	                .setContentIntent(pendingIntent)  
	                .setSmallIcon(R.drawable.ic_launcher)  
	                .setWhen(System.currentTimeMillis()).build();  
	  
		mManager.notify(0, mNotification);
	}

	int count = 0;
	class PollingThread extends Thread {
		@Override
		public void run() {
			final Context c = getApplicationContext();
			Looper.prepare();
			DCUtil.requestQDOrderList(getApplicationContext(), null, "1"
					, MySPTool.getLon(c), MySPTool.getLat(c), new Handler() {
						@Override
						public void handleMessage(Message msg) {
							super.handleMessage(msg);
							ArrayList<Order> orderList = (ArrayList<Order>) msg.obj;
							if(orderList != null && orderList.size()>0) {
								for(int i=0;i<orderList.size();i++) {
									if(orderList.get(i).getIsnew() == 0) {
										UIHelper.showQiangDanDialog(getApplicationContext(),orderList.get(i),true,false);
										showNotification();
										return ;
									}
									LogWrapper.e(PollingService.class, "轮训到订单数据，但最近一条无效");
								}
							}
							
//							ArrayList<Order> orderList = (ArrayList<Order>) msg.obj;
//							if(orderList != null && orderList.size()>0) {
//								Order order = orderList.get(0);
//								if(order.getOrderstatusId().equals(INI.STATE.ORDER_WAITING)) {
//									showNotification();
//								}
//							}
						}
			});
		}
	}

//	private void showStartNotification(Context context) { 
//		Notification notification = new Notification(R.drawable.ic_launcher,  
//				context.getString(R.string.app_name), System.currentTimeMillis());  
//	  
//		PendingIntent pendingintent = PendingIntent.getActivity(context, 0,  
//				new Intent(context, HomePageActivity.class), 0);  
//		notification.setLatestEventInfo(context, "接收新订单", "请保持程序在后台运行",  
//	 	pendingintent);  
//		startForeground(0x111, notification);
//	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("Service:onDestroy");
	}
}
