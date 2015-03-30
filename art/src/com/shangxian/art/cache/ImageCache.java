package com.shangxian.art.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageCache extends LruCache<String, Bitmap> {
	public ImageCache(int maxSize) {
		super(maxSize);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int sizeOf(String key, Bitmap value) {
		// TODO Auto-generated method stub
		return value.getRowBytes() * value.getHeight();
	}

	@Override
	protected void entryRemoved(boolean evicted, String key, Bitmap oldValue,
			Bitmap newValue) {
		// TODO Auto-generated method stub
		super.entryRemoved(evicted, key, oldValue, newValue);
		if (evicted) {
			oldValue.recycle();
			oldValue = null;
		}
	}

}
