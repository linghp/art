package com.shangxian.art;

/**
 * 分类
 */
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.shangxian.art.adapter.ClassificationAdp;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ClassificationModel;
import com.shangxian.art.utils.CommonUtil;

public class ClassificationActivity extends BaseActivity {

	private ListView list;
	private List<ClassificationModel>model;
	private ClassificationAdp adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classification);
		initView();
		initData();
		listener();
	}
	
	//添加数据
	private void initData() {
		// TODO Auto-generated method stub
		model = new ArrayList<ClassificationModel>();
		for (int i = 0; i < 10; i++) {
			ClassificationModel m = new ClassificationModel();
			m.setTitle("特价美食" + 1+i);
			m.setSummary("特价美食，干果/仙贝" + 1+i);
			model.add(m);
		}
		adapter = new ClassificationAdp(this, R.layout.item_classifiymain, model);
		list.setAdapter(adapter);
		
	}

	//初始化控件
	private void initView() {
		// TODO Auto-generated method stub
		list = (ListView) findViewById(R.id.classify);
		
	}

	//事件监听
	private void listener(){
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				System.out.println(">>>>>>>>分类：点击了" + model.get(position).getTitle());
				CommonUtil.gotoActivity(ClassificationActivity.this, ClassifyCommodityActivity.class, true);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.classification, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
