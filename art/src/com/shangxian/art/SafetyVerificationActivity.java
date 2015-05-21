package com.shangxian.art;

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

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.PasswordServer;
import com.shangxian.art.net.PasswordServer.OnNewSafeCodeListener;
import com.shangxian.art.net.PasswordServer.OnSendCodeListener;
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
	private boolean isNew = false;
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

	private enum UiModel {
		codeing, toCode, isNew, isUp
	}

	private void changeUi(UiModel m) {
		switch (m) {
		case codeing:
			if (codeMin == 120) tv_code.setEnabled(false);
			tv_code.setText(-- codeMin + "S");
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
			et_old.setVisibility(View.GONE);
			break;
		case isUp:
			et_old.setVisibility(View.VISIBLE);
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
	private Editable oldPass;

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
		phone = curUserInfo.getPhoneNumber();
		phone = "<font color='#212121'>点击获取短信验证码，验证码将发送到手机号为</font>"
				+ "<font color='#259b24'>" + getPhone(phone) + "</font>"
				+ "<font color='#212121'>的手机上</font>";
		isNew = getIntent().getIntExtra(Constant.INT_SAFE_PAY_NEW,
				Integer.MIN_VALUE) != Integer.MIN_VALUE;
	}

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
		topView.setTitle(getString(R.string.title_activity_safetyverification));

		tv_code = (TextView) findViewById(R.id.saft_tv_code);
		tv_hint = (TextView) findViewById(R.id.saft_tv_hint);
		tv_cancel = (TextView) findViewById(R.id.saft_tv_cancel);
		tv_ok = (TextView) findViewById(R.id.saft_tv_ok);

		et_code = (EditText) findViewById(R.id.safe_et_code);
		et_old = (EditText) findViewById(R.id.safe_et_old);
		et_new = (EditText) findViewById(R.id.safe_et_new);
		et_reNew = (EditText) findViewById(R.id.safe_et_renew);

		tv_hint.setText(Html.fromHtml(phone));
		if (isNew) {
			changeUi(UiModel.isNew);
		} else {
			changeUi(UiModel.isUp);
		}
	}

	public void doClick(View v) {
		finish();
	}

	private void listener() {
		tv_code.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handler.sendEmptyMessage(codeing);
				PasswordServer.toSendCode(new OnSendCodeListener() {
					@Override
					public void onSendCode(boolean isSend) {
						if (!isSend) {
							myToast("验证码获取失败");
							handler.sendEmptyMessage(tocode);
						}
					}
				});
			}
		});
		
		tv_ok.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				if (match()) {
					final ProgressDialog dialog = new ProgressDialog(mAc);
					dialog.setCanceledOnTouchOutside(false);
					if (isNew) {
						dialog.setMessage("正在保存支付密码...");
						dialog.show();
						PasswordServer.toNewSafeCode(code, pass, rePass, new OnNewSafeCodeListener() {
							@Override
							public void onNewSafeCode(boolean isNew) {
								dialog.dismiss();
								if (isNew) {
									myToast("支付密码设置成功");
									share.put("payed", true);
									curUserInfo = share.getUser();
									finish();
								} else {
									myToast("支付密码设置失败");
									handler.sendEmptyMessage(tocode);
								}
							}
						});
					} else {
						dialog.setMessage("正在修改支付密码...");
						dialog.show();
					}
				}
			}
		});
	}

	protected boolean match() {
		code = et_code.getText().toString();
		pass = et_new.getText().toString();
		rePass = et_reNew.getText().toString();
		if (!isNew) oldPass = et_old.getText();
		if (TextUtils.isEmpty(code)) {
			if (isCodeing) myToast("请输入短信验证码"); else myToast("请获取短信验证码");
			return false;
		}
		if (!isNew && TextUtils.isEmpty(oldPass)) {
			myToast("请输入您当前的支付密码");
			return false;
		}
		if (TextUtils.isEmpty(pass)) {
			myToast("支付密码不能为空");
			return false;
		}
		if (TextUtils.isEmpty(rePass)) {
			myToast("请您再一次输入您的支付密码");
			return false;
		}
		if (!rePass.equals(pass)) {
			myToast("两次密码一致");
			return false;
		}
		return true;
	}
}
