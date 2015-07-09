package com.shangxian.art;

import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ShengQingJieSuanModel;
import com.shangxian.art.net.ShopsServer;
import com.shangxian.art.net.ShopsServer.OnHttpShengQingJieSuanListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

/**
 * 申请结算
 * @author zyz
 *
 */
public class AskJieSuanActivity extends BaseActivity{
	private EditText et_name,et_phone,et_num,et_price,et_content;
	private TextView huoqu,time,quxiao,baocun;
	private String name,phone,num,price,content,date;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_askjiesuan);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideCenterSearch();//隐藏搜索框
		topView.hideRightBtn_invisible();//隐藏右按钮
		topView.showTitle();
		topView.setTitle("紧急结算申请");
		topView.setBack(R.drawable.back);//返回

		et_name = (EditText) findViewById(R.id.askjiesuan_name);//申请人姓名
		et_phone = (EditText) findViewById(R.id.askjiesuan_phone);//手机号码
		et_num = (EditText) findViewById(R.id.askjiesuan_num);//验证码
		et_price = (EditText) findViewById(R.id.askjiesuan_price);//金额
		et_content = (EditText) findViewById(R.id.askjiesuan_content);//情况说明

		huoqu = (TextView) findViewById(R.id.askjiesuan_huoqu);//获取
		time  =(TextView) findViewById(R.id.askjiesuan_day);//结算日期
		quxiao = (TextView) findViewById(R.id.askjiesuan_quxiao);//取消
		baocun = (TextView) findViewById(R.id.askjiesuan_baocun);//保存
		
	}

	private void upDataView() {
		if ("".equals(name)) {
			myToast("请输入申请人姓名");
		}else if ("".equals(phone)) {
			myToast("请输入申请人手机");
		}else if ("".equals(price)) {
			myToast("请输入申请金额");
		}else if ("".equals(content)) {
			myToast("请输入情况说明");
		}else if ("".equals(date)) {
			myToast("请输入日期");
		}
	}
	private void initData() {
	}
	private void initListener() {
		time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 选择日期
				AlertDialog.Builder builder = new AlertDialog.Builder(AskJieSuanActivity.this);  
				View view = View.inflate(AskJieSuanActivity.this, R.layout.dialog_datepicker, null);  
				final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);  
				builder.setView(view);  

				Calendar cal = Calendar.getInstance();  
				cal.setTimeInMillis(System.currentTimeMillis());  
				datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);  
				time.setInputType(InputType.TYPE_NULL);  

				builder.setTitle("选取结算日期");  
				builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {  
					@Override 
					public void onClick(DialogInterface dialog, int which) {  
						StringBuffer sb = new StringBuffer();  
						sb.append(String.format("%d-%02d-%02d",   
								datePicker.getYear(),   
								datePicker.getMonth() + 1,  
								datePicker.getDayOfMonth()));   
						time.setText(sb);  
						dialog.cancel();  
					}  
				}); 
				Dialog dialog = builder.create();  
				dialog.show(); 
			}
		});
		huoqu.setOnClickListener(new OnClickListener() {
			//获取验证码
			@Override
			public void onClick(View arg0) {

			}
		});
		quxiao.setOnClickListener(new OnClickListener() {
			//取消
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		baocun.setOnClickListener(new OnClickListener() {
			//保存(跳转到已申请)
			@Override
			public void onClick(View arg0) {
				name = et_name.getText().toString().trim();
				phone = et_phone.getText().toString().trim();
				price = et_price.getText().toString().trim();
				content = et_content.getText().toString().trim();
				date = time.getText().toString().trim();
				upDataView();
				
//				CommonUtil.gotoActivity(AskJieSuanActivity.this, YiJieSuanActivity.class, false);
				ShopsServer.toShenQingJieSuan(date, name, phone, price, content, new OnHttpShengQingJieSuanListener() {
					@Override
					public void onHttpShengQingJieSuan(String res) {
						myToast(res);
						if ("申请成功".equals(res)) {
							finish();
						}
					}
				});
			}
		});

	}
}
