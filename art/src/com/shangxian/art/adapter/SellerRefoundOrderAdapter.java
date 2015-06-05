package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangxian.art.MyOrderActivity;
import com.shangxian.art.R;
import com.shangxian.art.SellerOrderActivity;
import com.shangxian.art.bean.SellerRefoundOrderInfo;
import com.shangxian.art.bean.SellerRefoundOrderProductInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.Options;

/**
 * 卖家退货订单
 * 
 * @author libo
 */
public class SellerRefoundOrderAdapter extends
		EntityAdapter<SellerRefoundOrderInfo> {

	private ImageLoader loader;
	private DisplayImageOptions options;

	public SellerRefoundOrderAdapter(Activity mAc, int layoutId,
			List<SellerRefoundOrderInfo> dates) {
		super(mAc, layoutId, dates);
		loader = ImageLoader.getInstance();
		options = Options.getListOptions(true);
	}

	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
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
		SellerRefoundOrderInfo sellerRefoundOrderInfo = getItem(position);
		List<SellerRefoundOrderProductInfo> listCarGoodsBeans = sellerRefoundOrderInfo
				.getReturnOrderItemDtos();
		boolean isRefundStatus = false;// 是否是normal
		for (SellerRefoundOrderProductInfo sellerRefoundOrderProductInfo : listCarGoodsBeans) {
			View child = LayoutInflater.from(mAc).inflate(
					R.layout.list_car_goods_item, null);
			holder.ll_goodsitem_add.addView(child);
			((TextView) child.findViewById(R.id.car_goodsname))
					.setText(sellerRefoundOrderProductInfo.getName());
			// holder.goodsImg = (ImageView)
			// child.findViewById(R.id.car_goodsimg);
			// holder.goodsAttr = (TextView)
			// child.findViewById(R.id.car_goodsattr);
			TextView goodsNum = (TextView) child.findViewById(R.id.car_num);
			TextView goodsPrice = (TextView) child
					.findViewById(R.id.car_goods_price);
			TextView car_goodsstatus = (TextView) child
					.findViewById(R.id.car_goodsstatus);
			// final ViewHolder holder1 = new ViewHolder();
			ImageView goodsImg = (ImageView) child
					.findViewById(R.id.car_goodsimg);
			goodsNum.setText("x" + sellerRefoundOrderProductInfo.getQuantity());
			goodsPrice.setText("￥"
					+ CommonUtil.priceConversion(sellerRefoundOrderProductInfo
							.getProductPrice()));
			String status = sellerRefoundOrderInfo.getStatus();
			if (!status.equals(MyOrderActivity.orderReturnStatus[0])) {
				isRefundStatus = true;
				car_goodsstatus.setVisibility(View.VISIBLE);
				car_goodsstatus
						.setText(String.format(
								mAc.getResources().getString(
										R.string.text_refundstatus),
								MyOrderActivity.map_orderReturnStatusValue
										.get(status)));
			} else {
				isRefundStatus = false;
				car_goodsstatus.setVisibility(View.GONE);
			}
			child.findViewById(R.id.check_goods).setVisibility(View.GONE);
			loader.displayImage(Constant.BASEURL
					+ sellerRefoundOrderProductInfo.getProductSacle(),
					goodsImg, options);
		}

		if (sellerRefoundOrderInfo != null) {
			holder.storeName.setText(sellerRefoundOrderInfo.getShippingName());
			holder.tv_state.setText(MyOrderActivity.map_orderStateValue
					.get(sellerRefoundOrderInfo.getStatus()));
			holder.tv_allquantity.setText("共"
					+ sellerRefoundOrderInfo.getTotalQuantity() + "件商品");
			holder.tv_payment.setText("￥"
					+ CommonUtil.priceConversion(sellerRefoundOrderInfo
							.getTotalPrice()));



			// 根据订单状态显示下面一排按钮 //根据status显示item下面的按钮
			if (sellerRefoundOrderInfo.getStatus().equals(
					SellerOrderActivity.orderReturnStatus[2])) { // 待审核
				holder.tv_01.setVisibility(View.GONE);
				holder.tv_02.setVisibility(View.GONE);
				holder.tv_03.setVisibility(View.VISIBLE);
				holder.tv_03.setText("等待买家付款...");
			} else if (sellerRefoundOrderInfo.getStatus().equals(
					SellerOrderActivity.orderReturnStatus[7])) { // 待
				holder.tv_01.setText("删除订单");
				holder.tv_01.setVisibility(View.VISIBLE);
				holder.tv_02.setVisibility(View.GONE);
				holder.tv_03.setVisibility(View.GONE);
				holder.tv_01.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						// TODO: ================================ 删除订单  ====================================
						
						
					}
				});
			} else if (sellerRefoundOrderInfo.getStatus().equals(
					MyOrderActivity.orderState[2])) {// 待退款
				holder.tv_01.setVisibility(View.GONE);
				holder.tv_02.setVisibility(View.VISIBLE);
				holder.tv_03.setVisibility(View.GONE);
				
				holder.tv_02.setText("确认退款");
				
				holder.tv_02.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						// TODO:=======================================================================
						
						
					}

				});
			} else if (sellerRefoundOrderInfo.getStatus().equals(
					MyOrderActivity.orderState[3])) {// 待收货
				holder.tv_01.setVisibility(View.GONE);
				holder.tv_02.setVisibility(View.GONE);
				holder.tv_03.setVisibility(View.VISIBLE);
				holder.tv_03.setText("等待买家收货...");
				holder.tv_02.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						// TODO:=======================================================================
						
						
					}
				});
			} else if (sellerRefoundOrderInfo.getStatus().equals(
					MyOrderActivity.orderState[4])) {// 已完成交易
				if (isRefundStatus) {
					holder.tv_02.setText("退货中");
					holder.tv_02.setEnabled(false);
				} else {
					holder.tv_02.setText("退货");
					holder.tv_02.setEnabled(true);
				}
				holder.tv_01.setVisibility(View.GONE);
				holder.tv_02.setVisibility(View.VISIBLE);
				holder.tv_02.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						// TODO: ===============================================

					}
				});
			}

		}
		return convertView;
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
