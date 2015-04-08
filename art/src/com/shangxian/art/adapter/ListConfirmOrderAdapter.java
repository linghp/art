package com.shangxian.art.adapter;


import java.util.ArrayList;
import java.util.HashMap;
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
import com.shangxian.art.bean.ListCarStoreBean;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;

public class ListConfirmOrderAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<ListCarStoreBean> listStoreBean=new ArrayList<ListCarStoreBean>();
	private HashMap<String, List<ListCarGoodsBean>> hashmapGoodsBeans=new HashMap<String, List<ListCarGoodsBean>>();

	public ListConfirmOrderAdapter(Context contex, List<ListCarStoreBean> listStoreBean,HashMap<String, List<ListCarGoodsBean>> hashmapGoodsBeans) {
		this.context = contex;
		this.listStoreBean = listStoreBean;
		this.hashmapGoodsBeans = hashmapGoodsBeans;
		inflater = LayoutInflater.from(contex);
		//initdata();
	}



//	private void initdata() {
//		for (CarItem carItem : listdata) {
//			if(carItem.getType()==CarItem.SECTION){
//				listStoreBean.add(carItem.getListCarStoreBean());
//			}
//		}
//		for (ListCarStoreBean listCarStoreBean : listStoreBean) {
//			List<ListCarGoodsBean> listGoodsBeans=new ArrayList<ListCarGoodsBean>();
//			String storeid=listCarStoreBean.getStoreid();
//			for (CarItem carItem : listdata) {
//				if(carItem.getType()==CarItem.ITEM){
//					if(carItem.getListCarGoodsBean().getStoreId().equals(storeid)){
//						listGoodsBeans.add(carItem.getListCarGoodsBean());
//					}
//				}
//			}
//			hashmapGoodsBeans.put(storeid, listGoodsBeans);
//		}
//	}



	public int getCount() {
		return listStoreBean.size();
	}

	@Override
	public Object getItem(int position) {
		return listStoreBean.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder = new ViewHolder();
		MyLogger.i(position+"");
		final ListCarStoreBean listCarStoreBean = (ListCarStoreBean) getItem(position);
			convertView = inflater.inflate(R.layout.list_confirmorder_item, null);
			holder.storeName = (TextView) convertView.findViewById(R.id.car_storename);
			holder.storeName.setText(listCarStoreBean.storename);
			holder.ll_goodsitem_add=(LinearLayout) convertView.findViewById(R.id.ll_goodsitem_add);
			holder.ll_goodsitem_add.setBackgroundResource(R.drawable.shape_top);
			holder.ll_goodsitem_add.removeAllViews();
			List<ListCarGoodsBean> listCarGoodsBeans=hashmapGoodsBeans.get(listCarStoreBean.getStoreid());
			for (ListCarGoodsBean listCarGoodsBean : listCarGoodsBeans) {
				View child = inflater.inflate(
						R.layout.list_car_goods_item, null);
				View view=new View(context);
				holder.ll_goodsitem_add.addView(child);
				((TextView) child.findViewById(R.id.car_goodsname)).setText(listCarGoodsBean.goodsName);
				//holder.goodsImg = (ImageView) child.findViewById(R.id.car_goodsimg);
				//holder.goodsAttr = (TextView) child.findViewById(R.id.car_goodsattr);
				TextView goodsNum = (TextView) child.findViewById(R.id.car_num);
				TextView goodsPrice = (TextView) child.findViewById(R.id.car_goods_price);
				goodsNum.setText("x"+listCarGoodsBean.goodsNum);
				goodsPrice.setText("￥"+listCarGoodsBean.price);
				
				 child.findViewById(R.id.check_goods).setVisibility(View.GONE);
			}
			
			// 给控件赋值
//			DisplayImageOptions options;
//			options = new DisplayImageOptions.Builder().cacheInMemory(true)// 是否緩存都內存中
//					.cacheOnDisc(true)// 是否緩存到sd卡上
//					.build();
//			ImageLoader imageLoader = ImageLoader.getInstance();
//			String path = (RequestServer.FILE_REQUEST + item.goods.goodsImg).replaceAll("\\\\", "/");
//			imageLoader.displayImage(path, holder.goodsImg, options, null);
//			if(item.getType()==CarItem.ITEM){
//			holder.goodsName.setText(item.listCarGoodsBean.goodsName);
//			
//			//holder.goodsAttr.setText(item.listCarGoodsBean.goodsAttr);
//			holder.goodsNum.setText("x"+item.listCarGoodsBean.goodsNum);
//			if(!StringUtils.isEmpty(item.listCarGoodsBean.goodsOldPrice)){
//				holder.goodsOldPrice.setText(item.goods.goodsOldPrice);
//			}else{
//				holder.goodsOldPrice.setVisibility(View.GONE);
//			}
			//holder.goodsOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
//			holder.goodsPrice.setText("￥"+item.listCarGoodsBean.price);
//			holder.goodsid = item.listCarGoodsBean.goodsId;
			//}
		return convertView;
	}

//	public List<ListCarGoodsBean> getGoodsByStoreId(String id) {
//		List<ListCarGoodsBean> listgoods = new ArrayList<ListCarGoodsBean>();
//		for (int i = 0; i < listdata.size(); i++) {
//			CarItem item = listdata.get(i);
//			if (item.type == CarItem.ITEM) {
//				if (item.listCarGoodsBean.storeId .equals(id)) {
//					listgoods.add(item.listCarGoodsBean);
//				}
//			}
//		}
//		return listgoods;
//	}


//	public List<CarItem> getListItem() {
//		return listdata;
//	}

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
		public LinearLayout ll_goodsitem_add;
	}
}
