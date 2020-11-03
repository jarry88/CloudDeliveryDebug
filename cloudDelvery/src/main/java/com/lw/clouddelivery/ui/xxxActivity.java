package com.lw.clouddelivery.ui;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
//import com.google.android.material.snackbar.Snackbar;
//import androidx.core.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
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
import com.base.BaseSlidingActivity;
import com.base.util.BaseSPTool;
import com.base.util.LogWrapper;
import com.base.util.MyToast;
import com.base.util.SoundPlayUtil;
import com.base.util.StringUtil;
import com.base.util.VibratorUtil;
import com.base.widget.LoadingDialog;
import com.google.android.material.snackbar.Snackbar;
import com.lw.clouddelivery.CloudDeliveryAPP;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Order;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.ui.fragment.SlidingMenuFragment;
import com.lw.clouddelivery.util.CommTool;
import com.lw.clouddelivery.util.DBUtil;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.JsonBase;
import com.lw.clouddelivery.util.MySPTool;
import com.lw.clouddelivery.util.PermissionUtil;
import com.lw.clouddelivery.util.UIHelper;
import com.lw.clouddelivery.util.gps.Gps;
import com.lw.clouddelivery.util.gps.PositionUtil;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;

public class xxxActivity extends BaseSlidingActivity  implements BaiduMap.OnMapClickListener,
	OnGetRoutePlanResultListener {

	private String TAG = "xxxActivity";
	
	private SlidingMenuFragment slidingMenuFragment;

	private SlidingUpPanelLayout slidingPanel;
	private ImageView crossIV;
	private LinearLayout iv_qiangdan;
	private ImageView circleYellowIV; //黄色圆形按钮
	
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
	 
	 private TextView xxx_countdownTV; //倒计时组件
	 private int countType; //倒计时类型,1新订单，2待抢单，3已被抢
	 public static final int CT_NEW_ORDER = 1;
	 public static final int CT_ORDER_WAITING = 2;
	 public static final int CT_ORDER_CONSUMED = 3; //单已被抢
	 public static final int CT_ORDER_CANCLED = 4; //单已取消
	 private int countdown = 7;
	 private TextView xxx_price_tv,xxx_distance_tv,xxx_beizhu_tv
	 			,xxx_start_tv,xxx_end_tv,xxx_value_tv,xxx_yuyue_tv;
	 private TextView xxx_orderno_tv,xxx_ordername_tv,xxx_payment_tv;
	 private ImageView xxx_paystate_tv; //已支付，未支付
	 private SoundPlayUtil soundPlayUtil;
	 private VibratorUtil vibratorUtil;
	 private boolean checkMap;//查看地图
	 private boolean isNewOrder; //是否是新订单,由数据轮询得到的
	 private ImageView order_state_red_iv; //单已被抢，单已完成图标
	 
	 WakeLock mWakelock;
	 
	 private static final String[] PERMISSIONS_CONTACT = {Manifest.permission.ACCESS_COARSE_LOCATION,
	        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE};

	protected static final int REQUEST_CONTACTS = 0;

	 
	 
	@TargetApi(23)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xxx);
		
		final Window win = getWindow();
		 win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
		 | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		 win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
		 | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		 
		LogWrapper.e(xxxActivity.class, "xxxActivity.onCreate()");
		
		checkMap = getIntent().getBooleanExtra("checkMap", false);
		isNewOrder = true;
				//getIntent().getBooleanExtra("isNewOrder", true);
		View v = LayoutInflater.from(this).inflate(R.layout.activity_xxx, null);
		//slidinguppanel
		setBehindContentView(R.layout.userinfo_layoutframe);
		slidingMenuFragment = new SlidingMenuFragment();
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().replace(R.id.userinfo_layoutframe, slidingMenuFragment).commit();
		}
		
		slidingPanel = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
		slidingPanel.setDragView(findViewById(R.id.dragView));

