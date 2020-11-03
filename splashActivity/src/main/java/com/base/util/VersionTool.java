//package com.base.util;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.base.BaseApp;
//import com.base.bean.VersionInfo;
//import com.base.widget.LoadingDialog;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.Gallery;
//
///**
// * 版本更新工具
// * @author Leon
// *
// */
//public class VersionTool{
//
//	private LoadingDialog mDialog;
//	private Context mContext;
//	//用于处于版本更新的handler
//	private List<VersionInfo> list = new ArrayList<VersionInfo>();
//	
//	private static VersionTool mInstance;
//	
//	private boolean isChecking = false;
//	
//	private VersionInfo versionInfo;
//	
//	private int invokeFlag = 0; //调用来源，0表示来自主界面，1表示来自设置界面
//	
//	private Dialog downlaoddialog;
//	
//	private boolean isForceDownload;
//	
//	private static final int UPDATE_FAILED = 6;
//	
//	public synchronized static VersionTool getInstance(Context context,LoadingDialog dialog,int invokeFlag) {
//	
//		if(mInstance == null) {
//			return new VersionTool(context, dialog,invokeFlag);
//		} else {
//			return mInstance;
//		}
//	}
//	
//	private Handler updateHandler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//
//			switch (msg.what) {
//			case INI.MSG.SUCCESS_CODE:
//				WD_RS_VersionInfo wd_RS_VersionInfo = (WD_RS_VersionInfo)msg.obj;
//				versionInfo = wd_RS_VersionInfo.getResult();
//				if(versionInfo.getStatus() == 0) { //已是最新版本
//					if(invokeFlag != 0) { //不是来自主界面
//						UIHelper.showCustomDialog(mContext, "已是最新版本", null);
//					}
//					
//				} else if(versionInfo.getStatus() == 1) { //可选更新
//					if(invokeFlag == 0) { //来自主界面
//						SharedPreferences sp = mContext.getSharedPreferences(INI.SHARE_PRE_NAME,Context.MODE_PRIVATE);
//						long lastShowTime = sp.getLong(INI.SP.UPDATE_ALERT, 0);								
//						if((lastShowTime + INI.update_alert) < System.currentTimeMillis()) {
//							showChoiceDialog(versionInfo.getReleaseNotes());
//						} 
//					} else { //来自设置界面
//						showChoiceDialog(versionInfo.getReleaseNotes());
//					}
//				} else if(versionInfo.getStatus() == 2) { //强制更新
//					showForceDialog(versionInfo.getReleaseNotes());
//				}
//				break;
//			case UPDATE_FAILED:
//				if(isForceDownload) {
//					downlaoddialog.cancel();
//				} else {
//					downlaoddialog.cancel();
//				}
//				MyToast.showText("更新失败，请稍后重试!");
//				if(isForceDownload) {
//					BaseApp.getInstance().finishProgram();
//				}
//				break;
//			}
//		}
//	};
//	
//	private VersionTool(Context context,LoadingDialog dialog,int invokeFlag) {
//		this.mContext = context;
//		this.mDialog = dialog;
//		this.invokeFlag = invokeFlag;
//		
//		if(mDialog != null) {
//			mDialog.setDialogText(context.getString(R.string.versionupdating));
//		}
//	}
//
//	/**
//	 * 检查更新
//	 */
//	public void check() {
//		//1.显示进度条
//		if(mDialog != null && !mDialog.isShowing()) {
//			mDialog.show();
//		}
//		if(!isChecking) {
//			NetUtils<WD_RS_VersionInfo> util = new NetUtils<WD_RS_VersionInfo>(mDialog,mContext);
//			util.get(INI.U.VERSION_CHECK + "?type=Android"
//					+ "&version=" + CommTool.getAppVersion(mContext),null, updateHandler,WD_RS_VersionInfo.class);
//			
//			isChecking = true;
////			NetUtils<VersionInfo> updateUtil = new NetUtils<VersionInfo>(updateHandler
////					, mDialog, mContext);
////			MyAjaxParams params0 = new MyAjaxParams(mContext);
////			params0.put(INI.P_VERSIONCODE,CommTool.getVersion(mContext));
////			updateUtil.post(INI.URL_VERSIONCHECK, params0, updateHandler, list, VersionInfo.class);
//		}
//	}
//	
//	/**
//	 * 显示"可选更新"对话框
//	 */
//	public void showChoiceDialog(String message) {
//		UIHelper.showCustomDialog(mContext, "版本升级 " + versionInfo.getVersion(), message,Gravity.LEFT, new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				showDownloadNotification(false);
//			}
//		}, new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if(invokeFlag == 0) { //来自主界面的调用，要做延时处理
//					SharedPreferences sp = mContext.getSharedPreferences(INI.SHARE_PRE_NAME,Context.MODE_PRIVATE);
//					Editor editor = sp.edit();
//					editor.putLong(INI.SP.UPDATE_ALERT, System.currentTimeMillis());
//					editor.commit();
//				}
//			}
//		});
//	}
//	
//	public void involvBrowser() {
//		Intent intent = new Intent();        
//        intent.setAction("android.intent.action.VIEW");    
//        Uri content_url = Uri.parse(versionInfo.getUrl());   
//        intent.setData(content_url);  
//        mContext.startActivity(intent);
//	}
//	
//	/**
//	 * 显示暂无更新对话框
//	 */
//	public void showCancelDialog() {
//		UIHelper.showCustomDialog(mContext, "已是最新版本", null);
//	}
//	
//	/**
//	 * 显示强制更新对话框
//	 */
//	public void showForceDialog(String message) {
//		
//		UIHelper.showCustomDialog(mContext, "版本升级 " + versionInfo.getVersion(), message,Gravity.LEFT, new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				showDownloadNotification(true);
//			}
//		}, new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				WodeApp.finishProgram();
//			}
//		});
//	}
//	
//	/**
//	 * 弹出下载框
//	 * @param isForceUpdate 
//	 */
//	private void showDownloadNotification(final boolean isForceUpdate) {
//		Intent intent=new Intent(mContext, DownloadService.class);  
//		Bundle bundle = new Bundle();
//		bundle.putString("downurl", versionInfo.getUrl());
//		bundle.putString("updatemsg", versionInfo.getVersion());
//		intent.putExtras(bundle);
//		//由intent启动service，后台运行下载进程，在服务中调用notifycation状态栏显示进度条
//		mContext.startService(intent);
//	}
//}
