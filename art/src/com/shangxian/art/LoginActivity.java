package com.shangxian.art;

import com.shangxian.art.base.BaseActivity;

import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener{

	private EditText et_pass;
	private EditText et_user;
	private TextView tv_login;
	private TextView tv_find;
	private TextView tv_regist;
	private String user;
	private String pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_login);
		initDate();
		initView();
		initListener();
	}
	
	public void initDate(){
		
	}
	
	public void initView(){
		et_user = (EditText) findViewById(R.id.loge_et_user);
		et_pass = (EditText) findViewById(R.id.loge_et_pass);
		
		tv_login = (TextView) findViewById(R.id.logt_tv_login);
		tv_find = (TextView) findViewById(R.id.logt_tv_find);
		tv_regist = (TextView) findViewById(R.id.logt_tv_regist);
	} 
	
	public void initListener(){
		tv_login.setOnClickListener(this);
		tv_find.setOnClickListener(this);
		tv_regist.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == tv_login) {
			if (mathLogin()) {
				Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
			}
		} else if (v == tv_find) {
			Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();	
		} else if (v == tv_regist){
			Toast.makeText(this, "注册", Toast.LENGTH_SHORT).show();
		}
	}

	private boolean mathLogin() {
		user = et_user.getText().toString();
		pass = et_pass.getText().toString();
		if (TextUtils.isEmpty(user)) {
			return false;
		}
		if (TextUtils.isEmpty(pass)){
			return false;
		}
		return true;
	}
}
