package com.shangxian.art.base;

import android.app.Application;
import android.util.Log;
import android.view.LayoutInflater;
import cn.jpush.android.api.JPushInterface;

import com.ab.image.AbImageLoader;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.shangxian.art.R;
import com.shangxian.art.constant.Global;
import com.shangxian.art.net.BaseServer;


public class MyApplication extends Application {

	private static MyApplication mInstance ;
	private AbImageLoader loader;
	
	private LocationClient mLocationClient;
	private MyLocationListener mMyLocationListener;
	private GeofenceClient mGeofenceClient;
	private BDLocation mloc;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		Global.mInflater = LayoutInflater.from(this);
		Global.mContext = this;

//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//				getApplicationContext())
//				.threadPriority(Thread.NORM_PRIORITY - 2)
//				.denyCacheImageMultipleSizesInMemory()
//				.discCacheFileNameGenerator(new Md5FileNameGenerator())
//				.tasksProcessingOrder(QueueProcessingType.FIFO)
//				.writeDebugLogs() // Remove for release app
//				.build();
		// Initialize ImageLoader with configuration.
//		ImageLoader.getInstance().init(config);
		BaseServer.toRegistContext(mInstance);
		SDKInitializer.initialize(getApplicationContext());
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());
		mLocationClient.start();
		loader = AbImageLoader.newInstance(this);
		loader.setEmptyImage(R.drawable.image_empty);
		loader.setErrorImage(R.drawable.image_error);
		loader.setLoadingImage(R.drawable.image_loading);
		//推送
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
	}
	
	public BDLocation getMLoc(){
		return mloc;
	}
	
	/**
	 * 实时监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			mloc = location;
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
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			Log.i("BaiduLocationApiDem", sb.toString());
		}

	}

	public static MyApplication getInstance() {
		return mInstance;
	}

	public AbImageLoader getLoader() {
		return loader;
	}

	public void setLoader(AbImageLoader loader) {
		this.loader = loader;
	}
	
	
}
