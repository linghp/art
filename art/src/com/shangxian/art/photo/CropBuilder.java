package com.shangxian.art.photo;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

/**
 * Created by libo on 2015/5/15.
 */
public class CropBuilder {
    public Uri imageUrl;//ͼƬ·��
    public Uri outPutUrl;//ͼƬ���·��
    public String outPutPath;

    public boolean crop = true;         //�����ڿ�����Intent��������ʾ��VIEW�ɲü�
    public boolean scale =true;         //����ͼƬ�Ƿ�������
    public boolean return_data = false; //���ü��к��Ƿ��з���ֵ
    public boolean noFaceDetection = false;    //�����Ƿ���������ʶ��

    //���ÿ�߱���
    public int aspectX = 1;
    public int aspectY = 1;

    //���òü�ͼƬ���
    public int outputX = 340;
    public int outputY = 340;

    public String outputFormat = Bitmap.CompressFormat.JPEG.toString(); //����ͼƬ�����ʽ

    private boolean isOutPutUrl = false;

    public CropBuilder( Uri imageUrl) {
        if (imageUrl == null){
            throw new RuntimeException("imageUrl is null");
        }
        this.imageUrl = imageUrl;
        customOutPutUrl();
    }

    public CropBuilder customOutPutUrl(){
        if (!isOutPutUrl) this.outPutUrl = getfilename();
        return this;
    }

    public CropBuilder outPutUrl( Uri outPutUrl){
        if (outPutUrl == null){
            customOutPutUrl();
        } else {
            this.isOutPutUrl = true;
            this.outPutUrl = outPutUrl;
        }
        return this;
    }

    public CropBuilder crop( boolean crop){
        this.crop = crop;
        return this;
    }

    public CropBuilder scale( boolean scale){
        this.scale = scale;
        return this;
    }

    public CropBuilder return_data( boolean return_data){
        this.return_data = return_data;
        return this;
    }

    public CropBuilder noFaceDetection( boolean noFaceDetection){
        this.noFaceDetection = noFaceDetection;
        return this;
    }

    public CropBuilder aspect( int x,  int y){
        this.aspectX = x;
        this.aspectY = y;
        return this;
    }

    public CropBuilder outPut( int x,  int y){
        this.outputX = x;
        this.outputY = y;
        return this;
    }

    public CropBuilder outputFormat( String outputFormat){
        this.outputFormat = outputFormat;
        return this;
    }

    public Intent toIntent(){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUrl, "image/*");
        intent.putExtra("crop", crop ? "true" : "false");//�ɲü�
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", scale);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUrl);
        intent.putExtra("return-data", return_data);//��Ϊfalse���ʾ����������
        intent.putExtra("outputFormat", outputFormat);
        intent.putExtra("noFaceDetection", noFaceDetection);
        return intent;
    }

    /**
     * ��ȡͼƬ����·����
     * isSave = false,·��Ϊ�̶���
     * isSave = true, ����ͼƬ����·��
     */
    public Uri getfilename() {
        //ͼƬ���� ʱ������
        //����File�������ڴ洢���յ�ͼƬ SD����Ŀ¼
        File outputImage = new File(Environment.getExternalStorageDirectory(),"temple/temp" + System.currentTimeMillis() + ".jpg");
        //�洢��DCIM�ļ���
        //File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        //File outputImage = new File(path, "temple/temp" + (option.isSave ? format.format(date) : "") + ".jpg");
        this.outPutPath = outputImage.getAbsolutePath();
        return Uri.fromFile(outputImage);
    }
}
