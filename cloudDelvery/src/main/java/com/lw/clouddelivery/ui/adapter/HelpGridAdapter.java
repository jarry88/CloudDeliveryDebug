package com.lw.clouddelivery.ui.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.base.widget.ArrayListAdapter;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Help;
import com.lw.clouddelivery.ui.adapter.GridItemAdapter.Gridviews;
import com.lw.clouddelivery.util.UIHelper;

public class HelpGridAdapter extends ArrayListAdapter<Help> implements OnItemClickListener {
	private LayoutInflater layoutInflater;
	private int[] icon = { R.drawable.sc_03, R.drawable.sc_05,
			R.drawable.sc_07, R.drawable.sc_12, R.drawable.sc_13,
			R.drawable.sc_14, R.drawable.sc_18, R.drawable.sc_19,
			R.drawable.sc_20, R.drawable.sc_24, R.drawable.sc_25 };
	private String[] sIcon = { "服务流程", "违约管理",
			"岗前准备", "风险规避", "事故处理",
			"手机调试", "处罚标准", "合作协议",
			"行为规范", "奖励政策", "安全策略" };
	
	public HelpGridAdapter(Activity context) {
		super(context);
		this.layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Gridviews views = null;
		if (convertView == null) {
			views = new Gridviews();
			convertView = layoutInflater.inflate(R.layout.gridviewitem, null);
			views.imageView = (ImageView) convertView
					.findViewById(R.id.img_gridview);
			convertView.setTag(views);
		} else {
			views = (Gridviews) convertView.getTag();
		}
		views.imageView.setImageResource(icon[position]);
		return convertView;
	}
	public final class Gridviews {
		public ImageView imageView;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		for (Help help : mList) {
			Log.e("ccqx", "title:"+help.getTitle());
			if(sIcon.length > position && sIcon[position].equals(help.getTitle())){
				UIHelper.showHelpDetail(mContext, help.getTitle(), help.getContent());
				return;
			}
		}
		UIHelper.showHelpDetail(mContext, sIcon[position], "");
	}
}
