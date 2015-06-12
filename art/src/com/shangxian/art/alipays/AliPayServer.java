package com.shangxian.art.alipays;

import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;

/**
 * 支付宝支付接口
 * 
 * @author libo
 * @time 2015/4/25
 */
public class AliPayServer extends AliPayBase{
	/**
	 * 支付宝及时支付
	 * 
	 * @param order_id
	 *            // 订单号(不为空)
	 * @param good_name
	 *            // 商品名(不为空)
	 * @param good_det
	 *            // 商品详情(不为空)
	 * @param good_price
	 *            // 商品价格(不为空)
	 * @param l           
	 */
	public static void toPay(String order_id, String good_name, String good_det,
			String good_price, OnPayListener l){
		String order = AliPayBuilder.createAliPayOrder(order_id, good_name, good_det, good_price).toSign();
		pay(order, l);
	}
	
	/**
	 * 支付宝及时支付
	 * 
	 * @param order_id
	 *            // 订单号(不为空)
	 * @param good_name
	 *            // 商品名(不为空)
	 * @param good_det
	 *            // 商品详情(不为空)
	 * @param good_price
	 *            // 商品价格(不为空)
	 * @param l           
	 */
	public static void toPay(String order_id, String good_name, String good_det,
			String good_price){
		String order = AliPayBuilder.createAliPayOrder(order_id, good_name, good_det, good_price).toSign();
		pay(order, null);
	}

	
	/**
	 * 支付宝充值
	 * 
	 * @param order_id
	 *            // 订单号(不为空)
	 * @param good_name
	 *            // 商品名(不为空)
	 * @param good_det
	 *            // 商品详情(不为空)
	 * @param good_price
	 *            // 商品价格(不为空)
	 * @param l           
	 */
	public static void toRecharge(String good_name, String good_det,
			String good_price, OnPayListener l){
		String order_id="R"+CommonUtil.getUUID();
		MyLogger.i(order_id);
		String order = AliPayBuilder.createAliPayOrder(order_id, good_name, good_det, good_price).toSign();
		pay(order, l);
	}
	
}
