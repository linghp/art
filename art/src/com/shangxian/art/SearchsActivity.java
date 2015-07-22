package com.shangxian.art;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.shangxian.art.adapter.MyOrderListAdapter1;
import com.shangxian.art.adapter.SearchsAdapter;
import com.shangxian.art.adapter.SellerRefoundOrderAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.MyOrderItem_all;
import com.shangxian.art.bean.SearchProductInfo;
import com.shangxian.art.bean.SellerRefoundOrderInfo;
import com.shangxian.art.bean.SellerRefoundstat;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.SearchServer;
import com.shangxian.art.net.SearchServer.SearchType_enum;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
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
	private List<ListCarGoodsBean> data = new ArrayList<ListCarGoodsBean>();

	private MyOrderItem_all all;
	private List<MyOrderItem> mOrderItems = new ArrayList<MyOrderItem>();
	private MyOrderListAdapter1 myOrderListAdapter1;
	
	protected SellerRefoundstat refoundstat;
	private List<SellerRefoundOrderInfo> mOrderItem = new ArrayList<SellerRefoundOrderInfo>();
	private SellerRefoundOrderAdapter myOrderListAdapter;

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

	//是否为发货/退货订单
	private String isorder;

	private int skip = 0; // 从第skip+1条开始查询
	private final int pageSize = 10;

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
			searchsAdapter.setToSearch(false);
		}

		if ("fahuo".equals(isorder)) {
			ll_group.setVisibility(View.GONE);
			et_sreach.setHint(R.string.in_shop_fahuo);
			//			searchsAdapter.setToSearch(false);
		}else if ("tuihuo".equals(isorder)) {
			ll_group.setVisibility(View.GONE);
			et_sreach.setHint(R.string.in_shop_tuihuo);
			//			searchsAdapter.setToSearch(false);
		}
	}

	public static void startThisActivity(String shopid, Context context) {
		Intent intent = new Intent(context, SearchsActivity.class);
		intent.putExtra("shopid", shopid);
		context.startActivity(intent);
	}
	public static void startThisActivity1(String isorder, Context context) {
		Intent intent = new Intent(context, SearchsActivity.class);
		intent.putExtra("isorder", isorder);
		context.startActivity(intent);
	}

	private void initData() {
		shopid=getIntent().getStringExtra("shopid");

		isorder = getIntent().getStringExtra("isorder");

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
				data);
		myOrderListAdapter1 = new MyOrderListAdapter1(mAc, mOrderItems);
		myOrderListAdapter = new SellerRefoundOrderAdapter(mAc,R.layout.list_myorder_item, mOrderItem);

		if ("fahuo".equals(isorder)) {
			lv_info.setAdapter(myOrderListAdapter1);
		}else if ("tuihuo".equals(isorder)) {
			lv_info.setAdapter(myOrderListAdapter);
		}else {
			lv_info.setAdapter(searchsAdapter);
		}
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
				}else if (all != null) {
					changeUi(UiModel.footerLoading);
					loadMore();
				}else if (refoundstat != null) {
					changeUi(UiModel.footerLoading);
					loadMore();
				}
				
			}
		});
		lv_info.addFooterView(footLayout);
	}

	private void loadSearch() {
		if(!TextUtils.isEmpty(shopid)){
			searchType_enum=SearchType_enum.innershop;
			Type type = new TypeToken<SearchProductInfo>(){}.getType();
			SearchServer.onSearchProduct(scan_info, "0", "10",shopid, searchType_enum, type, new CallBack() {	
				@Override
				public void onSimpleSuccess(Object res) {
					MyLogger.i("搜索数据>>>>>>>>"+res);
					if (res != null) {
						if (!TextUtils.isEmpty(shopid)) {
							skip = 0;
							info = (SearchProductInfo) res;
							if (!info.isNull()) {
								data.clear();
								data.addAll(info.getData());
								searchsAdapter.upDateList(data);
								changeUi(UiModel.showData);
								if (info != null && info.isMore()) {
									changeUi(UiModel.footerToLoad);
								} else {
									changeUi(UiModel.footerNoMore);
								}
							}
						}
					}else {
						changeUi(UiModel.noData);
					}
				}

				@Override
				public void onSimpleFailure(int code) {
					changeUi(UiModel.noData);
				}
			});

		}
		if ("fahuo".equals(isorder)) {
			searchType_enum=SearchType_enum.fahuo;
			Type type = new TypeToken<MyOrderItem_all>(){}.getType();
			SearchServer.onSearchProduct(scan_info, "0", "10",shopid, searchType_enum, type, new CallBack() {

				@Override
				public void onSimpleSuccess(Object res) {
					MyLogger.i("搜索数据>>>>>>>>"+res);
					if (res != null) {
						all = (MyOrderItem_all) res;
						if (!all.isNull()) {
							mOrderItems.clear();
							mOrderItems.addAll(all.getData());
							changeUi(UiModel.showData);
							myOrderListAdapter1.notifyDataSetChanged();
							if (all != null) {
								changeUi(UiModel.footerToLoad);
							} else {
								changeUi(UiModel.footerNoMore);
							}
						} else {
							changeUi(UiModel.footerNoMore);
						}
					} else {
						changeUi(UiModel.footerNoMore);
					}
				}

				@Override
				public void onSimpleFailure(int code) {
					changeUi(UiModel.noData);
				}

			});

		}else if ("tuihuo".equals(isorder)) {
			//退货搜索
			searchType_enum=SearchType_enum.tuihuo;
			Type type = new TypeToken<SellerRefoundstat>(){}.getType();
			SearchServer.onSearchProduct(scan_info, "0", "10",shopid, searchType_enum, type, new CallBack() {

				@Override
				public void onSimpleSuccess(Object res) {
					MyLogger.i("搜索数据>>>>>>>>"+res);
					if (res != null) {
						refoundstat = (SellerRefoundstat) res;
						if (!refoundstat.isNull()) {
							mOrderItem.clear();
							mOrderItem.addAll(refoundstat.getData());
							changeUi(UiModel.showData);
							myOrderListAdapter.notifyDataSetChanged();
							if (refoundstat != null) {
								changeUi(UiModel.footerToLoad);
							} else {
								changeUi(UiModel.footerNoMore);
							}
						} else {
							changeUi(UiModel.footerNoMore);
						}
					} else {
						changeUi(UiModel.footerNoMore);
					}
					
				}

				@Override
				public void onSimpleFailure(int code) {
					// TODO Auto-generated method stub
					
				}
				
			});
		}

	}

	private void loadMore() {
		if(!TextUtils.isEmpty(shopid)){
			System.out.println("搜索更多数据>>>>>>>>");
			skip += pageSize;
			Type type = new TypeToken<SearchProductInfo>(){}.getType();
			SearchServer.onSearchProduct(scan_info, skip+ "", "10", shopid, searchType_enum,  type, new CallBack() {	
				@Override
				public void onSimpleSuccess(Object res) {
					MyLogger.i("搜索更多数据>>>>>>>>"+res);
					if (res != null) {
						info = (SearchProductInfo) res;
						if (!info.isNull()) {
							data.addAll(info.getData());
							searchsAdapter.upDateList(data);
							changeUi(UiModel.showData);
							if (info != null && info.isMore()) {
								changeUi(UiModel.footerToLoad);
							} else {
								changeUi(UiModel.footerNoMore);
							}
						}else{
							changeUi(UiModel.footerNoMore);
						}
					}else{
						skip -= pageSize;
					}
				}

				@Override
				public void onSimpleFailure(int code) {
					changeUi(UiModel.noData);
					skip -= pageSize;
				}
			});
		}else if ("fahuo".equals(isorder)) {
			System.out.println("搜索更多数据>>>>>>>>");
			skip += pageSize;
			searchType_enum=SearchType_enum.fahuo;
			Type type = new TypeToken<MyOrderItem_all>(){}.getType();
			SearchServer.onSearchProduct(scan_info, skip+ "", "10",shopid, searchType_enum, type, new CallBack() {

				@Override
				public void onSimpleSuccess(Object res) {
					MyLogger.i("搜索更多数据>>>>>>>>"+res);
					if (res != null) {
						all = (MyOrderItem_all) res;
						if (!all.isNull()) {
							mOrderItems.addAll(all.getData());
							myOrderListAdapter1.notifyDataSetChanged();
							changeUi(UiModel.showData);
							if (all != null) {
								changeUi(UiModel.footerToLoad);
							} else {
								changeUi(UiModel.footerNoMore);
							}
						} else {
							changeUi(UiModel.footerNoMore);
						}
					} else {
						skip -= pageSize;
					}
				}

				@Override
				public void onSimpleFailure(int code) {
					changeUi(UiModel.noData);
					skip -= pageSize;
				}

			});
		}else if ("tuihuo".equals(isorder)) {
			//退货搜索
			searchType_enum=SearchType_enum.tuihuo;
			skip += pageSize;
			Type type = new TypeToken<SellerRefoundstat>(){}.getType();
			SearchServer.onSearchProduct(scan_info, skip+ "", "10",shopid, searchType_enum, type, new CallBack() {

				@Override
				public void onSimpleSuccess(Object res) {
					MyLogger.i("搜索数据>>>>>>>>"+res);
					if (res != null) {
						refoundstat = (SellerRefoundstat) res;
						if (!refoundstat.isNull()) {
							mOrderItem.addAll(refoundstat.getData());
							changeUi(UiModel.showData);
							myOrderListAdapter.notifyDataSetChanged();
							if (refoundstat != null) {
								changeUi(UiModel.footerToLoad);
							} else {
								changeUi(UiModel.footerNoMore);
							}
						} else {
							changeUi(UiModel.footerNoMore);
						}
					} else {
						skip -= pageSize;
					}
					
				}

				@Override
				public void onSimpleFailure(int code) {
					changeUi(UiModel.noData);
					skip -= pageSize;
				}
				
			});
		}
		
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
		
			lv_info.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					//订单详情
					if ("fahuo".equals(isorder)) {
					SellerOrderDetailsActivity.startThisActivity_MyOrder(
							mOrderItems.get(position).getOrderId() + "", position, mAc);
					}
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
