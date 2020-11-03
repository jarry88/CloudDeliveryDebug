package com.lw.clouddelivery.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.widget.ArrayListAdapter;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Problem;

public class ProblemListAdapter extends ArrayListAdapter<Problem> {

	public ProblemListAdapter(Activity context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_problem, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_problem_title);
			holder.date = (TextView) convertView.findViewById(R.id.item_problem_date);
			holder.content = (TextView)convertView.findViewById(R.id.item_problem_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(mList.get(position).getTitle());
		holder.date.setText(mList.get(position).getCreatime());
		String content = mList.get(position).getContent();
		content = content.replaceAll("<p>", "").replaceAll("</p>", "");
		holder.content.setText(content);
		return convertView;
	}

	class ViewHolder {
		TextView title,date,content;
	}
}
