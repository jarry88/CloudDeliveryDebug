package com.lw.clouddelivery.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.base.BaseActivity;
import com.base.BaseApp;
import com.lw.clouddelivery.CloudDeliveryAPP;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.MyIncoming;
import com.lw.clouddelivery.bean.User;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.MySPTool;
import com.lw.clouddelivery.util.UIHelper;

/**
 * "我的壹步达"界面
 * @author leon
 *
 */
public class MyYunSongActivity extends BaseActivity implements OnClickListener {

	private TextView totalMoneyTV,kyMoneyTV,yjMoneyTV;
	private Button logoutBtn;
	private TextView user_phoneTV,user_nameTV;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_myyunsong);
		getTitleBar().initTitleText("我的壹步达");
		
		findViews();
	}

	@Override
	public void findViews() {
		super.findViews();
		findViewById(R.id.myyunsong_item_1).setOnClickListener(this);
		findViewById(R.id.myyunsong_item_2).setOnClickListener(this);
		findViewById(R.id.myyunsong_item_3).setOnClickListener(this);
		findViewById(R.id.myyunsong_item_4).setOnClickListener(this);
		
		totalMoneyTV = (TextView)this.findViewById(R.id.totalMoneyTV);
		kyMoneyTV = (TextView)this.findViewById(R.id.montyMoneyTV);
		yjMoneyTV = (TextView)this.findViewById(R.id.zuoriMoneyTV);
		logoutBtn = (Button)this.findViewById(R.id.logoutBtn);
		logoutBtn.setOnClickListener(this);
		
		user_phoneTV = (TextView)this.findViewById(R.id.user_phoneTV);
		user_nameTV= (TextView)this.findViewById(R.id.user_nameTV);
		fillData();
	}

	@Override
	public void fillData() {
		super.fillData();
		user_phoneTV.setText(MySPTool.getString(MyYunSongActivity.this, INI.SP.PHONE, ""));
		user_nameTV.setText(MySPTool.getString(MyYunSongActivity.this, INI.SP.UNAME, ""));
		DCUtil.getMyIncoming(MyYunSongActivity.this, MySPTool.getUid(this), new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				MyIncoming incoming = (MyIncoming) msg.obj;
				totalMoneyTV.setText(incoming.getCountmoney() + "");
				kyMoneyTV.setText(incoming.getKymoney() + "");
				yjMoneyTV.setText(incoming.getYjmoney() + "");
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.myyunsong_item_1:
			UIHelper.showOrderList(MyYunSongActivity.this,2);
			break;
		case R.id.myyunsong_item_2:
			UIHelper.showCashAccount(MyYunSongActivity.this);
			break;
		case R.id.myyunsong_item_3:
			UIHelper.showPunishPage(MyYunSongActivity.this);
			break;
		case R.id.myyunsong_item_4:
			break;
		case R.id.logoutBtn:
			UIHelper.logout(this);
			break;
		}
	}
}
