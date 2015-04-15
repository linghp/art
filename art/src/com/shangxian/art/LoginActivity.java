package com.shangxian.art;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.UserInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.BaseServer.OnLoginListener;
import com.shangxian.art.net.UserServer;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.LocalUserInfo;
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
		topView.setActivity(this);
		topView.setBack(R.drawable.back);
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
				UserServer.toLogin(user, pass, new OnLoginListener() {
					@Override
					public void onLogin(UserInfo info) {
						if (info != null && !info.isNull()) {
							System.out.println("登录=========================" + info.toString());
							myToast("登录成功");
							share.putUser(info);
							share.put(Constant.PRE_LOGIN_USERNAME, user);
							share.put(Constant.PRE_LOGIN_PASSWORD, pass);
							share.put(Constant.PRE_LOGIN_LASTTIME, System.currentTimeMillis());
							share.put(Constant.PRE_LOGIN_STATE, true);
							finish();
						} else {
							myToast("登录失败");
						}
					}
				});
			}
		} else if (v == tv_find) {
			Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
		} else if (v == tv_regist) {
			Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
			startActivity(intent);
		}
	}

	private void savedata() {
		LocalUserInfo.getInstance(this).setUserInfo("username", user);
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
