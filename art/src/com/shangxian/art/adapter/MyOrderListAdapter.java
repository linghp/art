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

import com.shangxian.art.R;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.ProductItemDto;
import com.shangxian.art.cache.Imageloader_homePager;
import com.shangxian.art.constant.Constant;

public class MyOrderListAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<MyOrderItem> myOrderItems=new ArrayList<MyOrderItem>();

	public MyOrderListAdapter(Context contex, List<MyOrderItem> myOrderItems) {
		this.context = contex;
		this.myOrderItems = myOrderItems;
		inflater = LayoutInflater.from(contex);
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
				Imageloader_homePager.displayImage(Constant.BASEURL
						+ productItemDto.getProductSacle(),
						goodsImg,
						new Handler(), null);
			}
			if(myOrderItem!=null){
			holder.storeName.setText(myOrderItem.getShopName());
			Imageloader_homePager.displayImage(Constant.BASEURL
					+ myOrderItem.getShopLogo(),
					holder.iv_logo,
					new Handler(), null);
			}
		return convertView;
	}

	public class ViewHolder {
		public CheckBox check_store;
		public ImageView iv_logo;
		public TextView storeName;
		public TextView goodsName;
		public ImageView goodsImg;
		public TextView goodsAttr;
		public TextView goodsNum;
		public TextView goodsPrice;
		public ImageView goodsDelete;
		public LinearLayout ll_goodsitem_add;
	}
}
