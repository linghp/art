package com.shangxian.art.adapter;




import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Telephony.Mms.Rate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;
import com.shangxian.art.LocationActivity;
import com.shangxian.art.NearlyActivity;
import com.shangxian.art.R;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;


public class CommentListAdapter extends BaseAdapter{
	
	private static String TAG = "ImageListAdapter";
  
	private Context mContext;
	//xml转View对象
    private LayoutInflater mInflater;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private List mData;
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
    public CommentListAdapter(Context context, List data,
            int resource){
    	this.mContext = context;
    	this.mData = data;
    	this.mResource = resource;
        //用于将xml转为View
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //图片下载器
        mAbImageLoader = AbImageLoader.newInstance(mContext);
        mAbImageLoader.setMaxWidth(150);
        mAbImageLoader.setMaxHeight(150);
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
          ratingBar.setRating(4.2f);
		  //获取该行的数据
          final Map<String, Object>  obj = (Map<String, Object>)mData.get(position);
          String imageUrl = (String)obj.get("itemsIcon");
//          itemsTitle.setText((String)obj.get("itemsTitle"));
//          itemsText.setText((String)obj.get("itemsText"));
          //设置加载中的View
//          mAbImageLoader.setLoadingView(convertView.findViewById(R.id.progressBar));
          //图片的下载
          mAbImageLoader.display(itemsIcon,imageUrl);
          ll_itemsImage.setVisibility(View.VISIBLE);
          ll_itemsImage.removeAllViews();
          for (int i = 0; i < 3; i++) {
              LayoutParams layoutParams=new LayoutParams(CommonUtil.dip2px(mContext, 40), CommonUtil.dip2px(mContext, 40));
			ImageView imageView=new ImageView(mContext);
			imageView.setLayoutParams(layoutParams);
			CommonUtil.setMargins(imageView, 0, 0, 10, 0);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			mAbImageLoader.display(imageView,((Map<String, Object>)mData.get(i)).get("itemsIcon")+"");
			ll_itemsImage.addView(imageView);
		}
          return convertView;
    }
    
}
