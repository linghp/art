package com.shangxian.art.dialog;

import com.shangxian.art.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PayPasswordDialog extends Dialog {
	int[] imgs = new int[]{
			R.drawable.paymentpass0,
			R.drawable.paymentpass1,
			R.drawable.paymentpass2,
			R.drawable.paymentpass3,
			R.drawable.paymentpass4,
			R.drawable.paymentpass5,
			R.drawable.paymentpass6
	};
	
	private EditText et_pass;
	private ImageView iv_pass;
	private ImageView iv_dis;
	private TextView tv_money;
	private TextView tv_type;
	private String money;
	private String type;
	private String pass;
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			pass = et_pass.getText().toString();
			if (TextUtils.isEmpty(pass)) {
				iv_pass.setImageResource(imgs[0]);
			} else {
				int passlen = pass.length();
				iv_pass.setImageResource(imgs[passlen >= imgs.length - 1 ? imgs.length - 1 : passlen <= 0 ? 0 : passlen]);
				if (passlen == 6 && mListener != null) {
					dismiss();
					mListener.onScan(pass);
				}
			}
		};
	};

	private OnScanedListener mListener;

	public PayPasswordDialog(Context context) {
		super(context, android.R.style.Theme_Translucent);
		money = "¥ 0.00";
		type = "暂无";
	}

	public PayPasswordDialog(Context context, int theme) {
		super(context, android.R.style.Theme_Translucent);
		money = "¥ 0.00";
		type = "暂无";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
//				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_pay_password);
		setCanceledOnTouchOutside(false);
		ininView();
		initListener();
	}
	
	private void ininView() {
		et_pass = (EditText) findViewById(R.id.pade_et_pass);
		
		iv_pass = (ImageView) findViewById(R.id.padi_iv_pass);
		iv_dis = (ImageView) findViewById(R.id.padi_iv_dis);
		
		tv_money = (TextView) findViewById(R.id.padt_tv_money);
		tv_type = (TextView) findViewById(R.id.padt_tv_type);
		
		upViewData();
	}

	private void upViewData() {
		tv_money.setText(money);
		tv_type.setText(type);
	}

	private void initListener() {
		et_pass.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				handler.sendEmptyMessage(0);
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			@Override
			public void afterTextChanged(Editable s) {}
		});
		
		iv_dis.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	
	public void setMoney(String money){
		if (!TextUtils.isEmpty(money)) {
			if (tv_money != null) {
				tv_money.setText(money);
			}
			this.money = money;
		}
	}
	
	public void setType(String type){
		if (!TextUtils.isEmpty(type)) {
			if (tv_type != null) {
				tv_type.setText(type);
			}
			this.type = type;
		}
	}
	
	public void setOnScanedListener(OnScanedListener listener){
		this.mListener = listener;
	}
	
	public interface OnScanedListener{
		void onScan(String pass);
	}
}
