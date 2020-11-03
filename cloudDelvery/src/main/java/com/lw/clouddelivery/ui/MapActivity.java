package com.lw.clouddelivery.ui;

import org.jivesoftware.smack.util.StringUtils;

import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.base.BaseActivity;
import com.base.util.LogWrapper;
import com.base.util.StringUtil;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Order;
import com.lw.clouddelivery.ui.xxxActivity.MyLocationListenner;
import com.lw.clouddelivery.util.MySPTool;
import com.lw.clouddelivery.util.gps.Gps;
import com.lw.clouddelivery.util.gps.PositionUtil;

/**
 * "查看地图"界面
 * @author leon
 *
 */
public class MapActivity extends BaseActivity implements BaiduMap.OnMapClickListener,
	OnGetRoutePlanResultListener{

	//地图相关
		MapView mMapView = null;    // 地图View
		BaiduMap mBaidumap = null;
		LocationClient mLocClient;
		public MyLocationListenner myListener = new MyLocationListenner();
		boolean isFirstLoc = true;// 是否首次定位
		
		private Order mOrder;
		// 初始化全局 bitmap 信息，不用时及时 recycle
		BitmapDescriptor bdS = BitmapDescriptorFactory.fromResource(R.drawable.tk_03); //起点
		BitmapDescriptor bdD = BitmapDescriptorFactory.fromResource(R.drawable.tk_06); //终点
		BitmapDescriptor bdWo = BitmapDescriptorFactory.fromResource(R.drawable.wo); //终点
		RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用
		 PlanNode stNode,enNode,meNode;
		 OverlayManager routeOverlay = null;
		 RouteLine route = null;
		 boolean useDefaultIcon = true;
		 BitmapDescriptor mCurrentMarker;
		 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_map);
		getTitleBar().initTitleText("查看地图");
		mMapView = (MapView) findViewById(R.id.map);
		mBaidumap = mMapView.getMap();
        //地图点击事件处理
        mBaidumap.setOnMapClickListener(this);
        
    	//地图overlay
		if(getIntent().getExtras() != null) {
			mOrder = (Order) getIntent().getExtras().getSerializable("order");
		}
		
		Gps gps = PositionUtil.gcj02_To_Bd09(StringUtil.parseDouble(mOrder.getLat()), StringUtil.parseDouble(mOrder.getLng()) );
		Gps gpsD = PositionUtil.gcj02_To_Bd09(StringUtil.parseDouble(mOrder.getDLat()), StringUtil.parseDouble(mOrder.getDLng()) );
		Log.e("ccqx", mOrder.toString());
		LatLng llS = new LatLng(gps.getWgLat() ,gps.getWgLon());
		LatLng llD = new LatLng(gpsD.getWgLat() ,gpsD.getWgLon());
//		LatLng llS = new LatLng(Double.valueOf(mOrder.getLat()) ,Double.valueOf(mOrder.getLng()));
//		LatLng llD = new LatLng(Double.valueOf(mOrder.getDLat()) ,Double.valueOf(mOrder.getDLng()));
		LatLng IIMe = new LatLng(Double.valueOf(MySPTool.getLat(this)) ,Double.valueOf(MySPTool.getLon(this)));
		//地图线路规划
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
		stNode = PlanNode.withLocation(llS);
		enNode = PlanNode.withLocation(llD);
        search(1);
        
        //地图定位
		mCurrentMarker = BitmapDescriptorFactory
				.fromResource(R.drawable.wo);
        mBaidumap.setMyLocationConfigeration(new MyLocationConfiguration(
        		LocationMode.NORMAL, true, mCurrentMarker));
		// 开启定位图层
		mBaidumap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
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
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(0).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaidumap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaidumap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		 if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
	            LogWrapper.e(xxxActivity.class, "抱歉，未找到结果");
	        }
	        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
	            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
	            //result.getSuggestAddrInfo()
	            return;
	        }
	        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
	            route = result.getRouteLines().get(0);
	            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
	            routeOverlay = overlay;
	            mBaidumap.setOnMarkerClickListener(overlay);
	            overlay.setData(result.getRouteLines().get(0));
	            overlay.addToMap();
	            overlay.zoomToSpan();
	        }
	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {

        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
        	LogWrapper.e(xxxActivity.class, "抱歉，未找到结果");
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            result.getSuggestAddrInfo();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            route = result.getRouteLines().get(0);
            TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaidumap);
            mBaidumap.setOnMarkerClickListener(overlay);
            routeOverlay = overlay;
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }
	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		 if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			 LogWrapper.e(xxxActivity.class, "抱歉，未找到结果");
	        }
	        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
	            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
	            //result.getSuggestAddrInfo()
	            return;
	        }
	        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
	            route = result.getRouteLines().get(0);
	            WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaidumap);
	            mBaidumap.setOnMarkerClickListener(overlay);
	            routeOverlay = overlay;
	            overlay.setData(result.getRouteLines().get(0));
	            overlay.addToMap();
	            overlay.zoomToSpan();
	        }
	}

	@Override
	public void onMapClick(LatLng arg0) {
	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		return false;
	}
	
	 public void search(int type) {
 		route = null;
     mBaidumap.clear();
 	 // 实际使用中请对起点终点城市进行正确的设定
    switch(type) {
    case 1:
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));
 	   break;
    case 2:
        mSearch.transitSearch((new TransitRoutePlanOption())
                .from(stNode)
                .city("上海")
                .to(enNode));
 	   break;
    case 3:
        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(stNode)
                .to(enNode));
 	   break;
    case 4:
 	   break;
    }
 }
	 
		
	    private class MyTransitRouteOverlay extends TransitRouteOverlay {

	        public MyTransitRouteOverlay(BaiduMap baiduMap) {
	            super(baiduMap);
	        }

	        @Override
	        public BitmapDescriptor getStartMarker() {
	            if (useDefaultIcon) {
	                return BitmapDescriptorFactory.fromResource(R.drawable.ji);
	            }
	            return null;
	        }

	        @Override
	        public BitmapDescriptor getTerminalMarker() {
	            if (useDefaultIcon) {
	                return BitmapDescriptorFactory.fromResource(R.drawable.shou);
	            }
	            return null;
	        }
	    }
	  //定制RouteOverly
	    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

	        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
	            super(baiduMap);
	        }

	        @Override
	        public BitmapDescriptor getStartMarker() {
	            if (useDefaultIcon) {
	                return BitmapDescriptorFactory.fromResource(R.drawable.ji);
	            }
	            return null;
	        }

	        @Override
	        public BitmapDescriptor getTerminalMarker() {
	            if (useDefaultIcon) {
	                return BitmapDescriptorFactory.fromResource(R.drawable.shou);
	            }
	            return null;
	        }
	    }
	    
	    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

	        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
	            super(baiduMap);
	        }

	        @Override
	        public BitmapDescriptor getStartMarker() {
	            if (useDefaultIcon) {
	                return BitmapDescriptorFactory.fromResource(R.drawable.ji);
	            }
	            return null;
	        }

	        @Override
	        public BitmapDescriptor getTerminalMarker() {
	            if (useDefaultIcon) {
	                return BitmapDescriptorFactory.fromResource(R.drawable.shou);
	            }
	            return null;
	        }
	    }
}
