package com.shangxian.art;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.shangxian.art.adapter.ListConfirmOrderAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CarItem;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.bean.ListCarStoreBean;
import com.shangxian.art.view.TagViewPager;
import com.shangxian.art.view.TopView;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 确认订单
 * @author Administrator
 *
 */
public class ConfirmOrderActivity extends BaseActivity {
	private static TopView topView;
	private ListView listview;
	private View headView = null;
	
	private ListConfirmOrderAdapter listadapter;
	private List<ListCarStoreBean> listStoreBean;
	private HashMap<String, List<ListCarGoodsBean>> hashmapGoodsBeans;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order);
		initViews();
		initdata();
	}

	private void initdata() {
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
		
		initHeadView();
		listview.addHeaderView(headView);
	}
	
	private void initHeadView() {
		headView = LayoutInflater.from(this).inflate(
				R.layout.confirmorder_header, null);
	}
}
