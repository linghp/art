package com.shangxian.art;

import android.os.Bundle;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;


/**
 * 添加子类
 * @author Administrator
 *
 */
public class AddSubClassActivity extends BaseActivity{
	TextView xuanze,addimg,addname,quxiao,baocun;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addsubclass);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		xuanze = (TextView) findViewById(R.id.addsubclass_xuanze);
		addimg = (TextView) findViewById(R.id.addsubclass_addimg);
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}

	private void initListener() {
		// TODO Auto-generated method stub
		
	}

}
