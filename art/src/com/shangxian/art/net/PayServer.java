package com.shangxian.art.net;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.shangxian.art.bean.AccountSumInfo;

import android.text.TextUtils;

public class PayServer extends BaseServer {
	public static void loadAccountSum(final OnAccountSumListener l){
		toPostJson(NET_ACCOUNT, null, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				System.out.println("pay account ===================== " + res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onAccountSum(null);
					} else {
						Type type = new TypeToken<AccountSumInfo>(){}.getType();
						AccountSumInfo info = gson.fromJson(res, type);
						l.onAccountSum(info);
					}
				}
			}
		});
	}	
}
