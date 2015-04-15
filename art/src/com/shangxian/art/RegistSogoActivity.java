package com.shangxian.art;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

/**
 * 注册商铺
 * @author libo
 * @time 2015/4/9 ...
 */
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
	private EditText et_loc;
	private EditText et_nickname;
	private EditText et_shouji;
	private EditText et_cardid;
	private ImageView iv_zpic;
	private ImageView iv_fpiac;
	private TextView tv_no3;
	private TextView tv_next3;
	private EditText et_backuser;
	private EditText et_bank;
	private EditText et_bankcity;
	private EditText et_subbank;
	private EditText et_account;
	private TextView tv_submit;
	
	private void initData() {
		anim_right_in = AnimationUtils.loadAnimation(this, R.anim.anim_right_in); 
		anim_left_out = AnimationUtils.loadAnimation(this, R.anim.anim_left_out); 
	}

	private void initView() {
		//改变topbar
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_regist_sogo));
		
		tv_title = (TextView) findViewById(R.id.sogt_tv_title);
		ll_li1 = (LinearLayout) findViewById(R.id.sogl_ll_li1);
		ll_li2 = (LinearLayout) findViewById(R.id.sogl_ll_li2);
		ll_li3 = (LinearLayout) findViewById(R.id.sogl_ll_li3);
		ll_li4 = (LinearLayout) findViewById(R.id.sogl_ll_li4);
		initRegist();
		initSogoInfo();
		initUi3();
		ininUi4();
		showView(UI_LI1);
	}

	/**
	 * 加载界面4
	 */
	private void ininUi4() {
		et_backuser = (EditText) findViewById(R.id.soge_et_bankuser);
		et_bank = (EditText) findViewById(R.id.soge_et_bank);
		et_bankcity = (EditText) findViewById(R.id.soge_et_bankcity);
		et_subbank = (EditText) findViewById(R.id.soge_et_subbank);
		et_account = (EditText) findViewById(R.id.soge_et_account);
		
		tv_submit = (TextView) findViewById(R.id.sogt_tv_submit);
	}

	private void initUi3() {
		et_loc = (EditText) findViewById(R.id.soge_et_loc);
		et_nickname = (EditText) findViewById(R.id.soge_et_nickname);
		et_shouji = (EditText) findViewById(R.id.soge_et_shouji);
		et_cardid = (EditText) findViewById(R.id.soge_et_cardid);
		
		iv_zpic = (ImageView) findViewById(R.id.sogi_iv_zpic);
		iv_fpiac = (ImageView) findViewById(R.id.sogi_iv_fpic);
		
		tv_no3 = (TextView) findViewById(R.id.sogt_tv_no3);
		tv_next3 = (TextView) findViewById(R.id.sogt_tv_next3);
		
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
	private static final int TO_PIC_UI3_Z = 0x00117;
	private static final int TO_PIC_UI3_F = 0x00118;

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
			tv_title.setText("使用手机号注册爱农谷账号");
			ll_li1.setVisibility(View.VISIBLE);
			ll_li2.setVisibility(View.GONE);
			ll_li3.setVisibility(View.GONE);
			ll_li4.setVisibility(View.GONE);
			break;
		case UI_LI2:
			tv_title.setText("请提供你的商铺信息");
			ll_li1.startAnimation(anim_left_out);
			ll_li1.setVisibility(View.GONE);
			ll_li2.startAnimation(anim_right_in);
			ll_li2.setVisibility(View.VISIBLE);
			ll_li3.setVisibility(View.GONE);
			ll_li4.setVisibility(View.GONE);
			break;
		case UI_LI3:
			tv_title.setText("请提供法定代表信息");
			ll_li1.setVisibility(View.GONE);
			ll_li2.startAnimation(anim_left_out);
			ll_li2.setVisibility(View.GONE);
			ll_li3.startAnimation(anim_right_in);
			ll_li3.setVisibility(View.VISIBLE);
			ll_li4.setVisibility(View.GONE);
			break;
		case UI_LI4:
			tv_title.setText("请设置商铺银行账户信息");
			ll_li1.setVisibility(View.GONE);
			ll_li2.setVisibility(View.GONE);
			ll_li3.startAnimation(anim_left_out);
			ll_li3.setVisibility(View.GONE);
			ll_li4.startAnimation(anim_right_in);
			ll_li4.setVisibility(View.VISIBLE);
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
	private String ui2_name;
	private String ui2_charter;
	private String ui2_location;
	private String ui2_totime;
	private String ui2_address;
	private String ui2_cell;
	private String ui3_loc;
	private String ui3_nickname;
	private String ui3_shouji;
	private String ui3_cardid;
	private String ui4_bankuser;
	private String ui4_bank;
	private String ui4_bankcity;
	private String ui4_subbank;
	private String ui4_account;
	private AlertDialog dialog;
	
	private void initListener() {
		rl_yan.setOnClickListener(this);
		tv_next1.setOnClickListener(this);
		tv_no1.setOnClickListener(this);
		tv_no2.setOnClickListener(this);
		tv_next2.setOnClickListener(this);
		iv_lipic.setOnClickListener(this);
		iv_zpic.setOnClickListener(this);
		iv_fpiac.setOnClickListener(this);
		tv_no3.setOnClickListener(this);
		tv_next3.setOnClickListener(this);
		tv_submit.setOnClickListener(this);
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
			next2();
		} else if (v == tv_no2) {
			no1();
		} else if (v == iv_lipic) {
			SelectImgUtil.toSelectImg(this, TO_PIC_UI2);
		} else if (v == iv_zpic) {
			SelectImgUtil.toSelectImg(this, TO_PIC_UI3_Z);
		} else if (v == iv_fpiac) {
			SelectImgUtil.toSelectImg(this, TO_PIC_UI3_F);
		} else if (v == tv_no3) {
			no1();
		} else if (v == tv_next3) {
			next3();
		} else if (v == tv_submit) {
			submit();
		}
	}
	
	@SuppressLint("NewApi")
	private void submit() {
		if (match4()) {
			dialog = new AlertDialog.Builder(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen).setView(initDialog()).show();
			dialog.setCanceledOnTouchOutside(false);
		}
	}

	private View initDialog() {
		View view = getLayoutInflater().inflate(R.layout.dialog_registsogo, null);
		view.findViewById(R.id.sodt_tv_ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		return view;
	}

	private boolean match4() {
		ui4_bankuser = et_backuser.getText().toString();
		ui4_bank = et_bank.getText().toString();
		ui4_bankcity = et_bankcity.getText().toString();
		ui4_subbank = et_subbank.getText().toString();
		ui4_account = et_account.getText().toString();
		if (TextUtils.isEmpty(ui4_bankuser)) {
			myToast("银行开户名不能为空");
			return false;
		}
		if (TextUtils.isEmpty(ui4_bank)) {
			myToast("开户银行不能为空");
			return false;
		}
		if (TextUtils.isEmpty(ui4_bankcity)) {
			myToast("银行所在城市不能为空");
			return false;
		}
		if (TextUtils.isEmpty(ui4_subbank)) {
			myToast("支行名称不能为空");
			return false;
		}
		if (TextUtils.isEmpty(ui4_account)) {
			myToast("门店对公账户不能为空");
			return false;
		}
		return true;
	}

	private void next3() {
		if (match3()) {
			showView(UI_LI4);
		}
	}

	private boolean match3() {
		ui3_loc = et_loc.getText().toString();
		ui3_nickname = et_nickname.getText().toString();
		ui3_shouji = et_shouji.getText().toString();
		ui3_cardid = et_cardid.getText().toString();
		if (TextUtils.isEmpty(ui3_loc)) {
			myToast("法定代表人归属地不能为空");
			return false;
		}
		if (TextUtils.isEmpty(ui3_nickname)) {
			myToast("法定代表人真实姓名不能为空");
			return false;
		}
		if (TextUtils.isEmpty(ui3_shouji)) {
			myToast("法定代表人手机号不能为空");
			return false;
		}
		if (TextUtils.isEmpty(ui3_cardid)) {
			myToast("法定代表人身份证号码不能为空");
			return false;
		}
		return true;
	}

	private void next2() {
		if (match2()) {
			showView(UI_LI3);
		}
	}

	private void next1() {
		if (match1()) {
			showView(UI_LI2);
		}
	}
	/**
	 * 匹配界面2输入内容格式
	 * @return
	 */
	private boolean match2() {
		ui2_name = et_name.getText().toString();
		ui2_charter = et_charter.getText().toString();
		ui2_location = et_location.getText().toString();
		ui2_totime = et_totime.getText().toString();
		ui2_address = et_addresss.getText().toString();
		ui2_cell = et_cell.getText().toString();
		if (TextUtils.isEmpty(ui2_name)) {
			myToast("商铺名称不能为空");
			return false;
		}
		
		if (TextUtils.isEmpty(ui2_charter)) {
			myToast("营业执照注册号不能为空");
			return false;
		}
		
		if (TextUtils.isEmpty(ui2_location)) {
			myToast("营业执照所在地不能为空");
			return false;
		}
		
		if (TextUtils.isEmpty(ui2_totime)) {
			myToast("营业执照期限不能为空");
			return false;
		}
		
		if (TextUtils.isEmpty(ui2_address)) {
			myToast("常用地址不能为空");
			return false;
		}
		
		if (TextUtils.isEmpty(ui2_cell)) {
			myToast("联系电话不能为空");
			return false;
		}	
		return true;
	}

	private String picPath_ui2;
	private Bitmap bitmap_ui2;
	private String picPath_ui3_z;
	private Bitmap bitmap_ui3_z;
	private String picPath_ui3_f;
	private Bitmap bitmap_ui3_f;
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent data) {
		//super.onActivityResult(arg0, arg1, data);
		if (arg1 == RESULT_OK) {
			if (arg0 == TO_PIC_UI2) {
				picPath_ui2 = SelectImgUtil.getImgPath(this, data);
				BitmapFactory.Options options = new BitmapFactory.Options();
		        options.inSampleSize = 2;
		        bitmap_ui2 = BitmapFactory.decodeFile(picPath_ui2, options);
				iv_lipic.setImageBitmap(bitmap_ui2);
			} else if (arg0 == TO_PIC_UI3_Z) {
				picPath_ui3_z = SelectImgUtil.getImgPath(this, data);
				BitmapFactory.Options options = new BitmapFactory.Options();
		        options.inSampleSize = 2;
		        bitmap_ui3_z = BitmapFactory.decodeFile(picPath_ui3_z, options);
		        iv_zpic.setImageBitmap(bitmap_ui3_z);
			} else if (arg0 == TO_PIC_UI3_F) {
				picPath_ui3_f = SelectImgUtil.getImgPath(this, data);
				BitmapFactory.Options options = new BitmapFactory.Options();
		        options.inSampleSize = 2;
		        bitmap_ui3_f = BitmapFactory.decodeFile(picPath_ui3_f, options);
		        iv_fpiac.setImageBitmap(bitmap_ui3_f);	
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

	
	
	/**
	 * 匹配界面1输入内容格式
	 * @return
	 */
	private boolean match1() {
		phone = et_phone.getText().toString();
		yan = et_yan.getText().toString();
		pass = et_pass.getText().toString();
		repass = et_repass.getText().toString();
		if (TextUtils.isEmpty(phone)) {
			myToast("请输入手机号");
			return false;
		}
		if (TextUtils.isEmpty(yan)) {
			myToast("请输入验证码");
			return false;
		}
		if (TextUtils.isEmpty(pass)) {
			myToast("请输入密码");
			return false;
		}
		if (TextUtils.isEmpty(repass)) {
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
		if (bitmap_ui3_z != null) {
			bitmap_ui3_z.recycle();
		}
		if (bitmap_ui3_f != null) {
			bitmap_ui3_f.recycle();
		}
	}
}
