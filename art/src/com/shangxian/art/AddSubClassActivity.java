package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;


/**
 * 添加子类
 * @author Administrator
 *
 */
public class AddSubClassActivity extends BaseActivity{
	TextView xuanze,addname,quxiao,baocun;
	ImageView addimg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addsubclass);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		xuanze = (TextView) findViewById(R.id.addsubclass_xuanze);
		addimg = (ImageView) findViewById(R.id.addsubclass_addimg);
		addname = (TextView) findViewById(R.id.addsubclass_addname);
		quxiao = (TextView) findViewById(R.id.addsubclass_quxiao);
		baocun = (TextView) findViewById(R.id.addsubclass_baocun);
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void initListener() {
		// TODO Auto-generated method stub
		xuanze.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>>>>选择父类");
			}
		});
		addimg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>>>>添加图片");
			}
		});
		addname.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>>>>添加分类名称");
			}
		});
		quxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>>>>取消");
			}
		});
		baocun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>>>>保存");
			}
		});
	}

}
