package com.lw.clouddelivery.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.widget.ArrayListAdapter;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.MyMessage;
import com.lw.clouddelivery.ui.adapter.ShouZhiListAdapter.ViewHolder;
import com.lw.clouddelivery.util.CommTool;

public class MessageListAdapter extends ArrayListAdapter<MyMessage>{

	public MessageListAdapter(Activity context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_message, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_message_title);
			holder.date = (TextView) convertView.findViewById(R.id.item_message_date);
			holder.text = (TextView)convertView.findViewById(R.id.item_message_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(mList.get(position).getXiaoxi());
		holder.date.setText(CommTool.date2CNStr(mList.get(position).getTime()));
		holder.text.setText(mList.get(position).getReason());
		return convertView;
	}
	
	class ViewHolder {
		TextView title,date,text;
	}
}
