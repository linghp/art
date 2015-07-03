package com.shangxian.art.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.ab.http.AbRequestParams;
import com.google.gson.JsonObject;
import com.shangxian.art.base.DataTools;

public class PasswordServer extends BaseServer {
	public static void toSendCode(boolean isLogin, final OnSendCodeListener l) {
		toGet2(isLogin ? NET_LOGIN_PASSWORD_SENDCODE : NET_NEW_PAYPASSWORD_SENDCODE , null, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				if (l != null) {
					if ("发送成功".equals(res)) {
						l.onSendCode(true);
					} else {
						l.onSendCode(false);
					}
				}
			}
		});
	}

	public static void toNewPassword(String code, String newPassword,
			String reNewPassword, boolean isLogin, final OnNewSafeCodeListener l) {
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		if(!isLogin){
			newPassword=DataTools.generatePassword(newPassword);
			reNewPassword=DataTools.generatePassword(reNewPassword);
			pairs.add(new BasicNameValuePair("action", "new"));
		}
		pairs.add(new BasicNameValuePair("captcha", code));
		pairs.add(new BasicNameValuePair("newPassword", newPassword));
		pairs.add(new BasicNameValuePair("reNewPassword", reNewPassword));
		toPostWithToken2(isLogin ? NET_NEW_LOGIN_PASSWORD : NET_NEW_PAYPASSWORD , pairs, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				if (l != null) {
					try {
						JSONObject json = new JSONObject(res);
						String resRea = json.getString("reason");
						if ("success".equals(resRea)) {
							l.onNewSafeCode(true);
						} else {
							l.onNewSafeCode(false);
						}
					} catch (JSONException e) {
						l.onNewSafeCode(false);
						e.printStackTrace();
					} catch (NullPointerException e) {
						l.onNewSafeCode(false);
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	public static final void toUpPassword(String old, String _new, String reNew, boolean isLogin, final OnNewSafeCodeListener l){
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		if(!isLogin){
			old=DataTools.generatePassword(old);
			_new=DataTools.generatePassword(_new);
			reNew=DataTools.generatePassword(reNew);
			pairs.add(new BasicNameValuePair("action", "update"));
		}
		pairs.add(new BasicNameValuePair("oldPassword", old));
		pairs.add(new BasicNameValuePair("newPassword", _new));
		pairs.add(new BasicNameValuePair("reNewPassword", reNew));
		toPostWithToken2(isLogin ? NET_UPDATA_LOGIN_PASSWORD : NET_UPDATA_PAYPASSWORD, pairs, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				if (l != null) {
					try {
						JSONObject json = new JSONObject(res);
						String resRea = json.getString("reason");
						if ("success".equals(resRea)) {
							l.onNewSafeCode(true);
						} else {
							l.onNewSafeCode(false);
						}
					} catch (JSONException e) {
						l.onNewSafeCode(false);
						e.printStackTrace();
					} catch (NullPointerException e) {
						l.onNewSafeCode(false);
						e.printStackTrace();
					}
				}
			}
		});
	}

	public interface OnSendCodeListener {
		void onSendCode(boolean isSend);
	}

	public interface OnNewSafeCodeListener {
		void onNewSafeCode(boolean isNew);
	}
}
