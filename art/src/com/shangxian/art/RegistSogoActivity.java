package com.shangxian.art;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.utils.SelectImgUtil;
import com.shangxian.art.view.TopView;

public class RegistSogoActivity extends BaseActivity implements OnClickListener{

	private TextView tv_title;
	private LinearLayout ll_li1;
	private LinearLayout ll_li2;
	private LinearLayout ll_li3;
	private LinearLayout ll_li4;
	private EditText et_phone;
	private EditText et_yan;
	private EditText et_pass;
	private EditText et_repass;
	private TextView tv_getyan;
	private TextView tv_next1;
	private TextView tv_no1;
	private LinearLayout ll_yanloading;
	private RelativeLayout rl_yan;
	private boolean isToYan = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_registso);
		thread.start();
		initData();
		initView();
		initListener();
	}

	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case 0:
				if (timer <= 0) {
					isToYan = false;
					showView(NORMAL);
					et_yan.setText("");
				} else {
					tv_getyan.setText(timer -- + "秒重新获取");
				}
				break;
			}
		};
	};
	
	private int timer;
	private Animation anim_right_in;
	private Animation anim_left_out;
	private EditText et_name;
	private EditText et_charter;
	private EditText et_location;
	private EditText et_totime;
	private EditText et_addresss;
	private EditText et_cell;
	private TextView tv_no2;
	private TextView tv_next2;
	private ImageView iv_lipic;
	
	private void initData() {
		anim_right_in = AnimationUtils.loadAnimation(this, R.anim.anim_right_in); 
		anim_left_out = AnimationUtils.loadAnimation(this, R.anim.anim_right_in); 
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		//topView.showBackBtn();
		topView.setTitle("爱农谷");
		topView.hideCenterSearch();
		topView.hideLeftBtn();
		topView.hideRightBtn();
		
		tv_title = (TextView) findViewById(R.id.sogt_tv_title);
		ll_li1 = (LinearLayout) findViewById(R.id.sogl_ll_li1);
		ll_li2 = (LinearLayout) findViewById(R.id.sogl_ll_li2);
		//ll_li3 = (LinearLayout) findViewById(R.id.sogl_ll_li3);
		//ll_li4 = (LinearLayout) findViewById(R.id.sogl_ll_li4);
		initRegist();
		initSogoInfo();
		showView(UI_LI2);
	}

	private void initSogoInfo() {
		et_name = (EditText) findViewById(R.id.soge_et_name);//商铺名
		et_charter = (EditText) findViewById(R.id.soge_et_charter);//执照注册号
		et_location = (EditText) findViewById(R.id.soge_et_location);//执照所在地
		et_totime = (EditText) findViewById(R.id.soge_et_totime);//营业期限
		et_addresss = (EditText) findViewById(R.id.soge_et_address);
		et_cell = (EditText) findViewById(R.id.soge_et_cell);
		
		tv_no2 = (TextView) findViewById(R.id.sogt_tv_no2);
		tv_next2 = (TextView) findViewById(R.id.sogt_tv_next2);
		
		iv_lipic = (ImageView) findViewById(R.id.sogi_iv_license);
	}

	private void initRegist() {
		et_phone = (EditText) findViewById(R.id.soge_et_phone);
		et_yan = (EditText) findViewById(R.id.soge_et_yan);
		et_pass = (EditText) findViewById(R.id.soge_et_pass);
		et_repass = (EditText) findViewById(R.id.soge_et_repass);
		
		tv_getyan = (TextView) findViewById(R.id.sogt_tv_getyan);
		tv_next1 = (TextView) findViewById(R.id.sogt_tv_next1);
		tv_no1 = (TextView) findViewById(R.id.sogt_tv_no1);
		
		ll_yanloading = (LinearLayout) findViewById(R.id.sogl_ll_loading);
		
		rl_yan = (RelativeLayout) findViewById(R.id.sogr_rl_yan);
		showView(NORMAL);
	}
	private static final int TO_PIC_UI2 = 0x00116;
	private static final int TO_PIC_UI3_Z = 0x100117;
	private static final int TO_PIC_UI3_F = 0x100118;

	private static final int NORMAL = 1001;
	private static final int LOADING = 1002;
	private static final int TOYAN = 1003;
	
	private static final int UI_LI1 = 2001;
	private static final int UI_LI2 = 2002;
	private static final int UI_LI3 = 2003;
	private static final int UI_LI4 = 2004;
	private int curShow;
	private void showView(int show){
		curShow = show;
		switch (show) {
		case NORMAL:
			tv_getyan.setVisibility(View.VISIBLE);
			ll_yanloading.setVisibility(View.INVISIBLE);
			rl_yan.setSelected(false);
			tv_getyan.setText("获取验证码");
			break;
		case LOADING:
			tv_getyan.setVisibility(View.INVISIBLE);
			ll_yanloading.setVisibility(View.VISIBLE);
			rl_yan.setSelected(true);
			break;
		case TOYAN:
			tv_getyan.setVisibility(View.VISIBLE);
			ll_yanloading.setVisibility(View.INVISIBLE);
			rl_yan.setSelected(true);
			timer  = 60;
			tv_getyan.setText(timer -- + "秒重新获取");
			isToYan = true;	
			break;
		case UI_LI1:
			topView.setTitle("使用手机号注册爱农谷账号");
			ll_li1.setVisibility(View.VISIBLE);
			ll_li2.setVisibility(View.GONE);
			//ll_li3.setVisibility(View.GONE);
			//ll_li4.setVisibility(View.GONE);
			break;
		case UI_LI2:
			topView.setTitle("请提供你的商铺信息");
			ll_li1.setVisibility(View.GONE);
			ll_li2.setVisibility(View.VISIBLE);
			//ll_li3.setVisibility(View.GONE);
			//ll_li4.setVisibility(View.GONE);
			break;
		case UI_LI3:
			topView.setTitle("请提供法定代表信息");
			ll_li1.setVisibility(View.GONE);
			ll_li2.setVisibility(View.GONE);
			//ll_li3.setVisibility(View.VISIBLE);
			//ll_li4.setVisibility(View.GONE);
			break;
		case UI_LI4:
			topView.setTitle("请设置商铺银行账户信息");
			ll_li1.setVisibility(View.GONE);
			ll_li2.setVisibility(View.GONE);
			//ll_li3.setVisibility(View.GONE);
			//ll_li4.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	Thread thread = new Thread(){
		public void run() {
			while (true) {
				if (isToYan) {
					try {
						Thread.sleep(1000);
						mHandler.sendEmptyMessage(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	};
	private String phone;
	private String yan;
	private String pass;
	private String repass;
	
	private void initListener() {
		//tv_getyan.setOnClickListener(this);
		rl_yan.setOnClickListener(this);
		tv_next1.setOnClickListener(this);
		tv_no1.setOnClickListener(this);
		tv_no2.setOnClickListener(this);
		tv_next2.setOnClickListener(this);
		iv_lipic.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == rl_yan) {
			phone = et_phone.getText().toString();
			if (!TextUtils.isEmpty(phone)) {
				if (!isToYan) {
					getYan();
				}
			} else {
				myToast("手机号不能为空");
			}
		} else if (v == tv_next1){
			next1();
		} else if (v == tv_no1) {
			no1();
		} else if (v == tv_next2) {
			
		} else if (v == tv_no2) {
			no1();
		} else if (v == iv_lipic) {
			//liPic();
			SelectImgUtil.toSelectImg(this, TO_PIC_UI2);
		}
	}
	
	private String picPath_ui2;
	private Bitmap bitmap_ui2;
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent data) {
		//super.onActivityResult(arg0, arg1, data);
		if (arg1 == RESULT_OK) {
			System.out.println("reqid =============== " + arg0 + "    " + (arg0 == TO_PIC_UI2) + "   "+ arg0);
			if (arg0 == TO_PIC_UI2) {
				picPath_ui2 = SelectImgUtil.getImgPath(this, data);
				BitmapFactory.Options options = new BitmapFactory.Options();
		        options.inSampleSize = 2;
		        bitmap_ui2 = BitmapFactory.decodeFile(picPath_ui2, options);
		        System.out.println("bit ================ " + (bitmap_ui2 == null) + " ===========  " + picPath_ui2);
				iv_lipic.setImageBitmap(bitmap_ui2);
			} else if (arg0 == TO_PIC_UI3_Z) {
				
			} else if (arg0 == TO_PIC_UI3_F) {
				
			}
		}
	}
	
	/**
	 * 获取验证码
	 */
	private void getYan() {
		showView(LOADING);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				showView(TOYAN);
			}
		}, 2000);
	}

	private void next1() {
		if (match1()) {
			showView(UI_LI2);
		}
	}

	private boolean match1() {
		phone = et_phone.getText().toString();
		yan = et_yan.getText().toString();
		pass = et_pass.getText().toString();
		repass = et_repass.getText().toString();
		if (TextUtils.isEmpty(phone)) {
			myToast("请输入手机号");
			return false;
		}
		if (TextUtils.isEmpty(phone)) {
			myToast("请输入验证码");
			return false;
		}
		if (TextUtils.isEmpty(phone)) {
			myToast("请输入密码");
			return false;
		}
		if (TextUtils.isEmpty(phone)) {
			myToast("请重复您的密码");
			return false;
		}
		if (!pass.equals(repass)) {
			myToast("两次密码不一致");
			return false;
		}
		return true;
	}

	private void no1() {
		finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (bitmap_ui2 != null) {
			bitmap_ui2.recycle();
		}
	}
}
