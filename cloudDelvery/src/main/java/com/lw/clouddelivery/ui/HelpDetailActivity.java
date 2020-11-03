package com.lw.clouddelivery.ui;

import org.jivesoftware.smack.util.StringUtils;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.base.BaseActivity;
import com.base.util.StringUtil;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.util.HtmlRegexpUtil;

/**
 *  更多，跳转详情界面
 */
public class HelpDetailActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String title = getIntent().getStringExtra("title");
		String content = getIntent().getStringExtra("content");
		setContentLayout(R.layout.activity_helpdetail);
		getTitleBar().initTitleText(title);
		
		TextView contentTV = (TextView)this.findViewById(R.id.helpdetail_contentTV);
//		contentTV.setText(HtmlRegexpUtil.filterHtml(content));
		if(content != null && !"".equals(content))
			contentTV.setText(Html.fromHtml(content));
	}
}
