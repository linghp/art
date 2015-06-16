package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ab.fragment.AbLoadDialogFragment;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbLogUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.google.gson.Gson;
import com.shangxian.art.adapter.ShopsListAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.base.MyApplication;
import com.shangxian.art.bean.ClassityCommdityResultModel;
import com.shangxian.art.bean.ShopsListModel;
import com.shangxian.art.bean.ShopsListResultModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 商铺列表
 * @author Administrator
 *
 */
public class ShopsListActivity extends BaseActivity implements
OnHeaderRefreshListener, OnFooterLoadListener,OnClickListener{
	private MyApplication application;
	private ArrayList<String> mPhotoList = new ArrayList<String>();
	private AbPullToRefreshView mAbPullToRefreshView = null;
	private ListView mListView = null;
	private View ll_nonetwork, loading_big;
	//	private List<Map<String, Object>> list = null;
	private ShopsListAdapter myListViewAdapter = null;
	private AbLoadDialogFragment mDialogFragment = null;
	//	private int currentPage = 1;
	//	private int pageSize = 15;
	//	private int total = 50;

	//数据请求
	private AbHttpUtil httpUtil = null;
	private String url = "";
	private List<ShopsListModel> model;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.pull_to_refresh_list);
		initView();
		initData();
		initListener();
	}



	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setVisibility(View.VISIBLE);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.setBack(R.drawable.back);//返回
		topView.showCenterSearch();

		application = (MyApplication) abApplication;

		// 获取ListView对象
		mAbPullToRefreshView = (AbPullToRefreshView) this
				.findViewById(R.id.mPullRefreshView);
		mListView = (ListView) this.findViewById(R.id.mListView);
		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		//mAbPullToRefreshView.setOnFooterLoadListener(this);
		mAbPullToRefreshView.setLoadMoreEnable(false);

		// 设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));

		ll_nonetwork = findViewById(R.id.ll_nonetwork);
		loading_big = findViewById(R.id.loading_big);
	}
	private void initData() {
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(Constant.timeOut);
		String geturl = getIntent().getStringExtra("url");

		url = Constant.BASEURL + Constant.CONTENT + geturl;

		model = new ArrayList<ShopsListModel>();
		System.out.println(">>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<url"+url);
		refreshTask(url);
		//		// ListView数据
		//		list = new ArrayList<Map<String, Object>>();

		// 使用自定义的Adapter(得到item中的控件)

		// 显示进度框
		//		mDialogFragment = AbDialogUtil.showLoadDialog(this,
		//				R.drawable.progress_loading2, "查询中,请等一小会");
	}

	private void initListener() {
		//		mDialogFragment.setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
		//
		//			@Override
		//			public void onLoad() {
		//				// 下载网络数据
		//				refreshTask(url);
		//			}
		//
		//		});


		// item被点击事件
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CommonUtil.gotoActivity(ShopsListActivity.this, ShopsActivity.class, false);

			}
		});

	}

	//	public static void startThisActivity(String id,
	//			Context context) {
	//		Intent intent = new Intent(context, ShopsListActivity.class);
	//		intent.putExtra("id", id);
	//		context.startActivity(intent);
	//	}

	public static void startThisActivity_url(String url, Context context) {
		Intent intent = new Intent(context, ShopsListActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}
	@Override
	public void onFooterLoad(AbPullToRefreshView arg0) {
		// TODO Auto-generated method stub
		//		loadMoreTask();
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView arg0) {
		// TODO Auto-generated method stub
		refreshTask(url);
	}

	//
	public void refreshTask(String url) {
		httpUtil.post(url, new AbStringHttpResponseListener() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish() {
				mAbPullToRefreshView.onHeaderRefreshFinish();
				mAbPullToRefreshView.onFooterLoadFinish();
			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
				mAbPullToRefreshView.setVisibility(View.GONE);
				ll_nonetwork.setVisibility(View.VISIBLE);
				loading_big.setVisibility(View.GONE);
			}

			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				AbLogUtil.i(ShopsListActivity.this, arg1);
				mAbPullToRefreshView.setVisibility(View.VISIBLE);
				loading_big.setVisibility(View.GONE);
				//解析
				if (!TextUtils.isEmpty(arg1)) {
					Gson gson = new Gson();
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(arg1);
						String result_code = jsonObject.getString("result_code");
						String reason = jsonObject.getString("reason");
						if (result_code.equals("200")&&reason.equals("success")) {
							String str=jsonObject.getString("result");
							ShopsListResultModel shopsListResultModel=gson.fromJson(str, ShopsListResultModel.class);
							if(shopsListResultModel!=null){
							model = shopsListResultModel.getData();
							if (model != null) {
								myListViewAdapter = new ShopsListAdapter(ShopsListActivity.this, model, R.layout.item_list);
								mListView.setAdapter(myListViewAdapter);
								loading_big.setVisibility(View.GONE);
							}else {
								loading_big.setVisibility(View.VISIBLE);
							}
							/*int length = resultObjectArray.length();
							model.clear();
							for (int i = 0; i < length; i++) {
								JSONObject jo = resultObjectArray.getJSONObject(i);
								model.add(gson.fromJson(jo.toString(),ShopsListModel.class));
							}*/
							if(model!=null&&model.size()>0){
								showNoData(NoDataModel.noShop);
							}else{
								hideNoData();
							}
							MyLogger.i(model.toString());
							myListViewAdapter.notifyDataSetChanged();
							}
						}else{
							
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_reload:
			mAbPullToRefreshView.setVisibility(View.GONE);
			ll_nonetwork.setVisibility(View.GONE);
			loading_big.setVisibility(View.VISIBLE);
			initData();
			break;
		default:
			break;
		}
	}
	//		AbLogUtil.prepareLog(this);
	//		AbTask mAbTask = new AbTask();
	//		final AbTaskItem item = new AbTaskItem();
	//		item.setListener(new AbTaskListListener() {
	//			@Override
	//			public List<?> getList() {
	//				List<Map<String, Object>> newList = null;
	//				try {
	//					Thread.sleep(1000);
	//					currentPage = 1;
	//					newList = new ArrayList<Map<String, Object>>();
	//					Map<String, Object> map = null;
	//
	//					for (int i = 0; i < pageSize; i++) {
	//						map = new HashMap<String, Object>();
	//						map.put("itemsIcon", mPhotoList.get(i));
	//						map.put("itemsTitle", "item" + (i + 1));
	//						map.put("itemsText", "item..." + (i + 1));
	//						newList.add(map);
	//
	//					}
	//				} catch (Exception e) {
	//				}
	//				return newList;
	//			}
	//
	//			@Override
	//			public void update(List<?> paramList) {
	//
	//				// 通知Dialog
	//				mDialogFragment.loadFinish();
	//				AbLogUtil.d(ShopsListActivity.this, "返回", true);
	//				//				List<Map<String, Object>> newList = (List<Map<String, Object>>) paramList;
	//				List<ShopsListModel> newList = (List<ShopsListModel>) paramList;
	//				model.clear();
	//				if (newList != null && newList.size() > 0) {
	//					model.addAll(newList);
	//					myListViewAdapter.notifyDataSetChanged();
	//					newList.clear();
	//				}
	//				mAbPullToRefreshView.onHeaderRefreshFinish();
	//			}
	//
	//		});
	//
	//		mAbTask.execute(item);
	//	}
	//	public void loadMoreTask() {
	//		AbTask mAbTask = new AbTask();
	//		final AbTaskItem item = new AbTaskItem();
	//		item.setListener(new AbTaskListListener() {
	//
	//			@Override
	//			public void update(List<?> paramList) {
	//				//				List<Map<String, Object>> newList = (List<Map<String, Object>>) paramList;
	//				List<ShopsListModel> newList = (List<ShopsListModel>) paramList;
	//				if (newList != null && newList.size() > 0) {
	//					model.addAll(newList);
	//					myListViewAdapter.notifyDataSetChanged();
	//					newList.clear();
	//				}
	//				mAbPullToRefreshView.onFooterLoadFinish();
	//			}
	//
	//			@Override
	//			public List<?> getList() {
	//				List<Map<String, Object>> newList = null;
	//				try {
	//					currentPage++;
	//					Thread.sleep(1000);
	//					newList = new ArrayList<Map<String, Object>>();
	//					Map<String, Object> map = null;
	//
	//					for (int i = 0; i < pageSize; i++) {
	//						map = new HashMap<String, Object>();
	//						map.put("itemsIcon", mPhotoList.get(i));
	//						map.put("itemsTitle", "item上拉"
	//								+ ((currentPage - 1) * pageSize + (i + 1)));
	//						map.put("itemsText", "item上拉..."
	//								+ ((currentPage - 1) * pageSize + (i + 1)));
	//						if ((model.size() + newList.size()) < total) {
	//							newList.add(map);
	//						}
	//					}
	//
	//				} catch (Exception e) {
	//					currentPage--;
	//					newList.clear();
	//					AbToastUtil.showToastInThread(ShopsListActivity.this,
	//							e.getMessage());
	//				}
	//				return newList;
	//			};
	//		});
	//
	//		mAbTask.execute(item);
	//	}
}
