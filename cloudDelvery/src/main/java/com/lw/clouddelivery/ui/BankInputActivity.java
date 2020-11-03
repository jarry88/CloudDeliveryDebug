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
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.MySPTool;

/**
 *   填写银行信息界面
 * @author leon
 *
 */
public class BankInputActivity extends BaseActivity {

	public static String NAME = "name"; //持卡人姓名
	public static String CARD_NO = "cardno"; //银行卡卡号
	public static String BANK_TYPE = "banktype"; //开户行
	public static String BANK_NAME = "bankname"; //开户行名称
	public Button submitBtn; //提交按钮
	private EditText bankET,bankNameET,nameET,cardNoET;
	
	private String name,cardno,bank,bankname;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_input_bank);
		getTitleBar().initTitleText("修改银行卡信息");
		
		nameET = (EditText)this.findViewById(R.id.name);
		bankNameET = (EditText)this.findViewById(R.id.bankinput_bankname);
		bankET = (EditText)this.findViewById(R.id.bankinput_bank);
		cardNoET = (EditText)this.findViewById(R.id.bankinput_cardnum);
		
		nameET.setText(MySPTool.getString(BankInputActivity.this, INI.SP.CARD_NAME, ""));
		bankNameET.setText(MySPTool.getString(this, INI.SP.BANK_NAME, ""));
		bankET.setText(MySPTool.getString(this, INI.SP.BANK, ""));
		cardNoET.setText(MySPTool.getString(this, INI.SP.CARD_NO, ""));
				
		submitBtn = (Button)this.findViewById(R.id.resetpwd_btn);
		submitBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				name = nameET.getText().toString().trim();
				cardno = cardNoET.getText().toString().trim();
				bank = bankET.getText().toString().trim();
				bankname = bankNameET.getText().toString().trim();
				
				DCUtil.postBankInfo(BankInputActivity.this, pd, name, cardno, bank, bankname, new Handler() {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						MyToast.showText(BankInputActivity.this, "修改成功!");
						
						//保存银行卡信息
						MySPTool.putString(BankInputActivity.this, INI.SP.CARD_NO, cardno);
						MySPTool.putString(BankInputActivity.this, INI.SP.BANK, bank);
						MySPTool.putString(BankInputActivity.this, INI.SP.BANK_NAME, bankname);
						
						BankInputActivity.this.finish();
					}
				});
			}
		});
	}
	
	
}
