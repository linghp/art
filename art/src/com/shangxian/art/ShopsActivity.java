package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
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
import com.shangxian.art.bean.ShopsModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 商家情况
 * @author Administrator
 *
 */
public class ShopsActivity extends BaseActivity implements OnClickListener{
	ImageView img,shopsimg,collectionimg,img1,img2;
	TextView shopsname,guanzu,all,up,youhui,summary1,price1,summary2,price2;
	LinearLayout call,dingwei;

	//判断是否收藏
	boolean iscollection = false;
	//联系电话
	String phonenum = null;
	TextView phone,address;
	//popupwindow
	private PopupWindow popupWindow;
	private View view;
	private TextView fenlei,jianjie;

	private View headView = null;
	ListView mGridView;
	//	GridLinearLayout mGridView;
	ShopsModel model;
	List<ProductDto>list;
	ShopsAdapter adapter;

	//数据请求
	private AbHttpUtil httpUtil = null;
	private String url = "";

	//图片下载器
	private AbImageLoader mAbImageLoader = null;

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
		headView = LayoutInflater.from(this).inflate(
				R.layout.head_shops, null);


		img = (ImageView) headView.findViewById(R.id.shops_img);
		img.setScaleType(ImageView.ScaleType.FIT_XY); 
		LayoutParams layoutParams=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutParams.width=CommonUtil.getScreenWidth(this);
		layoutParams.height=layoutParams.width*2/3;
		img.setLayoutParams(layoutParams);

		shopsimg = (ImageView) headView.findViewById(R.id.shops_shopsimg);
		collectionimg = (ImageView) headView.findViewById(R.id.shops_collectionimg);
		shopsname= (TextView) headView.findViewById(R.id.shops_shopsname);
		guanzu= (TextView) headView.findViewById(R.id.shops_guanzu);
		all= (TextView) headView.findViewById(R.id.shops_all);
		up= (TextView) headView.findViewById(R.id.shops_up);
		youhui= (TextView) headView.findViewById(R.id.shops_youhui);

		call = (LinearLayout) headView.findViewById(R.id.shops_call);
		dingwei = (LinearLayout) headView.findViewById(R.id.shops_dingwei);

