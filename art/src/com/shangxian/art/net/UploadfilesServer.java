package com.shangxian.art.net;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class UploadfilesServer extends BaseServer {
    private static AsyncHttpClient  asyncHttpClient = new AsyncHttpClient();
    
	public static void toUploadFile(Context context,RequestParams params, AsyncHttpResponseHandler responseHandler) {
		asyncHttpClient.post(context, NET_COMMENTADD, params, responseHandler);
	}	
}
