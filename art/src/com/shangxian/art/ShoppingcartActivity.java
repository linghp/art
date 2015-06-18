package com.shangxian.art;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.google.gson.Gson;
import com.shangxian.art.adapter.ListCarAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CarItem;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.bean.ListCarStoreBean;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.dialog.DeleteDialog;
import com.shangxian.art.dialog.DeleteDialog.Delete_I;
import com.shangxian.art.dialog.GoodsDialog.GoodsDialogEditListener;
import com.shangxian.art.dialog.RefreshDialog;
import com.shangxian.art.net.HttpClients;
import com.shangxian.art.net.HttpClients.HttpCilentListener;
import com.shangxian.art.net.HttpUtils;
import com.shangxian.art.net.NetWorkHelper;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 购物车
 * 
 * @author Administrator
 *
 */
public class ShoppingcartActivity extends BaseActivity implements
		OnHeaderRefreshListener, OnClickListener, HttpCilentListener, Delete_I,GoodsDialogEditListener,OnItemClickListener {
	private ListView listcar;
	private ImageView img_no_content_icon;
	public CheckBox selecteall;
	// private static ListCarAdapter adapter;
	// private CustomProgressDialog dialog;
	private TextView allprice;
	private float price;// 总价
	private Button btn_settlement;
	private AbPullToRefreshView mAbPullToRefreshView;
	private View ll_nonetwork, loading_big, ll_refresh_empty, rl_bottom;
	private AbHttpUtil httpUtil;
	private List<CarItem> listCarItem = new ArrayList<CarItem>();
	private List<ListCarStoreBean> listStore = new ArrayList<ListCarStoreBean>();
	private List<ListCarStoreBean> listStoreSelected = new ArrayList<ListCarStoreBean>();
	private ListCarAdapter adapter;
	private boolean isFromConfirmOrderAct;

	boolean isother = false;

	private Dialog refreshDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoppingcart);
		initViews();
	}
	
	public static void startThisActivity(Boolean isother, Context context) {
		Intent intent = new Intent(context, ShoppingcartActivity.class);
		intent.putExtra("isother", isother);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	private void initdata() {
		if (isLogin()) {
			loading_big.setVisibility(View.VISIBLE);
			requestTask();
		} else {
			loading_big.setVisibility(View.GONE);
		}
		adapter = new ListCarAdapter(ShoppingcartActivity.this, listCarItem);
		selecteall.setChecked(false);
		accountCar();
		// 全选点击事件
		selecteall.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (buttonView.isPressed() && adapter != null) {
					// myToast("click");
					adapter.selectAll(selecteall.isChecked());
					accountCar();
				}
			}
		});
	}

	private void requestTask() {
		HttpClients.getDo(Constant.BASEURL + Constant.CONTENT + Constant.CART,
				this);
	}

	@Override
	public void onResponse(String content) {
		MyLogger.i(content);
		mAbPullToRefreshView.onHeaderRefreshFinish();
		if (content == null) {
			ll_nonetwork.setVisibility(View.VISIBLE);
		}
		Gson gson = new Gson();
		try {
			JSONObject jsonObject = new JSONObject(content);
			String result_code = jsonObject.getString("result_code");
			if (result_code.equals("200")) {
				ll_nonetwork.setVisibility(View.GONE);
				rl_bottom.setVisibility(View.VISIBLE);
				JSONArray resultObjectArray = jsonObject.getJSONArray("result");
				int length = resultObjectArray.length();
				listStore.clear();
				for (int i = 0; i < length; i++) {
					JSONObject jo = resultObjectArray.getJSONObject(i);
					listStore.add(gson.fromJson(jo.toString(),
							ListCarStoreBean.class));
				}
				if (listStore.size() == 0) {// 无内容显示
					ll_refresh_empty.setVisibility(View.VISIBLE);
				} else {
					ll_refresh_empty.setVisibility(View.GONE);
				}
				assembleData();
				adapter.initState();
				listcar.setAdapter(adapter);
				setSelecteAll();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			loading_big.setVisibility(View.GONE);
		}

	}

	private void assembleData() {
		listCarItem.clear();
		for (ListCarStoreBean listCarStoreBean : listStore) {
			CarItem carItem = new CarItem(CarItem.SECTION, listCarStoreBean,
					null, "");
			listCarItem.add(carItem);
			String storeId = listCarStoreBean.getShopId();
			List<ListCarGoodsBean> listCarGoodsBeans = listCarStoreBean
					.getItemDtos();
			for (ListCarGoodsBean listCarGoodsBean : listCarGoodsBeans) {
				listCarGoodsBean.setShopId(storeId);
				CarItem carItem2 = new CarItem(CarItem.ITEM, null,
						listCarGoodsBean, "");
				listCarItem.add(carItem2);
			}
		}
		MyLogger.i(listCarItem.toString());
	}

	private void initViews() {
		Intent intent = getIntent();
		isother = intent.getBooleanExtra("isother", false);
		System.out.println("><><><><><><><<><><>" + isother);
		if (isother) {
			topView = (TopView) findViewById(R.id.top_title);
			topView.setActivity(this);
			topView.setVisibility(View.VISIBLE);
			topView.hideCenterSearch();
			topView.setTitle("购物篮");
			topView.showTitle();
			topView.setBack(R.drawable.back);// 返回
			topView.showRightBtn();
			topView.setRightBtnDrawable(R.drawable.delete);
			topView.setRightBtnListener(this);
		}

		img_no_content_icon=(ImageView) findViewById(R.id.img_no_content_icon);
		img_no_content_icon.setVisibility(View.VISIBLE);
		img_no_content_icon.setImageResource(R.drawable.noshoppingcartimage);
		((TextView)findViewById(R.id.txt_empty_message)).setText("购物篮空了，赶紧去购物");
		mAbPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);
		ll_nonetwork = findViewById(R.id.ll_nonetwork);
		listcar = (ListView) findViewById(R.id.listcar);
		selecteall = (CheckBox) findViewById(R.id.selectall);
		allprice = (TextView) findViewById(R.id.tv_car_allprice_value);
		btn_settlement = (Button) findViewById(R.id.btn_settlement);
		rl_bottom = findViewById(R.id.rl_bottom);
		loading_big = findViewById(R.id.loading_big);
		ll_refresh_empty = findViewById(R.id.ll_refresh_empty);

		rl_bottom.setVisibility(View.GONE);

		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		btn_settlement.setOnClickListener(this);
		listcar.setOnItemClickListener(this);
		
		mAbPullToRefreshView.setLoadMoreEnable(false);
		// 设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
	}

	// 结算购物车的总价格
	public void accountCar() {
		// 遍历选中的商品，算出总价
		price = 0;
		Iterator iter1 = adapter.getGoodsCheced().entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry entry = (Map.Entry) iter1.next();
			if ((Boolean) entry.getValue() == true) {
				for (int i = 0; i < adapter.listdata.size(); i++) {
					CarItem item = adapter.listdata.get(i);
					if (item.type == CarItem.ITEM) {
						if (item.listCarGoodsBean.getCartItemId().equals(
								entry.getKey())) {
							float goodsprice = item.listCarGoodsBean
									.getPromotionPrice();
							price += goodsprice
									* item.listCarGoodsBean.getQuantity();
						}
					}
				}
			}
		}
		allprice.setText("￥ " + CommonUtil.priceConversion(price));
	}

	// 设置全选是否选中
	public void setSelecteAll() {
		int flag = 0;
		Iterator iter = adapter.getStoreCheced().entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			if ((Boolean) entry.getValue() == true) {
				flag++;
			}
		}
		MyLogger.i(adapter.getStoreCheced().toString());
		if (flag == adapter.getStoreCheced().size() && flag > 0) {
			selecteall.setChecked(true);
		} else {
			selecteall.setChecked(false);
		}
		accountCar();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isFromConfirmOrderAct) {
			isFromConfirmOrderAct = false;
		} else {
			topView = MainActivity.getTopView();
			topView.setActivity(this);
			topView.hideLeftBtn();
			topView.showRightBtn();
			topView.setRightBtnDrawable(R.drawable.delete);
			topView.setRightBtnListener(this);
			topView.hideCenterSearch();
			topView.setCenterListener(null);
			topView.setTitle("购物篮");
			topView.showTitle();
			if (NetWorkHelper.checkNetState(this)) {
				loading_big.setVisibility(View.VISIBLE);
				if (!isLogin() && listCarItem.size() > 0) {// 如果是没有登陆且购物车有数据，清空
					listCarItem.clear();
					selecteall.setChecked(false);
					adapter.notifyDataSetChanged();
				} else {
					initdata();
				}
				ll_nonetwork.setVisibility(View.GONE);
			} else {
				ll_nonetwork.setVisibility(View.VISIBLE);
				loading_big.setVisibility(View.GONE);
			}
		}

	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		refreshTask();
	}

	public void refreshTask() {
		requestTask();
		// AbLogUtil.prepareLog(this);
		// AbTask mAbTask = new AbTask();
		// final AbTaskItem item = new AbTaskItem();
		// item.setListener(new AbTaskListListener() {
		// @Override
		// public List<?> getList() {
		// List<Map<String, Object>> newList = null;
		// try {
		// Thread.sleep(1000);
		// // newList = new ArrayList<Map<String, Object>>();
		// // Map<String, Object> map = null;
		// //
		// // for (int i = 0; i < pageSize; i++) {
		// // map = new HashMap<String, Object>();
		// // map.put("itemsIcon", mPhotoList.get(i));
		// // map.put("itemsTitle", "item" + (i + 1));
		// // map.put("itemsText", "item..." + (i + 1));
		// // newList.add(map);
		// //
		// // }
		// } catch (Exception e) {
		// }
		// return newList;
		// }
		//
		// @Override
		// public void update(List<?> paramList) {
		//
		// // 通知Dialog
		// // mDialogFragment.loadFinish();
		// // AbLogUtil.d(NearlyActivity.this, "返回", true);
		// // List<Map<String, Object>> newList = (List<Map<String,
		// // Object>>) paramList;
		// // list.clear();
		// // if (newList != null && newList.size() > 0) {
		// // list.addAll(newList);
		// // myListViewAdapter.notifyDataSetChanged();
		// // newList.clear();
		// // }
		// mAbPullToRefreshView.onHeaderRefreshFinish();
		// }
		//
		// });
		//
		// mAbTask.execute(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_settlement:
			doSettlement();
			break;
		case R.id.iv_reload:
			if (HttpUtils.checkNetWork(this)) {
				loading_big.setVisibility(View.VISIBLE);
				initdata();
			} else {
				listCarItem.clear();
			}
			break;
		case R.id.btn_right:
			if (adapter != null && listCarItem.size() > 0) {
				if (selectedCount() > 0) {
					//new DeleteDialog(this, this, "确定要删除所选的商品？").show();
					doDelete();
				} else {
					myToast("请选择要删除的商品");
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void doDelete() {
		Map<String, Boolean> goodsCheced = adapter.getGoodsCheced();
		Map<String, Boolean> storeCheced = adapter.getStoreCheced();
		final List<CarItem> listCarItemDelete = new ArrayList<CarItem>();
		List<String> cartItemIds = new ArrayList<String>();
		for (CarItem carItem : listCarItem) {
			if (carItem.getType() == CarItem.SECTION) {
				if (storeCheced.get(carItem.getListCarStoreBean().getShopId())) {
					listCarItemDelete.add(carItem);
				}
			} else {
				ListCarGoodsBean listCarGoodsBean = carItem
						.getListCarGoodsBean();
				if (goodsCheced.get(listCarGoodsBean.getCartItemId())) {
					listCarItemDelete.add(carItem);
					cartItemIds.add(listCarGoodsBean.getCartItemId());
				}
			}
		}
		Gson gson = new Gson();
		String json = gson.toJson(cartItemIds);
		MyLogger.i(json);
		HttpClients.postDo(Constant.BASEURL + Constant.CONTENT
				+ Constant.DELCART, json, new HttpCilentListener() {

			@Override
			public void onResponse(String res) {
				MyLogger.i(res);
				try {
					JSONObject jsonObject = new JSONObject(res);
					String result_code = jsonObject.getString("result_code");
					if (result_code.equals("200")) {
						doDeleteNext(listCarItemDelete);
					}
				} catch (Exception e) {
					e.printStackTrace();
					myToast("删除失败");
				}
			}
		});
	}

	private void doDeleteNext(List<CarItem> listCarItemDelete) {
		listCarItem.removeAll(listCarItemDelete);
		adapter.initState();
		adapter.notifyDataSetChanged();
		setSelecteAll();
		myToast("删除成功");
	}

	private void doSettlement() {
		if (adapter != null) {
			if (selectedCount() > 0) {
				// Map<String, Boolean> mapSelect = new HashMap<String,
				// Boolean>();
				// List<ListCarGoodsBean> listGoods = new
				// ArrayList<ListCarGoodsBean>();
				// Iterator iter1 =
				// adapter.getGoodsCheced().entrySet().iterator();
				// while (iter1.hasNext()) {
				// Map.Entry entry1 = (Map.Entry) iter1.next();
				// if ((Boolean) entry1.getValue() == true) {
				// for (CarItem caritem : listCarItem) {
				// if (caritem != null && caritem.listCarGoodsBean != null) {
				// if
				// (caritem.listCarGoodsBean.goodsId.equals(entry1.getKey())){
				// listGoods.add(caritem.listCarGoodsBean);
				// mapSelect.put(caritem.listCarGoodsBean.storeId, true);
				// }
				// }
				// }
				// }
				// }

				// if (mapSelect.size() > 1) {
				// CustomerToast.makeText(this, "暂不支持多家店铺同时下单,请重新选择");
				// } else {
				// MerCartDTO car = new MerCartDTO();
				// car.setFstoreId(listGoods.get(0).storeId);
				// car.setStoreName(listGoods.get(0).storeName);
				List<CarItem> listCarItem_select = new ArrayList<CarItem>();// 选中的商品
				Map<String, Boolean> goodsCheced = adapter.getGoodsCheced();
				List<ListCarStoreBean> listStoreBean = new ArrayList<ListCarStoreBean>();
				// 提取店铺list
				LinkedHashMap<String, ListCarStoreBean> linkedHashMap = new LinkedHashMap<String, ListCarStoreBean>();
				ListCarStoreBean listCarStoreBean = null;
				for (CarItem carItem : listCarItem) {
					if (carItem.getType() == CarItem.SECTION) {
						listCarStoreBean = carItem.getListCarStoreBean();
					} else {
						if (goodsCheced.get(carItem.getListCarGoodsBean()
								.getCartItemId())) {
							listCarItem_select.add(carItem);
							linkedHashMap.put(carItem.getListCarGoodsBean()
									.getShopId(), listCarStoreBean);
						}
					}
				}
				for (ListCarStoreBean listCarStoreBean2 : linkedHashMap
						.values()) {
					listStoreBean.add(listCarStoreBean2);
				}

				// 存放每个店铺下的订单
				// HashMap<String, List<ListCarGoodsBean>> hashmapGoodsBeans =
				// new HashMap<String, List<ListCarGoodsBean>>();
				// for (ListCarStoreBean listCarStoreBean2 : linkedHashMap
				// .values()) {
				// List<ListCarGoodsBean> listGoodsBeans = new
				// ArrayList<ListCarGoodsBean>();
				// String storeid = listCarStoreBean2.getShopId();
				// for (CarItem carItem : listCarItem_select) {
				// if (carItem.getType() == CarItem.ITEM) {
				// if (carItem.getListCarGoodsBean().getShopId()
				// .equals(storeid)) {
				// listGoodsBeans.add(carItem
				// .getListCarGoodsBean());
				// }
				// }
				// }
				// hashmapGoodsBeans.put(storeid, listGoodsBeans);
				// }
				listStoreSelected.clear();
				for (ListCarStoreBean listCarStoreBean2 : linkedHashMap
						.values()) {
					List<ListCarGoodsBean> listGoodsBeans = new ArrayList<ListCarGoodsBean>();
					String storeid = listCarStoreBean2.getShopId();
					for (CarItem carItem : listCarItem_select) {
						if (carItem.getType() == CarItem.ITEM) {
							if (carItem.getListCarGoodsBean().getShopId()
									.equals(storeid)) {
								listGoodsBeans.add(carItem
										.getListCarGoodsBean());
							}
						}
					}
					listCarStoreBean2.setItemDtos(listGoodsBeans);;
					listStoreSelected.add(listCarStoreBean2);
				}
				// for (String storeid : hashmapGoodsBeans.keySet()) {
				// for (CarItem carItem : listCarItem) {
				// if(carItem.getType()==CarItem.ITEM){
				// if(storeid.equals(carItem.getListCarStoreBean().getStoreid()))
				// listStoreBean.add(carItem.getListCarStoreBean());
				// }
				// }
				// }

				ConfirmOrderActivity.startThisActivity(this, listStoreSelected,
						price, false);
			} else {
				myToast("请选择要结算的商品");
			}
		}
	}

	private int selectedCount() {
		int flag = 0;
		Iterator iter = adapter.getGoodsCheced().entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			if ((Boolean) entry.getValue() == true) {
				flag++;
			}
		}
		return flag;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		MyLogger.i("onActivityResult---resultCode:"+resultCode);
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			if (resultCode != RESULT_OK) {
				MyLogger.i("onActivityResult");
				isFromConfirmOrderAct = true;
			} else {
				isFromConfirmOrderAct = false;// 当结算完成时，确认订单销毁，返回到此要刷新
			}
		}
	}

	@Override
	public void goodsDialogEdit(ListCarGoodsBean listCarGoodsBean,ListCarGoodsBean newListCarGoodsBean) {
		refreshDialog=new RefreshDialog(this, 0); 
		refreshDialog.show();
		listCarGoodsBean.setSelectedSpec(newListCarGoodsBean.getSelectedSpec());
		listCarGoodsBean.setQuantity(newListCarGoodsBean.getQuantity());
		adapter.notifyDataSetChanged();
		refreshDialog.dismiss();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MyLogger.i("");
		CarItem carItem=listCarItem.get(position);
		if (carItem.type == CarItem.SECTION) {
			ListCarStoreBean listCarStoreBean=carItem.listCarStoreBean;
			if(listCarStoreBean!=null){
				ShopsActivity.startThisActivity(listCarStoreBean.getShopId(), this);
			}
		}else{
			ListCarGoodsBean listCarGoodsBean=carItem.listCarGoodsBean;
			if(listCarGoodsBean!=null){
				CommodityContentActivity.startThisActivity(listCarGoodsBean.getProductId(), this);
			}
		}
	}
}
