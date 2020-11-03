//package com.lw.clouddelivery.ui;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//
//import com.base.BaseActivity;
//import com.base.util.LogWrapper;
//import com.lw.clouddelivery.R;
//import com.lw.clouddelivery.bean.Help;
//import com.lw.clouddelivery.bean.Vehicle;
//import com.lw.clouddelivery.util.DCUtil;
//
//public class InterfaceActivity extends BaseActivity implements OnClickListener{
//	private List<Help> helpList;
//	
//	private Button btn1,btn2,btn3,btn4,btn5,btn6;
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_interface);
//		btn1 = (Button)this.findViewById(R.id.btn1);
//		btn1.setOnClickListener(this);
//		btn2 = (Button)this.findViewById(R.id.btn2);
//		btn2.setOnClickListener(this);
//		btn3 = (Button)this.findViewById(R.id.btn3);
//		btn3.setOnClickListener(this);
//		btn4 = (Button)this.findViewById(R.id.btn4);
//		btn4.setOnClickListener(this);
//		btn5 = (Button)this.findViewById(R.id.btn5);
//		btn5.setOnClickListener(this);
//		btn6 = (Button)this.findViewById(R.id.btn6);
//		btn6.setOnClickListener(this);
//	}
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn1:
//			DCUtil.requestMessageList(InterfaceActivity.this,pd,1);
//			break;
//		case R.id.btn2:
//			DCUtil.requestHelpList(InterfaceActivity.this,pd,2);
//			break;
//		case R.id.btn3:
//			DCUtil.requestHelpDetail(this, pd, "32");
//			break;
//		case R.id.btn4:
////			DCUtil.requestJROrderList(this, pd, null, "121.48","31.41");
//			break;
//		case R.id.btn5:
////			DCUtil.requestVehicleList(this,pd);
//			DCUtil.requestVehicleList(this, pd, new Handler() {
//				@Override
//				public void handleMessage(Message msg) {
//					super.handleMessage(msg);
//					ArrayList<Vehicle> orderList = (ArrayList<Vehicle>) msg.obj;
//					for(Vehicle v:orderList) {
//						LogWrapper.e(HomePageActivity.class, v.toString());
//					}
//				}
//			});
//			break;
//		case R.id.btn6:
//			DCUtil.selectMyVehicle(this,pd,"2","1",null);
//			break;
//		default:
//			break;
//		}
//	}
//	
//}
