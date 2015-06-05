package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

/**
 * 手机验证
 * @author Administrator
 *
 */
public class PhoneValidateActivity extends BaseActivity{

	private TextView huoqu,quxiao,baocun;
	private EditText phone,yazhengma;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonevalidate);
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
		topView.setTitle(getString(R.string.title_activity_phonevalidate));
		
		quxiao = (TextView) findViewById(R.id.phonevalidate_quxiao);//取消
		huoqu = (TextView) findViewById(R.id.phonevalidate_huoqu);//获取验证码
		baocun = (TextView) findViewById(R.id.phonevalidate_baocun);//保存
		phone = (EditText) findViewById(R.id.phonevalidate_num);//手机号
		yazhengma = (EditText) findViewById(R.id.phonevalidate_yanzhengma);//输入验证码
		
	}

	private void initListener() {
		huoqu.setOnClickListener(new OnClickListener() {
			//获取验证码
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		quxiao.setOnClickListener(new OnClickListener() {
			//取消
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		baocun.setOnClickListener(new OnClickListener() {
			//保存
			@Override
			public void onClick(View v) {
			}
		});
	}
}
