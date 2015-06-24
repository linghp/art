package com.shangxian.art;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.shangxian.art.adapter.OperatorManagementAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.OperatorManagementModel;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

public class OperatorManagementActivity extends BaseActivity{

	private ListView listview;
	private OperatorManagementAdapter adapter;
	private List<OperatorManagementModel> model;
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
		topView.setRightBtnDrawable(R.drawable.addto);
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_operatormanagement));
		listview = (ListView) findViewById(R.id.search_list);
		
	}

	private void initData() {
		
		
	}

	private void initListener() {
		topView.setRightBtnListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//添加操作员
				CommonUtil.gotoActivity(OperatorManagementActivity.this, AddOperatorActivity.class, false);
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 操作员详情
//				CommonUtil.gotoActivity(OperatorManagementActivity.this, OperatorDetailsActivity.class, false);
			}
		});
	}
}
