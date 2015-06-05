package com.shangxian.art.view;
import com.shangxian.art.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;

/**
 * 评星自定义控件
 * 
 * @author yunzhong.zhou
 *
 */
public class StarRatingView extends View{

	protected int position; // 五角星当前个数

	protected int r; // 五角星大小 ，r越大，五角星越大

	protected int count=5; // 五角星最大个数

	protected int selectNums=3; // 选中的 个数

	protected int width=160; // 这个控件的宽

	protected int height=100; // 这个控件的高

	protected Matrix matrix=new Matrix(); // 缩放工具类

	protected static Bitmap foreground; // 五角星选中时图片

	protected static Bitmap background; // 五角星未选中时图片

	protected Context context;

	protected float bitmapWidth; // 图片的宽

	protected float bitmapHeight; // 图片的高

	public StarRatingView(Context context, int width, int height) {
		super(context);
		this.width=width;
		this.height=height;
		this.context=context;
		this.setLongClickable(true);
		initBitmap();
		setScale();
	}
	/**
	 * 设置缩放
	 */
	private void setScale() {
		if(height > width / count) {
			r=width / count - 2;
		} else {
			r=height - 2;
		}
		matrix.preScale(r / bitmapWidth, r / bitmapHeight);
	}

	/**
	 * 初始化图片
	 */
	private void initBitmap() {
		if(foreground == null) {
			//选中的图片
			foreground=BitmapFactory.decodeResource(context.getResources(), R.drawable.star_on);
		}
		if(background == null) {
			//未选中的图片
			background=BitmapFactory.decodeResource(context.getResources(), R.drawable.star_off);
		}
		bitmapWidth=foreground.getWidth();
		bitmapHeight=foreground.getHeight();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		for(int i=0; i < count; i++) {
			if(i != 0) {
				matrix.postTranslate(width / 5, 0);
			} else {
				matrix.reset();
				matrix.postTranslate(0, (height - r) / 2);
				matrix.preScale(r / bitmapWidth, r / bitmapHeight);
			}
			canvas.drawBitmap(background, matrix, null);
		}
		if(selectNums == 0) {
			selectNums=1;
		}
		for(int i=0; i < selectNums; i++) {
			if(i != 0) {
				matrix.postTranslate(width / 5, 0);
			} else {
				matrix.reset();
				matrix.postTranslate(0, (height - r) / 2);
				matrix.preScale(r / bitmapWidth, r / bitmapHeight);
			}
			canvas.drawBitmap(foreground, matrix, null);
		}
	}

	/**
	 * @param num 设置默认选中星星数
	 */
	 public void setSelectNums(int num) {
		this.selectNums=num;
		if(this.selectNums > count) {
			this.selectNums=count;
		}
		this.invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		this.setMeasuredDimension(width, height);
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_MOVE) {
			position=(int)event.getX();
			if(position > 0) {
				selectNums=position / (width / count) + 1;
				if(selectNums > count) {
					selectNums=count;
				}
			} else {
				selectNums=0;
			}
		}
		this.invalidate();
		return super.onTouchEvent(event);
	}

	/**
	 * @return 当前选中的星星数量
	 */
	public int getSelectNums() {
		if(selectNums == 0) {
			selectNums=1;
		}
		// LOG.hj("getSelectNums", "" + selectNums);
		return selectNums;
	}

	public void recyle() {
		if(foreground != null) {
			foreground.recycle();
			foreground=null;
		}
		if(background != null) {
			background.recycle();
			background=null;
		}
	}
}
