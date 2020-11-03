package com.lw.clouddelivery.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.base.BaseActivity;
import com.base.util.MyToast;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.util.DCUtil;

/**
 * 重置密码界面
 * @author leon
 *
 */
public class ResetPwdActivity extends BaseActivity {

	private EditText mobileET,pwdET,confirmPwdET,smsCodeET;
	private Button submitBtn;
	private Button sendSmsBtn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_resetpwd);
		getTitleBar().initTitleText("重置密码");
		mobileET = (EditText)this.findViewById(R.id.resetpwd_mobile);
		pwdET = (EditText)this.findViewById(R.id.resetpwd_pwd);
		confirmPwdET = (EditText)this.findViewById(R.id.resetpwd_confirmpwd);
		smsCodeET = (EditText)this.findViewById(R.id.resetpwd_msCodeET);
		submitBtn = (Button)this.findViewById(R.id.resetpwd_btn);
		
		submitBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String pwd = pwdET.getText().toString().trim();
				String confrimPwd = confirmPwdET.getText().toString().trim();
				if(pwd.equals(confrimPwd)) {
					DCUtil.resetPwd(ResetPwdActivity.this, mobileET.getText().toString().trim()
							, confirmPwdET.getText().toString().trim()
							, smsCodeET.getText().toString().trim(),new Handler() {
								@Override
								public void handleMessage(Message msg) {
									super.handleMessage(msg);
									MyToast.showText(ResetPwdActivity.this,"更改密码成功");
									ResetPwdActivity.this.finish();
								}
					});
				} else {
					MyToast.showText(ResetPwdActivity.this, "两次密码输入不一致!");
				}
			}
		});
		
		sendSmsBtn = (Button)this.findViewById(R.id.sendsmsBtn);
		sendSmsBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DCUtil.sendPwdSMS(ResetPwdActivity.this, mobileET.getText().toString().trim(), new Handler() {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						MyToast.showText(ResetPwdActivity.this, "验证码发送成功!");
					}
				});
			}
		});
	}
}
