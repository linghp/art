package com.shangxian.art.adapter;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.image.AbImageLoader;
import com.shangxian.art.MyOrderActivity;
import com.shangxian.art.R;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.ProductItemDto;
import com.shangxian.art.cache.Imageloader_homePager;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;

public class MyOrderListAdapter extends BaseAdapter {
	private AbImageLoader mAbImageLoader_logo,mAbImageLoader_goodsImg;
	private Context context;
	private LayoutInflater inflater;
	private List<MyOrderItem> myOrderItems=new ArrayList<MyOrderItem>();

	public MyOrderListAdapter(Context contex, List<MyOrderItem> myOrderItems) {
		this.context = contex;
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
	public Object getItem(int position) {
		return myOrderItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder ;;
		final MyOrderItem myOrderItem = (MyOrderItem) getItem(position);
		if(convertView==null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_myorder_item, null);
			holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
			holder.storeName = (TextView) convertView.findViewById(R.id.car_storename);
			holder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
			holder.tv_allquantity = (TextView) convertView.findViewById(R.id.tv_allquantity);
			holder.tv_payment = (TextView) convertView.findViewById(R.id.tv_payment);
			holder.tv_01 = (TextView) convertView.findViewById(R.id.tv_01);
			holder.tv_02 = (TextView) convertView.findViewById(R.id.tv_02);
			holder.ll_goodsitem_add=(LinearLayout) convertView.findViewById(R.id.ll_goodsitem_add);
			holder.ll_goodsitem_add.setBackgroundResource(R.drawable.shape_top);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
			holder.ll_goodsitem_add.removeAllViews();
			List<ProductItemDto> listCarGoodsBeans=myOrderItem.getProductItemDtos();
			for (ProductItemDto productItemDto : listCarGoodsBeans) {
				View child = inflater.inflate(
						R.layout.list_car_goods_item, null);
				holder.ll_goodsitem_add.addView(child);
				((TextView) child.findViewById(R.id.car_goodsname)).setText(productItemDto.getName());
				//holder.goodsImg = (ImageView) child.findViewById(R.id.car_goodsimg);
				//holder.goodsAttr = (TextView) child.findViewById(R.id.car_goodsattr);
				TextView goodsNum = (TextView) child.findViewById(R.id.car_num);
				TextView goodsPrice = (TextView) child.findViewById(R.id.car_goods_price);
				//final ViewHolder holder1 = new ViewHolder();
				ImageView goodsImg = (ImageView) child.findViewById(R.id.car_goodsimg);
				goodsNum.setText("x"+productItemDto.getQuantity());
				goodsPrice.setText("￥"+productItemDto.getPrice());
			    child.findViewById(R.id.check_goods).setVisibility(View.GONE);
				mAbImageLoader_goodsImg.display(goodsImg,Constant.BASEURL
						+ productItemDto.getProductSacle());
			}
			if(myOrderItem!=null){
			holder.storeName.setText(myOrderItem.getShopName());
			holder.tv_state.setText(myOrderItem.stateValue);
			holder.tv_allquantity.setText("共"+myOrderItem.getTotalQuantity()+"件商品");
			holder.tv_payment.setText("￥"+myOrderItem.getTotalPrice());
	        mAbImageLoader_logo.display(holder.iv_logo,Constant.BASEURL
					+ myOrderItem.getShopLogo());
			}
			if(myOrderItem.getStatus().equals(MyOrderActivity.orderState[1])){
				holder.tv_01.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						CommonUtil.toast("click", context);
					}
				});
				holder.tv_02.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						CommonUtil.toast("click", context);
					}
				});
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
}
