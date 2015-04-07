package com.shangxian.art;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class ConfirmOrderActivity extends BaseActivity {
	private static TopView topView;
	private ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order);
		initViews();
	}

	private void initViews() {
		//改变topbar
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_confirm_order));
		
		listview=(ListView) findViewById(R.id.listview);
	}
}
