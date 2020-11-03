package com.lw.clouddelivery.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.base.util.BaseCommTool;
import com.base.util.MyToast;
import com.base.widget.LoadingDialog;
import com.google.gson.reflect.TypeToken;
import com.lw.clouddelivery.CloudDeliveryAPP;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.CashAccount;
import com.lw.clouddelivery.bean.Help;
import com.lw.clouddelivery.bean.HelpDetail;
import com.lw.clouddelivery.bean.MyIncoming;
import com.lw.clouddelivery.bean.MyMessage;
import com.lw.clouddelivery.bean.Order;
import com.lw.clouddelivery.bean.OrderCount;
import com.lw.clouddelivery.bean.Problem;
import com.lw.clouddelivery.bean.Punish;
import com.lw.clouddelivery.bean.ShouZhiItem;
import com.lw.clouddelivery.bean.User;
import com.lw.clouddelivery.bean.Vehicle;
import com.lw.clouddelivery.bean.Version;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.ui.AuthenticationActivity;
import com.lw.clouddelivery.ui.HomePageActivity;
import com.lw.clouddelivery.ui.LoginActivity;
import com.lw.clouddelivery.ui.TainExamActivity;

public class DCUtil {

	/**
	 * 请求个人信息数据
	 * @param context
	 * @param handler
	 */
	public static void  requestUserInfo(final Context context) {
		NetUtils<User> util = new NetUtils<User>(null, context);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.ID,MySPTool.getUid(context));
		FinalHttp fh = new FinalHttp();
		fh.post(INI.U.USER_INFO, params, new AjaxCallBack<Object>() {

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				JSONObject jsonObj;
				try {
					jsonObj = new JSONObject(t.toString());
					User user = new User();
					user.setRegtime(jsonObj.optString("regtime"));
					user.setRoleid(jsonObj.optString("roleid"));
					user.setUname(jsonObj.optString("uname"));
					user.setTotalcore(jsonObj.optInt("score"));
					user.setPassword(jsonObj.optString("password"));
					user.setLogid(jsonObj.optInt("logid"));
					user.setMobile(jsonObj.optString("mobile"));
					 user.setImg1(jsonObj.optString("img1"));
					 user.setImg2(jsonObj.optString("img2"));
					 user.setImg3(jsonObj.optString("img3"));
					 user.setAdd(jsonObj.optString("add"));
					 user.setHeight(jsonObj.optString("height"));
					 user.setWeight(jsonObj.optString("weight"));
					 user.setScore(jsonObj.optString("score"));
					 
					//2016年9月22日 11:48:27 增加考试个人资料
					MySPTool.putString(context, INI.SP.ADD, user.getAdd());
					MySPTool.putString(context, INI.SP.IMG1, user.getImg1());
					MySPTool.putString(context, INI.SP.IMG2, user.getImg2());
					MySPTool.putString(context, INI.SP.IMG3, user.getImg3());
					MySPTool.putString(context, INI.SP.WEIGHT, user.getWeight());
					MySPTool.putString(context, INI.SP.HEIGHT, user.getHeight());
					MySPTool.putString(context, INI.SP.SCORE, user.getScore());
					
					double score = 0;
					if(!TextUtils.isEmpty(user.getScore())){
						score = Double.parseDouble(user.getScore());
					}
					
//					if((TextUtils.isEmpty(MySPTool.getString(context, INI.SP.IMG1, INI.SP.IMG1))||
//						TextUtils.isEmpty(MySPTool.getString(context, INI.SP.IMG2, INI.SP.IMG2))||
//						TextUtils.isEmpty(MySPTool.getString(context, INI.SP.IMG3, INI.SP.IMG3))||
//						TextUtils.isEmpty(MySPTool.getString(context, INI.SP.ADD, INI.SP.ADD))||
//						TextUtils.isEmpty(MySPTool.getString(context, INI.SP.WEIGHT, INI.SP.WEIGHT))||
//						TextUtils.isEmpty(MySPTool.getString(context, INI.SP.HEIGHT, INI.SP.HEIGHT))||
//						score<100)){
					if(TextUtils.isEmpty(user.getImg1())||TextUtils.isEmpty(user.getImg2())||
							TextUtils.isEmpty(user.getImg3())||
							TextUtils.isEmpty(user.getWeight())||
							TextUtils.isEmpty(user.getHeight())/*||score<100*/){
					
							DialogUtils.AlearImageSeletDialog(context, new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// 完成考试
									Intent intentauth =new Intent();
									intentauth.setClass(context, TainExamActivity.class);
									context.startActivity(intentauth);
									
								}
							}, new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// 认证资料
									Intent intentauth =new Intent();
									intentauth.setClass(context, AuthenticationActivity.class);
									context.startActivity(intentauth);
								}
							}, true);
					}
					
					MySPTool.putString(context, INI.SP.PHONE, user.getMobile());
					MySPTool.putString(context, INI.SP.UNAME, jsonObj.optString("uname"));
					
					String bank_type = jsonObj.optString("bank_type");
					String bank_name = jsonObj.optString("bank_name");
					String bank_card_no = jsonObj.optString("bank_card_no");
					String uidname = jsonObj.optString("uidname"); //持卡人姓名
					
					//保存银行卡信息
					MySPTool.putString(context, INI.SP.CARD_NO, bank_card_no);
					MySPTool.putString(context, INI.SP.BANK, bank_type);
					MySPTool.putString(context, INI.SP.BANK_NAME, bank_name);
					MySPTool.putString(context, INI.SP.CARD_NAME, uidname);
					
