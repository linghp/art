package com.shangxian.art.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.ab.activity.AbActivity;
import com.ab.http.AbHttpUtil;
import com.ab.image.AbImageLoader;
import com.ab.view.pullview.AbPullToRefreshView;
import com.shangxian.art.LoginActivity;
import com.shangxian.art.R;
import com.shangxian.art.SafetyVerificationActivity;
import com.shangxian.art.alipays.AliPayBase;
import com.shangxian.art.bean.UserInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.dialog.RefreshDialog;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.LocalUserInfo;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

public class BaseActivity extends AbActivity{
	protected TopView topView;
	protected LocalUserInfo share;
	protected BaseActivity mAc;
	protected MyApplication app;
	protected AbHttpUtil httpUtil;
	public static UserInfo curUserInfo;
	protected LayoutInflater inflater;
	protected View ll_nodata,ll_loading_big;
	protected ImageView iv_nodata;
	protected AbPullToRefreshView mAbPullToRefreshView;
	protected AbImageLoader mAbImageLoader;
	protected  Dialog refreshDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLogger.i(getClass().getSimpleName());
		share = LocalUserInfo.getInstance(this);
		app = MyApplication.getInstance();
		curUserInfo = share.getUser();
		inflater = LayoutInflater.from(this);
		mAc = this;
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(Constant.timeOut);
		AliPayBase.initContext(this);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		refreshDialog=new RefreshDialog(this, 0); 
		
		mAbImageLoader = AbImageLoader.newInstance(this);
		mAbImageLoader.setLoadingImage(R.drawable.image_loading);
		mAbImageLoader.setErrorImage(R.drawable.image_error);
		mAbImageLoader.setEmptyImage(R.drawable.image_empty);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		if (findViewById(R.id.nodata_ll) != null) {
			ll_nodata = findViewById(R.id.nodata_ll);
			iv_nodata = (ImageView) findViewById(R.id.nodata_icon);
		}
		ll_loading_big=findViewById(R.id.loading_big);
		if(ll_loading_big==null){
			ll_loading_big=new View(this);
		}
		initRefreshView();
	}
	
	private void initRefreshView() {
		mAbPullToRefreshView = (AbPullToRefreshView)findViewById(R.id.mPullRefreshView);
		if(mAbPullToRefreshView!=null){
			// 设置进度条的样式
			mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
					this.getResources().getDrawable(R.drawable.progress_circular));
			mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
					this.getResources().getDrawable(R.drawable.progress_circular));
		}
	}
	
	protected enum NoDataModel { 
		noShop,
		noProduct,
		noCategory,
		noMingxi,
		noMsg,
		noPingjia
	}
	
//	protected void showNoData(NoDataModel model,String res){
//		if (ll_nodata != null) {
//			ll_nodata.setVisibility(View.VISIBLE);
//			switch (model) {
//			case noShop:
//				iv_nodata.setImageResource(R.drawable.noshop);
//				break;
//			case noProduct:
//				iv_nodata.setImageResource(R.drawable.noproduct);
//				break;
//			case noCategory:
//				iv_nodata.setImageResource(R.drawable.nocategory);
//				break;
//			case noMingxi:
//				iv_nodata.setImageResource(R.drawable.nomingxi);
//				break;
//			case noMsg:
//				iv_nodata.setImageResource(R.drawable.nomsg);
//				break;
//			case noPingjia:
//				iv_nodata.setImageResource(R.drawable.nopingjia);
//				break;
//				
//			}
//		}
//	}
	protected void setNoData(NoDataModel model,String str){
		if (ll_nodata != null) {
			ll_nodata.setVisibility(View.VISIBLE);
			if(!TextUtils.isEmpty(str)){
				((TextView)findViewById(R.id.nodata_title)).setText(str);
			}
			switch (model) {
			case noShop:
				iv_nodata.setImageResource(R.drawable.noshop);
				break;
			case noProduct:
				iv_nodata.setImageResource(R.drawable.noproduct);
				break;
			case noCategory:
				iv_nodata.setImageResource(R.drawable.nocategory);
				break;
			case noMingxi:
				iv_nodata.setImageResource(R.drawable.nomingxi);
				break;
			case noMsg:
				iv_nodata.setImageResource(R.drawable.nomsg);
				break;
			case noPingjia:
				iv_nodata.setImageResource(R.drawable.nopingjia);
				break;
				
			}
		}
	}
	
	protected void hideNoData() {
		if (ll_nodata != null) {
			ll_nodata.setVisibility(View.GONE);
		}
	}
	protected void showNoData() {
		if (ll_loading_big != null) {
			ll_nodata.setVisibility(View.VISIBLE);
		}
	}
	protected void hideloading() {
		if (ll_loading_big != null) {
			ll_loading_big.setVisibility(View.GONE);
		}
	}
	protected void showloading() {
		if (ll_nodata != null) {
			ll_loading_big.setVisibility(View.VISIBLE);
		}
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
			final AlertDialog dialog = new AlertDialog.Builder(mAc)
					.setTitle("提示")
					.setMessage("系统检查您尚未设置支付密码，为提升您的用户体验，请尽快设置!")
					.setNegativeButton("去设置",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									Bundle bundle = new Bundle();
									bundle.putInt(
											Constant.INT_SAFE_PAY_NEW,
											SafetyVerificationActivity.PAY_PASS_NEW);
									CommonUtil.gotoActivityWithData(mAc,
											SafetyVerificationActivity.class,
											bundle, false);
								}
							})
					.setNeutralButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).create();
			dialog.show();
		}
		return curUserInfo.isPayed();
	}
	
    protected void showProgressDialog(boolean show) {
        if (show) {
            refreshDialog.show();
        } else {
        	refreshDialog.dismiss();
        }
    }
    
    protected int getUserId(){
    	curUserInfo = share.getUser();
    	return curUserInfo.getId();
    }
    protected String getshopId(){
    	curUserInfo = share.getUser();
    	return curUserInfo.getShopId();
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	JPushInterface.onResume(this);
    }
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	JPushInterface.onPause(this);
    }
}
