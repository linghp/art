package com.shangxian.art.adapter;




import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;
import com.shangxian.art.CommodityContentActivity;
import com.shangxian.art.PhotoViewPagerActivity;
import com.shangxian.art.R;
import com.shangxian.art.bean.GoodsCommentBean;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;


public class CommentListAdapter extends BaseAdapter{
	
	private static String TAG = "ImageListAdapter";
  
	private Context mContext;
	//xml转View对象
    private LayoutInflater mInflater;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private List<GoodsCommentBean> mData;
    //图片下载器
    private AbImageLoader mAbImageLoader = null;
    
   /**
    * 构造方法
    * @param context
    * @param data 列表展现的数据
    * @param resource 单行的布局
    * @param from Map中的key
    * @param to view的id
    */
    public CommentListAdapter(Context context, List<GoodsCommentBean> data,
            int resource){
    	this.mContext = context;
    	this.mData = data;
    	this.mResource = resource;
        //用于将xml转为View
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //图片下载器
        mAbImageLoader = AbImageLoader.newInstance(mContext);
        mAbImageLoader.setMaxHeight(100);
        mAbImageLoader.setMaxWidth(100);
        mAbImageLoader.setLoadingImage(R.drawable.image_loading);
        mAbImageLoader.setErrorImage(R.drawable.image_error);
        mAbImageLoader.setEmptyImage(R.drawable.image_empty);
    }   
    
    @Override
    public int getCount() {
        return mData.size();
    }
    
    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
          if(convertView == null){
	           //使用自定义的list_items作为Layout
	           convertView = mInflater.inflate(mResource, parent, false);
          }
          
          ImageView itemsIcon = AbViewHolder.get(convertView, R.id.itemsIcon);
          TextView tv_reviewername = AbViewHolder.get(convertView, R.id.tv_reviewername);
          TextView tv_commentcontent = AbViewHolder.get(convertView, R.id.tv_commentcontent);
          RatingBar ratingBar=AbViewHolder.get(convertView, R.id.ratingbar);
          LinearLayout ll_itemsImage=AbViewHolder.get(convertView, R.id.ll_itemsImage);
          TextView tv_date = AbViewHolder.get(convertView, R.id.tv_date);
          
		  //获取该行的数据
          final GoodsCommentBean  goodsCommentBean = mData.get(position);
          tv_reviewername.setText(goodsCommentBean.getPersionName());
          ratingBar.setRating(goodsCommentBean.getLevel()+3);
          tv_commentcontent.setText(goodsCommentBean.getComment());
          String photoUrl = goodsCommentBean.getUserScaleimage();
//          itemsTitle.setText((String)obj.get("itemsTitle"));
//          itemsText.setText((String)obj.get("itemsText"));
          //设置加载中的View
//          mAbImageLoader.setLoadingView(convertView.findViewById(R.id.progressBar));
          //图片的下载
          mAbImageLoader.display(itemsIcon,Constant.BASEURL+photoUrl);
          ll_itemsImage.setVisibility(View.VISIBLE);
          ll_itemsImage.removeAllViews();
          final List<String> images=goodsCommentBean.getPhotos();
          for (int i = 0; i < images.size(); i++) {
        	  final int j=i;
        	  if(!TextUtils.isEmpty(images.get(i))){
            LayoutParams layoutParams=new LayoutParams(CommonUtil.dip2px(mContext, 40), CommonUtil.dip2px(mContext, 40));
			ImageView imageView=new ImageView(mContext);
			imageView.setLayoutParams(layoutParams);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			mAbImageLoader.display(imageView,Constant.BASEURL+images.get(i));
			ll_itemsImage.addView(imageView);
			CommonUtil.setMargins(imageView, 0, 0, CommonUtil.dip2px(mContext, 9), 0);
			imageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					PhotoViewPagerActivity.startThisActivity(mContext, images,j);
				}
			});
        	  }
		}
          return convertView;
    }
    
}
