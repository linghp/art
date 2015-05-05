package com.shangxian.art;

import android.os.Bundle;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

/**
 * 
 * @author Administrator
 *
 */
public class YiJieSuanActivity extends BaseActivity{

	TextView num,price,jindu,jiesuantime,name,phone,shenqingtime,chexiao,chaxun;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yishenqing);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideCenterSearch();//隐藏搜索框
		topView.hideRightBtn_invisible();//隐藏右按钮
		topView.showTitle();
		topView.setTitle("紧急结算");
		topView.setBack(R.drawable.back);//返回
		
		
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}

	private void initListener() {
		// TODO Auto-generated method stub
		
	}
}
