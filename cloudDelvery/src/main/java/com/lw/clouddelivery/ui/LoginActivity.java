package com.lw.clouddelivery.ui;

import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.base.BaseActivity;
import com.base.util.BaseSPTool;
import com.base.util.LogWrapper;
import com.base.util.MyToast;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Help;
import com.lw.clouddelivery.bean.HelpDetail;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.ui.adapter.HelpGridAdapter;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.MySPTool;
import com.lw.clouddelivery.util.UIHelper;
import com.lw.clouddelivery.util.polling.PollingService;
import com.lw.clouddelivery.util.polling.PollingUtils;

public class LoginActivity extends BaseActivity {

	private EditText phoneET,pwdET;
	private Button loginBtn;
	private TextView findPwdTV;
	
	private Button btn_registrationUser;//注册
	private TextView user_agreement;//用户协议
	
	private String areementtitle;
	private String areementcontent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_login);
		getTitleBar().initTitleText(R.string.btn_login);
		getTitleBar().getIBRight().setVisibility(View.GONE);
		findViews();
		
		DCUtil.requestHelpDetail(this,pd,"38",new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				HelpDetail hd=(HelpDetail) msg.obj;
				areementtitle = hd.getTitle();
				areementcontent = hd.getContent();
			}
			
		});
	}

	@Override
	public void findViews() {
		super.findViews();
		phoneET = (EditText)this.findViewById(R.id.login_et_phone);
		pwdET = (EditText)this.findViewById(R.id.login_et_pwd);
		loginBtn = (Button)this.findViewById(R.id.login_btn);
		findPwdTV = (TextView)this.findViewById(R.id.login_findPwdTV);
		btn_registrationUser= (Button)this.findViewById(R.id.btn_registrationUser);
		
		
		findPwdTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showResetPwd(LoginActivity.this);
			}
		});
		
		loginBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loginBtn.setEnabled(false);
				FinalHttp fh = new FinalHttp();
				AjaxParams params = new AjaxParams();
				params.put(INI.P.MOBILE, phoneET.getText().toString());
				params.put(INI.P.PASSWORD, pwdET.getText().toString());
				params.put(INI.P.TOKEN,"12445332");
				Log.v("LoginActivity66", INI.U.LOGIN+"?"+INI.P.MOBILE+"="+ phoneET.getText().toString()+"&"+INI.P.PASSWORD+"="+pwdET.getText().toString()+"&"+INI.P.TOKEN+"=12445332");
				if (pd != null) {
					pd.show();
				}	
				fh.post(INI.U.LOGIN, params, new AjaxCallBack<String>() {
					@Override
					public void onSuccess(String t) {
						loginBtn.setEnabled(true);
						super.onSuccess(t);
						if (pd != null) {
							pd.cancel();
						}
						LogWrapper.e(LoginActivity.class, t);
						JSONObject jsonObj;
						try {
							jsonObj = new JSONObject(t);
							if(jsonObj.opt("code").equals("002")) {
								String uid = jsonObj.optString("uid");
								String token = jsonObj.optString("token");
								
								//保存登陆信息
								BaseSPTool.putString(LoginActivity.this,INI.SP.PHONE, phoneET.getText().toString());
								BaseSPTool.putString(LoginActivity.this, INI.SP.PASSWORD, pwdET.getText().toString());
								BaseSPTool.putString(LoginActivity.this, INI.SP.UID, uid);
								BaseSPTool.putString(LoginActivity.this, INI.SP.TOKEN, token);
								
								
								UIHelper.showMainPage(LoginActivity.this);
								LoginActivity.this.finish();
							} else {
								String msg = jsonObj.optString("msg");
								MyToast.showText(LoginActivity.this,  msg);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						if (pd != null) {
							pd.cancel();
						}	
						loginBtn.setEnabled(true);
						if(strMsg != null) {
							LogWrapper.e(LoginActivity.class, strMsg);
						}
					}
				});
			}
		});
		//注册页面
		btn_registrationUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inreg=new Intent();
				inreg.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(inreg);
			}
		});
		
		
		//用户协议
		user_agreement= (TextView) findViewById(R.id.user_agreement);
		user_agreement.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showHelpDetail(LoginActivity.this, areementtitle, areementcontent);
			}
		});
		
	}
	
}
