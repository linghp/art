package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.BuyerReturnOrderInfo;
import com.shangxian.art.bean.BuyerReturnOrderProductInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.BuyerOrderServer;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.Options;
import com.shangxian.art.view.TopView;

public class BuyerReturnOrderDetailsActivity extends BaseActivity {
	private TopView topView;
	private LinearLayout ll_goodsitem_add;
	private ImageView iv_logo;
	private TextView tv_storeName, tv_01, tv_02;
	private final static String INT_BUYER_RETURN_ORDER = "ordernumber";
	private final static String INT_INDEX = "int_index";
	private TextView tv_03;
	private int index;

	private BuyerReturnOrderInfo buyerReturnOrderInfo;
	private List<BuyerReturnOrderProductInfo> buyerReturnOrderProductInfos = new ArrayList<BuyerReturnOrderProductInfo>();
	private ImageLoader loader;

	String[] orderState = { "NORMAL", "SUCCESS", "WAIT_SELLER_APPROVAL",
			"WAIT_BUYER_DELIVERY", "WAIT_COMPLETED", "COMPLETED_REFUSE",
			"ORDER_RETURNING", "CANCELLED", "FAILURE" };
	String[] orderReturnStatus = { "正常，不退货", "退款成功", "等待卖家审核", "等待买家退货",
			"买家已发货,等待卖家签收", "卖家拒绝签收", "已签收，退款成功", "取消", "退货失败" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order_details);
		loader = ImageLoader.getInstance();
		initData();
		initViews();
	}

	public static void startThisActivity_MyOrder(
			BuyerReturnOrderInfo buyerReturnOrderInfo, int index,
			Fragment fragment) {
		Intent intent = new Intent(fragment.getActivity(),
				BuyerReturnOrderDetailsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(INT_BUYER_RETURN_ORDER, buyerReturnOrderInfo);
		bundle.putInt(INT_INDEX, index);
		fragment.startActivityForResult(intent, 1);
	}

	private void initData() {
		if (getIntent().getSerializableExtra(INT_BUYER_RETURN_ORDER) != null) {
			buyerReturnOrderInfo = (BuyerReturnOrderInfo) getIntent()
					.getSerializableExtra(INT_BUYER_RETURN_ORDER);
			index = getIntent().getIntExtra(INT_INDEX, -1);
		} else {
			myToast("请求数据异常");
			finish();
		}
	}

	private void initViews() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_my_order_details));

		iv_logo = (ImageView) findViewById(R.id.iv_logo);
		tv_storeName = (TextView) findViewById(R.id.car_storename);
		ll_goodsitem_add = (LinearLayout) findViewById(R.id.ll_goodsitem_add);
		tv_01 = (TextView) findViewById(R.id.tv_01);
		tv_02 = (TextView) findViewById(R.id.tv_02);
		tv_03 = (TextView) findViewById(R.id.tv_03);
		findViewById(R.id.tv_noaddress).setVisibility(View.GONE);

		updateViews();
	}

	private void updateViews() {
		((TextView) findViewById(R.id.tv_header_01))
				.setText(SellerOrderActivity.map_orderReturnStatusValue
						.get(buyerReturnOrderInfo.getStatus()));
		((TextView) findViewById(R.id.tv_header_02))
				.setText(String.format(getString(R.string.text_order_Price),
						CommonUtil.priceConversion(buyerReturnOrderInfo
								.getTotalPrice())));
		((TextView) findViewById(R.id.tv_header_03)).setVisibility(View.GONE);

		((TextView) findViewById(R.id.tv_receiver)).setText(String.format(
				getString(R.string.text_receiver),
				buyerReturnOrderInfo.getReceiverName()));
		((TextView) findViewById(R.id.tv_address)).setText(String.format(
				getString(R.string.text_receiver_address),
				buyerReturnOrderInfo.getBuyerAddress()));
		((TextView) findViewById(R.id.tv_phone)).setText(buyerReturnOrderInfo
				.getBuyerName());

		((TextView) findViewById(R.id.tv_tradetime)).setText("退货时间:"
				+ buyerReturnOrderInfo.getReturnOrderTime());

		// 动态添加商品
		((TextView) findViewById(R.id.car_storename))
				.setText(buyerReturnOrderInfo.getShippingName());
		// mAbImageLoader_logo.display(holder.iv_logo,Constant.BASEURL
		// + myOrderItem.getShopLogo());
		ll_goodsitem_add.removeAllViews();
		buyerReturnOrderProductInfos = buyerReturnOrderInfo
				.getReturnOrderItemDtos();
		for (int i = 0; i < buyerReturnOrderProductInfos.size(); i++) {
			BuyerReturnOrderProductInfo sellerRefoundOrderProductInfo = buyerReturnOrderProductInfos
					.get(i);
			View child = inflater.inflate(R.layout.list_car_goods_item, null);
			ll_goodsitem_add.addView(child);
			((TextView) child.findViewById(R.id.car_goodsname))
					.setText(sellerRefoundOrderProductInfo.getName());
			// holder.goodsImg = (ImageView)
			// child.findViewById(R.id.car_goodsimg);
			// holder.goodsAttr = (TextView)
			// child.findViewById(R.id.car_goodsattr);
			TextView goodsNum = (TextView) child.findViewById(R.id.car_num);
			TextView goodsPrice = (TextView) child
					.findViewById(R.id.car_goods_price);
			ImageView goodsImg = (ImageView) child
					.findViewById(R.id.car_goodsimg);
			goodsNum.setText("x" + sellerRefoundOrderProductInfo.getQuantity());
			goodsPrice.setText("￥"
					+ CommonUtil.priceConversion(sellerRefoundOrderProductInfo
							.getNowPrice()));

			((CheckBox) child.findViewById(R.id.check_goods))
					.setVisibility(View.GONE);

			ImageLoader.getInstance().displayImage(
					Constant.BASEURL
							+ sellerRefoundOrderProductInfo.getProductSacle(),
					goodsImg, Options.getListOptions(false));
		}

		// 根据状态显示按钮
		final String status = buyerReturnOrderInfo.getStatus();
		if (status.equals(orderState[2])) {
			changeTextViewShow("取消退货", null, "等待卖家审核");
			tv_01.setOnClickListener(new View.OnClickListener() { // CURRENT: //
																	// // 待审核...
				@Override
				public void onClick(View v) {
					new BuyerOrderServer().toBuyerCancelReturnOrder(
							buyerReturnOrderProductInfos.get(0).getId() + "",
							buyerReturnOrderInfo.getReturnOrderNum(),
							new CallBack() {
								@Override
								public void onSimpleSuccess(Object res) {
									myToast("取消成功");
									finish();
								}

								@Override
								public void onSimpleFailure(int code) {
									myToast("取消退货失败");
								}
							});
				}
			});
		} else if (status.equals(orderState[1]) || orderState[8].equals(status)
				|| orderState[6].equals(status)) { // CURRENT: 退货成功...
			changeTextViewShow("删除订单", null,
					orderState[8].equals(status) ? "退货失败" : "退款成功");
			tv_01.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new BuyerOrderServer().toBuyerDeleteReturnOrder(
							buyerReturnOrderInfo.getReturnOrderNum(),
							new CallBack() {
								@Override
								public void onSimpleSuccess(Object res) {
									CommonUtil.toast("删除成功");
									finish();
								}

								@Override
								public void onSimpleFailure(int code) {
									CommonUtil.toast("删除失败");
								}
							});
				}
			});
		} else if (status.equals(orderState[3])) {// CURRENT: 等待买家发货...
			changeTextViewShow(null, "退货", "卖家审核通过");
			tv_02.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					LogisticsInformationActivity.startThisActivity(mAc,
							buyerReturnOrderProductInfos.get(0).getId() + "",
							index, buyerReturnOrderInfo.getReturnOrderNum(),false);
				}
			});
		} else if (orderState[4].equals(status)) {// CURRENT: 买家已发货,等待卖家签收...
			changeTextViewShow(null, null, "等待卖家签收...");
		} else if (orderState[5].equals(status)) {
			// changeTextViewShow(null, null, "已拒绝签收");
			// TODO:
		}
	}

	/**
	 * 设置tv_01、tv_02、tv_03显示文本，如果文本为空，默认设置不显示...
	 * 
	 * @param tv_01_title
	 * @param tv_02_title
	 * @param tv_03_title
	 */
	private void changeTextViewShow(String tv_01_title, String tv_02_title,
			String tv_03_title) {
		tv_01.setText(tv_01_title);
		tv_02.setText(tv_02_title);
		tv_03.setText(tv_03_title);

		tv_01.setVisibility(!TextUtils.isEmpty(tv_01_title) ? View.VISIBLE
				: View.GONE);
		tv_02.setVisibility(!TextUtils.isEmpty(tv_02_title) ? View.VISIBLE
				: View.GONE);
		tv_03.setVisibility(!TextUtils.isEmpty(tv_03_title) ? View.VISIBLE
				: View.GONE);
	}

	@Override
	public void finish() {
		Bundle bundle = new Bundle();
		bundle.putInt("res", index);
		setResult(RESULT_OK, new Intent().putExtras(bundle));
		super.finish();
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		finish();
	}
}
