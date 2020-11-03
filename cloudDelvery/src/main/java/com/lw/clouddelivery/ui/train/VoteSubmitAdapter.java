package com.lw.clouddelivery.ui.train;

import java.util.ArrayList;
import java.util.List;

import com.lw.clouddelivery.R;
import com.lw.clouddelivery.ui.exam.VoteSubmitItem;

import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

public class VoteSubmitAdapter extends PagerAdapter {

	TrainVoteSubmitActivity mContext;
	// 传递过来的页面view的集合
	List<View> viewItems;
	// 每个item的页面view
	View convertView;
	// 传递过来的所有数据
	ArrayList<VoteSubmitItem> dataItems;
	// 题目选项的adapter
	VoteSubmitListAdapter listAdapter;

	ViewHolder holder = null;

	public VoteSubmitAdapter(TrainVoteSubmitActivity context,
			List<View> viewItems, ArrayList<VoteSubmitItem> dataItems) {
		mContext = context;
		this.viewItems = viewItems;
		this.dataItems = dataItems;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(viewItems.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		holder = new ViewHolder();
		convertView = viewItems.get(position);
		holder.title = (TextView) convertView
				.findViewById(R.id.vote_submit_title);
		holder.question = (TextView) convertView
				.findViewById(R.id.vote_submit_question);
		holder.listView = (ListView) convertView
				.findViewById(R.id.vote_submit_listview);
		holder.previousBtn = (LinearLayout) convertView
				.findViewById(R.id.vote_submit_linear_previous);
		holder.previoustext = (TextView) convertView
				.findViewById(R.id.vote_submit_previous_text);
		holder.nextBtn = (LinearLayout) convertView
				.findViewById(R.id.vote_submit_linear_next);
		holder.nextText = (TextView) convertView
				.findViewById(R.id.vote_submit_next_text);
		holder.nextImage = (ImageView) convertView
				.findViewById(R.id.vote_submit_next_image);

//		holder.title.setText("<您关注的新闻民意调查>");
		listAdapter = new VoteSubmitListAdapter(mContext,
				dataItems.get(position).voteAnswers);
		holder.question.setText(dataItems.get(position).voteQuestion);
		holder.listView.setAdapter(listAdapter);
		holder.listView.setOnItemClickListener(new ListViewOnClickListener(
				listAdapter));
		
		holder.previoustext.setText("上一页");
		holder.nextText.setText("下一页");
		
		// 第一页隐藏"上一步"按钮
		if (position == 0) {
			holder.previousBtn.setVisibility(View.GONE);
		} else {
			holder.previousBtn.setVisibility(View.VISIBLE);
			holder.previousBtn.setOnClickListener(new LinearOnClickListener(
					position - 1));
		}
		// 最后一页修改"下一步"按钮文字
		if (position == viewItems.size() - 1) {
			holder.nextText.setText("阅读完毕");
			holder.nextImage.setImageResource(R.drawable.vote_submit_finish);
		}
		holder.nextBtn.setOnClickListener(new LinearOnClickListener(position + 1));
		container.addView(viewItems.get(position));
		return viewItems.get(position);
	}

	/**
	 * @author wisdomhu 自定义listview的item点击事件
	 */
	class ListViewOnClickListener implements OnItemClickListener {

		private VoteSubmitListAdapter mListAdapter;

		public ListViewOnClickListener(VoteSubmitListAdapter VoteSubmlistAdapteritListAdapter) {
			mListAdapter = listAdapter;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				final int position, long id) {
			// 设置更新选中项图片和文本变化
			mListAdapter.updateIndex(position);
		}

	}

	/**
	 * @author wisdomhu 设置上一步和下一步按钮监听
	 * 
	 */
	class LinearOnClickListener implements OnClickListener {

		private int mPosition;

		public LinearOnClickListener(int position) {
			mPosition = position;
		}

		@Override
		public void onClick(View v) {
			if (mPosition == viewItems.size()) {
				Toast.makeText(mContext, "培训完毕，请参加考试!", Toast.LENGTH_SHORT).show();
				mContext.finish();
			} else {
				mContext.setCurrentView(mPosition);
			}
		}

	}

	@Override
	public int getCount() {
		if (viewItems == null)
			return 0;
		return viewItems.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	/**
	 * @author wisdomhu 自定义类
	 */
	class ViewHolder {
		ListView listView;
		TextView title;
		TextView question;
		TextView answer;
		LinearLayout previousBtn, nextBtn;
		TextView nextText;
		ImageView nextImage;
		TextView previoustext;
		
	}

}
