package com.shangxian.art.net.call;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.shangxian.art.bean.BaseBean;

public abstract class BaseCallBack<S> extends RequestCallBack<String> {
	@Override
	public void onFailure(HttpException e, String res) {
		onSimpleFailure(ErrorCode.http_error, res);
	}

	@Override
	public void onSuccess(ResponseInfo<String> result) {
		String json = result.result;
		try {
			Type type = new TypeToken<BaseBean<S>>(){}.getType();	
			BaseBean<S> bean = new BaseBean<S>();
			bean = new Gson().fromJson(json, type);
			onSimpleSuccess(bean);
		} catch (Exception e) {
			e.printStackTrace();
			onSimpleFailure(ErrorCode.gson_error, "Gson  Exception");
		}
	}

	public abstract void onSimpleSuccess(BaseBean<S> bean);
	
	public abstract void onSimpleFailure(int code, String res);
}
