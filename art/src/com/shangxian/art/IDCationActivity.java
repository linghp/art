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
 * 实名认证
 * @author Administrator
 *
 */
public class IDCationActivity extends BaseActivity{

	EditText name,num;
	ImageView photo1,photo2;
	TextView btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idcation);
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
		topView.setTitle(getString(R.string.title_activity_idcation));
		
		name = (EditText) findViewById(R.id.idcation_name);//姓名
		num = (EditText) findViewById(R.id.idcation_num);//身份证号码
		photo1 = (ImageView) findViewById(R.id.idcation_photo1);//照片1
		photo2 = (ImageView) findViewById(R.id.idcation_photo2);//照片2
		btn = (TextView) findViewById(R.id.idcation_btn);//申请认证
	}

	private void initData() {
		
		
	}

	private void initListener() {
		btn.setOnClickListener(new OnClickListener() {
			//申请认证
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}
