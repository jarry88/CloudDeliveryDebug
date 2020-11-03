package com.lw.clouddelivery.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.base.BaseActivity;
import com.base.R;
import com.base.listeners.Base_OnLoadingMoreListener;
import com.base.util.MyToast;
import com.base.widget.RTPullListView;
import com.lw.clouddelivery.bean.MyMessage;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.ui.adapter.MessageListAdapter;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.MySPTool;

/**
 * 消息中心界面
 * @author leon
 *
 */
public class MessageListActivity extends BaseActivity {

	private int pageType = 1; //页面类型，1重要通知，2表示消息3一般通知
	private RTPullListView rtListView;
	private int page = 1;
	private boolean isAllLoad = false;
	private MessageListAdapter mAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.base_activity_listview);
		
//		pageType = getIntent().getIntExtra("pageType", 1);
		pageType = Integer.parseInt(getIntent().getStringExtra("pageType"));
		Log.v("messagelistactivity40", "pageType+"+pageType);
		if(pageType == 1) {
			getTitleBar().initTitleText("通知");
		} else {
			getTitleBar().initTitleText("消息");
		}
		
		rtListView = (RTPullListView)this.findViewById(R.id.order_detailList);
		rtListView.setDividerHeight(1);
		rtListView.setOnLoadMoreListener(new Base_OnLoadingMoreListener() {
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
			if(pageType==2){
			DCUtil.requestMessageList(MessageListActivity.this, pd, page,MySPTool.getUid(MessageListActivity.this), pageType, new Handler(){
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					if(msg.arg1 == 1) {
						ArrayList<MyMessage> messageList = (ArrayList<MyMessage>) msg.obj;
						if(page == 1) { //第一页
							mAdapter = new MessageListAdapter(MessageListActivity.this);
							mAdapter.setList(messageList);
							rtListView.setAdapter(mAdapter);
							if(pageType == 1) { //记录最新一条的通知时刻
								MySPTool.putLong(MessageListActivity.this, INI.SP.MESSAGE_LAST_TIME, messageList.get(0).getTime());
							}
						} else {
							mAdapter.getList().addAll(messageList);
							mAdapter.notifyDataSetChanged();
						}
					} else {
						isAllLoad = true;
						MyToast.showText(MessageListActivity.this, "已加载全部数据");
					}
				}
			});
			}else{
				DCUtil.requestMessageList(MessageListActivity.this, pd, page,"", pageType, new Handler(){
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						if(msg.arg1 == 1) {
							ArrayList<MyMessage> messageList = (ArrayList<MyMessage>) msg.obj;
							if(page == 1) { //第一页
								mAdapter = new MessageListAdapter(MessageListActivity.this);
								mAdapter.setList(messageList);
								rtListView.setAdapter(mAdapter);
								if(pageType == 1) { //记录最新一条的通知时刻
									MySPTool.putLong(MessageListActivity.this, INI.SP.MESSAGE_LAST_TIME, messageList.get(0).getTime());
								}
							} else {
								mAdapter.getList().addAll(messageList);
								mAdapter.notifyDataSetChanged();
							}
						} else {
							isAllLoad = true;
							MyToast.showText(MessageListActivity.this, "已加载全部数据");
						}
					}
				});
			}
		} 
	}
}
