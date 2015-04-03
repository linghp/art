package com.shangxian.art;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText et_pass;
	private EditText et_user;
	private TextView tv_login;
	private TextView tv_find;
	private TextView tv_regist;
	private String user;
	private String pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_login);
		initDate();
		initView();
		initListener();
	}

	public void initDate() {

	}

	public void initView() {
		et_user = (EditText) findViewById(R.id.loge_et_user);
		et_pass = (EditText) findViewById(R.id.loge_et_pass);
		
		tv_login = (TextView) findViewById(R.id.logt_tv_login);
		tv_find = (TextView) findViewById(R.id.logt_tv_find);
		tv_regist = (TextView) findViewById(R.id.logt_tv_regist);
		
		topView = (TopView) findViewById(R.id.top_title);
		topView.hideCenterSearch();
		topView.hideLeftBtn();
		topView.hideBackBtn();
		topView.setTitle("登录");
		topView.showTitle();
		topView.setRightText("商户入驻", new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				CommonUtil.gotoActivity(LoginActivity.this, RegistSogoActivity.class, false);
			}
		});
	}

	public void initListener() {
		tv_login.setOnClickListener(this);
		tv_find.setOnClickListener(this);
		tv_regist.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == tv_login) {
			if (mathLogin()) {
				Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
				savedata();
				finish();
			}
		} else if (v == tv_find) {
			Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
		} else if (v == tv_regist) {
			// Toast.makeText(this, "注册", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
			startActivity(intent);
		}
	}

	private void savedata() {
		SharedPreferences preferences = getSharedPreferences("userinfo",
				MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("username", user);
		editor.commit();
	}

	private boolean mathLogin() {
		user = et_user.getText().toString();
		pass = et_pass.getText().toString();
		if (TextUtils.isEmpty(user)) {
			return false;
		}
		if (TextUtils.isEmpty(pass)) {
			return false;
		}
		return true;
	}
}
