package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangxian.art.MyOrderActivity;
import com.shangxian.art.R;
import com.shangxian.art.SellerOrderActivity;
import com.shangxian.art.SellerOrderReturnDetailsActivity;
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
	private Fragment mFragment;

//	public SellerRefoundOrderAdapter(Activity mAc, int layoutId,
//			List<SellerRefoundOrderInfo> dates) {
//		super(mAc, layoutId, dates);
//		loader = ImageLoader.getInstance();
//		options = Options.getListOptions(true);
//	}
	
	public SellerRefoundOrderAdapter(Activity mAc, Fragment fragment,int layoutId,
			List<SellerRefoundOrderInfo> dates) {
		super(mAc, layoutId, dates);
		loader = ImageLoader.getInstance();
		options = Options.getListOptions(true);
		this.mFragment = fragment;
	}

	public void removeItem(SellerRefoundOrderInfo info) {
		if (info != null && dates.contains(info)) {
			dates.remove(info);
			notifyDataSetChanged();
		}
	}
	
	public void removeItem(int position){
		if (position >= 0 && dates.size() > 0 && (dates.size() -1) >= position) {
			dates.remove(position);
			notifyDataSetChanged();
		}
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
		final SellerRefoundOrderInfo sellerRefoundOrderInfo = getItem(position);
		List<SellerRefoundOrderProductInfo> listCarGoodsBeans = sellerRefoundOrderInfo
				.getReturnOrderItemDtos();
		//boolean isRefundStatus = false;// 是否是normal
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
//			if (!status.equals(MyOrderActivity.orderReturnStatus[0])) {
//				isRefundStatus = true;
//				car_goodsstatus.setVisibility(View.VISIBLE);
//				car_goodsstatus
//						.setText(String.format(
//								mAc.getResources().getString(
//										R.string.text_refundstatus),
//								MyOrderActivity.map_orderReturnStatusValue
//										.get(status)));
//			} else {
//				isRefundStatus = false;
//				car_goodsstatus.setVisibility(View.GONE);
//			}
			child.findViewById(R.id.check_goods).setVisibility(View.GONE);
			loader.displayImage(Constant.BASEURL
					+ sellerRefoundOrderProductInfo.getProductSacle(),
					goodsImg, options);
		}
		
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SellerOrderReturnDetailsActivity.startThisActivity(position, sellerRefoundOrderInfo,
						mFragment);
			}
		});
		
		changeTextViewShow(holder, null, null, "正在加载...");
		
		if (sellerRefoundOrderInfo != null) {
			holder.storeName.setText(sellerRefoundOrderInfo.getShippingName());
			holder.tv_state
					.setText(SellerOrderActivity.map_orderReturnStatusValue
							.get(sellerRefoundOrderInfo.getStatus()));
			holder.tv_allquantity.setText("共"
					+ sellerRefoundOrderInfo.getTotalQuantity() + "件商品");
			holder.tv_payment.setText("￥"
					+ CommonUtil.priceConversion(sellerRefoundOrderInfo
							.getTotalPrice()));

			// 根据订单状态显示下面一排按钮 //根据status显示item下面的按钮
			String status = sellerRefoundOrderInfo.getStatus();
			if (status.equals(SellerOrderActivity.orderReturnStatus[2])) { // 待审核
				changeTextViewShow(holder, "审核不通过", "审核通过", null);
				holder.tv_01.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						SellerOrderReturnDetailsActivity.check_fialure(
								sellerRefoundOrderInfo, sellerRefoundOrderInfo
										.getReturnOrderItemDtos().get(0)
										.getId()
										+ "");
					}
				});

				holder.tv_02.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						SellerOrderReturnDetailsActivity.seller_operation(
								sellerRefoundOrderInfo, sellerRefoundOrderInfo
										.getReturnOrderItemDtos().get(0)
										.getId()
										+ "");
					}
				});
			} else if (status.equals(SellerOrderActivity.orderReturnStatus[3])) { // 待
				changeTextViewShow(holder, null, null, "审核成功，等待买家发货...");
			} else if (status.equals(MyOrderActivity.orderState[4])) {// 待退款
				changeTextViewShow(holder, "拒绝签收", "确认签收", null);
				holder.tv_01.setOnClickListener(new OnClickListener() {	
					@Override
					public void onClick(View v) {
						SellerOrderReturnDetailsActivity.competed_refuse(
								sellerRefoundOrderInfo, sellerRefoundOrderInfo
										.getReturnOrderItemDtos().get(0)
										.getId()
										+ "");
					}
				});
				holder.tv_02.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						SellerOrderReturnDetailsActivity.seller_operation(
								sellerRefoundOrderInfo, sellerRefoundOrderInfo
										.getReturnOrderItemDtos().get(0)
										.getId()
										+ "");
					}
				});
			} else if (status.equals(SellerOrderActivity.orderReturnStatus[8])) {
				changeTextViewShow(holder, null, null, "审核未通过");
			} else if (SellerOrderActivity.orderReturnStatus[5].equals(status)) {
				changeTextViewShow(holder, null, null, "已拒绝签收");
			} else if (SellerOrderActivity.orderReturnStatus[6].equals(status)){
				changeTextViewShow(holder, null, null, "已签收，退款成功");
			} else if (status.equals(SellerOrderActivity.orderReturnStatus[1])) {
				changeTextViewShow(holder, null, null, "退款成功");
			}
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
