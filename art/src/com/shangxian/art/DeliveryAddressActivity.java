package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.shangxian.art.adapter.DeliveryAddressAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.DeliveryAddressModel;
import com.shangxian.art.net.AccountSecurityServer;
import com.shangxian.art.net.AccountSecurityServer.OnHttpAddressListener;
import com.shangxian.art.net.AccountSecurityServer.OnHttpDeleteListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 收货地址管理
 * @author Administrator
 *
 */
public class DeliveryAddressActivity extends BaseActivity{

	private ListView listview;
	//	private SlidingListView listview;
	private List<DeliveryAddressModel>list;
	private DeliveryAddressAdapter adapter;

	Boolean isfromConfirmOrder = false;

	private View loading_big,ll_refresh_empty;

	private int delete = 0;
	DeliveryAddressModel model;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
		initData();
		initListener();
	}
	@Override
	protected void onResume() {
		super.onResume();
		list = new ArrayList<DeliveryAddressModel>();
		/*for (int i = 0; i < 5; i++) {
			DeliveryAddressModel model = new DeliveryAddressModel();
			model.setName("小明"+i);
			model.setNum("1869663681"+i);
			model.setAddress("重庆市沙坪坝区沙中路23号通江大道1栋3单元8—"+(i+1));
			list.add(model);
		}*/	
		refreshTask();

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
		//		listview = (SlidingListView) findViewById(R.id.search_list);
		loading_big = findViewById(R.id.loading_big);//加载
		ll_refresh_empty = findViewById(R.id.ll_refresh_empty);//无数据
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
	private void refreshTask() {
		AccountSecurityServer.toDeliveiveAddress(new OnHttpAddressListener() {

			@Override
			public void onHttpAddress(List<DeliveryAddressModel> mlist) {
				if (mlist != null) {
					list = mlist;
					adapter = new DeliveryAddressAdapter(DeliveryAddressActivity.this, R.layout.item_deliveryaddress, mlist);
					listview.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					loading_big.setVisibility(View.GONE);
				}else {
					loading_big.setVisibility(View.GONE);
					ll_refresh_empty.setVisibility(View.GONE);
				}
			}
		});


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
					bundle.putBoolean("isRevise", true);
					//					bundle.putInt("id", list.get(position).getId());
					bundle.putSerializable("DeliveryAddressModel", list.get(position));
					CommonUtil.gotoActivityWithData(DeliveryAddressActivity.this, AddDeliveryAddressActivity.class, bundle,false);
				}
			}
		});

		/*listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				delete = 0;
				//对画框
				AlertDialog.Builder multiDia=new AlertDialog.Builder(DeliveryAddressActivity.this);
				multiDia.setTitle("请选择");//标题
				multiDia.setSingleChoiceItems(new String[] {"删除","设为默认地址"}, 0, new DialogInterface.OnClickListener() {
					//选项框
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) 
							delete = 0;
						else 
							delete = 1;
					}
				});
				multiDia.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (delete == 0) {
							//删除
							AccountSecurityServer.toGetDeleteAddress(list.get(position).getId(), new OnHttpDeleteListener() {								
								@Override
								public void onHttpDelete(String res) {
									myToast(res);
									onResume();
								}
							});

						}else {
							MyLogger.i(">>>>>>>>>>确定+选择的是设为默认地址"+position);
							//设为默认地址
							AccountSecurityServer.toGetDefaultAddress(list.get(position).getId(), new OnHttpDeleteListener() {

								@Override
								public void onHttpDelete(String res) {
									MyLogger.i("设为默认地址》》》》》》》》》》》》："+res);
									myToast(res);
								}
							});
						}
					}
				});
				multiDia.setNegativeButton("取消", null);
				multiDia.show(); 
				return true;
			}
		});*/
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				AlertDialog.Builder multiDia=new AlertDialog.Builder(DeliveryAddressActivity.this);
				multiDia.setTitle("删除");//标题
				multiDia.setMessage("确定要删除吗？");
				multiDia.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						AccountSecurityServer.toGetDeleteAddress(list.get(position).getId(), new OnHttpDeleteListener() {								
							@Override
							public void onHttpDelete(String res) {
								myToast(res);
								onResume();
							}
						});
					}
				});
				multiDia.setNegativeButton("取消", null);
				multiDia.show();
				return true;
			}
		});

	}
}
