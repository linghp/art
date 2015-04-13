package com.shangxian.art.net;

import java.lang.reflect.Type;

import com.ab.http.AbRequestParams;
import com.google.gson.reflect.TypeToken;
import com.shangxian.art.bean.AccountSumInfo;

import android.text.TextUtils;

/**
 * 支付接口管理类
 * @author libo
 *	
 */
public class PayServer extends BaseServer {
	
	/**
	 * 获取用户余额
	 * @param l
	 */
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
	
	
	public static void toPayOrder(){
		AbRequestParams params = new AbRequestParams();
		//params.put("", value);
		toPost(NET_PAY_ORDER, params, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				
			}
		});
	}
	
	public static void toPayment(String pass, double amount, String type){
		AbRequestParams params = new AbRequestParams();
		params.put("from", curUser.getId() + "");
		params.put("to", "3");
		params.put("amount", String.valueOf(amount));
		params.put("payPassword", pass);
		params.put("payType", type);
		toPost(NET_PAY_ORDER, params, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				
			}
		});
	}
}
