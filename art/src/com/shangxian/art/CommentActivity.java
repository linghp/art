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
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

public class CommentActivity extends BaseActivity implements
		OnHeaderRefreshListener, OnFooterLoadListener, OnClickListener {
	private ListView listView;
	private TextView txt_all_comment_count, txt_good_comment_count,
			txt_neutral_comment_count, txt_bad_comment_count;
	// private AbPullToRefreshView mAbPullToRefreshView = null;
	private View ll_nonetwork, loading_big;
	private String all_str, good_str, neutral_str, bad_str;

	private CommentListAdapter commentListAdapter;
	private ArrayList<String> mPhotoList = new ArrayList<String>();
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	private int currentPage = 1;
	private int total = 50;
	private int pageSize = 15;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		initViews();
		initData();
		requestTask();
	}

	private void requestTask() {
		for (int i = 0; i < 23; i++) {
			mPhotoList.add(Constant.BASEURL1
					+ "content/templates/amsoft/images/rand/" + i + ".jpg");
		}

		String url = Constant.BASEURL + Constant.CONTENT + Constant.CATEGORYS;
		// AbRequestParams params = new AbRequestParams();
		// params.put("shopid", "1019");
		// params.put("code", "88881110344801123456");
		// params.put("phone", "15889936624");
		httpUtil.get(url, new AbStringHttpResponseListener() {

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				// mAbPullToRefreshView.onHeaderRefreshFinish();
			}

			@Override
			public void onFailure(int statusCode, String content,
					Throwable error) {
				// mListView.setVisibility(View.GONE);
				// ll_nonetwork.setVisibility(View.VISIBLE);
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				// mListView.setVisibility(View.VISIBLE);
				if (!TextUtils.isEmpty(content)) {
					Gson gson = new Gson();
					try {
						JSONObject jsonObject = new JSONObject(content);
						String result_code = jsonObject
								.getString("result_code");
						if (result_code.equals("200")) {
							List<Map<String, Object>> newList = null;
							currentPage = 1;
							newList = new ArrayList<Map<String, Object>>();
							Map<String, Object> map = null;

							for (int i = 0; i < pageSize; i++) {
								map = new HashMap<String, Object>();
								map.put("itemsIcon", mPhotoList.get(i));
								map.put("itemsTitle", "item" + (i + 1));
								map.put("itemsText", "item..." + (i + 1));
								newList.add(map);

							}

							list.clear();
							if (newList != null && newList.size() > 0) {
								list.addAll(newList);
								commentListAdapter.notifyDataSetChanged();
								newList.clear();
							}
							// mAbPullToRefreshView.onHeaderRefreshFinish();
							updateViews();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		});
	}

	private void updateViews() {
		txt_all_comment_count.setText(String.format(all_str, 10));
		txt_good_comment_count.setText(String.format(good_str, 5));
		txt_neutral_comment_count.setText(String.format(neutral_str, 3));
		txt_bad_comment_count.setText(String.format(bad_str, 2));
	}

	private void initData() {
		txt_all_comment_count.setSelected(true);
		all_str = getString(R.string.text_all_comment);
		good_str = getString(R.string.text_good_comment);
		neutral_str = getString(R.string.text_neutral_comment);
		bad_str = getString(R.string.text_bad_comment);
		commentListAdapter = new CommentListAdapter(this, list,
				R.layout.item_list, new String[] { "itemsIcon", "itemsTitle",
						"itemsText" }, new int[] { R.id.itemsIcon,
						R.id.itemsTitle, R.id.itemsText, R.id.items_bottom });
		listView.setAdapter(commentListAdapter);
	}

	private void initViews() {
		listView = (ListView) findViewById(R.id.list_comment);
		txt_all_comment_count = (TextView) findViewById(R.id.txt_all_comment_count);
		txt_good_comment_count = (TextView) findViewById(R.id.txt_good_comment_count);
		txt_neutral_comment_count = (TextView) findViewById(R.id.txt_neutral_comment_count);
		txt_bad_comment_count = (TextView) findViewById(R.id.txt_bad_comment_count);

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
	public void onFooterLoad(AbPullToRefreshView view) {
		loadMoreTask();
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		requestTask();
	}

	public void loadMoreTask() {
		AbTask mAbTask = new AbTask();
		final AbTaskItem item = new AbTaskItem();
		item.setListener(new AbTaskListListener() {

			@Override
			public void update(List<?> paramList) {
				List<Map<String, Object>> newList = (List<Map<String, Object>>) paramList;
				if (newList != null && newList.size() > 0) {
					list.addAll(newList);
					commentListAdapter.notifyDataSetChanged();
					newList.clear();
				}
				// mAbPullToRefreshView.onFooterLoadFinish();

			}

			@Override
			public List<?> getList() {
				List<Map<String, Object>> newList = null;
				try {
					currentPage++;
					Thread.sleep(1000);
					newList = new ArrayList<Map<String, Object>>();
					Map<String, Object> map = null;

					for (int i = 0; i < pageSize; i++) {
						map = new HashMap<String, Object>();
						map.put("itemsIcon", mPhotoList.get(i));
						map.put("itemsTitle", "item上拉"
								+ ((currentPage - 1) * pageSize + (i + 1)));
						map.put("itemsText", "item上拉..."
								+ ((currentPage - 1) * pageSize + (i + 1)));
						if ((list.size() + newList.size()) < total) {
							newList.add(map);
						}
					}

				} catch (Exception e) {
					currentPage--;
					newList.clear();
				}
				return newList;
			};
		});

		mAbTask.execute(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_all_comment_count:
			txt_all_comment_count.setSelected(true);
			txt_good_comment_count.setSelected(false);
			txt_neutral_comment_count.setSelected(false);
			txt_bad_comment_count.setSelected(false);
			break;
		case R.id.txt_good_comment_count:
			txt_all_comment_count.setSelected(false);
			txt_good_comment_count.setSelected(true);
			txt_neutral_comment_count.setSelected(false);
			txt_bad_comment_count.setSelected(false);
			break;
		case R.id.txt_neutral_comment_count:
			txt_all_comment_count.setSelected(false);
			txt_good_comment_count.setSelected(false);
			txt_neutral_comment_count.setSelected(true);
			txt_bad_comment_count.setSelected(false);
			break;
		case R.id.txt_bad_comment_count:
			txt_all_comment_count.setSelected(false);
			txt_good_comment_count.setSelected(false);
			txt_neutral_comment_count.setSelected(false);
			txt_bad_comment_count.setSelected(true);
			break;

		default:
			break;
		}
	}
}