//					BaseApp.getInstance().saveObject(user, "user");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
			}
		});
	}
	
	/**
	 *  请求消息列表
	 * @param c
	 * @param pd
	 * @param currentPage
	 */
	public static void requestMessageList(Context c,LoadingDialog pd,int currentPage,String uid,int type,Handler handler) {
		NetUtils<MyMessage> util = new NetUtils<MyMessage>(pd, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.CURRENT_PAGE, currentPage + "");
		params.put(INI.P.UID,uid);
		params.put(INI.P.TYPE, type + "");
		util.post(INI.U.MESSAGE_LIST, params, true,handler,new TypeToken<JsonArrayBase<MyMessage>>(){},true);
	}
	
	/**
	 * 请求帮助列表
	 * @param c
	 * @param pd
	 * @param type 列表类型，1表示常见问题，2表示员工手册
	 */
	public static void requestHelpList(Context c,LoadingDialog pd,int type,Handler handler) {
		NetUtils<Help> util = new NetUtils<Help>(pd, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.TYPE, type + "");
		util.post(INI.U.HELP_LIST, params, true,handler,new TypeToken<JsonArrayBase<Help>>(){});
	}
	
	/**
	 * 请求帮助列表
	 * @param c
	 * @param pd
	 * @param type 列表类型，1表示常见问题，2表示员工手册
	 */
	public static void requestHelpDetail(Context c,LoadingDialog pd,String id,Handler handler) {
		NetUtils<HelpDetail> util = new NetUtils<HelpDetail>(pd, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.ID, id);
		util.post(INI.U.HELP_DETAIL, params, false,handler,new TypeToken<JsonObjBase<HelpDetail>>(){});
	}
	
	/**
	 * 今日订单列表
	 * @param c
	 * @param pd
	 * @param uid 用户已抢订单列表需要包含该参数
	 * @param lon
	 * @param lat
	 */
	public static void requestJROrderList(Context c,LoadingDialog pd,String uid,String lon,String lat,Handler handler) {
		NetUtils<Order> util = new NetUtils<Order>(pd, c);
		AjaxParams params = new AjaxParams();
		if(uid != null) {
			params.put(INI.P.UID, uid);
		}
		params.put(INI.P.LON, lon);
		params.put(INI.P.LAT, lat);
		util.post(INI.U.ORDER_TODAY, params, true,handler,new TypeToken<JsonArrayBase<Order>>(){},false);
	}
	
	/**
	 * 今日订单列表
	 * @param c
	 * @param pd
	 * @param uid 用户已抢订单列表需要包含该参数
	 * @param lon
	 * @param lat
	 */
	public static void requestQDOrderList(Context c,LoadingDialog pd,String uid,String lon,String lat,Handler handler) {
		NetUtils<Order> util = new NetUtils<Order>(pd, c);
		AjaxParams params = new AjaxParams();
		if(uid != null) {
			params.put(INI.P.UID, uid);
		}
		params.put(INI.P.LON, lon);
		params.put(INI.P.LAT, lat);
		util.post(INI.U.QIANGDAN_LIST, params, true,handler,new TypeToken<JsonArrayBase<Order>>(){},false);
	}
	
	
	
	/**
	 * 请求今日订单完成情况
	 */
	public static void requestJROrderCount(Context c,LoadingDialog pd,String uid,Handler handler) {
		NetUtils<OrderCount> util = new NetUtils<OrderCount>(pd, c);
		AjaxParams params = new AjaxParams();
		if(uid != null) {
			params.put(INI.P.UID, uid);
		}
		util.post(INI.U.ORDER_COUNT, params, false,handler,new TypeToken<JsonObjBase<OrderCount>>(){});
	}
	
	
	/**
	 *  请求交通工具列表
	 * @param c
	 * @param pd
	 */
	public static void requestVehicleList(Context c,LoadingDialog pd,Handler handler) {
		NetUtils<Vehicle> util = new NetUtils<Vehicle>(pd, c);
		util.post(INI.U.VEHICLE_LIST, null, true,handler,new TypeToken<JsonArrayBase<Vehicle>>(){});
	}
	
	/**
	 * 选择交通工具
	 * @param c
	 * @param pd
	 * @param vehicleId
	 * @param uid
	 */
	public static void selectMyVehicle(Context c,LoadingDialog pd,String vehicleId,String uid,Handler handler) {
		NetUtils<JsonBase> util = new NetUtils<JsonBase>(pd, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.TYPE,vehicleId);
		params.put(INI.P.UID, uid);
		util.post(INI.U.SELECT_VEHICLE, params,false,handler,null);
	}
	
	/**
	 * 提交意见反馈到服务器
	 * @param c
	 * @param pd
	 * @param mobile
	 * @param title
	 * @param content
	 * @param type 1意见反馈，2上传事件
	 */
	public static void commitFeedback(Context c,LoadingDialog pd
			,String mobile,String title,String content,int type,Handler handler) {
		NetUtils<JsonBase> util = new NetUtils<JsonBase>(pd, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.MOBILE,mobile);
		params.put(INI.P.TITLE, title);
		params.put(INI.P.TYPE, type + "");
		params.put(INI.P.CONTENT, content);
		util.post(INI.U.FEEDBACE, params, false,handler,null);
	}
	
	/**
	 * 
	 * @param c
	 * @param pd
	 * @param mobile
	 * @param title
	 * @param content
	 * @param type
	 * @param handler
	 */
	public static void qiangdanClose(Context c,LoadingDialog pd
			,String orderId,Handler handler) {
		NetUtils<JsonBase> util = new NetUtils<JsonBase>(pd, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.ID, orderId);
		util.post(INI.U.CLOSEORDER, params, false,handler,null);
	}
	
	/**
	 * 抢单
	 * @param c
	 * @param uid 用户id
	 * @param id 订单id
	 * @param handler
	 */
	public static void qiangdan(Context c,String uid,int id,Handler handler) {
		NetUtils<String> util = new NetUtils<String>(null, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.UID,uid);
		params.put(INI.P.ID, id + "");
		util.post(INI.U.QIANGDAN, params, false,handler,null,true,false);
	}
	
	/**
	 * 发送短信验证码接口
	 * @param c
	 * @param id
	 * @param type 2表示取件，1表示收件，先取件再收件
	 * @param ctype 1不重置密码，2重置密码
	 */
	public static void sendSMS(Context c,String id,String type,String ctype,Handler handler) {
		NetUtils<String> util = new NetUtils<String>(null, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.TYPE,type);
		params.put(INI.P.ID, id + "");
		params.put(INI.P.CTYPE, ctype);
		util.post(INI.U.	SEND_SMS, params, false,handler,null);
	}
	
	/**
	 * 发送短信验证码接口
	 * @param c
	 * @param id
	 * @param type 2表示取件，1表示收件，先取件再收件
	 * @param ctype 1不重置密码，2重置密码
	 */
	public static void sendPwdSMS(Context c,String mobile,Handler handler) {
		NetUtils<String> util = new NetUtils<String>(null, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.MOBILE,mobile);
		util.post(INI.U.SEND_PWD_SMS, params, false,handler,null);
	}
	
	/**
	 * 验证取件密码
	 * @param c
	 * @param id
	 * @param pwd
	 * @param handler
	 */
	public static void checkQuJianSMS (Context c,String id,String pwd,Handler handler) {
		NetUtils<String> util = new NetUtils<String>(null, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.PWD,pwd);
		params.put(INI.P.ID, id + "");
		params.put(INI.P.UID, MySPTool.getUid(c));
		util.post(INI.U.	CHECK_QUJIAN_SMS, params, false,handler,null);
	}
	
	
	/**
	 * 验证取件密码
	 * @param c
	 * @param id
	 * @param pwd
	 * @param handler
	 */
	public static void checkShouJianSMS (Context c,String id,String pwd,String uid,Handler handler) {
		NetUtils<String> util = new NetUtils<String>(null, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.PWD,pwd);
		params.put(INI.P.ID, id + "");
		params.put(INI.P.UID, uid);
		util.post(INI.U.	CHECK_SHOUJIAN_SMS, params, false,handler,null);
	}
	
	/**
	 * 获取我的收入接口
	 */
	public static void getMyIncoming(Context c,String uid,Handler handler) {
		NetUtils<MyIncoming> util = new NetUtils<MyIncoming>(null, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.UID, uid);
		util.post(INI.U.	MY_INCOMING, params, false,handler,new TypeToken<JsonObjBase<MyIncoming>>(){});
	}

	/**
	 * 我的订单列表接口
	 * @param c
	 * @param uid
	 * @param handler
	 */
	public static void requestMyOrderList(Context c,LoadingDialog pd,String uid,Handler handler) {
		NetUtils<Order> util = new NetUtils<Order>(pd, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.UID, uid);
		util.post(INI.U.	MY_ORDERLIST, params, true,handler,new TypeToken<JsonArrayBase<Order>>(){});
	}
	
	/**
	 * 请求现金账户数据
	 * @param c
	 * @param pd
	 * @param uid
	 * @param handler
	 */
	public static void requestCashAccount(Context c,LoadingDialog pd,String uid,Handler handler) {
		NetUtils<CashAccount> util = new NetUtils<CashAccount>(pd, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.UID, uid);
		util.post(INI.U.	CASH_ACCOUNT, params, false,handler,new TypeToken<JsonObjBase<CashAccount>>(){});
	}
	
	/**
	 * 获取收支明细接口
	 * @param c
	 * @param pd
	 * @param uid
	 * @param handler
	 */
	public static void requestShouZhiList(Context c,LoadingDialog pd,String uid,int page,Handler handler) {
		NetUtils<ShouZhiItem> util = new NetUtils<ShouZhiItem>(pd, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.UID, uid);
		params.put(INI.P.CURRENT_PAGE, page + "");
		util.post(INI.U.	SHOUZHI_LIST, params, true,handler,new TypeToken<JsonArrayBase<ShouZhiItem>>(){},true);
	}
	
