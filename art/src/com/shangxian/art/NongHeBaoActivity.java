package com.shangxian.art;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CommonBean;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.dialog.CustomOnlyDisplayDialog;
import com.shangxian.art.net.AiNongkaServer;
import com.shangxian.art.net.AiNongkaServer.OnHttpResultQrListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

/**
 * 爱农卡
 * @author zyz
 *
 */
public class NongHeBaoActivity extends BaseActivity implements OnHttpResultQrListener{
	private LinearLayout balance,recharge,cash,profit,bankcard,expenses,qrcode,transaction;
	private int userid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nonghebao);
		initView();
		initData();
		initListener();
		changeview();
	}

	private void initView() {
		// TODO Auto-generated method stub
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideCenterSearch();//隐藏搜索框
		topView.hideRightBtn_invisible();//隐藏右按钮
		topView.showTitle();//显示title
		topView.setTitle("爱农卡");
		topView.setBack(R.drawable.back);//返回
		
		balance = (LinearLayout) findViewById(R.id.nonghebao_linear1);//余额
		recharge = (LinearLayout) findViewById(R.id.nonghebao_linear2);//充值
		cash = (LinearLayout) findViewById(R.id.nonghebao_linear3);//提现
		profit = (LinearLayout) findViewById(R.id.nonghebao_linear4);//收益
		bankcard = (LinearLayout) findViewById(R.id.nonghebao_linear5);//银行卡
		expenses = (LinearLayout) findViewById(R.id.nonghebao_linear6);//收支明细
		transaction = (LinearLayout) findViewById(R.id.nonghebao_linear8);//交易明细
		qrcode = (LinearLayout) findViewById(R.id.nonghebao_linear7);//二维码
	}

	private void initData() {
		userid=getUserId();
	}

	private void changeview() {
		if (isLogin()&&share.getInt(Constant.PRE_USER_LOGINTYPE,0)== 1) {//买家
			qrcode.setVisibility(View.GONE);
			transaction.setVisibility(View.GONE);
		} else if(isLogin()&&share.getInt(Constant.PRE_USER_LOGINTYPE,0)== 2){//卖家
			qrcode.setVisibility(View.VISIBLE);
			transaction.setVisibility(View.VISIBLE);
		}else {//未登录
			qrcode.setVisibility(View.GONE);
			transaction.setVisibility(View.GONE);
		}
	}
	
	private void initListener() {
		// TODO Auto-generated method stub
		balance.setOnClickListener(new OnClickListener() {
			//账户余额
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonUtil.gotoActivity(NongHeBaoActivity.this, BalanceActivity.class, false);
			}
		});
		recharge.setOnClickListener(new OnClickListener() {
			//充值
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonUtil.gotoActivity(NongHeBaoActivity.this, RechargeActivity.class, false);
			}
		});
		cash.setOnClickListener(new OnClickListener() {
			//提现
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonUtil.gotoActivity(NongHeBaoActivity.this, CashActivity.class, false);
			}
		});
		profit.setOnClickListener(new OnClickListener() {
			//农合宝
			@Override
			public void onClick(View v) {
				
				CommonUtil.gotoActivity(NongHeBaoActivity.this, ProfitActivity.class, false);
			}
		});
		bankcard.setOnClickListener(new OnClickListener() {
			//银行卡
			@Override
			public void onClick(View v) {
				
				
			}
		});
		expenses.setOnClickListener(new OnClickListener() {
			//收支明细
			@Override
			public void onClick(View v) {
				
				CommonUtil.gotoActivity(NongHeBaoActivity.this, IandEDetailsActivity.class, false);
			}
		});
		transaction.setOnClickListener(new OnClickListener() {
			//交易明细
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putBoolean("isjiaoyi", true);
				CommonUtil.gotoActivityWithData(NongHeBaoActivity.this, IandEDetailsActivity.class, bundle, false);
			}
		});
		qrcode.setOnClickListener(new OnClickListener() {
			//二维码
			@Override
			public void onClick(View v) {
				if(userid!=Integer.MIN_VALUE){
				AiNongkaServer.onGetQrCode(userid+"",NongHeBaoActivity.this);
				}else{
					myToast("请登录");
				}
			}
		});
	}

	@Override
	public void onHttpResultQr(CommonBean commonBean) {
		if(commonBean!=null){
			if(commonBean.getResult_code().equals("200")){
				CustomOnlyDisplayDialog customOnlyDisplayDialog=new CustomOnlyDisplayDialog(NongHeBaoActivity.this, 0, R.layout.dialog_customonlydisplay);
				customOnlyDisplayDialog.show();
				ImageView imageView=(ImageView) customOnlyDisplayDialog.findViewById(R.id.iv_example);
				mAbImageLoader.display(imageView, Constant.BASEURL+commonBean.getResult());
			}else if(commonBean.getResult_code().equals("400")){
				myToast(commonBean.getResult());
			}
		}else{
			myToast("请求失败");
		}
	}

}
