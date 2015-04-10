package com.shangxian.art.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ab.http.AbBinaryHttpResponseListener;
import com.ab.http.AbFileHttpResponseListener;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.google.gson.Gson;
import com.shangxian.art.bean.UserInfo;

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
	//public static final String HOST = "http://192.168.1.125:8888/art/api/";
	public static final String HOST = "http://test.peoit.com/api/";
	protected static final String NET_LOGIN = HOST + "login";//登录接口
	protected static final String NET_ADS = HOST + "abs";//首页广告列表
	/**
	 * 
	 * ----------------------------------------------------------------
	 * 
	 */
	
	private static AbHttpUtil mAbHttpUtil = null;
	private static Context mContext;
	protected static Gson gson = new Gson();
	
	public static void toRegistContext(Context mContext){
		BaseServer.mContext = mContext;
		mAbHttpUtil = AbHttpUtil.getInstance(mContext);
	}
	
	protected static void toGet(String url, AbRequestParams params, final OnHttpListener l){
		mAbHttpUtil.get(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onStart() {}
			
			@Override
			public void onFinish() {}
			
			@Override
			public void onFailure(int code, String res, Throwable t) {
				System.out.println("http ======================" + "失败" + "=========================");
				if (l != null) {
					l.onHttp(null);
				}
			}
			
			@Override
			public void onSuccess(int code, String res) {
				System.out.println("http ======================" + res + "=========================");
				if (l != null) {
					if (code != 200) {
						l.onHttp(null);
					} else {
						try {
							JSONObject json = new JSONObject(res);
							int result_code = json.getInt("result_code");
							if (result_code == 200) {
								l.onHttp(json.getString("result"));
							}
						} catch (JSONException e) {
							e.printStackTrace();
							l.onHttp(null);
						}
						l.onHttp(res);
					}
				}
			}
		});
	}
	
	protected static void toPost(String url, AbRequestParams params, final OnHttpListener l){
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
						try {
							JSONObject json = new JSONObject(res);
							int result_code = json.getInt("result_code");
							if (result_code == 200) {
								l.onHttp(json.getString("result"));
							}
						} catch (JSONException e) {
							e.printStackTrace();
							l.onHttp(null);
						}
						l.onHttp(res);
					}
				}
			}
		});
	}
	
	protected static void toFile(String url, AbRequestParams params, final OnHttpListener l){
		mAbHttpUtil.post(url, params, new AbFileHttpResponseListener() {
			@Override
			public void onStart() {
				
			}
			@Override
			public void onFinish() {
				
			}
			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
				
			}
		});
	}
	
	protected interface OnHttpListener{
		void onHttp(String res);
	}
	
	public interface OnLoginListener{
		void onLogin(UserInfo info);
	}
}
