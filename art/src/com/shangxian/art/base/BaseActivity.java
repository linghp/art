package com.shangxian.art.base;

import android.R.bool;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.http.AbHttpUtil;
import com.shangxian.art.LocationActivity;
import com.shangxian.art.LoginActivity;
import com.shangxian.art.SafetyVerificationActivity;
import com.shangxian.art.alipays.AliPayBase;
import com.shangxian.art.bean.UserInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.LocalUserInfo;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

public class BaseActivity extends AbActivity {
	protected TopView topView;
	protected LocalUserInfo share;
	protected BaseActivity mAc;
	protected MyApplication app;
	protected AbHttpUtil httpUtil;
	protected UserInfo curUserInfo;
	protected LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLogger.i(getClass().getSimpleName());
		share = LocalUserInfo.getInstance(this);
		curUserInfo = share.getUser();
		app = MyApplication.getInstance();
		inflater = LayoutInflater.from(this);
		mAc = this;
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(Constant.timeOut);
		AliPayBase.initContext(this);
	}

	protected void myToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	// 触摸其他区域，输入法界面消失
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);
	}

	public boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			// 获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	protected boolean isLogin() {
		return share.getBoolean(Constant.PRE_LOGIN_STATE, false);
	}

	protected boolean isLoginAndToLogin() {
		if (share.getBoolean(Constant.PRE_LOGIN_STATE, false)) {
			return true;
		} else {
			CommonUtil.gotoActivity(mAc, LoginActivity.class, false);
			return false;
		}
	}

	protected boolean isPayed(boolean isShowDialog) {
		if (isShowDialog && !curUserInfo.isPayed()) {
			final AlertDialog dialog = new AlertDialog.Builder(mAc).setTitle("提示")
					.setMessage("系统检查您尚未设置支付密码，为提升您的用户体验，请尽快设置!")
					.setNegativeButton("去设置", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Bundle bundle = new Bundle();
							bundle.putInt(Constant.INT_SAFE_PAY_NEW, 1);
							CommonUtil.gotoActivityWithData(mAc, SafetyVerificationActivity.class, bundle, false);
						}
					})
					.setNeutralButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					})
					.create();
			dialog.show();
		}
		return curUserInfo.isPayed();
	}
}
