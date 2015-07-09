package com.shangxian.art;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ab.http.AbHttpClient;
import com.ab.http.AbHttpUtil;
import com.google.gson.Gson;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.BenQiJieSuanModel;
import com.shangxian.art.bean.ShopsSummaryModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.BaseServer;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.HttpClients;
import com.shangxian.art.net.ShopsServer;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.LocalUserInfo;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 本期结算
 * @author Administrator
 *
 */
public class BenQiJieSuanActivity extends BaseActivity{

	private TextView tv_parice,tv_remainder,tv_time,tv_type;
	private BenQiJieSuanModel model;
	private View loading_big;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_benqijiesuan);
		initView();
		initData();
	}

	private void initView() {
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideCenterSearch();//隐藏搜索框
		topView.hideRightBtn_invisible();//隐藏右按钮
		topView.showTitle();
		topView.setTitle("本期结算");
		topView.setBack(R.drawable.back);//返回

		tv_parice = (TextView) findViewById(R.id.benqijiesuan_parice);//总金额
		tv_remainder = (TextView) findViewById(R.id.benqijiesuan_parice);//账户余额
		tv_time = (TextView) findViewById(R.id.benqijiesuan_time);//结算时间
		tv_type = (TextView) findViewById(R.id.benqijiesuan_type);//结算类型
//		loading_big=findViewById(R.id.loading_big);
	}

	private void initData() {
		model = new BenQiJieSuanModel();
		String shopid = LocalUserInfo.getInstance(this).getString(Constant.INT_SHOPID);
		MyLogger.i("shopid》》》》》》》》》》："+shopid);
		if (shopid != "0") {
			refreshTask(shopid);
		}else {
			myToast("数据错误，请重新登录");
		}

	}

	private void refreshTask(String shopid) {
//		loading_big.setVisibility(View.GONE);
		ShopsServer.toBenQiJieSuan(shopid, new CallBack() {

			@Override
			public void onSimpleSuccess(Object res) {
				
				MyLogger.i("本期结算数据："+res);
				System.out.println("本期结算数据："+res);
				if (res != null) {
					try {
						Gson gson = new Gson();
						JSONObject jsonObject = new JSONObject(res.toString());
						model=gson.fromJson(jsonObject.toString(),BenQiJieSuanModel.class);
						if (model != null) {
							MyLogger.i(model.toString());
							tv_parice.setText(CommonUtil.priceConversion(model.getAmount()));
							tv_time.setText(model.getSettlementTime());
							if (model.getSettlementType().equals("NORMAL")) {
								tv_type.setText("定时结算");
							}else {
								tv_type.setText("紧急结算");
							}
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					myToast("数据请求失败");
				}
			}

			@Override
			public void onSimpleFailure(int code) {
				myToast("数据请求失败");
			}
		});
	}
}
