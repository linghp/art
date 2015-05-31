package com.shangxian.art.net;

import java.lang.reflect.Type;

import com.baidu.mapapi.model.LatLng;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.bean.NearlyShopStat;
import com.shangxian.art.utils.MyLogger;

public class NearlyServer extends BaseServer {
	public void toNearlyShop(String lng, int r, int index, final OnNearlyShopListener l) {
		if (l == null) return; 
		RequestParams params = new RequestParams();
		params.addBodyParameter("location", lng);
		params.addBodyParameter("radius", r + "");
		params.addBodyParameter("page_index", index + "");
		if (!curUser.isNull())
			params.addHeader("User-Token", curUser.getId() + "");
		getHttpUtils().send(HttpMethod.POST,
				NET_NEARLY, params,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						l.onNearly(null);
					}
					
					@Override
					public void onSuccess(ResponseInfo<String> result) {
						//MyLogger.d();
						try {
							String res = getRes(result.result);
							MyLogger.i(res);
							Type type = new TypeToken<NearlyShopStat>(){}.getType();
							NearlyShopStat stat = gson.fromJson(res, type);
							l.onNearly(stat);
						} catch (Exception e) {
							e.printStackTrace();
							l.onNearly(null);
						}
					}
				});
	}

	public interface OnNearlyShopListener {
		void onNearly(NearlyShopStat stat);
	}
}
