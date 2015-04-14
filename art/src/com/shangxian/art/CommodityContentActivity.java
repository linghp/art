package com.shangxian.art;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbLogUtil;
import com.google.gson.Gson;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CommodityContentModel;
import com.shangxian.art.cache.Imageloader_homePager;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.HttpClients;
import com.shangxian.art.net.HttpClients.HttpCilentListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;
/**
 * 商品详情
 * @author Administrator
 *
 */
public class CommodityContentActivity extends BaseActivity implements OnClickListener,HttpCilentListener{
	private LinearLayout shangpu;
    private ImageView commoditycontent_img,commoditycontent_shoucang;
    private TextView commoditycontent_jieshao,commoditycontent_jiage,commoditycontent_jiarugouwuche;
	
  //判断是否收藏
  	boolean iscollection = false;
  	
	private AbHttpUtil httpUtil = null;
	private CommodityContentModel model;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commoditycontent);
		
		initView();
		initData();
		listener();
	}

	public static void startThisActivity(String id,
			Context context) {
		Intent intent = new Intent(context, CommodityContentActivity.class);
		intent.putExtra("id", id);
		context.startActivity(intent);
	}
	
	public static void startThisActivity_url(String url, Context context) {
		Intent intent = new Intent(context, CommodityContentActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}
	
	private void listener() {
		commoditycontent_jiarugouwuche.setOnClickListener(this);
		shangpu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonUtil.gotoActivity(CommodityContentActivity.this, ShopsActivity.class, false);
			}
		});
		commoditycontent_shoucang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!iscollection) {
					commoditycontent_shoucang.setImageResource(R.drawable.collection_on);
					myToast("已收藏");
				}else {
					commoditycontent_shoucang.setImageResource(R.drawable.collection_off);
					myToast("取消收藏");
				}
				iscollection = !iscollection;
			}
		});
	}

	private void initData() {
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(Constant.timeOut);
		String id=getIntent().getStringExtra("id");
		String geturl = getIntent().getStringExtra("url");
		String url = "";
		if (TextUtils.isEmpty(geturl)) {
			url=Constant.BASEURL+Constant.CONTENT+"/product"+"/"+id;
		} else {
			url = Constant.BASEURL + Constant.CONTENT + geturl;
		}
        refreshTask(url);
	}

	private void refreshTask(String url) {
	//	AbRequestParams params = new AbRequestParams();
//		params.put("shopid", "1019");
//		params.put("code", "88881110344801123456");
//		params.put("phone", "15889936624");
		httpUtil.get(url,new AbStringHttpResponseListener() {

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				// AbDialogUtil.removeDialog(HomeActivity.this);
				// mAbPullToRefreshView.onHeaderRefreshFinish();
			}

			@Override
			public void onFailure(int statusCode, String content,
					Throwable error) {
				// AbToastUtil.showToast(HomeActivity.this, error.getMessage());
//				imgList.clear();
//				ArrayList<String> imgs = new ArrayList<String>();
//				imgs.add("http://img1.imgtn.bdimg.com/it/u=3784117098,1253514089&fm=21&gp=0.jpg");
//				mDatas.setImgList(imgs);
//				if (mDatas != null) {
//					if (mDatas.getImgList() != null
//							&& mDatas.getImgList().size() > 0) {
//						imgList.addAll(mDatas.getImgList());
//						// viewPager.setVisibility(View.VISIBLE);
//						viewPager.setOnGetView(new OnGetView() {
//
//							@Override
//							public View getView(ViewGroup container,
//									int position) {
//								ImageView iv = new ImageView(HomeActivity.this);
//								Imageloader_homePager.displayImage(
//										imgList.get(position), iv,
//										new Handler(), null);
//								container.addView(iv);
//								return iv;
//							}
//						});
//						viewPager.setAdapter(imgList.size());
//					}
//
//				}

			}

			@Override
			public void onSuccess(int statusCode, String content) {
				// AbToastUtil.showToast(HomeActivity.this, content);
				AbLogUtil.i(CommodityContentActivity.this, content);
				if (!TextUtils.isEmpty(content)) {
					Gson gson = new Gson();
					try {
						JSONObject jsonObject = new JSONObject(content);
						String result_code = jsonObject
								.getString("result_code");
						if (result_code.equals("200")) {
							JSONObject jsonObject1 = jsonObject
									.getJSONObject("result");
								model=gson.fromJson(
										jsonObject1.toString(), CommodityContentModel.class);
								MyLogger.i(model.toString());
								if(model!=null)
								updateView();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	
			}
		});
	}
	
	private void updateView() {
		Imageloader_homePager.displayImage(Constant.BASEURL
				+ model.getPhotos().get(0),
				commoditycontent_img,
				new Handler(), null);// TODO Auto-generated method stub
		commoditycontent_jieshao.setText(model.getName());
		commoditycontent_jiage.setText("￥"+model.getPromotionPrice());
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		commoditycontent_img = (ImageView) findViewById(R.id.commoditycontent_img);
		commoditycontent_shoucang = (ImageView) findViewById(R.id.commoditycontent_shoucang);
		commoditycontent_jieshao = (TextView) findViewById(R.id.commoditycontent_jieshao);
		commoditycontent_jiage = (TextView) findViewById(R.id.commoditycontent_jiage);
		commoditycontent_jiarugouwuche = (TextView) findViewById(R.id.commoditycontent_jiarugouwuche);
		shangpu = (LinearLayout) findViewById(R.id.commoditycontent_shangpu);
//		star = (StarRatingView) findViewById(R.id.commoditycontent_starRating);
//		star.setSelectNums(1);//设置默认选中星星数
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);//返回
		topView.setTitle("商品详情");//title文字
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.commoditycontent_jiarugouwuche:
			dotask_addcart();
			break;

		default:
			break;
		}
	}

	private void dotask_addcart() {
		String json="{\"productId\":"+model.getId()+",\"sepcs\":\"颜色:红\",\"buyCount\":2}";
		//HttpClients.postDo(Constant.BASEURL+Constant.CONTENT+Constant.CART, json, this);
	}

	@Override
	public void onResponse(String content) {
		MyLogger.i(content);
		if (!TextUtils.isEmpty(content)) {
			try {
				JSONObject jsonObject = new JSONObject(content);
				String result_code = jsonObject
						.getString("result_code");
				if (result_code.equals("200")) {
					myToast(jsonObject
						.getString("result"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