/**
 * 获取奖惩记录接口
 * @param c
 * @param pd
 * @param page
 * @param recard_type A005奖励，A006惩罚
 * @param handler
 */
	public static void requestPunishList(Context c,LoadingDialog pd,int page,String recard_type,Handler handler) {
		NetUtils<Punish> util = new NetUtils<Punish>(pd, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.UID, MySPTool.getUid(c));
		params.put(INI.P.CURRENT_PAGE, page + "");
		params.put(INI.P.RECARD_TYPE, recard_type);
		util.post(INI.U.	JC_LIST, params, true,handler,new TypeToken<JsonArrayBase<Punish>>(){},true);
	}
	
	/**
	 * 重置密码
	 * @param c
	 * @param uid 用户id
	 * @param id 订单id
	 * @param handler
	 */
	public static void resetPwd(Context c,String mobile,String pwd,String smsCode,Handler handler) {
		NetUtils<String> util = new NetUtils<String>(null, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.MOBILE,mobile);
		params.put(INI.P.PASSWORD,pwd);
		params.put(INI.P.SOURS, smsCode);
		util.post(INI.U.	RESET_PWD, params, false,handler,null);
	}
	
	/**
	 * 上传图片到服务器
	 * @param c
	 * @param pd
	 * @param orderId
	 * @param type
	 * @param handler
	 */
	public static void updateImgs(Context c,LoadingDialog pd,String orderId,ArrayList<File> files,int type,Handler handler) {
		NetUtils<JsonBase> util = new NetUtils<JsonBase>(pd, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.UID, MySPTool.getUid(c));
		params.put(INI.P.ORDERID,orderId);
		params.put(INI.P.TYPE, type + "");
		try {
			int limit = files.size() <= 5?files.size():5;
			for(int i=0;i<limit;i++) {
				params.put("img" + (i+1), files.get(i));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		util.post(INI.U.	UPDATE_IMG, params, false,handler,null);
	}
	
	/**
	 * 根据statusid返回状态字符串
	 * @param orderStatusId
	 * @return
	 */
	public static String getStatusStr(String orderStatusId) {
		if(orderStatusId.equals(INI.STATE.ORDER_CANCEL)) {
			return "已取消";
		} else if(orderStatusId.equals(INI.STATE.ORDER_CANCELLING)) {
			return "取消中";
		} else if(orderStatusId.equals(INI.STATE.ORDER_DELIVERY)) {
			return "闪送中";
		} else if(orderStatusId.equals(INI.STATE.ORDER_FINISHED)) {
			return "已完成";
		} else if(orderStatusId.equals(INI.STATE.ORDER_TAKING)) {
			return "取单中";
		} else {
			return "待抢单";
		}
	}
	
	/**
	 * 提现接口 
	 * @param uid
	 * @param money
	 */
	public static void tixian(Context c,String uid,String money,Handler handler) {
		NetUtils<String> util = new NetUtils<String>(null, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.UID,uid);
		params.put(INI.P.MONEY,money);
		util.post(INI.U.	TIXIAN, params, false,handler,null);
	}
	
	/**
	 * 是否可以提现
	 */
	public static void canTiXian(Context c,String uid,Handler handler) {
		NetUtils<String> util = new NetUtils<String>(null, c);
		AjaxParams params = new AjaxParams();
		params.put(INI.P.UID,uid);
		util.post(INI.U.CAN_TIXIAN, params, false,handler,null);
	}
	
	/**
	 * 请求常用问题列表
	 * @param c
	 * @param pd
	 * @param type
	 * @param handler
	 */
		public static void requestProblemList(Context c,LoadingDialog pd,Handler handler) {
			NetUtils<Problem> util = new NetUtils<Problem>(pd, c);
			AjaxParams params = new AjaxParams();
			params.put(INI.P.TYPE, "1");
			util.post(INI.U.HELP_LIST, params, true,handler,new TypeToken<JsonArrayBase<Problem>>(){});
		}
		
		/**
		 *  版本更新
		 */
		public static void versionCheck(LoadingDialog pd,final Context c,final boolean showToast) {
			NetUtils<Version> util = new NetUtils<Version>(pd, c);
			AjaxParams params = new AjaxParams();
			util.post(INI.U.	VERSION_LIST, params, false,new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					final Version version = (Version) msg.obj;
					Double vInt = Double.valueOf(version.getNumber());
					int appVersioni = getAppNumber(c);
					if(appVersioni == 0) {
						MyToast.showText(c, "检查版本失败!");
						return;
					}
					Double appVersiond=(double) appVersioni;
					if(appVersiond < vInt) {
						AlertDialog.Builder builder = new AlertDialog.Builder(c);
						builder.setCancelable(false);
						builder.setTitle("提示")
						.setMessage("发现新版本，请立即更新")
						.setPositiveButton("好的", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Uri  uri = Uri.parse(version.getUrl());
								 Intent  intent = new  Intent(Intent.ACTION_VIEW, uri);
								 c.startActivity(intent);
								 CloudDeliveryAPP.getInstance().finishProgram();
							}
						} ).setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//本地测试 暂时注释  caryhua 2016年1月9日16:15:25
//								CloudDeliveryAPP.getInstance().finishProgram();
							}
						}).show();
					} else {
						if(showToast) {
							MyToast.showText(c, "已经是最新版本!");
						}
					}
				}
			},new TypeToken<JsonObjBase<Version>>(){});
		}
		
		public static int getAppNumber(Context c){
		    try {
		        PackageManager manager = c.getPackageManager();
		        PackageInfo info = manager.getPackageInfo(c.getPackageName(), 0);
		        String version = info.versionName;
		        int ver = Integer.valueOf(version.substring(version.indexOf(".") + 1, version.length()));
		        return ver;
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return 0;
		}
		
	/**
	 * 提交银行卡信息数据
	 * @param c
	 * @param pd
	 * @param type
	 * @param handler
	 */
		public static void postBankInfo(Context c,LoadingDialog pd,String name
				,String cardno,String banktype,String bankname,Handler handler) {
			NetUtils<String> util = new NetUtils<String>(pd, c);
			AjaxParams params = new AjaxParams();
			params.put(INI.P.NAME, name);
			params.put(INI.P.CARD_NO,cardno);
			params.put(INI.P.BANK_TYPE, banktype);
			params.put(INI.P.BANK_NAME, bankname);
			params.put(INI.P.ID, MySPTool.getUid(c));
			util.post(INI.U.BANKINPUT, params, true,handler,null);
		}
			
	/**
	 * 注册个人信息
	 * @param c
	 * @param pd
	 * @param type
	 * @param handler
	 */
		public static void postReginfo(Context c,LoadingDialog pd,String mobile
				,String password,String code,String yaoqingcode,Handler handler) {
			NetUtils<String> util = new NetUtils<String>(pd, c);
			AjaxParams params = new AjaxParams();
			params.put(INI.P.MOBILE, mobile);
			params.put(INI.P.PWD,password);
			params.put(INI.P.SOURS, code);
			params.put(INI.P.rmobileNo, yaoqingcode);
			util.post(INI.U.REGISTERURL, params, true,handler,null);
		}
		/**
		 * 更新考试成绩
		 * @param c
		 * @param pd
		 * @param score
		 * @param handler
		 */
			public static void postSourceinfo(Context c,LoadingDialog pd, String score,Handler handler) {
				NetUtils<String> util = new NetUtils<String>(null, c);
				AjaxParams params = new AjaxParams();
				params.put(INI.P.SCORE, score);
				params.put(INI.P.UID, MySPTool.getUid(c));
				util.post(INI.U.EXAMUPDATESOURCE, params, true,handler,null);
			}
		/**
		 * 填写资料
		 * @param c
		 * @param pd
		 * @param name
		 * @param height
		 * @param weight
		 * @param img1
		 * @param img2
		 * @param img3
		 * @param handler
		 */
		public static void postCommitInfo(Context c,LoadingDialog pd,String name,
				String cardid,String  sex,String height,String weight,File img1,File img2,File img3,Handler handler) {
			try {
				NetUtils<String> util = new NetUtils<String>(pd, c);
				AjaxParams params = new AjaxParams();
				params.put(INI.P.ID, MySPTool.getUid(c));
				params.put(INI.P.NAME, name);
				params.put(INI.P.CARDID, cardid);
				params.put(INI.P.SEX, sex);
				params.put(INI.P.TALLER,height);
				params.put(INI.P.WEIGHT, weight);
				params.put(INI.P.IMGONE, img1);
				params.put(INI.P.IMGTWO, img2);
				params.put(INI.P.IMGTHREE, img3);
				params.put("state", "0");
				params.put("add", "上海");
				util.post(INI.U.UPDATEUSERINFO, params, true,handler,null);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}	
}
