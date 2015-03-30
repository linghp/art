package com.shangxian.art.base;

import android.app.Application;
import android.view.LayoutInflater;

import com.ab.image.AbImageLoader;
import com.shangxian.art.R;
import com.shangxian.art.constant.Global;


public class MyApplication extends Application {

	private static MyApplication mInstance ;
	private AbImageLoader loader;

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
		
		loader = AbImageLoader.newInstance(this);
		loader.setEmptyImage(R.drawable.image_empty);
		loader.setErrorImage(R.drawable.image_error);
		loader.setLoadingImage(R.drawable.image_loading);
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
