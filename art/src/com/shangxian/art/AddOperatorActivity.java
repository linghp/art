package com.shangxian.art;

import java.io.File;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.multipart.HttpMultipartMode;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.net.BaseServer;
import com.shangxian.art.net.ShopOperatorServer;
import com.shangxian.art.net.ShopOperatorServer.OnHttpResultAddOperatorListener;
import com.shangxian.art.view.TopView;

public class AddOperatorActivity extends BaseActivity implements OnHttpResultAddOperatorListener{

	private EditText et_user,et_name,et_id,et_num,et_pwd;
	private TextView tv_baocun;
	private String user,name,id,num,pwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addoperator);
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
		topView.setTitle(getString(R.string.title_addoperator));
		
		et_user = (EditText) findViewById(R.id.addsubclass_et1);
		et_name = (EditText) findViewById(R.id.addsubclass_et2);
		et_id = (EditText) findViewById(R.id.addsubclass_et3);
		et_num = (EditText) findViewById(R.id.addsubclass_et4);
		et_pwd = (EditText) findViewById(R.id.addsubclass_et5);
		tv_baocun = (TextView) findViewById(R.id.addsubclass_tv);
	}

	private void initData() {
		
		
	}

	private void initListener() {
		tv_baocun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 保存
				user = et_user.getText().toString().trim();//用户名
				name = et_name.getText().toString().trim();//真实姓名
				id = et_id.getText().toString().trim();//身份证
				num = et_num.getText().toString().trim();//电话号码
				pwd = et_pwd.getText().toString().trim();//mima
				if(match()){
					RequestParams params = BaseServer.getParams();
					//params.setContentType("multipart/form-data; boundary=--ling--");
					//params.addBodyParameter("file", new File(path));
					MultipartEntity multipartEntity=new MultipartEntity();
					multipartEntity.setMultipartSubtype("multipart/form-data; boundary=--ling--");//加上这个就不报404了，坑
					params.setBodyEntity(multipartEntity);
					params.addQueryStringParameter("username", user);
					params.addQueryStringParameter("password", pwd);
					params.addQueryStringParameter("name", name);
					params.addQueryStringParameter("phoneNumber", num);
					params.addQueryStringParameter("cardNumber", id);
					ShopOperatorServer.onAddOperator_xutils(params, AddOperatorActivity.this);
//					List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
//					pairs.add(new BasicNameValuePair("username", user));
//					pairs.add(new BasicNameValuePair("password", pwd));
//					pairs.add(new BasicNameValuePair("name", name));
//					pairs.add(new BasicNameValuePair("phoneNumber", num));
//					pairs.add(new BasicNameValuePair("cardNumber", id));
//					ShopOperatorServer.onAddOperator(pairs, AddOperatorActivity.this);
				}
			}
		});
		
	}
	
	private boolean match() {
		if(TextUtils.isEmpty(user)){
			myToast("用户名不能为空");
			return false;
		}else if(TextUtils.isEmpty(name)){
			myToast("真实姓名不能为空");
			return false;
		}else if(TextUtils.isEmpty(name)){
			myToast("身份证号码不能为空");
			return false;
		}else if(TextUtils.isEmpty(name)){
			myToast("手机号码不能为空");
			return false;
		}else if(TextUtils.isEmpty(name)){
			myToast("密码不能为空");
			return false;
		}
		return true;
	}

	@Override
	public void onHttpResultAddOperator(String str) {
		myToast(str);
		if(str.equals("添加成功")){
			setResult(RESULT_OK);
			finish();
		}
	}
}
