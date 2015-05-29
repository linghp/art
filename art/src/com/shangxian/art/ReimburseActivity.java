package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

/**
 * 退货申请
 * @author zyz
 *
 */
public class ReimburseActivity extends BaseActivity{

	private TextView tv_need,tv_notneed,tv_quxiao,tv_tijiao;//需要、不需要
	private EditText et_cause,et_money,et_explain;//原因、金额、说明
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reimburse);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.setRightBtnDrawable(R.drawable.addto);
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_iandedetailscontent));
		
		tv_need = (TextView) findViewById(R.id.reimburse_tv1);
		tv_notneed = (TextView) findViewById(R.id.reimburse_tv2);
		tv_quxiao = (TextView) findViewById(R.id.reimburse_quxiao);
		tv_tijiao = (TextView) findViewById(R.id.reimburse_baocun);
		
		et_cause = (EditText) findViewById(R.id.reimburse_edit1);
		et_money = (EditText) findViewById(R.id.reimburse_edit2);
		et_explain = (EditText) findViewById(R.id.reimburse_edit3);

	}

	private void initData() {	
	}

	private void initListener() {
		tv_quxiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//取消
				finish();
			}
		});
		tv_tijiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//提交
				
			}
		});
	}
}
