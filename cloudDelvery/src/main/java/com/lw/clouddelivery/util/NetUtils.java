package com.lw.clouddelivery.util;

import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.base.util.LogWrapper;
import com.base.util.MyToast;
import com.base.util.NetUtil;
import com.google.gson.reflect.TypeToken;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Vehicle;
import com.lw.clouddelivery.conf.INI;

/**
 * 接口数据请求工具类
 */
public class NetUtils<T> {
	private Dialog mDialog;
	private Context mContext;
	
	public void setDialog(Dialog dialog) {
		this.mDialog = dialog;
	}	
	public NetUtils( Dialog mDialog, Context context) {
		this.mDialog = mDialog;
		this.mContext = context;
	}	
	public void post(final String url,final AjaxParams params, final boolean isArray,final Handler handler,final TypeToken typeToken) {
		post(url, params, isArray, handler, typeToken,false,true);
	}
	
	public void post(final String url,final AjaxParams params, final boolean isArray,final Handler handler,final TypeToken typeToken,boolean showToast) {
		post(url, params, isArray, handler, typeToken,false,showToast);
	}
	
	/**
	 * 
	 * @param url
	 * @param params
	 * @param isArray
	 * @param handler
	 * @param typeToken
	 * @param isSendError 是否反馈错误情况，有时需要，比如数据分业时，最后一页时需要提示用户“已加载全部数据“
	 */
	public void post(final String url,final AjaxParams params, final boolean isArray,final Handler handler,final TypeToken typeToken,final boolean isSendError,final boolean showToast) {
		LogWrapper.e(NetUtil.class, url);
		if (CommTool.isNetworkConnected(mContext)) {
			FinalHttp fh = new FinalHttp();
			if (mDialog != null) {
				mDialog.show();
			}			
			fh.post(url, params, new AjaxCallBack<String>() {
				@Override
				public void onSuccess(String t) {
					super.onSuccess(t);
					if(params!= null) {
						LogWrapper.e(NetUtil.class, "URL:" + url + "|Params:" + params.toString() + "|Result:请求成功! " +t.toString());
					} else {
						LogWrapper.e(NetUtil.class, "URL:" + url + "|Params:" + "null" + "|Result:请求成功! " +t.toString());
					}
					JsonDataParse<T> jsonParse = new JsonDataParse<T>();
					JsonBase jsonBase;
					if(typeToken != null){
							if(isArray) {		
								 jsonBase = (JsonArrayBase<T>) jsonParse.createArrayEntity(t,typeToken);								
							} else {
								 jsonBase = (JsonObjBase<T>) jsonParse.createObjEntity(t,typeToken);
							}
					} else {
						jsonBase = jsonParse.creaBaseEntity(t);						
					}
					if (mDialog != null && mDialog.isShowing()) {
						mDialog.cancel();
					}
					
					if(jsonBase.getCode().equals(INI.CODE.OK)) {
						if(handler!= null) {
							Message msg =new Message();
							if(isArray) {
								if(typeToken != null) {
									msg.obj= ((JsonArrayBase<T>) jsonBase).getDate();
									LogWrapper.e(NetUtils.class,"解析成功:" +  ((JsonArrayBase<T>) jsonBase).getDate().toString());
								}
							} else  {
								if(typeToken != null) {
									msg.obj = ((JsonObjBase<T>) jsonBase).getDate();
									LogWrapper.e(NetUtils.class,"解析成功:" + ((JsonObjBase<T>) jsonBase).getDate().toString());
								}
							}
							msg.arg1 = 1; //1表示成功,即code="00"
							handler.sendMessage(msg);
						}
					} else {
						if(showToast) {
							MyToast.showText(mContext, jsonBase.getMsg());
						}
						LogWrapper.e(NetUtil.class, "失败:code" + jsonBase.getCode() + "|msg:"+ jsonBase.getMsg());
						if(isSendError) {
							Message msg = new Message();
							msg.obj = jsonBase;
							msg.arg1 = -1; //1表示失败,即code != "00"
							handler.sendMessage(msg);
						}
					}
				}
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					super.onFailure(t, errorNo, strMsg);
					if (mDialog != null && mDialog.isShowing()) {
						mDialog.cancel();
					}
//					LogWrapper.e(NetUtil.class, "URL:" + url + "|Params:" + params.toString() + "|Result:" + " 请求失败:" + strMsg.toString()
//							+ "|" + t.toString() + "|" + errorNo);
				}
			});
		} else {
			MyToast.showText(mContext,R.string.error_network);
			if (mDialog != null) {
				mDialog.cancel();
			}
		}
	}
}
