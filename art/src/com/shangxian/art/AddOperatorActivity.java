package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

public class AddOperatorActivity extends BaseActivity{

	private EditText et_user,et_name,et_id,et_num;
	private TextView tv_baocun;
	private String user,name,id,num;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addoperator);
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
		
		et_user = (EditText) findViewById(R.id.addsubclass_et1);
		et_name = (EditText) findViewById(R.id.addsubclass_et2);
		et_id = (EditText) findViewById(R.id.addsubclass_et3);
		et_num = (EditText) findViewById(R.id.addsubclass_et4);
		tv_baocun = (TextView) findViewById(R.id.addsubclass_tv);
	}

	private void initData() {
		
		
	}

	private void initListener() {
		tv_baocun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 保存
				user = et_user.getText().toString().trim();//用户名
				name = et_name.getText().toString().trim();//真实姓名
				id = et_id.getText().toString().trim();//身份证
				num = et_num.getText().toString().trim();//电话号码
				
			}
		});
		
	}
}
