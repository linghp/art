package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.image.AbImageLoader;
import com.google.gson.Gson;
import com.shangxian.art.adapter.ShopsAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ProductDto;
import com.shangxian.art.bean.ShopLocInfo;
import com.shangxian.art.bean.ShopsModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.FollowServer;
import com.shangxian.art.net.FollowServer.OnFollowListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 商家情况
 * 
 * @author Administrator
 *
 */
public class ShopsActivity extends BaseActivity implements OnClickListener {
	ImageView img, shopsimg, collectionimg, img1, img2;
	TextView shopsname, guanzu, all, up, youhui, summary1, price1, summary2,
	price2;
	LinearLayout call, dingwei;

	// 判断是否收藏
	boolean iscollection = false;
	// 联系电话
	String phonenum = null;
	TextView phone, address;
	// popupwindow
	private PopupWindow popupWindow;
	private View view;
	private TextView fenlei, jianjie;

	private View headView = null;
	ListView mGridView;
	// GridLinearLayout mGridView;
	ShopsModel model;
	List<ProductDto> list;
	ShopsAdapter adapter;

	// 数据请求
	private AbHttpUtil httpUtil = null;
	private String url = "";

	// 图片下载器
	private AbImageLoader mAbImageLoader = null;

	private String shopid;
	private LinearLayout ll_noData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shops);

		initView();
		initData();
		initListener();
		initPopupWindow();
	}

	private void initHeadView() {
		headView = LayoutInflater.from(this).inflate(R.layout.head_shops, null);

		img = (ImageView) headView.findViewById(R.id.shops_img);
		/*
		 * //获取屏幕宽、高 Display mDisplay= getWindowManager().getDefaultDisplay();
		 * int width= mDisplay.getWidth(); int Height= mDisplay.getHeight();
		 * img.setScaleType(ImageView.ScaleType.FIT_CENTER);
		 * img.setAdjustViewBounds(true); img.setMaxHeight(Height);//屏幕高度
		 * img.setMaxWidth(width);//屏幕宽度
		 */
		// img.setScaleType(ImageView.ScaleType.FIT_XY);
		LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutParams.width = CommonUtil.getScreenWidth(this);
		layoutParams.height = layoutParams.width * 2 / 3;
		img.setLayoutParams(layoutParams);

		shopsimg = (ImageView) headView.findViewById(R.id.shops_shopsimg);
		collectionimg = (ImageView) headView
				.findViewById(R.id.shops_collectionimg);
		shopsname = (TextView) headView.findViewById(R.id.shops_shopsname);
		guanzu = (TextView) headView.findViewById(R.id.shops_guanzu);
		all = (TextView) headView.findViewById(R.id.shops_all);
		up = (TextView) headView.findViewById(R.id.shops_up);
		youhui = (TextView) headView.findViewById(R.id.shops_youhui);

		call = (LinearLayout) headView.findViewById(R.id.shops_call);
		dingwei = (LinearLayout) headView.findViewById(R.id.shops_dingwei);

		phone = (TextView) headView.findViewById(R.id.shops_phonenum);
		address = (TextView) headView.findViewById(R.id.shops_address);
	}

	private void initView() {
		// TODO Auto-generated method stub
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.setRightBtnDrawable(R.drawable.more1);
		topView.getRightbtn().setPadding(CommonUtil.dip2px(this, 20),
				CommonUtil.dip2px(this, 20), CommonUtil.dip2px(this, 20),
				CommonUtil.dip2px(this, 20));
		topView.showCenterSearch();
		topView.setBack(R.drawable.back);// 返回
		topView.setCneterHint("搜索店内商品");
		topView.setCenterListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//myToast("搜索");
				CommonUtil.gotoActivity(ShopsActivity.this, SearchsActivity.class, false);
			}
		});

		mGridView = (ListView) this.findViewById(R.id.shops_mListView);
		// mGridView = (GridLinearLayout) findViewById(R.id.shops_grid);
		// mGridView.setColumns(2);
		// summary1= (TextView) findViewById(R.id.shops_summary1);
		// price1= (TextView) findViewById(R.id.shops_price1);
		// summary2= (TextView) findViewById(R.id.shops_summary2);
		// price2= (TextView) findViewById(R.id.shops_price2);
		initHeadView();
		mGridView.addHeaderView(headView);
		adapter = new ShopsAdapter(
				ShopsActivity.this);
		list = new ArrayList<ProductDto>();
		adapter.updateData(list);
		mGridView.setAdapter(adapter);
		// 图片下载器
		mAbImageLoader = AbImageLoader.newInstance(mAc);
		mAbImageLoader.setLoadingImage(R.drawable.image_loading);
		mAbImageLoader.setErrorImage(R.drawable.image_error);
		mAbImageLoader.setEmptyImage(R.drawable.image_empty);

		ll_noData = (LinearLayout) findViewById(R.id.ll_nodata);
	}

	private void initData() {
		// TODO Auto-generated method stub
		model = new ShopsModel();
		// 请求解析数据
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(Constant.timeOut);
		String id = getIntent().getStringExtra("id");
		String geturl = getIntent().getStringExtra("url");
		String url = "";
		if (TextUtils.isEmpty(geturl)) {
			url = Constant.BASEURL + Constant.CONTENT + "/shop/" + id;
		} else {
			String[] geturls=geturl.split("/");
			geturl=geturls[geturls.length-1];
			url = Constant.BASEURL + Constant.CONTENT + "/shop/"+geturl;
		}

		refreshTask(url);

	}

	public static void startThisActivity(String id, Context context) {
		Intent intent = new Intent(context, ShopsActivity.class);
		intent.putExtra("id", id);
		context.startActivity(intent);
	}

	public static void startThisActivity_url(String url, Context context) {
		Intent intent = new Intent(context, ShopsActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}

	private void refreshTask(String url) {
		// TODO Auto-generated method stub
		httpUtil.post(url, new AbStringHttpResponseListener() {

			@Override
			public void onSuccess(int arg0, String arg1) {
				MyLogger.i(arg1);

				// 解析
				if (!TextUtils.isEmpty(arg1)) {
					Gson gson = new Gson();
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(arg1);
						String result_code = jsonObject
								.getString("result_code");
						String reason = jsonObject.getString("reason");
						if (result_code.equals("200")
								&& reason.equals("success")) {
							JSONObject resultObject = jsonObject
									.getJSONObject("result");
							model = gson.fromJson(resultObject.toString(),
									ShopsModel.class);
							if (model != null) {
								shopid = model.getId() + "";
								mAbImageLoader.display(img, Constant.BASEURL
										+ model.getLogo());// 图片

								mAbImageLoader
								.display(shopsimg, Constant.BASEURL
										+ model.getIndexLogo());// 商铺图标
								shopsname.setText("" + model.getName());// 商铺名
								guanzu.setText(model.getNoticeCount() + "人关注");// 关注
								all.setText("" + model.getProductCount());// 全部商品
								up.setText("" + model.getNewCount());// 上新
								youhui.setText("" + model.getSpecialCount());// 优惠
								phone.setText(model.getShopPhoneNumber());// 电话
								address.setText(model.getShopAddress());// 地址
								collectionimg.setSelected(model.getAttened());
								//热销商品
								list = model.getProductDtos();
								if (list!=null&&list.size() != 0) {
									if (adapter == null) {
									} else {
										// adapter.upDateList(list);
										adapter.updateData(list);
									}
								}else {
									MyLogger.i(">>>>>>>>>>>>>>>>>>热销商品为空");
								}

							}else {
								MyLogger.i(">>>>>>>>>>>>>>>>>>数据为空");
							}

						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initListener() {
		// TODO Auto-generated method stub
		/*
		 * mGridView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { // TODO Auto-generated method stub
		 * System.out.println(">>>>>>>>>"+position); } });
		 */
		/*
		 * mGridView.setOnCellClickListener(new OnCellClickListener() {
		 * 
		 * @Override public void onCellClick(int index) {
		 * 
		 * System.out.println(">>>>>>>>>index"+index); } });
		 */
		topView.setRightBtnListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.showAsDropDown(topView.getRightbtn());
			}
		});
		// 收藏
		collectionimg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isLoginAndToLogin()) {
					// if (!iscollection) {
					// collectionimg.setImageResource(R.drawable.collection_on);
					// myToast("已收藏");
					// }else {
					// collectionimg.setImageResource(R.drawable.collection_off);
					// myToast("取消收藏");
					// }
					// iscollection = !iscollection;
					if (model == null) {
						collectionimg.setSelected(false);
						myToast("关注失败！");
					} else {
						collectionimg.setSelected(!collectionimg.isSelected());
						if (!collectionimg.isSelected()) {
							new FollowServer().toDelFollowGoods(model.getId(),
									true, new CallBack() {
								@Override
								public void onSimpleSuccess(Object res) {
									myToast("取消关注成功");
								}

								@Override
								public void onSimpleFailure(int code) {
									myToast("取消关注失败");
									collectionimg
									.setSelected(!collectionimg
											.isSelected());
								}
							});
						} else {
							new FollowServer().toFollowShop(model.getId(),
									new OnFollowListener() {
								@Override
								public void onFollow(boolean isFollow) {
									if (!isFollow) {
										myToast("关注失败");
										collectionimg
										.setSelected(!collectionimg
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
		// 打电话

		call.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//拨打电话前可编辑
				phonenum = phone.getText().toString().trim();
				/*Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
						+ phonenum));*/
				//不编辑直接拨打电话
				if (phonenum != "") {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
							+ phonenum));
					startActivity(intent);
				}
			}
		});
		// 定位
		dingwei.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				ShopLocInfo info = new ShopLocInfo();
				info.setId(model.getId());
				info.setTitle(model.getName());
				info.setPhoto(model.getLogo());
				info.setLng(model.getLat());
				info.setAddress(model.getShopAddress());
				bundle.putSerializable(Constant.INT_SHOPS_2_LOC, info);
				bundle.putInt(Constant.INT_LOC_TOTYPE, Constant.MAP_SHOPS_2_LOC);
				CommonUtil.gotoActivityWithData(ShopsActivity.this,
						LocationActivity.class, bundle, false);
			}
		});
	}

	private void initPopupWindow() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		// 引入窗口配置文件
		view = inflater.inflate(R.layout.shops_popupwindow, null);
		// 创建popupwindow对象
		popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, false);
		// popupWindow.setBackgroundDrawable(R.drawable.background);

		// popupWindow.setHeight(500);
		// 点击外边可消失
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击外边窗口消失
		popupWindow.setOutsideTouchable(true);
		// 设置可获得焦点
		popupWindow.setFocusable(true);
		// popupwindow是否消失监听
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub

			}
		});
		// 得到popupwindow里面的控件
		fenlei = (TextView) view.findViewById(R.id.shops_popup_fenlei);
		jianjie = (TextView) view.findViewById(R.id.shops_popup_jianjie);
		fenlei.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				Bundle bundle = new Bundle();
				bundle.putBoolean("isother", true);
				CommonUtil.gotoActivityWithData(ShopsActivity.this, ClassificationActivity.class, bundle, false);

			}
		});
		jianjie.setOnClickListener(new OnClickListener() {
			// 跳转到商铺简介
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();

				ShopsSummaryActivity.startThisActivity(shopid,
						ShopsActivity.this);
				// CommonUtil.gotoActivity(ShopsActivity.this,
				// ShopsSummaryActivity.class, false);
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_share:
			CommonUtil.showShare(this, "test", "http://www.peoit.com/",
					"http://www.peoit.com/");
			break;

		default:
			break;
		}
	}

}
