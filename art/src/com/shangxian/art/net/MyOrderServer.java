package com.shangxian.art.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.ab.http.AbRequestParams;
import com.google.gson.Gson;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.MyOrderItem_all;
import com.shangxian.art.utils.MyLogger;

public class MyOrderServer extends BaseServer {
	/*
	 * 返回监听
	 */
	public interface OnHttpResultListener {
		void onHttpResult(MyOrderItem_all myOrderItemAll);
	}

	public static void toGetOrder(String status,
			final OnHttpResultListener l) {
		toPostWithToken(NET_ORDERS+status, null, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				MyLogger.i(res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onHttpResult(null);
					} else {
						l.onHttpResult(getMyOrderItemAll(res));
					}
				}
			}
		});
	}
	
	protected static MyOrderItem_all getMyOrderItemAll(String content) {
		MyOrderItem_all myOrderItem_all=null;
		try {
			JSONObject jsonObject = new JSONObject(content);
			String result_code = jsonObject.getString("result_code");
			if (result_code.equals("200")) {
				if (jsonObject.getString("reason").equals("success")) {
					String json = jsonObject.getString("result");
					Gson gson = new Gson();
					 myOrderItem_all = gson.fromJson(json,
							MyOrderItem_all.class);
					MyLogger.i(myOrderItem_all.toString());
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return myOrderItem_all;
	}
}
