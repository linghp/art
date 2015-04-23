package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.tencent.qzone.QZone;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.image.AbImageLoader;
import com.ab.util.AbLogUtil;
import com.ab.util.AbSharedUtil;
import com.google.gson.Gson;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CommodityContentModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.constant.Global;
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
OnClickListener, HttpCilentListener {
	private TagViewPager viewPager = null;
	/** 轮播图地址 */
	private List<String> imgList = new ArrayList<String>();

	private ImageView dingwei,call,next,shopsimg;
	private ImageView commoditycontent_shoucang;
	private TextView commoditycontent_jieshao, commoditycontent_jiage,
	commoditycontent_jiarugouwuche,address,guige,dianpu;

	// 判断是否收藏
	boolean iscollection = false;

	private AbHttpUtil httpUtil = null;
	private CommodityContentModel model;
	private String shopid;

	private AbImageLoader mAbImageLoader = null;

	RatingBar ratingbar;
	float rating = 0;
	//	private StarRatingView ratingbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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

	private void listener() {
		commoditycontent_jiarugouwuche.setOnClickListener(this);
		next.setOnClickListener(new OnClickListener() {
			//跳转到商铺
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				CommonUtil.gotoActivity(CommodityContentActivity.this,
				//						ShopsActivity.class, false);
				ShopsActivity.startThisActivity(shopid, CommodityContentActivity.this);
			}
		});
		dingwei.setOnClickListener(new OnClickListener() {
			//跳转到定位
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putSerializable(Constant.INT_SHOPS_2_LOC, model);
				bundle.putInt(Constant.INT_LOC_TOTYPE, Constant.MAP_SHOPS_2_LOC);
				CommonUtil.gotoActivityWithData(CommodityContentActivity.this, LocationActivity.class, bundle, false);
				
			}
		});
		call.setOnClickListener(new OnClickListener() {
			//跳转到打电话
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+"18696636812"));
				startActivity(intent);
			}
		});
		commoditycontent_shoucang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isLoginAndToLogin()) {
					if (!iscollection) {
						commoditycontent_shoucang
						.setImageResource(R.drawable.collection_on);
						myToast("已收藏");
					} else {
						commoditycontent_shoucang
						.setImageResource(R.drawable.collection_off);
						myToast("取消收藏");
					}
					iscollection = !iscollection;
				}
			}
		});
		topView.setRightBtnListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putBoolean("isother", true);
				CommonUtil.gotoActivityWithDataForResult(CommodityContentActivity.this, ShoppingcartActivity.class, bundle, 10086, false);
			}
		});
		
		

	}
	String geturl;
	private void initData() {
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(Constant.timeOut);
		String id = getIntent().getStringExtra("id");
		geturl = getIntent().getStringExtra("url");
		//		if (TextUtils.isEmpty(geturl)) {
		//			// 获取网页数据test
		//			Uri uri = getIntent().getData();
		//			geturl = uri.getQueryParameter("arg0");
		//		}
		String url = "";
		if (TextUtils.isEmpty(geturl)) {
			url = Constant.BASEURL + Constant.CONTENT + "/product" + "/" + id;
		} else {
			url = Constant.BASEURL + Constant.CONTENT + geturl;
		}
		refreshTask(url);

	}

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
				//				imgList.clear();
				AbLogUtil.i(CommodityContentActivity.this, content);
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
							//							MyLogger.i(model.toString());
							if (model != null){
								shopid=model.getShopId()+"";
								updateView();
							}

							imgList.addAll(model.getPhotos());
							MyLogger.i(imgList.get(0));
							//							viewPager.setVisibility(View.VISIBLE);
							viewPager.setOnGetView(new OnGetView() {

								@Override
								public View getView(ViewGroup container, int position) {
									ImageView iv = new ImageView(CommodityContentActivity.this);

									iv.setScaleType(ImageView.ScaleType.FIT_XY); 
									LayoutParams layoutParams=viewPager.getLayoutParams();
									layoutParams.width=CommonUtil.getScreenWidth(CommodityContentActivity.this);
									layoutParams.height=layoutParams.width*2/3;
									iv.setLayoutParams(layoutParams);

									//图片的下载
									mAbImageLoader.display(iv,Constant.BASEURL+imgList.get(position));
									container.addView(iv);
									return iv;

								}
							});	
							viewPager.setAdapter(imgList.size());

						}
						//						else {
						//							viewPager.setVisibility(View.GONE);
						//						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
	}

	private void updateView() {
		//		Imageloader_homePager.displayImage(Constant.BASEURL
		//				+ model.getPhotos().get(0), commoditycontent_img,
		//				new Handler(), null);// TODO Auto-generated method stub
		commoditycontent_jieshao.setText(model.getName());
		commoditycontent_jiage.setText("￥" + model.getPromotionPrice());
//		guige.setText(model.get);//规格
		dianpu.setText(model.getShopName());
//		address.setText(model.get);//地址
		//图片的下载
		mAbImageLoader.display(shopsimg,Constant.BASEURL+model.getShopLogo());
		//		System.out.println("**************"+(float)model.getEvaluateScore());
		//		System.out.println("**************"+ (float) ((float)model.getEvaluateScore()/100*5));
		rating = (float) ((float)model.getEvaluateScore()/100*5);
		//设置评星星级
		ratingbar.setRating(rating);
	}

	private void initView() {
		mAbImageLoader = AbImageLoader.newInstance(mAc);
		mAbImageLoader.setLoadingImage(R.drawable.image_loading);
		mAbImageLoader.setErrorImage(R.drawable.image_error);
		mAbImageLoader.setEmptyImage(R.drawable.image_empty);
		//		commoditycontent_img = (ImageView) findViewById(R.id.commoditycontent_img);
		viewPager = (TagViewPager) findViewById(R.id.commoditycontent_mTagViewPager);
		initTagViewPager();//初始化轮播VIEW

		 commoditycontent_shoucang = (ImageView) findViewById(R.id.commoditycontent_shoucang);
		 commoditycontent_jieshao = (TextView) findViewById(R.id.commoditycontent_jieshao);
		 commoditycontent_jiage = (TextView) findViewById(R.id.commoditycontent_jiage);
		 commoditycontent_jiarugouwuche = (TextView) findViewById(R.id.commoditycontent_jiarugouwuche);

		 ratingbar = (RatingBar) findViewById(R.id.commoditycontent_starRating);

		 guige = (TextView) findViewById(R.id.commoditycontent_guige);
		 dianpu = (TextView) findViewById(R.id.commoditycontent_shopstxt);
		 shopsimg = (ImageView) findViewById(R.id.commoditycontent_dianpuimg);
		 dingwei = (ImageView) findViewById(R.id.commoditycontent_dingwei);
		 call = (ImageView) findViewById(R.id.commoditycontent_call);
		 next = (ImageView) findViewById(R.id.commoditycontent_next4);
		 address = (TextView) findViewById(R.id.commoditycontent_address);

		 topView = (TopView) findViewById(R.id.top_title);
		 topView.setActivity(this);
		 topView.hideRightBtn_invisible();
		 topView.hideCenterSearch();
		 topView.showRightBtn();
		 topView.setRightBtnDrawable(R.drawable.shopcart);
		 topView.showTitle();
		 topView.setBack(R.drawable.back);// 返回
		 topView.setTitle("商品详情");// title文字

	}

	/** 初始化轮播VIEW */
	private void initTagViewPager() {
		viewPager.init(R.drawable.tagvewpager_point01,
				R.drawable.tagvewpager_point02, 14, 5, 2, 20);
		viewPager.setAutoNext(true, 5000);
		//		AbLogUtil.e(
		//				this,
		//				"轮播图宽度"
		//						+ AbSharedUtil.getInt(CommodityContentActivity.this,
		//								Global.KEY_SCREEN_WIDTH));

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
		case R.id.commoditycontent_jiarugouwuche:
			if (isLoginAndToLogin()) {
				dotask_addcart();
			}
			break;
		case R.id.tv_share:
			showShare();
			break;

		default:
			break;
		}
	}

	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://www.peoit.com/");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("test");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://www.peoit.com/");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://www.peoit.com/");
		//oks.setImageUrl("http://www.peoit.com/");
		// 启动分享GUI
		oks.show(this);
	}

	/**
	 * 快捷分享项目现在添加为不同的平台添加不同分享内容的方法。
	 *本类用于演示如何区别Twitter的分享内容和其他平台分享内容。
	 */
	public class ShareContentCustomizeDemo implements ShareContentCustomizeCallback {

		public void onShare(Platform platform, ShareParams paramsToShare) {
			if (QZone.NAME.equals(platform.getName())) {
				//String text = platform.getContext().getString(R.string.share_content_short);
				paramsToShare.setImageUrl("http://www.peoit.com/");
			}
		}

	}

	private void dotask_addcart() {
		String json = "{\"productId\":" + model.getId()
				+ ",\"sepcs\":\"颜色:红\",\"buyCount\":2}";
		// MyLogger.i(json);
		HttpClients.postDo(Constant.BASEURL + Constant.CONTENT + Constant.CART,
				json, this);
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
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
