package com.lw.clouddelivery.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.base.BaseActivity;
import com.base.util.LogWrapper;
import com.lw.clouddelivery.R;

public class MapDownloadActivity extends BaseActivity 
	implements OnClickListener,MKOfflineMapListener,OnItemClickListener  {

	private TextView downloadTitleLeft,downloadTitleRight;
	private TextView subtitle;
	private ListView download_city_lv,downloading_lv;
	
	private int cid;
	
	private MKOfflineMap mOffline = null;
	private ArrayList<MKOLSearchRecord> records1;
	/**
	 * 已下载的离线地图信息列表
	 */
	private ArrayList<MKOLUpdateElement> localMapList = null;
	private LocalMapAdapter lAdapter = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_map_download);
		getTitleBar().initTitleText("城市离线下载");
		mOffline = new MKOfflineMap();
		mOffline.init(this);
		findViews();
		fillData();
		
		
	}

	@Override
	public void fillData() {
		super.fillData();
		ArrayList<String> hotCities = new ArrayList<String>();
		// 获取热闹城市列表
		records1 = mOffline.getHotCityList();
		if (records1 != null) {
			for (MKOLSearchRecord r : records1) {
				hotCities.add(r.cityName + "(" + r.cityID + ")" + "   --"
						+ this.formatDataSize(r.size));
				LogWrapper.e(MapDownloadActivity.class, r.cityName + "|" + r.cityID);
			}
		}
		ListAdapter hAdapter = (ListAdapter) new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, hotCities);
		download_city_lv.setAdapter(hAdapter);
		download_city_lv.setOnItemClickListener(this);
		
		// 获取已下过的离线地图信息
		localMapList = mOffline.getAllUpdateInfo();
		if (localMapList == null) {
			localMapList = new ArrayList<MKOLUpdateElement>();
		}

		lAdapter = new LocalMapAdapter();
		downloading_lv.setAdapter(lAdapter);
	}
	
	/**
	 * 开始下载
	 * 
	 * @param view
	 */
	public void start(int cityid) {
		this.cid = cityid;
		mOffline.start(cityid);
		Toast.makeText(this, "开始下载离线地图. cityid: " + cityid, Toast.LENGTH_SHORT)
				.show();
		updateView();
	}
	
	

	@Override
	public void findViews() {
		super.findViews();
		downloadTitleLeft = (TextView)this.findViewById(R.id.map_download_titleLeft);
		downloadTitleRight = (TextView)this.findViewById(R.id.map_download_titleRight);
		downloadTitleLeft.setOnClickListener(this);
		downloadTitleRight.setOnClickListener(this);
		
		download_city_lv = (ListView)this.findViewById(R.id.download_city_lv);
		downloading_lv = (ListView)this.findViewById(R.id.downloading_lv);
		
		subtitle = (TextView)this.findViewById(R.id.map_download_subtitle);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.map_download_titleLeft:
			downloadTitleLeft.setTextColor(getResources().getColor(R.color.base_titlebar_bg));
			downloadTitleRight.setTextColor(getResources().getColor(R.color.black));
			downloading_lv.setVisibility(View.GONE);
			download_city_lv.setVisibility(View.VISIBLE);
			subtitle.setText("热门城市");
			break;
		case R.id.map_download_titleRight:
			downloadTitleLeft.setTextColor(getResources().getColor(R.color.black));
			downloadTitleRight.setTextColor(getResources().getColor(R.color.base_titlebar_bg));
			downloading_lv.setVisibility(View.VISIBLE);
			download_city_lv.setVisibility(View.GONE);
			subtitle.setText("正在下载");
			break;
		}
	}

	public String formatDataSize(int size) {
		String ret = "";
		if (size < (1024 * 1024)) {
			ret = String.format("%dK", size / 1024);
		} else {
			ret = String.format("%.1fM", size / (1024 * 1024.0));
		}
		return ret;
	}
	@Override
	public void onGetOfflineMapState(int type, int state) {
		switch (type) {
		case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
			MKOLUpdateElement update = mOffline.getUpdateInfo(state);
			// 处理下载进度更新提示
			if (update != null) {
//				downloadTitleRight.setText("正在下载:" + String.format("%s : %d%%", update.cityName,
//						update.ratio));
				updateView();
			}
		}
			break;
		case MKOfflineMap.TYPE_NEW_OFFLINE:
			// 有新离线地图安装
			Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
			break;
		case MKOfflineMap.TYPE_VER_UPDATE:
			// 版本更新提示
			// MKOLUpdateElement e = mOffline.getUpdateInfo(state);

			break;
		}
	}
	
	/**
	 * 离线地图管理列表适配器
	 */
	public class LocalMapAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return localMapList.size();
		}

		@Override
		public Object getItem(int index) {
			return localMapList.get(index);
		}

		@Override
		public long getItemId(int index) {
			return index;
		}

		@Override
		public View getView(int index, View view, ViewGroup arg2) {
			MKOLUpdateElement e = (MKOLUpdateElement) getItem(index);
			view = View.inflate(MapDownloadActivity.this,
					R.layout.item_map_downloading, null);
			initViewItem(view, e);
			return view;
		}

		void initViewItem(View view, final MKOLUpdateElement e) {
			TextView title = (TextView) view.findViewById(R.id.item_download_cityname);
			TextView ratio = (TextView) view.findViewById(R.id.item_download_progress_tv);
			ProgressBar pb = (ProgressBar)view.findViewById(R.id.item_download_progressbar);
			if(e.ratio == 100) {
				ratio.setText("已完成");
			} else {
				ratio.setText("正在下载" + e.ratio + "%");
			}
			title.setText(e.cityName);
			pb.setProgress(e.ratio);
		}
	}
	
	/**
	 * 更新状态显示
	 */
	public void updateView() {
		localMapList = mOffline.getAllUpdateInfo();
		if (localMapList == null) {
			localMapList = new ArrayList<MKOLUpdateElement>();
		}
		lAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		start(records1.get(position).cityID);
	}
	
	
	@Override
	protected void onPause() {
		MKOLUpdateElement temp = mOffline.getUpdateInfo(cid);
		if (temp != null && temp.status == MKOLUpdateElement.DOWNLOADING) {
			mOffline.pause(cid);
		}
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		/**
		 * 退出时，销毁离线地图模块
		 */
		mOffline.destroy();
		super.onDestroy();
	}
}
