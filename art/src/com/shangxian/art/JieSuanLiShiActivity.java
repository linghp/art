package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.shangxian.art.adapter.JieSuanLiShiAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.JieSuanLiShiModel;
import com.shangxian.art.view.TopView;

/**
 * 结算历史
 * @author Administrator
 *
 */
public class JieSuanLiShiActivity extends BaseActivity{
	private ListView listview;
	private List<JieSuanLiShiModel>list;
	private JieSuanLiShiAdapter adapter;
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
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle("结算历史");
		listview = (ListView) findViewById(R.id.search_list);
	}
	private void initData() {
		list = new ArrayList<JieSuanLiShiModel>();
		for (int i = 0; i < 10; i++) {
			JieSuanLiShiModel model = new JieSuanLiShiModel();
			model.setType("紧急结算");
			model.setTime("2015-04-1"+(0+i));
			model.setSprice("￥50"+(0+i));
			list.add(model);
		}
		adapter = new JieSuanLiShiAdapter(this, R.layout.item_jinjijiesuan, list);
		listview.setAdapter(adapter);

	}
	private void initListener() {
		

	}

}
