package com.shangxian.art;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.utils.CommonUtil;

public class MineActivity extends BaseActivity {
	private String username_local;
	private View ll_loginbefore,ll_loginafter;
	private TextView tv_username;
	
	
	private ImageView user_head;
	
	private String userphoto_filename;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_mine);
		initView();
		initListener();
	}

	private void initListener() {
		findViewById(R.id.mine).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MineActivity.this, LoginActivity.class);
				startActivityForResult(intent, 1);
			}
		});
		//商品管理
		findViewById(R.id.ll_tab1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonUtil.gotoActivity(MineActivity.this, MerchandiseControlActivity.class, false);
			}
		});
	}

	private void initView() {
		ll_loginbefore=findViewById(R.id.ll_loginbefore);
		ll_loginafter=findViewById(R.id.ll_loginafter);
		changeview();
	}

	private void changeview() {
		if(islogin()){
			ll_loginbefore.setVisibility(View.GONE);
			ll_loginafter.setVisibility(View.VISIBLE);
			tv_username=(TextView) findViewById(R.id.tv_username);
			tv_username.setText(username_local);
		}else{
			ll_loginbefore.setVisibility(View.VISIBLE);
			ll_loginafter.setVisibility(View.GONE);
		}
	}

	private boolean islogin() {
		SharedPreferences preferences = getSharedPreferences("userinfo",
				MODE_PRIVATE);
		username_local = preferences.getString("username", "");
		return !TextUtils.isEmpty(username_local);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		changeview();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mine, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		topView = MainActivity.getTopView();
		topView.setActivity(this);
		topView.hideLeftBtn();
		topView.hideRightBtn();
		topView.hideCenterSearch();
		topView.setCenterListener(null);
		topView.setTitle("我的");
		topView.showTitle();
	}
	
	public void doClick(View view){
		switch (view.getId()) {
		case R.id.iv_settingbutton:
			startActivityForResult((new Intent(this, AccountSecurityActivity.class)),2);
			break;

		default:
			break;
		}
	}
}
