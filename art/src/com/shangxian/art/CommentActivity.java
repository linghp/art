package com.shangxian.art;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.http.AbStringHttpResponseListener;
import com.ab.task.AbTask;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListListener;
import com.ab.util.AbLogUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.ab.view.titlebar.AbTitleBar;
import com.google.gson.Gson;
import com.shangxian.art.adapter.CommentListAdapter;
import com.shangxian.art.adapter.ImageListAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.CommentLevelAllBean;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.fragment.CommentFragment;
import com.shangxian.art.net.CommentServer;
import com.shangxian.art.net.CommentServer.OnHttpResultGetCommentLevelAllListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

/**
 * 商品评论（从商品详情点入）
 * @author ling
 *
 */
public class CommentActivity extends BaseActivity implements OnClickListener,OnHttpResultGetCommentLevelAllListener {
	//private ListView listView;
	private TextView txt_all_comment_count, txt_good_comment_count,
			txt_neutral_comment_count, txt_bad_comment_count;
	// private AbPullToRefreshView mAbPullToRefreshView = null;
	private String all_str, good_str, neutral_str, bad_str;

	private CommentFragment firstFragment,secondFragment,thirdFragment,fourthFragment;
	public static String[] url_parts={"productCommentsList","getpositive","getzero","getNegative"};
	
	public static String productid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		initData();
		initViews();
		//requestTask();
		CommentServer.toGetCommentLevelAll(productid, this);
	}


	private void updateViews(CommentLevelAllBean commentLevelAllBean) {
		int allcount=commentLevelAllBean.getNEGATIVE()+commentLevelAllBean.getPOSITIVE()+commentLevelAllBean.getZERO();
		txt_all_comment_count.setText(String.format(all_str, allcount));
		txt_good_comment_count.setText(String.format(good_str, commentLevelAllBean.getPOSITIVE()));
		txt_neutral_comment_count.setText(String.format(neutral_str, commentLevelAllBean.getZERO()));
		txt_bad_comment_count.setText(String.format(bad_str, commentLevelAllBean.getNEGATIVE()));
	}

	private void initData() {
		productid=getIntent().getStringExtra("id");
		all_str = getString(R.string.text_all_comment);
		good_str = getString(R.string.text_good_comment);
		neutral_str = getString(R.string.text_neutral_comment);
		bad_str = getString(R.string.text_bad_comment);
	}

	private void initViews() {
		txt_all_comment_count = (TextView) findViewById(R.id.txt_all_comment_count);
		txt_good_comment_count = (TextView) findViewById(R.id.txt_good_comment_count);
		txt_neutral_comment_count = (TextView) findViewById(R.id.txt_neutral_comment_count);
		txt_bad_comment_count = (TextView) findViewById(R.id.txt_bad_comment_count);
		txt_all_comment_count.setSelected(true);
		
		firstFragment=new CommentFragment(this,url_parts[0]);
		secondFragment=new CommentFragment(this,url_parts[1]);
		thirdFragment=new CommentFragment(this,url_parts[2]);
		fourthFragment=new CommentFragment(this,url_parts[3]);
		getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, firstFragment,"firstFragment").commit();
		//firstFragment.getData();
		
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showRightBtn();
		topView.setRightBtnDrawable(R.drawable.shopcart);
		topView.showTitle();
		topView.setBack(R.drawable.back);// 返回
		topView.setTitle(getString(R.string.title_activity_comment));// title文字
		topView.setRightBtnListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putBoolean("isother", true);
				CommonUtil.gotoActivityWithDataForResult(CommentActivity.this,
						ShoppingcartActivity.class, bundle, 10086, false);
			}
		});
	}

	public static void startThisActivity(String id, Context context) {
		Intent intent = new Intent(context, CommentActivity.class);
		intent.putExtra("id", id);
		context.startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_all_comment_count:
			txt_all_comment_count.setSelected(true);
			txt_good_comment_count.setSelected(false);
			txt_neutral_comment_count.setSelected(false);
			txt_bad_comment_count.setSelected(false);
			getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, firstFragment,"firstFragment").commit();
			break;
		case R.id.txt_good_comment_count:
			txt_all_comment_count.setSelected(false);
			txt_good_comment_count.setSelected(true);
			txt_neutral_comment_count.setSelected(false);
			txt_bad_comment_count.setSelected(false);
			getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, secondFragment,"secondFragment").commit();
			break;
		case R.id.txt_neutral_comment_count:
			txt_all_comment_count.setSelected(false);
			txt_good_comment_count.setSelected(false);
			txt_neutral_comment_count.setSelected(true);
			txt_bad_comment_count.setSelected(false);
			getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, thirdFragment,"thirdFragment").commit();
			break;
		case R.id.txt_bad_comment_count:
			txt_all_comment_count.setSelected(false);
			txt_good_comment_count.setSelected(false);
			txt_neutral_comment_count.setSelected(false);
			txt_bad_comment_count.setSelected(true);
			getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, fourthFragment,"fourthFragment").commit();
			break;

		default:
			break;
		}
	}

	@Override
	public void onHttpResultGetCommentLevelAll(
			CommentLevelAllBean commentLevelAllBean) {
		if(commentLevelAllBean!=null){
			updateViews(commentLevelAllBean);
		}else{
			
		}
	}
}
