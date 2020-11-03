package com.lw.clouddelivery.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.widget.ArrayListAdapter;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Punish;
import com.lw.clouddelivery.ui.adapter.MessageListAdapter.ViewHolder;
import com.lw.clouddelivery.util.CommTool;

public class JCAdapter extends ArrayListAdapter<Punish> {

	public JCAdapter(Activity context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_punish, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_punish_title);
			holder.date = (TextView) convertView.findViewById(R.id.item_punish_date);
			holder.text = (TextView)convertView.findViewById(R.id.item_punish_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(mList.get(position).getInstruction());
		holder.date.setText(CommTool.date2CNStr(mList.get(position).getRecard_date()));
		holder.text.setText(mList.get(position).getInstruction());
		return convertView;
	}
	
	public final class ViewHolder {
		public TextView text;
		public TextView title;
		public TextView date;
		
	}
}
