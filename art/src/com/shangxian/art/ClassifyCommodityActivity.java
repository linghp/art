package com.shangxian.art;
/**
 * 分类--->  商品展示
 */
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.shangxian.art.adapter.ClassificationAdp;
import com.shangxian.art.adapter.ClassityCommodiyAdp;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ClassificationModel;
import com.shangxian.art.bean.ClassityCommdityModel;
import com.shangxian.art.utils.CommonUtil;

public class ClassifyCommodityActivity extends BaseActivity{
	//列表
	private ListView list;
	private List<ClassityCommdityModel>model;
	private ClassityCommodiyAdp adapter;
	
	//列表里的购物车图标
	TextView shop;

	//底部选项
	TextView shaixuan,xiaoliang,jiage,xinpin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classitycommodity);
		initView();
		initData();
		listener();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		list = (ListView) findViewById(R.id.classitycommodity);
		//底部选项
		shaixuan = (TextView) findViewById(R.id.classitycommodity_saixuan);//筛选
		xiaoliang = (TextView) findViewById(R.id.classitycommodity_xiaoliang);//销量
		jiage = (TextView) findViewById(R.id.classitycommodity_jiage);//价格
		xinpin = (TextView) findViewById(R.id.classitycommodity_xinpin);//新品

	}
	private void initData() {
		model = new ArrayList<ClassityCommdityModel>();
		for (int i = 0; i < 10; i++) {
			ClassityCommdityModel m = new ClassityCommdityModel();
			m.setTitle("新鲜菠萝/葡萄/香蕉/苹果果篮特价出售" + 1+i);
			m.setSummary("新鲜水果上市，欢迎选购" + 1+i);
			m.setPrice(""+ 3+i);
			model.add(m);
		}
		adapter = new ClassityCommodiyAdp(this, R.layout.item_classitycommodity, model);
		list.setAdapter(adapter);
		
		shop = (TextView) findViewById(R.id.item_commodity_shop);

	}
	private void listener() {
		// TODO Auto-generated method stub
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>分类商品，点击了"+model.get(arg2).getTitle());
				CommonUtil.gotoActivity(ClassifyCommodityActivity.this, CommodityContentActivity.class, false);
			}
		});
		shaixuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>筛选");
			}
		});
		xiaoliang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>销量");
			}
		});
		jiage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>价格");
			}
		});
		xinpin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>新品");
			}
		});
		
//		//购物车
//		shop.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				System.out.println(">>>>>>>加入购物车");
//			}
//		});
	}
}
