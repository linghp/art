package com.shangxian.art.net;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.text.TextUtils;

import com.ab.http.AbRequestParams;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.bean.AccountSumInfo;
import com.shangxian.art.bean.CommonBean;
import com.shangxian.art.bean.ModelText;
import com.shangxian.art.bean.PayOrderInfo;
import com.shangxian.art.utils.MyLogger;

/**
 * 支付接口管理类
 * @author libo
 *	
 */
public class PayServer extends BaseServer {
	/*
	 * 获取支付宝支付订单之前通知服务器订单号返回监听
	 */
	public interface OnPayNoticeListener {
		void onPayNotice(CommonBean commonBean);
	}
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
						info.setAlb(info.getAlb()/100);
						info.setAly(info.getAly()/100);
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
	
//	public static void toPayment(String pass, int toid, int amount, String type, final OnPaymentListener l){
//		AbRequestParams params = new AbRequestParams();
//		params.put("from", curUser.getId() + "");
//		params.put("to", "3");
//		params.put("amount", amount + "");
//		params.put("payPassword", pass);
//		params.put("payType", type);
//		MyLogger.i(curUser.getId() + " ---- " + String.valueOf(amount) + " ---- " + pass + "----" + type);
//		toPost("http://test.peoit.com/api/payment", params, new OnHttpListener() {
//			@Override
//			public void onHttp(String res) {
//				if (l != null) {
//					l.onPayment(res);
//				}
//			}
//		});
//	}
	
	/**
	 * 扫码支付
	 * 
	 * @param pass
	 * @param toid
	 * @param amount
	 * @param type
	 * @param call
	 */
	
	public void toPayment(String pass, String toid, double amount, String type,CallBack call){
		RequestParams params = getParams();
		params.addBodyParameter("from", curUser.getId() + "");
		params.addBodyParameter("to", toid);
		params.addBodyParameter("amount", (int)amount*100+"");
		params.addBodyParameter("payPassword", pass);
		params.addBodyParameter("payType", type);
		MyLogger.i("请求地址"+NET_PAYMENT);
		MyLogger.i("卖家id"+toid+"密码："+pass+"金额"+amount+"类型"+type+"买家id："+curUser.getId());
		MyLogger.i(""+params);
		toXUtils(HttpMethod.POST, NET_PAYMENT, params, null, call);
		
		/*AbRequestParams params = new AbRequestParams();
		params.put("from", curUser.getId() + "");
		params.put("to", toid);
		params.put("amount", amount*100 + "");
		params.put("payPassword", pass);
		params.put("payType", type);
		toPost(NET_PAYMENT, params, new OnHttpListener() {
			
			@Override
			public void onHttp(String res) {
				
				MyLogger.i("支付后的数据："+res);
			}
		});*/

	}
	/*ModelText modelText;
	public void toPayment(String pass, String toid, double amount, String type,OnPaymentListener l){
		modelText = new ModelText();
		modelText.setFrom(curUser.getId() + "");
		modelText.setTo(toid);
		modelText.setAmount(amount+"");
		modelText.setPayPassword(pass);
		modelText.setPayType(type);
		Gson gson = new Gson();
		String json = gson.toJson(modelText);
		toPostJson2(NET_PAYMENT, json, new OnHttpListener() {
			
			@Override
			public void onHttp(String res) {
				MyLogger.i("支付后的数据："+res);
				
			}
		});
	}*/
	
	/**
	 * 支付宝支付订单之前通知服务器
	 * @param alipayTradeNumber
	 * @param payType
	 * @param orderNumbers
	 * @param l
	 */
	public static void toPaymentNotice(String alipayTradeNumber,String payType, List<String> orderNumbers,final OnPayNoticeListener l){
		PaymentNoticeRequest paymentNoticeRequest=new PaymentNoticeRequest(payType, orderNumbers);
		String json=gson.toJson(paymentNoticeRequest);
		if(!TextUtils.isEmpty(json)){
		toPostJson2(NET_PAY_NOTICE+alipayTradeNumber, json,  new OnHttpListener() {
			
			@Override
			public void onHttp(String res) {
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onPayNotice(null);
					} else {
						CommonBean commonBean=gson.fromJson(res, CommonBean.class);
						l.onPayNotice(commonBean);
					}
				}
			}
		});
		}
	}
	
	public static class PaymentNoticeRequest {

		@Expose
		public String payType;
		@Expose
		public List<String> orderNumber = new ArrayList<String>();
		public PaymentNoticeRequest(String payType, List<String> orderNumber) {
			super();
			this.payType = payType;
			this.orderNumber = orderNumber;
		}
		
	}
}
