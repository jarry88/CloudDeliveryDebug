package com.lw.clouddelivery.util;

import java.text.DecimalFormat;

import net.tsz.afinal.exception.AfinalException;

import com.base.BaseApp;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Order;
import com.lw.clouddelivery.ui.AboutActivity;
import com.lw.clouddelivery.ui.BankInputActivity;
import com.lw.clouddelivery.ui.FeedbackActivity;
import com.lw.clouddelivery.ui.HelpDetailActivity;
import com.lw.clouddelivery.ui.LoginActivity;
import com.lw.clouddelivery.ui.MapActivity;
import com.lw.clouddelivery.ui.MessageListActivity;
import com.lw.clouddelivery.ui.ProblemListActivity;
import com.lw.clouddelivery.ui.PunishListActivity;
import com.lw.clouddelivery.ui.ReLocActivity;
import com.lw.clouddelivery.ui.RechargeActivity;
import com.lw.clouddelivery.ui.ResetPwdActivity;
import com.lw.clouddelivery.ui.SettingActivity;
import com.lw.clouddelivery.ui.ShouZhiListActivity;
import com.lw.clouddelivery.ui.AfterQiangdanActivity;
import com.lw.clouddelivery.ui.CashAccountActivity;
import com.lw.clouddelivery.ui.ChoiceVehicleActivity;
import com.lw.clouddelivery.ui.ConfirmActivity;
import com.lw.clouddelivery.ui.HomePageActivity;
import com.lw.clouddelivery.ui.MapDownloadActivity;
import com.lw.clouddelivery.ui.MyYunSongActivity;
import com.lw.clouddelivery.ui.OrderListActivity;
import com.lw.clouddelivery.ui.OrderDetailActivity;
import com.lw.clouddelivery.ui.TiXianActivity;
import com.lw.clouddelivery.ui.UserManualActivity;
import com.lw.clouddelivery.ui.YS_Step1Activity;
import com.lw.clouddelivery.ui.xxxActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class UIHelper {

	public static void showMainPage(Context c) {
		Intent i = new Intent();
		i.setClass(c, HomePageActivity.class);
		c.startActivity(i);
	}
	
	/**
	 * 订单列表
	 * @param pageType,1表示今日订单，2表示我的订单
	 * @param c
	 */
	public static void showOrderList(Context c,int pageType) {
		Intent i = new Intent();
		i.setClass(c, OrderListActivity.class);
		i.putExtra("pageType", pageType);
		c.startActivity(i);
	}
	
	public static void showChoiceVehicle(Context c) {
		Intent i = new Intent();
		i.setClass(c, ChoiceVehicleActivity.class);
		((Activity)c).startActivityForResult(i,1);
	}
	
	public static void showOrderDetail(Context c,Order order) {
		Intent i  = new Intent();
		i.setClass(c, OrderDetailActivity.class);
		Bundle b = new Bundle();
		b.putSerializable("order", order);
		i.putExtras(b);
		c.startActivity(i);
	}
	/**
	 * 跳转到个人中心
	 * @param c
	 */
	public static void showMyYunSong(Context c) {
		Intent i = new Intent();
		i.setClass(c, MyYunSongActivity.class);
		c.startActivity(i);
	}
	
	/**
	 * 打开员工手册界面
	 * @param type 1表示常见问题，2表示壹步达员手册
	 */
	public static void showUserManual(Context c,int type) {
		Intent i = new Intent();
		i.setClass(c, UserManualActivity.class);
		i.putExtra("type", type);
		c.startActivity(i);
	}
	/**
	 * 打开现金账户
	 * @param c
	 */
	public static void showCashAccount(Context c) {
		Intent i = new Intent();
		i.setClass(c, CashAccountActivity.class);
		c.startActivity(i);
	}
	
	/**
	 * 显示抢单对话框
	 * @param isViewMap 是否是查看地图
	 * @param c
	 */
	public static void showQiangDanDialog(Context c,Order order,boolean isNewOrder,boolean isCheckMap) {

		DecimalFormat    df   = new DecimalFormat("######0.00");  
		String juniDistance = df.format((Double.valueOf(order.getShopjl()) / 1000.0));
		if(Double.valueOf(juniDistance) > 15f && isNewOrder == true){
			return;
		}
		
		Intent i = new Intent();
		i.setClass(c, xxxActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Bundle b = new Bundle();
		b.putSerializable("order", order);
		i.putExtras(b);
		i.putExtra("isNewOrder", isNewOrder);
		i.putExtra("checkMap", isCheckMap);
		c.startActivity(i);
	}
	/**
	 * 城市离线下载
	 * @param c
	 */
	public static void showMapDownload(Context c) {
		Intent i = new Intent();
		i.setClass(c, MapDownloadActivity.class);
		c.startActivity(i);
	}
	/**
	 * 关于界面
	 * @param c
	 */
	public static void showAboutPage(Context c) {
		Intent i = new Intent();
		i.setClass(c, AboutActivity.class);
		c.startActivity(i);
	}
	
	/**
	 * 送单第一步
	 * @param c
	 */
	public static void showYSStep1(Context c,Order order) {
		Intent i = new Intent();
		i.setClass(c, YS_Step1Activity.class);
		Bundle b = new Bundle();
		b.putSerializable("order", order);
		i.putExtras(b);
		c.startActivity(i);
	}
	
	/**
	 * 抢单成功跳转到引导界面
	 * @param c
	 */
	public static void showAfterQiangdan(Context c,Order order) {
		Intent i = new Intent();
		i.setClass(c, AfterQiangdanActivity.class);
		Bundle b = new Bundle();
		b.putSerializable("order", order);
		i.putExtras(b);
		c.startActivity(i);
	}
	
	/**
	 * "物品确认"
	 * @param c
	 */
	public static void showConfirmActivity(Activity c,int step,Order order) {
		Intent i = new Intent();
		i.setClass(c, ConfirmActivity.class);
		i.putExtra("step", step);
		Bundle b = new Bundle();
		b.putSerializable("order", order);
		i.putExtras(b);
		c.startActivityForResult(i, 1);
	}
	
//	/**
//	 * 输入验证码对话框
//	 * @param c
//	 */
//	public static void showSMSDialog(Context c,String textHint,final OnClickListener listener) {
//		LayoutInflater layoutInflater = LayoutInflater.from(c);
//		LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.patch_sms_code, null);
////		((EditText)layout.findViewById(R.id.sms_code_et)).setText(textHint);
//		AlertDialog.Builder builder = new AlertDialog.Builder(c);
//		builder.setTitle("请输入取件验证码")
//		.setView(layout).create();
//		final AlertDialog dialog = builder.create();
//		dialog.show();
//		layout.findViewById(R.id.sms_code_btn).setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				dialog.cancel();
//				listener.onClick(v);
//			}
//		});
//	}
	
	/**
	 * 跳转到收支明细列表界面
	 * @param c
	 */
	public static void showShouZhilList(Context c) {
		Intent i = new Intent();
		i.setClass(c, ShouZhiListActivity.class);
		c.startActivity(i);
	}
	
	/**
	 * 跳转到充值界面
	 * @param c
	 */
	public static void showChongZhiList(Context c) {
		Intent i = new Intent();
		i.setClass(c, RechargeActivity.class);
		c.startActivity(i);
	}
	/**
	 * 跳转到收支明细列表界面
	 * @param c
	 */
	public static void showMessageListActivity(Context c,String pageType) {
		Intent i = new Intent();
		i.setClass(c, MessageListActivity.class);
		i.putExtra("pageType", pageType);
		c.startActivity(i);
	}
	
	/**
	 * 跳转到设置界面
	 * @param c
	 */
	public static void showSettingPage(Context c) {
		Intent i = new Intent();
		i.setClass(c, SettingActivity.class);
		c.startActivity(i);
	}
	
	/**
	 * 跳转到奖惩记录
	 * @param c
	 */
	public static void showPunishPage(Context c) {
		Intent i = new Intent();
		i.setClass(c, PunishListActivity.class);
		c.startActivity(i);
	}
	/**
	 * 跳转到“重新定位”页面
	 * @param c
	 */
	public static void showReLocation(Context c) {
		Intent i = new Intent();
		i.setClass(c, ReLocActivity.class);
		((Activity)c).startActivityForResult(i, 1);
	}
	
	/**
	 * 跳转到填写银行信息界面
	 * @param c
	 */
	public static void showBankInput(Context c) {
		Intent i = new Intent();
		i.setClass(c, BankInputActivity.class);
		((Activity)c).startActivityForResult(i, 1);
	}
	
	/**
	 * 跳转到重置密码页面
	 * @param c
	 */
	public static void showResetPwd(Context c) {
		Intent i = new Intent();
		i.setClass(c, ResetPwdActivity.class);
		((Activity)c).startActivityForResult(i, 1);
	}
	
	/**
	 * 跳转到意见反馈界面
	 * @param c
	 */
	public static void showFeedback(Context c) {
		Intent i = new Intent();
		i.setClass(c, FeedbackActivity.class);
		c.startActivity(i);
	}
	/**
	 * 跳转到意见反馈界面
	 * @param c
	 */
	public static void showHelpDetail(Context c,String title,String content) {
		Intent i = new Intent();
		i.setClass(c, HelpDetailActivity.class);
		i.putExtra("title", title);
		i.putExtra("content",content);
		c.startActivity(i);
	}
	
	/**
	 * 跳转到"查看地图"
	 * @param c
	 */
	public static void showCheckMap(Context c,Order order) {
		Intent i = new Intent();
		i.setClass(c, MapActivity.class);
		Bundle b = new Bundle();
		b.putSerializable("order", order);
		i.putExtras(b);
		c.startActivity(i);
	}
	/**
	 * 跳转到"查看地图"
	 * @param c
	 */
	public static void showProblemList(Context c) {
		Intent i = new Intent();
		i.setClass(c, ProblemListActivity.class);
		c.startActivity(i);
	}

	/**
	 * 退出登陆
	 * @param c
	 */
	public static void logout(Context c) {
		MySPTool.clear(c);
		BaseApp.getInstance().clearActivity();
		
		Intent i = new Intent();
		i.setClass(c, LoginActivity.class);
		c.startActivity(i);
	}
	
	/**
	 * 跳转到提现界面
	 */
	public static void showTiXianPage(Context c,double tixianAvailable) {
		Intent i = new Intent();
		i.setClass(c, TiXianActivity.class);
		i.putExtra("tixianAvailable", tixianAvailable);
		c.startActivity(i);
	}
	/**
	 * 跳转到注册界面
	 * @param c
	 */
	public static void showRegister(Context c) {
		Intent i = new Intent();
		i.setClass(c, ProblemListActivity.class);
		c.startActivity(i);
	}
}
