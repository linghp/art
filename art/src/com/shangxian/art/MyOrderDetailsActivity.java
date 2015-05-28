package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.shangxian.art.adapter.MyOrderListAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.MyOrderDetailBean;
import com.shangxian.art.bean.MyOrderDetailBean.ReceiverInfo;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.ProductItemDto;
import com.shangxian.art.bean.MyOrderDetailBean.OrderItem;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.MyOrderServer;
import com.shangxian.art.net.MyOrderServer.OnHttpResultCancelOrderListener;
import com.shangxian.art.net.MyOrderServer.OnHttpResultDelOrderListener;
import com.shangxian.art.net.MyOrderServer.OnHttpResultOrderDetailsListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

public class MyOrderDetailsActivity extends BaseActivity implements OnHttpResultOrderDetailsListener,OnHttpResultCancelOrderListener,OnHttpResultDelOrderListener{
	private TopView topView;
	private LinearLayout ll_goodsitem_add;
	private ImageView iv_logo;
	private TextView tv_storeName,tv_01,tv_02;
    final static String INTENTDATAKEY="ordernumber";
    private MyOrderDetailBean myOrderDetailBean;
    private MyOrderItem myOrderItem;
	private AbImageLoader mAbImageLoader_logo,mAbImageLoader_goodsImg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order_details);
		initViews();
		initData();
	}

	public static void startThisActivity(String ordernumber, Context context) {
		Intent intent = new Intent(context, MyOrderDetailsActivity.class);
		intent.putExtra(INTENTDATAKEY, ordernumber);
		context.startActivity(intent);
	}

	private void initData() {
		String ordernumber=  getIntent().getStringExtra(INTENTDATAKEY);
		if(!TextUtils.isEmpty(ordernumber)){
			myOrderItem=new MyOrderItem();
			myOrderItem.setOrderNumber(ordernumber);
			MyOrderServer.toGetOrderDetails(ordernumber, this);
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
		ll_goodsitem_add = (LinearLayout) findViewById(R.id.ll_goodsitem_add);
		tv_01 = (TextView) findViewById(R.id.tv_01);
		tv_02 = (TextView) findViewById(R.id.tv_02);
		findViewById(R.id.tv_noaddress).setVisibility(View.GONE);
	}

	@Override
	public void onHttpResultOrderDetails(MyOrderDetailBean myOrderDetailBean) {
		if(myOrderDetailBean!=null){
			this.myOrderDetailBean=myOrderDetailBean;
			myOrderItem.setOrderNumber(myOrderDetailBean.getOrderNumber());
			myOrderItem.setTotalPrice(myOrderDetailBean.getTotalPrice());
			updateViews();
		}else{
			
		}
	}

	private void updateViews() {
		((TextView)findViewById(R.id.tv_header_01)).setText(MyOrderActivity.map_orderStateValue.get(myOrderDetailBean.getStatus()));
		((TextView)findViewById(R.id.tv_header_02)).setText(String.format(getString(R.string.text_order_Price), CommonUtil.priceConversion(myOrderDetailBean.getTotalPrice())));
		((TextView)findViewById(R.id.tv_header_03)).setText(String.format(getString(R.string.text_shippingPrice), CommonUtil.priceConversion(myOrderDetailBean.getShippingFee())));
		
		ReceiverInfo receiverInfo=myOrderDetailBean.getReceiverInfo();
		((TextView)findViewById(R.id.tv_receiver)).setText(String.format(getString(R.string.text_receiver), receiverInfo.getReceiverName()));
		((TextView)findViewById(R.id.tv_address)).setText(String.format(getString(R.string.text_receiver_address), receiverInfo.getDeliveryAddress()));
		((TextView)findViewById(R.id.tv_phone)).setText(receiverInfo.getReceiverTel());
		
		((TextView)findViewById(R.id.tv_tradetime)).setText(String.format(getString(R.string.text_tradetime), myOrderDetailBean.getOrderedDate()));
		
		//动态添加商品
		((TextView)findViewById(R.id.car_storename)).setText(myOrderDetailBean.getSellerName());
//        mAbImageLoader_logo.display(holder.iv_logo,Constant.BASEURL
//				+ myOrderItem.getShopLogo());
		ll_goodsitem_add.removeAllViews();
		List<OrderItem> orderItems=myOrderDetailBean.getOrderItems();
		for (OrderItem OrderItem : orderItems) {
			View child = inflater.inflate(
					R.layout.list_car_goods_item, null);
			ll_goodsitem_add.addView(child);
			((TextView) child.findViewById(R.id.car_goodsname)).setText(OrderItem.getName());
			//holder.goodsImg = (ImageView) child.findViewById(R.id.car_goodsimg);
			//holder.goodsAttr = (TextView) child.findViewById(R.id.car_goodsattr);
			TextView goodsNum = (TextView) child.findViewById(R.id.car_num);
			TextView goodsPrice = (TextView) child.findViewById(R.id.car_goods_price);
			//final ViewHolder holder1 = new ViewHolder();
			ImageView goodsImg = (ImageView) child.findViewById(R.id.car_goodsimg);
			goodsNum.setText("x"+OrderItem.getQuantity());
			goodsPrice.setText("￥"+CommonUtil.priceConversion(OrderItem.getPrice()));
		    child.findViewById(R.id.check_goods).setVisibility(View.GONE);
			mAbImageLoader_goodsImg.display(goodsImg,Constant.BASEURL
					+ OrderItem.getProductSacle());
		}
		
		//根据状态显示按钮
		String status=myOrderDetailBean.getStatus();
		String[] orderState=MyOrderActivity.orderState;
		if(status.equals(orderState[1])){
			tv_01.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//CommonUtil.toast("click", context);
					MyOrderServer.toCancelOrder(myOrderItem, MyOrderDetailsActivity.this);
				}
			});
			tv_02.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//CommonUtil.toast("click", context);
					List<String> ordernumber=new ArrayList<String>();
					ordernumber.add(myOrderItem.getOrderNumber());
					PayActivity.startThisActivity(ordernumber, CommonUtil.priceConversion(myOrderItem.getTotalPrice()), MyOrderDetailsActivity.this);
				}
			});
		}else if(status.equals(orderState[2])){
			
		}else if(status.equals(orderState[3])){
			
		}
	}
	
	@Override
	public void onHttpResultCancelOrder(MyOrderItem myOrderItem) {
		if(myOrderItem!=null){
		//	this.notifyDataSetChanged();
			CommonUtil.toast("取消成功", this);
		}else{
			CommonUtil.toast("取消失败", this);
		}
	}

	@Override
	public void onHttpResultDelOrder(MyOrderItem myOrderItem) {
		if(myOrderItem!=null){
//			myOrderItems.remove(myOrderItem);
//			this.notifyDataSetChanged();
			CommonUtil.toast("删除成功", this);
		}else{
			CommonUtil.toast("删除失败", this);
		}
	}
}
