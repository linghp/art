package com.shangxian.art.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.shangxian.art.R;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.bean.ListCarStoreBean;
import com.shangxian.art.cache.Imageloader_homePager;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;

public class ListConfirmOrderAdapter extends BaseAdapter {
	private AbImageLoader mAbImageLoader_logo, mAbImageLoader_goodsImg;
	private Context context;
	private LayoutInflater inflater;
	private List<ListCarStoreBean> listStoreBean = new ArrayList<ListCarStoreBean>();

	// private HashMap<String, List<ListCarGoodsBean>> hashmapGoodsBeans=new
	// HashMap<String,
	// List<ListCarGoodsBean>>();//此变量没有多少用，可以用listStoreBean代替，暂不修改

	public ListConfirmOrderAdapter(Context contex,
			List<ListCarStoreBean> listStoreBean) {
		this.context = contex;
		this.listStoreBean = listStoreBean;
		// this.hashmapGoodsBeans = hashmapGoodsBeans;
		inflater = LayoutInflater.from(contex);
		// initdata();
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

	// private void initdata() {
	// for (CarItem carItem : listdata) {
	// if(carItem.getType()==CarItem.SECTION){
	// listStoreBean.add(carItem.getListCarStoreBean());
	// }
	// }
	// for (ListCarStoreBean listCarStoreBean : listStoreBean) {
	// List<ListCarGoodsBean> listGoodsBeans=new ArrayList<ListCarGoodsBean>();
	// String storeid=listCarStoreBean.getStoreid();
	// for (CarItem carItem : listdata) {
	// if(carItem.getType()==CarItem.ITEM){
	// if(carItem.getListCarGoodsBean().getStoreId().equals(storeid)){
	// listGoodsBeans.add(carItem.getListCarGoodsBean());
	// }
	// }
	// }
	// hashmapGoodsBeans.put(storeid, listGoodsBeans);
	// }
	// }

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

	// private int selectIndex = -1;//当前item的位置
	// private int currentSelection = 0;//偏移量
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		;
		final ListCarStoreBean listCarStoreBean = (ListCarStoreBean) getItem(position);
		// MyLogger.i(listStoreBean.toString());
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_confirmorder_item,
					null);
			holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
			holder.storeName = (TextView) convertView
					.findViewById(R.id.car_storename);
			holder.tv_shippingfee = (TextView) convertView
					.findViewById(R.id.tv_shippingfee);
			holder.tv_payment = (TextView) convertView
					.findViewById(R.id.tv_payment);
			holder.tv_allquantity = (TextView) convertView
					.findViewById(R.id.tv_allquantity);
			holder.ll_goodsitem_add = (LinearLayout) convertView
					.findViewById(R.id.ll_goodsitem_add);
			holder.et_message = (EditText) convertView
					.findViewById(R.id.et_message);
			holder.ll_goodsitem_add.setBackgroundResource(R.drawable.shape_top);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.ll_goodsitem_add.removeAllViews();
		List<ListCarGoodsBean> listCarGoodsBeans = listCarStoreBean
				.getItemDtos();
		float allprice=0;
		if (listCarGoodsBeans != null && listCarGoodsBeans.size() > 0) {
			for (ListCarGoodsBean listCarGoodsBean : listCarGoodsBeans) {
				allprice+=listCarGoodsBean.getPromotionPrice()*listCarGoodsBean.getQuantity();
				
				View child = inflater.inflate(R.layout.list_car_goods_item,
						null);
				holder.ll_goodsitem_add.addView(child);
				((TextView) child.findViewById(R.id.car_goodsname))
						.setText(listCarGoodsBean.getName());
				// holder.goodsImg = (ImageView)
				// child.findViewById(R.id.car_goodsimg);
				TextView goodsAttr = (TextView) child
						.findViewById(R.id.car_goodsattr);
				TextView goodsNum = (TextView) child.findViewById(R.id.car_num);
				TextView goodsPrice = (TextView) child
						.findViewById(R.id.car_goods_price);
				// final ViewHolder holder1 = new ViewHolder();
				ImageView goodsImg = (ImageView) child
						.findViewById(R.id.car_goodsimg);
				goodsNum.setText("x" + listCarGoodsBean.getQuantity());
				goodsPrice.setText("￥"
						+ CommonUtil.priceConversion(listCarGoodsBean
								.getPromotionPrice()));

				Map<String, String> selectedspec = listCarGoodsBean
						.getSelectedSpec();
				String specs = "";
				for (Entry<String, String> entry : selectedspec.entrySet()) {
					specs = specs + entry.getValue() + "  ";
				}
				goodsAttr.setText(specs);

				child.findViewById(R.id.check_goods).setVisibility(View.GONE);
				mAbImageLoader_goodsImg.display(goodsImg, Constant.BASEURL
						+ listCarGoodsBean.getPhoto());
			}
		}
		holder.storeName.setText(listCarStoreBean.getShopName());
		holder.tv_shippingfee.setText("￥  "+CommonUtil.priceConversion(listCarStoreBean.getItemDtos().get(0).getShippingFee()));
		holder.tv_allquantity.setText("共  "+listCarStoreBean.getItemDtos().size()+"  件商品");
		holder.tv_payment.setText("共计：￥"+CommonUtil.priceConversion(allprice+listCarStoreBean.getItemDtos().get(0).getShippingFee()));
		mAbImageLoader_logo.display(holder.iv_logo, Constant.BASEURL
				+ listCarStoreBean.getLogo());
		holder.et_message.setText(listCarStoreBean.getRecommand());

		// holder.et_message.setOnFocusChangeListener(new
		// OnFocusChangeListener() {
		// public void onFocusChange(View view, boolean hasFocus) {
		// if (!hasFocus){
		// //final int position = view.getId();
		// final EditText editText = (EditText) view;
		// // myItems.set(position, editText.getText().toString());
		// listCarStoreBean.setRecommand(editText.getText().toString());
		// MyLogger.i(editText.getText().toString());
		// }
		// }
		// });
		holder.et_message.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				listCarStoreBean.setRecommand(s.toString());
				MyLogger.i(s.toString());
			}
		});

		return convertView;
	}

	// public List<ListCarGoodsBean> getGoodsByStoreId(String id) {
	// List<ListCarGoodsBean> listgoods = new ArrayList<ListCarGoodsBean>();
	// for (int i = 0; i < listdata.size(); i++) {
	// CarItem item = listdata.get(i);
	// if (item.type == CarItem.ITEM) {
	// if (item.listCarGoodsBean.storeId .equals(id)) {
	// listgoods.add(item.listCarGoodsBean);
	// }
	// }
	// }
	// return listgoods;
	// }

	// public List<CarItem> getListItem() {
	// return listdata;
	// }

	// private List<CarItem> removeDuplicate(List<CarItem> list) {
	// Set<CarItem> set = new HashSet<CarItem>();
	// List<CarItem> newList = new ArrayList<CarItem>();
	// for (Iterator<CarItem> iter = list.iterator(); iter.hasNext();) {
	// CarItem element = (CarItem) iter.next();
	// if (set.add(element))
	// newList.add(element);
	// }
	// return newList;
	// }

	public class ViewHolder {
		public CheckBox check_store;
		public CheckBox check_goods;
		public ImageView iv_logo;
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
		public EditText et_message;
		public String goodsid;
		public String storeid;
		public LinearLayout ll_goodsitem_add;
		
		public TextView tv_shippingfee;
		public TextView tv_allquantity;
		public TextView tv_payment;
	}
}
