package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.shangxian.art.adapter.MyMessageAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.MyMessageModel;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

/**
 * 我的消息 
 * @author Administrator
 *
 */
public class MyMessageActivity extends BaseActivity{

	private ListView listview;
	private List<MyMessageModel>list;
	private MyMessageAdapter adapter;
	
	private View loading_big,ll_refresh_empty;
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
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_my_message));
		listview = (ListView) findViewById(R.id.search_list);
		loading_big = findViewById(R.id.loading_big);//加载
		ll_refresh_empty = findViewById(R.id.ll_refresh_empty);//无数据
	}
	private void initData() {
		list = new ArrayList<MyMessageModel>();
		for (int i = 0; i < 10; i++) {
			MyMessageModel model = new MyMessageModel();
			model.setTitle("物流通知"+i);
			model.setTime("12:"+(10+i));
			model.setContent("尊敬的客户，您的订单45645468794已完成，感谢您使用京东快递");
			list.add(model);
		}
		adapter = new MyMessageAdapter(this, R.layout.item_mymessage, list);
		listview.setAdapter(adapter);
		ll_refresh_empty.setVisibility(View.VISIBLE);
		loading_big.setVisibility(View.GONE);
	}
	private void initListener() {
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CommonUtil.gotoActivity(MyMessageActivity.this, LogisticsNotifyActivity.class, false);
			}
		});
		
	}
}
