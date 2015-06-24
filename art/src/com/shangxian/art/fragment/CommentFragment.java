package com.shangxian.art.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.shangxian.art.CommentActivity;
import com.shangxian.art.R;
import com.shangxian.art.adapter.CommentListAdapter;
import com.shangxian.art.bean.GoodsCommentBean;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.net.CommentServer;
import com.shangxian.art.net.CommentServer.OnHttpResultGetCommentListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;

/**
 * 商品评论（从商品详情点入）
 * 
 * @author ling
 *
 */
public class CommentFragment extends BaseFragment implements
		OnHeaderRefreshListener, OnFooterLoadListener, OnItemClickListener,
		OnHttpResultGetCommentListener {
	private View view;
	private TextView tv_empty;
	private ListView listView;
	private ProgressBar progressBar;
	private AbPullToRefreshView mAbPullToRefreshView;

	private CommentListAdapter commentListAdapter;
	private List<GoodsCommentBean> goodsCommentBeans = new ArrayList<GoodsCommentBean>();
	private MyOrderItem myOrderItem;

	private boolean isScrollListViewFresh;// 旋转进度条显示与否
	private int skip = 0; // 从第skip+1条开始查询
	private final int pageSize = 10;

	private String url_part;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		MyLogger.i("");
		super.onCreate(savedInstanceState);
	}

	public CommentFragment(String url_part) {
		super();
		this.url_part = url_part;
	}

	private void initMainView() {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_main_action_frame1,
				(ViewGroup) getActivity().findViewById(R.id.vp_content), false);
		tv_empty = (TextView) view.findViewById(R.id.tv_empty);
		Drawable drawable = getResources().getDrawable(R.drawable.nopingjia);
		tv_empty.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
		tv_empty.setText("抱歉，没有找到相关评论");
		listView = (ListView) view.findViewById(R.id.lv_action);
		progressBar = (ProgressBar) view.findViewById(R.id.progress_order);
		mAbPullToRefreshView = (AbPullToRefreshView) view
				.findViewById(R.id.mPullRefreshView);
		// 设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));

		commentListAdapter = new CommentListAdapter(getActivity(),
				goodsCommentBeans, R.layout.item_commentlist);
		listView.setAdapter(commentListAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MyLogger.i("");
		initMainView();
		initListener();
		// getData();
		return view;
	}

	// @Override
	// public void onViewCreated(View view, Bundle savedInstanceState) {
	// super.onViewCreated(view, savedInstanceState);
	// changeUi(UiModel.loading);
	// }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getData();
	}

	private void initListener() {
		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		mAbPullToRefreshView.setOnFooterLoadListener(this);
		listView.setOnItemClickListener(this);
	}

	public void getData() {
		if (!isScrollListViewFresh) {
			progressBar.setVisibility(View.VISIBLE);
			// changeUi(UiModel.loading);
		}
		if (!TextUtils.isEmpty(CommentActivity.productid)) {
			CommentServer.toGetComment(url_part, CommentActivity.productid,
					this);
		}
	}

	@Override
	public void onResume() {
		MyLogger.i("");
		super.onResume();
	}

	@Override
	public void onPause() {
		MyLogger.i("");
		super.onPause();
	}

	@Override
	public void onStop() {
		MyLogger.i("");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		MyLogger.i("");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		MyLogger.i("");
		super.onDestroy();
	}

	public void updateView_nocontent() {
		if (tv_empty != null)
			if (goodsCommentBeans.size() == 0) {
				tv_empty.setVisibility(View.VISIBLE);
				// listView.setVisibility(View.GONE);
				// changeUi(UiModel.noData_noProduct);
			} else {
				// v_empty.setVisibility(View.GONE);
				tv_empty.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
				// changeUi(UiModel.showData);
			}
	}

	// @Override
	// public void onHttpResult(MyOrderItem_all myOrderItemAll) {
	// isScrollListViewFresh = false;
	// progressBar.setVisibility(View.GONE);
	//
	// mAbPullToRefreshView.onHeaderRefreshFinish();
	// if (myOrderItemAll != null) {
	// // MyLogger.i(myOrderItemAll.toString());
	// //changeUi(UiModel.showData);
	// skip = 0;
	// goodsCommentBeans.clear();
	// if (myOrderItemAll.getData() != null) {
	// goodsCommentBeans.addAll(myOrderItemAll.getData());
	// // MyLogger.i(myOrderItemAll.getData().toString());
	// myOrderListAdapter.notifyDataSetChanged();
	// }
	// updateView_nocontent();
	// } else {
	// CommonUtil.toast("网络错误", getActivity());
	// //changeUi(UiModel.noData_noProduct);
	// }
	// }
	//
	// @Override
	// public void onHttpResultMore(MyOrderItem_all myOrderItemAll) {
	// mAbPullToRefreshView.onFooterLoadFinish();
	// if (myOrderItemAll != null) {
	// List<MyOrderItem> myOrderItems = myOrderItemAll.getData();
	// if (myOrderItems != null && myOrderItems.size() > 0) {
	// goodsCommentBeans.addAll(myOrderItems);
	// myOrderListAdapter.notifyDataSetChanged();
	// } else {
	// CommonUtil.toast("已到最后一页", getActivity());
	// }
	// } else {
	// skip -= pageSize;
	// }
	// }

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		isScrollListViewFresh = true;
		getData();
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		isScrollListViewFresh = true;
		loadMore();
	}

	private void loadMore() {
		skip += pageSize;
		String json = "{\"skip\":" + skip + ",\"pageSize\":" + pageSize + "}";
		mAbPullToRefreshView.onFooterLoadFinish();
		// MyOrderServer.toGetOrderMore(status, json, this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// myOrderItem = mOrderItems.get(position);
		// // Intent intent = new Intent(getActivity(),
		// // MyOrderDetailsActivity.class);
		// // intent.putExtra(MyOrderDetailsActivity.INTENTDATAKEY,
		// // myOrderItem.getOrderNumber());
		// // startActivityForResult(intent, 1);
		// MyOrderDetailsActivity.startThisActivity_MyOrder(
		// myOrderItem.getOrderNumber(), getActivity(), this);
	}

	// @Override
	// public void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// MyLogger.i(requestCode + "--" + resultCode);
	// if (resultCode == getActivity().RESULT_OK) {
	// MyOrderItem myOrderItem_Return = (MyOrderItem) data
	// .getSerializableExtra("MyOrderItem");
	// if (myOrderItem != null && myOrderItem_Return != null) {
	// if (!myOrderItem.getStatus().equals(
	// myOrderItem_Return.getStatus())) {
	// myOrderItem.setStatus(myOrderItem_Return.getStatus());
	// myOrderListAdapter.notifyDataSetChanged();
	// }
	// }else if(data.getBooleanExtra("isdelete", false)){//订单详情中删除订单返回
	// mOrderItems.remove(myOrderItem);
	// myOrderListAdapter.notifyDataSetChanged();
	// }
	// super.onActivityResult(requestCode, resultCode, data);
	// }
	// }

	@Override
	public void onHttpResultGetComment(List<GoodsCommentBean> goodsCommentBeans) {
		isScrollListViewFresh = false;
		progressBar.setVisibility(View.GONE);
		mAbPullToRefreshView.onHeaderRefreshFinish();
		if (goodsCommentBeans != null) {
			skip = 0;
			this.goodsCommentBeans.clear();
			this.goodsCommentBeans.addAll(goodsCommentBeans);
			// MyLogger.i(myOrderItemAll.getData().toString());
			commentListAdapter.notifyDataSetChanged();
			updateView_nocontent();
		} else {
			CommonUtil.toast("获取评论失败", getActivity());
		}
	}

}
