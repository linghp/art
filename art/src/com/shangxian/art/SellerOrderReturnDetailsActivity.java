package com.shangxian.art;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.shangxian.art.bean.SellerRefoundOrderInfo;
import com.shangxian.art.bean.SellerRefoundOrderProductInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.constant.Global;
import com.shangxian.art.fragment.BuyerReturnOrderFragment;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.SellerOrderServer;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.Options;
import com.shangxian.art.view.TopView;

/**
 * 退货详情类
 * 
 * @author libo
 *
 */
public class SellerOrderReturnDetailsActivity extends BaseActivity {
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

	public static void startThisActivity(String time,String returns,String istrue,String money, String reason,String explain,Activity mAc) {
		Intent intent = new Intent(mAc, SellerOrderReturnDetailsActivity.class);
		intent.putExtra("time", time);
		intent.putExtra("returns", returns);
		intent.putExtra("istrue", istrue);
		intent.putExtra("money", money);
		intent.putExtra("reason", reason);
		intent.putExtra("explain", explain);
		mAc.startActivity(intent);
	}
	

	private void initData() {
		String time = getIntent().getStringExtra("time");
		tv_time.setText(time);//退款时间
		String returns = getIntent().getStringExtra("returns");
		switch (returns) {
		case "SUCCESS":
			tv_return.setText("退款成功");
			tv_goods.setText("未收到货");//货物状态
			break;
		case "WAIT_SELLER_APPROVAL":
			tv_return.setText("等待卖家审核");
			tv_goods.setText("未收到货");//货物状态
			break;
		case "WAIT_BUYER_DELIVERY":
			tv_return.setText("等待买家退货");
			tv_goods.setText("买家已收到货");//货物状态
			break;
		case "WAIT_COMPLETED":
			tv_return.setText("买家已发货,等待卖家签收");
			tv_goods.setText("卖家未收到货");//货物状态
			break;
		case "CANCELLED":
			tv_return.setText("订单已取消");
			tv_goods.setText("已收到货");//货物状态
			break;
			///////////////////////////////////////////////
		case "NORMAL":
			tv_return.setText("正常，不退货");
			tv_goods.setText("买家已收到货");//货物状态
			break;
		case "COMPLETED_REFUSE":
			tv_return.setText("卖家拒绝签收");
			tv_goods.setText("卖家拒绝收货");//货物状态
			break;
		case "ORDER_RETURNING":
			tv_return.setText("已签收，退款成功");
			tv_goods.setText("卖家已收到货");//货物状态
			break;
		case "FAILURE":
			tv_return.setText("退货失败");
			tv_goods.setText("未收到货");//货物状态
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

		String istrue = getIntent().getStringExtra("istrue");
		if (istrue.equals("true")) {
			tv_istrue.setText("是");//是否需要退还货物
		}else {
			tv_istrue.setText("否");//是否需要退还货物
		}

		String money = getIntent().getStringExtra("money");
		tv_money.setText(money);//退还金额
		String reason = getIntent().getStringExtra("reason");
		tv_reason.setText(reason);//退款原因
		String explain = getIntent().getStringExtra("explain");
		tv_explain.setText(explain);//退款说明	
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
	/**
	 * 卖家退货操作
	 * 
	 * @param operation
	 */
	private List<SellerRefoundOrderProductInfo> sellerRefoundOrderProductInfos;
	private SellerRefoundOrderInfo sellerRefundOrder;
	private void toSellerRefoundOperation(Operation operation) {
       showProgressDialog(true);
		String productId = sellerRefoundOrderProductInfos.get(0).getId() + "";
		switch (operation) {
		case seller_check_fialure:
			check_fialure(sellerRefundOrder, productId,refreshDialog);
			break;
		case seller_completed_refuse:
			competed_refuse(sellerRefundOrder, productId,refreshDialog);
			break;
		case seller_check_success:
		case seller_completed:
			seller_operation(sellerRefundOrder, productId,refreshDialog);
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
			final SellerRefoundOrderInfo sellerRefundOrder, String productId,final Dialog dialog) {
		SellerOrderServer.toSellerReturnOrderCompletedRefuse(
				sellerRefundOrder.getOrderNumber(), productId,
				sellerRefundOrder.getReturnOrderNum(), new CallBack() {
					@Override
					public void onSimpleSuccess(Object res) {
						CommonUtil.toast("拒绝签收信息已上传");
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
//							isToFinish = true;
							if(dialog!=null)
							dialog.dismiss();
						}
						Global.sellerReFundOrder = sellerRefundOrder;
					}

					@Override
					public void onSimpleFailure(int code) {
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
							// isToFinish = true;
							if(dialog!=null)
							dialog.dismiss();
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
	public static void seller_operation(final SellerRefoundOrderInfo sellerRefundOrder, String productId,final Dialog dialog) {
		SellerOrderServer.toSellerReturnOrderOperation(
				sellerRefundOrder.getStatus(),
				sellerRefundOrder.getOrderNumber(), productId,
				sellerRefundOrder.getReturnOrderNum(), new CallBack() {
					@Override
					public void onSimpleSuccess(Object res) {
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
//							isToFinish = true;
							if(dialog!=null)
							dialog.dismiss();
						}
						Global.sellerReFundOrder = sellerRefundOrder;
						CommonUtil.toast("信息上传成功");
					}

					@Override
					public void onSimpleFailure(int code) {
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
							// isToFinish = true;
							if(dialog!=null)
							dialog.dismiss();
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
	public  static void check_fialure(
			final SellerRefoundOrderInfo sellerRefundOrder, String productId,final Dialog dialog) {
		SellerOrderServer.toSellerReturnOrderFialure(
				sellerRefundOrder.getOrderNumber(), productId,
				sellerRefundOrder.getReturnOrderNum(), new CallBack() {
					@Override
					public void onSimpleSuccess(Object res) {
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
//							isToFinish = true;
							if(dialog!=null)
							dialog.dismiss();
						}
						Global.sellerReFundOrder = sellerRefundOrder;
						CommonUtil.toast("审核信息上传成功");
					}

					@Override
					public void onSimpleFailure(int code) {
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
							// isToFinish = true;
							if(dialog!=null)
							dialog.dismiss();
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
/*public class SellerOrderReturnDetailsActivity extends BaseActivity {
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
	private  boolean isfinishthread = false;

	Handler Handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			setResult(RESULT_OK, new Intent().putExtra("res", index));
			isfinishthread=true;
			finish();
		};
	};

	Thread finishThread = new Thread() {
		public void run() {
			while (!isfinishthread) {
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

	*//**
	 * 跳转本页的方法
	 * 
	 * @param sellerRefoundOrderInfo
	 * @param mAc
	 *//*
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
		topView.setTitle("退货订单详情");

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
			changeTextViewShow("拒绝签收", "确认签收", null);
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

	*//**
	 * 设置tv_01、tv_02、tv_03显示文本，如果文本为空，默认设置不显示...
	 * 
	 * @param tv_01_title
	 * @param tv_02_title
	 * @param tv_03_title
	 *//*
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

	*//**
	 * 卖家退货操作
	 * 
	 * @param operation
	 *//*
	private void toSellerRefoundOperation(Operation operation) {
        showProgressDialog(true);
		String productId = sellerRefoundOrderProductInfos.get(0).getId() + "";
		switch (operation) {
		case seller_check_fialure:
			check_fialure(sellerRefundOrder, productId,refreshDialog);
			break;
		case seller_completed_refuse:
			competed_refuse(sellerRefundOrder, productId,refreshDialog);
			break;
		case seller_check_success:
		case seller_completed:
			seller_operation(sellerRefundOrder, productId,refreshDialog);
			break;
		}
	}

	*//**
	 * 卖家拒绝签收
	 * 
	 * @param sellerRefundOrder2
	 * @param productId
	 *//*
	public static void competed_refuse(
			final SellerRefoundOrderInfo sellerRefundOrder, String productId,final Dialog dialog) {
		SellerOrderServer.toSellerReturnOrderCompletedRefuse(
				sellerRefundOrder.getOrderNumber(), productId,
				sellerRefundOrder.getReturnOrderNum(), new CallBack() {
					@Override
					public void onSimpleSuccess(Object res) {
						CommonUtil.toast("拒绝签收信息已上传");
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
							isToFinish = true;
							if(dialog!=null)
							dialog.dismiss();
						}
						Global.sellerReFundOrder = sellerRefundOrder;
					}

					@Override
					public void onSimpleFailure(int code) {
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
							// isToFinish = true;
							if(dialog!=null)
							dialog.dismiss();
						}
						CommonUtil.toast("拒绝签收信息上传失败");
					}
				});
	}

	*//**
	 * 卖家操作
	 * 
	 * @param sellerRefundOrder
	 * 
	 * @param productId
	 *//*
	public static void seller_operation(final SellerRefoundOrderInfo sellerRefundOrder, String productId,final Dialog dialog) {
		SellerOrderServer.toSellerReturnOrderOperation(
				sellerRefundOrder.getStatus(),
				sellerRefundOrder.getOrderNumber(), productId,
				sellerRefundOrder.getReturnOrderNum(), new CallBack() {
					@Override
					public void onSimpleSuccess(Object res) {
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
							isToFinish = true;
							if(dialog!=null)
							dialog.dismiss();
						}
						Global.sellerReFundOrder = sellerRefundOrder;
						CommonUtil.toast("信息上传成功");
					}

					@Override
					public void onSimpleFailure(int code) {
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
							// isToFinish = true;
							if(dialog!=null)
							dialog.dismiss();
						}
						CommonUtil.toast("操作失败，请稍后再试");
					}
				});
	}

	*//**
	 * 审核不通过
	 * 
	 * @param sellerRefundOrder
	 * 
	 * @param productId
	 *//*
	public  static void check_fialure(
			final SellerRefoundOrderInfo sellerRefundOrder, String productId,final Dialog dialog) {
		SellerOrderServer.toSellerReturnOrderFialure(
				sellerRefundOrder.getOrderNumber(), productId,
				sellerRefundOrder.getReturnOrderNum(), new CallBack() {
					@Override
					public void onSimpleSuccess(Object res) {
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
							isToFinish = true;
							if(dialog!=null)
							dialog.dismiss();
						}
						Global.sellerReFundOrder = sellerRefundOrder;
						CommonUtil.toast("审核信息上传成功");
					}

					@Override
					public void onSimpleFailure(int code) {
						if (Global.isSellerOrderReturnDetailsActivityIsShow) {
							// isToFinish = true;
							if(dialog!=null)
							dialog.dismiss();
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
}*/
