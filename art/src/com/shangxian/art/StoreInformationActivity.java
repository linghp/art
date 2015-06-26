package com.shangxian.art;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.http.AbStringHttpResponseListener;
import com.google.gson.Gson;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ShopsModel;
import com.shangxian.art.bean.ShopsSummaryModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.LocalUserInfo;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 商铺信息维护
 * @author zyz
 *
 */
public class StoreInformationActivity extends BaseActivity{

	private EditText et_name,et_summary,et_phone,et_address,et_return;
	private ImageView iv_storeimg;
	private TextView tv_save;
	String name,summary,phone,address,tuihuo;
	
	
	ShopsSummaryModel model;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_storeinformation);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_storeinformation));
		
		et_name = (EditText) findViewById(R.id.storeinformation_et1);
		et_summary = (EditText) findViewById(R.id.storeinformation_et2);
		et_phone = (EditText) findViewById(R.id.storeinformation_et3);
		et_address = (EditText) findViewById(R.id.storeinformation_et4);
		et_return = (EditText) findViewById(R.id.storeinformation_et5);
		iv_storeimg = (ImageView) findViewById(R.id.storeinformation_img1);
		tv_save = (TextView) findViewById(R.id.storeinformation_tv);
				
		
	}

	private void initData() {
		String shopid = LocalUserInfo.getInstance(this).getString(Constant.INT_SHOPID);
		MyLogger.i("商家shopid："+shopid);
		String url = Constant.BASEURL + Constant.CONTENT + "/shop/intro/" + shopid;
		MyLogger.i("商铺简介接口："+url);
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
				MyLogger.i("商铺信息维护"+arg1);
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
								mAbImageLoader.display(iv_storeimg, Constant.BASEURL+ model.getLogo());//图片
								et_name.setText(model.getName());//店铺名
								//								guanzu.setText(model.getConsignee()+"");//关注
//								et_return.setText(model.get);//退货信息
								if (model.getPhoneNumbers() != null) {
									et_phone.setText(model.getPhoneNumbers().get(0)+"");//电话
								}

								et_address.setText(model.getAddress());//地址
								et_summary.setText(model.getDetails());//简介
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
		tv_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//保存
				name = et_name.getText().toString().trim();
				summary = et_summary.getText().toString().trim();
				phone = et_phone.getText().toString().trim();
				address = et_address.getText().toString().trim();
				tuihuo = et_return.getText().toString().trim();
				
			}
		});
		
	}
}
