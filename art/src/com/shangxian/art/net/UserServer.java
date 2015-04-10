package com.shangxian.art.net;

import android.text.TextUtils;

import com.ab.http.AbRequestParams;

public class UserServer extends BaseServer {
	public static void toLogin(String username, String password, final OnLoginListener l) {
		AbRequestParams params = new AbRequestParams();
		params.put("", username);
		params.put("", password);
		toPost(NET_LOGIN, params, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onLogin(false);
					} else {
						l.onLogin(getLogin(res));
					}
				}
			}
		});
	}

	protected static boolean getLogin(String res) {
		
		return false;
	}
}
