package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

/**
 * 紧急结算
 * @author Administrator
 *
 */
public class JinJiJieSuanActivity extends BaseActivity{

	private TextView shenqing; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jinjijiesuan);
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
		shenqing = (TextView) findViewById(R.id.jinjijiesuan_btn);
	}

	private void initData() {
		
		
	}

	private void initListener() {
		shenqing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonUtil.gotoActivity(JinJiJieSuanActivity.this, AskJieSuanActivity.class, false);
			}
		});
		
	}
}
