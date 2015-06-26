package com.shangxian.art;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shangxian.art.alipays.AliPayBase.OnPayListener;
import com.shangxian.art.alipays.AliPayServer;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 充值
 * @author Administrator
 *
 */
public class RechargeActivity extends BaseActivity implements OnPayListener{

	EditText et_money;
	ImageView zhifubao,yinhangka;
	LinearLayout zhifubaolinear,yinhangkalinear;
	TextView quxiao,queren;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideCenterSearch();//隐藏搜索框
		topView.hideRightBtn_invisible();//隐藏右按钮
		topView.showTitle();//显示title
		topView.setTitle("充值");
		topView.setBack(R.drawable.back);//返回

		et_money = (EditText) findViewById(R.id.recharge_balance);

		zhifubao = (ImageView) findViewById(R.id.recharge_zhifubao);
		yinhangka = (ImageView) findViewById(R.id.recharge_yinhangka);
		zhifubaolinear = (LinearLayout) findViewById(R.id.recharge_zhifubao_linear);
		yinhangkalinear = (LinearLayout) findViewById(R.id.recharge_yinhangka_linear);

		quxiao = (TextView) findViewById(R.id.recharge_quxiao);
		queren = (TextView) findViewById(R.id.recharge_queren);

	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void initListener() {
		// TODO Auto-generated method stub
		zhifubaolinear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				zhifubao.setImageResource(R.drawable.sel_t);
				yinhangka.setImageResource(R.drawable.sel_n);
			}
		});
		yinhangkalinear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				zhifubao.setImageResource(R.drawable.sel_n);
				yinhangka.setImageResource(R.drawable.sel_t);
			}
		});
		quxiao.setOnClickListener(new OnClickListener() {
			//取消
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		queren.setOnClickListener(new OnClickListener() {
			//确认
			@Override
			public void onClick(View v) {
				String money=et_money.getText().toString().trim();
				int userid=getUserId();
				if(userid>0){
					if(!TextUtils.isEmpty(money)&&money.charAt(0)>='0'&&money.charAt(0)<='9'){
				AliPayServer.toRecharge("充值", userid+"", money, RechargeActivity.this);
				     }else{
					myToast("请输入充值金额或检查格式");
				   }
				}else{
					myToast("请登录");
				}
			}
		});
	}

	@Override
	public void onSuccess(String res) {
		//MyLogger.i(res);
		finish();
	}

	@Override
	public void onFailed(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on8000() {
		// TODO Auto-generated method stub
		
	}
}
