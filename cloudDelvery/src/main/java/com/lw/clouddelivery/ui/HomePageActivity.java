package com.lw.clouddelivery.ui;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.location.LocationClientOption.LocationMode;
import com.base.BaseActivity;
import com.base.BaseApp;
import com.base.util.BaseCommTool;
import com.base.util.BaseUIHelper;
import com.base.util.LogWrapper;
import com.base.util.BaseSPTool;
import com.base.util.MyToast;
import com.google.android.material.snackbar.Snackbar;
import com.lw.clouddelivery.CloudDeliveryAPP;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.Broadcase.OrderReceiver;
import com.lw.clouddelivery.bean.MyMessage;
import com.lw.clouddelivery.bean.Order;
import com.lw.clouddelivery.bean.OrderCount;
import com.lw.clouddelivery.bean.User;
import com.lw.clouddelivery.bean.Vehicle;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.ui.adapter.MyAdspter;
import com.lw.clouddelivery.ui.adapter.OrderListAdapter;
import com.lw.clouddelivery.ui.exam.ExamVoteSubmitActivity;
import com.lw.clouddelivery.util.CommTool;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.DialogUtils;
import com.lw.clouddelivery.util.MySPTool;
import com.lw.clouddelivery.util.NetUtils;
import com.lw.clouddelivery.util.PermissionUtil;
import com.lw.clouddelivery.util.UIHelper;
import com.lw.clouddelivery.util.polling.PollingService;
import com.lw.clouddelivery.util.polling.PollingUtils;

public class HomePageActivity extends BaseActivity implements OnItemClickListener, OnClickListener {
	private static final int REQUEST_CONTACTS = 0;

	private static final String[] PERMISSIONS_CONTACT = {Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE};

	private String TAG = "HomePageActivity";
//	Manifest.permission.ACCESS_COARSE_LOCATION,
//    Manifest.permission.ACCESS_FINE_LOCATION,
//    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//    Manifest.permission.READ_PHONE_STATE;
	
	private ListView drawer_LV;
	private ImageView btn_left, btn_right;
	private DrawerLayout mDrawerLayout;
	private TextView txt_showMap;
	private  TextView txt_ByDate;
	private Button btn_update, homepgerbtn_order, homepage_btn_Vehicle;
	private TextView jrOrderCount, jrOrderMoney;
	private ListView home_todayOrderLV; // 今日已抢订单列表
	private Button refreshIV;
	private OrderListAdapter orderListAdapter;

	// 位置相关
	private LocationClient mLocationClient;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String[] tempcoor = new String[] { "gcj02", "bd09ll", "bd09" };// 国家测绘局标准//百度经纬度标准//百度墨卡托标准
	private int frequence = 60000; // 请求时间间隔

	/**
	 * *******************************************************
	 * 
	 * 左抽屉<listview 数据>
	 * 
	 * *******************************************************
	 */
	private String[] str = { "壹步达会员手册", "常见问题", "客服电话", "地图离线", "通知", "消息",
			"设置", "意见反馈", "密码修改", "版本升级", "关于", "退出登录" };
	private int[] icon_item = { R.drawable.cl_03, R.drawable.cl_06,
			R.drawable.cl_09, R.drawable.cl_10, R.drawable.cl_15,
			R.drawable.cl_19, R.drawable.cl_22, R.drawable.cl_24,
			R.drawable.cl_26, R.drawable.cls_03,
			R.drawable.cls_06, R.drawable.cls_08 };

