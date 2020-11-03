package com.lw.clouddelivery.ui.adapter;

import java.util.List;
import java.util.Map;



import com.lw.clouddelivery.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GridItemAdapter extends BaseAdapter {
	private List<Map<String, Object>> data_list;
//	private Content content;
	private LayoutInflater layoutInflater;

	public GridItemAdapter(Context context, List<Map<String, Object>> data) {
//		this.content = content;
		this.data_list = data;
		this.layoutInflater = LayoutInflater.from(context);
	}

	public final class Gridviews {
		public ImageView imageView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewgroup) {
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
		views.imageView.setImageResource((Integer) data_list.get(position).get(
				"image"));
		return convertView;
	}
}
