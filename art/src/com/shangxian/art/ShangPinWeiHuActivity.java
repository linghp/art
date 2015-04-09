package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shangxian.art.R.color;
import com.shangxian.art.adapter.SearchAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.SearchModel;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.SlidingListView;
/**
 * 商品类别维护
 * @author Administrator
 *
 */
public class ShangPinWeiHuActivity extends BaseActivity{

	private SlidingListView list;
	List<SearchModel> model;
	SearchAdapter adapter;

	//添加父类、子类 
	LinearLayout addParentClass,addSubClass;
	TextView addParentClass_txt,addSubClass_txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shangpinweihu);
		initView();
		initData();
		listener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		list = (SlidingListView) findViewById(R.id.shangpinweihu_list);
		addParentClass = (LinearLayout) findViewById(R.id.shangpinweihu_addparentclass);
		addSubClass = (LinearLayout) findViewById(R.id.shangpinweihu_subclass);
		addParentClass_txt = (TextView) findViewById(R.id.shangpinweihu_addparentclass_txt);
		addSubClass_txt = (TextView) findViewById(R.id.shangpinweihu_subclass_txt);
	}
	private void initData() {
		
		// TODO Auto-generated method stub
		model = new ArrayList<SearchModel>();
		for (int i = 0; i < 5; i++) {
			SearchModel m = new SearchModel();
			m.setTitle("特价美食"+ (1+i));
			model.add(m);
		}
		adapter = new SearchAdapter(this, R.layout.item_search, model);
		list.setAdapter(adapter);
	}

	private void listener() {
		// TODO Auto-generated method stub
		addParentClass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				CommonUtil.gotoActivity(ShangPinWeiHuActivity.this, AddParentClassActivity.class, false);
			}
		});
		addSubClass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>>添加子类");
				CommonUtil.gotoActivity(ShangPinWeiHuActivity.this, AddSubClassActivity.class, false);
			}
		});

	}
}
