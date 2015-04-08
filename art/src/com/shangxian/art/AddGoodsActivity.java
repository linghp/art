package com.shangxian.art;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shangxian.art.base.BaseActivity;

/**
 * 添加商品
 * @author Administrator
 *
 */
public class AddGoodsActivity extends BaseActivity{
	EditText miaoshu;
	TextView zishu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addgoods);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		miaoshu = (EditText) findViewById(R.id.addgoods_edit);
		zishu = (TextView) findViewById(R.id.addgoods_zishu_txt);
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}

	private void initListener() {
		// TODO Auto-generated method stub
		TextWatcher watcher = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
//				System.out.println(">>>>>>>>>>>>>输入了"+s.length()+"个字符");
				zishu.setText(s.length()+"/150");
				if (s.length() > 150) {
					myToast("您输入的字数已超过150字");
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		};
		miaoshu.addTextChangedListener(watcher);
	}
}
