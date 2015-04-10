package com.shangxian.art;

import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.shangxian.art.adapter.ListConfirmOrderAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.bean.ListCarStoreBean;
import com.shangxian.art.view.TopView;
/**
 * 确认订单
 * @author Administrator
 *
 */
public class ConfirmOrderActivity extends BaseActivity implements OnClickListener{
	private static TopView topView;
	private ListView listview;
	private TextView tv_car_allprice_value;
	private View headView = null;
	
	private ListConfirmOrderAdapter listadapter;
	private List<ListCarStoreBean> listStoreBean;
	private HashMap<String, List<ListCarGoodsBean>> hashmapGoodsBeans;
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
			myToast("去结算");
			break;

		default:
			break;
		}
	}

	private void dosettlement() {
	}
}
