package com.shangxian.art;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.task.AbTask;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListListener;
import com.ab.util.AbLogUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.shangxian.art.adapter.ListCarAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CarItem;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.bean.ListCarStoreBean;
import com.shangxian.art.view.TopView;
/**
 * 购物车
 * @author Administrator
 *
 */
public class ShoppingcartActivity extends BaseActivity implements
OnHeaderRefreshListener,OnClickListener{
	private ListView listcar;
	public  static CheckBox selecteall;
	//private static ListCarAdapter adapter;
	//private CustomProgressDialog dialog;
	static TextView allprice;
	private Button btn_settlement;
	private AbPullToRefreshView mAbPullToRefreshView = null;
	
	private List<CarItem> listCarItem = new ArrayList<CarItem>();
	
	private static ListCarAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoppingcart);
		initViews();
		initdata();
	}

	private void initdata() {
		ListCarStoreBean store=new ListCarStoreBean("001", "商店名称1");
		CarItem section = new CarItem(CarItem.SECTION, store, null, store.getStoreid());
		listCarItem.add(section);
		for (int i = 0; i < 2; i++) {
			ListCarGoodsBean listCarGoodsBean=new ListCarGoodsBean("001"+(i+1), "商品"+(i+1), store.getStoreid(), 1,(float)(Math.round(145.10f*100))/100);
			CarItem item = new CarItem(CarItem.ITEM, null, listCarGoodsBean, store.getStoreid());
			listCarItem.add(item);
		}
		
		ListCarStoreBean store1=new ListCarStoreBean("002", "商店名称2");
		CarItem section1 = new CarItem(CarItem.SECTION, store1, null, store1.getStoreid());
		listCarItem.add(section1);
		for (int i = 0; i < 2; i++) {
			ListCarGoodsBean listCarGoodsBean=new ListCarGoodsBean("002"+(i+1), "商品"+(i+1), store1.getStoreid(), 1,(float)(Math.round(23.50f*100))/100);
			CarItem item = new CarItem(CarItem.ITEM, null, listCarGoodsBean, store1.getStoreid());
			listCarItem.add(item);
		}
		
		ListCarStoreBean store2=new ListCarStoreBean("003", "商店名称3");
		CarItem section2 = new CarItem(CarItem.SECTION, store2, null, store2.getStoreid());
		listCarItem.add(section2);
		for (int i = 0; i < 3; i++) {
			ListCarGoodsBean listCarGoodsBean=new ListCarGoodsBean("003"+(i+1), "商品"+(i+1), store2.getStoreid(),1,(float)(Math.round(45.32f*100))/100);
			CarItem item = new CarItem(CarItem.ITEM, null, listCarGoodsBean, store1.getStoreid());
			listCarItem.add(item);
		}
		
		adapter = new ListCarAdapter(this, listCarItem);
		listcar.setAdapter(adapter);
		// 全选点击事件
		selecteall.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (buttonView.isPressed()) {
					adapter.selectAll(selecteall.isChecked());
					accountCar();
				}
			}
		});
	}

	private void initViews() {
//		Intent intent = new Intent();
		
//		topView=(TopView) findViewById(R.id.top_title);
//		topView.setActivity(this);
//		topView.setBack(R.drawable.back);//返回
//		topView.hideRightBtn();
//		topView.hideCenterSearch();
//		topView.setCenterListener(null);
//		topView.setTitle("购物车");
//		topView.showTitle();
		
		
		mAbPullToRefreshView = (AbPullToRefreshView)findViewById(R.id.mPullRefreshView);
		listcar=(ListView) findViewById(R.id.listcar);
		selecteall=(CheckBox) findViewById(R.id.selectall);
		allprice=(TextView) findViewById(R.id.tv_car_allprice_value);
		btn_settlement=(Button) findViewById(R.id.btn_settlement);
		
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
		float price = 0;
		// 遍历选中的商品，算出总价
		Iterator iter1 = adapter.getGoodsCheced().entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry entry = (Map.Entry) iter1.next();
			if ((Boolean) entry.getValue() == true) {
				for (int i = 0; i < adapter.listdata.size(); i++) {
					CarItem item = adapter.listdata.get(i);
					if (item.type == CarItem.ITEM) {
						if (item.listCarGoodsBean.goodsId.equals(entry.getKey())) {
							float goodsprice = item.listCarGoodsBean.price;
							price += goodsprice;
						}
					}
				}
			}
		}
		allprice.setText("￥" + price + "元");
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
		// TODO Auto-generated method stub
		super.onResume();
		topView = MainActivity.getTopView();
		topView.setActivity(this);
		topView.hideLeftBtn();
		topView.hideRightBtn();
		topView.hideCenterSearch();
		topView.setCenterListener(null);
		topView.setTitle("购物车");
		topView.showTitle();
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
//					newList = new ArrayList<Map<String, Object>>();
//					Map<String, Object> map = null;
//
//					for (int i = 0; i < pageSize; i++) {
//						map = new HashMap<String, Object>();
//						map.put("itemsIcon", mPhotoList.get(i));
//						map.put("itemsTitle", "item" + (i + 1));
//						map.put("itemsText", "item..." + (i + 1));
//						newList.add(map);
//
//					}
				} catch (Exception e) {
				}
				return newList;
			}

			@Override
			public void update(List<?> paramList) {

				// 通知Dialog
//				mDialogFragment.loadFinish();
//				AbLogUtil.d(NearlyActivity.this, "返回", true);
//				List<Map<String, Object>> newList = (List<Map<String, Object>>) paramList;
//				list.clear();
//				if (newList != null && newList.size() > 0) {
//					list.addAll(newList);
//					myListViewAdapter.notifyDataSetChanged();
//					newList.clear();
//				}
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
//				Map<String, Boolean> mapSelect = new HashMap<String, Boolean>();
//				List<ListCarGoodsBean> listGoods = new ArrayList<ListCarGoodsBean>();
//				Iterator iter1 = adapter.getGoodsCheced().entrySet().iterator();
//				while (iter1.hasNext()) {
//					Map.Entry entry1 = (Map.Entry) iter1.next();
//					if ((Boolean) entry1.getValue() == true) {
//						for (CarItem caritem : listCarItem) {
//							if (caritem != null && caritem.listCarGoodsBean != null) {
//								if (caritem.listCarGoodsBean.goodsId.equals(entry1.getKey())){
//									listGoods.add(caritem.listCarGoodsBean);
//									mapSelect.put(caritem.listCarGoodsBean.storeId, true);
//								}
//							}
//						}
//					}
//				}
		        
//				if (mapSelect.size() > 1) {
//		              CustomerToast.makeText(this, "暂不支持多家店铺同时下单,请重新选择");
//				} else {
//					MerCartDTO car = new MerCartDTO();
//					car.setFstoreId(listGoods.get(0).storeId);
//					car.setStoreName(listGoods.get(0).storeName);
				List<CarItem> listCarItem_select=new ArrayList<CarItem>();
				Map<String, Boolean> goodsCheced=adapter.getGoodsCheced();
				List<ListCarStoreBean> listStoreBean=new ArrayList<ListCarStoreBean>();
				//提取店铺list
				LinkedHashMap<String, ListCarStoreBean> linkedHashMap=new LinkedHashMap<String, ListCarStoreBean>();
				ListCarStoreBean listCarStoreBean=null;
				for (CarItem carItem : listCarItem) {
					if(carItem.getType()==CarItem.SECTION){
						listCarStoreBean=carItem.getListCarStoreBean();
					}else{
						if(goodsCheced.get(carItem.getListCarGoodsBean().getGoodsId())){
							listCarItem_select.add(carItem);
							linkedHashMap.put(carItem.getListCarGoodsBean().getStoreId(), listCarStoreBean);
						}
					}
				}
				for (ListCarStoreBean listCarStoreBean2 : linkedHashMap.values()) {
					listStoreBean.add(listCarStoreBean2);
				}
		
				//存放每个店铺下的订单
				HashMap<String, List<ListCarGoodsBean>> hashmapGoodsBeans=new HashMap<String, List<ListCarGoodsBean>>();
				for(ListCarStoreBean listCarStoreBean2:linkedHashMap.values()){
					List<ListCarGoodsBean> listGoodsBeans=new ArrayList<ListCarGoodsBean>();
					String storeid=listCarStoreBean2.getStoreid();
					for (CarItem carItem : listCarItem_select) {
						if(carItem.getType()==CarItem.ITEM){
							if(carItem.getListCarGoodsBean().getStoreId().equals(storeid)){
								listGoodsBeans.add(carItem.getListCarGoodsBean());
							}
						}
					}
					hashmapGoodsBeans.put(storeid, listGoodsBeans);
				}
				
//				for (String storeid : hashmapGoodsBeans.keySet()) {
//					for (CarItem carItem : listCarItem) {
//						if(carItem.getType()==CarItem.ITEM){
//							if(storeid.equals(carItem.getListCarStoreBean().getStoreid()))
//								listStoreBean.add(carItem.getListCarStoreBean());
//						}
//					}
//				}
				
					Intent intent = new Intent(this, ConfirmOrderActivity.class);
					intent.putExtra("mapCarItem_goods",(Serializable)hashmapGoodsBeans);
					intent.putExtra("listCarItem_stores",(Serializable)listStoreBean);
//					Bundle bundle = new Bundle();
//					bundle.putSerializable("car", car);
//					intent.putExtras(bundle);
//					intent.putExtra("from", GoodsOrder.FROMCAR);
					startActivity(intent);
//				}

			} else {
				myToast("请选择要结算的商品");
			}
		}
	}


}
