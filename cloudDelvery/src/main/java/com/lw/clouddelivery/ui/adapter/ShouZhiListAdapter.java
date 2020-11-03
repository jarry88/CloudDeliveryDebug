package com.lw.clouddelivery.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.widget.ArrayListAdapter;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.ShouZhiItem;
import com.lw.clouddelivery.ui.ShouZhiListActivity;
import com.lw.clouddelivery.util.CommTool;

public class ShouZhiListAdapter extends ArrayListAdapter<ShouZhiItem> {

	public ShouZhiListAdapter(Activity context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_shouzhi, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_shouzhi_text);
			holder.date = (TextView) convertView.findViewById(R.id.item_shouzhi_date);
			holder.num = (TextView)convertView.findViewById(R.id.item_shouzhi_num);
			convertView.setTag(holder);                                                                     
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(mList.get(position).getInstruction());
		holder.date.setText(CommTool.date2CNStr((long)mList.get(position).getRecard_date()));
		if(mList.get(position).getIncome() != 0) {
			holder.num.setText(mList.get(position).getIncome() + "");
			holder.num.setTextColor(mContext.getResources().getColor(R.color.base_titlebar_bg));
		} else {
			holder.num.setText(mList.get(position).getSpending() + "");
			holder.num.setTextColor(0xfffc206d);
		}

		return convertView;
	}
	class ViewHolder {
		TextView title,date,num;
	}
}
