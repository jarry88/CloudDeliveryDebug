package com.lw.clouddelivery.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.base.BaseActivity;
import com.base.listeners.Base_OnLoadingMoreListener;
import com.base.util.MyToast;
import com.base.widget.RTPullListView;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.ShouZhiItem;
import com.lw.clouddelivery.ui.adapter.ShouZhiListAdapter;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.MySPTool;

/**
 * 收支明细列表界面
 * @author leon
 *
 */
public class ShouZhiListActivity extends BaseActivity {

	private RTPullListView detailListLV;
	private int page = 0;
	private boolean isAllLoad = false;
	
	private ShouZhiListAdapter mAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.base_activity_listview);
		getTitleBar().initTitleText("收支明细");
		
		detailListLV = (RTPullListView)this.findViewById(R.id.order_detailList);
		detailListLV.setDividerHeight(1);
		detailListLV.setOnLoadMoreListener(new Base_OnLoadingMoreListener() {
			@Override
			public void OnLoadingMore() {
				page ++;
				fillData();
			}
		});
		fillData();
	}
	@Override
	public void fillData() {
		super.fillData();
		if(!isAllLoad) {
			DCUtil.requestShouZhiList(ShouZhiListActivity.this, pd, MySPTool.getUid(ShouZhiListActivity.this), page, new Handler(){
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					if(msg.arg1 == 1) {
						ArrayList<ShouZhiItem> shouZhiList = (ArrayList<ShouZhiItem>) msg.obj;
						if(page == 0) { //第一页
							mAdapter = new ShouZhiListAdapter(ShouZhiListActivity.this);
							mAdapter.setList(shouZhiList);
							detailListLV.setAdapter(mAdapter);
						} else {
							mAdapter.getList().addAll(shouZhiList);
							mAdapter.notifyDataSetChanged();
						}
						page ++ ;
					} else {
						isAllLoad = true;
						MyToast.showText(ShouZhiListActivity.this, "已加载全部数据");
					}
				}
			});
		} 
	}
}
