package com.shangxian.art;

import java.lang.reflect.Type;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.shangxian.art.adapter.SearchsAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.SearchProductInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.SearchServer;
import com.shangxian.art.net.SearchServer.SearchType_enum;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.CircleImageView1;

public class SearchsActivity extends BaseActivity {
	//private SearchModel curModel = SearchModel.shop;
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
	private LinearLayout ll_scan;
	private LinearLayout popuv;
	private LinearLayout ll_shop;
	private LinearLayout ll_goods;
	private PopupWindow popu;
	private TextView tv_sea;
	private boolean isChangModel;
	private CircleImageView1 iv_noData;
/** 店铺内搜索传来的*/
	private String shopid;
	
//	public enum SearchModel {
//		product, shop, all,
//	}
    private SearchType_enum searchType_enum=SearchType_enum.shop;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_searchs);
		initData();
		initView();
		listener();
		updataViews();
	}

	private void updataViews() {
		if(!TextUtils.isEmpty(shopid)){
			ll_group.setVisibility(View.GONE);
			et_sreach.setHint(R.string.in_shop_search);
		}
	}

	public static void startThisActivity(String shopid, Context context) {
		Intent intent = new Intent(context, SearchsActivity.class);
		intent.putExtra("shopid", shopid);
		context.startActivity(intent);
	}
	
	private void initData() {
		shopid=getIntent().getStringExtra("shopid");
	}

	private void initView() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_search = (ImageView) findViewById(R.id.iv_search);
		iv_noData = (CircleImageView1) findViewById(R.id.notData);

		tv_sea = (TextView) findViewById(R.id.tv_sear);

		et_sreach = (EditText) findViewById(R.id.et_search);

		ll_group = (LinearLayout) findViewById(R.id.ll_group);
		ll_noData = (LinearLayout) findViewById(R.id.ll_nodata);
		ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
		ll_scan = (LinearLayout) findViewById(R.id.ll_scan);

		lv_info = (ListView) findViewById(R.id.lv_info);
		addFoot();
		searchsAdapter = new SearchsAdapter(mAc, R.layout.item_searchs,
				info == null ? null : info.getData());
		lv_info.setAdapter(searchsAdapter);

		initPopuWindow();
		changeUi(UiModel.normal);
		changeUi(UiModel.shop);
	}

	@SuppressWarnings("deprecation")
	private void initPopuWindow() {
		popuv = (LinearLayout) getLayoutInflater().inflate(
				R.layout.search_popu, null);
		ll_shop = (LinearLayout) popuv.findViewById(R.id.popu_shop);
		ll_goods = (LinearLayout) popuv.findViewById(R.id.popu_goods);
		popu = new PopupWindow(popuv, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);

		popu.setTouchable(true);
		popu.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popu.setBackgroundDrawable(new BitmapDrawable());

		popu.getContentView().setFocusableInTouchMode(true);
		popu.getContentView().setFocusable(true);

		ll_goods.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeUi(UiModel.goods);
				searchType_enum=SearchType_enum.product;
				popu.dismiss();
			}
		});
		ll_shop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeUi(UiModel.shop);
				searchType_enum=SearchType_enum.shop;
				popu.dismiss();
			}
		});
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
//		SearchServer.onSearchProduct(scan_info, "0", "10",
//				curModel == SearchModel.shop, null, new OnSearchProductListener() {
//					@Override
//					public void onSearch(SearchProductInfo product) {
//						if (product != null && !product.isNull()) {
//							changeUi(UiModel.showData);
//							info = product;
//							searchsAdapter.upDateList(info.getData());
//							if (info != null && info.isMore()) {
//								changeUi(UiModel.footerToLoad);
//							} else {
//								changeUi(UiModel.footerNoMore);
//							}
//						} else {
//							changeUi(UiModel.noData);
//						}
//					}
//				});
		if(!TextUtils.isEmpty(shopid)){
			searchType_enum=SearchType_enum.innershop;
		}
		Type type = new TypeToken<SearchProductInfo>(){}.getType();
		SearchServer.onSearchProduct(scan_info, "0", "10",shopid, searchType_enum, type, new CallBack() {	
			@Override
			public void onSimpleSuccess(Object res) {
				if (res != null) {
					info = (SearchProductInfo) res;
					if (!info.isNull()) {
						searchsAdapter.upDateList(info.getData());
						changeUi(UiModel.showData);
						if (info != null && info.isMore()) {
							changeUi(UiModel.footerToLoad);
						} else {
							changeUi(UiModel.footerNoMore);
						}
					}else {
						changeUi(UiModel.noData);
					}
				}
			}
			
			@Override
			public void onSimpleFailure(int code) {
				changeUi(UiModel.noData);
			}
		});
	}

	private void loadMore() {
//		SearchServer.onSearchProduct(scan_info, info.getStart() + "",
//				info.getPageSize() + "", curModel == SearchModel.shop,
//				new OnSearchProductListener() {
//					@Override
//					public void onSearch(SearchProductInfo product) {
//						info = product;
//						if (info != null)
//							searchsAdapter.addFootDataList(info.getData());
//						// changeUi(UiModel.showData);
//						if (info != null && info.isMore()) {
//							changeUi(UiModel.footerToLoad);
//						} else {
//							changeUi(UiModel.footerNoMore);
//						}
//					}
//				});
		Type type = new TypeToken<SearchProductInfo>(){}.getType();
		SearchServer.onSearchProduct(scan_info, (info.getStart() + 1) + "", "10", shopid, searchType_enum,  type, new CallBack() {	
			@Override
			public void onSimpleSuccess(Object res) {
				if (res != null) {
					info = (SearchProductInfo) res;
					if (!info.isNull()) {
						searchsAdapter.upDateList(info.getData());
						changeUi(UiModel.showData);
						if (info != null && info.isMore()) {
							changeUi(UiModel.footerToLoad);
						} else {
							changeUi(UiModel.footerNoMore);
						}
					}
				}
			}
			
			@Override
			public void onSimpleFailure(int code) {
				changeUi(UiModel.noData);
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
					//if (!scan.equals(scan_info) || isChangModel) {
						isChangModel = !isChangModel;
						scan_info = scan;
						changeUi(UiModel.loading);
						loadSearch();
				//	}
				}
			}
		});
		ll_group.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popu.showAsDropDown(ll_scan, -CommonUtil.dip2px(mAc, 8), 0);
			}
		});
	}

	public enum UiModel {
		footerToLoad, footerLoading, footerNoMore, loading, noData, normal, showData, shop, goods
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
			iv_noData
					.setImageResource(SearchType_enum.shop == searchType_enum ? R.drawable.noshop
							:  R.drawable.noproduct);
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
		case shop:
			tv_sea.setText("商铺");
			ll_shop.setSelected(true);
			ll_goods.setSelected(false);
			if (searchsAdapter != null)
				searchsAdapter.setToSearch(true);
			isChangModel = true;
			break;
		case goods:
			tv_sea.setText("商品");
			ll_goods.setSelected(true);
			ll_shop.setSelected(false);
			if (searchsAdapter != null)
				searchsAdapter.setToSearch(false);
			isChangModel = true;
			break;
		}
	}
}
