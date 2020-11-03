package com.lw.clouddelivery.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.BaseActivity;
import com.base.util.BaseCommTool;
import com.base.util.LogWrapper;
import com.base.util.MyToast;
import com.base.widget.CounttingTextView;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Order;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.util.DBUtil;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.UIHelper;

/**
 * 送单第一步，我已就绪
 * @author leon
 *
 */
public class YS_Step1Activity extends BaseActivity implements OnClickListener {

	private ImageView readyBtn;
	private ImageView additionBtn;
	private ImageView callBtn;
	private LinearLayout checkMapLayout; //查看地图
	private long readyT; //就绪时刻
	private long reachT; //到达时刻
	
	public static  final int SEND_CALL = 2; //打电话
	public static final int SEND_READY = 3; //就绪
	public static final int SEND_SMS = 4;//等待短信
	public static final int RECEIVE_CALL = 5; //壹步达中
	public static final int RECEIVE_READY = 6; //已到达
	public static final int RECEIVE_SMS = 7; //完成

	
	private  int step = -1; //流程步骤
	
	private LinearLayout dialogLayout;
	private String smsCode;
	private Order mOrder; 

	private TextView placeTV;
	private LinearLayout callLayout;
	private TextView ys_peopleTV; //寄件人，收件人
	
	private ImageView xxx_paystate_tv; //已支付，未支付
	private TextView xxx_orderno_tv,xxx_ordername_tv,xxx_payment_tv;
	private ImageView icon_qu_shou; //取，收 图标
	private CounttingTextView counntingTV;
	
	private long startTime;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mOrder = (Order) getIntent().getExtras().getSerializable("order");
		
		setContentLayout(R.layout.activity_songdan_step1);
		getTitleBar().initTitleText("壹步达");
		
		step = DBUtil.getInstance(YS_Step1Activity.this).getOrderStep(mOrder);
		if(step == -1 || step == 0) {
			step = Integer.valueOf(mOrder.getOrderstatusId());
		}
		
