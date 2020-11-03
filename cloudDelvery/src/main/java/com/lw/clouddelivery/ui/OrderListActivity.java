package com.lw.clouddelivery.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.BaseActivity;
import com.base.util.BaseSPTool;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Order;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.ui.adapter.OrderListAdapter;
import com.lw.clouddelivery.util.DBUtil;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.MySPTool;
import com.lw.clouddelivery.util.UIHelper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 订单列表界面，“我的订单”，“今日订单”
 * @author leon
 *
 */
public class OrderListActivity extends BaseActivity {
	private ListView orderLV;
	private OrderListAdapter mAdapter;
	public static int ORDERLIST_TYPE_TODAY = 1;
	public static int ORDERLIST_TYPE_MINE = 2;
	private int pageType; //1表示今日订单,2表示我的订单
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			ArrayList<Order> orderList = (ArrayList<Order>) msg.obj;
//			DBUtil.getInstance(OrderListActivity.this).saveOrderList(orderList);
			mAdapter.setList(orderList);
			orderLV.setAdapter(mAdapter);
			orderLV.setOnItemClickListener(mAdapter);
		}
	};
	
	@Override
	protected void onResume() {
		super.onResume();
		if(pageType == ORDERLIST_TYPE_TODAY) {
			DCUtil.requestJROrderList(OrderListActivity.this
					, pd, null, MySPTool.getLon(OrderListActivity.this)
						,  MySPTool.getLat(OrderListActivity.this), handler);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pageType = getIntent().getIntExtra("pageType", ORDERLIST_TYPE_TODAY);
		setContentLayout(R.layout.activity_order_by_today);
		orderLV = (ListView) findViewById(R.id.order_TD_listview);
		mAdapter = new OrderListAdapter(OrderListActivity.this);
		mAdapter.setPageType(pageType);
		
		if(pageType == ORDERLIST_TYPE_TODAY) {
			getTitleBar().initTitleText("壹步达待抢单");
			DCUtil.requestJROrderList(OrderListActivity.this
					, pd, null, MySPTool.getLon(OrderListActivity.this)
						,  MySPTool.getLat(OrderListActivity.this), handler);
		} else {
			getTitleBar().initTitleText("我的订单");
			DCUtil.requestMyOrderList(OrderListActivity.this
					, pd,  MySPTool.getUid(OrderListActivity.this), handler);
		}
	}
}
