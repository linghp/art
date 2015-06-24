package com.shangxian.art.net;

import java.lang.reflect.Type;
import java.net.InterfaceAddress;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.ab.http.AbRequestParams;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.bean.UserInfo;
import com.shangxian.art.utils.MyLogger;

public class UserServer extends BaseServer {
	/**
	 * 登录
	 * 
	 * @param username
	 *            //用户名
	 * @param password
	 *            //密码
	 * @param l
	 */
	public static void toLogin(String username, String password,
			final OnLoginListener l) {
		AbRequestParams params = new AbRequestParams();
		params.put("phoneNumber", username);
		params.put("password", password);
		toPost(NET_LOGIN, params, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				MyLogger.i("user ===============================  "
						+ res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onLogin(null);
					} else {
						l.onLogin(getLogin(res));
					}
				}
			}
		});
	}

	protected static UserInfo getLogin(String res) {
		Type type = new TypeToken<UserInfo>() {
		}.getType();
		UserInfo info = gson.fromJson(res, type);
		
		return info;

	}

	public void toSogoRegistCode(String phone, final CallBack call) {
		if (call == null)
			return;
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("phoneNumber",phone);
		toXUtils(HttpMethod.POST, NET_SOGO_REGIST_CODE , params, null, call);
//		new HttpUtils().send(HttpMethod.GET, NET_SOGO_REGIST_CODE + phone,
//				new RequestCallBack<String>() {
//
//					@Override
//					public void onFailure(HttpException arg0, String arg1) {
//						l.onRegist(null);
//					}
//
//					@Override
//					public void onSuccess(ResponseInfo<String> res) {
//						String result = res.result;
//						JSONObject json;
//						try {
//							json = new JSONObject(result);
//							int result_code = json.getInt("result_code");
//							System.out.println("result_code ================="
//									+ result_code);
//							if (result_code == 200) {
//								String rest = json.getString("result");
//								System.out.println("toPsot -> rest >>>>>>> "
//										+ rest);
//								Type type = new TypeToken<UserInfo>(){}.getType();
//								UserInfo info = gson.fromJson(rest, type);
//								l.onRegist(info);
//							} else {
//								l.onRegist(null);
//							}	
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//					}
//				});
	}

	public void toSogoRegist(String phone, String captcha, String password, String rePassword, final Type type, final CallBack call){
		RequestParams params = new RequestParams();
		params.addBodyParameter("phoneNumber", phone);
		params.addBodyParameter("captcha", captcha);
		params.addBodyParameter("password", password);
		params.addBodyParameter("rePassword", rePassword);
		toXUtils(HttpMethod.POST, NET_SOGO_REGIST, params, type, call);
	}
}
