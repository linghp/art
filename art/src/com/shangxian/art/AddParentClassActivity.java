package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;

/**
 * 添加父类
 * @author Administrator
 *
 */
public class AddParentClassActivity extends BaseActivity{

	ImageView addimg;//添加分类图片
	EditText addname;//添加分类名称
	TextView quxiao,baocun;//取消、保存
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addparentclass);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		addimg = (ImageView) findViewById(R.id.addparentclass_addimg);
		addname = (EditText) findViewById(R.id.addparentclass_addname);
		quxiao = (TextView) findViewById(R.id.addparentclass_quxiao);
		baocun = (TextView) findViewById(R.id.addparentclass_baocun);
		
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}

	private void initListener() {
		// TODO Auto-generated method stub
		quxiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>取消");
				finish();
			}
		});
		baocun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>保存");
			}
		});
	}
	
}
