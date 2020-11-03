package com.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;

import com.base.listeners.IBaseActivity;
import com.base.util.BaseCommTool;
import com.base.widget.LoadingDialog;
import com.base.widget.TitleBar;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends Activity implements IBaseActivity {

	private LinearLayout rl_base_container;
	// 内容区域的布局
	private View contentView;
		
	TitleBar tb_base;
	
	protected LoadingDialog pd; //用于加载各页面时使用
	
	protected int scW,scH;
	
	public View getContentView() {
		return contentView;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_base);

		tb_base = (TitleBar) findViewById(R.id.tb_base);
		
		rl_base_container = (LinearLayout) findViewById(R.id.rl_base_container);
		pd = new LoadingDialog(BaseActivity.this);
		
		BaseApp.getInstance().add(this);
		
		scW = BaseCommTool.getScreenWidth(this);
		scH = BaseCommTool.getScreenHeight(this);
	}

	/***
	 * 设置内容区域
	 * 
	 * @param resId
	 *            资源文件ID
	 */
	public void setContentLayout(int resId) {

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contentView = inflater.inflate(resId, null);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		contentView.setLayoutParams(layoutParams);
//		contentView.setBackgroundDrawable(null);
		if (null != rl_base_container) {
			rl_base_container.addView(contentView);
		}
	}

	/***
	 * 设置内容区域
	 * 
	 * @param view
	 *            View对象
	 */
	public void setContentLayout(View view) {
		if (null != rl_base_container) {
			rl_base_container.addView(view);
		}
	}

	/**
	 * 得到内容的View
	 * 
	 * @return
	 */
	public View getLyContentView() {

		return contentView;
	}

	public TitleBar getTitleBar() {
		return tb_base;
	}

	@Override
	protected void onDestroy() {
		if (null != pd) {
			pd.dismiss();
			pd = null;
		}
		BaseApp.getInstance().remove(this);
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	    MobclickAgent.onResume(this);          //统计时长
	}

	@Override
	protected void onResume() {
		super.onResume();
		    MobclickAgent.onPause(this);
	}

	public void pdDismiss() {

		if (null != pd) {
			pd.dismiss();
		}
	}

	/**
	 * 设置弹出进度条的文字描述
	 * 
	 * @param text
	 */
	public void setDialogText(String text) {
		pd.setDialogText(text);
	}

	@Override
	public void fillData() {
	}

	@Override
	public void findViews() {
	}
}
