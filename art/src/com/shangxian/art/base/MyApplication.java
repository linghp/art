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
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.shangxian.art.R;
import com.shangxian.art.alipays.AliPayBase;
import com.shangxian.art.constant.Global;
import com.shangxian.art.net.BaseServer;
import com.shangxian.art.utils.MyLogger;

public class MyApplication extends Application {

	private static MyApplication mInstance;
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
		DataTools.newInstance().initApplication(mInstance);

		MyLogger.i("densityDpi", this.getResources().getDisplayMetrics().densityDpi+"");
		// ImageLoaderConfiguration config = new
		// ImageLoaderConfiguration.Builder(
		// getApplicationContext())
		// .threadPriority(Thread.NORM_PRIORITY - 2)
		// .denyCacheImageMultipleSizesInMemory()
		// .discCacheFileNameGenerator(new Md5FileNameGenerator())
		// .tasksProcessingOrder(QueueProcessingType.FIFO)
		// .writeDebugLogs() // Remove for release app
		// .build();
		// Initialize ImageLoader with configuration.
		// ImageLoader.getInstance().init(config);
		BaseServer.toRegistContext(mInstance);
		AliPayBase.initContext(mInstance);
		SDKInitializer.initialize(getApplicationContext());
		
		initBdLoc();
		initImageLoader();

		loader = AbImageLoader.newInstance(this);
		loader.setEmptyImage(R.drawable.image_empty);
		loader.setErrorImage(R.drawable.image_error);
		loader.setLoadingImage(R.drawable.image_loading);
		// 推送
		JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
		JPushInterface.init(this); // 初始化 JPush
	}

	private void initBdLoc() {
		mLocationClient = new LocationClient(this.getApplicationContext());
		mGeofenceClient = new GeofenceClient(getApplicationContext());
		
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(2000);
		
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}
	
	/**
	 * 实时监听
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
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
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			Log.i("BaiduLocationApiDem", sb.toString());
		}

	}

	private void initImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				mInstance)
				// .memoryCacheExtraOptions(480, 800) // max width, max
				// height，即保存的每个缓存文件的最大长宽
				// .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75,
				// null) // Can slow ImageLoader, use it carefully (Better don't
				// use it)设置缓存的详细信息，最好不要设置这个
				.threadPoolSize(5)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 1)
				.denyCacheImageMultipleSizesInMemory()
				// .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 *
				// 1024)) // You can pass your own memory cache
				// implementation你可以通过自己的内存缓存实现
				.memoryCacheSize(3 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())// 将保存的时候的URI名称用MD5
				// 加密
				// .discCacheFileNameGenerator(new
				// HashCodeFileNameGenerator())//将保存的时候的URI名称用HASHCODE加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// .discCacheFileCount(100) //缓存的File数量
				// .discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
				// .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				// .imageDownloader(new BaseImageDownloader(context, 5 * 1000,
				// 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);// 全局初始化此配置
	}

	public BDLocation getMLoc() {
		return mloc;
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
