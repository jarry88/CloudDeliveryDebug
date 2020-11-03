package com.lw.clouddelivery.util;

import android.os.Environment;

public class Constants {

	
	public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getPath()
			+ "/AYiBuDa";
	public static final String IMAGE_PATH = ROOT_PATH + "/photoimage";
	
	public static final int TYPE_COUNT = 2;
	public static final int TYPE_LEFT = 1;
	public static final int TYPE_RIGHT = 0;
	
	public static final int MSG_0 = 1001;
	public static final int MSG_1 = 1002;
	public static final int MSG_2 = 1003;
	public static final int MSG_4 = 1004;
	public static final int MSG_5 = 1005;
	public static final int MSG_6 = 1006;
	
	public static final int MSG_8 = 1008;
	public static final int MSG_9 = 1009;
	public static final int MSG_10 = 10010;
	public static final int MSG_11 = 10011;
	public static final int MSG_ADAPTER_PRAISE = 2000;
	public static final int MSG_ADAPTER_CACELPRAISE = 2001;
	public static final int MSG_DELETEALARM = 2002;
	
	
	public static final int DATE_MODE_FIX = 0;
	public static final int DATE_MODE_WEEK = 1;
	public static final int DATE_MODE_MONTH = 2;
	
	public static final int BASE_SIZE_150 = 150;
	public static final int BASE_SIZE_120 = 120;
	public static final int BASE_SIZE_100 = 100;
	
	public static final String PLATFORM = "Platform";
	
	public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
	public static final String GROUP_USERNAME = "item_groups";
	public static final String CHAT_ROOM = "item_chatroom";
	public static final String MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call";
	public static final String MESSAGE_ATTR_IS_VIDEO_CALL = "is_video_call";
	public static final String ACCOUNT_REMOVED = "account_removed";
	
	public static final int TAB_MAIN_HOME = 0;
	public static final int TAB_MAIN_BAO_YING = 1;
	public static final int TAB_MAIN_YOU_JIANG = 2;
	public static final int TAB_MAIN_PERSONAL = 3;
	// 下拉刷新、上拉加载更多标志
	public static final int REFRESH = 0;
	public static final int LOAD_MORE = 1;
    // 广播
	public static final String CHANGE_TAB_BAO_YONG = "change.tab.bao.ying";
	public static final String NOTIFICATION_MESSAGE_CLICK = "notification.message.click";
	public static final String USER_LOGOUT = "user.logout";
	//掉红包的时间
	public static final long DOQWNGOLDTIME = 5000;
    //	login type flag
	public static final int LOGIN_TYPE_USER = 0;
	public static final int LOGIN_TYPE_QQ = 1;
	public static final int LOGIN_TYPE_WEI_XIN = 2;
	public static final int LOGIN_TYPE_SINA = 3;
	public static final int LOGIN_TYPE_NO_LOGIN = 4;
	
	//充值类型 1 充值  2 打赏
	public static final String PAY_TYPE_CHONG_ZHI = "1";
	public static final String PAY_TYPE_DA_SHANG  = "2";
	
}
