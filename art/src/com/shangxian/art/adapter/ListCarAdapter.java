package com.shangxian.art.adapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.shangxian.art.R;
import com.shangxian.art.R.string;
import com.shangxian.art.ShoppingcartActivity;
import com.shangxian.art.bean.CarItem;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.dialog.GoodsDialog;
import com.shangxian.art.dialog.GoodsDialog.GoodsDialogConfirmListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;

public class ListCarAdapter extends BaseAdapter implements GoodsDialogConfirmListener{
	private AbImageLoader mAbImageLoader_logo,mAbImageLoader_goodsImg;
	private ShoppingcartActivity shoppingcartActivity;
	private LayoutInflater inflater;
	public  List<CarItem> listdata;
	Map<String, Boolean> storeChecked = new HashMap<String, Boolean>();
	Map<String, Boolean> goodsCheced = new HashMap<String, Boolean>();
	int num = 0;

	public ListCarAdapter(Context contex, List<CarItem> listdata) {
		this.shoppingcartActivity = (ShoppingcartActivity) contex;
		this.listdata = listdata;
		inflater = LayoutInflater.from(contex);
		initState();
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

	public void initState() {
		clearState();
		for (int i = 0; i < listdata.size(); i++) {
			CarItem item = listdata.get(i);
			if (item.type == CarItem.SECTION) {
				storeChecked.put(item.listCarStoreBean.getShopId(), false);
			} else {
				goodsCheced.put(item.listCarGoodsBean.getCartItemId(), false);
			}
		}
	}

	public void clearState(){
		storeChecked.clear();
		goodsCheced.clear();
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
		MyLogger.i(item+"");
		if (item.type == CarItem.SECTION) {
			convertView = inflater.inflate(R.layout.list_car_store_item, null);
			LinearLayout ll_item= (LinearLayout) convertView.findViewById(R.id.ll_item);
			if(position==0){
				CommonUtil.setMargins(ll_item, 0, CommonUtil.px2dip(shoppingcartActivity, 20), 0, 0);
			}
			
			holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
			holder.storeName = (TextView) convertView.findViewById(R.id.car_storename);
			holder.check_store = (CheckBox) convertView.findViewById(R.id.check_store);
			holder.storeName.setText(item.listCarStoreBean.getShopName());
			mAbImageLoader_logo.display(holder.iv_logo,Constant.BASEURL
					+ item.getListCarStoreBean().getLogo());
			final List<ListCarGoodsBean> list = getGoodsByStoreId(item.listCarStoreBean.getShopId());
			holder.check_store.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean cheched) {

					if (cheched) {
						storeChecked.put(item.listCarStoreBean.getShopId(), true);
						for (int i = 0; i < list.size(); i++) {
							goodsCheced.put(list.get(i).getCartItemId(), true);
						}
					} else {
						storeChecked.put(item.listCarStoreBean.getShopId(), false);
						for (int i = 0; i < list.size(); i++) {
							goodsCheced.put(list.get(i).getCartItemId(), false);
						}

					}
					shoppingcartActivity.setSelecteAll();
					ListCarAdapter.this.notifyDataSetChanged();
				}
			});

