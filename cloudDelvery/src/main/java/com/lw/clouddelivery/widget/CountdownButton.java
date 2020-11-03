package com.lw.clouddelivery.widget;

import com.lw.clouddelivery.R;
import com.lw.clouddelivery.conf.INI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class CountdownButton  {

	private int state; //待抢单，抢单中，已取消，已完成
	private Button button;
	
	
	public CountdownButton(Context c) {
		button = new Button(c);
		button.setBackgroundResource(R.drawable.selector_btn_qiangdan);
	}
	public void refreshState(int state) {
	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Button getButton() {
		return button;
	}
	public void setButton(Button button) {
		this.button = button;
	}
}
