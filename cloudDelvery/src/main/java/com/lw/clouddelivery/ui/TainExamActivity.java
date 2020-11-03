package com.lw.clouddelivery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.base.BaseActivity;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.ui.exam.ExamVoteSubmitActivity;
import com.lw.clouddelivery.ui.train.TrainVoteSubmitActivity;

public class TainExamActivity extends BaseActivity{
	
	private Button traindata,startexam;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_trainexam);
		getTitleBar().initTitleText("在线考试");
		
		findViews();
		//培训资料
		traindata.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentauth =new Intent();
				intentauth.setClass(TainExamActivity.this, TrainVoteSubmitActivity.class);
				startActivity(intentauth);
			}
		});
		//开始考试
		startexam.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentauth =new Intent();
				intentauth.setClass(TainExamActivity.this, ExamVoteSubmitActivity.class);
				startActivity(intentauth);
			}
		});
	}
	@Override
	public void findViews() {
		super.findViews();
		
		traindata=(Button) findViewById(R.id.traindata);
		startexam=(Button) findViewById(R.id.startexam);
		
	}

}
