package com.shangxian.art;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.view.TopView;

/**
 * 收支明细详情
 * @author zyz
 *
 */
public class IandEDetailsContentActivity extends BaseActivity{
	private TextView tv_title,tv_price,tv_liushuihao,tv_fangxiang,tv_fangshi,tv_time,tv_beizhu;
	private ImageView img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iandedetailscontent);
		initView();
		initData();
		initListener();
	}
	public static void startThisActivity(String id, Context context) {
		Intent intent = new Intent(context, IandEDetailsContentActivity.class);
		intent.putExtra("id", id);
		context.startActivity(intent);
	}
	public static void startThisActivity_url(String url, Context context) {
		Intent intent = new Intent(context, IandEDetailsContentActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}
	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn();
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
		
		String id = getIntent().getStringExtra("id");
		String geturl = getIntent().getStringExtra("url");
		String url = "";
		if (TextUtils.isEmpty(geturl)) {
			url = id;
		} else {
			url = geturl;
		}
		
		refreshTask(url);
	}

	private void refreshTask(String url) {
		
		
	}
	private void initListener() {
		
		
	}

}
