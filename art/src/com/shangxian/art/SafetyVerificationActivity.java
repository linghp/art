package com.shangxian.art;

import java.util.Currency;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.BaseBean;
import com.shangxian.art.bean.UserInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.PasswordServer;
import com.shangxian.art.net.PasswordServer.OnNewSafeCodeListener;
import com.shangxian.art.net.PasswordServer.OnSendCodeListener;
import com.shangxian.art.net.call.BaseCallBack;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

/**
 * 安全验证（找回登录）
 * 
 * @author Administrator
 *
 *         找回支付密码 iszhifu = true
 *
 */
public class SafetyVerificationActivity extends BaseActivity {
	private TextView tv_code;
	private String phone;
	// private boolean isNew = false;
	private TextView tv_hint;
	private TextView tv_cancel;
	private TextView tv_ok;
	private EditText et_code;
	private EditText et_old;
	private EditText et_new;
	private EditText et_reNew;

	private static final int codeing = 0x00001000;
	private static final int tocode = 0x00001001;
	// private static final int codeing = 0x00001000;

	private int curToPass;
	public static final int PAY_PASS_NEW = 0x00001011;
	public static final int PAY_PASS_UP = 0x00001012;
	public static final int USER_PASS_NEW = 0x00001013;
	public static final int USER_PASS_UP = 0x00001014;
	public static final int USER_PASS_FINDWORD = 0x00001015;

