package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.shangxian.art.adapter.MyOrderListAdapter;
import com.shangxian.art.adapter.MyOrderListAdapter1;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.MyOrderDetailBean;
import com.shangxian.art.bean.MyOrderDetailBean.ReceiverInfo;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.ProductItemDto;
import com.shangxian.art.bean.MyOrderDetailBean.OrderItem;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.fragment.MyOrder_All_Fragment;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.MyOrderServer;
import com.shangxian.art.net.MyOrderServer.OnHttpResultCancelOrderListener;
import com.shangxian.art.net.MyOrderServer.OnHttpResultDelOrderListener;
import com.shangxian.art.net.MyOrderServer.OnHttpResultOrderDetailsListener;
import com.shangxian.art.net.SellerOrderServer;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

public class SellerOrderDetailsActivity extends BaseActivity {
	private TopView topView;
	private LinearLayout ll_goodsitem_add;
	private ImageView iv_logo;
	private TextView tv_storeName, tv_01, tv_02;
	private final static String INTENTDATAKEY = "ordernumber";
	private final static String INT_INDEX = "int_index";
	private MyOrderDetailBean myOrderDetailBean;
	private MyOrderItem myOrderItem;
	private AbImageLoader mAbImageLoader_logo, mAbImageLoader_goodsImg;
	private TextView tv_03,tv_dingdan,tv_jiaoyi;
	private int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order_details);
		initViews();
		initData();
	}

