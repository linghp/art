package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.image.AbImageLoader;
import com.ab.util.AbLogUtil;
import com.google.gson.Gson;
import com.shangxian.art.adapter.ShopsAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ProductDto;
import com.shangxian.art.bean.ShopsListModel;
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
public class ShopsActivity extends BaseActivity{
	ImageView img,shopsimg,collectionimg,img1,img2;
	TextView shopsname,guanzu,all,up,youhui,summary1,price1,summary2,price2;
	LinearLayout call,dingwei;

	//判断是否收藏
	boolean iscollection = false;
	//popupwindow
	private PopupWindow popupWindow;
	private View view;
	private TextView fenlei,jianjie;

	GridView mGridView;
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

	private void initView() {
		// TODO Auto-generated method stub
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.setRightBtnDrawable(R.drawable.more1);
		topView.showCenterSearch();
		topView.setBack(R.drawable.back);//返回

		img = (ImageView) findViewById(R.id.shops_img);
		shopsimg = (ImageView) findViewById(R.id.shops_shopsimg);
		collectionimg = (ImageView) findViewById(R.id.shops_collectionimg);
		//		img1 = (ImageView) findViewById(R.id.shops_img1);
		//		img2 = (ImageView) findViewById(R.id.shops_img2);
		shopsname= (TextView) findViewById(R.id.shops_shopsname);
		guanzu= (TextView) findViewById(R.id.shops_guanzu);
		all= (TextView) findViewById(R.id.shops_all);
		up= (TextView) findViewById(R.id.shops_up);
		youhui= (TextView) findViewById(R.id.shops_youhui);
		
		call = (LinearLayout) findViewById(R.id.shops_call);
		dingwei = (LinearLayout) findViewById(R.id.shops_dingwei);

		mGridView = (GridView) findViewById(R.id.shops_grid);
		//		summary1= (TextView) findViewById(R.id.shops_summary1);
		//		price1= (TextView) findViewById(R.id.shops_price1);
		//		summary2= (TextView) findViewById(R.id.shops_summary2);
		//		price2= (TextView) findViewById(R.id.shops_price2);
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
		url = Constant.BASEURL + Constant.CONTENT + "/shop/1";//第一条数据地址
		refreshTask(url);	
		
	}

	private void refreshTask(String url2) {
		// TODO Auto-generated method stub
		httpUtil.get(url, new AbStringHttpResponseListener() {

			@Override
			public void onSuccess(int arg0, String arg1) {
				AbLogUtil.i(ShopsActivity.this, arg1);
//				System.out.println(">>>>>>>>>>请求到的数据"+arg1);
				//解析
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
							
							mAbImageLoader.display(img, Constant.BASEURL+ model.getLogo());
							shopsname.setText(""+model.getName());
							
							
							list=model.getProductDtos();
							if (adapter == null) {
								adapter = new ShopsAdapter(ShopsActivity.this, R.layout.item_shops, list);
								mGridView.setAdapter(adapter);
							}else {
								adapter.upDateList(list);
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
		topView.setRightBtnListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.showAsDropDown(topView, 460, -30);
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
				Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+10086));
				startActivity(intent);
			}
		});
		//定位
		dingwei.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putSerializable(Constant.INT_SHOPS_2_LOC, model);
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
}
