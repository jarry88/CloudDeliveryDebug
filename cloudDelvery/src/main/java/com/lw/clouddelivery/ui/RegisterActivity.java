package com.lw.clouddelivery.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.BaseActivity;
import com.base.util.MyToast;
import com.lw.clouddelivery.CloudDeliveryAPP;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.util.CommTool;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.RegexUtil;

/**
 * 注册
 * @author leon
 *
 */
public class RegisterActivity extends BaseActivity {

	private String TAG= "RegisterActivity";
	
	private EditText register_mobile,resetpwd_msCodeET ,register_password,resetpwd_yaoqingma;
	private Button resetpwd_btn;
	private Button sendsmsBtn;
	private TextView register_phone_text;
	
	private RelativeLayout xiaolicity,peisongtool;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_register);
		getTitleBar().initTitleText("开设账号");
		register_mobile = (EditText)this.findViewById(R.id.register_mobile);
		resetpwd_msCodeET = (EditText)this.findViewById(R.id.resetpwd_msCodeET);
		register_password = (EditText)this.findViewById(R.id.register_password);
		resetpwd_yaoqingma = (EditText) this.findViewById(R.id.resetpwd_yaoqingma);

		//注册提交
		resetpwd_btn = (Button)this.findViewById(R.id.resetpwd_btn);
		resetpwd_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String mobile = register_mobile.getText().toString().trim();
				String password = register_password.getText().toString().trim();
				String codestr = resetpwd_msCodeET.getText().toString().trim();
				String	resetpwd_yaoqingmastr = resetpwd_yaoqingma.getText().toString().trim();
				
				if(TextUtils.isEmpty(mobile)){
					MyToast.showText(RegisterActivity.this, "请填写手机号!");
				}else if(TextUtils.isEmpty(password)){
					MyToast.showText(RegisterActivity.this, "请填写密码!");
				}else{
					if(CommTool.match(password)){
						DCUtil.postReginfo(RegisterActivity.this,pd, mobile
								, password
								,codestr ,resetpwd_yaoqingmastr,new Handler() {
									@Override
									public void handleMessage(Message msg) {
										super.handleMessage(msg);
										MyToast.showText(RegisterActivity.this,"注册成功");
										RegisterActivity.this.finish();
									}
						});
					}else{
						MyToast.showText(RegisterActivity.this, "密码由6-12位字母数字组合");
					}
				}
				
			}
		});
		
		
		
		//发送验证码
		sendsmsBtn = (Button)this.findViewById(R.id.sendsmsBtn);
		
		if(CloudDeliveryAPP.loginTime != 40){
			sendsmsBtn.setEnabled(false);
			sendsmsBtn.setText(String.format(getResources().getString(R.string.login_reg_txt_yzm_reciprocal), CloudDeliveryAPP.loginTime));
			new Thread(CloudDeliveryAPP.getInstance().getRegisterCodeRunnable(handler)).start();
		}
		sendsmsBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String mobilestr = register_mobile.getText().toString().trim();
				if(TextUtils.isEmpty(mobilestr) || !RegexUtil.checkTelephone(mobilestr)){
					MyToast.showText(RegisterActivity.this, "请输入正确的手机号码!");
				}else{
					sendsmsBtn.setEnabled(false);
					DCUtil.sendPwdSMS(RegisterActivity.this, mobilestr, new Handler() {
						@Override
						public void handleMessage(Message msg) {
							super.handleMessage(msg);
							new Thread(CloudDeliveryAPP.getInstance().getRegisterCodeRunnable(handler)).start();
							MyToast.showText(RegisterActivity.this, "验证码发送成功!");
						}
					});
				}
			}
		});
		
		//拨打电话
		register_phone_text = (TextView) this.findViewById(R.id.register_phone_text);
		register_phone_text.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String number=register_phone_text.getText().toString().trim();
				 Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number));  
	                startActivity(intent);  
			}
		});
		xiaolicity=(RelativeLayout) this.findViewById(R.id.xiaolicity);
		
		
		//交通工具
		peisongtool=(RelativeLayout) this.findViewById(R.id.peisongtool);
		peisongtool.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent itool=new Intent(RegisterActivity.this,ChoiceVehicleActivity.class);
				itool.putExtra("itool", "reg");
				startActivity(itool);
			}
		});
		
	}
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				sendsmsBtn.setText(String.format(getResources().getString(R.string.login_reg_txt_yzm_reciprocal), CloudDeliveryAPP.loginTime));
				break;
			case 1:
				sendsmsBtn.setEnabled(true);
				sendsmsBtn.setText("发送验证码");
				break;
			default:
				break;
			}
		}
	};
	
	public void onDestroy() {
		super.onDestroy();
		handler.removeMessages(1);
		handler.removeMessages(0);
		CloudDeliveryAPP.getInstance().getRegisterCodeRunnable(null);
	};
}
