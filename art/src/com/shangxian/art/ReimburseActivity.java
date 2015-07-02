package com.shangxian.art;

import android.app.Activity;
import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CommonBean;
import com.shangxian.art.net.MyOrderServer;
import com.shangxian.art.net.MyOrderServer.OnHttpResultRefundListener;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 退款/货申请
 * 
 * @author zyz
 *
 */
public class ReimburseActivity extends BaseActivity implements
		OnHttpResultRefundListener {

	private TextView tv_need, tv_notneed, tv_quxiao, tv_tijiao,tv_txt1;// 需要、不需要、取消、提交
	private ImageView img1,img2,img3;
	private EditText et_cause, et_money, et_explain;// 原因、金额、说明
	private String cause, explain;
	private String money;
	private String orderid, productid, totalprice;
	private LinearLayout ll_linear1,ll_linear8,ll_linear9;
	
	private boolean isGoods;//是否为退货申请   退款/退货  false/true
	private boolean isNeed = true;//是否需要退还货物   需要/不需要  true/false
	/** 是否是直接付款而不是从我的订单去付款*/
	private  boolean isDirectPay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reimburse);
		initView();
		initData();
		initListener();
	}

	public static void startThisActivity_Fragment(boolean isGoods,
			String orderid, String productid, float totalprice, Context mAc,
			Fragment fragment) {
		Intent intent = new Intent(mAc, ReimburseActivity.class);
		intent.putExtra("isGoods", isGoods);
		intent.putExtra("orderid", orderid);
		intent.putExtra("productid", productid);
		intent.putExtra("totalprice", totalprice);
		fragment.startActivityForResult(intent, 111);
	}
	
	public static void startThisActivity(boolean isGoods,
			String orderid, String productid, float totalprice, Activity mAc
			) {
		Intent intent = new Intent(mAc, ReimburseActivity.class);
		intent.putExtra("isGoods", isGoods);
		intent.putExtra("orderid", orderid);
		intent.putExtra("productid", productid);
		intent.putExtra("totalprice", totalprice);
		intent.putExtra("isDirectPay", true);
		mAc.startActivityForResult(intent, 111);
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		
		tv_need = (TextView) findViewById(R.id.reimburse_tv1);
		tv_notneed = (TextView) findViewById(R.id.reimburse_tv2);
		tv_quxiao = (TextView) findViewById(R.id.reimburse_quxiao);
		tv_tijiao = (TextView) findViewById(R.id.reimburse_baocun);

		et_cause = (EditText) findViewById(R.id.reimburse_edit1);
		et_money = (EditText) findViewById(R.id.reimburse_edit2);
		et_explain = (EditText) findViewById(R.id.reimburse_edit3);
		
		img1 = (ImageView) findViewById(R.id.reimburse_img1);
		img2 = (ImageView) findViewById(R.id.reimburse_img2);
		img3 = (ImageView) findViewById(R.id.reimburse_img3);
		tv_txt1 = (TextView) findViewById(R.id.reimburse_txt1);
		ll_linear1 = (LinearLayout) findViewById(R.id.reimburse_linear1);
		ll_linear8 = (LinearLayout) findViewById(R.id.reimburse_linear8);
		ll_linear9 = (LinearLayout) findViewById(R.id.reimburse_linear9);

	}

	private void initData() {
		orderid = getIntent().getStringExtra("orderid");
		productid = getIntent().getStringExtra("productid");
		isGoods = getIntent().getBooleanExtra("isGoods", false);
		isDirectPay=getIntent().getBooleanExtra("isDirectPay", false);
		if (isGoods) {
			topView.setTitle(getString(R.string.text_main_reimburse));//退货申请
			
		} else {
			topView.setTitle(getString(R.string.title_activity_refund));//退款申请
			tv_txt1.setVisibility(View.GONE);
			ll_linear1.setVisibility(View.GONE);
			ll_linear8.setVisibility(View.GONE);
			ll_linear9.setVisibility(View.GONE);
		}
	}

	private void initListener() {
		tv_need.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//需要退货
				isNeed = true;
				tv_need.setBackgroundResource(R.drawable.leftcorner_green);
				tv_notneed.setBackgroundResource(R.drawable.rightcorner);
				tv_need.setTextColor(getResources().getColor(R.color.txt_white));
				tv_notneed.setTextColor(getResources().getColor(R.color.txt_black1));
			}
		});
		tv_notneed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 不需要退货
				isNeed = false;
				tv_need.setBackgroundResource(R.drawable.leftcorner);
				tv_notneed.setBackgroundResource(R.drawable.rightcorner_green);
				tv_need.setTextColor(getResources().getColor(R.color.txt_black1));
				tv_notneed.setTextColor(getResources().getColor(R.color.txt_white));
			}
		});
		tv_quxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 取消
				finish();
			}
		});
		tv_tijiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (match()) {
					if (isGoods) {
						//退货
						MyOrderServer.toRequestRefund("true", productid,
								orderid, money, cause, explain,
								ReimburseActivity.this);
						
					} else {
						//退款
						MyOrderServer.toRequestRefund("false", productid,
								orderid,money, cause, explain,
								ReimburseActivity.this);
					}
				}
			}
		});
	}

	private boolean match() {
		cause = et_cause.getText().toString().trim();
		String money_str = et_money.getText().toString().trim();
		explain = et_explain.getText().toString().trim();
		if (TextUtils.isEmpty(cause)) {
			myToast("退款原因不能为空");
			return false;
		} else if (TextUtils.isEmpty(money_str)) {
			myToast("退款金额不能为空");
			return false;
		}else {
			try {
				double money_temp=Double.parseDouble(money_str);
				money=(int)(money_temp*100)+"";
				MyLogger.i(money);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				myToast("退款金额格式错误");
				return false;
			}
		}
		return true;
	}

	@Override
	public void onHttpResultRefund(CommonBean<Object> commonBean) {
		if (commonBean != null) {
			if (commonBean.getResult_code().equals("200")) {
				myToast("等待卖家审核");
				if(!isDirectPay){
				MyOrderActivity.currentFragment
						.setNeedFresh_Refund(MyOrderActivity.orderReturnStatus[2]);
				}
				finish();
			}
		} else {
			myToast("退款失败");
		}
	}
}
