package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

/**
 * 身份验证
 * @author Administrator
 *
 */
public class IdValidateActivity extends BaseActivity{
	EditText mima,yanzhengma;
	TextView shoujihao,huoqu,shangyibu,xiayibu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idvalidate);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideCenterSearch();//隐藏搜索框
		topView.hideRightBtn_invisible();//隐藏右按钮
		topView.showTitle();//显示title
		topView.setTitle("身份验证");
		topView.setBack(R.drawable.back);//返回
		
		mima = (EditText) findViewById(R.id.idvalidate_mima);//密码
		yanzhengma = (EditText) findViewById(R.id.idvalidate_yanzhengma);//验证码
		
		shoujihao = (TextView) findViewById(R.id.idvalidate_txt2);//短信验证码上面的文字(请输入手机157****8182收到的短信校验码)
		huoqu = (TextView) findViewById(R.id.idvalidate_huoqu);//获取验证码
		shangyibu = (TextView) findViewById(R.id.idvalidate_shangyibu);//下一步
		xiayibu = (TextView) findViewById(R.id.idvalidate_next);//下一步
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}

	private void initListener() {
		// TODO Auto-generated method stub
		huoqu.setOnClickListener(new OnClickListener() {
			//获取验证码
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		shangyibu.setOnClickListener(new OnClickListener() {
			//上一步
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		xiayibu.setOnClickListener(new OnClickListener() {
			//下一步
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
