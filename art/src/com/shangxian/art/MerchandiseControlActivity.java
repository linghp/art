package com.shangxian.art;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

/**
 * 商品管理（登陆成功后）
 * @author Administrator
 * 结算中心
 * isjiesuan = true
 * 
 */
public class MerchandiseControlActivity extends BaseActivity{
	private LinearLayout linear1,linear2,linear3;

	boolean isjiesuan =  false;
	boolean isshangpu =  false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_merchandisecontrol);
		initView();
		initData();
		listener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideCenterSearch();//隐藏搜索框
		topView.hideRightBtn_invisible();//隐藏右按钮
		topView.showTitle();
		topView.setTitle("商品管理");
		topView.setBack(R.drawable.back);//返回

		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		
		//结算中心
		Intent intent = getIntent();
		isjiesuan = intent.getBooleanExtra("isjiesuan", false);
		if (isjiesuan == true) {
			topView.setTitle("商户结算");
			linear1.setVisibility(View.GONE);
			linear2.setVisibility(View.VISIBLE);
			linear3.setVisibility(View.GONE);
		}
		//商铺管理
		Intent intent1 = getIntent();
		isjiesuan = intent1.getBooleanExtra("isshangpu", false);
		if (isjiesuan == true) {
			topView.setTitle("商铺管理");
			linear1.setVisibility(View.GONE);
			linear2.setVisibility(View.GONE);
			linear3.setVisibility(View.VISIBLE);
		}
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void listener() {
		
	}
	
	public void onclick(View v) {
		switch (v.getId()) {
		case R.id.merchandisecontrol_linear1:
			//商品类别
			CommonUtil.gotoActivity(MerchandiseControlActivity.this, ShangPinWeiHuActivity.class, false);
			break;
		case R.id.merchandisecontrol_linear2:
			//配送类别维护
			CommonUtil.gotoActivity(MerchandiseControlActivity.this, DeliveryServiceActivity.class, false);
			break;
		/*case R.id.merchandisecontrol_linear3:
		 *   //添加商品
			CommonUtil.gotoActivity(MerchandiseControlActivity.this, AddGoodsActivity.class, false);
			break;*/
		case R.id.merchandisecontrol_linear4:
			//搜索商品
			CommonUtil.gotoActivity(MerchandiseControlActivity.this, SearchsActivity.class, false);
			break;
		case R.id.benqijiesuan:
			//本期结算
			CommonUtil.gotoActivity(MerchandiseControlActivity.this, BenQiJieSuanActivity.class, false);
			break;
		case R.id.jinjijiesuan:
			//紧急结算
			CommonUtil.gotoActivity(MerchandiseControlActivity.this, JinJiJieSuanActivity.class, false);
			break;
		case R.id.lishichaxun:
			//结算历史查询
			CommonUtil.gotoActivity(MerchandiseControlActivity.this, JieSuanLiShiActivity.class, false);
			break;
		case R.id.shangpuxinxi:
			//商铺信息
//			CommonUtil.gotoActivity(MerchandiseControlActivity.this, JinJiJieSuanActivity.class, false);
			break;
		case R.id.caozuoyuan:
			//操作员管理
//			CommonUtil.gotoActivity(MerchandiseControlActivity.this, JieSuanLiShiActivity.class, false);
			break;
		default:
			break;
		}

	}

}
