package com.shangxian.art.net;

import android.text.TextUtils;

import com.ab.http.AbRequestParams;
import com.google.gson.JsonSyntaxException;
import com.shangxian.art.bean.ClassityCommdityResultModel;
import com.shangxian.art.bean.MyOrderItem_all;
import com.shangxian.art.net.MyOrderServer.OnHttpResultListener;

/**
 * 
 * @Description 
 * @author ling
 * @date 2015年6月2日
 */
public class FilterServer extends BaseServer{

	/*
	 * 返回确认收货监听
	 */
	public interface OnHttpResultFilterListener {
		void onHttpResultFilter(ClassityCommdityResultModel classityCommdityResultModel);
	}
	/*
	 * 返回更多监听
	 */
	public interface OnHttpResultMoreListener {
		void onHttpResultMore(ClassityCommdityResultModel classityCommdityResultModel);
	}

	public static void toPostFilter(String url,AbRequestParams params, final OnHttpResultFilterListener l) {
		toPost(url, params, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				//MyLogger.i(res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onHttpResultFilter(null);
					} else {
						l.onHttpResultFilter(getClassityCommdityResultModel(res));
					}
				}
			}
		});
	}

	protected static ClassityCommdityResultModel getClassityCommdityResultModel(String res) {
		ClassityCommdityResultModel classityCommdityResultModel=null;
		try {
			classityCommdityResultModel=gson.fromJson(res, ClassityCommdityResultModel.class);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classityCommdityResultModel;
	}
	
	public static void toPostMore(String url,AbRequestParams params, final OnHttpResultMoreListener l) {
		if(params==null){
			toPost(url,  null,new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				//MyLogger.i(res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onHttpResultMore(null);
					} else {
						l.onHttpResultMore(getClassityCommdityResultModel(res));
					}
				}
			}
		});
	}else{
		toPost(url, params, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				//MyLogger.i(res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onHttpResultMore(null);
					} else {
						l.onHttpResultMore(getClassityCommdityResultModel(res));
					}
				}
			}
		});
		}
	}
}
