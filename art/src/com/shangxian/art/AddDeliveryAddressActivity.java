package com.shangxian.art;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.AddDeliveryAddressModel;
import com.shangxian.art.bean.DeliveryAddressModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.HttpClients;
import com.shangxian.art.net.HttpClients.HttpCilentListener;
import com.shangxian.art.view.TopView;

/**
 * 添加收货地址
 * @author zyz
 *
 */
public class AddDeliveryAddressActivity extends BaseActivity{

	private EditText name,num,youbian,address;
	private TextView diqu,quxiao,baocun;
	private ImageView diqu_img;

	private String receiverName,receiverTel,deliveryAddress;//收货人、联系方式、详细地址
	private AddDeliveryAddressModel model;


	Boolean isRevise = false;//是否为修改地址
	int id = -1;
	DeliveryAddressModel deliveryAddressModel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adddeliveryaddress);
		initView();
		initData();
		initListener();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		name.requestFocus();
	}
	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_adddeliveryaddress));

		name = (EditText) findViewById(R.id.adddeliveryaddress_name);//收货人
		num = (EditText) findViewById(R.id.adddeliveryaddress_num);//联系方式
		youbian = (EditText) findViewById(R.id.adddeliveryaddress_youzheng);//邮政编码
		address = (EditText) findViewById(R.id.adddeliveryaddress_address);//详细地址
		//		diqu = (TextView) findViewById(R.id.adddeliveryaddress_address_txt);//地区
		//		diqu_img = (ImageView) findViewById(R.id.adddeliveryaddress_address_img);//地区img
		quxiao = (TextView) findViewById(R.id.adddeliveryaddress_quxiao);//取消
		baocun = (TextView) findViewById(R.id.adddeliveryaddress_baocun);//保存

		model = new AddDeliveryAddressModel();
	}
	public static void startThisActivity(String id, Context context) {
		Intent intent = new Intent(context, AddDeliveryAddressActivity.class);
		intent.putExtra("id", id);
		context.startActivity(intent);
	}

	public static void startThisActivity_url(String url, Context context) {
		Intent intent = new Intent(context, AddDeliveryAddressActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}
	private void initData() {
		Intent intent = getIntent();
		//		isRevise = intent.getBooleanExtra("isRevise", false);
		//		id = intent.getIntExtra("id", -1);
		deliveryAddressModel = (DeliveryAddressModel) intent.getSerializableExtra("DeliveryAddressModel");
		System.out.println(">>>>>>>>>>>deliveryAddressModel"+deliveryAddressModel);
		if (deliveryAddressModel != null) {
			//修改地址
			name.setText(deliveryAddressModel.getReceiverName()+"");
			num.setText(deliveryAddressModel.getReceiverTel()+"");
			address.setText(deliveryAddressModel.getDeliveryAddress()+"");

			id = deliveryAddressModel.getId();
			isRevise = true;
		}
		isRevise = false;
	}
	private void initListener() {
		/*		diqu_img.setOnClickListener(new OnClickListener() {
			//选择地区
			@Override
			public void onClick(View v) {


			}
		});*/
		baocun.setOnClickListener(new OnClickListener() {
			//保存
			@Override
			public void onClick(View v) {

				receiverName = name.getText().toString().trim();//收货人
				receiverTel = num.getText().toString().trim();//联系方式
				deliveryAddress = address.getText().toString().trim();//详细地址
				if (!receiverName.equals("") && !receiverTel.equals("") && !deliveryAddress.equals("")) {
					model.setReceiverName(receiverName);
					model.setReceiverTel(receiverTel);
					model.setDeliveryAddress(deliveryAddress);
					System.out.println(">>>>>><<<<<<<<id+"+id);
					model.setId(id);

					//添加地址
					String url = "";
					url = Constant.BASEURL + Constant.CONTENT + "/receiving";
					refreshTask(url);

				}else {
					myToast("请认真输入");
				}
			}

		});
		quxiao.setOnClickListener(new OnClickListener() {
			//取消
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	//添加地址
	private void refreshTask(String url) {
		System.out.println(">>>>>>>>>>>>>添加地址");
		Gson gson = new Gson();
		String json = gson.toJson(model);
		System.out.println("<<<<<<<<<<<<<<json"+json);
		HttpClients.postDo(url, json, new HttpCilentListener() {

			@Override
			public void onResponse(String res) {
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(res);
					System.out.println(">>>>>>>>>>>>"+res);
					String result_code = jsonObject.getString("result_code");
					if (result_code.equals("200")) {
						//						JSONArray str=jsonObject.getJSONArray("result");
						myToast("添加地址成功");
						finish();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
						myToast("添加地址失败");
				} 
			}
		});
	}
	/*//修改地址
	private void refreshTask1(String url1) {
		System.out.println(">>>>>>>>>>>>>修改地址");

	}*/
}
