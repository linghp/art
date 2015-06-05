package com.shangxian.art.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.shangxian.art.MyOrderActivity;
import com.shangxian.art.PayActivity;
import com.shangxian.art.R;
import com.shangxian.art.RefundOrderActivity;
import com.shangxian.art.ReimburseActivity;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.ProductItemDto;
import com.shangxian.art.bean.RefundOrderInfo;
import com.shangxian.art.bean.RefundOrderInfo_all;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.fragment.MyOrder_All_Fragment;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.MyOrderServer;
import com.shangxian.art.net.MyOrderServer.OnHttpResultCancelOrderListener;
import com.shangxian.art.net.MyOrderServer.OnHttpResultDelOrderListener;
import com.shangxian.art.utils.CommonUtil;

public class MyRefundOrderListAdapter extends BaseAdapter /*implements*/
		/*OnHttpResultCancelOrderListener, OnHttpResultDelOrderListener*/ {
	private AbImageLoader mAbImageLoader_logo, mAbImageLoader_goodsImg;
	private Context context;
	private Fragment fragment;
	private LayoutInflater inflater;
	private List<RefundOrderInfo_all> myOrderItems = new ArrayList<RefundOrderInfo_all>();

	public MyRefundOrderListAdapter(Context contex, Fragment fragment,
			List<RefundOrderInfo_all> myOrderItems) {
		this.context = contex;
		this.fragment = fragment;
		this.myOrderItems = myOrderItems;
		inflater = LayoutInflater.from(contex);
		mAbImageLoader_logo = AbImageLoader.newInstance(contex);
		mAbImageLoader_goodsImg = AbImageLoader.newInstance(contex);

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

	public int getCount() {
		return myOrderItems.size();
	}

	@Override
	public RefundOrderInfo_all getItem(int position) {
		return myOrderItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		final RefundOrderInfo_all myOrderItem = getItem(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_myorder_item, null);
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
			holder.tv_02 = (TextView) convertView.findViewById(R.id.tv_02);
			holder.ll_goodsitem_add = (LinearLayout) convertView
					.findViewById(R.id.ll_goodsitem_add);
			holder.ll_goodsitem_add.setBackgroundResource(R.drawable.shape_top);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.ll_goodsitem_add.removeAllViews();
		final List<RefundOrderInfo> listCarGoodsBeans = myOrderItem
				.getReturnOrderItemDtos();
		
		for (RefundOrderInfo productItemDto : listCarGoodsBeans) {
			View child = inflater.inflate(R.layout.list_car_goods_item, null);
			holder.ll_goodsitem_add.addView(child);
			((TextView) child.findViewById(R.id.car_goodsname))
					.setText(productItemDto.getName());
			// holder.goodsImg = (ImageView)
			child.findViewById(R.id.car_goodsimg);
			// holder.goodsAttr = (TextView)
			child.findViewById(R.id.car_goodsattr);
			TextView goodsNum = (TextView) child.findViewById(R.id.car_num);
			TextView goodsPrice = (TextView) child
					.findViewById(R.id.car_goods_price);
			// final ViewHolder holder1 = new ViewHolder();
			ImageView goodsImg = (ImageView) child
					.findViewById(R.id.car_goodsimg);
			goodsNum.setText("x" + productItemDto.getQuantity());
			goodsPrice.setText("￥"
					+ CommonUtil.priceConversion(productItemDto.getSubtotal()));
			child.findViewById(R.id.check_goods).setVisibility(View.GONE);
			mAbImageLoader_goodsImg.display(goodsImg, Constant.BASEURL
					+ productItemDto.getProductSacle());
		}

		if (myOrderItem != null) {
			holder.storeName.setText(RefundOrderActivity.map_orderStateValue.get(myOrderItem.getStatus()));
			holder.tv_state.setText(MyOrderActivity.map_orderStateValue
					.get(myOrderItem.getStatus()));
			holder.tv_allquantity.setText("共" + myOrderItem.getTotalQuantity()
					+ "件商品");
			holder.tv_payment.setText("￥"
					+ CommonUtil.priceConversion(myOrderItem.getTotalPrice()));
//			mAbImageLoader_logo.display(holder.iv_logo, Constant.BASEURL
//					+ myOrderItem.getProductSacle());

			// 根据订单状态显示下面一排按钮 //根据status显示item下面的按钮
			if (myOrderItem.getStatus().equals(RefundOrderActivity.orderState[0])) {// 待付款
				holder.tv_01.setText("取消退货");
				holder.tv_02.setText("付款");
				holder.tv_01.setVisibility(View.VISIBLE);
				holder.tv_02.setVisibility(View.GONE);
				holder.tv_01.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// CommonUtil.toast("click", context);
						for (int i = 0; i < listCarGoodsBeans.size(); i++) {
							RefundOrderInfo info = listCarGoodsBeans.get(i);
							MyOrderServer.toCancelRefund(info.getId() + "/" + myOrderItem.getOrderNumber(), new CallBack() {
								@Override
								public void onSimpleSuccess(Object res) {
									notifyDataSetChanged();
									CommonUtil.toast("取消成功", context);
								}
								
								@Override
								public void onSimpleFailure(int code) {
									CommonUtil.toast("删除失败", context);
								}
							});
						}
					}
				});
//				holder.tv_02.setOnClickListener(new View.OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// CommonUtil.toast("click", context);
////						List<String> ordernumber = new ArrayList<String>();
////						ordernumber.add(myOrderItem.getOrderNumber());
////						((MyOrder_All_Fragment) fragment)
////								.setMyOrderItem(myOrderItem);
////						PayActivity.startThisActivity_Fragment(ordernumber,
////								CommonUtil.priceConversion(myOrderItem
////										.getTotalPrice()), (Activity) context,
////								fragment);
//					}
//				});
			} else if (myOrderItem.getStatus().equals(
					MyOrderActivity.orderState[7])) {// 已取消交易
				holder.tv_01.setText("删除订单");
				holder.tv_01.setVisibility(View.VISIBLE);
				holder.tv_02.setVisibility(View.GONE);
				holder.tv_01.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// CommonUtil.toast("click", context);
//						MyOrderServer.toDelOrder(myOrderItem,
//								MyRefundOrderListAdapter.this);
					}
				});
			} else {// 待发货
				holder.tv_02.setText("退款");
				holder.tv_01.setVisibility(View.GONE);
				holder.tv_02.setVisibility(View.GONE);
			}

		}
		return convertView;
	}

	public class ViewHolder {
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
		public ImageView goodsDelete;
		public LinearLayout ll_goodsitem_add;
	}

//	@Override
//	public void onHttpResultCancelOrder(MyOrderItem myOrderItem) {
//		if (myOrderItem != null) {
//			this.notifyDataSetChanged();
//			CommonUtil.toast("取消成功", context);
//		} else {
//			CommonUtil.toast("取消失败", context);
//		}
//	}
//
//	@Override
//	public void onHttpResultDelOrder(MyOrderItem myOrderItem) {
//		if (myOrderItem != null) {
//			myOrderItems.remove(myOrderItem);
//			this.notifyDataSetChanged();
//			CommonUtil.toast("删除成功", context);
//		} else {
//			CommonUtil.toast("删除失败", context);
//		}
//	}
}