//		slidingPanel.setEnableDragViewTouchEvents(false);
		slidingPanel.setMotionEventSplittingEnabled(true);
		slidingPanel.setPanelHeight(CommTool.dpToPx(xxxActivity.this, 50));
		slidingPanel.setOverlayed(false);
		mMapView = (MapView) findViewById(R.id.map);
		
		mBaidumap = mMapView.getMap();
        //地图点击事件处理
        mBaidumap.setOnMapClickListener(this);
        slidingPanel.setPanelSlideListener(new PanelSlideListener() {
			
			@Override
			public void onPanelSlide(View panel, float slideOffset) {
			}
			
			@Override
			public void onPanelHidden(View panel) {
			}
			
			@Override
			public void onPanelExpanded(View panel) {
				((ImageView)xxxActivity.this.findViewById(R.id.loc_dialog_arrow)).setImageResource(R.drawable.arrows_down);
				((TextView)xxxActivity.this.findViewById(R.id.loc_dialog_text)).setText("向下滑动查看订单信息");
				
			}
			
			@Override
			public void onPanelCollapsed(View panel) {
				((ImageView)xxxActivity.this.findViewById(R.id.loc_dialog_arrow)).setImageResource(R.drawable.arrows_up);
				((TextView)xxxActivity.this.findViewById(R.id.loc_dialog_text)).setText("向上滑动查看地图信息");
			}
			
			@Override
			public void onPanelAnchored(View panel) {
			}
		});
        crossIV = (ImageView)this.findViewById(R.id.qiangdan_icon_close);
        crossIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*DCUtil.qiangdanClose(xxxActivity.this,pd,String.valueOf(mOrder.getId()),new Handler() {
							@Override
							public void handleMessage(Message msg) {
								super.handleMessage(msg);
								
							}
				});*/
				CloudDeliveryAPP.tempList.add(mOrder.getId());
				
				MySPTool.putLong(xxxActivity.this, INI.SP.LATEST_ORDER_TIME, mOrder.getCreatetime());
				finish();
			}
		});
        iv_qiangdan = (LinearLayout)this.findViewById(R.id.qiangdan_btn_layout);
        iv_qiangdan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(circleYellowIV.isEnabled()) {
					if(MySPTool.getBoolean(xxxActivity.this, INI.SP.SETTING_CONFIEM, false)) { //显示确认按钮
						AlertDialog.Builder builder = new AlertDialog.Builder(xxxActivity.this);
						builder.setTitle("提示")
						.setMessage("确认要进行抢单动作吗?")
						.setPositiveButton("是的", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								qiangdan();
							}
						}).setNegativeButton("不了",new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						}).show();
					} else {
						qiangdan();
					}
				}
			}
		});
        
		//地图overlay
		if(getIntent().getExtras() != null) {
			mOrder = (Order) getIntent().getExtras().getSerializable("order");
		}
		Gps gps = PositionUtil.gcj02_To_Bd09(StringUtil.parseDouble(mOrder.getLat()), StringUtil.parseDouble(mOrder.getLng()) );
		Gps gpsD = PositionUtil.gcj02_To_Bd09(StringUtil.parseDouble(mOrder.getDLat()), StringUtil.parseDouble(mOrder.getDLng()) );
		
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
        

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(xxxActivity.this)) {
				if (Build.VERSION.SDK_INT>=23){
				    showContacts(v);
				}else{
			       initLocation();
				   mLocClient.start();
				}
            }
            else {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
		}else{
			initLocation();
		   mLocClient.start();
		}
        findViews();
        fillData();

        if(isNewOrder) {
        		countType = CT_NEW_ORDER;
        } else {
        		switch (Integer.valueOf(mOrder.getOrderstatusId())) {
				case 1:
					countType = CT_ORDER_WAITING;
					break;
				case 2:
				case 3:
				case 4:
					countType = CT_ORDER_CONSUMED;
					break;
				case 5:
				case 6:
					countType = CT_ORDER_CANCLED;
					break;
				default:
					break;
				}
        }
        refreshCountDown();
        timer.schedule(task, 1000, 1000);       // timeTask  
        
        
        if(MySPTool.getBoolean(this, INI.SP.SETTING_VOICE, true)  && mOrder.getOrderstatusId().equals("1")) {
            soundPlayUtil = new SoundPlayUtil(xxxActivity.this, R.raw.lingsheng);
            soundPlayUtil.playSound();
        }

        if(MySPTool.getBoolean(this, INI.SP.SETTING_VERBOSE, true) && mOrder.getOrderstatusId().equals("1")) {
            vibratorUtil = new VibratorUtil(xxxActivity.this);
            vibratorUtil.start(new long[]{1000,1000,1000,1000});
        }
        
        if(checkMap) {
        		slidingPanel.expandPanel();
        		findViewById(R.id.dialog_order_info_layout).setVisibility(View.INVISIBLE);
        }
        
        
	}
	
	private void initLocation() {
		 //地图定位
		mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.wo);
        mBaidumap.setMyLocationConfigeration(new MyLocationConfiguration(LocationMode.NORMAL, true, mCurrentMarker));
		// 开启定位图层
		mBaidumap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(10000);
		mLocClient.setLocOption(option);
	}
	
    private void qiangdan() {
	    	DCUtil.qiangdan(xxxActivity.this, MySPTool.getUid(xxxActivity.this), mOrder.getId(), new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					if(msg.arg1 == 1) {
						new Thread() {
							@Override
							public void run() {
								super.run();
								//保存订单信息到数据库
								mOrder.setQiangdanTime(System.currentTimeMillis()); //添加抢单时间
								DBUtil.getInstance(xxxActivity.this).saveOrder(mOrder);
								
								DCUtil.qiangdanClose(xxxActivity.this,pd,String.valueOf(mOrder.getId()),new Handler() {
									@Override
									public void handleMessage(Message msg) {
										super.handleMessage(msg);
										
									}
						});
							}
						}.start();
						UIHelper.showAfterQiangdan(xxxActivity.this, mOrder);
						xxxActivity.this.finish();   
					} else {
						JsonBase jsonbase = (JsonBase) msg.obj;
						if(jsonbase.getCode().equals("20")) { //单已被抢
							order_state_red_iv.setImageResource(R.drawable.tkq_077);
			        			order_state_red_iv.setVisibility(View.VISIBLE);
			        			circleYellowIV.setEnabled(false);
						}else if(jsonbase.getCode().equals("60")) { //接单已超过最大量
							order_state_red_iv.setImageResource(R.drawable.tkq_060);
		        			order_state_red_iv.setVisibility(View.VISIBLE);
		        			circleYellowIV.setEnabled(false);
						}else if(jsonbase.getCode().equals("40")) { //考试不通过
							order_state_red_iv.setImageResource(R.drawable.tkq_030);
		        			order_state_red_iv.setVisibility(View.VISIBLE);
		        			circleYellowIV.setEnabled(false);
						}else if(jsonbase.getCode().equals("30")) { //资料审核不通过
							order_state_red_iv.setImageResource(R.drawable.tkq_040);
		        			order_state_red_iv.setVisibility(View.VISIBLE);
		        			circleYellowIV.setEnabled(false);
						}else if(jsonbase.getCode().equals("50")) { //资料审核不通过
							order_state_red_iv.setImageResource(R.drawable.tkq_050);
		        			order_state_red_iv.setVisibility(View.VISIBLE);
		        			circleYellowIV.setEnabled(false);
						}
					}
				}
			});	
    }
    
	/**
	 * 刷新倒计时状态
	 */
	private void refreshCountDown() {
		 switch(countType) {
	        case CT_NEW_ORDER:
	        	countdown = 7;
	        	circleYellowIV.setEnabled(false);
	        	break;
	        case CT_ORDER_WAITING:
	        	countdown = 60;
	        	circleYellowIV.setEnabled(true);
	        	break;
	        case CT_ORDER_CANCLED:
	        	circleYellowIV.setEnabled(false);
	        	countdown = 60;
	        	break;
	        case CT_ORDER_CONSUMED:
	        	circleYellowIV.setEnabled(false);
	        	countdown = 60;
	        	break;
	        }
	        xxx_countdownTV.setText("" + countdown);
	}

	  @Override
	  public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
	      // return true;//返回真表示返回键被屏蔽掉
			/*DCUtil.qiangdanClose(xxxActivity.this,pd,String.valueOf(mOrder.getId()),new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					
				}
			});*/
	    	
	    	CloudDeliveryAPP.tempList.add(mOrder.getId());
			MySPTool.putLong(xxxActivity.this, INI.SP.LATEST_ORDER_TIME, mOrder.getCreatetime());
			finish();
	    }
	    return super.onKeyDown(keyCode, event);
	  }
	
    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
        mWakelock.release();
    }
    
    @Override
	public void findViews() {
		super.findViews();
		 xxx_price_tv = (TextView)this.findViewById(R.id.xxx_price_tv);
		 xxx_distance_tv = (TextView)this.findViewById(R.id.xxx_distance_tv);
		 xxx_beizhu_tv = (TextView)this.findViewById(R.id.xxx_beizhu_tv);
		 xxx_yuyue_tv = (TextView)this.findViewById(R.id.xxx_yuyue_tv);
		 xxx_value_tv = (TextView)this.findViewById(R.id.xxx_value_tv);
		xxx_start_tv = (TextView)this.findViewById(R.id.xxx_start_tv);
		xxx_end_tv = (TextView)this.findViewById(R.id.xxx_end_tv);
		xxx_countdownTV = (TextView)this.findViewById(R.id.xxx_countdownTV);
		circleYellowIV = (ImageView)this.findViewById(R.id.xxx_circle_yellow);
		xxx_paystate_tv = (ImageView)this.findViewById(R.id.xxx_paystate_tv);
		order_state_red_iv = (ImageView)this.findViewById(R.id.order_state_red_iv);
		
		xxx_orderno_tv = (TextView)this.findViewById(R.id.xxx_orderno_tv);
		xxx_ordername_tv = (TextView)this.findViewById(R.id.xxx_ordername_tv);
		xxx_payment_tv = (TextView)this.findViewById(R.id.xxx_payment_tv);
	}

    
	@Override
	public void fillData() {
		super.fillData();
		xxx_price_tv.setText(mOrder.getTotalamount() + "");
		DecimalFormat    df   = new DecimalFormat("######0.00");  
		String juniDistance = df.format((Double.valueOf(mOrder.getShopjl()) / 1000.0));
		xxx_distance_tv.setText(mOrder.getJuli() + "公里/" + mOrder.getWeight() + "公斤/距你" + juniDistance + "公里");
		if(mOrder.getReamaker() != null && !mOrder.getReamaker().equals("")) {
			xxx_beizhu_tv.setVisibility(View.VISIBLE);
			xxx_beizhu_tv.setText("备注：" + mOrder.getReamaker());
		}
		if(mOrder.getJprice() != null && !mOrder.getJprice().equals("")) {
			xxx_value_tv.setVisibility(View.VISIBLE);
			xxx_value_tv.setText("价值申明：" + mOrder.getJprice());
		}
		if(mOrder.getYtime() != null && !mOrder.getYtime().equals("")) {
			xxx_yuyue_tv.setVisibility(View.VISIBLE);
			xxx_yuyue_tv.setText("预约时间：" + mOrder.getYtime());
		}
		xxx_start_tv.setText(mOrder.getDeli_address());
		xxx_end_tv.setText(mOrder.getSaddress());
		if(mOrder.getPayState().equals("已支付")) {
			xxx_paystate_tv.setVisibility(View.VISIBLE);
		} else {
			xxx_paystate_tv.setVisibility(View.GONE);
		}
		xxx_orderno_tv.setText("单号：" + mOrder.getOrderno() + "");
		xxx_ordername_tv.setText("物品：" + mOrder.getOrdername());
		xxx_payment_tv.setText("付款方式：" + mOrder.getPaytype());
		
		if(mOrder.getOrderstatusId().equals(INI.STATE.ORDER_CANCEL) || mOrder.getOrderstatusId().equals(INI.STATE.ORDER_CANCELLING)) {
        	order_state_red_iv.setImageResource(R.drawable.tkq_07);
        	order_state_red_iv.setVisibility(View.VISIBLE);
		} else if(mOrder.getOrderstatusId().equals(INI.STATE.ORDER_DELIVERY) 
				|| mOrder.getOrderstatusId().equals(INI.STATE.ORDER_TAKING) || mOrder.getOrderstatusId().equals(INI.STATE.ORDER_FINISHED)) {
			order_state_red_iv.setImageResource(R.drawable.tkq_077);
        		order_state_red_iv.setVisibility(View.VISIBLE);
		}
	}

	@Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.SCREEN_DIM_WAKE_LOCK, "PollingService");
         mWakelock.acquire();
    }
    @Override
    protected void onDestroy() {
    	if(mLocClient!=null){
			// 退出时销毁定位
			mLocClient.stop();
			// 关闭定位图层
			mBaidumap.setMyLocationEnabled(false);
    	}
    		mSearch.destroy();
        mMapView.onDestroy();
        super.onDestroy();
        bdD.recycle();
        bdS.recycle();
        bdWo.recycle();
        
        if(soundPlayUtil != null) {
            soundPlayUtil.stopSound();
        }
        if(vibratorUtil != null) {
            vibratorUtil.stop();
        }
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


	Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			runOnUiThread(new Runnable() { // UI thread
				@Override
				public void run() {
					if(countdown > 0) {
						countdown--;
						xxx_countdownTV.setText("" + countdown);
					} else {
						if(countType == CT_NEW_ORDER) {
							countType = CT_ORDER_WAITING;
							refreshCountDown();
						} else {
							timer.cancel();
							timer.purge();
							xxxActivity.this.finish();
						}
					}
				}
			});
		}
	};
	    
	
	@Override
	public void onMapClick(LatLng arg0) {
		mBaidumap.hideInfoWindow();
	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		return false;
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
			   mLocClient.start();

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
//	            || ActivityCompat.shouldShowRequestPermissionRationale(this,
//	            Manifest.permission.READ_PHONE_STATE)
	            ) {

	        // Provide an additional rationale to the user if the permission was not granted
	        // and the user would benefit from additional context for the use of the permission.
	        // For example, if the request has been denied previously.
	        Log.i(TAG,"Displaying contacts permission rationale to provide additional context.");
	        // Display a SnackBar with an explanation and a button to trigger the request.
	        Snackbar.make( v, "permission_contacts_rationale",Snackbar.LENGTH_INDEFINITE).setAction("ok", new View.OnClickListener() {
	                    @Override
	                    public void onClick(View view) {
	                        ActivityCompat .requestPermissions(xxxActivity.this, PERMISSIONS_CONTACT,  REQUEST_CONTACTS);
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
				   mLocClient.start();
	        } else {

	        }


	    }else{
	    	super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	    }
	}
}
