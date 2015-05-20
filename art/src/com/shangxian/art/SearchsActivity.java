package com.shangxian.art;

import java.util.List;

import com.shangxian.art.adapter.SearchsAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.bean.SearchProductInfo;
import com.shangxian.art.net.SearchServer;
import com.shangxian.art.net.SearchServer.OnSearchProductListener;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SearchsActivity extends BaseActivity {
	private SearchModel curModel = SearchModel.normal;
	private ImageView iv_back;
	private ImageView iv_search;
	private EditText et_sreach;
	private LinearLayout ll_group;
	private ListView lv_info;
	private SearchsAdapter searchsAdapter;

	private SearchProductInfo info;

	private String scan_info;
	private LinearLayout footLayout;
	private LinearLayout l_laoding;
	private LinearLayout l_toLoad;
	private LinearLayout ll_noData;
	private LinearLayout ll_loading;
	private TextView tv_m;

	public enum SearchModel {
		product, shop, all, normal
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_searchs);
		initData();
		initView();
		listener();
	}

	private void initData() {

	}

	private void initView() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_search = (ImageView) findViewById(R.id.iv_search);

		et_sreach = (EditText) findViewById(R.id.et_search);

		ll_group = (LinearLayout) findViewById(R.id.ll_group);
		ll_noData = (LinearLayout) findViewById(R.id.ll_nodata);
		ll_loading = (LinearLayout) findViewById(R.id.ll_loading);

		lv_info = (ListView) findViewById(R.id.lv_info);
		addFoot();
		searchsAdapter = new SearchsAdapter(mAc, R.layout.item_searchs,
				info == null ? null : info.getData());
		lv_info.setAdapter(searchsAdapter);

		changeUi(UiModel.normal);
	}

	private void addFoot() {
		footLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.search_loadmore, null);
		l_laoding = (LinearLayout) footLayout
				.findViewById(R.id.search_loadmore_loading);
		l_toLoad = (LinearLayout) footLayout
				.findViewById(R.id.search_loadmore_toload);
		tv_m = (TextView) footLayout.findViewById(R.id.search_tv_m);
		l_toLoad.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (info != null && info.isMore()) {
					changeUi(UiModel.footerLoading);
					loadMore();
				}
			}
		});
		lv_info.addFooterView(footLayout);
	}

	private void loadSearch() {
		SearchServer.onSearchProduct(scan_info, "0", "10",
				"{'createDate': 'asc'}", new OnSearchProductListener() {
					@Override
					public void onSearch(SearchProductInfo product) {
						if (!product.isNull()) {
							changeUi(UiModel.showData);
							info = product;
							searchsAdapter.upDateList(info.getData());	
							if (info != null && info.isMore()) {
								changeUi(UiModel.footerToLoad);
							} else {
								changeUi(UiModel.footerNoMore);
							}
						} else {
							changeUi(UiModel.noData);
						}
					}
				});
	}

	private void loadMore() {
		SearchServer.onSearchProduct(scan_info, info.getStart() + "",
				info.getPageSize() + "", "{'createDate': 'asc'}",
				new OnSearchProductListener() {
					@Override
					public void onSearch(SearchProductInfo product) {
						info = product;
						if (info != null) searchsAdapter.addFootDataList(info.getData());
						//changeUi(UiModel.showData);
						if (info != null && info.isMore()) {
							changeUi(UiModel.footerToLoad);
						} else {
							changeUi(UiModel.footerNoMore);
						}
					}
				});
	}

	private void listener() {
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		iv_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String scan = et_sreach.getText().toString();
				if (TextUtils.isEmpty(scan)) {
					myToast("请输入搜索条件");
				} else {
					if (!scan.equals(scan_info)) {
						scan_info = scan;
						changeUi(UiModel.loading);
						loadSearch();
					}
				}
			}
		});
	}

	public enum UiModel {
		footerToLoad, footerLoading, footerNoMore, loading, noData, normal, showData
	}

	private void changeUi(UiModel m) {
		switch (m) {
		case footerToLoad:
			l_laoding.setVisibility(View.GONE);
			l_toLoad.setVisibility(View.VISIBLE);
			l_toLoad.setEnabled(true);
			tv_m.setEnabled(true);
			tv_m.setText("点击加载更多");
			break;
		case footerLoading:
			l_laoding.setVisibility(View.VISIBLE);
			l_toLoad.setVisibility(View.GONE);
			break;
		case footerNoMore:
			l_laoding.setVisibility(View.GONE);
			l_toLoad.setVisibility(View.VISIBLE);
			l_toLoad.setEnabled(false);
			tv_m.setEnabled(false);
			tv_m.setText("暂无数据加载");
			break;
		case loading:
			ll_loading.setVisibility(View.VISIBLE);
			ll_noData.setVisibility(View.GONE);
			lv_info.setVisibility(View.GONE);
			break;
		case noData:
			ll_loading.setVisibility(View.GONE);
			ll_noData.setVisibility(View.VISIBLE);
			lv_info.setVisibility(View.GONE);
			break;
		case normal:
			ll_loading.setVisibility(View.GONE);
			ll_noData.setVisibility(View.GONE);
			lv_info.setVisibility(View.GONE);
			break;
		case showData:
			ll_loading.setVisibility(View.GONE);
			ll_noData.setVisibility(View.GONE);
			lv_info.setVisibility(View.VISIBLE);
			break;
		}
	}
}
