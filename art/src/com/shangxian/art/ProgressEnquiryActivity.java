package com.shangxian.art;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

public class ProgressEnquiryActivity extends BaseActivity{

	private TextView tv_reason;
	private ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progressenquiry);
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
		topView.setTitle(getString(R.string.title_progressenquiry));
		
		tv_reason = (TextView) findViewById(R.id.progressenquiry_tv2);
		listview = (ListView) findViewById(R.id.progressenquiry_list);
		
	}

	private void initData() {
		
		
	}

	private void initListener() {
		
		
	}
}