			int flag = 0;
			for (int i = 0; i < list.size(); i++) {
				if (goodsCheced.get(list.get(i).getCartItemId()) != null && goodsCheced.get(list.get(i).getCartItemId()) == true) {
					flag++;
				}
			}
			//MyLogger.i(storeChecked.toString());
			if (flag == list.size() || (storeChecked.get(item.listCarStoreBean.getShopId())!=null&&storeChecked.get(item.listCarStoreBean.getShopId()) == true)) {
				holder.check_store.setChecked(true);
			} else {
				holder.check_store.setChecked(false);
			}
			//点击跳转到商铺详情界面
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
//					Intent intent=new Intent(context, StoreDetail.class);
//					intent.putExtra("storeId", item.listCarStoreBean.storeid);
//					context.startActivity(intent);
				}
			});	
		} else {
			convertView = inflater.inflate(R.layout.list_car_goods_item, null);
			holder.goodsName = (TextView) convertView.findViewById(R.id.car_goodsname);
			holder.goodsImg = (ImageView) convertView.findViewById(R.id.car_goodsimg);
			holder.goodsAttr = (TextView) convertView.findViewById(R.id.car_goodsattr);
			holder.goodsNum = (TextView) convertView.findViewById(R.id.car_num);
			holder.goodsPrice = (TextView) convertView.findViewById(R.id.car_goods_price);
			holder.check_goods = (CheckBox) convertView.findViewById(R.id.check_goods);
			holder.updata = (ImageView) convertView.findViewById(R.id.car_updata);
			holder.updata.setVisibility(View.VISIBLE);
			mAbImageLoader_goodsImg.display(holder.goodsImg,Constant.BASEURL
					+ item.getListCarGoodsBean().getPhoto());
			// 给控件赋值
//			DisplayImageOptions options;
//			options = new DisplayImageOptions.Builder().cacheInMemory(true)// 是否緩存都內存中
//					.cacheOnDisc(true)// 是否緩存到sd卡上
//					.build();
//			ImageLoader imageLoader = ImageLoader.getInstance();
//			String path = (RequestServer.FILE_REQUEST + item.goods.goodsImg).replaceAll("\\\\", "/");
//			imageLoader.displayImage(path, holder.goodsImg, options, null);

			holder.goodsName.setText(item.listCarGoodsBean.getName());
			Map<String, String> selectedspec=item.listCarGoodsBean.getSelectedSpec();
			String specs="";
			for (Entry<String, String> entry : selectedspec.entrySet()) {
				specs=specs+entry.getValue()+"  ";
			}
			holder.goodsAttr.setText(specs);
			holder.goodsNum.setText("x"+item.listCarGoodsBean.getQuantity());
//			if(!StringUtils.isEmpty(item.listCarGoodsBean.goodsOldPrice)){
//				holder.goodsOldPrice.setText(item.goods.goodsOldPrice);
//			}else{
//				holder.goodsOldPrice.setVisibility(View.GONE);
//			}
			//holder.goodsOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
			holder.goodsPrice.setText("￥"+CommonUtil.priceConversion(item.listCarGoodsBean.getPromotionPrice()));
			//holder.goodsid = item.listCarGoodsBean.getCartItemId();

			if (goodsCheced.get(item.listCarGoodsBean.getCartItemId()) != null) {
				holder.check_goods.setChecked(goodsCheced.get(item.listCarGoodsBean.getCartItemId()));
			}

			holder.check_goods.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton arg0, boolean checked) {
					MyLogger.i("check_goods");
					if (checked) {
						goodsCheced.put(item.listCarGoodsBean.getCartItemId(), true);
					} else {
						goodsCheced.put(item.listCarGoodsBean.getCartItemId(), false);
						storeChecked.put(item.listCarGoodsBean.getShopId(), false);

					}
					shoppingcartActivity.setSelecteAll();
					ListCarAdapter.this.notifyDataSetChanged();
				}
			});
			
			holder.updata.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					GoodsDialog dialog =  new GoodsDialog(shoppingcartActivity,ListCarAdapter.this,item.listCarGoodsBean);
					dialog.show();
				}
			});

			// 删除购物车
//			holder.goodsDelete.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//
//					CustomerAlterView.showSelectDialog(context, "您确定要删除此商品吗", new DialogClickListener() {
//						@Override
//						public void confirm() {
//							deleteGoods(item.id);
//						}
//
//						@Override
//						public void cancel() {
//
//						}
//					});
//
//				}
//			});
			// 显示商品详情
