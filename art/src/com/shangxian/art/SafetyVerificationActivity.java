package com.shangxian.art;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

/**
 * 安全验证（找回登录）
 * @author Administrator
 *
 *找回支付密码
 *iszhifu = true
 *
 */
public class SafetyVerificationActivity extends BaseActivity{

	private TextView num,huoqu,next;
	private EditText yazhengma;
	
	boolean iszhifu = false;
	LinearLayout zhifu;
	TextView question;
	EditText answer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_safetyverification);
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
		topView.setTitle(getString(R.string.title_activity_safetyverification));
		
		num = (TextView) findViewById(R.id.safetverification_num);
		huoqu = (TextView) findViewById(R.id.safetverification_huoqu);//获取验证码
		next = (TextView) findViewById(R.id.safetverification_next);//下一步
		yazhengma = (EditText) findViewById(R.id.safetverification_yanzhengma);//输入验证码
		
		zhifu = (LinearLayout) findViewById(R.id.safetverification_zhi);
		question = (TextView) findViewById(R.id.safetverification_question);//问题
		answer = (EditText) findViewById(R.id.safetverification_answer);//答案
		
		Intent intent = getIntent();
		iszhifu = intent.getBooleanExtra("iszhifu", false);
		if (iszhifu == true) {
			zhifu.setVisibility(View.VISIBLE);
		}
	}

	private void initListener() {
		huoqu.setOnClickListener(new OnClickListener() {
			//获取验证码
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		next.setOnClickListener(new OnClickListener() {
			//下一步
			@Override
			public void onClick(View v) {
				//修改登录密码
				Bundle bundle = new Bundle();
				bundle.putBoolean("iszhaohui", true);
				CommonUtil.gotoActivityWithData(SafetyVerificationActivity.this, ChangePasswordActivity.class, bundle, false);
			}
		});
	}
}
