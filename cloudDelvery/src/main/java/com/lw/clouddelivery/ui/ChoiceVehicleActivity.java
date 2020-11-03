package com.lw.clouddelivery.ui;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.base.BaseActivity;
import com.base.util.BaseSPTool;
import com.base.util.MyToast;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Vehicle;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.MySPTool;

/**
 * 选择交通工具界面
 * @author leon
 *
 */
public class ChoiceVehicleActivity extends BaseActivity {
	public static final int RESULT_VEHICLE = 0x55;
	private ImageView[] cbs = new ImageView[4];
	private Button submitBtn;
	private int currentSelected;
	
	private ArrayList<Vehicle> vehicleList;
	
	private String itool="";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_selectvehicle);
		currentSelected = MySPTool.getInt(ChoiceVehicleActivity.this, INI.SP.VEHICLE_INDEX, 0);
		getTitleBar().initTitleText("选择交通工具");
		findViews();
		itool=getIntent().getStringExtra("itool");
		//请求交通工具列表
		DCUtil.requestVehicleList(ChoiceVehicleActivity.this, pd, new Handler() {
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				vehicleList = (ArrayList<Vehicle>) msg.obj;
			}
		});
	}
	@Override
	public void findViews() {
		super.findViews();
		cbs[0] = (ImageView)findViewById(R.id.choicevehicle_cb1);
		cbs[1] = (ImageView)findViewById(R.id.choicevehicle_cb2);
		cbs[2] = (ImageView)findViewById(R.id.choicevehicle_cb3);
		cbs[3] = (ImageView)findViewById(R.id.choicevehicle_cb4);
		submitBtn = (Button)findViewById(R.id.choicevehicle_submitBtn);
		for(int i=0;i<cbs.length;i++) {
			final int j = i;
			cbs[i].setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(currentSelected != j) {
						currentSelected = j;
						refreshCBS();
					}
				}
			});	
		}
		submitBtn.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("HandlerLeak")
			@Override
			public void onClick(View v) {
				if(vehicleList.size()>0) {
					//如果不是注册页面过来的，那么将执行
					if(!"reg".equals(itool)){
						String uidstr=BaseSPTool.getString(ChoiceVehicleActivity.this, INI.SP.UID, "");
						if(TextUtils.isEmpty(uidstr)){
							uidstr="";
						}
						DCUtil.selectMyVehicle(ChoiceVehicleActivity.this,pd, vehicleList.get(currentSelected).getId(),uidstr, new Handler() {
							@Override
							public void handleMessage(Message msg) {
								super.handleMessage(msg);
								MyToast.showText(ChoiceVehicleActivity.this,"提交成功!");
								Intent i = new Intent();
								Bundle b = new Bundle();
								b.putSerializable("vehicle", vehicleList.get(currentSelected));
								i.putExtras(b);
								setResult(RESULT_VEHICLE, i);
								ChoiceVehicleActivity.this.finish();
								MySPTool.putInt(ChoiceVehicleActivity.this, INI.SP.VEHICLE_INDEX, currentSelected);
							}
						});
					}else{
						ChoiceVehicleActivity.this.finish();
					}
				}else{
					MyToast.showText(ChoiceVehicleActivity.this,"请选择交通工具!");
				}
			}
		});
	}
	
	/**
	 * 刷新CheckBox状态
	 */
	private void refreshCBS() {
		for(int i=0;i<cbs.length;i++) {
			if(i == currentSelected) {
				cbs[i].setImageResource(R.drawable.xk_10);
			} else {
				cbs[i].setImageResource(R.drawable.xk_07);
			}
		}
	}
}
