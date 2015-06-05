package com.shangxian.art.net;

import android.widget.Toast;

import com.ab.http.AbRequestParams;
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
		AbRequestParams params = new AbRequestParams();	
		System.out.println(">>>>>>>>>>删除的id"+addressId);
		params.put("addressId", addressId);;
		toGet(NET_DELETEADDRESS, params, new OnHttpListener() {
			
			@Override
			public void onHttp(String res) {
				System.out.println(">>>>>>>>>>>删除收货地址"+res);
				if (res == null) {
					System.out.println(">>>>>>>>>>>>>删除失败");
				}
			}
		});
	}
}
