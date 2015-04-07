package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.shangxian.art.adapter.SearchAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.SearchModel;

public class ShangPinWeiHuActivity extends BaseActivity{

	ListView list;
	List<SearchModel> model;
	SearchAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shangpinweihu);
		initView();
		initData();
		listener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		list = (ListView) findViewById(R.id.shangpinweihu_list);
	}

	private void initData() {
		// TODO Auto-generated method stub
		model = new ArrayList<SearchModel>();
		for (int i = 0; i < 5; i++) {
			SearchModel m = new SearchModel();
			m.setTitle("特价美食"+ (1+i));
			model.add(m);
		}
		adapter = new SearchAdapter(this, R.layout.item_search, model);
		list.setAdapter(adapter);
	}

	private void listener() {
		// TODO Auto-generated method stub
		
	}
}