//	public static void startThisActivity(String ordernumber, Context context) {
//		Intent intent = new Intent(context, SellerOrderDetailsActivity.class);
//		intent.putExtra(INTENTDATAKEY, ordernumber);
//		((Activity) context).startActivityForResult(intent, 1);
//	}

	public static void startThisActivity_MyOrder(String ordernumber, int index,
			Activity mAc) {
		Intent intent = new Intent(mAc, SellerOrderDetailsActivity.class);
		intent.putExtra(INTENTDATAKEY, ordernumber);
		intent.putExtra(INT_INDEX, index);
		mAc.startActivityForResult(intent, 1);
	}

	private void initData() {
		String ordernumber = getIntent().getStringExtra(INTENTDATAKEY);
		index = getIntent().getIntExtra(INT_INDEX, -1);
		if (!TextUtils.isEmpty(ordernumber)) {
			myOrderItem = new MyOrderItem();
			myOrderItem.setOrderNumber(ordernumber);
			SellerOrderServer.toGetSellerOrderDetails(ordernumber,
					new CallBack() {
						@Override
						public void onSimpleSuccess(Object res) {
							if (res != null) {
								myOrderDetailBean = (MyOrderDetailBean) res;
								myOrderItem.setOrderNumber(myOrderDetailBean
										.getOrderNumber());
								myOrderItem.setTotalPrice(myOrderDetailBean
										.getTotalPrice());
								myOrderItem.setOrderId(myOrderDetailBean
										.getOrderId());
								myOrderItem.setStatus(myOrderDetailBean
										.getStatus());
								updateViews();
							} else {
								myToast("请求失败");
							}
						}

						@Override
						public void onSimpleFailure(int code) {
							myToast("http请求失败");
						}
					});
		}

		mAbImageLoader_logo = AbImageLoader.newInstance(this);
		mAbImageLoader_goodsImg = AbImageLoader.newInstance(this);
		mAbImageLoader_logo.setMaxWidth(100);
		mAbImageLoader_logo.setMaxHeight(100);
		mAbImageLoader_logo.setLoadingImage(R.drawable.businessman);
		mAbImageLoader_logo.setErrorImage(R.drawable.businessman);
		mAbImageLoader_logo.setEmptyImage(R.drawable.businessman);
		mAbImageLoader_goodsImg.setMaxWidth(100);
		mAbImageLoader_goodsImg.setMaxHeight(100);
		mAbImageLoader_goodsImg.setLoadingImage(R.drawable.image_loading);
		mAbImageLoader_goodsImg.setErrorImage(R.drawable.image_error);
		mAbImageLoader_goodsImg.setEmptyImage(R.drawable.image_empty);
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
//		tv_dingdan = (TextView) findViewById(R.id.tv_ordernumber);
//		tv_jiaoyi = (TextView) findViewById(R.id.tv_nhbnumber);
		ll_goodsitem_add = (LinearLayout) findViewById(R.id.ll_goodsitem_add);
		tv_01 = (TextView) findViewById(R.id.tv_01);
		tv_02 = (TextView) findViewById(R.id.tv_02);
		tv_03 = (TextView) findViewById(R.id.tv_03);
		tv_01.setVisibility(View.GONE);
		tv_02.setVisibility(View.GONE);
		findViewById(R.id.tv_noaddress).setVisibility(View.GONE);
	}

	private void updateViews() {
		((TextView) findViewById(R.id.tv_header_01))
				.setText(MyOrderActivity.map_orderStateValue
						.get(myOrderDetailBean.getStatus()));
		((TextView) findViewById(R.id.tv_header_02)).setText(String.format(
				getString(R.string.text_order_Price),
				CommonUtil.priceConversion(myOrderDetailBean.getTotalPrice())));
		((TextView) findViewById(R.id.tv_header_03))
				.setText(String.format(getString(R.string.text_shippingPrice),
						CommonUtil.priceConversion(myOrderDetailBean
								.getShippingFee())));

		ReceiverInfo receiverInfo = myOrderDetailBean.getReceiverInfo();
		((TextView) findViewById(R.id.tv_receiver)).setText(String.format(
				getString(R.string.text_receiver),
				receiverInfo.getReceiverName()));
		((TextView) findViewById(R.id.tv_address)).setText(String.format(
				getString(R.string.text_receiver_address),
				receiverInfo.getDeliveryAddress()));
		((TextView) findViewById(R.id.tv_phone)).setText(receiverInfo
				.getReceiverTel());

		((TextView) findViewById(R.id.tv_ordernumber)).setText(String.format(
				getString(R.string.text_ordernumber),
				myOrderDetailBean.getOrderNumber()));
		((TextView) findViewById(R.id.tv_nhbnumber)).setText(String.format(
				getString(R.string.text_nhbnumber),
				myOrderDetailBean.getOrderId()));
		((TextView) findViewById(R.id.tv_tradetime)).setText(String.format(
				getString(R.string.text_tradetime),
				myOrderDetailBean.getOrderedDate()));

		// 动态添加商品
		((TextView) findViewById(R.id.car_storename)).setText(myOrderDetailBean
				.getSellerName());
		// mAbImageLoader_logo.display(holder.iv_logo,Constant.BASEURL
		// + myOrderItem.getShopLogo());
		ll_goodsitem_add.removeAllViews();
		List<OrderItem> orderItems = myOrderDetailBean.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			View child = inflater.inflate(R.layout.list_car_goods_item, null);
			ll_goodsitem_add.addView(child);
			((TextView) child.findViewById(R.id.car_goodsname))
					.setText(orderItem.getName());
			// holder.goodsImg = (ImageView)
			// child.findViewById(R.id.car_goodsimg);
			TextView goodsAttr = (TextView)child.findViewById(R.id.car_goodsattr);
			TextView goodsNum = (TextView) child.findViewById(R.id.car_num);
			TextView goodsPrice = (TextView) child
					.findViewById(R.id.car_goods_price);
			// final ViewHolder holder1 = new ViewHolder();
			ImageView goodsImg = (ImageView) child
					.findViewById(R.id.car_goodsimg);
			
			Map<String, String> selectedspec = orderItem.getSpecs();
			String specs = "";
			for (Entry<String, String> entry : selectedspec.entrySet()) {
				specs = specs + entry.getValue() + "  ";
			}
			goodsAttr.setText(specs);
			
			goodsNum.setText("x" + orderItem.getQuantity());
			goodsPrice.setText("￥"
					+ CommonUtil.priceConversion(orderItem.getPrice()));
			child.findViewById(R.id.check_goods).setVisibility(View.GONE);
			mAbImageLoader_goodsImg.display(goodsImg, Constant.BASEURL
					+ orderItem.getProductSacle());
		}

		// 根据状态显示按钮
		String status = myOrderDetailBean.getStatus();
		String[] orderState = { "PENDING", "SUBMITTED", "PAID", "SHIPPING",
				"COMPLETED", "ORDER_RETURNING", "EVALUATE", "CANCELLED" };
		// public static String[] orderStateValues = { "未提交", "待付款", "待发货",
		// "待收货",
		// "已完成交易", "退款中", "待评价", "已取消交易" };
		if (status.equals(orderState[1])) {
			changeTextViewShow(null, null, "等待买家付款...");
		} else if (status.equals(orderState[2])) {
			changeTextViewShow(null, "发货", null);
			tv_02.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					SellerOrderServer.toSellerSendOrder(myOrderItem.getOrderId() + "", new CallBack() {
						@Override
						public void onSimpleSuccess(Object res) {
							myToast("发货成功");
							setResult(RESULT_OK, new Intent().putExtra("res", index));
							finish();
						}
						
						@Override
						public void onSimpleFailure(int code) {
							myToast("发货失败");
						}
					});
				}
			});
		} else if (status.equals(orderState[3])) {
			changeTextViewShow(null, null, "等待买家收货...");
		} else if (status.equals(orderState[4])) {
			changeTextViewShow(null, null, "已完成交易");
		} else if (status.equals(orderState[6])) {
			changeTextViewShow(null, null, "等待买家评价...");
		} else{
			changeTextViewShow(null, null, null);
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
}
