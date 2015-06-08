package com.shangxian.art;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;
/**
 * 评价商品
 * @author zyz
 *
 */
public class ReviewsProductActivity extends BaseActivity {
	private TextView tv_quxiao,tv_tijiao,tv_gongkai,tv_niming;
	private RatingBar ratingbar;
	private LinearLayout ll_view;
	float ratings = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reviewsproduct);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle("评价商品");

		tv_quxiao = (TextView) findViewById(R.id.reviewsproduct_quxiao);//取消
		tv_tijiao = (TextView) findViewById(R.id.reviewsproduct_baocun);//提交
		tv_gongkai = (TextView) findViewById(R.id.reviewsproduct_gongkai);//公开
		tv_niming = (TextView) findViewById(R.id.reviewsproduct_niming);//匿名
		ratingbar = (RatingBar) findViewById(R.id.reviewsproduct_starRating);//评分
		
		ll_view = (LinearLayout) findViewById(R.id.reviewsproduct_linear2);
	}

	private void initData() {
		// 设置评星星级
//		ratingbar.setRating(ratings);
		//添加布局
		View child = inflater.inflate(
				R.layout.item_reviewsproduct, null);
		ll_view.addView(child);
	}

	private void initListener() {
		ratingbar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				myToast(""+rating);
				
			}
		});
		tv_quxiao.setOnClickListener(new OnClickListener() {
			//取消
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tv_tijiao.setOnClickListener(new OnClickListener() {
			//提交
			@Override
			public void onClick(View v) {
				

			}
		});
		tv_gongkai.setOnClickListener(new OnClickListener() {
			//公开
			@Override
			public void onClick(View v) {
				Drawable drawable = getResources().getDrawable(R.drawable.sel_t);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
				tv_gongkai.setCompoundDrawables(drawable, null, null,null);//画在左边
				Drawable drawable1 = getResources().getDrawable(R.drawable.sel_n);
				drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight()); //设置边界
				tv_niming.setCompoundDrawables(drawable1, null, null,null);//画在左边
			}
		});
		tv_niming.setOnClickListener(new OnClickListener() {
			//匿名
			@Override
			public void onClick(View v) {
				Drawable drawable = getResources().getDrawable(R.drawable.sel_t);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
				tv_niming.setCompoundDrawables(drawable, null, null,null);//画在左边
				Drawable drawable1 = getResources().getDrawable(R.drawable.sel_n);
				drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight()); //设置边界
				tv_gongkai.setCompoundDrawables(drawable1, null, null,null);//画在左边
			}
		});

		
	}

}
