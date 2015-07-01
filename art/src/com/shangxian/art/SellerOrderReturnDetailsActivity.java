package com.shangxian.art;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangxian.art.adapter.MyOrderListAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.MyOrderDetailBean;
import com.shangxian.art.bean.MyOrderDetailBean.ReceiverInfo;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.ProductItemDto;
import com.shangxian.art.bean.MyOrderDetailBean.OrderItem;
import com.shangxian.art.bean.SellerRefoundOrderInfo;
import com.shangxian.art.bean.SellerRefoundOrderProductInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.constant.Global;
import com.shangxian.art.fragment.MyOrder_All_Fragment;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.MyOrderServer;
import com.shangxian.art.net.MyOrderServer.OnHttpResultCancelOrderListener;
import com.shangxian.art.net.MyOrderServer.OnHttpResultDelOrderListener;
import com.shangxian.art.net.MyOrderServer.OnHttpResultOrderDetailsListener;
import com.shangxian.art.net.SellerOrderServer;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.utils.Options;
import com.shangxian.art.view.TopView;
import com.shangxian.art.zxing.decoding.FinishListener;

/**
 * 退货详情类
 * 
 * @author libo
 *
 */
public class SellerOrderReturnDetailsActivity extends BaseActivity {
	private TopView topView;
	private LinearLayout ll_goodsitem_add;
	private ImageView iv_logo;
	private TextView tv_storeName, tv_01, tv_02;
	private TextView tv_ordernumber;//订单编号
	private TextView tv_nhbnumber;//退货编号
	private SellerRefoundOrderInfo sellerRefundOrder;
	private static final String INT_SELLER_RETURN = "int_seller_return";
	private static final String INT_SELLER_INDEX = "int_seller_index";
	private TextView tv_03;
	private List<SellerRefoundOrderProductInfo> sellerRefoundOrderProductInfos;
	private static boolean isToFinish = false;

