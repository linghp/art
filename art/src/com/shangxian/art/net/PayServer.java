package com.shangxian.art.net;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.ab.http.AbRequestParams;
import com.baidu.mapapi.map.Text;
import com.google.gson.reflect.TypeToken;
import com.shangxian.art.bean.AccountSumInfo;
import com.shangxian.art.bean.PayOrderInfo;
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
	
	public static void toPayOrder(PayOrderInfo info, final OnPayListener l){
		String json = gson.toJson(info);
		System.out.println("toPayorder >>>>>>>> json ---------------->" + json);
		toPostJson(NET_PAY_ORDER, json, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				System.out.println("toPayOrder ==>>>>>>>>>>>>>> " + res);
				if (l != null && !TextUtils.isEmpty(res) && res.equals("支付成功")) {
					l.onPayment(true);
				} else {
					l.onPayment(false);
				}
			}
		});
	}
	
	public static void toPayment(String pass, int toid, int amount, String type, final OnPaymentListener l){
		AbRequestParams params = new AbRequestParams();
		params.put("from", curUser.getId() + "");
		params.put("to", "3");
		params.put("amount", amount + "");
		params.put("payPassword", pass);
		params.put("payType", type);
		MyLogger.i(curUser.getId() + " ---- " + String.valueOf(amount) + " ---- " + pass + "----" + type);
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
