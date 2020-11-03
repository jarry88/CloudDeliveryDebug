package com.lw.clouddelivery.ui.exam;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import com.base.widget.LoadingDialog;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.util.DCUtil;

import java.util.ArrayList;
import java.util.List;

public class VoteSubmitAdapter extends PagerAdapter {

	ExamVoteSubmitActivity mContext;
	// 传递过来的页面view的集合
	List<View> viewItems;
	// 每个item的页面view
	View convertView;
	// 传递过来的所有数据
	ArrayList<VoteSubmitItem> dataItems;
	// 题目选项的adapter
	VoteSubmitListAdapter listAdapter;

	ViewHolder holder = null;
	LoadingDialog pd;
	
	private int post;//是题目编号
	private int total= 0;//总分   
	
	private boolean zero = true,one = true, two = true, three = true,four= true,five= true,six= true,seven= true,eight= true,night= true;
	
	public VoteSubmitAdapter(ExamVoteSubmitActivity context, List<View> viewItems, ArrayList<VoteSubmitItem> dataItems,LoadingDialog pd) {
		mContext = context;
		this.viewItems = viewItems;
		this.dataItems = dataItems;
		this.pd  = pd;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(viewItems.get(position));
	}

	@SuppressWarnings("static-access")
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		holder = new ViewHolder();
		convertView = viewItems.get(position);
		holder.title = (TextView) convertView.findViewById(R.id.vote_submit_title);
		holder.question = (TextView) convertView.findViewById(R.id.vote_submit_question);
		holder.listView = (ListView) convertView.findViewById(R.id.vote_submit_listview);
		holder.previoustext = (TextView) convertView.findViewById(R.id.vote_submit_previous_text);
		holder.previousBtn = (LinearLayout) convertView.findViewById(R.id.vote_submit_linear_previous);
		holder.nextBtn = (LinearLayout) convertView.findViewById(R.id.vote_submit_linear_next);
		holder.nextText = (TextView) convertView.findViewById(R.id.vote_submit_next_text);
		holder.nextImage = (ImageView) convertView.findViewById(R.id.vote_submit_next_image);

//		holder.title.setText("<您关注的新闻民意调查>");   java里面   类是不能强转的，必须set
		listAdapter = new VoteSubmitListAdapter(mContext, dataItems.get(position).voteAnswers );
//		listAdapter.setListItemLinster((IItemLinster) mContext);
		//转换代码 
		holder.question.setText(dataItems.get(position).voteQuestion);
		holder.listView.setAdapter(listAdapter);
		holder.listView.setOnItemClickListener(new ListViewOnClickListener(listAdapter));

		holder.previoustext.setText("上一题"); 
		holder.nextText.setText("下一题");
		
		// 第一页隐藏"上一步"按钮
		if (position == 0) {
			holder.previousBtn.setVisibility(View.GONE);
		} else {
			holder.previousBtn.setVisibility(View.VISIBLE);
			holder.previousBtn.setOnClickListener(new LinearOnClickListener(position - 1));
		}
		// 最后一页修改"下一步"按钮文字
		if (position == viewItems.size() - 1) {
			holder.nextText.setText("完成考试");
			holder.nextImage.setImageResource(R.drawable.vote_submit_finish);
		}
		post=position;
		holder.nextBtn.setOnClickListener(new LinearOnClickListener(position + 1));
		container.addView(viewItems.get(position));
//		Log.v("VotesubmitAdatper95", "当前位置post："+post+"--position："+position);
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
		public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
			// 设置更新选中项图片和文本变化
			mListAdapter.updateIndex(position);
			//post 当前题号，因为上面+1 ，这个地方要减去1
			switch (post) {
			case 1:
				if(position==2){
					if(zero){
						zero=false;
						total=total+10;
					}
				}else{
					total=total;
				}
				break;
			case 2:
				if(position==0){
					if(one){
						one=false;
						total=total+10;
					}
				}else{
					total=total;
				}	
				break;
			case 3:
				if(position==0){
					if(two){
						two=false;
						total=total+10;
					}
				}else{
					total=total;
				}	
				break;
			case 4:
				if(position==0){
					if(three){
						three=false;
						total=total+10;
					}
				}else{
					total=total;
				}	
				break;
			case 5:
				if(position==2){
					if(four){
						four=false;
						total=total+10;
					}
				}else{
					total=total;
				}	
				break;
			case 6:
				if(position==2){
					if(five){
						five=false;
						total=total+10;
					}
				}else{
					total=total;
				}	
				break;
			case 7:
				if(position==1){//5公里内，30分钟送达
					if(six){
						six=false;
						total=total+10;
					}
				}else{
					total=total;
				}	
				break;
			case 8:
				if(position==0){
					if(seven){
						seven=false;
						total=total+10;
					}
				}else{
					total=total;
				}	
				break;
			case 9:
				if(position==1){
					if(eight){
						eight=false;
						total=total+10;
					}
				}else{
					total=total;
				}	
				post=post+1;//最后一题进入这里
				Log.v("223", "post:"+post);
				break;
			case 10:
				if(position==0){
					if(night){
						night=false;
						total=total+10;//post =9，并没有点击出来10此处最后一个没有点击，所以这个地方要加20
					}
				}else{
					total=total;
				}	
				break;
			default:
				break;
			}
			Log.v("VotesubmitAdatper259", "题号："+post+"--考试成绩："+total);
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
				if(total>=100){
					Toast.makeText(mContext, "恭喜您通过考试!", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(mContext, "很遗憾，您未通过考试!", Toast.LENGTH_LONG).show();
				}
				DCUtil.postSourceinfo(  mContext, pd , total+"", new Handler() {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
//						MyToast.showText(mContext,"注册成功");
						mContext.finish();
					}
				});
				Log.v("VotesubmitAdatper133", "考试成绩："+total);
				
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
