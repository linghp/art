package com.shangxian.art;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.util.AbFileUtil;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.photo.PhotoHelper;
import com.shangxian.art.photo.PhotoHelper.ImageCallBack;
import com.shangxian.art.photo.PhotoOptions;
import com.shangxian.art.photo.PhotoUtil;
import com.shangxian.art.utils.LocalUserInfo;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 实名认证
 * @author Administrator
 *
 */
public class IDCationActivity extends BaseActivity{

	EditText name,num;
	ImageView photo1,photo2;
	private String imagelocaldir;// 图片存储根路径
	private String imageName;//图片存储文件名
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	TextView btn;
	
	PhotoHelper photoHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idcation);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_idcation));

		name = (EditText) findViewById(R.id.idcation_name);//姓名
		num = (EditText) findViewById(R.id.idcation_num);//身份证号码
		photo1 = (ImageView) findViewById(R.id.idcation_photo1);//照片1
		photo2 = (ImageView) findViewById(R.id.idcation_photo2);//照片2
		btn = (TextView) findViewById(R.id.idcation_btn);//申请认证
	}

	private void initData() {
		imagelocaldir = AbFileUtil.getImageDownloadDir(this) + File.separator;
		imageName = LocalUserInfo.getInstance(this).getUserInfo(
				LocalUserInfo.USERPHOTO_FILENAME);
		if (!TextUtils.isEmpty(imageName)) {
			File file = new File(imagelocaldir, imageName);
			// 从文件中找
			if (file.exists()) {
				// Log.i("aaaa", "image exists in file" + filename);
				photo1.setImageBitmap(BitmapFactory.decodeFile(imagelocaldir
						+ imageName));
			}
		}
	}

	private void initListener() {
		btn.setOnClickListener(new OnClickListener() {
			//申请认证
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		photo1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showPhotoDialog();

			}
		});
		photo2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {


			}
		});
	}
	private void showPhotoDialog() {
		final AlertDialog dlg = new AlertDialog.Builder(this).create();
		dlg.show();
		Window window = dlg.getWindow();
		// *** 主要就是在这里实现这种效果的.
		// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
		window.setContentView(R.layout.alertdialog);
		// 为确认按钮添加事件,执行退出应用操作
		TextView tv_paizhao = (TextView) window.findViewById(R.id.tv_content1);
		tv_paizhao.setText("拍照");
		tv_paizhao.setOnClickListener(new View.OnClickListener() {

			/*@Override
			public void onClick(View v) {
				photoHelper = new PhotoHelper(new PhotoOptions(mAc).isCrop(false));
				photoHelper.toNativePhoto();
			}*/
			@SuppressLint("SdCardPath")
			public void onClick(View v) {

				imageName = getNowTime() + ".png";
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// 指定调用相机拍照后照片的储存路径
				intent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(imagelocaldir, imageName)));
				startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
				dlg.cancel();
			}
			
		});
		TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_content2);
		tv_xiangce.setText("相册");
		tv_xiangce.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				getNowTime();
				imageName = getNowTime() + ".png";
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

				dlg.cancel();
			}
		});

	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// MyLogger.i("onActivityResult");
		if (resultCode == RESULT_OK) {
			/*photoHelper.imageCallBack(requestCode, data, new ImageCallBack() {
				
				@Override
				public void onCallBack(String imageUrl, Bitmap bitmap) {
					photo1.setImageBitmap(PhotoUtil.zoomBitmap(mAc, bitmap));
					
				}
			});*/
			switch (requestCode) {
			case PHOTO_REQUEST_TAKEPHOTO:

				startPhotoZoom(
						Uri.fromFile(new File(imagelocaldir, imageName)), 480);
				break;

			case PHOTO_REQUEST_GALLERY:
				if (data != null)
					startPhotoZoom(data.getData(), 480);
				break;

			case PHOTO_REQUEST_CUT:
				// BitmapFactory.Options options = new BitmapFactory.Options();
				//
				// /**
				// * 最关键在此，把options.inJustDecodeBounds = true;
				// * 这里再decodeFile()，返回的bitmap为空
				// * ，但此时调用options.outHeight时，已经包含了图片的高了
				// */
				// options.inJustDecodeBounds = true;\
				MyLogger.i(imagelocaldir);
				Bitmap bitmap = BitmapFactory.decodeFile(imagelocaldir
						+ imageName);
				photo1.setImageBitmap(bitmap);
//				updatePhotoInServer(imageName);
				break;

			}

			super.onActivityResult(requestCode, resultCode, data);

		}
	}
	//显示到头像
	private void updatePhotoInServer(final String image) {
		LocalUserInfo.getInstance(this).setUserInfo(
				LocalUserInfo.USERPHOTO_FILENAME, image);
		// Map<String, String> map = new HashMap<String, String>();
		// if ((new File("/sdcard/fanxin/" + image)).exists()) {
		// map.put("file", "/sdcard/fanxin/" + image);
		// map.put("image", image);
		// } else {
		// return;
		// }
		// map.put("hxid", hxid);
		//
		// LoadDataFromServer task = new LoadDataFromServer(
		// MyUserInfoActivity.this, Constant.URL_UPDATE_Avatar, map);
		//
		// task.getData(new DataCallBack() {
		//
		// @SuppressLint("ShowToast")
		// @Override
		// public void onDataCallBack(JSONObject data) {
		// try {
		// int code = data.getInteger("code");
		// if (code == 1) {
		// LocalUserInfo.getInstance(MyUserInfoActivity.this)
		// .setUserInfo("avatar", image);
		// } else if (code == 2) {
		//
		// Toast.makeText(MyUserInfoActivity.this, "更新失败...",
		// Toast.LENGTH_SHORT).show();
		// } else if (code == 3) {
		//
		// Toast.makeText(MyUserInfoActivity.this, "图片上传失败...",
		// Toast.LENGTH_SHORT).show();
		//
		// } else {
		//
		// Toast.makeText(MyUserInfoActivity.this, "服务器繁忙请重试...",
		// Toast.LENGTH_SHORT).show();
		// }
		//
		// } catch (JSONException e) {
		//
		// Toast.makeText(MyUserInfoActivity.this, "数据解析错误...",
		// Toast.LENGTH_SHORT).show();
		// e.printStackTrace();
		// }
		//
		// }
		//
		// });

	}
	private void startPhotoZoom(Uri uri1, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri1, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", false);

		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(imagelocaldir, imageName)));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}
	private String getNowTime() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS");
		return dateFormat.format(date);
	}
}
