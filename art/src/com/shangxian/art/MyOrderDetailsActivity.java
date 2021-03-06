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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.shangxian.art.adapter.MyOrderListAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CommonBean;
import com.shangxian.art.bean.MyOrderDetailBean;
import com.shangxian.art.bean.MyOrderDetailBean.OrderItem;
import com.shangxian.art.bean.MyOrderDetailBean.ReceiverInfo;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.MyOrderServer;
import com.shangxian.art.net.MyOrderServer.OnHttpResultCancelOrderListener;
import com.shangxian.art.net.MyOrderServer.OnHttpResultConfirmGoodsListener;
import com.shangxian.art.net.MyOrderServer.OnHttpResultDelOrderListener;
import com.shangxian.art.net.MyOrderServer.OnHttpResultOrderDetailsListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 订单详情
 * @author Administrator
 *
 */
public class MyOrderDetailsActivity extends BaseActivity implements OnHttpResultOrderDetailsListener,OnHttpResultCancelOrderListener,
OnHttpResultDelOrderListener,OnHttpResultConfirmGoodsListener{
	private TopView topView;
	private LinearLayout ll_goodsitem_add;
	private ImageView iv_logo;
	private TextView tv_storeName,tv_01,tv_02;
    private final static String INTENTDATAKEY="ordernumber";
    private MyOrderDetailBean myOrderDetailBean;
    private MyOrderItem myOrderItem;
	private AbImageLoader mAbImageLoader_logo,mAbImageLoader_goodsImg;
	private String productid;
	/** 是否是直接付款产生的而不是从我的订单去进入*/
	private  boolean isDirectPay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order_details);
		initViews();
		initData();
	}

	public static void startThisActivity(String ordernumber, Context context) {
		Intent intent = new Intent(context, MyOrderDetailsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(INTENTDATAKEY, ordernumber);
		intent.putExtra("isDirectPay", true);
		((Activity)context).startActivityForResult(intent, 1);
	}
	public static void startThisActivity_MyOrder(String ordernumber,String shopLogo,String shopName, Context context,Fragment fragment) {
		Intent intent = new Intent(context, MyOrderDetailsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(INTENTDATAKEY, ordernumber);
		intent.putExtra("Logo", shopLogo);
		intent.putExtra("Name", shopName);
		fragment.startActivityForResult(intent, 1);
	}

	private void initData() {
		String ordernumber=  getIntent().getStringExtra(INTENTDATAKEY);
		
		isDirectPay=getIntent().getBooleanExtra("isDirectPay", false);
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
	public void onHttpResultOrderDetails(MyOrderDetailBean myOrderDetailBean,CommonBean commonBean) {
		if(myOrderDetailBean!=null){
			this.myOrderDetailBean=myOrderDetailBean;
			myOrderItem.setOrderNumber(myOrderDetailBean.getOrderNumber());
			myOrderItem.setTotalPrice(myOrderDetailBean.getTotalPrice());
			myOrderItem.setStatus(myOrderDetailBean.getStatus());
			myOrderItem.setStatus(myOrderDetailBean.getStatus());
			if(myOrderDetailBean.getOrderItems()!=null&&myOrderDetailBean.getOrderItems().size()>0){
			myOrderItem.setShopName(myOrderDetailBean.getOrderItems().get(0).getName());
			}
			updateViews();
		}else if(commonBean!=null){
			myToast(commonBean.getResult());
		}else{
			myToast("查询失败");
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
		
		((TextView)findViewById(R.id.tv_ordernumber)).setText(String.format(getString(R.string.text_ordernumber), myOrderDetailBean.getOrderNumber()));
		((TextView)findViewById(R.id.tv_nhbnumber)).setText(String.format(getString(R.string.text_nhbnumber), myOrderDetailBean.getOrderId()));
		((TextView)findViewById(R.id.tv_tradetime)).setText(String.format(getString(R.string.text_tradetime), myOrderDetailBean.getOrderedDate()));
		System.out.println(">>>>>>>>>图标："+getIntent().getStringExtra("Logo")+"名字："+getIntent().getStringExtra("Name"));
		mAbImageLoader_logo.display(iv_logo,Constant.BASEURL + getIntent().getStringExtra("Logo"));
		tv_storeName.setText(""+getIntent().getStringExtra("Name"));
        
		//动态添加商品
		//((TextView)findViewById(R.id.car_storename)).setText(myOrderDetailBean.getSellerName());
		String[] orderState=MyOrderActivity.orderState;
		String[] orderReturnStatus=MyOrderActivity.orderReturnStatus;
		ll_goodsitem_add.removeAllViews();
		List<OrderItem> orderItems=myOrderDetailBean.getOrderItems();
		boolean isHaveProduct = false;// 是否有可以退款的商品
		if(orderItems.size()>0){
			final List<CheckBox> checkBoxs=new ArrayList<CheckBox>();
			for (final OrderItem orderItem : orderItems) {
			isHaveProduct=true;
			View child = inflater.inflate(
					R.layout.list_car_goods_item, null);
			ll_goodsitem_add.addView(child);
			((TextView) child.findViewById(R.id.car_goodsname)).setText(orderItem.getName());
			//holder.goodsImg = (ImageView) child.findViewById(R.id.car_goodsimg);
			TextView goodsAttr = (TextView) child.findViewById(R.id.car_goodsattr);
			TextView goodsNum = (TextView) child.findViewById(R.id.car_num);
			TextView goodsPrice = (TextView) child.findViewById(R.id.car_goods_price);
			//final ViewHolder holder1 = new ViewHolder();
			ImageView goodsImg = (ImageView) child.findViewById(R.id.car_goodsimg);
			CheckBox checkBox =  (CheckBox) child.findViewById(R.id.check_goods);
			Map<String, String> selectedspec = orderItem.getSpecs();
			String specs = "";
			for (Entry<String, String> entry : selectedspec.entrySet()) {
				specs = specs + entry.getValue() + "  ";
			}
			goodsAttr.setText(specs);
			goodsNum.setText("x"+orderItem.getQuantity());
			goodsPrice.setText("￥"+CommonUtil.priceConversion(orderItem.getUnitPrice()));
			if(!((myOrderItem.getStatus().equals(orderState[2])||myOrderItem.getStatus().equals(orderState[4])||myOrderItem.getStatus().equals(orderState[6]))&&orderItem.getOrderItemStatus().equals(orderReturnStatus[0]))){
				checkBox.setVisibility(View.GONE);
			}else{
				checkBoxs.add(checkBox);
				checkBox.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						for (CheckBox checkBox : checkBoxs) {
							checkBox.setChecked(false);
						}
							((CheckBox)v).setChecked(true);
							productid=orderItem.getId();
					}
				});
				
			}
			mAbImageLoader_goodsImg.display(goodsImg,Constant.BASEURL
					+ orderItem.getProductSacle());
			}
		}
		//根据状态显示按钮
		String status=myOrderDetailBean.getStatus();
		if(status.equals(orderState[1])){
			tv_01.setOnClickListener(new View.OnClickListener() {// 待付款   -----------------------------------1
				
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
					PayActivity.startThisActivity(ordernumber, CommonUtil.priceConversion(myOrderItem.getTotalPrice()),myOrderItem.getShopName(), MyOrderDetailsActivity.this);
				}
			});
		}else if(myOrderItem.getStatus().equals(orderState[7])||myOrderItem.getStatus().equals(
				MyOrderActivity.orderState[9])){// 已取消交易/交易关闭 ------------------------------------------7 9
			tv_01.setText("删除订单");
			tv_02.setVisibility(View.GONE);
			tv_01.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//CommonUtil.toast("click", context);
					MyOrderServer.toDelOrder(myOrderItem, MyOrderDetailsActivity.this);
				}
			});
		}else if(status.equals(orderState[2])){// 待发货    ------------------------------------------------------------2
			//List<OrderItem> orderItems2=myOrderDetailBean.getOrderItems();
			if(isHaveProduct){
//			String status_temp=myOrderDetailBean.getOrderItems().get(0).getOrderItemStatus();
//			MyLogger.i(status_temp);
//			if(TextUtils.isEmpty(status_temp)){
//				status_temp=orderReturnStatus[0];
//			}
			tv_02.setText("退款");
			tv_01.setVisibility(View.GONE);
			tv_02.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(match()){
						if(isDirectPay){
							ReimburseActivity.startThisActivity(false,myOrderDetailBean.getOrderNumber(), productid,0f, MyOrderDetailsActivity.this);
						}else if(MyOrderActivity.currentFragment!=null){
							MyOrderActivity.currentFragment.setMyOrderItem(myOrderItem);
							ReimburseActivity.startThisActivity_Fragment(false,myOrderDetailBean.getOrderNumber(), productid,0f, MyOrderDetailsActivity.this, MyOrderActivity.currentFragment);
					  }
					}
				}
			  });
			}else{
				tv_01.setVisibility(View.GONE);
				tv_02.setVisibility(View.GONE);
			}
		}else if(myOrderItem.getStatus().equals(orderState[3])){// 待收货   ---------------------------------------------------3
			tv_01.setVisibility(View.GONE);
			tv_02.setText("确认收货");
			tv_02.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//CommonUtil.toast("click", context);
					MyOrderServer.toConfirmGoods(myOrderItem, MyOrderDetailsActivity.this);
				}
			});
		}else if(status.equals(orderState[4])||status.equals(orderState[6])||status.equals(orderState[8])){// 已完成交易  待评价   已评价  -----------------4 6 8
			((TextView)findViewById(R.id.tv_header_01)).setText(MyOrderActivity.orderStateValue[4]);
//			String status_temp=myOrderDetailBean.getOrderItems().get(0).getOrderItemStatus();
//			MyLogger.i(status_temp);
//			if(TextUtils.isEmpty(status_temp)){
//				status_temp=orderReturnStatus[0];
//			}
			if (!isHaveProduct) {
				tv_01.setVisibility(View.VISIBLE);
				tv_01.setText("删除订单");
				tv_02.setVisibility(View.GONE);
			} else {
				tv_01.setVisibility(View.VISIBLE);
				tv_01.setText("删除订单");
				tv_02.setText("退款/退货");
				tv_02.setEnabled(true);
			}
			tv_01.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// CommonUtil.toast("click", context);
					MyOrderServer.toDelOrder(myOrderItem,
							MyOrderDetailsActivity.this);
				}
			});
			tv_02.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(match()){
						if(isDirectPay){
							ReimburseActivity.startThisActivity(true,myOrderDetailBean.getOrderNumber(), productid,0f, MyOrderDetailsActivity.this);
						}else if(MyOrderActivity.currentFragment!=null){
							MyOrderActivity.currentFragment.setMyOrderItem(myOrderItem);
							ReimburseActivity.startThisActivity_Fragment(true,myOrderDetailBean.getOrderNumber(), productid,0f, MyOrderDetailsActivity.this, MyOrderActivity.currentFragment);
					   }
					}
				}
			});
		}else if (myOrderItem.getStatus().equals(
				MyOrderActivity.orderState[5])) {// 退款失败   --------------------------------------------------------5
			tv_01.setText("取消订单");
			tv_01.setVisibility(View.VISIBLE);
			tv_02.setVisibility(View.GONE);
			tv_01.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// CommonUtil.toast("click", context);
					MyOrderServer.toCancelOrder(myOrderItem,
							MyOrderDetailsActivity.this);
				}
			});
		}
	}
	
	protected boolean match() {
		if(TextUtils.isEmpty(productid)){
			myToast("请选择商品");
			return false;
		}
		return true;
	}

	@Override
	public void onHttpResultCancelOrder(MyOrderItem myOrderItem) {
		if(myOrderItem!=null){
		//	this.notifyDataSetChanged();
			CommonUtil.toast("取消成功", this);
			setResult(RESULT_OK, new Intent().putExtra("MyOrderItem", myOrderItem));
			finish();
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
			setResult(RESULT_OK, new Intent().putExtra("isdelete", true));
			finish();
		}else{
			CommonUtil.toast("删除失败", this);
		}
	}

	@Override
	public void onHttpResultConfirmGoods(MyOrderItem myOrderItem) {
		if(myOrderItem!=null){
			CommonUtil.toast("确认收货完成", this);
			setResult(RESULT_OK, new Intent().putExtra("MyOrderItem", myOrderItem));
			finish();
	   }else{
		CommonUtil.toast("确认收货完成失败", this);
	  }
	}
}
