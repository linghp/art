package com.shangxian.art.photo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

/**
 * Created by libo on 2015/5/14.
 */
public class PhotoOptions {
    public static final int REQUEST_CODE = 0x000001256;           //startActivityForResult ���󷵻���(Ĭ��)
    public static final int CROP_CODE = 0x00001255;

    public String imagePath; //ͼƬ����·���������Զ���Ϊ���ȣ�
    public Uri imageUrl;

    public int requestCode = REQUEST_CODE;                       //

    public int cropCode = CROP_CODE;

    public boolean isSave = false;

    public boolean isCrop = false;

    public CropBuilder builder = null;

    public Activity mAct;

    public Fragment mFra;

    public ToIntent toIntent = ToIntent.NORAML;

    public enum ToIntent{
        mAct,mFra,NORAML
    }

    public PhotoOptions( Activity mAct){
        if (mAct == null) throw  new NullPointerException("Activty is null");
        getfilename();
        this.mAct = mAct;
        this.toIntent = ToIntent.mAct;
    }

    public PhotoOptions( Fragment mFra){
        if (mFra == null) throw new NullPointerException("Fragment is null");
        getfilename();
        this.mFra = mFra;
        this.mAct = mFra.getActivity();
        this.toIntent = ToIntent.mFra;
    }

    private  boolean isCustomUrl = false;
    public PhotoOptions( Activity mAct,  Uri imageUrl){
        if (imageUrl == null) throw new NullPointerException("imageUrl is null");
        if (mAct == null) throw  new NullPointerException("Activty is null");
        this.mAct = mAct;
        this.isCustomUrl = true;
        this.imageUrl = imageUrl;
        this.imagePath = UriToString(imageUrl);
        this.toIntent = ToIntent.mAct;
    }

    public PhotoOptions( Fragment mFra,  Uri imageUrl){
        if (imageUrl == null) throw new NullPointerException("imageUrl is null");
        if (mFra == null) throw new NullPointerException("Fragment is null");
        this.mFra = mFra;
        this.mAct = mFra.getActivity();
        this.isCustomUrl = true;
        this.imageUrl = imageUrl;
        this.imagePath = UriToString(imageUrl);
        this.toIntent = ToIntent.mFra;
    }

    public PhotoOptions( Activity mAct,  String imageUrl){
        if (TextUtils.isEmpty(imageUrl)) throw new NullPointerException("imageUrl is null");
        if (mAct == null) throw  new NullPointerException("Activty is null");
        this.mAct = mAct;
        this.isCustomUrl = true;
        this.toIntent = ToIntent.mAct;
        this.imagePath = imageUrl;
        File file = getPath(imageUrl);
        if (file == null) file = getFile();
        this.imageUrl = Uri.fromFile(file);
    }

    public PhotoOptions( Fragment mFra,  String imageUrl){
        if (TextUtils.isEmpty(imageUrl)) throw new NullPointerException("imageUrl is null");
        if (mFra == null) throw new NullPointerException("Fragment is null");
        this.mFra = mFra;
        this.mAct = mFra.getActivity();
        this.isCustomUrl = true;
        this.toIntent = ToIntent.mFra;
        this.imagePath = imageUrl;
        File file = getPath(imageUrl);
        if (file == null) file = getFile();
        this.imageUrl = Uri.fromFile(file);
    }

    private File getFile() {
        //ͼƬ���� ʱ������
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        //����File�������ڴ洢���յ�ͼƬ SD����Ŀ¼
        File outputImage = new File(Environment.getExternalStorageDirectory(),"temple/temp" + (isSave ? format.format(date) : "") + ".jpg");
        return outputImage;
    }

    private File getPath(String imageUrl) {
        File file = new File(imageUrl);
        try {
            if(file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    /**
     * ��ȡͼƬ����·����
     * isSave = false,·��Ϊ�̶���
     * isSave = true, ����ͼƬ����·��
     */
    private void getfilename() {
        if (!isCustomUrl){
            //ͼƬ���� ʱ������
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date(System.currentTimeMillis());
            //����File�������ڴ洢���յ�ͼƬ SD����Ŀ¼
            File outputImage = new File(Environment.getExternalStorageDirectory(),"temple/temp" + (isSave ? format.format(date) : "") + ".jpg");
            this.imagePath = outputImage.getAbsolutePath();
            this.imageUrl = Uri.fromFile(outputImage);
        }
    }

    public String UriToString(Uri uri){
        return uri.toString().replace("file://", "");
    }

    public PhotoOptions isSave(boolean isSave){
        this.isSave = isSave;
        getfilename();
        if (isCrop) builder.customOutPutUrl();
        return this;
    }

    public PhotoOptions isCrop(boolean isCrop){
        this.isCrop = isCrop;
        if (isCrop) this.builder = new CropBuilder(imageUrl);
        if (!isCrop) this.builder = null;
        return this;
    }

    public PhotoOptions setBuilder(CropBuilder builder){
        if (!isCrop) return this;
        if (builder == null) builder = new CropBuilder(imageUrl);
        this.builder = builder;
        return this;
    }

    public PhotoOptions requestCode(int requestCode){
        if (requestCode > 10000){
            throw new RuntimeException("requestCode No more than 10000");
        }
        this.requestCode = requestCode;
        return this;
    }

    public PhotoOptions cropCode(int cropCode){
        if (cropCode > 10000){
            throw new RuntimeException("requestCode No more than 10000");
        }
        this.cropCode = cropCode;
        return this;
    }

    public PhotoOptions imageUri(Uri imageUri){
        this.isCustomUrl = true;
        this.imageUrl = imageUri;
        this.imagePath = UriToString(imageUrl);
        return this;
    }

    public PhotoOptions imagePath(String imagePath){
        this.isCustomUrl = true;
        this.imagePath = imagePath;
        File file = getPath(imagePath);
        if (file == null) file = getFile();
        this.imageUrl = Uri.fromFile(file);
        return this;
    }

    /**
     * ������ת��Fragment��ActivityҪ���ֿ���
     *
     * @param intent
     * @param code
     */
    public void startActivity(Intent intent, int code){
        switch (toIntent) {
            case mAct:
                mAct.startActivityForResult(intent, code);
                break;
            case mFra:
                mFra.startActivityForResult(intent, code);
                break;
        }
    }
}
