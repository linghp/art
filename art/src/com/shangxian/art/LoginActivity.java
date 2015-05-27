package com.shangxian.art;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.UserInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.BaseServer.OnLoginListener;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.UserServer;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.LocalUserInfo;
import com.shangxian.art.view.TopView;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private EditText et_pass;
	private EditText et_user;
	private TextView tv_login;
	private TextView tv_find;
	private LinearLayout tv_regist;
	private String user;
	private String pass;
	private ImageView iv_loading;
	private LinearLayout ll_login;
	private Animation anim_r;

	private Handler Handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			user = et_user.getText().toString();
			pass = et_pass.getText().toString();
			if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)) {
				showView(CAN_LOGIN);
			} else {
				showView(NOT_LOGIN);
			}
		};
	};
	private boolean isLoading = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_login);
		initDate();
		initView();
		initListener();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isLogin()) {
			finish();
		}
	}

	public void initDate() {
		anim_r = AnimationUtils.loadAnimation(this, R.anim.rotating);
		anim_r.setInterpolator(new LinearInterpolator());
	}

	public void initView() {
		et_user = (EditText) findViewById(R.id.loge_et_user);
		et_pass = (EditText) findViewById(R.id.loge_et_pass);

		tv_login = (TextView) findViewById(R.id.logt_tv_login);
		tv_find = (TextView) findViewById(R.id.logt_tv_find);
		tv_regist = (LinearLayout) findViewById(R.id.logl_ll_regist);

		iv_loading = (ImageView) findViewById(R.id.logi_iv_loading);

		ll_login = (LinearLayout) findViewById(R.id.logl_ll_login);
		// ll_login.setEnabled(false);

		topView = (TopView) findViewById(R.id.top_title);
		topView.hideCenterSearch();
		topView.setActivity(this);
		topView.setBack(R.drawable.back);
		topView.setTitle("登录");
		topView.showTitle();
		topView.setRightText("商户入驻", new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!isLoading) {
					CommonUtil.gotoActivity(LoginActivity.this,
							RegistSogoActivity.class, false);
				}
			}
		});
		showView(NOT_LOGIN);
	}

	public void initListener() {
		// tv_login.setOnClickListener(this);
		tv_find.setOnClickListener(this);
		tv_regist.setOnClickListener(this);

		ll_login.setOnClickListener(this);
		et_user.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				Handler.sendEmptyMessage(0);
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		et_pass.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				Handler.sendEmptyMessage(0);
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private static final int NOT_LOGIN = 1000;
	private static final int CAN_LOGIN = 1001;
	private static final int LOGINING = 1002;
	private static final int STOP_LOGINING = 1003;
	private static final int LOGIN_SUCCESS = 1004;

	private void showView(int type) {
		switch (type) {
		case NOT_LOGIN:
			ll_login.setEnabled(false);
			break;
		case CAN_LOGIN:
			ll_login.setEnabled(true);
			break;
		case LOGINING:
			tv_find.setEnabled(false);
			tv_regist.setEnabled(false);
			et_user.setEnabled(false);
			et_pass.setEnabled(false);
			isLoading = true;

			tv_login.setText("正在登录...");
			ll_login.setEnabled(false);
			iv_loading.startAnimation(anim_r);
			break;
		case LOGIN_SUCCESS:
			tv_find.setEnabled(true);
			tv_regist.setEnabled(true);
			et_user.setEnabled(true);
			et_pass.setEnabled(true);
			isLoading = false;

			iv_loading.clearAnimation();
			tv_login.setText("登录成功");
			Handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					finish();
				}
			}, 1000);
			break;
		case STOP_LOGINING:
			tv_find.setEnabled(true);
			tv_regist.setEnabled(true);
			et_user.setEnabled(true);
			et_pass.setEnabled(true);
			isLoading = false;

			iv_loading.clearAnimation();
			tv_login.setText("登录失败");
			ll_login.setEnabled(false);
			Handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					et_pass.setText("");
					tv_login.setText("登录");
					Handler.sendEmptyMessage(0);
				}
			}, 1000);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		if (v == ll_login) {
			showView(LOGINING);
			UserServer.toLogin(user, pass, new OnLoginListener() {
				@Override
				public void onLogin(UserInfo info) {
					if (info != null && !info.isNull()) {
						System.out.println("登录========================="
								+ info.toString());
						share.putUser(info);
						showView(LOGIN_SUCCESS);
					} else {
						// myToast("登录失败");
						showView(STOP_LOGINING);
					}
				}
			});
		} else if (v == tv_find) {
			//Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
			LocationActivity.startTihsActivity(Constant.MAP_REGIST_LOC, mAc);
		} else if (v == tv_regist) {
			Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
			startActivity(intent);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int ResultCode, Intent data) {
		if (ResultCode == RESULT_OK && data != null) {
			String address = data.getStringExtra("add");
			double lat = data.getDoubleExtra("lat", Double.MIN_VALUE);
			double lng = data.getDoubleExtra("lng", Double.MIN_VALUE);
			myToast(" add:" + address + " lat:" + lat+ " lng:" + lng);
		}
	}
	
	private void savedata() {
		LocalUserInfo.getInstance(this).setUserInfo("username", user);
	}
}
