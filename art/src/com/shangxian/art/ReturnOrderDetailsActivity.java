package com.shangxian.art;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.fragment.BuyerReturnOrderFragment;
import com.shangxian.art.view.TopView;

/**
 * 退货订单详情
 * @author zyz
 *
 */
public class ReturnOrderDetailsActivity extends BaseActivity{
	private TextView tv_time,tv_return,tv_goods,tv_istrue,tv_money,tv_reason,tv_explain;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_returnorderdetails);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_returnorderdetails));
		
		tv_time = (TextView) findViewById(R.id.returnorderdetails_tv1);//退款时间
		tv_return = (TextView) findViewById(R.id.returnorderdetails_tv2);//退款状态
		tv_goods = (TextView) findViewById(R.id.returnorderdetails_tv3);//货物状态
		tv_istrue = (TextView) findViewById(R.id.returnorderdetails_tv4);//是否需要退还货物
		tv_money = (TextView) findViewById(R.id.returnorderdetails_tv5);//退还金额
		tv_reason = (TextView) findViewById(R.id.returnorderdetails_tv6);//退款原因
		tv_explain = (TextView) findViewById(R.id.returnorderdetails_tv7);//退款说明
	}

	public static void startThisActivity(String time,String returns,String goods,String istrue,String money, String reason,String explain,Context context) {
		Intent intent = new Intent(context, ReturnOrderDetailsActivity.class);
		intent.putExtra("time", time);
		intent.putExtra("returns", returns);
		intent.putExtra("goods", goods);
		intent.putExtra("istrue", istrue);
		intent.putExtra("money", money);
		intent.putExtra("reason", reason);
		intent.putExtra("explain", explain);
		context.startActivity(intent);
	}
	
	private void initData() {
		String time = getIntent().getStringExtra("time");
		tv_time.setText(time);//退款时间
		String returns = getIntent().getStringExtra("returns");
		switch (returns) {
		case "SUCCESS":
			tv_return.setText("退款成功");
			break;
		case "WAIT_SELLER_APPROVAL":
			tv_return.setText("等待卖家审核");
			break;
		case "WAIT_BUYER_DELIVERY":
			tv_return.setText("卖家审核通过");
			break;
		case "WAIT_COMPLETED":
			tv_return.setText("买家已发货,等待卖家签收");
			break;
		case "CANCELLED":
			tv_return.setText("订单已取消");
			break;
		///////////////////////////////////////////////
		case "NORMAL":
			tv_return.setText("正常，不退货");
			break;
		case "COMPLETED_REFUSE":
			tv_return.setText("卖家拒绝签收");
			break;
		case "ORDER_RETURNING":
			tv_return.setText("已签收，退款成功");
			break;
		case "FAILURE":
			tv_return.setText("退货失败");
			break;
		default:
			break;
		}
		/*if (returns == "SUCCESS") {
			tv_return.setText("退款成功");
		}else if (returns == "WAIT_SELLER_APPROVAL") {
			tv_return.setText("等待卖家审核");
		}else if (returns == "WAIT_BUYER_DELIVERY") {
			tv_return.setText("卖家审核通过");
		}else if (returns == "WAIT_COMPLETED") {
			tv_return.setText("买家已发货,等待卖家签收");
		}else if (returns == "CANCELLED") {
			tv_return.setText("订单已取消");
		}else {
			tv_return.setText("订单正常");
		}*/
//		String goods = getIntent().getStringExtra("goods");
//		tv_goods.setText(goods);//货物状态
//		String istrue = getIntent().getStringExtra("istrue");
		tv_istrue.setText("是");//是否需要退还货物
		String money = getIntent().getStringExtra("money");
		tv_money.setText(money);//退还金额
//		String reason = getIntent().getStringExtra("reason");
//		tv_reason.setText(reason);//退款原因
//		String explain = getIntent().getStringExtra("explain");
//		tv_explain.setText(explain);//退款说明	
	}

	private void initListener() {
		
		
	}

	public static void startThisActivity(String returnOrderTime,
			String returnOrderTime2, String returnOrderTime3,
			String returnOrderTime4, String returnOrderTime5,
			String returnOrderTime6, String returnOrderTime7,
			BuyerReturnOrderFragment buyerReturnOrderFragment) {
		// TODO Auto-generated method stub
		
	}
}
