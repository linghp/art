package com.shangxian.art;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.util.AbDialogUtil;
import com.google.gson.Gson;
import com.shangxian.art.adapter.ListConfirmOrderAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CommitOrder;
import com.shangxian.art.bean.DeliveryAddressModel;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.bean.ListCarStoreBean;
import com.shangxian.art.bean.Mapbean;
import com.shangxian.art.bean.NowBuySettlementBean;
import com.shangxian.art.bean.NowBuySettlementBean.Product;
import com.shangxian.art.bean.OrderItem;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.AccountSecurityServer;
import com.shangxian.art.net.AccountSecurityServer.OnHttpAddressListener;
import com.shangxian.art.net.HttpClients;
import com.shangxian.art.net.HttpClients.HttpCilentListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 确认订单
 * 
 * @author Administrator
 *
 */
public class ConfirmOrderActivity extends BaseActivity implements
		OnClickListener, HttpCilentListener {
	private TopView topView;
	private ListView listview;
	private TextView tv_car_allprice_value;
	private View headView;
	private View tv_noaddress;
	private View rl_address;

	private String deliveryAddressId;

	private ListConfirmOrderAdapter listadapter;
	private List<ListCarStoreBean> listStoreBean;
	// private HashMap<String, List<ListCarGoodsBean>> hashmapGoodsBeans;
	// private Map
	private float totalprice;
	private boolean isNowBuy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order);
		initViews();
		initdata();
		listener();
	}

	public static void startThisActivity(Context context,
			List<ListCarStoreBean> listStoreBean, float priceAll,
			boolean isNowBuy) {
		Intent intent = new Intent(context, ConfirmOrderActivity.class);
		intent.putExtra("totalprice", CommonUtil.priceConversion(priceAll));
		intent.putExtra("isNowBuy", isNowBuy);
		intent.putExtra("listCarItem_stores", (Serializable) listStoreBean);
		((Activity) context).startActivityForResult(intent, 1);
	}

	private void listener() {
		findViewById(R.id.tv_settlement).setOnClickListener(this);
		listview.setOnScrollListener(new OnScrollListener() {// 为了edittext能够焦点

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
					view.requestFocus();
				} else {
					view.clearFocus();
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initdata() {
		isNowBuy = getIntent().getBooleanExtra("isNowBuy", false);
		totalprice = getIntent().getFloatExtra("totalprice", 0f);
		tv_car_allprice_value.setText("￥" + totalprice + "元");
		listStoreBean = (List<ListCarStoreBean>) getIntent()
				.getSerializableExtra("listCarItem_stores");
		// hashmapGoodsBeans=(HashMap<String, List<ListCarGoodsBean>>)
		// getIntent().getSerializableExtra("mapCarItem_goods");
		// MyLogger.i(hashmapGoodsBeans.toString());
		MyLogger.i(listStoreBean.toString());

		if (listStoreBean != null) {
			listadapter = new ListConfirmOrderAdapter(this, listStoreBean);
		}
		listview.setAdapter(listadapter);
		
		AccountSecurityServer.toDeliveiveAddress(new OnHttpAddressListener() {
			
			@Override
			public void onHttpAddress(List<DeliveryAddressModel> list) {
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).get_default() == true) {
							tv_noaddress.setVisibility(View.GONE);
							deliveryAddressId = list.get(i).getId();
							((TextView) findViewById(R.id.tv_receiver)).setText(String
									.format(getString(R.string.text_receiver),
											list.get(i).getReceiverName()));
							((TextView) findViewById(R.id.tv_address)).setText(String
									.format(getString(R.string.text_receiver_address),
											list.get(i).getDeliveryAddress()));
							((TextView) findViewById(R.id.tv_phone))
									.setText(list.get(i).getReceiverTel());

						}
					}
				}else {
					tv_noaddress.setVisibility(View.VISIBLE);
				}
				
				
			}
		});
	}

	private void initViews() {
		// 改变topbar
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_confirm_order));

		listview = (ListView) findViewById(R.id.listview);
		listview.setItemsCanFocus(true);
		tv_car_allprice_value = (TextView) findViewById(R.id.tv_car_allprice_value);

		initHeadView();
		listview.addHeaderView(headView);
		tv_noaddress = findViewById(R.id.tv_noaddress);
		rl_address = findViewById(R.id.rl_address);
		rl_address.setOnClickListener(this);
	}

	private void initHeadView() {
		headView = LayoutInflater.from(this).inflate(
				R.layout.confirmorder_header, null);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_settlement:
			dosettlement();
			// myToast("去结算");
			break;
		case R.id.rl_address:
			// myToast("待完善中...");
			DeliveryAddressActivity.startThisActivity(this);
			break;

		default:
			break;
		}
	}

	private void dosettlement() {
		if (!TextUtils.isEmpty(deliveryAddressId)) {
			AbDialogUtil.showLoadDialog(this, R.drawable.progress_circular,
					"请稍等...");
			if (isNowBuy) {
				//myToast("isnowbuy");
				NowBuySettlementBean nowBuySettlementBean=new NowBuySettlementBean();
				nowBuySettlementBean.setAddressId(deliveryAddressId);
				for (ListCarStoreBean listCarStoreBean : listStoreBean) {
					nowBuySettlementBean.setGuestMessage(listCarStoreBean.getRecommand());
					Product product=new Product();
					for (ListCarGoodsBean listCarGoodsBean2 : listCarStoreBean
							.getItemDtos()) {
						product.setBuyCount(listCarGoodsBean2.getQuantity());
						product.setProductId(listCarGoodsBean2.getProductId());
						product.setSpecs(listCarGoodsBean2.getSelectedSpec());
					}
					nowBuySettlementBean.setProduct(product);
				}
				Gson gson = new Gson();
				String json = gson.toJson(nowBuySettlementBean);
				MyLogger.i(json);
				HttpClients.postDo(Constant.BASEURL + Constant.CONTENT
						+ Constant.NOWBUYORDER, json, this);
			} else {
				CommitOrder commitOrder = new CommitOrder();
				commitOrder.setAddressId(deliveryAddressId);
				List<OrderItem> orderItems = new ArrayList<OrderItem>();
				// for (List<ListCarGoodsBean> listCarGoodsBeans :
				// hashmapGoodsBeans.values()) {
				// for (ListCarGoodsBean listCarGoodsBean : listCarGoodsBeans) {
				// String selectedSpec="";
				// Map<String, String>
				// selectedSpecMap=listCarGoodsBean.getSelectedSpec();
				// for (Entry<String,String> listCarGoodsBean2 :
				// selectedSpecMap.entrySet()) {
				// selectedSpec=listCarGoodsBean2.getValue();
				// }
				// OrderItem orderItem=new
				// OrderItem(listCarGoodsBean.getShopId(), selectedSpec,
				// listCarGoodsBean.getQuantity(), "");
				// orderItems.add(orderItem);
				// }
				// }
				// for (Entry<String, List<ListCarGoodsBean>> entry :
				// hashmapGoodsBeans.entrySet()) {
				// String shopid=entry.getKey();
				// List<ListCarGoodsBean> listCarGoodsBeans=entry.getValue();
				// List<String> cartOrderItemId=new ArrayList<String>();
				// for (ListCarGoodsBean listCarGoodsBean2 : listCarGoodsBeans)
				// {
				// cartOrderItemId.add(listCarGoodsBean2.getCartItemId());
				// }
				// OrderItem orderItem=new OrderItem(shopid,cartOrderItemId,
				// "");
				// orderItems.add(orderItem);
				// }
				for (ListCarStoreBean listCarStoreBean : listStoreBean) {
					List<String> cartOrderItemId = new ArrayList<String>();
					for (ListCarGoodsBean listCarGoodsBean2 : listCarStoreBean
							.getItemDtos()) {
						cartOrderItemId.add(listCarGoodsBean2.getCartItemId());
					}
					OrderItem orderItem = new OrderItem(
							listCarStoreBean.getShopId(), cartOrderItemId,
							listCarStoreBean.getRecommand());
					MyLogger.i(orderItem.toString());
					orderItems.add(orderItem);
				}
				commitOrder.setOrderItems(orderItems);
				Gson gson = new Gson();
				String json = gson.toJson(commitOrder);
				MyLogger.i(json);
				HttpClients.postDo(Constant.BASEURL + Constant.CONTENT
						+ Constant.ORDER, json, this);
			}
		} else {
			myToast("请添加收货地址");
		}
	}

	@Override
	public void onResponse(String content) {
		MyLogger.i(content);
		try {
			JSONObject jsonObject = new JSONObject(content);
			String result_code = jsonObject.getString("result_code");
			if (result_code.equals("200")) {
				if (jsonObject.getString("reason").equals("success")) {
					String result = jsonObject.getString("result");
					Gson gson = new Gson();
					Mapbean mapbeans = gson.fromJson("{\"result\":" + result
							+ "}", Mapbean.class);
					List<String> ordernumbers = new ArrayList<String>(
							mapbeans.result.keySet());
					PayActivity.startThisActivity(ordernumbers, totalprice,listStoreBean.get(0).getItemDtos().get(0).getName(),
							this);
				}
				// myToast(jsonObject.getString("result"));
				setResult(RESULT_OK);
				finish();
			} else {
				myToast("结算失败");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			myToast("结算失败");
		} finally {
			AbDialogUtil.removeDialog(ConfirmOrderActivity.this);
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent data) {
		super.onActivityResult(arg0, arg1, data);
		if (arg1 == RESULT_OK) {
			if (arg0 == 1000) {
				boolean isPay = data.getBooleanExtra("pay_order_res", false);
				if (isPay) {
					finish();
				}
			} else if (arg0 == 1001) {
				DeliveryAddressModel deliveryAddressModel = (DeliveryAddressModel) data
						.getSerializableExtra("DeliveryAddressModel");
				if (deliveryAddressModel != null) {
					MyLogger.i(deliveryAddressModel.toString());
					tv_noaddress.setVisibility(View.GONE);
					deliveryAddressId = deliveryAddressModel.getId();
					((TextView) findViewById(R.id.tv_receiver)).setText(String
							.format(getString(R.string.text_receiver),
									deliveryAddressModel.getReceiverName()));
					((TextView) findViewById(R.id.tv_address)).setText(String
							.format(getString(R.string.text_receiver_address),
									deliveryAddressModel.getDeliveryAddress()));
					((TextView) findViewById(R.id.tv_phone))
							.setText(deliveryAddressModel.getReceiverTel());
				}
			}
		}
	}
}
