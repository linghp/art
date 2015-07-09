package com.shangxian.art;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.BenQiJieSuanModel;
import com.shangxian.art.bean.JinJiJieSuanModel;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.ShopsServer;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 紧急结算
 * @author zyz
 *
 */
public class JinJiJieSuanActivity extends BaseActivity{

	private TextView shenqing; 
	private TextView num,price,jindu,jiesuantime,name,phone,shenqingtime,content;
	private JinJiJieSuanModel model;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jinjijiesuan);
		initView();
		
		initListener();
	}

	private void initView() {
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideCenterSearch();//隐藏搜索框
		topView.hideRightBtn();//隐藏右按钮
		topView.showTitle();
		topView.setTitle("紧急结算");
		topView.setBack(R.drawable.back);//返回
		shenqing = (TextView) findViewById(R.id.jinjijiesuan_btn);//申请紧急结算/进度查询

		num = (TextView) findViewById(R.id.yishenqing_num);//申请编号
		price = (TextView) findViewById(R.id.yishenqing_price);//申请金额
		jindu = (TextView) findViewById(R.id.yishenqing_jindu);//进度
		jiesuantime = (TextView) findViewById(R.id.yishenqing_time);//申请结算时间
		name = (TextView) findViewById(R.id.yishenqing_name);//申请人
		phone = (TextView) findViewById(R.id.yishenqing_phone);//申请人手机号
		shenqingtime = (TextView) findViewById(R.id.yishenqing_time1);//申申请时间
		content = (TextView) findViewById(R.id.yishenqing_content);//情况说明
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}
	private void initData() {
		model = new JinJiJieSuanModel();
		ShopsServer.toJinJiJieSuan(new CallBack() {

			@Override
			public void onSimpleSuccess(Object res) {
				MyLogger.i("紧急结算数据："+res);
				System.out.println("紧急结算数据："+res);
				if (res != null) {
					try {
						Gson gson = new Gson();
						JSONObject jsonObject = new JSONObject(res.toString());
						model=gson.fromJson(jsonObject.toString(),JinJiJieSuanModel.class);
						if (model != null) {
							findViewById(R.id.yisheniqng_linear2).setVisibility(View.GONE);
							findViewById(R.id.yisheniqng_linear).setVisibility(View.VISIBLE);
							shenqing.setVisibility(View.GONE);
							MyLogger.i(model.toString());
							num.setText(model.getId()+"");
							price.setText("¥"+CommonUtil.priceConversion(model.getAmount()));
							jiesuantime.setText(model.getSettlementTimeLast());
							name.setText(model.getPersoname());
							phone.setText(model.getPhoneNum());
//							shenqingtime.setText(model.getSettlementTime()+"");
							content.setText(model.getRemarks());
							if (model.getIsFinished() == false) {
								jindu.setText("待处理");
							}else {
								jindu.setText("已结算");
							}
							/*if (model.getSettlementType().equals("NORMAL")) {
								tv_type.setText("定时结算");
							}else {
								tv_type.setText("紧急结算");
							}*/
							topView.setRightText("撤销",new OnClickListener() {

								@Override
								public void onClick(View v) {
									ShopsServer.toCheXiaoJieSuan(model.getId()+"", new CallBack() {
										
										@Override
										public void onSimpleSuccess(Object res) {
											System.out.println("撤销结算数据："+res);
											myToast("撤销成功");
											
										}
										
										@Override
										public void onSimpleFailure(int code) {
											myToast("撤销失败");
											
										}
									});

								}
							});
						}else {
							myToast("没有紧急结算");
							shenqing.setText("申请紧急结算");
							findViewById(R.id.yisheniqng_linear2).setVisibility(View.VISIBLE);
							findViewById(R.id.yisheniqng_linear).setVisibility(View.GONE);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
//					myToast("数据请求失败");
					shenqing.setText("申请紧急结算");
					findViewById(R.id.yisheniqng_linear2).setVisibility(View.VISIBLE);
					findViewById(R.id.yisheniqng_linear).setVisibility(View.GONE);
				}
			}

			@Override
			public void onSimpleFailure(int code) {
				myToast("数据请求失败");

			}
		});
	}

	private void initListener() {
		shenqing.setOnClickListener(new OnClickListener() {
			//申请
			@Override
			public void onClick(View v) {
				CommonUtil.gotoActivity(JinJiJieSuanActivity.this, AskJieSuanActivity.class, false);
			}
		});

	}
}
