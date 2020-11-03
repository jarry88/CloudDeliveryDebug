package com.lw.clouddelivery.ui.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.widget.ArrayListAdapter;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Order;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.ui.OrderListActivity;
import com.lw.clouddelivery.ui.xxxActivity;
import com.lw.clouddelivery.util.CommTool;
import com.lw.clouddelivery.util.UIHelper;

public class OrderListAdapter extends ArrayListAdapter<Order> implements OnItemClickListener {

	private LayoutInflater mInflater;
	private int pageType;
	
	public OrderListAdapter(Activity context) {
		super(context);
		mInflater = LayoutInflater.from(context);
	}

	public void setPageType(int pageType) {
		this.pageType = pageType;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder initorderview = null;
		if (convertView == null) {
			initorderview = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listitem_ordertoday,
					null);
			initorderview.startPlace = (TextView) convertView
					.findViewById(R.id.startPlace);
			initorderview.endPlace = (TextView) convertView
					.findViewById(R.id.endPlace);
			initorderview.order_Date = (TextView) convertView
					.findViewById(R.id.order_Date);
			initorderview.order_Num = (TextView) convertView
					.findViewById(R.id.order_Num);
			initorderview.order_td_heavy = (TextView) convertView
					.findViewById(R.id.order_td_heavy);
			initorderview.order_td_money = (TextView) convertView
					.findViewById(R.id.order_td_money);
			initorderview.order_td_kmetre = (TextView) convertView
					.findViewById(R.id.order_td_kmetre);
			initorderview.item_order_today_state = (ImageView)convertView.findViewById(R.id.item_order_today_state);
			initorderview.order_PayStyle = (ImageView)convertView.findViewById(R.id.order_PayStyle);
			convertView.setTag(initorderview);
		} else {
			initorderview = (ViewHolder) convertView.getTag();
		}
		Order order = (Order) mList.get(position);
		initorderview.order_td_kmetre.setText(order.getJuli());
		initorderview.order_td_money.setText(order.getTotalamount() + "元");
		initorderview.order_td_heavy.setText(order.getWeight());
		initorderview.startPlace.setText(order.getDeli_address());
		initorderview.endPlace.setText(order.getSaddress());
		initorderview.order_Date.setText(CommTool.date2CNStr(mList.get(position).getCreatetime()));
		initorderview.order_Num.setText(order.getOrderno());
		switch(Integer.valueOf(order.getOrderstatusId())) {
		case 1:
			initorderview.item_order_today_state.setImageResource(R.drawable.dd_state_01);
			break;
		case 2:
			initorderview.item_order_today_state.setImageResource(R.drawable.dd_state_02);
			break;
		case 3:
			initorderview.item_order_today_state.setImageResource(R.drawable.dd_state_03);
			break;
		case 4:
			initorderview.item_order_today_state.setImageResource(R.drawable.dd_state_04);
			break;
		case 5:
			initorderview.item_order_today_state.setImageResource(R.drawable.dd_state_05);
			break;
		case 6:
			initorderview.item_order_today_state.setImageResource(R.drawable.dd_state_06);
			break;
		}
		
		if(order.getPayState().equals("已支付")) {
			initorderview.order_PayStyle.setVisibility(View.VISIBLE);
			initorderview.order_PayStyle.setImageResource(R.drawable.dqd_02);
		} else if(order.getPayState().equals("月结")){
			initorderview.order_PayStyle.setVisibility(View.VISIBLE);
			initorderview.order_PayStyle.setImageResource(R.drawable.dqd_03);
		} else {
			initorderview.order_PayStyle.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	private class ViewHolder {

		private TextView startPlace, endPlace, order_td_money, order_td_kmetre,
				order_td_heavy, order_Num, order_Date;
		private ImageView item_order_today_state;
		private ImageView order_PayStyle; //已支付
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(pageType == OrderListActivity.ORDERLIST_TYPE_MINE) {
			String orderStatusId = mList.get(position).getOrderstatusId();
			if(orderStatusId.equals(INI.STATE.ORDER_WAITING)) {
				UIHelper.showQiangDanDialog(mContext, mList.get(position),false,false);
			} else if(orderStatusId.equals(INI.STATE.ORDER_FINISHED)){
					UIHelper.showOrderDetail(mContext,mList.get(position));
			} else {
				UIHelper.showYSStep1(mContext, mList.get(position));
			}
		} else if(pageType == OrderListActivity.ORDERLIST_TYPE_TODAY){
			UIHelper.showQiangDanDialog(mContext, mList.get(position),false,false);
		}
	}
}
