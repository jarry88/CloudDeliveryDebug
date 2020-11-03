package com.lw.clouddelivery.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.BaseActivity;
import com.base.util.MyToast;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Help;
import com.lw.clouddelivery.ui.adapter.GridItemAdapter;
import com.lw.clouddelivery.ui.adapter.HelpGridAdapter;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.UIHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class UserManualActivity extends BaseActivity {
	private GridView gridView;
	private ImageView img_gridview;
	private TextView txt_gridviewitem;
	private ImageView gridViewbtn_back;
	
	ArrayList<Help> helpList = new ArrayList<Help>();

	private int type;
	private int[] icon = { R.drawable.sc_03, R.drawable.sc_05,
			R.drawable.sc_07, R.drawable.sc_12, R.drawable.sc_13,
			R.drawable.sc_14, R.drawable.sc_18, R.drawable.sc_19,
			R.drawable.sc_20, R.drawable.sc_24, R.drawable.sc_25 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_user_manual);
		getTitleBar().initTitleText("壹步达会员手册");
		
		gridView = (GridView) findViewById(R.id.gridlayout);
		img_gridview = (ImageView) findViewById(R.id.img_gridview);
		fillData();
	}
	

	@Override
	public void fillData() {
		super.fillData();
		type = getIntent().getIntExtra("type", 1);
		DCUtil.requestHelpList(UserManualActivity.this, pd, type, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				helpList = (ArrayList<Help>) msg.obj;
				HelpGridAdapter mAdapter = new HelpGridAdapter(UserManualActivity.this);
				mAdapter.setList(helpList);
				gridView.setAdapter(mAdapter);// 绑定gridView 适配器
				gridView.setOnItemClickListener(mAdapter);
			}
		});
	}
}
