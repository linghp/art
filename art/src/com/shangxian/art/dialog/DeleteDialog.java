package com.shangxian.art.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.animation.SlideTop;

/**
 * 删除dialog
 * 
 * @author Administrator
 *
 */
public class DeleteDialog extends AlertDialog implements
		android.view.View.OnClickListener {
	private TextView tv_message;
	private Button button1,button2;
	private View ll_info;
//	private Animation anim_in;
//	private Animation anim_out;
	private String message;
    private Context context;
	private Delete_I delete_i;

	public interface Delete_I {
		void doDelete();
	}

	public DeleteDialog(Context context, Delete_I delete_i,String message) {
		super(context, android.R.style.Theme_Translucent);
		this.delete_i = delete_i;
		this.message=message;
		this.context=context;
		init();
	}

	public DeleteDialog(Context context, int theme) {
		super(context, android.R.style.Theme_Translucent);
		init();
	}

	private void init() {
//		anim_in = AnimationUtils.loadAnimation(getContext(),
//				R.anim.anim_bottom_in);
//		anim_out = AnimationUtils.loadAnimation(getContext(),
//				R.anim.anim_bottom_out);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_delete);
		setCanceledOnTouchOutside(true);
		initViews();
		initListener();
	}

	@Override
	public void show() {
		super.show();
		//ll_info.startAnimation(anim_in);
		SlideTop slideTop=new SlideTop();
		slideTop.setDuration(300);
		slideTop.start(ll_info);
	}

	@Override
	public void dismiss() {
		super.dismiss();
		//ll_info.startAnimation(anim_out);
//		anim_out.setAnimationListener(new AnimationListener() {
//			@Override
//			public void onAnimationStart(Animation animation) {
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				DeleteDialog.super.dismiss();
//			}
//		});
	}

	private void initViews() {
		tv_message = (TextView) findViewById(R.id.message);
		button1 =  (Button) findViewById(R.id.button1);
		button2 =  (Button) findViewById(R.id.button2);
		tv_message.setText(message);
		button1.setText("取消");
		button2.setText("确定");
		ll_info = findViewById(R.id.main);
	}


	private void initListener() {
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		ll_info.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == button1) {
			dismiss();
		} else if (button2 == v) {
			delete_i.doDelete();
			dismiss();
		}else if(v==ll_info){
			//CommonUtil.toast("click", context);
			dismiss();
		}
		
	}

}
