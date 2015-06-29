package com.shangxian.art.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.RequestExpectContinue;

import com.ab.http.AbRequestParams;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.shangxian.art.bean.SearchProductInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.MyLogger;

import android.text.TextUtils;

public class SearchServer extends BaseServer {
	public static void onSearchProduct(String key, String skip,
		String pageSize, /*String sort,*/ boolean isShop, Type type, CallBack back /*final OnSearchProductListener l*/
			) {
//		AbRequestParams params = new AbRequestParams();
//		params.put("key", key);
//		params.put("skip", skip);
//		params.put("pageSize", pageSize);
//		//params.put("sort", sort);
//		toPost(isShop ? NET_SEARCH_SHOP : NET_SEARCH_PRODUCT, params, new OnHttpListener() {
//			@Override
//			public void onHttp(String res) {
//				if (l != null && !TextUtils.isEmpty(res)) {
//					try {
//						Type type = new TypeToken<SearchProductInfo>() {
//						}.getType();
//						SearchProductInfo info = gson.fromJson(res, type);
//						l.onSearch(info);
//					} catch (Exception e) {
//						e.printStackTrace();
//						l.onSearch(null);
//					}
//				} else {
//					l.onSearch(null);
//				}
//			}
//		});
		RequestParams params = new RequestParams();
		MultipartEntity multipartEntity=new MultipartEntity();
		multipartEntity.setMultipartSubtype("multipart/form-data; boundary=--ling--");//加上这个就不报404了，坑
		params.setBodyEntity(multipartEntity);
		params.addBodyParameter("key", key);
		params.addBodyParameter("skip", skip);
		params.addBodyParameter("pageSize", pageSize);
		String url= isShop ? NET_SEARCH_SHOP : NET_SEARCH_PRODUCT;
		MyLogger.i(key);
		MyLogger.i(url);
		toXUtils(HttpMethod.POST, url, params, type, back);
	}
	
//	public interface OnSearchProductListener {
//		void onSearch(SearchProductInfo product);
//	}
}
