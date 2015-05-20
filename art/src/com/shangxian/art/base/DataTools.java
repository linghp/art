package com.shangxian.art.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;


public class DataTools {
	private Map<String, Serializable> datas;
	private Map<String, List<? extends Serializable>> datass;
	
	private Application app;
	private static DataTools dao;
	
	//屏幕相关
	private DisplayMetrics metrics;
	public static int w_screeen;
	public static int h_screeen;
	
	private DataTools(){
		datas = new HashMap<String, Serializable>();
		datass = new HashMap<String, List<? extends Serializable>>();
	}
	
	public static DataTools newInstance(){
		if (dao == null) {
			dao = new DataTools();
		}
		return dao;
	}
	
	public void initApplication(Application app){
		this.app = app;
		this.metrics = getApplicationContext().getResources().getDisplayMetrics();
		this.w_screeen = metrics.widthPixels;
		this.h_screeen = metrics.heightPixels;
	}
	
	public Context getApplicationContext(){
		return app.getApplicationContext();
	}
	
	public void put(String key, Serializable data){
		if (TextUtils.isEmpty(key)) {
			throw new NullPointerException("key is null");
		}
		if (data == null){
			throw new NullPointerException("data is null");
		}
		this.datas.put(key, data);
	}
	
	public void put(String key, List<? extends Serializable> data){
		if (TextUtils.isEmpty(key)) {
			throw new NullPointerException("key is null");
		}
		if (data == null || data.size() == 0){
			throw new NullPointerException("data is null");
		}
		this.datass.put(key, data);
	}
	
	public Serializable getDatas(String key) {
		if (TextUtils.isEmpty(key)) {
			throw new NullPointerException("key is null");
		}
		if (this.datas.containsKey(key)) {
			return this.datas.get(key);
		} else {
			return null;
		}
	}
	
	public List<? extends Serializable> getDatass(String key) {
		if (TextUtils.isEmpty(key)) {
			throw new NullPointerException("key is null");
		}
		if (this.datas.containsKey(key)) {
			return this.datass.get(key);
		} else {
			return null;
		}
	}
}
