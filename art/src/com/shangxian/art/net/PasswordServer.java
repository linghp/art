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

public class PasswordServer extends BaseServer {
	public static void toSendCode(final OnSendCodeListener l) {
		toGet2(NET_NEW_PAYPASSWORD_SENDCODE, null, new OnHttpListener() {
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

	public static void toNewSafeCode(String code, String newPassword,
			String reNewPassword, final OnNewSafeCodeListener l) {
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("captcha", code));
		pairs.add(new BasicNameValuePair("newPassword", newPassword));
		pairs.add(new BasicNameValuePair("reNewPassword", reNewPassword));
		toPostWithToken2(NET_NEW_PAYPASSWORD, pairs, new OnHttpListener() {
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
