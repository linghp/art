package com.shangxian.art.net;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.bean.BuyerReturnOrderStat;

/**
 * 买家联网操作
 * 
 * @author Administrator
 */
public class BuyerOrderServer extends BaseServer {
	/**
	 * 获取买家退货列表
	 * 
	 * @param status
	 * @param skip
	 * @param back
	 */
	public void toBuyerReturnList(String status, String skip, CallBack back){
		RequestParams params = getParams();
		params.addBodyParameter("skip", skip);
		params.addBodyParameter("pageSize", "10");
		Type type = new TypeToken<BuyerReturnOrderStat>(){}.getType();
		toXUtils(HttpMethod.POST, NET_BUYER_RETURN_LIST + status, params, type, back);
	}
	
	/**
	 * 买家删除退货订单列表
	 * 
	 * @param orderId
	 * @param call
	 */
	public void toBuyerDeleteReturnOrder(String returnOrderNum, CallBack call){
		RequestParams params = getParams();
		toXUtils(HttpMethod.POST, NET_BUYER_DELETE_REUTRN_ORDER + returnOrderNum, params, null, call);
	}
	
	/**
	 * 买家取消退货订单
	 * 
	 * @param productId
	 * @param returnOrderNum
	 * @param call
	 */
	public void toBuyerCancelReturnOrder(String productId, String returnOrderNum, CallBack call) {
		toXUtils(HttpMethod.POST, NET_BUYER_CANCEL_RETURN_ORDER + productId + "/" + returnOrderNum, getParams(), null, call);
	}
}
