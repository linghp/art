package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.shangxian.art.adapter.DeliveryAddressAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.DeliveryAddressModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.HttpClients;
import com.shangxian.art.net.HttpClients.HttpCilentListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

/**
 * 收货地址管理
 * @author Administrator
 *
 */
public class DeliveryAddressActivity extends BaseActivity{

	private ListView listview;
	private List<DeliveryAddressModel>list;
	private DeliveryAddressAdapter adapter;
	
	Boolean isfromConfirmOrder = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
		initData();
		initListener();
	}
	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.setRightBtnDrawable(R.drawable.addto);
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_deliveryaddress));
		listview = (ListView) findViewById(R.id.search_list);

	}
	private void initData() {
		Intent intent = getIntent();
		isfromConfirmOrder=intent.getBooleanExtra("isfromConfirmOrder", false);
	}
	public static void startThisActivity(Context context) {
		Intent intent = new Intent(context, DeliveryAddressActivity.class);
		intent.putExtra("isfromConfirmOrder", true);
		((Activity)context).startActivityForResult(intent, 1001);
	}

	public static void startThisActivity_url(String url, Context context) {
		Intent intent = new Intent(context, AddDeliveryAddressActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}
	private void refreshTask(String url) {
		HttpClients.getDo(url, new HttpCilentListener() {


			@Override
			public void onResponse(String res) {
				list.clear();
				if (!TextUtils.isEmpty(res)) {
					Gson gson = new Gson();
					try {
						JSONObject jsonObject = new JSONObject(res);
						String result_code = jsonObject
								.getString("result_code");
						if (result_code.equals("200")) {
							JSONArray str=jsonObject.getJSONArray("result");
							for (int i = 0; i < str.length(); i++) {
								list.add(gson.fromJson(str.getString(i), DeliveryAddressModel.class));		
							}
							adapter = new DeliveryAddressAdapter(DeliveryAddressActivity.this, R.layout.item_deliveryaddress, list);
							listview.setAdapter(adapter);
							adapter.notifyDataSetChanged();

						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

	}

	@Override
	protected void onResume() {
		list = new ArrayList<DeliveryAddressModel>();
		/*for (int i = 0; i < 5; i++) {
			DeliveryAddressModel model = new DeliveryAddressModel();
			model.setName("小明"+i);
			model.setNum("1869663681"+i);
			model.setAddress("重庆市沙坪坝区沙中路23号通江大道1栋3单元8—"+(i+1));
			list.add(model);
		}*/
		String url = "";
		url = Constant.BASEURL + Constant.CONTENT + "/receiving";
		refreshTask(url);

		super.onResume();
	}
	private void initListener() {
		topView.setRightBtnListener(new OnClickListener() {
			//title添加收货地址
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonUtil.gotoActivity(DeliveryAddressActivity.this, AddDeliveryAddressActivity.class, false);
			}
		});

		listview.setOnItemClickListener(new OnItemClickListener() {
			//列表点击
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (isfromConfirmOrder) {
					//确认订单
					Intent intent = new Intent();
					intent.putExtra("DeliveryAddressModel", list.get(position));
				    DeliveryAddressActivity.this.setResult(RESULT_OK, intent);
				    DeliveryAddressActivity.this.finish();
				}else {
					//修改地址 
					Bundle bundle = new Bundle();
//					bundle.putBoolean("isRevise", true);
//					bundle.putInt("id", list.get(position).getId());
					bundle.putSerializable("DeliveryAddressModel", list.get(position));
					CommonUtil.gotoActivityWithData(DeliveryAddressActivity.this, AddDeliveryAddressActivity.class, bundle,false);
				}
			}
		});

	}
}
