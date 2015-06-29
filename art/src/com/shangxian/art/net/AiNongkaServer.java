package com.shangxian.art.net;

import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.bean.CommonBean;
import com.shangxian.art.utils.MyLogger;


public class AiNongkaServer extends BaseServer {
	/*
	 * 返回获取商家二维码监听
	 */
	public interface OnHttpResultQrListener {
		void onHttpResultQr(CommonBean commonBean);
	}
	
	public static void onGetQrCode(String shopid,final OnHttpResultQrListener l) {
		//RequestParams params = getParams();
		String url=NET_SHOPQR+shopid;
		MyLogger.i(url);
		toXUtils2(HttpMethod.POST, url, null, new CallBack() {
			
			@Override
			public void onSimpleSuccess(Object res) {
				CommonBean commonBean=null;
				MyLogger.i(res.toString());
				try {
					commonBean=gson.fromJson(res.toString(), CommonBean.class);
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 l.onHttpResultQr(commonBean);
			}
			
			@Override
			public void onSimpleFailure(int code) {
				// TODO Auto-generated method stub
				l.onHttpResultQr(null);
			}
		});
	}
	
}
