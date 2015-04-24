package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.shangxian.art.adapter.DeliveryAddressAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.DeliveryAddressModel;
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
		list = new ArrayList<DeliveryAddressModel>();
		for (int i = 0; i < 5; i++) {
			DeliveryAddressModel model = new DeliveryAddressModel();
			model.setName("小明"+i);
			model.setNum("1869663681"+i);
			model.setAddress("重庆市沙坪坝区沙中路23号通江大道1栋3单元8—"+(i+1));
			list.add(model);
		}
		adapter = new DeliveryAddressAdapter(this, R.layout.item_deliveryaddress, list);
		listview.setAdapter(adapter);
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
//				CommonUtil.gotoActivity(DeliveryAddressActivity.this, .class, false);
			}
		});
		
	}
}
