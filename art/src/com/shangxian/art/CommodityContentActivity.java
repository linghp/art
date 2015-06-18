package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.tencent.qzone.QZone;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.image.AbImageLoader;
import com.ab.util.AbSharedUtil;
import com.google.gson.Gson;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CommodityContentModel;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.bean.ListCarStoreBean;
import com.shangxian.art.bean.ShopLocInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.constant.Global;
import com.shangxian.art.dialog.GoodsDialog;
import com.shangxian.art.dialog.GoodsDialog.GoodsDialogConfirmListener;
import com.shangxian.art.dialog.GoodsDialog.GoodsDialogConfirmNowBuyListener;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.FollowServer;
import com.shangxian.art.net.FollowServer.OnFollowListener;
import com.shangxian.art.net.HttpClients;
import com.shangxian.art.net.HttpClients.HttpCilentListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TagViewPager;
import com.shangxian.art.view.TagViewPager.OnGetView;
import com.shangxian.art.view.TopView;

/**
 * 商品详情
 * 
 * @author Administrator
 *
 */
public class CommodityContentActivity extends BaseActivity implements
OnClickListener, HttpCilentListener, GoodsDialogConfirmListener ,GoodsDialogConfirmNowBuyListener{
	private TagViewPager viewPager = null;
	/** 轮播图地址 */
	private List<String> imgList = new ArrayList<String>();

	private ImageView call, next, shopsimg,img_dingwei;
	private ImageView commoditycontent_shoucang;
	private TextView commoditycontent_jieshao, commoditycontent_jiage,address, guige, dianpu,tt_dianhua;
	private View rl_footer;
	//	private TextView tv_first, tv_second;
	//	private ImageView img_first, img_second;
	private LinearLayout dingwei, shangpu, ll_guige, ll_dianhua;
	private WebView webView;
	// 判断是否收藏
	//boolean iscollection = false;

	private AbHttpUtil httpUtil;
	private CommodityContentModel model;
	private String shopid,phonenum=null;

	private AbImageLoader mAbImageLoader;

	RatingBar ratingbar;
	float rating = 0;

	// private StarRatingView ratingbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commoditycontent);
		initView();
		initData();
		listener();
	}

	public static void startThisActivity(String id, Context context) {
		Intent intent = new Intent(context, CommodityContentActivity.class);
		intent.putExtra("id", id);
		context.startActivity(intent);
	}

	public static void startThisActivity_url(String url, Context context) {
		Intent intent = new Intent(context, CommodityContentActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}


	String geturl;

	private void initData() {
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(Constant.timeOut);
		String id = getIntent().getStringExtra("id");
		geturl = getIntent().getStringExtra("url");
		// if (TextUtils.isEmpty(geturl)) {
		// // 获取网页数据test
		// Uri uri = getIntent().getData();
		// geturl = uri.getQueryParameter("arg0");
		// }
		String url = "";
		if (TextUtils.isEmpty(geturl)) {
			url = Constant.BASEURL + Constant.CONTENT + Constant.PRODUCT + "/" + id;
		} else {
			String[] geturls=geturl.split("/");
			geturl=geturls[geturls.length-1];
			url = Constant.BASEURL + Constant.CONTENT +Constant.PRODUCT+"/"+ geturl;
		}
		MyLogger.i("商品详情url：" + url);
		refreshTask(url);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}
		});
	}

	//	private void refreshTask_detail(String url) {
	//		// AbRequestParams params = new AbRequestParams();
	//		// params.put("shopid", "1019");
	//		// params.put("code", "88881110344801123456");
	//		// params.put("phone", "15889936624");
	//		httpUtil.get(url, new AbStringHttpResponseListener() {
	//
	//			@Override
	//			public void onStart() {
	//				MyLogger.i("");
	//			}
	//
	//			@Override
	//			public void onFinish() {
	//				MyLogger.i("");
	//				// AbDialogUtil.removeDialog(HomeActivity.this);
	//				// mAbPullToRefreshView.onHeaderRefreshFinish();
	//			}
	//
	//			@Override
	//			public void onFailure(int statusCode, String content,
	//					Throwable error) {
	//				MyLogger.i(content);
	//			}
	//
	//			@Override
	//			public void onSuccess(int statusCode, String content) {
	//				// AbToastUtil.showToast(HomeActivity.this, content);
	//				// imgList.clear();
	//				MyLogger.i(content);
	//				if (!TextUtils.isEmpty(content)) {
	//					webView.loadDataWithBaseURL(null,content,"text/html","utf-8", null);
	//				}
	//
	//			}
	//		});
	//	}


	private void refreshTask(String url) {
		// AbRequestParams params = new AbRequestParams();
		// params.put("shopid", "1019");
		// params.put("code", "88881110344801123456");
		// params.put("phone", "15889936624");
		httpUtil.get(url, new AbStringHttpResponseListener() {

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
				myToast("加载失败，请重试");
				// AbToastUtil.showToast(HomeActivity.this, error.getMessage());
				// imgList.clear();
				// ArrayList<String> imgs = new ArrayList<String>();
				// imgs.add("http://img1.imgtn.bdimg.com/it/u=3784117098,1253514089&fm=21&gp=0.jpg");
				// mDatas.setImgList(imgs);
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
				// }
				//
				// }

			}

			@Override
			public void onSuccess(int statusCode, String content) {
				// AbToastUtil.showToast(HomeActivity.this, content);
				// imgList.clear();
				MyLogger.i("商品详情数据:"+content);
				if (!TextUtils.isEmpty(content)) {
					Gson gson = new Gson();
					try {
						JSONObject jsonObject = new JSONObject(content);
						String result_code = jsonObject
								.getString("result_code");
						if (result_code.equals("200")) {
							JSONObject jsonObject1 = jsonObject
									.getJSONObject("result");
							model = gson.fromJson(jsonObject1.toString(),
									CommodityContentModel.class);
							// MyLogger.i(model.toString());
							if (model != null) {
								shopid = model.getShopId() + "";
//								phonenum = model.getShopPhoneNumber();
								updateView();
							}

							imgList.addAll(model.getPhotos());
							// MyLogger.i(imgList.get(0));
							// viewPager.setVisibility(View.VISIBLE);
							viewPager.setOnGetView(new OnGetView() {

								@Override
								public View getView(ViewGroup container,
									final	int position) {
									ImageView iv = new ImageView(
											CommodityContentActivity.this);
									//iv.setTag(position);
									iv.setOnClickListener(new OnClickListener() {
										
										@Override
										public void onClick(View v) {
											if(imgList.size()>0)
												PhotoViewPagerActivity.startThisActivity(CommodityContentActivity.this, imgList,position);
										}
									});
									iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
									LayoutParams layoutParams = viewPager
											.getLayoutParams();
									layoutParams.width = CommonUtil
											.getScreenWidth(CommodityContentActivity.this);
									layoutParams.height = layoutParams.width * 2 / 3;
									iv.setLayoutParams(layoutParams);

									// 图片的下载
									mAbImageLoader.display(iv, Constant.BASEURL
											+ imgList.get(position));
									container.addView(iv);
									return iv;

								}
							});
							viewPager.setAdapter(imgList.size());
							// 商品详情
							String url_detail = Constant.BASEURL
									+ Constant.CONTENT
									+ String.format(Constant.GOODSDETAIL,
											model.getId());
							MyLogger.i(url_detail);
							// refreshTask_detail(url_detail);
							webView.loadUrl(url_detail);
						}
						// else {
						// viewPager.setVisibility(View.GONE);
						// }
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
	}

	private void updateView() {
		// Imageloader_homePager.displayImage(Constant.BASEURL
		// + model.getPhotos().get(0), commoditycontent_img,
		// new Handler(), null);// TODO Auto-generated method stub
		rl_footer.setVisibility(View.VISIBLE);
		commoditycontent_jieshao.setText(model.getName().toString().trim());
		commoditycontent_jiage.setText("￥"
				+ CommonUtil.priceConversion(model.getPromotionPrice()));
		//		 guige.setText(model.getSpecs().get("房间"));//规格
		dianpu.setText(model.getShopName());
		address.setText(model.getShopAddress());// 地址
		tt_dianhua.setText(model.getShopPhoneNumber());//电话
		// 图片的下载
		mAbImageLoader
		.display(shopsimg, Constant.BASEURL + model.getShopLogo());
		// System.out.println("**************"+(float)model.getEvaluateScore());
		// System.out.println("**************"+ (float)
		// ((float)model.getEvaluateScore()/100*5));
		rating = (float) ((float) model.getEvaluateScore() / 100 * 5);
		commoditycontent_shoucang.setSelected(model.getAttened());
		// 设置评星星级
		ratingbar.setRating(rating);
	}

	private void initView() {
		mAbImageLoader = AbImageLoader.newInstance(mAc);
		mAbImageLoader.setLoadingImage(R.drawable.image_loading);
		mAbImageLoader.setErrorImage(R.drawable.image_error);
		mAbImageLoader.setEmptyImage(R.drawable.image_empty);
		// commoditycontent_img = (ImageView)
		// findViewById(R.id.commoditycontent_img);
		viewPager = (TagViewPager) findViewById(R.id.commoditycontent_mTagViewPager);
		initTagViewPager();// 初始化轮播VIEW

		rl_footer = findViewById(R.id.rl_footer);
		rl_footer.setVisibility(View.GONE);
		commoditycontent_shoucang = (ImageView) findViewById(R.id.commoditycontent_shoucang);
		commoditycontent_jieshao = (TextView) findViewById(R.id.commoditycontent_jieshao);
		commoditycontent_jiage = (TextView) findViewById(R.id.commoditycontent_jiage);

		ratingbar = (RatingBar) findViewById(R.id.commoditycontent_starRating);

		guige = (TextView) findViewById(R.id.commoditycontent_guige);
		dianpu = (TextView) findViewById(R.id.commoditycontent_shopstxt);
		shopsimg = (ImageView) findViewById(R.id.commoditycontent_dianpuimg);
		dingwei = (LinearLayout) findViewById(R.id.commoditycontent_dingwei);
		img_dingwei = (ImageView) findViewById(R.id.commoditycontent_img1);
		tt_dianhua = (TextView) findViewById(R.id.commoditycontent_txt1);
		ll_guige = (LinearLayout) findViewById(R.id.commoditycontent_guigelinear);
		ll_dianhua =(LinearLayout) findViewById(R.id.commoditycontent_linear1);
		call = (ImageView) findViewById(R.id.commoditycontent_call);
		next = (ImageView) findViewById(R.id.commoditycontent_next4);
		address = (TextView) findViewById(R.id.commoditycontent_address);
		shangpu = (LinearLayout) findViewById(R.id.commoditycontent_shangpu);
		webView = (WebView) findViewById(R.id.webView);
		//		tv_first = (TextView) findViewById(R.id.text_one);
		//		tv_second = (TextView) findViewById(R.id.text_two);
		//		img_first = (ImageView) findViewById(R.id.image_one);
		//		img_second = (ImageView) findViewById(R.id.image_two);

		webView.getSettings().setJavaScriptEnabled(true);

		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showRightBtn();
		topView.setRightBtnDrawable(R.drawable.shopcart);
		topView.showTitle();
		topView.setBack(R.drawable.back);// 返回
		topView.setTitle("商品详情");// title文字

		if (!isLogin())
			commoditycontent_shoucang.setSelected(false);

	}

	/** 初始化轮播VIEW */
	private void initTagViewPager() {
		viewPager.init(R.drawable.tagvewpager_point01,
				R.drawable.tagvewpager_point02, 14, 5, 2, 20);
		viewPager.setAutoNext(true, 5000);
		// AbLogUtil.e(
		// this,
		// "轮播图宽度"
		// + AbSharedUtil.getInt(CommodityContentActivity.this,
		// Global.KEY_SCREEN_WIDTH));

		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewPager
				.getLayoutParams();
		params.width = AbSharedUtil.getInt(CommodityContentActivity.this,
				Global.KEY_SCREEN_WIDTH);
		params.height = (int) (params.width / 2.65);
		viewPager.setLayoutParams(params);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.commoditycontent_jiarugouwuche://加入购物车
			if (isLoginAndToLogin()) {
				dotask_addcart();
			}
			break;
		case R.id.tv_nowbuy://加入购物车（立即购买）
			if (isLoginAndToLogin()) {
				doNowBuy();
			}
			break;
		case R.id.tv_share:
			CommonUtil.showShare(this, "test", "http://www.peoit.com/",
					"http://www.peoit.com/");
			break;
		case R.id.ll_comment:
			CommentActivity.startThisActivity("", this);
			break;
		case R.id.commoditycontent_dingwei:
			Bundle bundle = new Bundle();
			bundle.putInt(Constant.INT_LOC_TOTYPE, Constant.MAP_SHOPS_2_LOC);
			ShopLocInfo info = new ShopLocInfo();
			info.setId(model.getShopId());
			info.setTitle(model.getName());
			info.setPhoto(model.getPhotos() != null ? model.getPhotos().get(0) : null);
			info.setAddress(model.getShopAddress());
			info.setLng(model.getLat());
			bundle.putSerializable(Constant.INT_SHOPS_2_LOC, info);
			CommonUtil.gotoActivityWithData(mAc,
					LocationActivity.class, bundle, false);
			break;
		default:
			break;
		}
	}
	private void listener() {
		//		tv_first.setOnClickListener(this);
		//		tv_second.setOnClickListener(this);
		next.setOnClickListener(new OnClickListener() {
			// 跳转到商铺
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// CommonUtil.gotoActivity(CommodityContentActivity.this,
				// ShopsActivity.class, false);
				ShopsActivity.startThisActivity(shopid,
						CommodityContentActivity.this);
			}
		});
		ll_guige.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//规格
				select_specifications();
			}
		});
		shangpu.setOnClickListener(new OnClickListener() {
			//商铺
			@Override
			public void onClick(View v) {

				ShopsActivity.startThisActivity(shopid,
						CommodityContentActivity.this);
			}
		});
