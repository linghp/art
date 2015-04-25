package com.shangxian.art;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

public class ChangePasswordActivity extends BaseActivity{

	private EditText oldpwd,newpwd,newpwd1;
	private TextView quxiao,baocun;
	
	boolean iszhaohui = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepassword);
		initView();
		initListener();
		
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_changepassword));
		
		oldpwd = (EditText) findViewById(R.id.changepasswrod_old);//旧密码
		newpwd = (EditText) findViewById(R.id.changepasswrod_new);//新密码
		newpwd1 = (EditText) findViewById(R.id.changepasswrod_new1);//重复新密码
		baocun = (TextView) findViewById(R.id.changepasswrod_baocun);//保存
		quxiao = (TextView) findViewById(R.id.changepasswrod_quxiao);//取消
		
		//得到参数(找回密码)
		Intent intent = getIntent();
		iszhaohui = intent.getBooleanExtra("iszhaohui", false);
		if (iszhaohui == true) {
			oldpwd.setVisibility(View.GONE);
			topView.setTitle("修改密码");
		}
	}
	private void initListener() {
		
		baocun.setOnClickListener(new OnClickListener() {
			//保存
			@Override
			public void onClick(View v) {
				
			}
		});
		quxiao.setOnClickListener(new OnClickListener() {
			//取消
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}

}
