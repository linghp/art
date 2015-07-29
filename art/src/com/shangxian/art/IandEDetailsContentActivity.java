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
	public static void startThisActivity(String title, String price,String liushuihao,String fangxiang,String fangshi,String time,String beizhu,Context context) {
		Intent intent = new Intent(context, IandEDetailsContentActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("price", price);
		intent.putExtra("liushuihao", liushuihao);
		intent.putExtra("fangxiang", fangxiang);
		intent.putExtra("fangshi", fangshi);
		intent.putExtra("time", time);
		intent.putExtra("beizhu", beizhu);
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
		
		String title = getIntent().getStringExtra("title");
		String price = getIntent().getStringExtra("price");
		String liushuihao = getIntent().getStringExtra("liushuihao");
		String fangxiang = getIntent().getStringExtra("fangxiang");
		String fangshi = getIntent().getStringExtra("fangshi");
		String time = getIntent().getStringExtra("time");
		String beizhu = getIntent().getStringExtra("beizhu");
		tv_title.setText(title);
		tv_price.setText(price);
		tv_liushuihao.setText(liushuihao);
		tv_fangxiang.setText(fangxiang);
		if ("ALB_ALY".equals(fangshi.toString().trim())) {
			tv_fangshi.setText("爱农币和爱农元混合支付");
		}else if ("ALY".equals(fangshi.toString().trim())) {
			tv_fangshi.setText("爱农元支付");
		}else if ("ALB".equals(fangshi.toString().trim())) {
			tv_fangshi.setText("爱农币支付");
		}else if ("ALIPAY".equals(fangshi.toString().trim())) {
			tv_fangshi.setText("支付宝支付");
		}else if ("BANK".equals(fangshi.toString().trim())) {
			tv_fangshi.setText("银行支付");
		}else if ("ALL".equals(fangshi.toString().trim())) {
			tv_fangshi.setText("混合支付");
		}else if ("UNLINE".equals(fangshi.toString().trim())) {
			tv_fangshi.setText("线下支付");
		}else {
			tv_fangshi.setText(fangshi);
		}
		
		tv_time.setText(time);
		tv_beizhu.setText(beizhu);
//		tv_beizhu.setText("无");
		
		/*String url = "";
		if (TextUtils.isEmpty(geturl)) {
			url = id;
		} else {
			url = geturl;
		}
		
		refreshTask(url);*/
	}

	/*private void refreshTask(String url) {
		
		
	}*/
	private void initListener() {
		
		
	}

}
