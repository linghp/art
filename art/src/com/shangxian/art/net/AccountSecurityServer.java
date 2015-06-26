package com.shangxian.art.net;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.DeliveryAddressActivity;
import com.shangxian.art.R;
import com.shangxian.art.adapter.DeliveryAddressAdapter;
import com.shangxian.art.bean.DeliveryAddressModel;
import com.shangxian.art.net.HttpClients.HttpCilentListener;
import com.shangxian.art.utils.MyLogger;
/**
 * 收货地址请求数据类
 * 删除监听删除收货地址
 * @author zyz
 *
 */
public class AccountSecurityServer extends BaseServer{
	public interface OnHttpDeleteListener {
		void onHttpDelete(String res);
	}
	public interface OnHttpAddressListener {
		void onHttpAddress(List<DeliveryAddressModel> list);
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
	DeliveryAddressModel deliveryAddressModel;
	//添加数据
	public void toAddDeliveiveAdress(String id, String receiverName, String receiverTel, String deliveryAddress, String isDefault, final OnHttpDeleteListener l) {
		/*RequestParams params = getParams();
		params.addBodyParameter("receiverName", receiverName);
		params.addBodyParameter("receiverTel", receiverTel);
		params.addBodyParameter("deliveryAddress", deliveryAddress);
		params.addBodyParameter("isDefault", isDefault);
		params.addBodyParameter("id", id);
		//params.addBodyParameter(pairs);
		Type type = new TypeToken<DeliveryAddressModel>(){}.getType();
		toXUtils(HttpMethod.POST, BaseServer.HOST + "receivingAddOrUpdate", params, type, call);*/
		deliveryAddressModel = new DeliveryAddressModel();
		deliveryAddressModel.setId(id);
		deliveryAddressModel.setReceiverName(receiverName);
		deliveryAddressModel.setReceiverTel(receiverTel);
		deliveryAddressModel.setDeliveryAddress(deliveryAddress);
//		deliveryAddressModel.set_default(false);
		Gson gson = new Gson();
		String json = gson.toJson(deliveryAddressModel);
		toPostJson(BaseServer.HOST + "receivingAddOrUpdate", json, new OnHttpListener() {
			
			@Override
			public void onHttp(String res) {
				MyLogger.i(">>>>>>>>>>>添加收货地址:"+res);

				if (res != null) {
					MyLogger.i(">>>>>>>>>>>>>添加成功");
					l.onHttpDelete("保存成功");
				}else {
					MyLogger.i(">>>>>>>>>>>>>添加失敗");
					l.onHttpDelete("保存失败");
				}
				
			}
		});
	}

	private static List<DeliveryAddressModel> list;
	//地址列表
	public static void toDeliveiveAddress(final OnHttpAddressListener l){
		list = new ArrayList<DeliveryAddressModel>();
		MyLogger.i(">>>>>>>>>>>>>>>>收货地址url："+BaseServer.HOST + "receiving");
		HttpClients.getDo(BaseServer.HOST + "receiving", new HttpCilentListener() {

			@Override
			public void onResponse(String res) {
				MyLogger.i(">>>>>>>>>>收货地址返回数据："+res);
				if (!TextUtils.isEmpty(res)) {
					Gson gson = new Gson();
					try {
						JSONObject jsonObject = new JSONObject(res);
						String result_code = jsonObject
								.getString("result_code");
						if (result_code.equals("200")) {
							JSONArray str=jsonObject.getJSONArray("result");
							for (int i = 0; i < str.length(); i++) {
								list.add(gson.fromJson(str.getString(i), DeliveryAddressModel.class));
							}
							l.onHttpAddress(list);
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					l.onHttpAddress(null);
				}
			}
		});
	}
}
