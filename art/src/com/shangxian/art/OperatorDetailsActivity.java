package com.shangxian.art;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CommonBean;
import com.shangxian.art.bean.ShopOperatorBean;
import com.shangxian.art.net.BaseServer;
import com.shangxian.art.net.ShopOperatorServer;
import com.shangxian.art.net.ShopOperatorServer.OnHttpResultAddOperatorListener;
import com.shangxian.art.net.ShopOperatorServer.OnHttpResultDeleteOperatorListener;
import com.shangxian.art.view.TopView;

/**
 * 操作员管理详情
 * @author Administrator
 *
 */
public class OperatorDetailsActivity extends BaseActivity implements OnClickListener,OnHttpResultAddOperatorListener,OnHttpResultDeleteOperatorListener{

	private EditText et_user,et_name,et_id,et_num,et_pwd;
	private ShopOperatorBean shopOperatorBean;
	private String user,name,id,num,pwd;
	private String ismessage = "false";
	private TextView tv_false,tv_true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_operatordetails);
		initView();
		initData();
		initListener();
		//requestTask();
	}

	public static void startThisActivity(Activity context, ShopOperatorBean shopOperatorBean) {
		Intent intent = new Intent(context,
				OperatorDetailsActivity.class);
		intent.putExtra("shopOperatorBean", shopOperatorBean);
		context.startActivityForResult(intent, 1);
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_operatordetails));

		et_user = (EditText) findViewById(R.id.operatordetails_tv1);
		et_name = (EditText) findViewById(R.id.operatordetails_tv2);
		et_id = (EditText) findViewById(R.id.operatordetails_tv3);
		et_num = (EditText) findViewById(R.id.operatordetails_tv4);
		et_pwd = (EditText) findViewById(R.id.operatordetails_tv5);
		tv_false = (TextView) findViewById(R.id.addsubclass_tv1);
		tv_true = (TextView) findViewById(R.id.addsubclass_tv2);
		//		tv_role = (TextView) findViewById(R.id.operatordetails_tv5);//定义角色
		//		tv_purview = (TextView) findViewById(R.id.operatordetails_tv6);//权限

	}

	private void initData() {
		shopOperatorBean=(ShopOperatorBean) getIntent().getSerializableExtra("shopOperatorBean");
		if(shopOperatorBean!=null){
			updateViews();
		}
	}

	private void updateViews() {
		et_user.setText(shopOperatorBean.getUsername());
		et_name.setText(shopOperatorBean.getName());
		et_id.setText(shopOperatorBean.getCardNumber());
		et_num.setText(shopOperatorBean.getMobile());
		et_pwd.setText(shopOperatorBean.getPassword());
	}

	private void initListener() {
		tv_false.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 否
				tv_false.setBackgroundResource(R.drawable.leftcorner_green);
				tv_false.setTextColor(getResources().getColor(R.color.txt_white));
				tv_true.setBackgroundResource(R.drawable.rightcorner);
				tv_true.setTextColor(getResources().getColor(R.color.txt_green));
				//				ismessage = false;
				ismessage = "false";

			}
		});
		tv_true.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//是
				tv_false.setBackgroundResource(R.drawable.leftcorner);
				tv_false.setTextColor(getResources().getColor(R.color.txt_green));
				tv_true.setBackgroundResource(R.drawable.rightcorner_green);
				tv_true.setTextColor(getResources().getColor(R.color.txt_white));
				//				ismessage = true;
				ismessage = "true";
			}
		});

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_cancel:
			if(shopOperatorBean!=null){
				showProgressDialog(true);
				ShopOperatorServer.onDeleteOperator_xutils(shopOperatorBean.getId(), this);
			}
			break;
		case R.id.tv_ok:
			// 修改
			user = et_user.getText().toString().trim();//用户名
			name = et_name.getText().toString().trim();//真实姓名
			id = et_id.getText().toString().trim();//身份证
			num = et_num.getText().toString().trim();//电话号码
			pwd = et_pwd.getText().toString().trim();//mima
			if(match()){
				showProgressDialog(true);
				RequestParams params = BaseServer.getParams();
				MultipartEntity multipartEntity=new MultipartEntity();
				multipartEntity.setMultipartSubtype("multipart/form-data; boundary=--ling--");//加上这个就不报404了，坑
				params.setBodyEntity(multipartEntity);
				params.addQueryStringParameter("username", user);
				params.addQueryStringParameter("password", pwd);
				params.addQueryStringParameter("name", name);
				params.addQueryStringParameter("phoneNumber", num);
				params.addQueryStringParameter("cardNumber", id);
				ShopOperatorServer.onUpdateOperator_xutils(params, OperatorDetailsActivity.this);
			}
			break;

		default:
			break;
		}

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
		showProgressDialog(false);
		myToast(str);
		if(str.equals("修改成功")){
			setResult(RESULT_OK);
			finish();
		}
	}

	@Override
	public void onHttpResultDeleteOperator(CommonBean commonBean) {
		showProgressDialog(false);
		if(commonBean!=null){
			myToast(commonBean.getResult());
			if(commonBean.getResult_code().equals("200")){
				setResult(RESULT_OK);
				finish();
			}
		}else{
			myToast("删除失败");
		}
	}
}