//			convertView.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					Intent intent = new Intent(context, GoodsDetail.class);
//					if (item != null && item.goods != null) {
//						intent.putExtra("goodsId", "" + item.goods.goodsId);
//					}
//					context.startActivity(intent);
//				}
//			});
		}
		return convertView;
	}

	// 更改购物车数量
	public void updateCarNum(final int num, String id, final TextView tv) {
//		dialog.show();
//		MerCartDTO car = new MerCartDTO();
//		car.setFid(id);
//		car.setFmerAmout(num);
//		KJHttp http = new KJHttp();
//		KJStringParams params = new KJStringParams();
//		params.put("data", RequestServer.getParams(SendMsgCode.update_carnum, car, null, 0));
//		http.urlPost(RequestServer.URL, params, new StringCallBack() {
//
//			@Override
//			public void onSuccess(String str) {
//				dialog.dismiss();
//				System.out.println("结果===" + str);
//				if (!StringUtils.isEmpty(str)) {
//					MessageDTO message = JSON.parseObject(str, MessageDTO.class);
//					if (message.getErrorCode().equals(ErrorCode.common_success)) {
//						updateData();
//					} else {
//						CustomerToast.makeText(context, "数量修改失败");
//					}
//				} else {
//					CustomerToast.makeText(context, "数量修改失败");
//				}
//			}
//
//			@Override
//			public void onFailure(Throwable t, int errorNo, String strMsg) {
//				super.onFailure(t, errorNo, strMsg);
//				dialog.dismiss();
//				CustomerToast.makeText(context, strMsg);
//			}
//		});
	}

	// 删除购物车商品
	public void deleteGoods(int id) {
//		dialog.show();
//		MerCartDTO car = new MerCartDTO();
//		car.setFid(id);
//		KJHttp http = new KJHttp();
//		KJStringParams params = new KJStringParams();
//		params.put("data", RequestServer.getParams(SendMsgCode.delete_car, car, null, 0));
//		http.urlPost(RequestServer.URL, params, new StringCallBack() {
//			public void onSuccess(String str) {
//				dialog.dismiss();
//				if (!StringUtils.isEmpty(str)) {
//					MessageDTO message = JSON.parseObject(str, MessageDTO.class);
//					if (message.getErrorCode().equals(ErrorCode.common_success)) {
//						// 更新listview
//						updateData();
//						CustomerToast.makeText(context, "删除成功");
//					} else {
//						CustomerToast.makeText(context, "删除失败");
//					}
//				} else {
//					CustomerToast.makeText(context, "删除失败");
//				}
//			}
//
//			@Override
//			public void onFailure(Throwable t, int errorNo, String strMsg) {
//				super.onFailure(t, errorNo, strMsg);
//				dialog.dismiss();
//				CustomerToast.makeText(context, strMsg);
//			}
//		});
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
				if (item.listCarGoodsBean.getShopId() .equals(id)) {
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

	public void selectAll(boolean checked) {
		for (int i = 0; i < listdata.size(); i++) {
			if (listdata.get(i).type == CarItem.SECTION) {
				storeChecked.put(listdata.get(i).listCarStoreBean.getShopId(), checked);
			} else {
				goodsCheced.put(listdata.get(i).listCarGoodsBean.getCartItemId(), checked);
			}
		}
		ListCarAdapter.this.notifyDataSetChanged();
	}

	public Map<String, Boolean> getStoreCheced() {
		return storeChecked;
	}

	public Map<String, Boolean> getGoodsCheced() {
		return goodsCheced;
	}
	public List<CarItem> getListItem() {
		return listdata;
	}

	// 重新获取数据刷新listview
	public void updateData() {
//		dialog.show();
//		int uid = sp.readInt(context, "userInfo", "id");
//		KJHttp http = new KJHttp();
//		KJStringParams params = new KJStringParams();
//		params.put("data", RequestServer.getParams(SendMsgCode.request_carlist, uid, null, 0));
//		http.urlPost(RequestServer.URL, params, new StringCallBack() {
//			@Override
//			public void onSuccess(String str) {
//				dialog.dismiss();
//				if (!StringUtils.isEmpty(str)) {
//					MessageDTO message = JSON.parseObject(str, MessageDTO.class);
//					if (message.getErrorCode().equals(ErrorCode.common_success)) {
//						InputStream in = context.getResources().openRawResource(R.drawable.img_goods);
//						Bitmap bitmap = BitmapFactory.decodeStream(in);
//						List<CarItem> listData = new ArrayList<CarItem>();
//						List<MerCartDTO> listCar = JSON.parseArray(message.getMsgData().toString(), MerCartDTO.class);
//						for (int i = 0; i < listCar.size(); i++) {
//							MerCartDTO car = listCar.get(i);
//							MerSourceDTO source = car.getMerSourceDTO();
//							ListCarStoreBean stores = new ListCarStoreBean();
//							stores.storeId = car.getFstoreId();
//							stores.storeName = car.getStoreName();
//							CarItem section = new CarItem(CarItem.SECTION, stores, null, stores.storeId);
//							listData.add(section);
//
//							ListCarGoodsBean goods = new ListCarGoodsBean();
//							goods.goodsId = car.getFmerId();
//							goods.goodsName = car.getMerName();
//							goods.goodsAttr = source.getStylesName();
//							goods.goodsNum = "" + car.getFmerAmout();
//							goods.storeId = car.getFstoreId();
//							goods.goodsImg = source.getImgPath();
//							goods.stock = source.getFstock();
//							if (!StringUtils.isEmpty(source.getFavPrice())) {
//								goods.goodsOldPrice = source.getFprice();
//								goods.goodsPrice = source.getFavPrice();
//							} else {
//								goods.goodsOldPrice = "";
//								goods.goodsPrice =source.getFprice();
//							}
//							CarItem item = new CarItem(CarItem.ITEM, null, goods, car.getFid());
//							listData.add(item);
//						}
//
//						listdata.clear();
//						listdata.addAll(removeDuplicate(listData));
//						ListCarAdapter.this.notifyDataSetChanged();
//					} else {
//						CustomerToast.makeText(context, "网络请求失败");
//					}
//				} else {
//					CustomerToast.makeText(context, "网络请求失败");
//				}
//			}
//
//			@Override
//			public void onFailure(Throwable t, int errorNo, String strMsg) {
//				super.onFailure(t, errorNo, strMsg);
//				dialog.dismiss();
//				CustomerToast.makeText(context, strMsg);
//			}
//		});
	}

	private List<CarItem> removeDuplicate(List<CarItem> list) {
		Set<CarItem> set = new HashSet<CarItem>();
		List<CarItem> newList = new ArrayList<CarItem>();
		for (Iterator<CarItem> iter = list.iterator(); iter.hasNext();) {
			CarItem element = (CarItem) iter.next();
			if (set.add(element))
				newList.add(element);
		}
		return newList;
	}

	public class ViewHolder {
		public CheckBox check_store;
		public CheckBox check_goods;
		public TextView storeName;
		public TextView goodsName;
		public ImageView iv_logo;
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
		public ImageView updata;
	}

	@Override
	public void goodsDialogConfirm(String str) {
		this.notifyDataSetChanged();
	}

}
