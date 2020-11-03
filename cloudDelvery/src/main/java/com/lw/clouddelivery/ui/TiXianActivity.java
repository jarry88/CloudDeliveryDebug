package com.lw.clouddelivery.ui;

import java.util.Calendar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.base.BaseActivity;
import com.base.util.MyToast;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.MySPTool;

/**
 *  提现界面
 * @author leon
 *
 */
public class TiXianActivity extends BaseActivity {

	private double tixianAvailable; //可提现金额
	private TextView tixianTV;
	private Button submitBtn;
	private EditText tixianValueET;
    double tixianValue =0.00;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_tixian);
		getTitleBar().initTitleText("提现");
		tixianAvailable = getIntent().getDoubleExtra("tixianAvailable", 0.00);
		findViews();
		fillData();
	}
	@Override
	public void fillData() {
		super.fillData();
		tixianTV.setText(tixianAvailable + " 元");
		submitBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//添加提现规则，余额大于200
				String tixianValuestr = tixianValueET.getText().toString().trim();
				if( !(TextUtils.isEmpty(tixianValuestr))  ){
					tixianValue= Double.valueOf(tixianValuestr);
					if(tixianValue <=  0) {
						MyToast.showText(TiXianActivity.this, "提现失败，提现金额不少于0元");
						return ;
					}
					if((tixianAvailable - tixianValue) < 200) {
						MyToast.showText(TiXianActivity.this, "提现失败，提现后余额应不少于200元");
						return ;
					}
					Calendar calendar = Calendar.getInstance();
					int date = calendar.get(Calendar.DAY_OF_MONTH);
					/*if(date < 20 || date > 30) {
						MyToast.showText(TiXianActivity.this, "提现失败，只能在每月的20号到30号进行提现");
						return;
					}*/
					DCUtil.canTiXian(TiXianActivity.this, MySPTool.getUid(TiXianActivity.this), new Handler() {
						@Override
						public void handleMessage(Message msg) {
							super.handleMessage(msg);
							DCUtil.tixian(TiXianActivity.this,MySPTool.getUid(TiXianActivity.this)
									,tixianValue + "" , new Handler() {
										@Override
										public void handleMessage(Message msg) {
											super.handleMessage(msg);
											MyToast.showText(TiXianActivity.this, "提现申请已提交!");
											TiXianActivity.this.finish();
										}
							});
						}
					});
				}else{
					MyToast.showText(TiXianActivity.this, "输入提现金额不能为空");
				}
			}
		});
	}
	
	@Override
	public void findViews() {
		super.findViews();
		tixianTV = (TextView)this.findViewById(R.id.tixianTV);
		submitBtn = (Button)this.findViewById(R.id.submitBtn);
		tixianValueET = (EditText)this.findViewById(R.id.tixianET);
	}
}
