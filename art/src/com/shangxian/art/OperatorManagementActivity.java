package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.shangxian.art.adapter.OperatorManagementAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CommonBeanObject;
import com.shangxian.art.bean.ShopOperatorBean;
import com.shangxian.art.net.ShopOperatorServer;
import com.shangxian.art.net.ShopOperatorServer.OnHttpResultGetOperatorListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 操作员管理
 * @author Administrator
 *
 */
public class OperatorManagementActivity extends BaseActivity implements OnHttpResultGetOperatorListener{

	private ListView listview;
	private OperatorManagementAdapter adapter;
	private List<ShopOperatorBean> model=new ArrayList<ShopOperatorBean>();
	private View loading_big;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
		initData();
		initListener();
		requestTask();
	}

	private void requestTask() {
		ShopOperatorServer.onGetOperator_xutils(this);
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.setRightBtnDrawable(R.drawable.addto);
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_operatormanagement));
		
		listview = (ListView) findViewById(R.id.search_list);
		loading_big=findViewById(R.id.loading_big);
	}

	private void initData() {
		adapter=new OperatorManagementAdapter(this, R.layout.item_operatormanagement, model);
		listview.setAdapter(adapter);
	}

	private void initListener() {
		topView.setRightBtnListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 添加操作员
				CommonUtil.gotoActivityForResult(OperatorManagementActivity.this,
						AddOperatorActivity.class,1, false);
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 操作员详情
				OperatorDetailsActivity.startThisActivity(OperatorManagementActivity.this, model.get(position));
				// CommonUtil.gotoActivity(OperatorManagementActivity.this,
				// OperatorDetailsActivity.class, false);
			}
		});
	}

	@Override
	public void onHttpResultGetOperator(CommonBeanObject<List<ShopOperatorBean>> commonBeanObject) {
		loading_big.setVisibility(View.GONE);
		if(commonBeanObject!=null&&commonBeanObject.getResult_code().equals("200")){
			MyLogger.i(" 操作员管理数据："+commonBeanObject.toString());
			System.out.println(" 操作员管理数据："+commonBeanObject.toString());
			model.clear();
			List<ShopOperatorBean> shopOperatorBeans=commonBeanObject.getObject();
			if(shopOperatorBeans!=null){
			model.addAll(shopOperatorBeans);
			adapter.notifyDataSetChanged();
			}else{
				myToast("还没有操作员");
			}
			MyLogger.i(commonBeanObject.toString());
		}else{
			myToast("查询失败");
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			requestTask();
		}
	}
}
