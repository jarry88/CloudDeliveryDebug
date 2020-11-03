package com.lw.clouddelivery.ui;

import java.text.DecimalFormat;

import net.tsz.afinal.exception.AfinalException;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.BaseActivity;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Order;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.util.UIHelper;

public class AfterQiangdanActivity extends BaseActivity {
	private FrameLayout after_qiangdan_item;
	private Order mOrder;
	private TextView afterQD_priceTV,afterQD_orderNoTV,afterQD_beizhuTV;
	private LinearLayout afterQD_showmap;
	private ImageView order_PayStyle,order_appointment;
	
	private TextView startPlaceTV,endPlaceTV,afterQD_juliTV;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_after_qiangdan);
		getTitleBar().initTitleText("壹步达");
		mOrder = (Order) getIntent().getExtras().getSerializable("order");
		
		findViews();
		after_qiangdan_item = (FrameLayout)this.findViewById(R.id.after_qiangdan_item);
		after_qiangdan_item.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				mOrder.setOrderstatusId("2"); //抢单成功后修改状态为2
				UIHelper.showYSStep1(AfterQiangdanActivity.this,mOrder);
			}
		});
	}

	@Override
	public void findViews() {
		super.findViews();
		afterQD_priceTV = (TextView)this.findViewById(R.id.afterQD_priceTV);
		afterQD_priceTV.setText("￥" + mOrder.getTotalamount() + "/" + mOrder.getJuli() + "公里/" + mOrder.getWeight() + "公斤");
		
		afterQD_orderNoTV = (TextView)this.findViewById(R.id.afterQD_orderNoTV);
		afterQD_orderNoTV.setText("单号：" + mOrder.getOrderno());
		
		afterQD_beizhuTV = (TextView)this.findViewById(R.id.afterQD_beizhuTV);
		afterQD_beizhuTV.setText("备注： " + mOrder.getReamaker());
		
		startPlaceTV = (TextView)this.findViewById(R.id.startPlace);
		endPlaceTV = (TextView)this.findViewById(R.id.endPlace);
		startPlaceTV.setText(mOrder.getDeli_address());
		endPlaceTV.setText(mOrder.getSaddress());
		afterQD_juliTV = (TextView)this.findViewById(R.id.afterQD_juliTV);
//		DecimalFormat    df   = new DecimalFormat("######0.00");  
//		String juniDistance = df.format((Double.valueOf(mOrder.getJuli()) / 1000.0));
		afterQD_juliTV.setText(mOrder.getJuli() + "公里");
		order_PayStyle = (ImageView)this.findViewById(R.id.order_PayStyle);
		if(mOrder.getPayState().equals("已支付")) {
			order_PayStyle.setVisibility(View.VISIBLE);
		} else {
			order_PayStyle.setVisibility(View.GONE);
		}
		order_appointment = (ImageView)this.findViewById(R.id.order_appointment);
		if(mOrder.getYtime() != null && !mOrder.getYtime().equals("")) {
			order_appointment.setVisibility(View.VISIBLE);
		} else {
			order_appointment.setVisibility(View.GONE);
		}
		
		afterQD_showmap = (LinearLayout)this.findViewById(R.id.afterQD_showmap);
		afterQD_showmap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				UIHelper.showQiangDanDialog(AfterQiangdanActivity.this
//						, mOrder,false,true);
				UIHelper.showCheckMap(AfterQiangdanActivity.this, mOrder);
			}
		});
	}
}
