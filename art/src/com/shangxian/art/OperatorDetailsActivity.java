package com.shangxian.art;

import android.os.Bundle;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

public class OperatorDetailsActivity extends BaseActivity{

	private TextView tv_user,tv_name,tv_id,tv_num,tv_role,tv_purview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_operatordetails);
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
		topView.setTitle(getString(R.string.title_addoperator));
		
		tv_user = (TextView) findViewById(R.id.operatordetails_tv1);
		tv_name = (TextView) findViewById(R.id.operatordetails_tv2);
		tv_id = (TextView) findViewById(R.id.operatordetails_tv3);
		tv_num = (TextView) findViewById(R.id.operatordetails_tv4);
//		tv_role = (TextView) findViewById(R.id.operatordetails_tv5);//定义角色
//		tv_purview = (TextView) findViewById(R.id.operatordetails_tv6);//权限
		
	}

	private void initData() {
		
		
	}

	private void initListener() {
		
		
	}
}
