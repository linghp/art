package com.shangxian.art;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.task.AbTask;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListListener;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbLogUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.google.gson.Gson;
import com.shangxian.art.adapter.ListCarAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CarItem;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.bean.ListCarStoreBean;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.MyOrderItem_all;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.HttpClients;
import com.shangxian.art.net.HttpClients.HttpCilentListener;
import com.shangxian.art.utils.MyLogger;

/**
 * 购物车
 * 
 * @author Administrator
 *
 */
public class ShoppingcartActivity extends BaseActivity implements
		OnHeaderRefreshListener, OnClickListener, HttpCilentListener {
	private ListView listcar;
	public static CheckBox selecteall;
	// private static ListCarAdapter adapter;
	// private CustomProgressDialog dialog;
	static TextView allprice;
	private static float price;// 总价
	private Button btn_settlement;
	private AbPullToRefreshView mAbPullToRefreshView;
	private View ll_nonetwork;

	private AbHttpUtil httpUtil;
	private List<CarItem> listCarItem = new ArrayList<CarItem>();
	private List<ListCarStoreBean> listStore = new ArrayList<ListCarStoreBean>();
	private static ListCarAdapter adapter;
	private boolean isFromConfirmOrderAct;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoppingcart);
		initViews();
	}

	private void initdata() {
		if (isLogin()) {
			AbDialogUtil.showLoadDialog(this, R.drawable.progress_circular,
					"数据加载中...");
			HttpClients.getDo(Constant.BASEURL + Constant.CONTENT
					+ Constant.CART, this);
		}
		adapter = new ListCarAdapter(ShoppingcartActivity.this, listCarItem);
		selecteall.setChecked(false);
		accountCar();
		// 全选点击事件
		selecteall.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (buttonView.isPressed()&&adapter != null) {
					//myToast("click");
					adapter.selectAll(selecteall.isChecked());
					accountCar();
				}
			}
		});
	}

	@Override
	public void onResponse(String content) {
		MyLogger.i(content);
		AbDialogUtil.removeDialog(ShoppingcartActivity.this);
		if (content == null) {
			ll_nonetwork.setVisibility(View.VISIBLE);
		}
		Gson gson = new Gson();
		try {
			JSONObject jsonObject = new JSONObject(content);
			String result_code = jsonObject.getString("result_code");
			if (result_code.equals("200")) {
				ll_nonetwork.setVisibility(View.GONE);
				JSONArray resultObjectArray = jsonObject.getJSONArray("result");
				int length = resultObjectArray.length();
				listStore.clear();
				for (int i = 0; i < length; i++) {
					JSONObject jo = resultObjectArray.getJSONObject(i);
					listStore.add(gson.fromJson(jo.toString(),
							ListCarStoreBean.class));
				}
				assembleData();
				adapter.initState();
				listcar.setAdapter(adapter);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AbDialogUtil.removeDialog(ShoppingcartActivity.this);
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
		// Intent intent = new Intent();

		// topView=(TopView) findViewById(R.id.top_title);
		// topView.setActivity(this);
		// topView.setBack(R.drawable.back);//返回
		// topView.hideRightBtn();
		// topView.hideCenterSearch();
		// topView.setCenterListener(null);
		// topView.setTitle("购物车");
		// topView.showTitle();

		mAbPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);
		ll_nonetwork = findViewById(R.id.ll_nonetwork);
		listcar = (ListView) findViewById(R.id.listcar);
		selecteall = (CheckBox) findViewById(R.id.selectall);
		allprice = (TextView) findViewById(R.id.tv_car_allprice_value);
		btn_settlement = (Button) findViewById(R.id.btn_settlement);

		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		btn_settlement.setOnClickListener(this);

		mAbPullToRefreshView.setLoadMoreEnable(false);
		// 设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
	}

	// 结算购物车的总价格
	public static void accountCar() {
		// 遍历选中的商品，算出总价
		price = 0;
		Iterator iter1 = adapter.getGoodsCheced().entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry entry = (Map.Entry) iter1.next();
			if ((Boolean) entry.getValue() == true) {
				for (int i = 0; i < adapter.listdata.size(); i++) {
					CarItem item = adapter.listdata.get(i);
					if (item.type == CarItem.ITEM) {
						if (item.listCarGoodsBean.getProductId().equals(
								entry.getKey())) {
							float goodsprice = item.listCarGoodsBean
									.getPromotionPrice();
							price += goodsprice*item.listCarGoodsBean.getQuantity();
						}
					}
				}
			}
		}
		allprice.setText("￥ " + price);
	}

	// 设置全选是否选中
	public static void setSelecteAll() {
		int flag = 0;
		Iterator iter = adapter.getStoreCheced().entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			if ((Boolean) entry.getValue() == true) {
				flag++;
			}
		}
		if (flag == adapter.getStoreCheced().size()) {
			selecteall.setChecked(true);
		} else {
			selecteall.setChecked(false);
		}
		accountCar();
	}

	@Override
	protected void onResume() {
		MyLogger.i("");
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
			topView.setTitle("购物车");
			topView.showTitle();

			if (!isLogin() && listCarItem.size() > 0) {// 如果是没有登陆且购物车有数据，清空
				listCarItem.clear();
				selecteall.setChecked(false);
				adapter.notifyDataSetChanged();
			} else {
				initdata();
			}
			MyLogger.i("");
			ll_nonetwork.setVisibility(View.GONE);
		}

	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		refreshTask();
	}

	public void refreshTask() {
		AbLogUtil.prepareLog(this);
		AbTask mAbTask = new AbTask();
		final AbTaskItem item = new AbTaskItem();
		item.setListener(new AbTaskListListener() {
			@Override
			public List<?> getList() {
				List<Map<String, Object>> newList = null;
				try {
					Thread.sleep(1000);
					// newList = new ArrayList<Map<String, Object>>();
					// Map<String, Object> map = null;
					//
					// for (int i = 0; i < pageSize; i++) {
					// map = new HashMap<String, Object>();
					// map.put("itemsIcon", mPhotoList.get(i));
					// map.put("itemsTitle", "item" + (i + 1));
					// map.put("itemsText", "item..." + (i + 1));
					// newList.add(map);
					//
					// }
				} catch (Exception e) {
				}
				return newList;
			}

			@Override
			public void update(List<?> paramList) {

				// 通知Dialog
				// mDialogFragment.loadFinish();
				// AbLogUtil.d(NearlyActivity.this, "返回", true);
				// List<Map<String, Object>> newList = (List<Map<String,
				// Object>>) paramList;
				// list.clear();
				// if (newList != null && newList.size() > 0) {
				// list.addAll(newList);
				// myListViewAdapter.notifyDataSetChanged();
				// newList.clear();
				// }
				mAbPullToRefreshView.onHeaderRefreshFinish();
			}

		});

		mAbTask.execute(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_settlement:
			dosettlement();
			break;
		case R.id.iv_reload:
			initdata();
			break;
		case R.id.btn_right:
			myToast("delete");
			break;
		default:
			break;
		}
	}

	private void dosettlement() {
		int flag = 0;
		if (adapter != null) {
			Iterator iter = adapter.getGoodsCheced().entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				if ((Boolean) entry.getValue() == true) {
					flag++;
				}
			}
			if (flag > 0) {
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
				List<CarItem> listCarItem_select = new ArrayList<CarItem>();
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
								.getProductId())) {
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
				HashMap<String, List<ListCarGoodsBean>> hashmapGoodsBeans = new HashMap<String, List<ListCarGoodsBean>>();
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
					hashmapGoodsBeans.put(storeid, listGoodsBeans);
				}

				// for (String storeid : hashmapGoodsBeans.keySet()) {
				// for (CarItem carItem : listCarItem) {
				// if(carItem.getType()==CarItem.ITEM){
				// if(storeid.equals(carItem.getListCarStoreBean().getStoreid()))
				// listStoreBean.add(carItem.getListCarStoreBean());
				// }
				// }
				// }

				Intent intent = new Intent(this, ConfirmOrderActivity.class);
				intent.putExtra("totalprice", price);
				intent.putExtra("mapCarItem_goods",
						(Serializable) hashmapGoodsBeans);
				intent.putExtra("listCarItem_stores",
						(Serializable) listStoreBean);
				// Bundle bundle = new Bundle();
				// bundle.putSerializable("car", car);
				// intent.putExtras(bundle);
				// intent.putExtra("from", GoodsOrder.FROMCAR);
				startActivityForResult(intent, 1);
				// }

			} else {
				myToast("请选择要结算的商品");
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			MyLogger.i("");
			isFromConfirmOrderAct = true;
		}
	}
}
