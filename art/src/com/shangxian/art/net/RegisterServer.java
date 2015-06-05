package com.shangxian.art.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.ab.http.AbRequestParams;
import com.shangxian.art.bean.CommonBean;
import com.shangxian.art.bean.UserInfo;
import com.shangxian.art.utils.MyLogger;

public class RegisterServer extends BaseServer {
	/*
	 * 返回监听
	 */
	public interface OnHttpResultListener {
		void onHttpResult(CommonBean commonBean);
	}

	public static void toRegidter1(String phonenumber,
			final OnHttpResultListener l) {
		AbRequestParams params = new AbRequestParams();
		params.put("phoneNumber", phonenumber);
		toPost2(NET_CAPTCHA, params, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				MyLogger.i(res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onHttpResult(null);
					} else {
						l.onHttpResult(getCommonBean(res));
					}
				}
			}
		});
	}
	
	public static void toRegidter2(String phonenumber,String captcha,
			final OnHttpResultListener l) {
		AbRequestParams params = new AbRequestParams();
		params.put("phoneNumber", phonenumber);
		params.put("captcha", captcha);
		toPost2(NET_VALIDCAPTCHA,params, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				MyLogger.i(res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onHttpResult(null);
					} else {
						l.onHttpResult(getCommonBean(res));
					}
				}
			}
		});
	}
	public static void toRegidter3(String phonenumber,String captcha,String pass,String repass,
			final OnHttpResultListener l) {
		AbRequestParams params = new AbRequestParams();
		params.put("phoneNumber", phonenumber);
		params.put("captcha", captcha);
		params.put("password", pass);
		params.put("rePassword", repass);
		MyLogger.i(phonenumber+"--"+captcha+"--"+pass+"--"+repass);
		toPost2(NET_REGIST,params, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				MyLogger.i(res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onHttpResult(null);
					} else {
						CommonBean commonBean=new CommonBean<UserInfo>();
						try {
							JSONObject json = new JSONObject(res);
							int result_code = json.getInt("result_code");
							if (result_code == 200) {
								String rest = json.getString("result");
								UserInfo userInfo=gson.fromJson(rest, UserInfo.class);
								commonBean.setResult_code("200");
								commonBean.setReason("success");
							    commonBean.setObject(userInfo);
							}else{
								commonBean=gson.fromJson(res, CommonBean.class);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}catch (Exception e) {
							e.printStackTrace();
						}
						l.onHttpResult(commonBean);
					}
				}
			}
		});
	}

	protected static CommonBean getCommonBean(String res) {
		CommonBean CommonBean = null;
		try {
			CommonBean = gson.fromJson(res, CommonBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CommonBean;
	}
}
