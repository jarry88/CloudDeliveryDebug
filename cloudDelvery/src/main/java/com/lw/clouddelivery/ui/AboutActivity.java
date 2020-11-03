package com.lw.clouddelivery.ui;

import com.base.BaseActivity;
import com.lw.clouddelivery.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class AboutActivity extends BaseActivity implements OnClickListener {
	private TextView txt_tel;
	private ImageView telIV;
	private RelativeLayout about_layout_1;
	private TextView pagename;
	private String version_name = "壹步达会员";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_about);
		getTitleBar().initTitleText("关于");
		initview();
		pagename.setText(getVersion());
	}

	/**
	 * 控件初始化
	 */
	private void initview() {
		pagename = (TextView) findViewById(R.id.pagename);

		txt_tel = (TextView) findViewById(R.id.txt_tel);
		telIV = (ImageView) findViewById(R.id.img_tel);
		txt_tel.setOnClickListener(this);
		telIV.setOnClickListener(this);
		about_layout_1 = (RelativeLayout) this
				.findViewById(R.id.about_layout_1);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.topMargin = (int) (scH * 0.24);
		about_layout_1.setLayoutParams(params);
	}

	/*
	 * 按键监听
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 点击拨打电话
		case R.id.txt_tel:
		case R.id.img_tel:
			// String telNum = txt_tel.getText().toString();
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ txt_tel.getText().toString()));
			startActivity(intent);
			break;
		}
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public String getVersion() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			return version_name + version;
		} catch (Exception e) {
			e.printStackTrace();
			return version_name;
		}
	}
}
