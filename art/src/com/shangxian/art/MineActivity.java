package com.shangxian.art;

import java.io.File;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.util.AbFileUtil;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.net.HttpUtils;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.LocalUserInfo;

public class MineActivity extends BaseActivity implements OnClickListener{
	private String username_local;
	private View ll_loginbefore,ll_loginafter;
	private TextView tv_username;
	private ImageView user_head;
	
	private String userphoto_filename;
	
	private LinearLayout shangpinguanli;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_mine);
		userphoto_filename = LocalUserInfo.getInstance(this)
                .getUserInfo(LocalUserInfo.USERPHOTO_FILENAME);
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
		
		shangpinguanli.setOnClickListener(new OnClickListener() {
			//跳转到商品管理
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
		user_head=(ImageView) findViewById(R.id.user_head);
		
		shangpinguanli = (LinearLayout) findViewById(R.id.ll_tab1);//商品管理
		changeview();
	}

	private void changeview() {
		if(isLogin()){
			ll_loginbefore.setVisibility(View.GONE);
			ll_loginafter.setVisibility(View.VISIBLE);
			tv_username=(TextView) findViewById(R.id.tv_username);
			tv_username.setText(username_local);
		}else{
			ll_loginbefore.setVisibility(View.VISIBLE);
			ll_loginafter.setVisibility(View.GONE);
		}
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
		topView.showRightBtn();
		topView.setRightBtnDrawable(R.drawable.settingbuttonimage);
		topView.setRightBtnListener(this);
		topView.hideCenterSearch();
		topView.setCenterListener(null);
		topView.setTitle("我的");
		topView.showTitle();
		//显示头像
		if(isLogin()){
        String userphoto_filename_temp = LocalUserInfo.getInstance(this)
                .getUserInfo(LocalUserInfo.USERPHOTO_FILENAME);
       // if (!userphoto_filename_temp.equals(userphoto_filename)) {
        	String imagelocaldir=AbFileUtil.getImageDownloadDir(this)+File.separator;
    		if (!TextUtils.isEmpty(userphoto_filename_temp)) {
    			File file = new File(imagelocaldir, userphoto_filename_temp);
    			// 从文件中找
    			if (file.exists()) {
    				// Log.i("aaaa", "image exists in file" + filename);
    				user_head.setImageBitmap(BitmapFactory.decodeFile(imagelocaldir
    						+ userphoto_filename_temp));
    			}
    		}
      //  }
		}else{
			user_head.setImageResource(R.drawable.defaultloginheader);
		}
	}
	
	public void doClick(View view){
		switch (view.getId()) {
		case R.id.ll_my_item1:
			//我的订单
			if(HttpUtils.checkNetWork(this)&&isLoginAndToLogin()){
			startActivity(new Intent(this, MyOrderActivity.class));
			}
			break;
		case R.id.ll_my_item2:
			//爱农卡
			if(isLoginAndToLogin())
			startActivity(new Intent(this, NongHeBaoActivity.class));
			break;
		case R.id.ll_my_item3:
			//我的关注
			if(isLoginAndToLogin())
			startActivity(new Intent(this, MyConcernActivity.class));
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_right:
			startActivityForResult((new Intent(this, AccountSecurityActivity.class)),2);
			break;
		default:
			break;
		}
	}
}
