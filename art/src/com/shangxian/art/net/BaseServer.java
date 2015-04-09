package com.shangxian.art.net;

import android.content.Context;

import com.ab.http.AbBinaryHttpResponseListener;
import com.ab.http.AbFileHttpResponseListener;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;

/**
 * 联网基类
 * @author libo
 * @time 2015/4/9
 */
public class BaseServer {
	/**
	 * 
	 * ----------------------------请求------------------------------
	 * 
	 */
	public static final String HOST = "http://192.168.1.125:8888/art/api/";
	protected static final String NET_LOGIN = HOST + "login";//登录接口
	protected static final String NET_ADS = HOST + "login";//首页广告列表
	/**
	 * 
	 * ----------------------------------------------------------------
	 * 
	 */
	
	private static AbHttpUtil mAbHttpUtil = null;
	private static Context mContext;
	
	public static void toRegistContext(Context mContext){
		BaseServer.mContext = mContext;
		mAbHttpUtil = AbHttpUtil.getInstance(mContext);
	}
	
	protected void toGet(String url, AbRequestParams params, final OnHttpListener l){
		mAbHttpUtil.get(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onStart() {}
			
			@Override
			public void onFinish() {}
			
			@Override
			public void onFailure(int code, String res, Throwable t) {
				if (l != null) {
					l.onHttp(null);
				}
			}
			
			@Override
			public void onSuccess(int code, String res) {
				if (l != null) {
					if (code != 200) {
						l.onHttp(null);
					} else {
						l.onHttp(res);
					}
				}
			}
		});
	}
	
	protected void toPost(String url, AbRequestParams params, final OnHttpListener l){
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onStart() {}
			
			@Override
			public void onFinish() {}
			
			@Override
			public void onFailure(int code, String res, Throwable t) {
				if (l != null) {
					l.onHttp(null);
				}
			}
			
			@Override
			public void onSuccess(int code, String res) {
				if (l != null) {
					if (code != 200) {
						l.onHttp(null);
					} else {
						l.onHttp(res);
					}
				}
			}
		});
	}
	
	protected void toFile(){
		
	}
	
	protected interface OnHttpListener{
		void onHttp(String res);
	}
}
