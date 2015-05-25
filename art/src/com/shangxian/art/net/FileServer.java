package com.shangxian.art.net;

import java.io.File;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class FileServer extends BaseServer {
	public void toFile(File file, RequestCallBack<String> callBack) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("photo", file);
		params.addHeader("User-Token", curUser.getId() + "");
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, NET_UPLOAD_IMG, params, callBack);
	}	
}
