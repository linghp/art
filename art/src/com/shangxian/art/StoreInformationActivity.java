package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
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
