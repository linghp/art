package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.shangxian.art.adapter.JieSuanLiShiAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.BenQiJieSuanModel;
import com.shangxian.art.bean.DingShiJieSuanModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.ShopsServer;
import com.shangxian.art.utils.LocalUserInfo;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

public class DingShiJieSuanActivity extends BaseActivity{

	private ListView listview;
	private View loading_big,ll_refresh_empty;
	private List<BenQiJieSuanModel> list;
	private JieSuanLiShiAdapter adapter;
	private DingShiJieSuanModel model;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle("定时结算");
		listview = (ListView) findViewById(R.id.search_list);
		loading_big = findViewById(R.id.loading_big);//加载
		ll_refresh_empty = findViewById(R.id.ll_refresh_empty);//无数据
	}

	private void initData() {
		list = new ArrayList<BenQiJieSuanModel>();
		model = new DingShiJieSuanModel();
		String shopid = LocalUserInfo.getInstance(this).getString(Constant.INT_SHOPID);
		MyLogger.i("shopid》》》》》》》》》》："+shopid);
		if (shopid != "0") {
			refreshTask(shopid);
		}else {
			myToast("数据错误，请重新登录");
		}

		
	}

	private void refreshTask(String shopid) {
		ShopsServer.toDingShiJieSuan(shopid, new CallBack() {
			
			@Override
			public void onSimpleSuccess(Object res) {
				MyLogger.i("定时结算数据："+res);
				if (res != null) {
					Gson gson = new Gson();
					try {
						JSONObject jsonObject = new JSONObject(res.toString());
						model = gson.fromJson(jsonObject.toString(),DingShiJieSuanModel.class);
						list = model.getData();
						MyLogger.i("定时结算数据："+list.toString());
						adapter = new JieSuanLiShiAdapter(DingShiJieSuanActivity.this, R.layout.item_jinjijiesuan, list);
						listview.setAdapter(adapter);
						loading_big.setVisibility(View.GONE);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					myToast("数据请求失败");
					loading_big.setVisibility(View.GONE);
					ll_refresh_empty.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onSimpleFailure(int code) {
				myToast("数据请求失败");
			}
		});
		
	}

	private void initListener() {
		// TODO Auto-generated method stub
		
	}
}
