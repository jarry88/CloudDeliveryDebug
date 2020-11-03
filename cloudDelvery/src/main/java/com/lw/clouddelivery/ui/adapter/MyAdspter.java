package com.lw.clouddelivery.ui.adapter;

import java.util.List;
import java.util.Map;

import com.lw.clouddelivery.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MyAdspter extends BaseAdapter {

	private List<Map<String, Object>> data;
	private LayoutInflater layoutInflater;
	private Context context;

	public MyAdspter(Context context, List<Map<String, Object>> data) {
		this.context = context;
		this.data = data;
		this.layoutInflater = LayoutInflater.from(context);
	}

	/**
	 * 组件集合，对应list.xml中的控件
	 * 
	 * @author Administrator
	 */
	public final class Zujian {
		public ImageView image;
		public TextView title;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	/**
	 * 获得某一位置的数据
	 */
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	/**
	 * 获得唯一标识
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Zujian zujian = null;
		if (convertView == null) {
			zujian = new Zujian();
			// 获得组件，实例化组件
			convertView = layoutInflater.inflate(R.layout.listitem_left, null);
			zujian.image = (ImageView) convertView.findViewById(R.id.img_item);
			zujian.title = (TextView) convertView
					.findViewById(R.id.txt_itemTittle);
			convertView.setTag(zujian);
		} else {
			zujian = (Zujian) convertView.getTag();
		}
		// 绑定数据
		zujian.image.setBackgroundResource((Integer) data.get(position).get(
				"image"));
		zujian.title.setText((String) data.get(position).get("title"));
		return convertView;
	}

}
