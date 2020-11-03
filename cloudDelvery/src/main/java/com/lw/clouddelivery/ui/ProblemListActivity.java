package com.lw.clouddelivery.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.base.BaseActivity;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Problem;
import com.lw.clouddelivery.ui.adapter.ProblemListAdapter;
import com.lw.clouddelivery.util.DCUtil;

/**
 * 问题列表界面
 * @author leon
 *
 */
public class ProblemListActivity extends BaseActivity {
	private ListView problemLV; //常见问题
	private ProblemListAdapter madapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getTitleBar().initTitleText("常见问题");
		setContentLayout(R.layout.activity_problemlist);
		findViews();
		fillData();
		
		madapter = new ProblemListAdapter(ProblemListActivity.this);
	}

	@Override
	public void fillData() {
		super.fillData();
		DCUtil.requestProblemList(ProblemListActivity.this, pd, new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				ArrayList<Problem> problemList = (ArrayList<Problem>) msg.obj;
				madapter.setList(problemList);
				problemLV.setAdapter(madapter);
			}
		});
	}

	@Override
	public void findViews() {
		super.findViews();
		problemLV = (ListView)this.findViewById(R.id.problem_lv);
		
	}
}