		phone = (TextView) headView.findViewById(R.id.shops_phonenum);
		address = (TextView) headView.findViewById(R.id.shops_address);
	}

	private void initView() {
		// TODO Auto-generated method stub
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.setRightBtnDrawable(R.drawable.more1);
		topView.getRightbtn().setPadding(CommonUtil.dip2px(this, 20), CommonUtil.dip2px(this, 20), CommonUtil.dip2px(this, 20), CommonUtil.dip2px(this, 20));
		topView.showCenterSearch();
		topView.setBack(R.drawable.back);//返回

		mGridView = (ListView) this.findViewById(R.id.shops_mListView);
		//		mGridView = (GridLinearLayout) findViewById(R.id.shops_grid);
		//		mGridView.setColumns(2);
		//		summary1= (TextView) findViewById(R.id.shops_summary1);
		//		price1= (TextView) findViewById(R.id.shops_price1);
		//		summary2= (TextView) findViewById(R.id.shops_summary2);
		//		price2= (TextView) findViewById(R.id.shops_price2);
		initHeadView();
		mGridView.addHeaderView(headView);
		//图片下载器
		mAbImageLoader = AbImageLoader.newInstance(mAc);
		mAbImageLoader.setLoadingImage(R.drawable.image_loading);
		mAbImageLoader.setErrorImage(R.drawable.image_error);
		mAbImageLoader.setEmptyImage(R.drawable.image_empty);
	}

	private void initData() {
		// TODO Auto-generated method stub
		list = new ArrayList<ProductDto>();
		model = new ShopsModel();
		//请求解析数据
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(Constant.timeOut);
		String id = getIntent().getStringExtra("id");
		String geturl = getIntent().getStringExtra("url");
		String url = "";
		if (TextUtils.isEmpty(geturl)) {
			url = Constant.BASEURL + Constant.CONTENT + "/shop/"+id;
		} else {
			url = Constant.BASEURL + Constant.CONTENT + geturl;
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
		httpUtil.get(url, new AbStringHttpResponseListener() {

			@Override
			public void onSuccess(int arg0, String arg1) {
				MyLogger.i(arg1);

				// 解析
				if (!TextUtils.isEmpty(arg1)) {
					Gson gson = new Gson();
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(arg1);
						String result_code = jsonObject.getString("result_code");
						String reason = jsonObject.getString("reason");
						if (result_code.equals("200")&&reason.equals("success")) {
							JSONObject resultObject = jsonObject.getJSONObject("result");
							model=gson.fromJson(resultObject.toString(),ShopsModel.class);




							mAbImageLoader.display(img, Constant.BASEURL+ model.getLogo());//图片
							mAbImageLoader.display(shopsimg, Constant.BASEURL+ model.getIndexLogo());//商铺图标
							shopsname.setText(""+model.getName());//商铺名
							guanzu.setText(model.getNoticeCount()+"人关注");//关注
							all.setText(""+model.getProductCount());//全部商品
							up.setText(""+model.getNewCount());//上新
							youhui.setText(""+model.getSpecialCount());//优惠

							list=model.getProductDtos();

							if (adapter == null) {
								//								//给每项商品设置id
								//								for (int i = 0; i < list.size(); i++) {
								//									ProductDto p = new ProductDto();
								//									p.setId(i);
								//									list.add(p);
								//								}
								adapter = new ShopsAdapter(ShopsActivity.this);
								//adapter = new ShopsAdapter(this);
								adapter.updateData(list);
								mGridView.setAdapter(adapter);
								//								mGridView.bindLinearLayout();
							}else {
								//								adapter.upDateList(list);
								adapter.updateData(list);
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
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>>>"+position);
			}
		});
		/*mGridView.setOnCellClickListener(new OnCellClickListener() {

			@Override
			public void onCellClick(int index) {

					System.out.println(">>>>>>>>>index"+index);
			}
		});*/
		topView.setRightBtnListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.showAsDropDown(topView.getRightbtn());
			}
		});
		//收藏
		collectionimg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(isLoginAndToLogin()){
					if (!iscollection) {
						collectionimg.setImageResource(R.drawable.collection_on);
						myToast("已收藏");
					}else {
						collectionimg.setImageResource(R.drawable.collection_off);
						myToast("取消收藏");
					}
					iscollection = !iscollection;
				}
			}
		});
		//打电话
		call.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phonenum = phone.getText().toString().trim();
				Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phonenum));
				startActivity(intent);
			}
		});
		//定位
		dingwei.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putSerializable(Constant.INT_SHOPS_2_LOC, model);
				bundle.putInt(Constant.INT_LOC_TOTYPE, Constant.MAP_SHOPS_2_LOC);
				CommonUtil.gotoActivityWithData(ShopsActivity.this, LocationActivity.class, bundle, false);
			}
		});
	}
	private void initPopupWindow() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		//引入窗口配置文件
		view = inflater.inflate(R.layout.shops_popupwindow, null);
		//创建popupwindow对象
		popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,false);
		//		popupWindow.setBackgroundDrawable(R.drawable.background);

		//		popupWindow.setHeight(500);
		//点击外边可消失
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		//设置点击外边窗口消失
		popupWindow.setOutsideTouchable(true);
		//设置可获得焦点
		popupWindow.setFocusable(true);
		//popupwindow是否消失监听
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub

			}
		});	
		//得到popupwindow里面的控件
		fenlei = (TextView) view.findViewById(R.id.shops_popup_fenlei);
		jianjie = (TextView) view.findViewById(R.id.shops_popup_jianjie);
		fenlei.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				//				CommonUtil.gotoActivity(ShopsActivity.this, ClassificationActivity.class, true);
			}
		});
		jianjie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				CommonUtil.gotoActivity(ShopsActivity.this, ShopsSummaryActivity.class, false);
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
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

		// 启动分享GUI
		oks.show(this);
	}
}
