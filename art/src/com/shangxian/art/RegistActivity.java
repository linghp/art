package com.shangxian.art;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CommonBean;
import com.shangxian.art.bean.UserInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.HttpUtils;
import com.shangxian.art.net.RegisterServer;
import com.shangxian.art.net.RegisterServer.OnHttpResultListener;
import com.shangxian.art.view.TopView;

import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.TextUtils;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegistActivity extends BaseActivity implements OnClickListener,
		OnHttpResultListener {

	private EditText et_repass;
	private EditText et_pass;
	private EditText et_yan;
	private EditText et_phone;
	private ImageView iv_check;
	private TextView tv_ti;
	private TextView tv_getyan;
	private TextView tv_phone;
	private TextView tv_read;
	private TextView tv_toregist;
	private TextView tv_toyan;
	private LinearLayout ll_li1;
	private LinearLayout ll_li2;
	private LinearLayout ll_li3;
	private Animation anim_right_in;
	private Animation anim_left_out;
	private String phone, captcha, pass, repass;
	private int currentStep = 1;// 现在的步骤

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_regist);
		initDate();
		initView();
		initListener();
	}

	private void initDate() {
		anim_right_in = AnimationUtils
				.loadAnimation(this, R.anim.anim_right_in);
		anim_left_out = AnimationUtils
				.loadAnimation(this, R.anim.anim_left_out);
	}

	private void initView() {
		// 改变topbar
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_regist));

		tv_ti = (TextView) findViewById(R.id.regt_tv_ti1);
		tv_getyan = (TextView) findViewById(R.id.regt_tv_getyan);
		tv_phone = (TextView) findViewById(R.id.regt_tv_phone);
		tv_read = (TextView) findViewById(R.id.regt_tv_read);
		tv_toregist = (TextView) findViewById(R.id.regt_tv_toregist);
		tv_toyan = (TextView) findViewById(R.id.regt_tv_toyan);

		iv_check = (ImageView) findViewById(R.id.regi_iv_ch);

		et_phone = (EditText) findViewById(R.id.rege_et_phone);
		et_yan = (EditText) findViewById(R.id.rege_et_yan);
		et_pass = (EditText) findViewById(R.id.rege_et_pass);
		et_repass = (EditText) findViewById(R.id.rege_et_repass);

		ll_li1 = (LinearLayout) findViewById(R.id.regl_ll_li1);
		ll_li2 = (LinearLayout) findViewById(R.id.regl_ll_li2);
		ll_li3 = (LinearLayout) findViewById(R.id.regl_ll_li3);
		iv_check.setSelected(isCheck);
		showView(TI1);
	}

	private static final int TI1 = 1001;
	private static final int TI2 = 1002;
	private static final int TI3 = 1003;
	private static final String TIT1 = "<font color=\"#259b24\">1  输入手机号</font> <font color=\"#de212121\"> > 2  输入验证码 > 3  设置密码</font>";
	private static final String TIT2 = "<font color=\"#de212121\">1  输入手机号  > </font> <font color=\"#259b24\">2  输入验证码 </font> <font color=\"#de212121\"> > 3  设置密码</font>";
	private static final String TIT3 = "<font color=\"#de212121\">1  输入手机号  > </font> <font color=\"#de212121\">2  输入验证码  > </font> <font color=\"#259b24\">3  设置密码</font>";

	private void showView(int show) {
		switch (show) {
		case TI1:
			tv_ti.setText(Html.fromHtml(TIT1));
			ll_li1.setVisibility(View.VISIBLE);
			ll_li2.setVisibility(View.GONE);
			ll_li3.setVisibility(View.GONE);
			break;
		case TI2:
			tv_ti.setText(Html.fromHtml(TIT2));
			ll_li1.startAnimation(anim_left_out);
			ll_li1.setVisibility(View.GONE);
			ll_li2.startAnimation(anim_right_in);
			ll_li2.setVisibility(View.VISIBLE);
			ll_li3.setVisibility(View.GONE);
			//变成136****1111
			char[] phones=phone.toCharArray();
			for (int i = 0; i < phones.length; i++) {
				if(i>2&&i<7){
					phones[i]='*';
				}
			}
			String phonestar=new String(phones);
			((TextView)findViewById(R.id.regt_tv_phone)).setText(String.format(getString(R.string.text_register2_phone), phonestar));
			currentStep = 2;
			break;
		case TI3:
			tv_ti.setText(Html.fromHtml(TIT3));
			ll_li1.setVisibility(View.GONE);
			ll_li2.startAnimation(anim_left_out);
			ll_li2.setVisibility(View.GONE);
			ll_li3.startAnimation(anim_right_in);
			ll_li3.setVisibility(View.VISIBLE);
			currentStep = 3;
			break;
		}
	}

	private void initListener() {
		tv_getyan.setOnClickListener(this);
		// tv_phone.setOnClickListener(this);
		tv_read.setOnClickListener(this);
		tv_toregist.setOnClickListener(this);
		tv_toyan.setOnClickListener(this);

		iv_check.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == tv_getyan) {
			if (matchsPhone()) {
				if (isCheck) {
					if (HttpUtils.checkNetWork(this)) {
						RegisterServer.toRegidter1(phone, this);
					}
				} else {
					myToast("请阅读爱农宝用户协议");
				}
			}
		} else if (v == tv_read) {

		} else if (v == tv_toyan) {
			if (matchsCaptcha()) {
				if (HttpUtils.checkNetWork(this)) {
					RegisterServer.toRegidter2(phone, captcha, this);
				}
			}
		} else if (v == tv_toregist) {
			if (matchsPassword()) {
				if (HttpUtils.checkNetWork(this)) {
					RegisterServer.toRegidter3(phone, captcha, pass, repass,
							this);
				}
			}
		} else if (v == iv_check) {
			check();
		}
	}

	private boolean isCheck = true;

	private void check() {
		isCheck = !isCheck;
		iv_check.setSelected(isCheck);
	}

	/**
	 * 验证手机号
	 * 
	 * @return
	 */
	private boolean matchsPhone() {
		phone = et_phone.getText().toString().trim();
		if (TextUtils.isEmpty(phone)) {
			myToast("请输入您的手机号");
			return false;
		}
		if (!phone.matches("^1[3|4|5|7|8][0-9]{9}$")) {
			myToast("您输入的手机号为无效手机号");
			return false;
		}
		return true;
	}

	/**
	 * 验证验证码
	 * 
	 * @return
	 */
	private boolean matchsCaptcha() {
		captcha = et_yan.getText().toString().trim();
		if (TextUtils.isEmpty(captcha)) {
			myToast("请输入您的验证码");
			return false;
		}
		if (!captcha.matches("[0-9]{6}$")) {
			myToast("您输入的验证码格式不对");
			return false;
		}
		return true;
	}

	/**
	 * 验证密码
	 * 
	 * @return
	 */
	private boolean matchsPassword() {
		pass = et_pass.getText().toString().trim();
		repass = et_repass.getText().toString().trim();
		if (TextUtils.isEmpty(pass)) {
			myToast("请输入您的设置密码");
			return false;
		}
		if (TextUtils.isEmpty(repass)) {
			myToast("请输入您的确认密码");
			return false;
		}
		if (!pass.equals(repass)) {
			myToast("两次输入的密码不一致");
			return false;
		}

		return true;
	}

	@Override
	public void onHttpResult(CommonBean info) {
		if (info != null) {
			if (info.getReason().equals("success")) {
				if (currentStep == 1) {
					showView(TI2);
				} else if (currentStep == 2) {
					showView(TI3);
				} else {
					if (info.getObject() != null) {
						UserInfo userInfo = (UserInfo) info.getObject();
						if (userInfo != null) {
							myToast("注册成功");
							share.putUser(userInfo);
						}
					}
					finish();
				}
				myToast(info.getResult());
			}else{
				myToast(info.getResult());
			}
		}else{
			myToast("服务器错误");
		}
	}
}