		findViews();
		fillData();
	}

	@Override
	public void findViews() {
		super.findViews();
		placeTV = (TextView)this.findViewById(R.id.ys_place);
		checkMapLayout = (LinearLayout) this.findViewById(R.id.checkMapLayout);
		checkMapLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showCheckMap(YS_Step1Activity.this,mOrder);
			}
		});
		additionBtn = (ImageView) findViewById(R.id.additionBtn);
		additionBtn.setOnClickListener(this);
		readyBtn = (ImageView)findViewById(R.id.ys_step1_ready_btn);
		readyBtn.setOnClickListener(this);
		
		callLayout = (LinearLayout)this.findViewById(R.id.callLayout);
		callLayout.setOnClickListener(this);
		LayoutInflater mInflater = LayoutInflater.from(YS_Step1Activity.this);
		dialogLayout = (LinearLayout) mInflater.inflate(R.layout.patch_sms_code, null);
		dialogLayout.findViewById(R.id.sms_code_btn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				smsCode = ((EditText)dialogLayout.findViewById(R.id.sms_code_et)).getText().toString();
			}
		});
		ys_peopleTV = (TextView)this.findViewById(R.id.ys_peopleTV);
		
		xxx_orderno_tv = (TextView)this.findViewById(R.id.xxx_orderno_tv);
		xxx_ordername_tv = (TextView)this.findViewById(R.id.xxx_ordername_tv);
		xxx_payment_tv = (TextView)this.findViewById(R.id.xxx_payment_tv);
		xxx_paystate_tv = (ImageView)this.findViewById(R.id.xxx_paystate_tv);
		
		if(mOrder.getPayState().equals("已支付")) {
			xxx_paystate_tv.setVisibility(View.VISIBLE);
		} else {
			xxx_paystate_tv.setVisibility(View.GONE);
		}
		xxx_orderno_tv.setText("单号：" + mOrder.getOrderno() + "");
		xxx_ordername_tv.setText("物品：" + mOrder.getOrdername());
		xxx_payment_tv.setText("付款方式：" + mOrder.getPaytype());
		
		icon_qu_shou = (ImageView)this.findViewById(R.id.icon_qu_shou);
		
		counntingTV = (CounttingTextView)this.findViewById(R.id.counntingTV);
		startTime = DBUtil.getInstance(YS_Step1Activity.this).getQiangdanTime(mOrder);
		counntingTV.setStartTime(startTime);
		counntingTV.startCount();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == SEND_SMS) {
			step = RECEIVE_CALL;
		} else if(resultCode == RECEIVE_SMS) {

			LogWrapper.e(YS_Step1Activity.class, "完成一笔订单");
			//从数据库中删除该笔订单
			DBUtil.getInstance(YS_Step1Activity.this).deleteOrder(mOrder);
			
			Intent i = new Intent();
			i.setAction(INI.BROADCASE.ORDER);
			sendBroadcast(i);
			finish();
			UIHelper.showMainPage(YS_Step1Activity.this);
		}
	}

	/**
	 * 刷新界面
	 */
	private void refreshUI() {
		if(step == SEND_CALL) {
			additionBtn.setVisibility(View.GONE);
		} else {
			additionBtn.setVisibility(View.VISIBLE);
		}
		
		if(step <RECEIVE_CALL) {
			placeTV.setText(mOrder.getDeli_address());
			ys_peopleTV.setText("寄件人：" + mOrder.getDeli_name());
			icon_qu_shou.setImageResource(R.drawable.zj_03);
		} else {
			placeTV.setText(mOrder.getSaddress());
			ys_peopleTV.setText("收件人：" + mOrder.getSname());
			icon_qu_shou.setImageResource(R.drawable.icon_shou);
		}

		
		switch(step) {
		case SEND_CALL:
			readyBtn.setImageResource(R.drawable.selector_btn_ys_1);
			break;
		case RECEIVE_READY:
		case SEND_READY:
			readyBtn.setImageResource(R.drawable.selector_btn_ys_2);
			break;
		case SEND_SMS:
			readyBtn.setImageResource(R.drawable.selector_btn_ys_3);
			break;
		case RECEIVE_CALL:
			readyBtn.setImageResource(R.drawable.selector_btn_ys_4);
			break;
		case RECEIVE_SMS:
			readyBtn.setImageResource(R.drawable.selector_btn_ys_6);
			break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		refreshUI();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.ys_step1_ready_btn:
			switch(step) {
			case SEND_CALL:
				BaseCommTool.callPhone(YS_Step1Activity.this, mOrder.getDeli_mobile());
				break;
			case SEND_READY:
				sendSMS();
				//记录取件时刻，开始计时
				break;
			case SEND_SMS:
				UIHelper.showConfirmActivity(YS_Step1Activity.this, step,mOrder);
				break;
			case RECEIVE_CALL:
				BaseCommTool.callPhone(YS_Step1Activity.this, mOrder.getSmobile());
				break;
			case RECEIVE_READY:
				sendSMS();
				//记录收件时刻，开始计时
				break;
			case RECEIVE_SMS:
				UIHelper.showConfirmActivity(YS_Step1Activity.this, step,mOrder);
				break;
			}
			if(step != SEND_SMS && step < RECEIVE_SMS) {
				step ++;
				refreshUI();
			} 
			break;
		case R.id.additionBtn:
			AlertDialog.Builder builder = new AlertDialog.Builder(YS_Step1Activity.this);
			builder.setItems(new String[]{"上报事件","重发密码"}, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(which == 0) {
						UIHelper.showFeedback(YS_Step1Activity.this);
					} else {
						sendSMS();
					}
				}
			}).show();
			break;
		case R.id.callLayout:
			if(step < RECEIVE_CALL) { //寄件人电话
				BaseCommTool.callPhone(YS_Step1Activity.this, mOrder.getDeli_mobile());
			} else if(step >= RECEIVE_CALL) {
				BaseCommTool.callPhone(YS_Step1Activity.this, mOrder.getSmobile());
			}
			break;
		}
	}
	

	/**
	 *  调用发送短信验证码接口
	 */
	private void sendSMS() {
		String type = null;
		if(step == SEND_READY) {
			type = "1";
		} else if(step == RECEIVE_READY) {
			type = "2";
		}
		DCUtil.sendSMS(YS_Step1Activity.this, mOrder.getId() + "", type,"1",new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(step == YS_Step1Activity.SEND_READY) { //第五步，致电收件方
					step = SEND_SMS;
				} 
//				else if(step == YS_Step1Activity.RECEIVE_SMS) { //已完成订单，删除记录
//					DBUtil.getInstance(YS_Step1Activity.this).deleteOrder(mOrder);
//				}
				MyToast.showText(YS_Step1Activity.this, "验证码发送成功!");
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		counntingTV.stopCount();
		
		//保存订单当前步骤
		mOrder.setStep(step);
		if(startTime == -1) {
			startTime = System.currentTimeMillis();
		}
		mOrder.setQiangdanTime(startTime);
		
		DBUtil.getInstance(YS_Step1Activity.this).saveOrder(mOrder);
	}
}
