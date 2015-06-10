package com.shangxian.art.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shangxian.art.MyOrderActivity;
import com.shangxian.art.R;
import com.shangxian.art.bean.BuyerReturnOrderInfo;
import com.shangxian.art.bean.BuyerReturnOrderProductInfo;
import com.shangxian.art.bean.SellerRefoundOrderInfo;
import com.shangxian.art.bean.SellerRefoundOrderProductInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.BuyerOrderServer;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.Options;

public class BuyerReturnOrderAdapter extends
		EntityAdapter<BuyerReturnOrderInfo> {

	public BuyerReturnOrderAdapter(Fragment fragment, int layoutId,
			List<BuyerReturnOrderInfo> dates) {
		super(fragment, layoutId, dates);
	}

	@Override
	public View initView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater();
			holder = new ViewHolder();
			holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
			holder.storeName = (TextView) convertView
					.findViewById(R.id.car_storename);
			holder.tv_state = (TextView) convertView
					.findViewById(R.id.tv_state);
			holder.tv_allquantity = (TextView) convertView
					.findViewById(R.id.tv_allquantity);
			holder.tv_payment = (TextView) convertView
					.findViewById(R.id.tv_payment);
			holder.tv_01 = (TextView) convertView.findViewById(R.id.tv_01);
			holder.tv_01.setVisibility(View.GONE);
			holder.tv_02 = (TextView) convertView.findViewById(R.id.tv_02);
			holder.tv_03 = (TextView) convertView.findViewById(R.id.tv_03);
			holder.ll_goodsitem_add = (LinearLayout) convertView
					.findViewById(R.id.ll_goodsitem_add);
			holder.ll_goodsitem_add.setBackgroundResource(R.drawable.shape_top);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.ll_goodsitem_add.removeAllViews();
		final BuyerReturnOrderInfo buyerReturnOrderInfo = getItem(position);
		if (buyerReturnOrderInfo.isNull()) {
			return null;
		}
		final String status = buyerReturnOrderInfo.getStatus();
		final List<BuyerReturnOrderProductInfo> buyerReturnOrderProductInfos = buyerReturnOrderInfo
				.getReturnOrderItemDtos();
		for (BuyerReturnOrderProductInfo buyerReturnOrderProductInfo : buyerReturnOrderProductInfos) {
			View child = LayoutInflater.from(mAc).inflate(
					R.layout.list_car_goods_item, null);
			holder.ll_goodsitem_add.addView(child);
			((TextView) child.findViewById(R.id.car_goodsname))
					.setText(buyerReturnOrderProductInfo.getName());
			TextView goodsNum = (TextView) child.findViewById(R.id.car_num);
			TextView goodsPrice = (TextView) child
					.findViewById(R.id.car_goods_price);
			TextView car_goodsstatus = (TextView) child
					.findViewById(R.id.car_goodsstatus);
			ImageView goodsImg = (ImageView) child
					.findViewById(R.id.car_goodsimg);
			goodsNum.setText("x" + buyerReturnOrderProductInfo.getQuantity());
			goodsPrice.setText("￥"
					+ CommonUtil.priceConversion(buyerReturnOrderProductInfo
							.getProductPrice()));

			car_goodsstatus.setText(String.format(
					mAc.getResources().getString(R.string.text_refundstatus),
					MyOrderActivity.map_orderReturnStatusValue.get(status)));

			child.findViewById(R.id.check_goods).setVisibility(View.GONE);
			imageLoader.displayImage(Constant.BASEURL
					+ buyerReturnOrderProductInfo.getProductSacle(), goodsImg,
					Options.getListOptions(false));
		}

		String[] orderReturnStatus = { "NORMAL", "SUCCESS",
				"WAIT_SELLER_APPROVAL", "WAIT_BUYER_DELIVERY",
				"WAIT_COMPLETED", "COMPLETED_REFUSE", "ORDER_RETURNING",
				"CANCELLED", "FAILURE" };

		String[] orderReturnStatusValue = { "正常，不退货", "退款成功", "等待卖家审核",
				"等待买家退货", "买家已发货,等待卖家签收", "卖家拒绝签收", "已签收，退款成功", "取消", "退货失败" };
		
		// TODO: 跳转界面 -----------------------------------------------------------------------

		if (orderReturnStatus[1].equals(status)
				|| orderReturnStatus[8].equals(status) || orderReturnStatus[6].equals(status)) {
			changeTextViewShow(holder, "删除订单", null,
					orderReturnStatus[8].equals(status) ? "退货失败" : "退款成功");
			holder.tv_01.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new BuyerOrderServer().toBuyerDeleteReturnOrder(
							buyerReturnOrderInfo.getReturnOrderNum(),
							new CallBack() {
								@Override
								public void onSimpleSuccess(Object res) {
									CommonUtil.toast("删除成功");
									removeDataItem(position);
								}

								@Override
								public void onSimpleFailure(int code) {
									CommonUtil.toast("删除失败");
								}
							});
				}
			});
		} else if (orderReturnStatus[2].equals(status)) {
			changeTextViewShow(holder, "取消订单", null, "等待买家审核...");
			holder.tv_01.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new BuyerOrderServer().toBuyerCancelReturnOrder(
							buyerReturnOrderProductInfos.get(0).getId() + "",
							buyerReturnOrderInfo.getReturnOrderNum(),
							new CallBack() {
								@Override
								public void onSimpleSuccess(Object res) {
									CommonUtil.toast("取消订单成功");
									removeDataItem(position);
								}

								@Override
								public void onSimpleFailure(int code) {
									CommonUtil.toast("取消订单失败");
								}
							});
				}
			});
		} else if (orderReturnStatus[3].equals(status)) {
			changeTextViewShow(holder, null, "退货", "卖家审核通过");
			holder.tv_02.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});
		} else if (orderReturnStatus[4].equals(status)) {
			changeTextViewShow(holder, null, null, "已发货,等待卖家签收...");
		} else if (orderReturnStatus[5].equals(status)) {
			//changeTextViewShow(holder, null, null, tv_03_title);
			
			// TODO:  卖家拒绝签收  -------------------------------------------------
		}

		return convertView;
	}

	private void changeTextViewShow(ViewHolder holder, String tv_01_title,
			String tv_02_title, String tv_03_title) {
		holder.tv_01.setText(tv_01_title);
		holder.tv_02.setText(tv_02_title);
		holder.tv_03.setText(tv_03_title);

		holder.tv_01
				.setVisibility(!TextUtils.isEmpty(tv_01_title) ? View.VISIBLE
						: View.GONE);
		holder.tv_02
				.setVisibility(!TextUtils.isEmpty(tv_02_title) ? View.VISIBLE
						: View.GONE);
		holder.tv_03
				.setVisibility(!TextUtils.isEmpty(tv_03_title) ? View.VISIBLE
						: View.GONE);
	}

	private class ViewHolder {
		public CheckBox check_store;
		public ImageView iv_logo;
		public TextView tv_state;
		public TextView storeName;
		public TextView goodsName;
		public ImageView goodsImg;
		public TextView goodsAttr;
		public TextView tv_allquantity;
		public TextView tv_payment;
		public TextView goodsNum;
		public TextView goodsPrice;
		public TextView tv_01;
		public TextView tv_02;
		public TextView tv_03;
		public ImageView goodsDelete;
		public LinearLayout ll_goodsitem_add;
	}
}
