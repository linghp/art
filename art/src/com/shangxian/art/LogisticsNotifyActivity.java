package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.shangxian.art.adapter.LogisticsNotifyAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.LogisticsNotifyModel;
import com.shangxian.art.view.TopView;

public class LogisticsNotifyActivity extends BaseActivity{
	private ListView listview;
	private LogisticsNotifyAdapter adapter;
	private List<LogisticsNotifyModel>list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shops);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_logisticsnotify));
		listview = (ListView) findViewById(R.id.shops_mListView);
		
	}

	private void initData() {
		list = new ArrayList<LogisticsNotifyModel>();
		for (int i = 0; i < 10; i++) {
			LogisticsNotifyModel model = new LogisticsNotifyModel();
			model.setTitle("订单"+i+"已完成");
			model.setContent("尊敬的客户，您的订单45645468794已完成，感谢您使用京东快递");
			list.add(model);
		}
		adapter = new LogisticsNotifyAdapter(this, R.layout.item_logisticsnotify, list);
		listview.setAdapter(adapter);
		
	}

	private void initListener() {
		// TODO Auto-generated method stub
		
	}
}
