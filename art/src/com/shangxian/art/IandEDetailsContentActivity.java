package com.shangxian.art;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

public class IandEDetailsContentActivity extends BaseActivity{
	private TextView tv_title,tv_price,tv_liushuihao,tv_fangxiang,tv_fangshi,tv_time,tv_beizhu;
	private ImageView img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
		tv_title = (TextView) findViewById(R.id.iandedetailscontent_title);//
		tv_price = (TextView) findViewById(R.id.iandedetailscontent_price);//
		tv_liushuihao = (TextView) findViewById(R.id.iandedetailscontent_liushuihao);//
		tv_fangxiang = (TextView) findViewById(R.id.iandedetailscontent_fangxiang);//
		tv_fangshi = (TextView) findViewById(R.id.iandedetailscontent_fangshi);//
		tv_time = (TextView) findViewById(R.id.iandedetailscontent_time);//
		tv_beizhu = (TextView) findViewById(R.id.iandedetailscontent_beizhu);//
		
	}

	private void initData() {
		
		
	}

	private void initListener() {
		
		
	}

}
