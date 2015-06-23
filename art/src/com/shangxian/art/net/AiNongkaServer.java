package com.shangxian.art.net;

import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.bean.CommonBean;


public class AiNongkaServer extends BaseServer {
	/*
	 * 返回获取商家二维码监听
	 */
	public interface OnHttpResultQrListener {
		void onHttpResultQr(String qrurl);
	}
	
	public static void onGetQrCode(final OnHttpResultQrListener l) {
		//RequestParams params = getParams();
		toXUtils2(HttpMethod.POST, NET_SHOPQR, null, new CallBack() {
			
			@Override
			public void onSimpleSuccess(Object res) {
				CommonBean commonBean=null;
				try {
					commonBean=gson.fromJson(res.toString(), CommonBean.class);
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               if(commonBean!=null){
            	   if(commonBean.getResult_code().equals("200")){
            		   if(!TextUtils.isEmpty(commonBean.getResult())){
            			   l.onHttpResultQr(commonBean.getResult());
            			   return;
            		   }
            	   }
               }
               l.onHttpResultQr(null);
			}
			
			@Override
			public void onSimpleFailure(int code) {
				// TODO Auto-generated method stub
				l.onHttpResultQr(null);
			}
		});
	}
	
}
