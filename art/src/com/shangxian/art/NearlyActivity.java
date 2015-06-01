package com.shangxian.art;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.jpush.android.data.s;

import com.ab.fragment.AbDialogFragment.AbDialogOnLoadListener;
import com.ab.fragment.AbLoadDialogFragment;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.task.AbTask;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListListener;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbLogUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.ab.view.titlebar.AbTitleBar;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.shangxian.art.adapter.ImageListAdapter;
import com.shangxian.art.adapter.ListCarAdapter;
import com.shangxian.art.adapter.NearlyAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.base.MyApplication;
import com.shangxian.art.bean.ListCarStoreBean;
import com.shangxian.art.bean.NearlyShopStat;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.HttpUtils;
import com.shangxian.art.net.NearlyServer;
import com.shangxian.art.net.NearlyServer.OnNearlyShopListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

public class NearlyActivity extends BaseActivity implements
		OnHeaderRefreshListener, OnFooterLoadListener, OnClickListener {
	private MyApplication application;
	// private List<Map<String, Object>> list = null;

	private AbPullToRefreshView mAbPullToRefreshView = null;
	private ListView mListView = null;
	private View ll_nonetwork, loading_big;

	private ArrayList<String> mPhotoList = new ArrayList<String>();
	private AbTitleBar mAbTitleBar = null;
	private NearlyAdapter myListViewAdapter = null;
	private AbLoadDialogFragment mDialogFragment = null;

	protected NearlyShopStat stat;

	private String lng;

	private LatLng ll;

	private TextView tv_reload;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pull_to_refresh_list);
		application = (MyApplication) abApplication;

		// mAbTitleBar = this.getTitleBar();
		// mAbTitleBar.setTitleText(R.string.pull_list_name);
		// mAbTitleBar.setLogo(R.drawable.button_selector_back);
		// mAbTitleBar.setTitleBarBackground(R.drawable.top_bg);
		// mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
		// mAbTitleBar.setLogoLine(R.drawable.line);
		initLoc();
		
		for (int i = 0; i < 23; i++) {
			mPhotoList.add(Constant.BASEURL1
					+ "content/templates/amsoft/images/rand/" + i + ".jpg");
		}
		initviews();

		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		mAbPullToRefreshView.setOnFooterLoadListener(this);

		// 设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));

		// ListView数据
		// list = new ArrayList<Map<String, Object>>();

		// 使用自定义的Adapter
		myListViewAdapter = new NearlyAdapter(mAc, R.layout.nearly_item,
				stat != null && !stat.isNull() ? stat.getContents() : null);
		mListView.setAdapter(myListViewAdapter);

		// item被点击事件
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});

		refreshTask();
	}

	private void initLoc() {
		BDLocation bdl = app.getMLoc();
		if (bdl != null) {
			ll = new LatLng(bdl.getLatitude(), bdl.getLongitude());
		} else {
			myToast("获取位置失败");
		}
		
		try {
			lng = ll.longitude + "," + ll.latitude;
		} catch (Exception e) {
			lng = null;
			myToast("获取位置失败");
		}
	}

	private void initviews() {
		mAbPullToRefreshView = (AbPullToRefreshView) this
				.findViewById(R.id.mPullRefreshView);
		mListView = (ListView) this.findViewById(R.id.mListView);
		ll_nonetwork = findViewById(R.id.ll_nonetwork);
		loading_big = findViewById(R.id.loading_big);
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		loadMoreTask();
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		refreshTask();
	}

	// public void refreshTask1() {
	// AbLogUtil.prepareLog(this);
	// AbTask mAbTask = new AbTask();
	// final AbTaskItem item = new AbTaskItem();
	// item.setListener(new AbTaskListListener() {
	// @Override
	// public List<?> getList() {
	// List<Map<String, Object>> newList = null;
	// try {
	// Thread.sleep(1000);
	// currentPage = 1;
	// newList = new ArrayList<Map<String, Object>>();
	// Map<String, Object> map = null;
	//
	// for (int i = 0; i < pageSize; i++) {
	// map = new HashMap<String, Object>();
	// map.put("itemsIcon", mPhotoList.get(i));
	// map.put("itemsTitle", "item" + (i + 1));
	// map.put("itemsText", "item..." + (i + 1));
	// newList.add(map);
	//
	// }
	// } catch (Exception e) {
	// }
	// return newList;
	// }
	//
	// @Override
	// public void update(List<?> paramList) {
	//
	// // 通知Dialog
	// mDialogFragment.loadFinish();
	// AbLogUtil.d(NearlyActivity.this, "返回", true);
	// List<Map<String, Object>> newList = (List<Map<String, Object>>)
	// paramList;
	// list.clear();
	// if (newList != null && newList.size() > 0) {
	// list.addAll(newList);
	// myListViewAdapter.notifyDataSetChanged();
	// newList.clear();
	// }
	// mAbPullToRefreshView.onHeaderRefreshFinish();
	// }
	//
	// });
	//
	// mAbTask.execute(item);
	// }
	
	private int curPage = 0;
	private void refreshTask() {
		// String url = Constant.BASEURL + Constant.CONTENT +
		// Constant.CATEGORYS;
		// // AbRequestParams params = new AbRequestParams();
		// // params.put("shopid", "1019");
		// // params.put("code", "88881110344801123456");
		// // params.put("phone", "15889936624");
		// httpUtil.get(url, new AbStringHttpResponseListener() {
		//
		// @Override
		// public void onStart() {
		// }
		//
		// @Override
		// public void onFinish() {
		// mAbPullToRefreshView.onHeaderRefreshFinish();
		// }
		//
		// @Override
		// public void onFailure(int statusCode, String content,
		// Throwable error) {
		// mListView.setVisibility(View.GONE);
		// ll_nonetwork.setVisibility(View.VISIBLE);
		// // AbToastUtil.showToast(HomeActivity.this, error.getMessage());
		// // imgList.clear();
		// // ArrayList<String> imgs = new ArrayList<String>();
		// //
		// imgs.add("http://img1.imgtn.bdimg.com/it/u=3784117098,1253514089&fm=21&gp=0.jpg");
		// // mDatas.setImgList(imgs);
		// // if (mDatas != null) {
		// // if (mDatas.getImgList() != null
		// // && mDatas.getImgList().size() > 0) {
		// // imgList.addAll(mDatas.getImgList());
		// // // viewPager.setVisibility(View.VISIBLE);
		// // viewPager.setOnGetView(new OnGetView() {
		// //
		// // @Override
		// // public View getView(ViewGroup container,
		// // int position) {
		// // ImageView iv = new ImageView(HomeActivity.this);
		// // Imageloader_homePager.displayImage(
		// // imgList.get(position), iv,
		// // new Handler(), null);
		// // container.addView(iv);
		// // return iv;
		// // }
		// // });
		// // viewPager.setAdapter(imgList.size());
		// // }
		// //
		// // }
		//
		// }
		//
		// @Override
		// public void onSuccess(int statusCode, String content) {
		// // AbToastUtil.showToast(HomeActivity.this, content);
		// // model.clear();
		// mListView.setVisibility(View.VISIBLE);
		// if (!TextUtils.isEmpty(content)) {
		// Gson gson = new Gson();
		// try {
		// JSONObject jsonObject = new JSONObject(content);
		// String result_code = jsonObject
		// .getString("result_code");
		// if (result_code.equals("200")) {
		// List<Map<String, Object>> newList = null;
		// currentPage = 1;
		// newList = new ArrayList<Map<String, Object>>();
		// Map<String, Object> map = null;
		//
		// for (int i = 0; i < pageSize; i++) {
		// map = new HashMap<String, Object>();
		// map.put("itemsIcon", mPhotoList.get(i));
		// map.put("itemsTitle", "item" + (i + 1));
		// map.put("itemsText", "item..." + (i + 1));
		// newList.add(map);
		//
		// }
		//
		// AbLogUtil.d(NearlyActivity.this, "返回", true);
		// list.clear();
		// if (newList != null && newList.size() > 0) {
		// list.addAll(newList);
		// myListViewAdapter.notifyDataSetChanged();
		// newList.clear();
		// }
		// mAbPullToRefreshView.onHeaderRefreshFinish();
		// }
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//
		// }
		//
		// });
		if (!HttpUtils.checkNetWork(mAc) || TextUtils.isEmpty(lng)) {
			mListView.setVisibility(View.GONE);
			ll_nonetwork.setVisibility(View.VISIBLE);
			return;
		}
		new NearlyServer().toNearlyShop(lng, 10000, 0,
				new OnNearlyShopListener() {
					@Override
					public void onNearly(NearlyShopStat stat) {
						curPage = 0;
						MyLogger.i(stat != null ? stat.toString() : "null");
						if (stat != null && !stat.isNull()) {
							NearlyActivity.this.stat = stat;
							if (myListViewAdapter != null) {
								hideNoData();
								mListView.setVisibility(View.VISIBLE);
								myListViewAdapter
										.upDateList(NearlyActivity.this.stat
												.getContents());
								mAbPullToRefreshView.onHeaderRefreshFinish();
							} else {
								showNoData(NoDataModel.noShop);
							}
						} else {
							mListView.setVisibility(View.GONE);
							//ll_nonetwork.setVisibility(View.VISIBLE);
							showNoData(NoDataModel.noShop);
						}
					}
				});
	}

	public void loadMoreTask() {
		// AbTask mAbTask = new AbTask();
		// final AbTaskItem item = new AbTaskItem();
		// item.setListener(new AbTaskListListener() {
		//
		// @Override
		// public void update(List<?> paramList) {
		// List<Map<String, Object>> newList = (List<Map<String, Object>>)
		// paramList;
		// if (newList != null && newList.size() > 0) {
		// list.addAll(newList);
		// myListViewAdapter.notifyDataSetChanged();
		// newList.clear();
		// }
		// mAbPullToRefreshView.onFooterLoadFinish();
		//
		// }
		//
		// @Override
		// public List<?> getList() {
		// List<Map<String, Object>> newList = null;
		// try {
		// currentPage++;
		// Thread.sleep(1000);
		// newList = new ArrayList<Map<String, Object>>();
		// Map<String, Object> map = null;
		//
		// for (int i = 0; i < pageSize; i++) {
		// map = new HashMap<String, Object>();
		// map.put("itemsIcon", mPhotoList.get(i));
		// map.put("itemsTitle", "item上拉"
		// + ((currentPage - 1) * pageSize + (i + 1)));
		// map.put("itemsText", "item上拉..."
		// + ((currentPage - 1) * pageSize + (i + 1)));
		// if ((list.size() + newList.size()) < total) {
		// newList.add(map);
		// }
		// }
		//
		// } catch (Exception e) {
		// currentPage--;
		// newList.clear();
		// AbToastUtil.showToastInThread(NearlyActivity.this,
		// e.getMessage());
		// }
		// return newList;
		// };
		// });
		//
		// mAbTask.execute(item);
		if (!HttpUtils.checkNetWork(mAc) || TextUtils.isEmpty(lng)) {
			mListView.setVisibility(View.GONE);
			ll_nonetwork.setVisibility(View.VISIBLE);
			return;
		}
		new NearlyServer().toNearlyShop(lng, 10000, ++ curPage,
				new OnNearlyShopListener() {
					@Override
					public void onNearly(NearlyShopStat stat) {
						MyLogger.i(stat != null ? stat.toString() : "null");
						if (stat != null && !stat.isNull()) {
							NearlyActivity.this.stat = stat;
							if (myListViewAdapter != null) {
								myListViewAdapter
										.addFootDataList(NearlyActivity.this.stat
												.getContents());
								mAbPullToRefreshView.onFooterLoadFinish();
							}
						}
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		topView = MainActivity.getTopView();
		topView.setActivity(this);
		topView.hideLeftBtn();
		topView.showRightBtn();
		topView.hideCenterSearch();
		topView.setCenterListener(null);
		topView.setTitle("附近");
		topView.showTitle();
		topView.setRightBtnDrawable(R.drawable.map);
		topView.setCenterListener((MainActivity)getParent());
	}

	public void onPause() {
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_reload:
			mListView.setVisibility(View.GONE);
			ll_nonetwork.setVisibility(View.GONE);
			loading_big.setVisibility(View.VISIBLE);
			refreshTask(); 
			break;
		default:
			break;
		}
	}

}
