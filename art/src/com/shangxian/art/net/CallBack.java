package com.shangxian.art.net;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.shangxian.art.utils.MyLogger;

public abstract class CallBack extends RequestCallBack<String> {
	
	public abstract void onSimpleSuccess(Object res);
	
	public abstract void onSimpleFailure(int code);
	

	public void onDetailFailure(String msg) {
		
	}
	@Override
	public void onFailure(HttpException e, String msg) {
		
	}
	
	@Override
	public void onSuccess(ResponseInfo<String> res) {
		
		MyLogger.i(""+res);
	}
}
