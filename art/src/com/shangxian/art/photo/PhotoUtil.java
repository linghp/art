package com.shangxian.art.photo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

/**
 * Created by libo on 2015/5/13.
 */
public class PhotoUtil{
    // �Էֱ��ʽϴ��ͼƬ��������
    public static Bitmap zoomBitmap(Activity mAc,Bitmap bitmap) {
        if (bitmap == null) return null;
        // ��ȡ��Ļ�ֱ���
        DisplayMetrics dm_2 = new DisplayMetrics();
        mAc.getWindowManager().getDefaultDisplay().getMetrics(dm_2);
        // ͼƬ�ֱ�������Ļ�ֱ���
        //float scale_2 = bitmap.getWidth() / (float) dm_2.widthPixels;
        float scale_2 = Math.max(bitmap.getWidth() / (float) dm_2.widthPixels, bitmap.getHeight() / (float) dm_2.heightPixels);
        Bitmap newBitMap_2 = null;
        if (scale_2 < 1) {
            return bitmap;
        } else {
            float width = bitmap.getWidth() / scale_2;

            float height = bitmap.getHeight() / scale_2;

            int w = bitmap.getWidth();

            int h = bitmap.getHeight();

            Matrix matrix = new Matrix();

            float scaleWidth = ((float) width / w);

            float scaleHeight = ((float) height / h);

            matrix.postScale(scaleWidth, scaleHeight);// ���þ���������Ų�������ڴ����

            Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

            return newbmp;
        }
    }
}
