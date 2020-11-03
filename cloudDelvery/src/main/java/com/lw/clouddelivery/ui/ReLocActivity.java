package com.lw.clouddelivery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.base.BaseActivity;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.util.MySPTool;
import com.lw.clouddelivery.util.gps.Gps;
import com.lw.clouddelivery.util.gps.PositionUtil;

/**
 * 重新定位界面
 * 
 * @author leon
 *
 */
public class ReLocActivity extends BaseActivity implements OnClickListener {

	public static int RESULT_RELOC = 4;
	private TextView currentLocTV;
	private Button reLocBTN, submitBTN;
	private ImageView backIV;

	private String locStr;

	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;

	MapView mMapView;
	BaiduMap mBaiduMap;

	BDLocation mLocation;

//	boolean isFirstLoc = true;// 是否首次定位

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_relocation);

		currentLocTV = (TextView) this.findViewById(R.id.locTV);
		reLocBTN = (Button) this.findViewById(R.id.reLocBTN);
		mCurrentMode = LocationMode.NORMAL;
		backIV = (ImageView) this.findViewById(R.id.backIV);
		submitBTN = (Button) this.findViewById(R.id.submitBTN);

		// 地图初始化
		mMapView = (MapView) findViewById(R.id.locMap);
		mBaiduMap = mMapView.getMap();
		   mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(getApplicationContext());
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5000);
		 option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
         option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
         option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		mLocClient.setLocOption(option);
		mLocClient.start();

		reLocBTN.setOnClickListener(this);
		submitBTN.setOnClickListener(this);
		backIV.setOnClickListener(this);
		setDialogText("地图定位中...");
		
		// // 修改为自定义marker
		// mCurrentMarker = BitmapDescriptorFactory
		// .fromResource(R.drawable.wo);
		// mBaiduMap
		// .setMyLocationConfigeration(new MyLocationConfiguration(
		// mCurrentMode, true, mCurrentMarker));
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);

			locStr = location.getAddrStr();

			Gps gps = PositionUtil.bd09_To_Gcj02(location.getLatitude(), location.getLongitude());
			
			MySPTool.putString(ReLocActivity.this, INI.SP.LAT,gps.getWgLat() + "");
			MySPTool.putString(ReLocActivity.this, INI.SP.LON,gps.getWgLon() + "");
//			MySPTool.putString(ReLocActivity.this, INI.SP.LAT,location.getLatitude() + "");
//			MySPTool.putString(ReLocActivity.this, INI.SP.LON,location.getLongitude() + "");

//			if (isFirstLoc) {
//				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
//			}

			currentLocTV.setText(location.getAddrStr());
			mLocClient.stop();
			pd.dismiss();
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submitBTN:
			Intent i = new Intent();
			i.putExtra("locStr", locStr);
			setResult(RESULT_RELOC, i);
			finish();
			break;
		case R.id.backIV:
			finish();
			break;
		case R.id.reLocBTN:
			mLocClient.start();
			pd.show();
			break;
		}
	}
}
