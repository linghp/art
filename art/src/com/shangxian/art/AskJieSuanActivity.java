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
 * 申请结算
 * @author Administrator
 *
 */
public class AskJieSuanActivity extends BaseActivity{
	private EditText name,phone,num,price,content;
	private TextView huoqu,time,quxiao,baocun;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_askjiesuan);
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
		topView.setTitle("紧急结算申请");
		topView.setBack(R.drawable.back);//返回

		name = (EditText) findViewById(R.id.askjiesuan_name);//申请人姓名
		phone = (EditText) findViewById(R.id.askjiesuan_phone);//手机号码
		num = (EditText) findViewById(R.id.askjiesuan_num);//验证码
		price = (EditText) findViewById(R.id.askjiesuan_price);//金额
		content = (EditText) findViewById(R.id.askjiesuan_content);//情况说明

		huoqu = (TextView) findViewById(R.id.askjiesuan_huoqu);//获取
		time  =(TextView) findViewById(R.id.askjiesuan_day);//结算日期
		quxiao = (TextView) findViewById(R.id.askjiesuan_quxiao);//取消
		baocun = (TextView) findViewById(R.id.askjiesuan_baocun);//保存

	}

	private void initData() {


	}

	private void initListener() {
		huoqu.setOnClickListener(new OnClickListener() {
			//获取验证码
			@Override
			public void onClick(View arg0) {


			}
		});
		quxiao.setOnClickListener(new OnClickListener() {
			//取消
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		baocun.setOnClickListener(new OnClickListener() {
			//保存(跳转到已申请)
			@Override
			public void onClick(View arg0) {
			CommonUtil.gotoActivity(AskJieSuanActivity.this, YiJieSuanActivity.class, false);	
			}
		});

	}
}
