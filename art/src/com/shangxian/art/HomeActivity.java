package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbLogUtil;
import com.ab.util.AbSharedUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.google.gson.Gson;
import com.shangxian.art.adapter.HomeGridAdp;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.GoodBean;
import com.shangxian.art.bean.HomeData;
import com.shangxian.art.bean.HomeadsBean;
import com.shangxian.art.cache.Imageloader_homePager;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.constant.Global;
import com.shangxian.art.net.HttpUtils;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TagViewPager;
import com.shangxian.art.view.TagViewPager.OnGetView;

public class HomeActivity extends BaseActivity implements
		OnHeaderRefreshListener, OnClickListener {
	private AbPullToRefreshView mAbPullToRefreshView = null;
	private ListView mGridView = null;
	private TagViewPager viewPager = null;
	private View headView = null;
	private LinearLayout ll_mainhomehead_add;
	// private MarqueeText tv_tips = null;
	// private TextView tv_more = null;
	private Button btn_check, btn_shop, btn_recorder, btn_sign;
	private View ll_nonetwork, loading_big;

	private HomeGridAdp adp = null;

	/** 首页轮播图地址 */
	private List<String> imgList = new ArrayList<String>();
	/** 首页广告数据 */
	// private List<String> tipsList = new ArrayList<String>();
	/** 首页热门礼品 */
	// private List<GoodBean> goods = new ArrayList<GoodBean>();
	/** 首页数据集合 */
	private HomeData mDatas = new HomeData();

	private AbHttpUtil httpUtil = null;

	/** 动态添加图地址 */
	private List<HomeadsBean> listHomeadsBean = new ArrayList<HomeadsBean>();

	public static final String[] acActions = { "PRODUCT_LIST", // 一种商品类型下的商品列表
			"SHOP_LIST", // 一种商品类型下的商铺列表
			"SHOP", // 一个商铺
			"PRODUCT"// 一个商品
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_home);

		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(Constant.timeOut);

		initviews();
		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		// 设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.setLoadMoreEnable(false);

		initHeadView();

		mGridView.addHeaderView(headView);

		adp = new HomeGridAdp();
		mGridView.setAdapter(adp);

		// 请求轮滑图片
		refreshTask();
		// 请求动态布局的数据
		requestTask();
	}

	private void initviews() {
		mAbPullToRefreshView = (AbPullToRefreshView) this
				.findViewById(R.id.mPullRefreshView);
		// 获取ListView对象
		mGridView = (ListView) this.findViewById(R.id.mListView);
		ll_nonetwork = findViewById(R.id.ll_nonetwork);
		loading_big = findViewById(R.id.loading_big);
	}

	private void requestTask() {
		if (HttpUtils.checkNetWork(this)) {
			AbRequestParams params = new AbRequestParams();
			// params.put("shopid", "1019");
			// params.put("code", "88881110344801123456");
			// params.put("phone", "15889936624");
			String url = Constant.BASEURL + Constant.CONTENT + Constant.HOME;
			httpUtil.get(url, params, new AbStringHttpResponseListener() {
				@Override
				public void onStart() {
				}

				@Override
				public void onFinish() {
					MyLogger.i("onFinish");
					//loading_big.setVisibility(View.GONE);
					mAbPullToRefreshView.onHeaderRefreshFinish();
				}

				@Override
				public void onFailure(int statusCode, String content,
						Throwable error) {
					AbToastUtil.showToast(HomeActivity.this, error.getMessage());
					mGridView.setVisibility(View.GONE);
					ll_nonetwork.setVisibility(View.VISIBLE);
				}

				@Override
				public void onSuccess(int statusCode, String content) {
					// AbToastUtil.showToast(HomeActivity.this, content);
					AbLogUtil.i(HomeActivity.this, content);
					if (!TextUtils.isEmpty(content)) {
						Gson gson = new Gson();
						try {
							JSONObject jsonObject = new JSONObject(content);
							String result_code = jsonObject
									.getString("result_code");
							if (result_code.equals("200")) {
								//mGridView.setVisibility(View.VISIBLE);
								listHomeadsBean.clear();
								JSONArray resultObjectArray = jsonObject
										.getJSONArray("result");
								int length = resultObjectArray.length();
								for (int i = 0; i < length; i++) {
									JSONObject jo = resultObjectArray
											.getJSONObject(i);
									listHomeadsBean.add(gson.fromJson(
											jo.toString(), HomeadsBean.class));
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}catch (Exception e) {
							e.printStackTrace();
						}finally{
							mGridView.setVisibility(View.VISIBLE);
						}
					}

					ll_mainhomehead_add.removeAllViews();
					if (listHomeadsBean.size() > 0) {
						int i = 0;
						List<HomeadsBean> listHomeadsBean_three = new ArrayList<HomeadsBean>();
						for (HomeadsBean homeadsBean : listHomeadsBean) {
							if (homeadsBean.getSingle()) {
								View view = mInflater.inflate(
										R.layout.layout_main_home_item1, null);
								Imageloader_homePager.displayImage(
										Constant.BASEURL
												+ homeadsBean.getImageUrl(),
										(ImageView) view
												.findViewById(R.id.iv_01),
										new Handler(), null);
								ll_mainhomehead_add.addView(view);
								extracted(homeadsBean, view);
							} else {
								i++;
								listHomeadsBean_three.add(homeadsBean);
								if (i % 3 == 0) {
									View view2 = mInflater.inflate(
											R.layout.layout_main_home_item2,
											null);
									for (int j = 0; j < listHomeadsBean_three
											.size(); j++) {
										if (j == 0) {
											ImageView iv = (ImageView) view2
													.findViewById(R.id.iv_01);
											Imageloader_homePager
													.displayImage(
															Constant.BASEURL
																	+ listHomeadsBean_three
																			.get(0)
																			.getImageUrl(),
															iv, new Handler(),
															null);
											extracted(listHomeadsBean_three
													.get(j), iv);
										} else if (j == 1) {
											ImageView iv = (ImageView) view2
													.findViewById(R.id.iv_02);
											Imageloader_homePager
													.displayImage(
															Constant.BASEURL
																	+ listHomeadsBean_three
																			.get(1)
																			.getImageUrl(),
															iv, new Handler(),
															null);
											extracted(listHomeadsBean_three
													.get(j), iv);
										} else if (j == 2) {
											ImageView iv = (ImageView) view2
													.findViewById(R.id.iv_03);
											Imageloader_homePager
													.displayImage(
															Constant.BASEURL
																	+ listHomeadsBean_three
																			.get(2)
																			.getImageUrl(),
															iv, new Handler(),
															null);
											extracted(listHomeadsBean_three
													.get(j), iv);
										}
									}
									ll_mainhomehead_add.addView(view2);
									listHomeadsBean_three.clear();
								}
							}
						}
					}
				}

				// TestBean bean = (TestBean) AbJsonUtil.fromJson(content,
				// TestBean.class);
				// tv_tips.setText(bean.getPname());

				// imgList.clear();
				// // tipsList.clear();
				// // goods.clear();
				//
				// ArrayList<String> imgs = new ArrayList<String>();
				// imgs.add("http://img1.imgtn.bdimg.com/it/u=3784117098,1253514089&fm=21&gp=0.jpg");
				// imgs.add("http://img5.imgtn.bdimg.com/it/u=2421284418,1639597703&fm=15&gp=0.jpg");
				// imgs.add("http://img4.imgtn.bdimg.com/it/u=3412544834,2180569866&fm=15&gp=0.jpg");
				// imgs.add("http://img0.imgtn.bdimg.com/it/u=38005250,935145076&fm=15&gp=0.jpg");
				// // ArrayList<String> tips = new ArrayList<String>();
				// //
				// tips.add("中国通告一中国通告一中国通告一中国通告一中国通告一中国通告一中国通告一中国通告一中国通告一中国通告一");
				// // ArrayList<GoodBean> goodlist = new ArrayList<GoodBean>();
				// // for (int i = 0; i < 3; i++) {
				// // GoodBean good = new GoodBean();
				// // //good.setContent("中国测试产品:" + i);
				// // //good.setPrice(1000 + i + "");
				// //
				// good.setImg("http://b.hiphotos.baidu.com/image/pic/item/ae51f3deb48f8c54cdcbc85a38292df5e0fe7fae.jpg");
				// // goodlist.add(good);
				// // }
				//
				// mDatas.setImgList(imgs);
				// // mDatas.setTipsList(tips);
				// // mDatas.setGoods(goodlist);
				//
				// if (mDatas != null) {
				// if (mDatas.getImgList() != null
				// && mDatas.getImgList().size() > 0) {
				// imgList.addAll(mDatas.getImgList());
				// // viewPager.setVisibility(View.VISIBLE);
				// viewPager.setOnGetView(new OnGetView() {
				//
				// @Override
				// public View getView(ViewGroup container,
				// int position) {
				// ImageView iv = new ImageView(HomeActivity.this);
				// Imageloader_homePager.displayImage(
				// imgList.get(position), iv,
				// new Handler(), null);
				// container.addView(iv);
				// return iv;
				// }
				// });
				// viewPager.setAdapter(imgList.size());
				// } else {
				// // viewPager.setVisibility(View.GONE);
				// }
				//
				// // if (mDatas.getTipsList() != null
				// // && mDatas.getTipsList().size() > 0) {
				// // tipsList.addAll(mDatas.getTipsList());
				// // tv_tips.setVisibility(View.VISIBLE);
				// // tv_tips.setText(tipsList.get(0));
				// // } else {
				// // tv_tips.setVisibility(View.GONE);
				// // }
				//
				// // if (mDatas.getGoods() != null
				// // && mDatas.getGoods().size() > 0) {
				// // goods.addAll(mDatas.getGoods());
				// // adp.updateData(goods);
				// // }
				// }
				//
				// addlayout();

				private void extracted(HomeadsBean homeadsBean, View view) {
					view.setTag(homeadsBean.getAdAction());
					view.setTag(R.id.homeDataUrl, homeadsBean.getDataUrl());
					view.setOnClickListener(HomeActivity.this);
				}
			});
		} else if (listHomeadsBean.size() == 0) {// 如果没有网且没有缓存数据
			mGridView.setVisibility(View.GONE);
			ll_nonetwork.setVisibility(View.VISIBLE);
			mAbPullToRefreshView.onHeaderRefreshFinish();
		} else {// 如果没有网且有缓存数据
			mGridView.setVisibility(View.VISIBLE);
			mAbPullToRefreshView.onHeaderRefreshFinish();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		topView = MainActivity.getTopView();
		topView.setActivity(this);
		topView.showLeftBtn();
		topView.showRightBtn();
		topView.showCenterSearch();
		topView.hideTitle();
		MainActivity activity = (MainActivity) getParent();
		topView.setLeftBtnListener(activity);
		topView.setRightBtnListener(activity);
		topView.setRightBtnDrawable(R.drawable.map);
//		topView.setCenterListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				CommonUtil.gotoActivity(mAc, SearchsActivity.class, false);
//			}
//		});
	}

	private void refreshTask() {
		String url = "http://59.36.101.88:8013/app/shop/warehouse/custuihuosaomiao.asp?";
		AbRequestParams params = new AbRequestParams();
		params.put("shopid", "1019");
		params.put("code", "88881110344801123456");
		params.put("phone", "15889936624");
		httpUtil.get(url, params, new AbStringHttpResponseListener() {

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				// AbDialogUtil.removeDialog(HomeActivity.this);
				// mAbPullToRefreshView.onHeaderRefreshFinish();
			}

			@Override
			public void onFailure(int statusCode, String content,
					Throwable error) {
				// AbToastUtil.showToast(HomeActivity.this, error.getMessage());
				imgList.clear();
				ArrayList<String> imgs = new ArrayList<String>();
				imgs.add("http://img1.imgtn.bdimg.com/it/u=3784117098,1253514089&fm=21&gp=0.jpg");
				mDatas.setImgList(imgs);
				if (mDatas != null) {
					if (mDatas.getImgList() != null
							&& mDatas.getImgList().size() > 0) {
						imgList.addAll(mDatas.getImgList());
						// viewPager.setVisibility(View.VISIBLE);
						viewPager.setOnGetView(new OnGetView() {

							@Override
							public View getView(ViewGroup container,
									int position) {
								ImageView iv = new ImageView(HomeActivity.this);
								Imageloader_homePager.displayImage(
										imgList.get(position), iv,
										new Handler(), null);
								container.addView(iv);
								return iv;
							}
						});
						viewPager.setAdapter(imgList.size());
					}
				}
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				// AbToastUtil.showToast(HomeActivity.this, content);
				AbLogUtil.i(HomeActivity.this, content);
				// TestBean bean = (TestBean) AbJsonUtil.fromJson(content,
				// TestBean.class);
				// tv_tips.setText(bean.getPname());

				imgList.clear();
				// tipsList.clear();
				// goods.clear();

				ArrayList<String> imgs = new ArrayList<String>();
				imgs.add("http://img1.imgtn.bdimg.com/it/u=3784117098,1253514089&fm=21&gp=0.jpg");
				imgs.add("http://img5.imgtn.bdimg.com/it/u=2421284418,1639597703&fm=15&gp=0.jpg");
				imgs.add("http://img4.imgtn.bdimg.com/it/u=3412544834,2180569866&fm=15&gp=0.jpg");
				imgs.add("http://img0.imgtn.bdimg.com/it/u=38005250,935145076&fm=15&gp=0.jpg");
				// ArrayList<String> tips = new ArrayList<String>();
				// tips.add("中国通告一中国通告一中国通告一中国通告一中国通告一中国通告一中国通告一中国通告一中国通告一中国通告一");
				// ArrayList<GoodBean> goodlist = new ArrayList<GoodBean>();
				// for (int i = 0; i < 3; i++) {
				// GoodBean good = new GoodBean();
				// //good.setContent("中国测试产品:" + i);
				// //good.setPrice(1000 + i + "");
				// good.setImg("http://b.hiphotos.baidu.com/image/pic/item/ae51f3deb48f8c54cdcbc85a38292df5e0fe7fae.jpg");
				// goodlist.add(good);
				// }

				mDatas.setImgList(imgs);
				// mDatas.setTipsList(tips);
				// mDatas.setGoods(goodlist);

				if (mDatas != null) {
					if (mDatas.getImgList() != null
							&& mDatas.getImgList().size() > 0) {
						imgList.addAll(mDatas.getImgList());
						// viewPager.setVisibility(View.VISIBLE);
						viewPager.setOnGetView(new OnGetView() {

							@Override
							public View getView(ViewGroup container,
									int position) {
								ImageView iv = new ImageView(HomeActivity.this);
								Imageloader_homePager.displayImage(
										imgList.get(position), iv,
										new Handler(), null);
								container.addView(iv);
								return iv;
							}
						});
						viewPager.setAdapter(imgList.size());
					} else {
						// viewPager.setVisibility(View.GONE);
					}

					// if (mDatas.getTipsList() != null
					// && mDatas.getTipsList().size() > 0) {
					// tipsList.addAll(mDatas.getTipsList());
					// tv_tips.setVisibility(View.VISIBLE);
					// tv_tips.setText(tipsList.get(0));
					// } else {
					// tv_tips.setVisibility(View.GONE);
					// }

					// if (mDatas.getGoods() != null
					// && mDatas.getGoods().size() > 0) {
					// goods.addAll(mDatas.getGoods());
					// adp.updateData(goods);
					// }
				}

				// addlayout();
			}
		});
	}

	/** 初始化头部VIEW */
	private void initHeadView() {
		headView = LayoutInflater.from(this).inflate(
				R.layout.layout_main_home_gridview_head, null);
		viewPager = (TagViewPager) headView.findViewById(R.id.mTagViewPager);
		ll_mainhomehead_add = (LinearLayout) headView
				.findViewById(R.id.ll_mainhomehead_add);
		initTagViewPager();
		// tv_tips = (MarqueeText) headView.findViewById(R.id.tv_tips);
		// tv_more = (TextView) headView.findViewById(R.id.tv_more);
		// btn_check = (Button) headView.findViewById(R.id.btn_check);
		// btn_shop = (Button) headView.findViewById(R.id.btn_shop);
		// btn_recorder = (Button) headView.findViewById(R.id.btn_recorder);
		// btn_sign = (Button) headView.findViewById(R.id.btn_sign);
		//
		// //tv_more.setOnClickListener(new OnButtonClick(OnButtonClick.MORE));
		// btn_check.setOnClickListener(new OnButtonClick(OnButtonClick.CHECK));
		// btn_shop.setOnClickListener(new OnButtonClick(OnButtonClick.SHOP));
		// btn_recorder.setOnClickListener(new OnButtonClick(
		// OnButtonClick.RECORDER));
		// btn_sign.setOnClickListener(new OnButtonClick(OnButtonClick.SIGN));
	}

	/** 初始化广告轮播VIEW */
	private void initTagViewPager() {
		viewPager.init(R.drawable.tagvewpager_point01,
				R.drawable.tagvewpager_point02, 14, 5, 2, 20);
		viewPager.setAutoNext(true, 5000);
		AbLogUtil.i(
				this,
				"轮播图宽度"
						+ AbSharedUtil.getInt(HomeActivity.this,
								Global.KEY_SCREEN_WIDTH));

		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewPager
				.getLayoutParams();
		params.width = AbSharedUtil.getInt(HomeActivity.this,
				Global.KEY_SCREEN_WIDTH);
		params.height = (int) (params.width / 2.65);
		viewPager.setLayoutParams(params);
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView arg0) {
		// refreshTask();
		requestTask();
	}

	/** 首页按钮点击事件 */
	// class OnButtonClick implements OnClickListener {
	// public static final int CHECK = 1;
	// public static final int SHOP = 2;
	// public static final int RECORDER = 3;
	// public static final int SIGN = 4;
	// public static final int MORE = 5;
	//
	// int mSwitch;
	//
	// public OnButtonClick(int mSwitch) {
	// this.mSwitch = mSwitch;
	// }
	//
	// @Override
	// public void onClick(View v) {
	// switch (mSwitch) {
	// case CHECK:
	// if (AbSharedUtil.getString(Global.mContext, Global.KEY_ACCOUNT) != null)
	// {
	// AbToastUtil.showToast(HomeActivity.this, "积分查询");
	// } else {
	// AbToastUtil.showToast(HomeActivity.this, "绑定帐号");
	// }
	// break;
	//
	// case SHOP:
	// AbToastUtil.showToast(HomeActivity.this, "积分商城");
	// break;
	//
	// case RECORDER:
	// AbToastUtil.showToast(HomeActivity.this, "兑换记录");
	// break;
	//
	// case SIGN:
	// AbToastUtil.showToast(HomeActivity.this, "赚恩宝");
	// break;
	//
	// case MORE:
	// AbToastUtil.showToast(HomeActivity.this, "热兑好礼");
	// break;
	// }
	// }
	//
	// }
	@Override
	public void onClick(View v) {
		String tag = (String) v.getTag();
		String dataurl = (String) v.getTag(R.id.homeDataUrl);
		if (!TextUtils.isEmpty(tag)) {
			switch (tag) {
			case "PRODUCT_LIST":// 一种商品类型下的商品列表
				ClassifyCommodityActivity.startThisActivity_url(dataurl,
						HomeActivity.this);
				break;
			case "SHOP_LIST":// 一种商品类型下的商铺列表
				// CommonUtil.gotoActivity(HomeActivity.this,
				// ShopsListActivity.class, false);
				ShopsListActivity.startThisActivity_url(dataurl,
						HomeActivity.this);
				break;
			case "SHOP":// 一个商铺
				ShopsActivity.startThisActivity_url(dataurl, HomeActivity.this);
				break;
			case "PRODUCT":// 一个商品
				CommodityContentActivity.startThisActivity_url(dataurl,
						HomeActivity.this);
				break;
			default:
				break;
			}
			MyLogger.i(v.getTag() + "--" + dataurl);
		}

		switch (v.getId()) {
		case R.id.iv_reload:
			mGridView.setVisibility(View.GONE);
			ll_nonetwork.setVisibility(View.GONE);
			loading_big.setVisibility(View.VISIBLE);
			requestTask();
			break;
		default:
			break;
		}
	}

}