	private OrderReceiver orderReceiver = new OrderReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			super.onReceive(context, intent);
			LogWrapper.e(HomePageActivity.class, "收到订单完成的通知，重新请求数据，刷新界面!!");
			fillData();
		}
	};

	@TargetApi(23)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		mLocationClient = ((CloudDeliveryAPP) getApplication()).mLocationClient;
		View v = LayoutInflater.from(this).inflate(R.layout.activity_home_page, null);
		initView();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
				    showContacts(v);
				}else{
					initLocation();
					
				 }
			
            } else {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }else{
        	initLocation();
        }
		fillData();

		IntentFilter filter = new IntentFilter();
		filter.addAction(INI.BROADCASE.ORDER);
		registerReceiver(orderReceiver, filter);

		// 请求个人资料
		DCUtil.requestUserInfo(HomePageActivity.this);

		Log.v("homePageActivity149","是否显示对话框：IMG1："+(TextUtils.isEmpty(MySPTool.getString(this, INI.SP.IMG1, INI.SP.IMG1))
				+"-IMG2:" +
				TextUtils.isEmpty(MySPTool.getString(this, INI.SP.IMG2, INI.SP.IMG2))
				+"-IMG3:" +
				TextUtils.isEmpty(MySPTool.getString(this, INI.SP.IMG3, INI.SP.IMG3))
				+"-ADD:" +
				TextUtils.isEmpty(MySPTool.getString(this, INI.SP.ADD, INI.SP.ADD))
				+"-WEIGHT:" +
				TextUtils.isEmpty(MySPTool.getString(this, INI.SP.WEIGHT, INI.SP.WEIGHT))
				+"-HEIGHT:" +
				TextUtils.isEmpty(MySPTool.getString(this, INI.SP.HEIGHT, INI.SP.HEIGHT))	
				+"-SCORE:" +
				TextUtils.isEmpty(MySPTool.getString(this, INI.SP.SCORE, INI.SP.SCORE))));
		
		
		// 开启定时轮询服务
		PollingUtils.startPollingService(HomePageActivity.this, 7, PollingService.class, PollingService.ACTION);
		
		//请求通知列表接口
		DCUtil.requestMessageList(HomePageActivity.this, pd, 1,"", 1, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				ArrayList<MyMessage> messageList = (ArrayList<MyMessage>) msg.obj;
				if(messageList != null && messageList.size() > 0) {
					MyMessage message = messageList.get(0);
//					if(message.getTime() > MySPTool.getLong(HomePageActivity.this, INI.SP.MESSAGE_LAST_TIME, -1)) {
//						MyToast.showText(HomePageActivity.this, "有新通知!");
//						Dialog alertDialog = new AlertDialog.Builder(HomePageActivity.this).
//							    setTitle(messageList.get(0).getXiaoxi()).
//							    setMessage(messageList.get(0).getReason()).
//							    setIcon(R.drawable.icon).
//							    create();
//							  alertDialog.show();
						MySPTool.putLong(HomePageActivity.this, INI.SP.MESSAGE_LAST_TIME, message.getTime());
//					}
				}
			}
		});
		DCUtil.versionCheck(pd, HomePageActivity.this,false);
		
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		btn_left = (ImageView) findViewById(R.id.btn_menu_left);
		btn_right = (ImageView) findViewById(R.id.btn_menu_right);
		mLocationClient = new LocationClient(this.getApplicationContext());
		mLocationClient.registerLocationListener(new MyLocationListener());
		txt_showMap = (TextView) findViewById(R.id.txt_showMap);// 更新地址
		btn_update = (Button) findViewById(R.id.btn_update);
		homepgerbtn_order = (Button) findViewById(R.id.homepgerbtn_order);// 今日订单
		homepage_btn_Vehicle = (Button) findViewById(R.id.homepage_btn_Vehicle);// 交通工具
		jrOrderCount = (TextView) findViewById(R.id.jrOrderCount);
		jrOrderMoney = (TextView) findViewById(R.id.jrOrderMoney);
		refreshIV = (Button) findViewById(R.id.img_Refresh);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
		drawer_LV = (ListView) findViewById(R.id.listview_left);
		List<Map<String, Object>> list = getData();
		drawer_LV.setAdapter(new MyAdspter(getApplicationContext(), list));
		drawer_LV.setOnItemClickListener(this);
		btn_left.setOnClickListener(this);
		btn_right.setOnClickListener(this);
		btn_update.setOnClickListener(this);
		homepgerbtn_order.setOnClickListener(this);
		homepage_btn_Vehicle.setOnClickListener(this);
		home_todayOrderLV = (ListView) this.findViewById(R.id.home_todayOrderLV);
		refreshIV.setOnClickListener(this);

		txt_ByDate = (TextView)this.findViewById(R.id.txt_ByDate);
		txt_ByDate.setText(CommTool.date2CNStr(System.currentTimeMillis()));
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		switch (position) {
		case 0:// 壹步达员手册
			UIHelper.showUserManual(this,2);
			break;
		case 1:// 常见问题
//			UIHelper.showUserManual(this,1);
			UIHelper.showProblemList(HomePageActivity.this);
			break;
		case 2:// 客服电话
			BaseCommTool.callPhone(HomePageActivity.this, INI.SERVER_PHONE);
			break;
		case 3:// 地图离线
			UIHelper.showMapDownload(HomePageActivity.this);
			break;
		case 4:// 通知
			UIHelper.showMessageListActivity(this, 3+"");
			break;
		case 5:// 消息
			UIHelper.showMessageListActivity(this, 2+"");
			break;
		case 6:// 设置
			UIHelper.showSettingPage(this);
			break;
		case 7:// 意见反馈
			UIHelper.showFeedback(HomePageActivity.this);
			break;
		case 8:// 密码修改
			UIHelper.showResetPwd(HomePageActivity.this);
			break;
		case 9:// 版本升级
			DCUtil.versionCheck(pd, HomePageActivity.this,true);
			break;
		case 10:// 关于
			UIHelper.showAboutPage(this);
			break;
		case 11:// 退出
			// cleanPwd();
			UIHelper.logout(this);
			break;
		/*
		 * case 12: break; case 13: Intent Aboutintent = new
		 * Intent(getApplicationContext(), AboutActivity.class);
		 * startActivity(Aboutintent); break;
		 */
		}
		setTitle(str[position]);
	}

	@SuppressLint("HandlerLeak")
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		// 左抽屉
		case R.id.btn_menu_left:
			mDrawerLayout.openDrawer(Gravity.LEFT);
			break;
		case R.id.btn_menu_right:
			UIHelper.showMyYunSong(HomePageActivity.this);
			break;
		// 更新位置
		case R.id.btn_update: {
			UIHelper.showReLocation(this);
			break;
		}
		// 今日订单
		case R.id.homepgerbtn_order:
			UIHelper.showOrderList(HomePageActivity.this, 1);
			break;
		// 交通工具
		case R.id.homepage_btn_Vehicle:
			UIHelper.showChoiceVehicle(HomePageActivity.this);
			break;
		case R.id.img_Refresh:
			DCUtil.requestJROrderList(this, pd, MySPTool.getUid(this),
					MySPTool.getLon(this), MySPTool.getLat(this),
					new Handler() {
						@Override
						public void handleMessage(Message msg) {
							super.handleMessage(msg);
							ArrayList<Order> orderList = (ArrayList<Order>) msg.obj;
							orderListAdapter = new OrderListAdapter(HomePageActivity.this);
							orderListAdapter.setPageType(OrderListActivity.ORDERLIST_TYPE_MINE);
							orderListAdapter.setList(orderList);
							home_todayOrderLV.setAdapter(orderListAdapter);
							home_todayOrderLV.setOnItemClickListener(orderListAdapter);
						}
					});
			break;
		}
	}

	public List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < str.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", icon_item[i]);
			map.put("title", str[i]);
			list.add(map);
		}
		return list;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == ReLocActivity.RESULT_RELOC) {
			LogWrapper.e(HomePageActivity.class,
					"HomePageActivity.onActivityResult");
			String locStr = intent.getStringExtra("locStr");
			txt_showMap.setText(locStr);
			fillData();
		} else if (resultCode == ChoiceVehicleActivity.RESULT_VEHICLE) {
			Vehicle vehicle = (Vehicle) intent.getExtras().getSerializable(
					"vehicle");
			homepage_btn_Vehicle.setText(vehicle.getName());
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(orderReceiver);
	}

	/**
	 * clean shar
	 */
	private void cleanPwd() {
		SharedPreferences settings = getSharedPreferences("user", MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.remove("userPwd");
		editor.remove("userTel");
		editor.commit();
		finish();
		startActivity(new Intent(getApplicationContext(), LoginActivity.class));
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		mLocationClient.stop();
		super.onStop();
	}
	

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType(tempcoor[0]);// 可选，默认gcj02，设置返回的定位结果坐标系，
		int span = frequence;
		try {
			span = frequence;
		} catch (Exception e) {
			// TODO: handle exception
		}
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		// option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		mLocationClient.setLocOption(option);
		
		if(!mLocationClient.isStarted())
			mLocationClient.start();
		/*new Thread() {
			@Override
			public void run() {
				super.run();
				
				String lon = MySPTool.getLon(HomePageActivity.this);
				while(lon == null || lon.equals("")) {
					mLocationClient.start();
					lon = MySPTool.getLon(HomePageActivity.this);
					LogWrapper.e(HomePageActivity.class, "启动定位服务");
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();*/
	}

	@Override
	public void fillData() {
		super.fillData();

		DCUtil.requestJROrderCount(this, pd,
				MySPTool.getUid(HomePageActivity.this), new Handler() {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						OrderCount orderCount = (OrderCount) msg.obj;
						jrOrderCount.setText(orderCount.getCount());
						jrOrderMoney.setText(orderCount.getMoney() + "");
					}
				});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// 请求个人资料
		DCUtil.requestUserInfo(HomePageActivity.this);
		DCUtil.requestJROrderList(this, pd, MySPTool.getUid(this),
				MySPTool.getLon(this), MySPTool.getLat(this), new Handler() {

					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						ArrayList<Order> orderList = (ArrayList<Order>) msg.obj;
						orderListAdapter = new OrderListAdapter(
								HomePageActivity.this);
						orderListAdapter.setPageType(OrderListActivity.ORDERLIST_TYPE_MINE);
						orderListAdapter.setList(orderList);
						home_todayOrderLV.setAdapter(orderListAdapter);
						home_todayOrderLV
								.setOnItemClickListener(orderListAdapter);
					}
				});
	}

	/**
	 * 实现实时位置回调监听
	 */
	class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());

			// 保存经纬度数据
			MySPTool.putString(HomePageActivity.this, INI.SP.LAT,
					location.getLatitude() + "");
			MySPTool.putString(HomePageActivity.this, INI.SP.LON,
					location.getLongitude() + "");
			
			txt_showMap.setText(location.getAddrStr());
			fillData();
			
			String preLat = new String(MySPTool.getLat(HomePageActivity.this));
			if (preLat.equals("")) { // 原本是没有经纬度信息的,重新请求数据内容
				LogWrapper.e(HomePageActivity.class, "首次获取到经纬度信息，开始请求数据，填充界面");
				fillData();
			} else {
				LogWrapper.e(HomePageActivity.class,
						"获取到经纬度信息，Lat:" + location.getLatitude() + ",Lon:"
								+ location.getLongitude());
			}

			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());// 单位：公里每小时
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\nheight : ");
				sb.append(location.getAltitude());// 单位：米
				sb.append("\ndirection : ");
				sb.append(location.getDirection());
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\ndescribe : ");
				sb.append("gps定位成功");

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// 运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				sb.append("\ndescribe : ");
				sb.append("网络定位成功");
			} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
				sb.append("\ndescribe : ");
				sb.append("离线定位成功，离线定位结果也是有效的");
			} else if (location.getLocType() == BDLocation.TypeServerError) {
				sb.append("\ndescribe : ");
				sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
			} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
				sb.append("\ndescribe : ");
				sb.append("网络不同导致定位失败，请检查网络是否通畅");
			} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
				sb.append("\ndescribe : ");
				sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
			}
			sb.append("\nlocationdescribe : ");// 位置语义化信息
			sb.append(location.getLocationDescribe());
			List<Poi> list = location.getPoiList();// POI信息
			if (list != null) {
				sb.append("\npoilist size = : ");
				sb.append(list.size());
				for (Poi p : list) {
					sb.append("\npoi= : ");
					sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
				}
			}
			LogWrapper.e(CloudDeliveryAPP.class, sb.toString());
			Log.i("aaa", sb.toString());
		}
	}
	
	
	public void showContacts(View v) {
	    Log.i("HOmepage", "Show contacts button pressed. Checking permissions.");
	    // Verify that all required contact permissions have been granted.
	    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
	            != PackageManager.PERMISSION_GRANTED
	            || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
	            != PackageManager.PERMISSION_GRANTED
	            || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
	            != PackageManager.PERMISSION_GRANTED
	            || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
	            != PackageManager.PERMISSION_GRANTED) {
	        // Contacts permissions have not been granted.
	        Log.i(TAG, "Contact permissions has NOT been granted. Requesting permissions.");
	        requestContactsPermissions(v);

	    } else {

	        // Contact permissions have been granted. Show the contacts fragment.
	        Log.i(TAG, "Contact permissions have already been granted. Displaying contact details.");
			initLocation();
	    }
	}
	private void requestContactsPermissions(View v) {
	    // BEGIN_INCLUDE(contacts_permission_request)
	    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
	            Manifest.permission.ACCESS_COARSE_LOCATION)
	            || ActivityCompat.shouldShowRequestPermissionRationale(this,
	            Manifest.permission.ACCESS_FINE_LOCATION)
	            || ActivityCompat.shouldShowRequestPermissionRationale(this,
	            Manifest.permission.WRITE_EXTERNAL_STORAGE)
	            || ActivityCompat.shouldShowRequestPermissionRationale(this,
	            Manifest.permission.READ_PHONE_STATE)
	            ) {

	        // Provide an additional rationale to the user if the permission was not granted
	        // and the user would benefit from additional context for the use of the permission.
	        // For example, if the request has been denied previously.
	        Log.i(TAG,"Displaying contacts permission rationale to provide additional context.");
	        // Display a SnackBar with an explanation and a button to trigger the request.
	        Snackbar.make( v, "permission_contacts_rationale",Snackbar.LENGTH_INDEFINITE).setAction("ok", new View.OnClickListener() {
	                    @Override
	                    public void onClick(View view) {
	                        ActivityCompat .requestPermissions(HomePageActivity.this, PERMISSIONS_CONTACT,  REQUEST_CONTACTS);
	                    }
	                })
	                .show();
	    } else {
	        // Contact permissions have not been granted yet. Request them directly.
	        ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
	    }
	    // END_INCLUDE(contacts_permission_request)
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode,
			String[] permissions, int[] grantResults) {
		if (requestCode==REQUEST_CONTACTS){
	        if (PermissionUtil.verifyPermissions(grantResults)) {
	        	initLocation();
	        } else {

	        }


	    }else{
	    	super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	    }
	}
}