//		img_dingwei.setOnClickListener(new OnClickListener() {
//			// 跳转到定位
//			@Override
//			public void onClick(View v) {
//				if (/*model == null*/true) {
//					return;
//				}
//				Bundle bundle = new Bundle();
//				bundle.putInt(Constant.INT_LOC_TOTYPE, Constant.MAP_SHOPS_2_LOC);
//				ShopLocInfo info = new ShopLocInfo();
//				info.setId(model.getShopId()); 
//				info.setTitle(model.getShopName());
//				info.setPhoto(model.getShopLogo());
//				info.setAddress(model.getShopAddress());
//				//info.setLng(model.get);
//				bundle.putSerializable(Constant.INT_SHOPS_2_LOC, info);
//				CommonUtil.gotoActivityWithData(CommodityContentActivity.this,
//						LocationActivity.class, bundle, false);
//
//			}
//		});
//		img_dingwei.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if (/*model == null*/true) {
//					return;
//				}
//				
//				Bundle bundle1 = new Bundle(); 
//				bundle1.putInt(Constant.INT_LOC_TOTYPE, Constant.MAP_SHOPS_2_LOC);
//				ShopLocInfo info1 = new ShopLocInfo();
//				info1.setId(model.getShopId());
//				info1.setTitle(model.getShopName());
//				info1.setPhoto(model.getShopLogo());
//				info1.setAddress(model.getShopAddress());
//				//info.setLng(model.get);
//				bundle1.putSerializable(Constant.INT_SHOPS_2_LOC, info1);
//				CommonUtil.gotoActivityWithData(CommodityContentActivity.this,
//						LocationActivity.class, bundle1, false);
//				
//			}
//		});
		ll_dianhua.setOnClickListener(new OnClickListener() {
			// 跳转到打电话
			@Override
			public void onClick(View v) {
				phonenum = tt_dianhua.getText().toString().trim();
				//拨打电话前可编辑
				/*Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
						+ "18696636812"));*/
				//不编辑直接拨打电话
				if (phonenum != "") {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
							+ phonenum));
					startActivity(intent);
				}
			}
		});
		commoditycontent_shoucang.setOnClickListener(new OnClickListener() {
			// 收藏
			@Override
			public void onClick(View v) {
				if (isLoginAndToLogin()) {
					if (model == null) {
						commoditycontent_shoucang.setSelected(false);
						myToast("关注失败！");
					} else {
						commoditycontent_shoucang
						.setSelected(!commoditycontent_shoucang
								.isSelected());
						if (!commoditycontent_shoucang.isSelected()) {
							new FollowServer().toDelFollowGoods(
									model.getCategoryId(), false,
									new CallBack() {
										@Override
										public void onSimpleSuccess(Object res) {
											myToast("取消关注成功");
										}

										@Override
										public void onSimpleFailure(int code) {
											myToast("取消关注失败");
											commoditycontent_shoucang
											.setSelected(!commoditycontent_shoucang
													.isSelected());
										}
									});
						} else {
							new FollowServer().toFollowGoods(
									model.getCategoryId(),
									new OnFollowListener() {
										@Override
										public void onFollow(boolean isFollow) {
											if (!isFollow) {
												myToast("关注失败");
												commoditycontent_shoucang
												.setSelected(!commoditycontent_shoucang
														.isSelected());
											} else {
												myToast("关注成功");
											}
										}
									});
						}
					}
				}
			}
		});
		topView.setRightBtnListener(new OnClickListener() {
			// title右按钮（购物车） 跳转到购物车
			@Override
			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Bundle bundle = new Bundle();
//				bundle.putBoolean("isother", true);
//				CommonUtil.gotoActivityWithData(CommodityContentActivity.this,
//						ShoppingcartActivity.class, bundle, false);
				ShoppingcartActivity.startThisActivity(true, CommodityContentActivity.this);
			}
		});

	}

	//	private void setBackground_slide(int position) {
	//		img_first.setBackgroundResource(R.color.transparent);
	//		img_second.setBackgroundResource(R.color.transparent);
	//
	//		tv_first.setTextColor(Color.parseColor("#333333"));
	//		tv_second.setTextColor(Color.parseColor("#333333"));
	//
	//		switch (position) {
	//		case 0:
	//			img_first.setBackgroundResource(R.color.blue);
	//			tv_first.setTextColor(getResources().getColor(R.color.blue));
	//			webView.loadUrl("http://baidu.com");
	//			break;
	//		case 1:
	//			img_second.setBackgroundResource(R.color.blue);
	//			tv_second.setTextColor(getResources().getColor(R.color.blue));
	//			webView.loadUrl("http://www.taobao.com/");
	//			break;
	//		}
	//	}

	/**
	 * 快捷分享项目现在添加为不同的平台添加不同分享内容的方法。 本类用于演示如何区别Twitter的分享内容和其他平台分享内容。
	 */
	public class ShareContentCustomizeDemo implements
	ShareContentCustomizeCallback {

		public void onShare(Platform platform, ShareParams paramsToShare) {
			if (QZone.NAME.equals(platform.getName())) {
				// String text =
				// platform.getContext().getString(R.string.share_content_short);
				paramsToShare.setImageUrl("http://www.peoit.com/");
			}
		}

	}

	private void dotask_addcart() {
		//加入购物车
		GoodsDialog dialog = new GoodsDialog(this, this);
		dialog.setCommodityContent(model);
		dialog.show();
	}

	private void doNowBuy() {
		//立即购买
		GoodsDialog dialog = new GoodsDialog(this, this,true);
		dialog.setCommodityContent(model);
		dialog.show();
	}
	private void select_specifications() {
		//选择规格
		GoodsDialog dialog = new GoodsDialog(this, 0);
		dialog.setCommodityContent(model);
		dialog.show();
	}

	@Override
	public void onResponse(String content) {
		MyLogger.i(content);
		if (!TextUtils.isEmpty(content)) {
			try {
				JSONObject jsonObject = new JSONObject(content);
				String result_code = jsonObject.getString("result_code");
				if (result_code.equals("200")) {
					myToast(jsonObject.getString("result"));
				}else{
					myToast("加入购物车失败");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			myToast("加入购物车失败");
		}

	}

	@Override
	public void goodsDialogConfirm(String json) {
		HttpClients.postDo(Constant.BASEURL + Constant.CONTENT + Constant.CART,
				json, this);
	}

	@Override
	public void goodsDialogConfirmNowBuy(ListCarGoodsBean listCarGoodsBean) {
		List<ListCarStoreBean> listCarStoreBeans = new ArrayList<ListCarStoreBean>();
		ListCarStoreBean listCarStoreBean=new ListCarStoreBean();
		listCarStoreBean.setLogo(model.getShopLogo());
		listCarStoreBean.setShopId(model.getShopId());
		listCarStoreBean.setShopName(model.getShopName());
		List<ListCarGoodsBean> listCarGoodsBeans=new ArrayList<ListCarGoodsBean>();
		listCarGoodsBeans.add(listCarGoodsBean);
		listCarStoreBean.setItemDtos(listCarGoodsBeans);
		listCarStoreBeans.add(listCarStoreBean);

		ConfirmOrderActivity.startThisActivity(this, listCarStoreBeans, listCarGoodsBean.getPromotionPrice()*listCarGoodsBean.getQuantity(), true);
	}
}
