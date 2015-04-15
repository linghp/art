package com.shangxian.art;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shangxian.art.adapter.ListConfirmOrderAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CommitOrder;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.bean.ListCarStoreBean;
import com.shangxian.art.bean.Mapbean;
import com.shangxian.art.bean.OrderItem;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.HttpClients;
import com.shangxian.art.net.HttpClients.HttpCilentListener;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;
/**
 * 确认订单
 * @author Administrator
 *
 */
public class ConfirmOrderActivity extends BaseActivity implements OnClickListener,HttpCilentListener{
	private TopView topView;
	private ListView listview;
	private TextView tv_car_allprice_value;
	private View headView = null;
	
	private ListConfirmOrderAdapter listadapter;
	private List<ListCarStoreBean> listStoreBean;
	private HashMap<String, List<ListCarGoodsBean>> hashmapGoodsBeans;
	//private Map
	private float totalprice;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order);
		initViews();
		initdata();
		listener();
	}

	private void listener() {
		findViewById(R.id.tv_settlement).setOnClickListener(this);
	}

	private void initdata() {
		totalprice=getIntent().getFloatExtra("totalprice",0f);
		tv_car_allprice_value.setText("￥" + totalprice + "元");
		listStoreBean=(List<ListCarStoreBean>) getIntent().getSerializableExtra("listCarItem_stores");
		hashmapGoodsBeans=(HashMap<String, List<ListCarGoodsBean>>) getIntent().getSerializableExtra("mapCarItem_goods");
		if(listStoreBean!=null&&hashmapGoodsBeans!=null){
		listadapter=new ListConfirmOrderAdapter(this, listStoreBean,hashmapGoodsBeans);
		}
		listview.setAdapter(listadapter);
	}

	private void initViews() {
		//改变topbar
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_confirm_order));
		
		listview=(ListView) findViewById(R.id.listview);
		tv_car_allprice_value=(TextView) findViewById(R.id.tv_car_allprice_value);
		
		initHeadView();
		listview.addHeaderView(headView);
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
			//myToast("去结算");
			break;

		default:
			break;
		}
	}

	private void dosettlement() {
		CommitOrder commitOrder=new CommitOrder();
		commitOrder.setAddressId("1");
		List<OrderItem> orderItems=new ArrayList<OrderItem>();
		for (List<ListCarGoodsBean> listCarGoodsBeans : hashmapGoodsBeans.values()) {
			for (ListCarGoodsBean listCarGoodsBean : listCarGoodsBeans) {
				OrderItem orderItem=new OrderItem(listCarGoodsBean.getProductId(), listCarGoodsBean.getSpecs(), listCarGoodsBean.getQuantity(), "");
				orderItems.add(orderItem);
			}
		}
		commitOrder.setOrderItems(orderItems);
		Gson gson=new Gson();
		String json=gson.toJson(commitOrder);
		MyLogger.i(json);
		HttpClients.postDo(Constant.BASEURL+Constant.CONTENT+Constant.ORDER, json, this);
	}
	
	@Override
	public void onResponse(String content) {
		MyLogger.i(content);
		if (!TextUtils.isEmpty(content)) {
			try {
				JSONObject jsonObject = new JSONObject(content);
				String result_code = jsonObject
						.getString("result_code");
				if (result_code.equals("200")) {
					if(jsonObject
					.getString("reason").equals("success")){
						String result=jsonObject.getString("result");
						Gson gson=new Gson();
						Mapbean mapbeans=gson.fromJson("{\"result\":"+result+"}", Mapbean.class);
						List<String> ordernumbers=new ArrayList<String>(mapbeans.result.keySet());
						PayActivity.startThisActivity(ordernumbers, totalprice, this);
					}
					//myToast(jsonObject.getString("result"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
		}
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
			}
		}
	}
}
