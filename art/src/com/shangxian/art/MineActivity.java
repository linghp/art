package com.shangxian.art;

import java.io.File;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.util.AbFileUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.HttpUtils;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.LocalUserInfo;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.utils.Options;

/**
 * 我的
 * 
 * @author Administrator
 *
 */
public class MineActivity extends BaseActivity implements OnClickListener {
	private View ll_loginbefore, ll_loginafter;
	private TextView tv_username;
	private ImageView user_head;

	private String userphoto_filename;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_mine);
		initView();
		initListener();
	}

	private void initListener() {
		findViewById(R.id.mine).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MineActivity.this,
						LoginActivity.class);
				startActivityForResult(intent, 1);
			}
		});

	}

	private void initView() {
		ll_loginbefore = findViewById(R.id.ll_loginbefore);
		ll_loginafter = findViewById(R.id.ll_loginafter);
		user_head = (ImageView) findViewById(R.id.user_head);

	}

	private void changeview() {
		if (isLogin()) {
			ll_loginbefore.setVisibility(View.GONE);
			ll_loginafter.setVisibility(View.VISIBLE);
			tv_username = (TextView) findViewById(R.id.tv_username);
			tv_username.setText(share.getString(Constant.PRE_USER_NICKNAME, ""));
		} else {
			ll_loginbefore.setVisibility(View.VISIBLE);
			ll_loginafter.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// changeview();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		topView = MainActivity.getTopView();
		topView.setActivity(this);
		topView.hideLeftBtn();
		topView.showRightBtn();
		topView.setRightBtnDrawable(R.drawable.settingbuttonimage);
		topView.setRightBtnListener(this);
		topView.hideCenterSearch();
		topView.setCenterListener(null);
		topView.setTitle("我的");
		topView.showTitle();

		userphoto_filename = LocalUserInfo.getInstance(this).getUserInfo(
				LocalUserInfo.USERPHOTO_FILENAME);
		changeview();
		// 显示头像
		if (isLogin()) {
			String userphoto_filename_temp = LocalUserInfo.getInstance(this)
					.getUserInfo(LocalUserInfo.USERPHOTO_FILENAME);
			String imagelocaldir = AbFileUtil.getImageDownloadDir(this)
					+ File.separator;
			if (!TextUtils.isEmpty(userphoto_filename_temp)) {
				ImageLoader.getInstance().displayImage(Constant.BASEURL + userphoto_filename_temp, user_head, Options.getListOptions());
			}
		} else {
			user_head.setImageResource(R.drawable.defaultloginheader);
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		MyLogger.i("onStop");
	}

	public void doClick(View view) {
		switch (view.getId()) {

		case R.id.ll_tab1:
			// 商品管理
			CommonUtil.gotoActivity(MineActivity.this,
					MerchandiseControlActivity.class, false);
			break;
		case R.id.ll_tab2:
			// 订单管理
			break;
		case R.id.ll_tab3:
			// 商铺管理
			Bundle bundle1 = new Bundle();
			bundle1.putBoolean("isshangpu", true);
			CommonUtil.gotoActivityWithData(MineActivity.this,
					MerchandiseControlActivity.class, bundle1, false);
			break;
		case R.id.ll_tab4:
			// 结算中心224592
			Bundle bundle = new Bundle();
			bundle.putBoolean("isjiesuan", true);
			CommonUtil.gotoActivityWithData(MineActivity.this,
					MerchandiseControlActivity.class, bundle, false);
			break;
		case R.id.ll_my_item1:
			// 我的订单
			if (HttpUtils.checkNetWork(this) && isLoginAndToLogin()) {
				startActivity(new Intent(this, MyOrderActivity.class));
			}
			break;
		case R.id.ll_my_item2:
			// 爱农卡
			if (isLoginAndToLogin())
				startActivity(new Intent(this, NongHeBaoActivity.class));
			break;
		case R.id.ll_my_item3:
			// 我的关注
			if (isLoginAndToLogin())
				startActivity(new Intent(this, MyConcernActivity.class));
			break;
		case R.id.ll_my_item4:
			// 我的消息
			if (isLoginAndToLogin())
				startActivity(new Intent(this, MyMessageActivity.class));
			break;
		case R.id.ll_my_item5:
			// 我的预付
			break;
		case R.id.ll_my_item6:
			// 商品评价
			break;
		case R.id.ll_my_item7:
			// 账户与安全
			startActivityForResult((new Intent(this,
					AccountSecurityActivity.class)), 2);
			break;
		case R.id.ll_my_item8:
			// 退货/售后
			break;
		case R.id.ll_my_item9:
			// 意见反馈
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_right:
			startActivityForResult((new Intent(this,
					AccountSecurityActivity.class)), 2);
			break;
		default:
			break;
		}
	}
}