	private boolean isCodeing = false;
	private int codeMin = 120;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case codeing:
				changeUi(UiModel.codeing);
				break;
			case tocode:
				changeUi(UiModel.toCode);
				break;
			}
		};
	};

	public static void toThis(int type, Activity mac) {
		Bundle bundle = new Bundle();
		bundle.putInt(Constant.INT_SAFE_PAY_NEW, type);
		CommonUtil.gotoActivityWithData(mac, SafetyVerificationActivity.class,
				bundle, false);
	}

	private enum UiModel {
		codeing, toCode, isNew, isUp, findPassword
	}

	private void changeUi(UiModel m) {
		switch (m) {
		case codeing:
			if (codeMin == 120)
				tv_code.setEnabled(false);
			tv_code.setText(--codeMin + "S");
			isCodeing = true;
			break;
		case toCode:
			isCodeing = false;
			tv_code.setText("验证码");
			codeMin = 120;
			tv_code.setEnabled(true);
			et_code.setText("");
			break;
		case isNew:
			ll_code.setVisibility(View.VISIBLE);
			tv_hint.setVisibility(View.VISIBLE);
			et_old.setVisibility(View.GONE);
			break;
		case isUp:
			ll_code.setVisibility(View.GONE);
			tv_hint.setVisibility(View.GONE);
			et_old.setVisibility(View.VISIBLE);
			break;
		case findPassword:
			tv_hint.setVisibility(View.GONE);
			et_phone.setVisibility(View.VISIBLE);
			et_old.setVisibility(View.GONE);
			ll_code.setVisibility(View.VISIBLE);
			break;
		}
	}

	Thread codeThread = new Thread() {
		public void run() {
			while (true) {
				if (isCodeing && codeMin >= 0) {
					try {
						if (codeMin <= 0) {
							handler.sendEmptyMessage(tocode);
						} else {
							handler.sendEmptyMessage(codeing);
						}
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	};

	
	private String code;
	private String rePass;
	private String pass;
	private String oldPass;
	private LinearLayout ll_code;
	private TextView tv_passTitle;
	private TextView tv_enum;
	private EditText et_phone;
	private String phonehtml;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_safetyverification);
		initData();
		initView();
		listener();
		codeThread.start();
	}

	private void initData() {
		curToPass = getIntent().getIntExtra(Constant.INT_SAFE_PAY_NEW,
				Integer.MIN_VALUE);
		if (curToPass == Integer.MIN_VALUE) {
			throw new NullPointerException("请传入标示码！用于密码类型...");
		}
		if (!matchCurrent(USER_PASS_FINDWORD)) {
			phone = curUserInfo.getPhoneNumber();
			phonehtml = "<font color='#212121'>点击获取短信验证码，验证码将发送到手机号为</font>"
					+ "<font color='#259b24'>" + getPhone(phone) + "</font>"
					+ "<font color='#212121'>的手机上</font>";
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param phone
	 * @return
	 */
	private String getPhone(String phone) {
		return phone.replace(phone.substring(3, 9), "***");
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(curToPass == PAY_PASS_NEW ? "设置支付密码"
				: curToPass == PAY_PASS_UP ? "修改支付密码"
						: curToPass == USER_PASS_NEW ? "找回登录密码"
								: curToPass == USER_PASS_UP ? "修改登录密码" : "找回密码");

		tv_code = (TextView) findViewById(R.id.saft_tv_code);
		tv_hint = (TextView) findViewById(R.id.saft_tv_hint);
		tv_cancel = (TextView) findViewById(R.id.saft_tv_cancel);
		tv_ok = (TextView) findViewById(R.id.saft_tv_ok);
		tv_passTitle = (TextView) findViewById(R.id.tv_pass_title);
		tv_enum = (TextView) findViewById(R.id.saft_tv_enum);

		et_code = (EditText) findViewById(R.id.safe_et_code);
		et_old = (EditText) findViewById(R.id.safe_et_old);
		et_new = (EditText) findViewById(R.id.safe_et_new);
		et_reNew = (EditText) findViewById(R.id.safe_et_renew);
		et_phone = (EditText) findViewById(R.id.safe_et_phone);

		upDataView();

		ll_code = (LinearLayout) findViewById(R.id.safl_ll_code);
		if (curToPass == PAY_PASS_NEW || curToPass == USER_PASS_NEW) {
			changeUi(UiModel.isNew);
		} else if (matchCurrent(USER_PASS_FINDWORD)) {
			changeUi(UiModel.findPassword);
		} else {
			changeUi(UiModel.isUp);
		}
	}

	private void upDataView() {
		et_old.setHint(curToPass == PAY_PASS_UP ? "请输入当前支付密码" : "请输入当前登录密码");
		et_new.setHint((curToPass == PAY_PASS_NEW || curToPass == PAY_PASS_UP) ? "请输入新的支付密码"
				: "请输入新的登录密码");
		et_reNew.setHint((curToPass == PAY_PASS_NEW || curToPass == PAY_PASS_UP) ? "确认支付密码"
				: "确认登录密码");
		if (!matchCurrent(USER_PASS_FINDWORD))
			tv_hint.setText(Html.fromHtml(phonehtml));
		tv_passTitle.setText(curToPass == PAY_PASS_NEW ? "请输入您的支付密码"
				: curToPass == PAY_PASS_UP ? "请输入支付密码"
						: curToPass == USER_PASS_NEW ? "请输入新的登录密码" : "请输入登录密码");
		tv_enum.setText((curToPass == PAY_PASS_NEW || curToPass == PAY_PASS_UP) ? "(注:支付密码为6位数字)"
				: "(注:登录密码为6-18位数字、字母、部分特殊符号)");
	}

	public void doClick(View v) {
		finish();
	}

	private void listener() {
		tv_code.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (matchCurrent(USER_PASS_FINDWORD)) {
					phone = et_phone.getText().toString();
					if (TextUtils.isEmpty(phone)) {
						myToast("请输入手机号");
						return;
					}
					handler.sendEmptyMessage(codeing);
					RequestParams params = new RequestParams();
					params.addBodyParameter("phoneNumber", phone);
					new HttpUtils().send(HttpMethod.GET,
							Constant.NET_FINDWORD_CODE + phone, null,
							new BaseCallBack<String>() {
								@Override
								public void onSimpleSuccess(
										BaseBean<String> bean) {
									if (bean != null && bean.isSuccess()) {
										myToast("发送成功");
									} else {
										myToast("发送失败");
										handler.sendEmptyMessage(tocode);
									}
								}

								@Override
								public void onSimpleFailure(int code, String res) {
									myToast("发送失败");
									handler.sendEmptyMessage(tocode);
								}
							});
				} else {
					handler.sendEmptyMessage(codeing);
					PasswordServer.toSendCode(curToPass == USER_PASS_NEW,
							new OnSendCodeListener() {
								@Override
								public void onSendCode(boolean isSend) {
									if (!isSend) {
										myToast("验证码获取失败");
									}
								}
							});
				}
			}
		});

		tv_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (match()) {
					final ProgressDialog dialog = new ProgressDialog(mAc);
					dialog.setCanceledOnTouchOutside(false);
					if (matchCurrent(PAY_PASS_NEW)
							|| matchCurrent(USER_PASS_NEW)) {
						dialog.setMessage("正在保存密码...");
						dialog.show();
						PasswordServer.toNewPassword(code, pass, rePass,
								matchCurrent(USER_PASS_NEW),
								new OnNewSafeCodeListener() {
									@Override
									public void onNewSafeCode(boolean isNew) {
										dialog.dismiss();
										if (curToPass == PAY_PASS_NEW) {
											if (isNew) {
												myToast("支付密码设置成功");
												share.put("payed", true);
												curUserInfo = share.getUser();
												finish();
											} else {
												myToast("支付密码设置失败");
												handler.sendEmptyMessage(tocode);
											}
										} else {
											if (isNew) {
												myToast("登录密码设置成功");
												finish();
											} else {
												myToast("登录密码设置失败");
												handler.sendEmptyMessage(tocode);
											}
										}
									}
								});
					} else if (matchCurrent(USER_PASS_UP)
							|| matchCurrent(PAY_PASS_UP)) {
						dialog.setMessage("正在修改密码...");
						dialog.show();
						PasswordServer.toUpPassword(oldPass, pass, rePass,
								matchCurrent(USER_PASS_UP),
								new OnNewSafeCodeListener() {
									@Override
									public void onNewSafeCode(boolean isNew) {
										dialog.dismiss();
										if (curToPass == PAY_PASS_UP) {
											if (isNew) {
												myToast("支付密码修改成功");
												finish();
											} else {
												myToast("支付密码修改失败");
												// handler.sendEmptyMessage(tocode);
											}
										} else {
											if (isNew) {
												myToast("登录密码修改成功");
												finish();
											} else {
												myToast("登录密码修改失败");
												// handler.sendEmptyMessage(tocode);
											}
										}
									}
								});
					} else {
						dialog.setMessage("正在修改...");
						dialog.show();
						RequestParams params = new RequestParams();
						params.addBodyParameter("phoneNumber", phone);
						params.addBodyParameter("captcha", code);
						params.addBodyParameter("newPassword", pass);
						params.addBodyParameter("reNewPassword", rePass);
						new HttpUtils().send(HttpMethod.POST,
								Constant.NET_FINDWORD, params,
								new BaseCallBack<UserInfo>() {
									@Override
									public void onSimpleSuccess(
											BaseBean<UserInfo> bean) {
										dialog.dismiss();
										myToast("修改成功");
										finish();
									}

									@Override
									public void onSimpleFailure(int code,
											String res) {
										myToast("修改失败");
										dialog.dismiss();
									}
								});
					}
				}
			}
		});
	}

	/**
	 * 验证输入有效性
	 * 
	 * @return
	 */
	protected boolean match() {
		code = et_code.getText().toString();
		pass = et_new.getText().toString();
		rePass = et_reNew.getText().toString();
		oldPass = et_old.getText().toString();
		phone = et_phone.getText().toString();
		if (matchCurrent(USER_PASS_FINDWORD) && TextUtils.isEmpty(phone)) {
			myToast("请输入账号绑定的手机号");
			return false;
		}
		if ((matchCurrent(PAY_PASS_NEW) || matchCurrent(USER_PASS_NEW))
				&& TextUtils.isEmpty(code)) {
			if (isCodeing)
				myToast("请输入短信验证码");
			else
				myToast("请获取短信验证码");
			return false;
		}
		if ((curToPass == PAY_PASS_UP || curToPass == USER_PASS_UP)
				&& TextUtils.isEmpty(oldPass)) {
			myToast("请输入您当前的密码");
			return false;
		}
		if (TextUtils.isEmpty(pass)) {
			myToast("密码不能为空");
			return false;
		}
		if (TextUtils.isEmpty(rePass)) {
			myToast("请您再一次输入您的密码");
			return false;
		}
		if (!rePass.equals(pass)) {
			myToast("两次密码一致");
			return false;
		}
		return true;
	}

	private boolean matchCurrent(int type) {
		return curToPass == type;
	}
}
