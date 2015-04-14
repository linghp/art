package com.shangxian.art.net;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.ab.http.AbRequestParams;
import com.google.gson.reflect.TypeToken;
import com.shangxian.art.bean.AccountSumInfo;
import com.shangxian.art.utils.MyLogger;

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
	
	public static void toPayment(String pass, int toid, int amount, String type, final OnPaymentListener l){
		//List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
//		AbRequestParams params = new AbRequestParams();
//		params.put("from", "2");
//		params.put("to", "3");
//		params.put("amount", "200");
//		params.put("payPassword", "612714");
//		params.put("payType", "ALB_ALY");
		AbRequestParams params = new AbRequestParams();
		params.put("from", curUser.getId() + "");
		params.put("to", "3");
		params.put("amount", amount + "");
		params.put("payPassword", pass);
		params.put("payType", type);
		MyLogger.i(curUser.getId() + " ---- " + String.valueOf(amount) + " ---- " + pass + "----" + type);
		/*pairs.add(new BasicNameValuePair("from", curUser.getId() + ""));
		pairs.add(new BasicNameValuePair("to", "3"));
		pairs.add(new BasicNameValuePair("amount", String.valueOf(amount)));
		pairs.add(new BasicNameValuePair("payPassword", pass));
		pairs.add(new BasicNameValuePair("payType", type));
		System.out.println("params == " + curUser.getId() + " == " + pass + " == " + toid + " == " + amount + " == " + type);*/
		toPost("http://test.peoit.com/api/payment", params, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				if (l != null) {
					l.onPayment(res);
				}
			}
		});
	}
}
