package com.base.widget;


import com.base.R;
import com.base.conf.BaseINI;
import com.base.util.BaseCommTool;
import com.base.util.LogWrapper;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 公共标题栏组件
 * @author Leon.w
 *
 */
public class TitleBar extends RelativeLayout {
	private ImageView bt_left;
	private TextView tv_title,tv_left,tv_right;
	private ImageView bt_right;
	private Context mContext;
	private RelativeLayout rlBack;
	

	public TitleBar(Context context) {
		super(context);
		mContext = context;
		init();

	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();

	}

	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public void init() {
		LogWrapper.e(TitleBar.class, "--init()--");
		//设置高度
		int scW = BaseCommTool.getScreenWidth(mContext);
		LayoutInflater.from(mContext).inflate(R.layout.widget_titlebar, this, true);
		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,(int)(scW * BaseINI.titlebarH)));
		bt_left = (ImageView) findViewById(R.id.titlebar_leftbtn);
		tv_title = (TextView) findViewById(R.id.titlebar_titletv);
		bt_right = (ImageView) findViewById(R.id.titlebar_rightbtn);
		rlBack = (RelativeLayout) findViewById(R.id.rlBack);
		tv_left = (TextView)findViewById(R.id.titlebar_leftTv);
		tv_right = (TextView)findViewById(R.id.titlebar_rightTv);
		
		initIBLeft(R.drawable.arrows_03, new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((Activity)mContext).finish();
			}
		});
	}

	public void initIBLeft(int resId, OnClickListener onClickListener) {
		bt_left.setBackgroundResource(resId);
		bt_left.setVisibility(View.VISIBLE);
		rlBack.setOnClickListener(onClickListener);
	}

	public void initTitleText(CharSequence charSequence) {
		tv_title.setText(charSequence);
	}

	public void initTitleText(int textResId) {

		tv_title.setText(textResId);
	}
	
	public void initTitleText(int textResId,OnClickListener onClickListener) {
		tv_title.setText(textResId);
		tv_title.setOnClickListener(onClickListener);
	}

	public void initIBRight(int resId, OnClickListener onClickListener) {
//		bt_right.setImageResource(resId);
		bt_right.setBackgroundResource(resId);
		bt_right.setVisibility(View.VISIBLE);
		bt_right.setOnClickListener(onClickListener);
	}
	public void initTVRight(int resId, OnClickListener onClickListener) {
		tv_right.setText(resId);
		tv_right.setVisibility(View.VISIBLE);
		tv_right.setOnClickListener(onClickListener);
	}
	
	public void setTVRight(int resId) {
		tv_right.setText(resId);
		tv_right.setVisibility(View.VISIBLE);
	}
	public void setTVLeft(String text) {
		tv_left.setText(text);
		tv_left.setVisibility(View.VISIBLE);
	}
	
	public void initTVLeft(int resId, OnClickListener onClickListener) {
		tv_left.setText(resId);
		tv_left.setVisibility(View.VISIBLE);
		if(onClickListener != null) {
			tv_left.setOnClickListener(onClickListener);
		}
	}
	
	public void setIBRight(int resId) {
		bt_right.setBackgroundResource(resId);
	}
	
	
	public String getTitleStr() {
		return tv_title.getText().toString().trim();
	}
	
	public void initTitleIB(int resID,OnClickListener clickListener) {
		ImageButton ib = (ImageButton) findViewById(R.id.titlebar_titleIB);
		ib.setVisibility(View.VISIBLE);
		ib.setBackgroundResource(resID);
		ib.setOnClickListener(clickListener);
		tv_title.setVisibility(View.GONE);
	}
	
	public ImageView getIBRight() {
		return bt_right;
	}
}
