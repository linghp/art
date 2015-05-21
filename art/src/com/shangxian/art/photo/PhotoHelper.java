package com.shangxian.art.photo;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

/**
 * Created by libo on 2015/5/15.
 */
public class PhotoHelper {
    PhotoOptions options;
    private boolean isCrop = false;

    public PhotoHelper(PhotoOptions options){
        if (options == null) throw new NullPointerException("PhotoOptions is null");
        this.options = options;
    }

    public PhotoHelper( Activity mAct) {
        this.options = new PhotoOptions(mAct);
    }

    public PhotoHelper( Fragment mFar){
        this.options = new PhotoOptions(mFar);
    }

    public PhotoHelper( Activity mAct,  String imagePath){
        this.options = new PhotoOptions(mAct, imagePath);
    }

    public PhotoHelper( Activity mAct,  Uri imageUri){
        this.options = new PhotoOptions(mAct, imageUri);
    }

    public PhotoHelper( Fragment mFra,  String imagePath){
        this.options = new PhotoOptions(mFra, imagePath);
    }

    public PhotoHelper( Fragment mFra,  Uri imageUri){
        this.options = new PhotoOptions(mFra, imageUri);
    }

    public void toNativePhoto(){
        options.startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, options.imageUrl), options.requestCode);
    }

    public void toCropPoto(){
        isCrop = true;
        options.isCrop(isCrop).startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, options.imageUrl), options.requestCode);
    }

    public void imageCallBack(int requestCode, Intent data, ImageCallBack callBack) {
        if (requestCode == options.requestCode){
            photoBack(data, callBack);
        } else if (requestCode == options.cropCode){
            if (isCrop){
                isCrop = !isCrop;
            }
            Bitmap bitmap = data.getParcelableExtra("data");
            if (callBack != null){
                try {
                    callBack.onCallBack(options.builder.outPutPath, PhotoUtil.zoomBitmap(options.mAct, BitmapFactory.decodeStream(options.mAct.getContentResolver().openInputStream(options.builder.outPutUrl))));
                } catch (FileNotFoundException e) {
                    callBack.onCallBack(options.builder.outPutPath, bitmap);
                    e.printStackTrace();
                }
            }
        }
    }

    private void photoBack(Intent data, ImageCallBack callBack) {
        if (options.isCrop){
            options.startActivity(options.builder.toIntent(), options.cropCode);
        } else {
            ContentResolver resolver = options.mAct.getContentResolver();
            // ��Ƭ��ԭʼ��Դ��ַ
            Uri imgUri = options.imageUrl;
            // ʹ��ContentProviderͨ��Uri��ȡԭʼͼƬ
            Bitmap photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(resolver, imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (callBack != null){
                callBack.onCallBack(options.imagePath, PhotoUtil.zoomBitmap(options.mAct, photo));
            }
        }
    }

    public interface ImageCallBack{
        void onCallBack(String imageUrl, Bitmap bitmap);
    }
}
