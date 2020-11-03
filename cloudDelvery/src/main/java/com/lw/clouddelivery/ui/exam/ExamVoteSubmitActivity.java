package com.lw.clouddelivery.ui.exam;

import java.util.ArrayList;
import java.util.List;






import com.base.BaseActivity;
import com.lw.clouddelivery.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ExamVoteSubmitActivity extends BaseActivity {

	VoteSubmitViewPager viewPager;
	VoteSubmitAdapter pagerAdapter;
	List<View> viewItems = new ArrayList<View>();
	ArrayList<VoteSubmitItem> dataItems = new ArrayList<VoteSubmitItem>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote_submit);
//		getTitleBar().initTitleText("在线考试");
		TextView titletv=(TextView) findViewById(R.id.vote_submit_tabbar_title);
		titletv.setText("在线考试");
		ImageView backimg=(ImageView) findViewById(R.id.titlebackimg);
		backimg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		init();
	}

	/**
	 * 页面初始化
	 */
	private void init() {
		for (int i = 0; i < DataLoader.voteQuestion.length; i++) {
			viewItems.add(getLayoutInflater().inflate(R.layout.vote_submit_viewpager_item, null));
		}
		dataItems = new DataLoader().getTestData();
		viewPager = (VoteSubmitViewPager) findViewById(R.id.vote_submit_viewpager);
		pagerAdapter = new VoteSubmitAdapter(this, viewItems, dataItems,pd);
		viewPager.setAdapter(pagerAdapter);
		viewPager.getParent().requestDisallowInterceptTouchEvent(false);
	}

	/**
	 * @param index
	 *            根据索引值切换页面
	 */
	public void setCurrentView(int index) {
		viewPager.setCurrentItem(index);
	}
}
