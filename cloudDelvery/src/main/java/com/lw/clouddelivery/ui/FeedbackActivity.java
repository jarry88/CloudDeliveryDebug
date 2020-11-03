package com.lw.clouddelivery.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.base.BaseActivity;
import com.base.util.MyToast;
import com.base.util.BaseSPTool;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.util.DCUtil;

public class FeedbackActivity extends BaseActivity {

	private EditText contentET;
	private Button commitBtn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_feedback);
		contentET = (EditText)this.findViewById(R.id.feedback_et);
		commitBtn = (Button)this.findViewById(R.id.feedback_btn);
		
		commitBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!TextUtils.isEmpty(contentET.getText())) {
					DCUtil.commitFeedback(FeedbackActivity.this,pd,BaseSPTool.getString(FeedbackActivity.this, INI.SP.PHONE, "")
							,"意见反馈",contentET.getText().toString(),1,new Handler() {
								@Override
								public void handleMessage(Message msg) {
									super.handleMessage(msg);
									MyToast.showText(FeedbackActivity.this,"提交成功!");
									FeedbackActivity.this.finish();
								}
					});
				}
			}
		});
	}
}
