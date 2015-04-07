package com.shangxian.art.adapter;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.bean.CarItem;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;

public class ListConfirmOrderAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	public  List<CarItem> listdata;

	public ListConfirmOrderAdapter(Context contex, List<CarItem> listdata) {
		this.context = contex;
		this.listdata = listdata;
		inflater = LayoutInflater.from(contex);
	}



	public int getCount() {
		return listdata.size();
	}

	@Override
	public Object getItem(int position) {
		return listdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder = new ViewHolder();
		MyLogger.i(position+"");
		final CarItem item = (CarItem) getItem(position);
		if (item.type == CarItem.SECTION) {
			convertView = inflater.inflate(R.layout.list_car_store_item, null);
			LinearLayout ll_item= (LinearLayout) convertView.findViewById(R.id.ll_item);
			if(position==0){
				CommonUtil.setMargins(ll_item, 0, CommonUtil.px2dip(context, 20), 0, 0);
			}
			holder.storeName = (TextView) convertView.findViewById(R.id.car_storename);
			holder.check_store = (CheckBox) convertView.findViewById(R.id.check_store);
			holder.check_store.setVisibility(View.GONE);
			holder.storeName.setText(item.listCarStoreBean.storename);
		} else {
			convertView = inflater.inflate(R.layout.list_car_goods_item, null);
			holder.goodsName = (TextView) convertView.findViewById(R.id.car_goodsname);
			holder.goodsImg = (ImageView) convertView.findViewById(R.id.car_goodsimg);
			holder.goodsAttr = (TextView) convertView.findViewById(R.id.car_goodsattr);
			holder.goodsNum = (TextView) convertView.findViewById(R.id.car_num);
			holder.goodsPrice = (TextView) convertView.findViewById(R.id.car_goods_price);
			// 给控件赋值
//			DisplayImageOptions options;
//			options = new DisplayImageOptions.Builder().cacheInMemory(true)// 是否緩存都內存中
//					.cacheOnDisc(true)// 是否緩存到sd卡上
//					.build();
//			ImageLoader imageLoader = ImageLoader.getInstance();
//			String path = (RequestServer.FILE_REQUEST + item.goods.goodsImg).replaceAll("\\\\", "/");
//			imageLoader.displayImage(path, holder.goodsImg, options, null);

			holder.goodsName.setText(item.listCarGoodsBean.goodsName);
			//holder.goodsAttr.setText(item.listCarGoodsBean.goodsAttr);
			holder.goodsNum.setText("x"+item.listCarGoodsBean.goodsNum);
//			if(!StringUtils.isEmpty(item.listCarGoodsBean.goodsOldPrice)){
//				holder.goodsOldPrice.setText(item.goods.goodsOldPrice);
//			}else{
//				holder.goodsOldPrice.setVisibility(View.GONE);
//			}
			//holder.goodsOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
			holder.goodsPrice.setText("￥"+item.listCarGoodsBean.price);
			holder.goodsid = item.listCarGoodsBean.goodsId;
		}
		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	public List<ListCarGoodsBean> getGoodsByStoreId(String id) {
		List<ListCarGoodsBean> listgoods = new ArrayList<ListCarGoodsBean>();
		for (int i = 0; i < listdata.size(); i++) {
			CarItem item = listdata.get(i);
			if (item.type == CarItem.ITEM) {
				if (item.listCarGoodsBean.storeId .equals(id)) {
					listgoods.add(item.listCarGoodsBean);
				}
			}
		}
		return listgoods;
	}

	@Override
	public int getItemViewType(int position) {
		CarItem item = (CarItem) getItem(position);
		return item.type;
	}

	public List<CarItem> getListItem() {
		return listdata;
	}

//	private List<CarItem> removeDuplicate(List<CarItem> list) {
//		Set<CarItem> set = new HashSet<CarItem>();
//		List<CarItem> newList = new ArrayList<CarItem>();
//		for (Iterator<CarItem> iter = list.iterator(); iter.hasNext();) {
//			CarItem element = (CarItem) iter.next();
//			if (set.add(element))
//				newList.add(element);
//		}
//		return newList;
//	}

	public class ViewHolder {
		public CheckBox check_store;
		public CheckBox check_goods;
		public TextView storeName;
		public TextView goodsName;
		public ImageView goodsImg;
		public TextView goodsAttr;
		public TextView goodsNum;
		public ImageButton numReduce;
		public ImageButton numAdd;
		public TextView goodsOldPrice;
		public TextView goodsPrice;
		public TextView storeId;
		public TextView goodsId;
		public ImageView goodsDelete;
		public String goodsid;
		public String storeid;
	}
}
