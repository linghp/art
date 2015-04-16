package com.shangxian.art;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ShopsModel;
import com.shangxian.art.constant.Constant;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

public class LocationActivity extends BaseActivity {
	private MapView mp_loc;
	private BaiduMap mMap;
	private LocationClient mLocClient;
	public boolean isFirstLoc = true;
	
	
	BitmapDescriptor bdShops = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_gcoding);//商铺地图图标
	
	BitmapDescriptor bdnA = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_marka);//A标签
	BitmapDescriptor bdnB = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markb);//B标签
	BitmapDescriptor bdnC = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markc);//C标签
	BitmapDescriptor bdnD = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markd);//D标签
	BitmapDescriptor bdnE = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_marke);//E标签
	BitmapDescriptor bdnF = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markf);//F标签
	BitmapDescriptor bdnG = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markg);//G标签
	BitmapDescriptor bdnH = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markh);//H标签
	BitmapDescriptor bdnI = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_marki);//I标签
	BitmapDescriptor bdnJ = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markj);//J标签
	
	List<BitmapDescriptor> bits = new ArrayList<BitmapDescriptor>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_location);
		mLoc = app.getMLoc();
		initData();
		initView();
		initListener();
	}

	private final static int MAP_SHOPS = Constant.MAP_SHOPS_2_LOC;
	private final static int MAP_NEARLY = Constant.MAP_NEARLY_LOC;
	private int curType;
	
	//TODO: --------------------------------------------------------------------------------------------
	private BDLocation mLoc;
	private Random mRandom = new Random();
	private LatLng mShopLatlng;
	private List<LatLng> mNearlyLatlngs = new ArrayList<LatLng>();
	
	private void initData() {
		Intent intent = getIntent();
		int type = intent.getIntExtra(Constant.INT_LOC_TOTYPE, Integer.MIN_VALUE);
		switch (type) {
		case MAP_SHOPS:
			curType = MAP_SHOPS;
			shops();
			break;
		case MAP_NEARLY:
			curType = MAP_NEARLY;
			nearly();
			break;
		case Integer.MIN_VALUE:
			myToast("请求错误");
			finish();
			break;
		}
	}

	private void nearly() {
		/*mNearlyLatlngs.add(new LatLng(mLoc.getLatitude() + mRandom.nextInt(20) / 1000000.0d, mLoc.getLongitude()  + mRandom.nextInt(20) / 1000000.0d));
		mNearlyLatlngs.add(new LatLng(mLoc.getLatitude() + mRandom.nextInt(20) / 1000000.0d, mLoc.getLongitude()  + mRandom.nextInt(20) / 1000000.0d));
		mNearlyLatlngs.add(new LatLng(mLoc.getLatitude() + mRandom.nextInt(20) / 1000000.0d, mLoc.getLongitude()  + mRandom.nextInt(20) / 1000000.0d));
		mNearlyLatlngs.add(new LatLng(mLoc.getLatitude() + mRandom.nextInt(20) / 1000000.0d, mLoc.getLongitude()  + mRandom.nextInt(20) / 1000000.0d));
		mNearlyLatlngs.add(new LatLng(mLoc.getLatitude() + mRandom.nextInt(20) / 1000000.0d, mLoc.getLongitude()  + mRandom.nextInt(20) / 1000000.0d));
		mNearlyLatlngs.add(new LatLng(mLoc.getLatitude() + mRandom.nextInt(20) / 1000000.0d, mLoc.getLongitude()  + mRandom.nextInt(20) / 1000000.0d));
		mNearlyLatlngs.add(new LatLng(mLoc.getLatitude() + mRandom.nextInt(20) / 1000000.0d, mLoc.getLongitude()  + mRandom.nextInt(20) / 1000000.0d));
		mNearlyLatlngs.add(new LatLng(mLoc.getLatitude() + mRandom.nextInt(20) / 1000000.0d, mLoc.getLongitude()  + mRandom.nextInt(20) / 1000000.0d));
		mNearlyLatlngs.add(new LatLng(mLoc.getLatitude() + mRandom.nextInt(20) / 1000000.0d, mLoc.getLongitude()  + mRandom.nextInt(20) / 1000000.0d));
		mNearlyLatlngs.add(new LatLng(mLoc.getLatitude() + mRandom.nextInt(20) / 1000000.0d, mLoc.getLongitude()  + mRandom.nextInt(20) / 1000000.0d));
		mNearlyLatlngs.add(new LatLng(mLoc.getLatitude() + mRandom.nextInt(20) / 1000000.0d, mLoc.getLongitude()  + mRandom.nextInt(20) / 1000000.0d));
		mNearlyLatlngs.add(new LatLng(mLoc.getLatitude() + mRandom.nextInt(20) / 1000000.0d, mLoc.getLongitude()  + mRandom.nextInt(20) / 1000000.0d));*/
		
		bits.add(bdnA);
		bits.add(bdnB);
		bits.add(bdnC);
		bits.add(bdnD);
		bits.add(bdnE);
		bits.add(bdnF);
		bits.add(bdnG);
		bits.add(bdnH);
		bits.add(bdnI);
		bits.add(bdnJ);
		int len = mRandom.nextInt(6) + 4;
		for (int i = 0; i < len; i++) {
			mNearlyLatlngs.add(new LatLng(mLoc.getLatitude() + (mRandom.nextInt(1000) - mRandom.nextInt(2000)) / 1000000.0d, mLoc.getLongitude()  + (mRandom.nextInt(1000) - mRandom.nextInt(2000)) / 1000000.0d));
		}
	}

	private void shops() {
		mShopLatlng = new LatLng(mLoc.getLatitude() + (mRandom.nextInt(1000) - mRandom.nextInt(2000)) / 1000000.0d, mLoc.getLongitude()  + (mRandom.nextInt(1000) - mRandom.nextInt(2000)) / 1000000.0d);
	}

	private void initView() {
		mp_loc = (MapView) findViewById(R.id.locb_bm_loc);
		mp_loc.showZoomControls(false);
		mp_loc.showScaleControl(false);
		mMap = mp_loc.getMap();
		upDataMap();
	}

	
	private void upDataMap() {
		MapStatus u = new MapStatus.Builder(mMap.getMapStatus()).target(getLat()).zoom(16f).build();
		MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(u);
		mMap.setMapStatus(msu);
		// 开启定位图层
		if (curType == MAP_SHOPS) {
			mMap.setMyLocationEnabled(false);
		} else if (curType == MAP_NEARLY) {
			mMap.setMyLocationEnabled(false);
		}
		//mMap.set
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(2000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		initOverlay();
	}	

	/**
	 * 定位SDK监听函数
	 */
	public MyLocationListenner myListener = new MyLocationListenner();
	private Marker mMarkerShop;
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mp_loc == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				/*MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mMap.animateMapStatus(u);*/
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	private void initOverlay() {
		switch (curType) {
		case MAP_SHOPS:
			overShop();
			break;
		case MAP_NEARLY:
			overNearly();
			break;
		}
	}
	
	List<Marker> markers;
	private void overNearly() {
		if (mNearlyLatlngs.size() > 0) {
			markers = new ArrayList<Marker>();
			for (int i = 0; i < mNearlyLatlngs.size(); i++) {
				OverlayOptions ooA = new MarkerOptions().position(mNearlyLatlngs.get(i)).icon(bits.get(i))
						.zIndex(mNearlyLatlngs.size() - i);
				markers.add((Marker) (mMap.addOverlay(ooA)));
			}
			mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
				@Override
				public boolean onMarkerClick(Marker marker) {
					int mark = markers.indexOf(marker);
					myToast("点击了" + mark);
					return true;
				}
			});
		}
	}

	private void overShop() {
		OverlayOptions ooA = new MarkerOptions().position(getLat()).icon(bdShops)
				.zIndex(9).draggable(true);
		mMarkerShop = (Marker) (mMap.addOverlay(ooA));
		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {	
			@Override
			public boolean onMarkerClick(Marker mark) {
				if (mark == mMarkerShop) {
					MapStatusUpdate msu = MapStatusUpdateFactory.zoomIn();
					return true;
				}
				return false;
			}
		});
	}

	private LatLng getLat() {
		if (curType == MAP_SHOPS) {
			return mShopLatlng;
		} else if (curType == MAP_NEARLY) {
			double lat = 0;
			double lon = 0;
			for (int i = 0; i < mNearlyLatlngs.size(); i++) {
				lat += mNearlyLatlngs.get(i).latitude;
				lon += mNearlyLatlngs.get(i).longitude;
			}
			return new LatLng(lat/mNearlyLatlngs.size(), lon/mNearlyLatlngs.size());
		}
		return null;
	}

	private void initListener() {

	}

	@Override
	protected void onPause() {
		// MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		mp_loc.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		mp_loc.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mp_loc.onDestroy();
		super.onDestroy();
		bdShops.recycle();
		bdnA.recycle();
		bdnB.recycle();
		bdnC.recycle();
		bdnD.recycle();
		bdnE.recycle();
		bdnF.recycle();
		bdnG.recycle();
		bdnH.recycle();
		bdnI.recycle();
		bdnJ.recycle();
	}
}
