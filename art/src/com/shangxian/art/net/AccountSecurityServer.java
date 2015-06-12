package com.shangxian.art.net;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.bean.DeliveryAddressModel;
import com.shangxian.art.utils.MyLogger;

public class AccountSecurityServer extends BaseServer{

	/**
	 * 删除监听删除收货地址
	 */
	public interface OnHttpDeleteListener {
		void onHttpDelete(String res);
	}

	//删除收货地址
	public static void toGetDeleteAddress(String addressId,final OnHttpDeleteListener l){
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("addressId", addressId));
		MyLogger.i(">>>>>>>>>>删除的id"+addressId);
		toPostWithToken(NET_DELETEADDRESS, pairs, new OnHttpListener() {
			
			@Override
			public void onHttp(String res) {
				MyLogger.i(">>>>>>>>>>>删除收货地址:"+res);
				
				if (res != null) {
					MyLogger.i(">>>>>>>>>>>>>删除成功");
					l.onHttpDelete(res);
				}else {
					MyLogger.i(">>>>>>>>>>>>>删除失敗");
					l.onHttpDelete("删除失败");
				}
			}
		});
	}
	//设为默认地址
	public static void toGetDefaultAddress(String addressId,final OnHttpDeleteListener l){
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("addressId", addressId));
		MyLogger.i(">>>>>>>>>>设置的id"+addressId);
		toPostWithToken(NET_DEFAULTADDRESS, pairs, new OnHttpListener() {
			
			@Override
			public void onHttp(String res) {
				MyLogger.i(">>>>>>>>>>>设为默认收货地址:"+res);
				
				if (res != null) {
					MyLogger.i(">>>>>>>>>>>>>设置成功");
					l.onHttpDelete("设置默认地址成功");
				}else {
					MyLogger.i(">>>>>>>>>>>>>设置失敗");
					l.onHttpDelete("设置默认地址失败");
				}
			}
		});
	}
	
	public void toAddDeliveiveAdress(String id, String receiverName, String receiverTel, String deliveryAddress, String isDefault, CallBack call) {
		RequestParams params = getParams();
		params.addBodyParameter("receiverName", receiverName);
		params.addBodyParameter("receiverTel", receiverTel);
		params.addBodyParameter("deliveryAddress", deliveryAddress);
		params.addBodyParameter("isDefault", isDefault);
		params.addBodyParameter("id", id);
		//params.addBodyParameter(pairs);
		Type type = new TypeToken<DeliveryAddressModel>(){}.getType();
		toXUtils(HttpMethod.POST, BaseServer.HOST + "receivingAddOrUpdate", params, type, call);
	}
}