	Handler Handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			finish();
			setResult(RESULT_OK, new Intent().putExtra("res", index));
		};
	};

	Thread finishThread = new Thread() {
		public void run() {
			while (true) {
				if (isToFinish) {
					Handler.sendEmptyMessage(0);
				} else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	};

	private static ProgressDialog dialog;
	private static int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order_details);
		Global.isSellerOrderReturnDetailsActivityIsShow = true;
		initDatas();
		initViews();
		finishThread.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Global.isSellerOrderReturnDetailsActivityIsShow = false;
	}

	/**
	 * 跳转本页的方法
	 * 
	 * @param sellerRefoundOrderInfo
	 * @param mAc
	 */
	public static void startThisActivity(int position,
			SellerRefoundOrderInfo sellerRefoundOrderInfo, Fragment mAc) {
		Intent intent = new Intent(mAc.getActivity(), SellerOrderReturnDetailsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(INT_SELLER_RETURN, sellerRefoundOrderInfo);
		bundle.putInt(INT_SELLER_INDEX, position);
		mAc.startActivityForResult(intent.putExtras(bundle), 1);
	}

	private void initDatas() {
		if (getIntent().getSerializableExtra(INT_SELLER_RETURN) != null) {
			this.sellerRefundOrder = (SellerRefoundOrderInfo) getIntent()
					.getSerializableExtra(INT_SELLER_RETURN);
			this.index = getIntent().getIntExtra(INT_SELLER_INDEX, -1);
		} else {
			myToast("请求数据错误");
			this.finish();
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
		tv_ordernumber = (TextView) findViewById(R.id.tv_ordernumber);
		tv_nhbnumber = (TextView) findViewById(R.id.tv_nhbnumber);
		findViewById(R.id.tv_noaddress).setVisibility(View.GONE);

		updateViews();
	}

	private void updateViews() {
		((TextView) findViewById(R.id.tv_header_01))
				.setText(SellerOrderActivity.map_orderReturnStatusValue
						.get(sellerRefundOrder.getStatus()));
		((TextView) findViewById(R.id.tv_header_02)).setText(String.format(
				getString(R.string.text_order_Price),
				CommonUtil.priceConversion(sellerRefundOrder.getTotalPrice())));
		((TextView) findViewById(R.id.tv_header_03)).setVisibility(View.GONE);

		((TextView) findViewById(R.id.tv_receiver)).setText(String.format(
				getString(R.string.text_receiver),
				sellerRefundOrder.getReceiverName()));
		((TextView) findViewById(R.id.tv_address)).setText(String.format(
				getString(R.string.text_receiver_address),
				sellerRefundOrder.getBuyerAddress()));
		((TextView) findViewById(R.id.tv_phone)).setText(sellerRefundOrder
				.getBuyerName());
		tv_ordernumber.setText("订单编号:"+sellerRefundOrder.getOrderNumber());//订单编号
		tv_nhbnumber.setText("退货订单编号:"+sellerRefundOrder.getReturnOrderNum());//退货订单编号
		findViewById(R.id.tv_ordernumber);
		((TextView) findViewById(R.id.tv_tradetime)).setText(
				"退货时间:"+ sellerRefundOrder.getReturnOrderTime());

		// 动态添加商品
		((TextView) findViewById(R.id.car_storename)).setText(sellerRefundOrder
				.getShippingName());
		// mAbImageLoader_logo.display(holder.iv_logo,Constant.BASEURL
		// + myOrderItem.getShopLogo());
		ll_goodsitem_add.removeAllViews();
		sellerRefoundOrderProductInfos = sellerRefundOrder
				.getReturnOrderItemDtos();
		for (int i = 0; i < sellerRefoundOrderProductInfos.size(); i++) {
			SellerRefoundOrderProductInfo sellerRefoundOrderProductInfo = sellerRefoundOrderProductInfos
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
			// final ViewHolder holder1 = new ViewHolder();
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
		String status = sellerRefundOrder.getStatus();
		String[] orderState = { "NORMAL", "SUCCESS", "WAIT_SELLER_APPROVAL",
				"WAIT_BUYER_DELIVERY", "WAIT_COMPLETED", "COMPLETED_REFUSE",
				"ORDER_RETURNING", "CANCELLED", "FAILURE" };
		String[] orderReturnStatus = { "正常，不退货", "退款成功", "等待卖家审核", "等待买家退货",
				"买家已发货,等待卖家签收", "卖家拒绝签收", "已签收，退款成功", "取消", "退货失败" };
		if (status.equals(orderState[2])) {
			changeTextViewShow("审核不通过", "审核通过", null);
			tv_01.setOnClickListener(new View.OnClickListener() { // CURRENT:
																	// 待审核...
				@Override
				public void onClick(View v) {
					toSellerRefoundOperation(Operation.seller_check_fialure);
				}
			});
			tv_02.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					toSellerRefoundOperation(Operation.seller_check_fialure);
				}
			});
		} else if (status.equals(orderState[1])) { // CURRENT: 退货成功...
			changeTextViewShow("删除订单", null, "退货成功");
			tv_01.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			});
		} else if (status.equals(orderState[3])) {// CURRENT: 等待买家发货...
			changeTextViewShow(null, null, "等待买家发货...");
		} else if (orderState[4].equals(status)) {// CURRENT: 买家已发货,等待卖家签收...
			changeTextViewShow("拒绝签收", "签收", null);
			tv_01.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					toSellerRefoundOperation(Operation.seller_completed_refuse);
				}
			});
			tv_02.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					toSellerRefoundOperation(Operation.seller_completed);
				}
			});
		} else if (orderState[5].equals(status)) {
			changeTextViewShow(null, null, "已拒绝签收");
		} else if (orderState[6].equals(status)) {
			changeTextViewShow(null, null, "已签收,退款成功");
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

	/**
	 * 卖家退货操作
	 * 
	 * @param operation
	 */
	private void toSellerRefoundOperation(Operation operation) {
		dialog = new ProgressDialog(mAc);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("正在保存...");
		dialog.show();
		String productId = sellerRefoundOrderProductInfos.get(0).getId() + "";
		switch (operation) {
		case seller_check_fialure:
			check_fialure(sellerRefundOrder, productId);
			break;
		case seller_completed_refuse:
			competed_refuse(sellerRefundOrder, productId);
			break;
		case seller_check_success:
		case seller_completed:
			seller_operation(sellerRefundOrder, productId);
			break;
		}
	}

	/**
	 * 卖家拒绝签收
	 * 
	 * @param sellerRefundOrder2
	 * @param productId
	 */
	public static void competed_refuse(
			final SellerRefoundOrderInfo sellerRefundOrder, String productId) {
		SellerOrderServer.toSellerReturnOrderCompletedRefuse(
				sellerRefundOrder.getOrderNumber(), productId,
				sellerRefundOrder.getReturnOrderNum(), new CallBack() {
					@Override
					public void onSimpleSuccess(Object res) {
						CommonUtil.toast("拒绝签收信息已上传");
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
							isToFinish = true;
							if (dialog != null) {
								dialog.dismiss();
							}
						}
						Global.sellerReFundOrder = sellerRefundOrder;
					}

					@Override
					public void onSimpleFailure(int code) {
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
							// isToFinish = true;
							if (dialog != null) {
								dialog.dismiss();
							}
						}
						CommonUtil.toast("拒绝签收信息上传失败");
					}
				});
	}

	/**
	 * 卖家操作
	 * 
	 * @param sellerRefundOrder
	 * 
	 * @param productId
	 */
	public static void seller_operation(
			final SellerRefoundOrderInfo sellerRefundOrder, String productId) {
		SellerOrderServer.toSellerReturnOrderOperation(
				sellerRefundOrder.getStatus(),
				sellerRefundOrder.getOrderNumber(), productId,
				sellerRefundOrder.getReturnOrderNum(), new CallBack() {
					@Override
					public void onSimpleSuccess(Object res) {
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
							isToFinish = true;
							if (dialog != null) {
								dialog.dismiss();
							}
						}
						Global.sellerReFundOrder = sellerRefundOrder;
						CommonUtil.toast("信息上传成功");
					}

					@Override
					public void onSimpleFailure(int code) {
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
							// isToFinish = true;
							if (dialog != null) {
								dialog.dismiss();
							}
						}
						CommonUtil.toast("操作失败，请稍后再试");
					}
				});
	}

	/**
	 * 审核不通过
	 * 
	 * @param sellerRefundOrder
	 * 
	 * @param productId
	 */
	public static void check_fialure(
			final SellerRefoundOrderInfo sellerRefundOrder, String productId) {
		SellerOrderServer.toSellerReturnOrderFialure(
				sellerRefundOrder.getOrderNumber(), productId,
				sellerRefundOrder.getReturnOrderNum(), new CallBack() {
					@Override
					public void onSimpleSuccess(Object res) {
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
							isToFinish = true;
							if (dialog != null) {
								dialog.dismiss();
							}
						}
						Global.sellerReFundOrder = sellerRefundOrder;
						CommonUtil.toast("审核信息上传成功");
					}

					@Override
					public void onSimpleFailure(int code) {
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
							// isToFinish = true;
							if (dialog != null) {
								dialog.dismiss();
							}
						}
						CommonUtil.toast("操作失败，请稍后再试");
					}
				});
	}

	private enum Operation {
		seller_check_fialure, // 卖家审核失败
		seller_check_success, // 卖家审核成功
		seller_completed_refuse, // 卖家拒绝签收
		seller_completed // 卖家签收
	}
}
