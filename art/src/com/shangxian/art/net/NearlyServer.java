package com.shangxian.art.net;

import com.baidu.mapapi.model.LatLng;
import com.lidroid.xutils.http.RequestParams;
import com.shangxian.art.bean.NearlyShopStat;

public class NearlyServer extends BaseServer {
	public void toNearlyShop(LatLng lng, int r, final OnNearlyShopListener l){
		RequestParams params = new RequestParams();
		//params.add
	}
	
	
	public interface OnNearlyShopListener{
		void onNearly(NearlyShopStat stat);
	}
}
