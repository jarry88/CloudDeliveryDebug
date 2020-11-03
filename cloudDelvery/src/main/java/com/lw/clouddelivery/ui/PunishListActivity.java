package com.lw.clouddelivery.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.base.BaseActivity;
import com.base.R;
import com.base.listeners.Base_OnLoadingMoreListener;
import com.base.util.MyToast;
import com.base.widget.RTPullListView;
import com.lw.clouddelivery.bean.MyMessage;
import com.lw.clouddelivery.bean.Punish;
import com.lw.clouddelivery.ui.adapter.JCAdapter;
import com.lw.clouddelivery.ui.adapter.MessageListAdapter;
import com.lw.clouddelivery.util.DCUtil;

/**
 * 奖惩记录
 * @author leon
 *
 */
public class PunishListActivity extends BaseActivity implements OnClickListener {

	private TextView titleTV1,titleTV2,lineTV1,lineTV2;
	private RTPullListView  lv1,lv2;
	private int page1 = 0;
	private boolean isAllLoad1 = false;
	private int page2 = 0;
	private boolean isAllLoad2 = false;
	
	private JCAdapter adapter1,adapter2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.base_activity_tab_listview);
		getTitleBar().initTitleText("奖惩记录");
		findViews();
	}
	@Override
	public void findViews() {
		super.findViews();
		titleTV1 = (TextView)this.findViewById(R.id.base_tabactivityitem1);
		titleTV2 = (TextView)this.findViewById(R.id.base_tabactivityitem2);
		lineTV1 = (TextView)this.findViewById(R.id.base_tabactivityline_1);
		lineTV2 = (TextView)this.findViewById(R.id.base_tabactivityline_2);
		lv1 = (RTPullListView)this.findViewById(R.id.base_tabactivitylv_1);
		lv2 = (RTPullListView)this.findViewById(R.id.base_tabactivitylv_2);
		
		lv1.setDividerHeight(1);
		lv2.setDividerHeight(1);
		
		titleTV1.setOnClickListener(this);
		titleTV2.setOnClickListener(this);
		fillLV1();
		fillLV2();
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.base_tabactivityitem1:
			titleTV1.setTextColor(getResources().getColor(R.color.base_titlebar_bg));
			titleTV2.setTextColor(getResources().getColor(R.color.black));
			lineTV1.setVisibility(View.VISIBLE);
			lineTV2.setVisibility(View.INVISIBLE);
			lv1.setVisibility(View.VISIBLE);
			lv2.setVisibility(View.GONE);

			lv1.setOnLoadMoreListener(new Base_OnLoadingMoreListener() {
				@Override
				public void OnLoadingMore() {
					page1 ++;
					fillLV1();
				}
			});
			break;
		case R.id.base_tabactivityitem2:
			titleTV1.setTextColor(getResources().getColor(R.color.black));
			titleTV2.setTextColor(getResources().getColor(R.color.base_titlebar_bg));
			lineTV1.setVisibility(View.INVISIBLE);
			lineTV2.setVisibility(View.VISIBLE);
			lv1.setVisibility(View.GONE);
			lv2.setVisibility(View.VISIBLE);
			lv2.setOnLoadMoreListener(new Base_OnLoadingMoreListener() {
				@Override
				public void OnLoadingMore() {
					page2 ++;
					fillLV2();
				}
			});
			break;
		}
	}
	
	/**
	 * 请求分页数据
	 * @param lv
	 * @param page
	 * @param pageType
	 * @param isAllLoad
	 */
	public void fillLV1() {
		if(!isAllLoad1) {
			DCUtil.requestPunishList(PunishListActivity.this, pd, page1, "A005", new Handler(){
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					if(msg.arg1 == 1) {
						ArrayList<Punish> punishList = (ArrayList<Punish>) msg.obj;
						if(page1 == 0) { //第一页
							adapter1 = new JCAdapter(PunishListActivity.this);
							adapter1.setList(punishList);
							lv1.setAdapter(adapter1);
						} else {
							adapter1.getList().addAll(punishList);
							adapter1.notifyDataSetChanged();
						}
					} else {
						isAllLoad1 = true;
						MyToast.showText(PunishListActivity.this, "已加载全部数据");
					}
				}
			});
		} 
	}
	
	public void fillLV2() {
		if(!isAllLoad2) {
			DCUtil.requestPunishList(PunishListActivity.this, pd, page2, "A006", new Handler(){
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					if(msg.arg1 == 1) {
						ArrayList<Punish> punishList = (ArrayList<Punish>) msg.obj;
						if(page2 == 0) { //第一页
							adapter2 = new JCAdapter(PunishListActivity.this);
							adapter2.setList(punishList);
							lv2.setAdapter(adapter2);
						} else {
							adapter2.getList().addAll(punishList);
							adapter2.notifyDataSetChanged();
						}
					} else {
						isAllLoad2 = true;
						MyToast.showText(PunishListActivity.this, "已加载全部数据");
					}
				}
			});
		} 
	}
}
