package com.shangxian.art;

import java.io.Serializable;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.view.HackyViewPager;

/**
 * @Description 
 * @author ling
 * @date 2015年6月10日
 */
public class PhotoViewPagerActivity extends BaseActivity {
	
	private ViewPager mViewPager;
	private TextView textView;
	private MenuItem menuLockItem;
	private List<String> photos;
	private int position;
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewpager);
//        textView = (TextView) findViewById(R.id.viewparent_tv);
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
		setContentView(mViewPager);
		initData();
		if(photos!=null&&photos.size()>0){
		mViewPager.setAdapter(new SamplePagerAdapter(photos,this));
		mViewPager.setCurrentItem(position);
//		textView.setText(""+position);
		}
	}

	private void initData() {
		Intent intent = getIntent();
		photos=(List<String>) intent.getSerializableExtra("photos");
		position=intent.getIntExtra("position", 0);
	}

	public static void startThisActivity(Context context,
			List<String> photos,int position) {
		Intent intent = new Intent(context, PhotoViewPagerActivity.class);
		intent.putExtra("photos",(Serializable) photos);
		intent.putExtra("position",position);
		context.startActivity(intent);
	}
	

	static class SamplePagerAdapter extends PagerAdapter {
		private List<String> photos;
		private AbImageLoader mAbImageLoader;
		private Context context;
		
		public SamplePagerAdapter(List<String> photos,Context context) {
			super();
			this.photos = photos;
			this.context = context;
			mAbImageLoader = AbImageLoader.newInstance(context);
			mAbImageLoader.setLoadingImage(R.drawable.image_loading);
			mAbImageLoader.setErrorImage(R.drawable.image_error);
			mAbImageLoader.setEmptyImage(R.drawable.image_empty);
		}

		@Override
		public int getCount() {
			return photos.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			photoView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					((Activity)context).finish();
				}
			});
			//photoView.setImageResource(sDrawables[position]);

			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			mAbImageLoader.display(photoView, Constant.BASEURL + photos.get(position));
			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

}
