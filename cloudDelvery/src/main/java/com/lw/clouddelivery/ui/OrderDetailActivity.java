package com.lw.clouddelivery.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.base.BaseActivity;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Order;
import com.lw.clouddelivery.util.CommTool;
import com.lw.clouddelivery.util.DCUtil;

public class OrderDetailActivity extends BaseActivity {

	private Order mOrder;
	private ImageView orderdetail_iv_bg;
	private TextView circleState1TV,circleJuliTV,circleMoneyTV
		,orderdetail_payment,orderdetail_weight,orderdetail_beizhu,orderdetail_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_orderdetail);
		getTitleBar().initTitleText("订单详情");

		mOrder = (Order) getIntent().getExtras().getSerializable("order");
		findViews();
		fillData();
	}
	@Override
	public void fillData() {
		super.fillData();
		circleState1TV.setText(DCUtil.getStatusStr(mOrder.getOrderstatusId()));
		circleJuliTV.setText(mOrder.getJuli() + "公里/" + mOrder.getWeight() + "公斤");
		circleMoneyTV.setText(mOrder.getTotalamount() + "");
		orderdetail_payment.setText(mOrder.getPayState());
		orderdetail_weight.setText("物品重量：" + mOrder.getWeight());
		orderdetail_beizhu.setText("备注：" + mOrder.getReamaker());
		orderdetail_name.setText("物品名称：" + mOrder.getOrdername());
	}
	@Override
	public void findViews() {
		super.findViews();
		orderdetail_iv_bg = (ImageView)this.findViewById(R.id.orderdetail_iv_bg);
		int circleW = (int)(scW * 0.582);
		LayoutParams params = new LayoutParams(circleW,circleW);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.topMargin = CommTool.dpToPx(this, 20);
		params.bottomMargin = CommTool.dpToPx(this, 10);
		orderdetail_iv_bg.setLayoutParams(params);
		
		 circleState1TV = (TextView)this.findViewById(R.id.circleState1TV);
		 circleJuliTV = (TextView)this.findViewById(R.id.circleJuliTV);
		 circleMoneyTV = (TextView)this.findViewById(R.id.circleMoneyTV);
		 orderdetail_payment = (TextView)this.findViewById(R.id.orderdetail_payment);
		 orderdetail_weight = (TextView)this.findViewById(R.id.orderdetail_weight);
		 orderdetail_beizhu = (TextView)this.findViewById(R.id.orderdetail_beizhu);
		 orderdetail_name = (TextView)this.findViewById(R.id.orderdetail_name);
	}
}
