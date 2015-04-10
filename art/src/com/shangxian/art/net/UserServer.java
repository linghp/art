package com.shangxian.art.net;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.ab.http.AbRequestParams;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.shangxian.art.bean.UserInfo;

public class UserServer extends BaseServer {
	public static void toLogin(String username, String password, final OnLoginListener l) {
		AbRequestParams params = new AbRequestParams();
		params.put("username", username);
		params.put("password", password);
		toPost(NET_LOGIN, params, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				System.out.println("user  ===============================  "  + res);
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
		Type type = new TypeToken<UserInfo>(){}.getType();
		UserInfo info = gson.fromJson(res, type);
		return info;
	}
}
