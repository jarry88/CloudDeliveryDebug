//package com.lw.clouddelivery.ui;
//
//import java.util.Random;
//
//
//
//
//import android.app.Activity;
//import android.location.Location;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.amap.api.location.AMapLocation;
//import com.amap.api.location.AMapLocationListener;
//import com.amap.api.location.LocationManagerProxy;
//import com.amap.api.location.LocationProviderProxy;
//import com.amap.api.maps.AMap;
//import com.amap.api.maps.CameraUpdateFactory;
//import com.amap.api.maps.LocationSource;
//import com.amap.api.maps.MapView;
//import com.lw.clouddelivery.R;
//import com.lw.clouddelivery.bean.CommonEntity;
//
//public class UpdateMapActivity extends Activity implements LocationSource,
//		AMapLocationListener, OnClickListener {
//
//	// 声明变量
//	private AMap aMap;
//	private MapView mapView;
//	private OnLocationChangedListener mListener;
//	private LocationManagerProxy mAMapLocationManager;
//	private Random mRandom = new Random();
//	private ImageView img_update_back;// 地图返回
//	private Button btn_updateMap;// 更新位置
//	private TextView txt_nowAddress;// 服务器地址 当前位置
//	private CommonEntity commonEntity = new CommonEntity();
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.activity_update_map);
//		mapView = (MapView) findViewById(R.id.map);
//		mapView.onCreate(savedInstanceState);// 必须要写
//		// aMap = mapView.getMap();
//		init();
//		initView();
//	}
//
//	// 地图初始化
//	private void init() {
//		if (aMap == null) {
//			aMap = mapView.getMap();
//			setUpMap();
//		}
//		// 初始化定位，只采用网络定位
//		mAMapLocationManager = LocationManagerProxy.getInstance(this);
//		mAMapLocationManager.setGpsEnable(false);
//		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
//		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
//		// 在定位结束后，在合适的生命周期调用destroy()方法
//		// 其中如果间隔时间为-1，则定位只定一次,
//		// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
//		mAMapLocationManager.requestLocationData(
//				LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);
//	}
//
//	// 初始化当前界面控件
//	private void initView() {
//		img_update_back = (ImageView) findViewById(R.id.img_update_back);// 返回键
//		btn_updateMap = (Button) findViewById(R.id.btn_updateMap);// 更新按钮
//		txt_nowAddress = (TextView) findViewById(R.id.txt_nowAddress);// 当前位置
//
//		btn_updateMap.setOnClickListener(this);
//		img_update_back.setOnClickListener(this);
//	}
//
//	private void setUpMap() {
//		aMap.setLocationSource(this);// 设置定位监听
//		aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
//		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//		// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
//		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
//		// 设置高德地图缩放级别 18
//		aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
//	}
//
//	/**
//	 * 获取更新地理位置的按键监听
//	 */
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.img_update_back:
//			finish();// 关闭当前页面
//			onDestroy();
//			break;
//		case R.id.btn_updateMap:
//			if (commonEntity.getAddress() != null) {
//				txt_nowAddress.setText(commonEntity.getAddress());
//			} else {
//				Toast.makeText(this, "获取地址失败，是稍后重试", 2000).show();
//			}
//			break;
//		default:
//			break;
//		}
//	}
//
//	/**
//	 * 方法必须重写
//	 */
//	@Override
//	protected void onResume() {
//		super.onResume();
//		mapView.onResume();
//	}
//
//	/**
//	 * 方法必须重写
//	 */
//	@Override
//	protected void onPause() {
//		super.onPause();
//		mapView.onPause();
//		deactivate();
//	}
//
//	/**
//	 * 方法必须重写
//	 */
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		mapView.onSaveInstanceState(outState);
//	}
//
//	/**
//	 * 方法必须重写
//	 */
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		mapView.onDestroy();
//	}
//
//	@Override
//	public void onLocationChanged(Location amapLocation) {
//	}
//
//	@Override
//	public void onProviderDisabled(String provider) {
//	}
//
//	@Override
//	public void onProviderEnabled(String provider) {
//	}
//
//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//	}
//
//	/**
//	 * 定位成功后回调函数
//	 */
//	@Override
//	public void onLocationChanged(AMapLocation amapLocation) {
//		if (mListener != null && amapLocation != null) {
//			if (amapLocation != null
//					&& amapLocation.getAMapException().getErrorCode() == 0) {
//				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
//				String getCityAddress = (amapLocation.getAddress());// 当前地址
//				commonEntity.setAddress(getCityAddress);
//			}
//		}
//	}
//
//	/**
//	 * 激活定位
//	 */
//	@Override
//	public void activate(OnLocationChangedListener listener) {
//		mListener = listener;
//		if (mAMapLocationManager == null) {
//			mAMapLocationManager = LocationManagerProxy.getInstance(this);
//			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
//			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
//			// 在定位结束后，在合适的生命周期调用destroy()方法
//			// 其中如果间隔时间为-1，则定位只定一次
//			// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
//			mAMapLocationManager.requestLocationData(
//					LocationProviderProxy.AMapNetwork, 30 * 1000, 10, this);
//		}
//	}
//
//	/**
//	 * 停止定位
//	 */
//	@Override
//	public void deactivate() {
//		mListener = null;
//		if (mAMapLocationManager != null) {
//			mAMapLocationManager.removeUpdates(this);
//			mAMapLocationManager.destroy();
//		}
//		mAMapLocationManager = null;
//	}
// }
