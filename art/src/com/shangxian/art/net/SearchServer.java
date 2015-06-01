package com.shangxian.art.net;

import java.lang.reflect.Type;
import com.ab.http.AbRequestParams;
import com.google.gson.reflect.TypeToken;
import com.shangxian.art.bean.SearchProductInfo;
import com.shangxian.art.constant.Constant;

import android.text.TextUtils;

public class SearchServer extends BaseServer {
	public static void onSearchProduct(String key, String skip,
			String pageSize, /*String sort,*/ boolean isShop,
			final OnSearchProductListener l) {
		AbRequestParams params = new AbRequestParams();
		params.put("key", key);
		params.put("skip", skip);
		params.put("pageSize", pageSize);
		//params.put("sort", sort);
		toPost(isShop ? NET_SEARCH_SHOP : NET_SEARCH_PRODUCT, params, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				if (l != null && !TextUtils.isEmpty(res)) {
					try {
						Type type = new TypeToken<SearchProductInfo>() {
						}.getType();
						SearchProductInfo info = gson.fromJson(res, type);
						l.onSearch(info);
					} catch (Exception e) {
						e.printStackTrace();
						l.onSearch(null);
					}
				} else {
					l.onSearch(null);
				}
			}
		});
	}
	
	public interface OnSearchProductListener {
		void onSearch(SearchProductInfo product);
	}
}
