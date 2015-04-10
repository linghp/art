package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbLogUtil;
import com.google.gson.Gson;
import com.shangxian.art.adapter.ClassityCommodiyAdp;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ClassityCommdityModel;
import com.shangxian.art.bean.CommodityContentModel;
import com.shangxian.art.cache.Imageloader_homePager;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.HttpClients;
import com.shangxian.art.net.HttpClients.HttpCilentListener;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;
/**
 * 商品详情
 * @author Administrator
 *
 */
public class CommodityContentActivity extends BaseActivity implements OnClickListener,HttpCilentListener{
  private LinearLayout shangpu;
    private ImageView commoditycontent_img;
    private TextView commoditycontent_jieshao,commoditycontent_jiage,commoditycontent_jiarugouwuche;
	
	private AbHttpUtil httpUtil = null;
	private CommodityContentModel model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commoditycontent);

		initView();
		initData();
		listener();
	}
	private void listener() {
		// TODO Auto-generated method stub
		shangpu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonUtil.gotoActivity(CommodityContentActivity.this, ShopsActivity.class, false);
			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void initView() {
		// TODO Auto-generated method stub
		//		star = (StarRatingView) findViewById(R.id.commoditycontent_starRating);
		//		star.setSelectNums(1);//设置默认选中星星数
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);//返回
		topView.setTitle("商品详情");//title文字

		shangpu = (LinearLayout) findViewById(R.id.commoditycontent_shangpu);
	}
}
