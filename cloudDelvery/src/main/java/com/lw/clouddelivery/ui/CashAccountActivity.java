package com.lw.clouddelivery.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.BaseActivity;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.CashAccount;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.MySPTool;
import com.lw.clouddelivery.util.UIHelper;

public class CashAccountActivity extends BaseActivity {

	private TextView accountTV,useableTV; 
	private Button detailBtn,showTiXianBtn;
	private Button rechargebtn;
	private LinearLayout bankinfo_layout;
	CashAccount cashAccount;
	private TextView bankTV,bankNameTV,cardNoTV;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_cashaccount);
		getTitleBar().initTitleText("现金账户");
		
		findViews();
		fillData();
	}

	@Override
	public void fillData() {
		super.fillData();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		DCUtil.requestCashAccount(CashAccountActivity.this, pd, MySPTool.getUid(this), new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				cashAccount = (CashAccount) msg.obj;
				accountTV.setText(cashAccount.getYjPrice() +  "元");
				useableTV.setText(cashAccount.getKyPrice() + "元");
				showTiXianBtn.setEnabled(true);
			}
		});
		bankTV.setText(MySPTool.getString(CashAccountActivity.this, INI.SP.BANK, ""));
		bankNameTV.setText(MySPTool.getString(CashAccountActivity.this, INI.SP.BANK_NAME, ""));
		cardNoTV.setText(MySPTool.getString(CashAccountActivity.this, INI.SP.CARD_NO, ""));
	}

	@Override
	public void findViews() {
		super.findViews();
		accountTV = (TextView)this.findViewById(R.id.cashaccount_yuer);
		useableTV = (TextView)this.findViewById(R.id.cashaccount_keyong);
		detailBtn = (Button)this.findViewById(R.id.cashaccount_detailbtn);
		showTiXianBtn = (Button)this.findViewById(R.id.showTiXianBtn);
		
		rechargebtn = (Button)this.findViewById(R.id.cashaccount_rechargebtn);
		
		bankinfo_layout = (LinearLayout)this.findViewById(R.id.bankinfo_layout);

		bankTV = (TextView)this.findViewById(R.id.bank);
		bankNameTV = (TextView)this.findViewById(R.id.bankname);
		cardNoTV = (TextView)this.findViewById(R.id.card_no);
		
		
		bankinfo_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showBankInput(CashAccountActivity.this);
			}
		});
		
		
		detailBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showShouZhilList(CashAccountActivity.this);
			}
		});
		showTiXianBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					UIHelper.showTiXianPage(CashAccountActivity.this, cashAccount.getKyPrice());
			}
		});
		rechargebtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showChongZhiList(CashAccountActivity.this);
			}
		});
	}
}
