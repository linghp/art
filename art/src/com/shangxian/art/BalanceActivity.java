package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.AccountSumInfo;
import com.shangxian.art.net.PayServer;
import com.shangxian.art.net.BaseServer.OnAccountSumListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

/**
 * 账户余额
 * 
 * @author Administrator
 *
 */
public class BalanceActivity extends BaseActivity {
	TextView ainongbi, ainongyuan, cash, recharge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideCenterSearch();// 隐藏搜索框
		topView.hideRightBtn_invisible();// 隐藏右按钮
		topView.showTitle();// 显示title
		topView.setTitle("账户余额");
		topView.setBack(R.drawable.back);// 返回

		ainongbi = (TextView) findViewById(R.id.balance_ainongbi);
		ainongyuan = (TextView) findViewById(R.id.balance_ainongyuan);
		cash = (TextView) findViewById(R.id.balance_tixian);// 提现
		recharge = (TextView) findViewById(R.id.balance_congzhi);// 充值
	}

	private void initData() {
		PayServer.loadAccountSum(new OnAccountSumListener() {
			@Override
			public void onAccountSum(AccountSumInfo info) {
				if (info != null && !info.isNull()) {
					System.out
					.println("account ======================+++++++++++++++++"
							+ info == null ? "null" : info.toString());
					ainongbi.setText(String.format("%.2f", info.getAlb()));
					ainongyuan.setText(String.format("%.2f", info.getAly()));
				}
			}
		});
	}

	private void initListener() {
		// TODO Auto-generated method stub
		cash.setOnClickListener(new OnClickListener() {
			// 提现
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonUtil.gotoActivity(BalanceActivity.this,
						CashActivity.class, false);
			}
		});
		recharge.setOnClickListener(new OnClickListener() {
			// 充值
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonUtil.gotoActivity(BalanceActivity.this,
						RechargeActivity.class, false);
			}
		});
	}

}
