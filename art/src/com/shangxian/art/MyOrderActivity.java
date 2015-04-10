package com.shangxian.art;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MyOrderActivity extends BaseActivity {
	private TopView topView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);
		initViews();
		initdata();
		listener();
	}
	private void listener() {
		// TODO Auto-generated method stub
		
	}
	private void initdata() {
		// TODO Auto-generated method stub
		
	}
	private void initViews() {
		//改变topbar
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_my_order));
	}


}
