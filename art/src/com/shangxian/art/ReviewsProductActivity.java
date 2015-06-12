package com.shangxian.art;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ProductItemDto;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.photochooser.ImagePagerActivity;
import com.shangxian.art.photochooser.PhotoOperate;
import com.shangxian.art.photochooser.PhotoPickActivity;
import com.shangxian.art.view.TopView;
/**
 * 评价商品           现只能对单个商品评论
 * @author zyz
 *
 */
public class ReviewsProductActivity extends BaseActivity {
	private TextView tv_quxiao,tv_tijiao,tv_gongkai,tv_niming;
	private EditText et_comment;
	private RatingBar ratingbar;
	private LinearLayout ll_view;
	private float ratings = 0;
	
	private String ordernumber, userid,comment;
	private List<ProductItemDto> productItemDtos;

	private int ratingValue=-1;
	private boolean isAnonymous=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reviewsproduct);
		initData();
		initView();
		initListener();
	}

	public static void startThisActivity(String ordernumber,String userid, List<ProductItemDto> productItemDtos,Context context) {
		Intent intent = new Intent(context, ReviewsProductActivity.class);
		intent.putExtra("ordernumber", ordernumber);
		intent.putExtra("userid", userid);
		intent.putExtra("productItemDtos", (Serializable)productItemDtos);
		((Activity)context).startActivityForResult(intent, 1);
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
		// 设置评星星级
		ratingbar.setRating(ratings);
		
		ll_view = (LinearLayout) findViewById(R.id.reviewsproduct_linear2);
		//添加布局
		View child = inflater.inflate(
				R.layout.item_reviewsproduct, null);
		ll_view.addView(child);
		et_comment=(EditText) findViewById(R.id.item_reviewsproduct_edit);
		gridView=(GridView) findViewById(R.id.gridView);
		
		imageWidthPx = 200;
		mSize = new ImageSize(imageWidthPx, imageWidthPx);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == mData.size()) {
					int count = PHOTO_MAX_COUNT - mData.size();
					if (count <= 0) {
						return;
					}

					Intent intent = new Intent(ReviewsProductActivity.this,
							PhotoPickActivity.class);
					intent.putExtra(PhotoPickActivity.EXTRA_MAX, count);
					startActivityForResult(intent, RESULT_REQUEST_PICK_PHOTO);

				} else {
					Intent intent = new Intent(ReviewsProductActivity.this,
							ImagePagerActivity.class);
					ArrayList<String> arrayUri = new ArrayList<String>();
					for (PhotoData item : mData) {
						arrayUri.add(item.uri.toString());
					}
					intent.putExtra("mArrayUri", arrayUri);
					intent.putExtra("mPagerPosition", position);
					intent.putExtra("needEdit", true);
					startActivityForResult(intent, RESULT_REQUEST_IMAGE);
				}
			}
		});
		
		if(productItemDtos!=null){
			((TextView)findViewById(R.id.item_reviewsproduct_name)).setText(productItemDtos.get(0).getName());
			ImageView iv_product=(ImageView)findViewById(R.id.item_reviewsproduct_img1);
			mAbImageLoader.display(iv_product, Constant.BASEURL
					+ productItemDtos.get(0).getProductSacle());
		}
	}

	private void initData() {
		Intent intent=getIntent();
		ordernumber=intent.getStringExtra("ordernumber");
		userid=intent.getStringExtra("userid");
		productItemDtos=(List<ProductItemDto>) intent.getSerializableExtra("productItemDtos");
	}

	private void initListener() {
		ratingbar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				myToast(""+rating);
				ratingValue=(int) rating;
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
             if(match()){
            	 
             }
			}
		});
		tv_gongkai.setOnClickListener(new OnClickListener() {
			//公开
			@Override
			public void onClick(View v) {
				isAnonymous=false;
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
				isAnonymous=true;
				Drawable drawable = getResources().getDrawable(R.drawable.sel_t);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
				tv_niming.setCompoundDrawables(drawable, null, null,null);//画在左边
				Drawable drawable1 = getResources().getDrawable(R.drawable.sel_n);
				drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight()); //设置边界
				tv_gongkai.setCompoundDrawables(drawable1, null, null,null);//画在左边
			}
		});

		
	}

	protected boolean match() {
		comment=et_comment.getText().toString().trim();
		if(TextUtils.isEmpty(comment)){
			myToast("评论内容不能为空");
			return false;
		}else if(ratingValue==-1){
			myToast("请给个评分呗");
			return false;
		}
		return true;
	}

	//-------------------------------以下为添加图片模块----------------------------------------------
	public static final int PHOTO_MAX_COUNT = 6;
	private GridView gridView;
	private int imageWidthPx;
	private ImageSize mSize;
	private PhotoOperate photoOperate = new PhotoOperate(this);
	private Uri fileUri;
	
	ArrayList<PhotoData> mData = new ArrayList();
	BaseAdapter adapter = new BaseAdapter() {

		public int getCount() {
			return mData.size() + 1;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return position;
		}

		ArrayList<ViewHolder> holderList = new ArrayList<ViewHolder>();

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				holder.image = (ImageView)LayoutInflater.from(ReviewsProductActivity.this).inflate(
						R.layout.image_make_maopao, parent, false);
				holderList.add(holder);
				holder.image.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == getCount() - 1) {
				if (getCount() == (PHOTO_MAX_COUNT + 1)) {
					holder.image.setVisibility(View.INVISIBLE);

				} else {
					holder.image.setVisibility(View.VISIBLE);
					holder.image.setImageResource(R.drawable.addimage);
					holder.uri = "";
				}

			} else {
				holder.image.setVisibility(View.VISIBLE);
				PhotoData photoData = mData.get(position);
				Uri data = photoData.uri;
				holder.uri = data.toString();

				ImageLoader.getInstance().loadImage(data.toString(), mSize,
						new SimpleImageLoadingListener() {
							@Override
							public void onLoadingComplete(String imageUri,
									View view, Bitmap loadedImage) {
								for (ViewHolder item : holderList) {
									if (item.uri.equals(imageUri)) {
										item.image.setImageBitmap(loadedImage);
									}
								}
							}
						});
			}

			return holder.image;
		}

		class ViewHolder {
			ImageView image;
			String uri = "";
		}

	};
	
	public static class PhotoData {
		Uri uri = Uri.parse("");
		String serviceUri = "";

		public PhotoData(File file) {
			uri = Uri.fromFile(file);
		}

		public PhotoData(PhotoDataSerializable data) {
			uri = Uri.parse(data.uriString);
			serviceUri = data.serviceUri;
		}
	}

	// 因为PhotoData包含Uri，不能直接序列化，所以有了这个类
	public static class PhotoDataSerializable implements Serializable {
		String uriString = "";
		String serviceUri = "";

		public PhotoDataSerializable(PhotoData data) {
			uriString = data.uri.toString();
			serviceUri = data.serviceUri;
		}
	}
	
	public static final int RESULT_REQUEST_IMAGE = 100;
	public static final int RESULT_REQUEST_FOLLOW = 1002;
	public static final int RESULT_REQUEST_PICK_PHOTO = 1003;
	public static final int RESULT_REQUEST_PHOTO = 1005;
	public static final int RESULT_REQUEST_LOCATION = 1006;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RESULT_REQUEST_PICK_PHOTO) {
			if (resultCode == Activity.RESULT_OK) {
				try {
					ArrayList<PhotoPickActivity.ImageInfo> pickPhots = (ArrayList<PhotoPickActivity.ImageInfo>) data
							.getSerializableExtra("data");
					for (PhotoPickActivity.ImageInfo item : pickPhots) {
						Uri uri = Uri.parse(item.path);
						File outputFile = photoOperate.scal(uri);
						mData.add(new ReviewsProductActivity.PhotoData(outputFile));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				adapter.notifyDataSetChanged();
			}
		} else if (requestCode == RESULT_REQUEST_PHOTO) {
			if (resultCode == RESULT_OK) {
				try {
					File outputFile = photoOperate.scal(fileUri);
					mData.add(mData.size(), new ReviewsProductActivity.PhotoData(
							outputFile));
					adapter.notifyDataSetChanged();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (requestCode == RESULT_REQUEST_IMAGE) {
			if (resultCode == RESULT_OK) {
				ArrayList<String> delUris = data
						.getStringArrayListExtra("mDelUrls");
				for (String item : delUris) {
					for (int i = 0; i < mData.size(); ++i) {
						if (mData.get(i).uri.toString().equals(item)) {
							mData.remove(i);
						}
					}
					adapter.notifyDataSetChanged();
				}
			}
		} else if (requestCode == RESULT_REQUEST_FOLLOW) {
			if (resultCode == RESULT_OK) {
				String name = data.getStringExtra("name");
				//mEnterLayout.insertText(name);
			}

		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}

	}
	
	@Override
	public void onBackPressed() {
		if (et_comment.getText().toString().isEmpty()) {
			finish();
		} else {
//			showDialog("冒泡", "放弃此次冒泡机会？",
//					new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							finishWithoutSave();
//						}
//					});
		}
	}
}
