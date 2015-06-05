package com.shangxian.art;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.image.AbImageLoader;
import com.google.gson.Gson;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ShopsModel;
import com.shangxian.art.bean.ShopsSummaryModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.view.TopView;
/**
 * 商铺简介
 * @author Administrator
 *
 */
public class ShopsSummaryActivity extends BaseActivity{
	ImageView shopsimg,phoneimg,addressimg;//商铺图片、电话图片、地址图片
	TextView shopsname,guanzu,zhanggui,phone,address,summary;//商铺名字、关注、掌柜名、电话、地址、简介
	//联系电话
	String phonenum = null;
	ShopsSummaryModel model;

	//图片下载器
	private AbImageLoader mAbImageLoader = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopssummary);
		initView();
		initData();
		initListener();
	}
	public static void startThisActivity(String id, Context context) {
		Intent intent = new Intent(context, ShopsSummaryActivity.class);
		intent.putExtra("id", id);
		context.startActivity(intent);
	}
	public static void startThisActivity_url(String url, Context context) {
		Intent intent = new Intent(context, CommodityContentActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}
	private void initView() {
		// TODO Auto-generated method stub
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.setRightBtnDrawable(R.drawable.collection);
		topView.setTitle("商铺简介");
		topView.setBack(R.drawable.back);//返回
		topView.hideCenterSearch();

		shopsimg = (ImageView) findViewById(R.id.shopssummary_shopsimg);
		phoneimg = (ImageView) findViewById(R.id.shopssummary_phoneimg);
		addressimg = (ImageView) findViewById(R.id.shopssummary_addressimg);

		shopsname = (TextView) findViewById(R.id.shopssummary_shopsname);
		guanzu = (TextView) findViewById(R.id.shopssummary_guanzu);
		zhanggui = (TextView) findViewById(R.id.shopssummary_zhanggui);
		phone = (TextView) findViewById(R.id.shopssummary_phone);
		address = (TextView) findViewById(R.id.shopssummary_address);
		summary = (TextView) findViewById(R.id.shopssummary_summary);

		//图片下载器
		mAbImageLoader = AbImageLoader.newInstance(mAc);
		mAbImageLoader.setLoadingImage(R.drawable.image_loading);
		mAbImageLoader.setErrorImage(R.drawable.image_error);
		mAbImageLoader.setEmptyImage(R.drawable.image_empty);
	}

	private void initData() {
		model = new ShopsSummaryModel();
		//请求解析数据
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(Constant.timeOut);
		String id = getIntent().getStringExtra("id");
		String geturl = getIntent().getStringExtra("url");
		String url = "";
		if (TextUtils.isEmpty(geturl)) {
			url = Constant.BASEURL + Constant.CONTENT + "/shop/intro/"+id;
		} else {
			url = Constant.BASEURL + Constant.CONTENT + geturl;
		}
		refreshTask(url);

	}
	private void refreshTask(String url) {
		httpUtil.get(url, new AbStringHttpResponseListener() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(int arg0, String arg1) {
				System.out.println(">>>>>>>>>>>>>商铺简介"+arg1);
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
							model=gson.fromJson(resultObject.toString(),ShopsSummaryModel.class);
							if (model != null) {
								mAbImageLoader.display(shopsimg, Constant.BASEURL+ model.getLogo());//图片
								shopsname.setText(model.getName());//店铺名
//								guanzu.setText(model.getConsignee()+"");//关注
								zhanggui.setText(model.getOwner());//掌柜
								phone.setText(model.getPhoneNumbers().get(0)+"");//电话
								address.setText(model.getAddress());//地址
								summary.setText(model.getDetails());//简介
							}
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
	}
	private void initListener() {
		// TODO Auto-generated method stub
		//拨打电话
		phoneimg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				phonenum = phone.getText().toString().trim();
				Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phonenum));
				startActivity(intent);
			}
		});
		//跳转到定位
		addressimg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

}
