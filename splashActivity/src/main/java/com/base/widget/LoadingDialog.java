package com.base.widget;

import com.base.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 自定义加载中dialog
 */
public class LoadingDialog extends Dialog {

	private Handler handler = new Handler();
	private ImageView iv;
	private AnimationDrawable ad;

	private TextView msg_tv;
	private String msg = null;
	public Context mContext;

	public LoadingDialog(Context context) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		mContext = context;
	}

	public LoadingDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mContext = context;
	}

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_loadingdialog);
		iv = (ImageView) findViewById(R.id.loadingImageView);
		msg_tv = (TextView) findViewById(R.id.id_tv_loadingmsg);
		ad = (AnimationDrawable) iv.getBackground();
	}

	@Override
	public void show() {
		super.show();
		if (msg == null) {
			msg = "数据加载中";
		}
		msg_tv.setText(msg);
		handler.post(new Runnable() {
			@Override
			public void run() {
				ad.start();
			}
		});
	}

	@Override
	public void dismiss() {
		super.dismiss();
		if (ad != null) {
			ad.stop();
		}
	}

	public LoadingDialog setDialogText(String msg) {
		this.msg = msg;
		return this;
	}
}
