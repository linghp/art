package com.shangxian.art.net;

import java.lang.reflect.Type;
import com.ab.http.AbRequestParams;
import com.google.gson.reflect.TypeToken;
import com.shangxian.art.bean.SearchProductInfo;
import com.shangxian.art.constant.Constant;

import android.text.TextUtils;

public class SearchServer extends BaseServer {
	public static void onSearchProduct(String key, String skip,
			String pageSize, String sort,
			final OnSearchProductListener l) {
		AbRequestParams params = new AbRequestParams();
		params.put("key", key);
		params.put("skip", skip);
		params.put("pageSize", pageSize);
		params.put("sort", sort);
		toPost(NET_SEARCH_PRODUCT, params, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				if (l != null && !TextUtils.isEmpty(res)) {
					Type type = new TypeToken<SearchProductInfo>() {
					}.getType();
					SearchProductInfo info = gson.fromJson(res, type);
//					if (isSave) {
//						tools.put(Constant.DATA_SEARCH_PRODUCT, info);
//					}
					l.onSearch(info);
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
