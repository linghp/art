package com.shangxian.art.net;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public abstract class CallBack extends RequestCallBack<String> {
	
	public abstract void onSimpleSuccess(Object res);
	
	public abstract void onSimpleFailure(int code);
	
	@Override
	public void onFailure(HttpException e, String msg) {
		
	}
	
	@Override
	public void onSuccess(ResponseInfo<String> res) {
		
	}
}
