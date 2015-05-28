package com.shangxian.art;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.http.AbFileHttpResponseListener;
import com.ab.util.AbFileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.FileServer;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.LocalUserInfo;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.utils.Options;
import com.shangxian.art.view.CircleImageView1;
import com.shangxian.art.view.TopView;

/**
 * 设置(账户与安全)
 * 
 * @author Administrator
 *
 */
public class AccountSecurityActivity extends BaseActivity {
	private CircleImageView1 iv_photo;

	private String imagelocaldir;// 图片存储根路径
	private String imageName;// 头像存储文件名

	private ImageView iv_loading;

	private Animation anim_;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_security);
		initviews();
		initdata();
	}

	private void initdata() {
		imagelocaldir = AbFileUtil.getImageDownloadDir(this) + File.separator;
		imageName = LocalUserInfo.getInstance(this).getUserInfo(
				LocalUserInfo.USERPHOTO_FILENAME);
		if (!TextUtils.isEmpty(imageName)) {
			ImageLoader.getInstance().displayImage(Constant.BASEURL + imageName, iv_photo, Options.getListOptions());
		}
	}

	private void initviews() {
		iv_photo = (CircleImageView1) findViewById(R.id.iv_photo);
		iv_loading = (ImageView) findViewById(R.id.iv_loading);
		anim_ = AnimationUtils.loadAnimation(this, R.anim.rotate);
		anim_.setInterpolator(new LinearInterpolator());
		iv_loading.startAnimation(anim_);
		iv_loading.setVisibility(View.GONE);
		iv_loading.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myToast("正在上传图片...");
			}
		});
		// 改变topbar
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_accountsecurity));
	}

	public void doClick(View view) {
		switch (view.getId()) {
		case R.id.tv_logout:
			myToast("注销成功");
			deletedata();
			finish();
			break;
		case R.id.rl_photo:
			//myToast("头像");
			showPhotoDialog();
			break;
		case R.id.ll_my_item1:
			// 收获地址管理
			CommonUtil.gotoActivity(AccountSecurityActivity.this,
					DeliveryAddressActivity.class, false);
			break;
		case R.id.ll_my_item2:
			// 修改登录密码
			// CommonUtil.gotoActivity(AccountSecurityActivity.this,
			// ChangePasswordActivity.class, false);
			Bundle bundle1 = new Bundle();
			bundle1.putInt(Constant.INT_SAFE_PAY_NEW,
					SafetyVerificationActivity.USER_PASS_UP);
			CommonUtil.gotoActivityWithData(AccountSecurityActivity.this,
					SafetyVerificationActivity.class, bundle1, false);
			break;
		case R.id.ll_my_item3:
			// 找回登录密码
			// CommonUtil.gotoActivity(AccountSecurityActivity.this,
			// SafetyVerificationActivity.class, false);
			Bundle bundle2 = new Bundle();
			bundle2.putInt(Constant.INT_SAFE_PAY_NEW,
					SafetyVerificationActivity.USER_PASS_NEW);
			CommonUtil.gotoActivityWithData(AccountSecurityActivity.this,
					SafetyVerificationActivity.class, bundle2, false);
			break;
		case R.id.ll_my_item4:
			// 手机认证
			CommonUtil.gotoActivity(AccountSecurityActivity.this,
					PhoneValidateActivity.class, false);
			break;
		case R.id.ll_my_item5:
			// 修改支付密码
			if (isPayed(true)) {
				Bundle bundle3 = new Bundle();
				bundle3.putInt(Constant.INT_SAFE_PAY_NEW,
						SafetyVerificationActivity.PAY_PASS_UP);
				CommonUtil.gotoActivityWithData(AccountSecurityActivity.this,
						SafetyVerificationActivity.class, bundle3, false);
			}
			break;
		case R.id.ll_my_item6:
			// 找回支付密码
			if (isPayed(true)) {
				Bundle bundle4 = new Bundle();
				bundle4.putInt(Constant.INT_SAFE_PAY_NEW,
						SafetyVerificationActivity.PAY_PASS_NEW);
				CommonUtil.gotoActivityWithData(AccountSecurityActivity.this,
						SafetyVerificationActivity.class, bundle4, false);
			}
			break;
		case R.id.ll_my_item7:
			// 实名认证
			CommonUtil.gotoActivity(AccountSecurityActivity.this,
					IDCationActivity.class, false);
			break;

		default:
			break;
		}
	}

	private void deletedata() {
		LocalUserInfo.getInstance(this).deleteUserInfo();
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
				iv_photo.setImageBitmap(bitmap);
				updatePhotoInServer(imageName);
				break;
			}
			super.onActivityResult(requestCode, resultCode, data);

		}
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

	private void updatePhotoInServer(final String image) {
		// iv_loading.setVisibility(View.VISIBLE);
		new FileServer().toFile(new File(imagelocaldir + imageName),
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> info) {
						iv_loading.setVisibility(View.GONE);
						String res = info.result;
						MyLogger.i(res);
						//myToast("res==" + res);
						if (!TextUtils.isEmpty(res)) {
							try {
								JSONObject json = new JSONObject(res);
								int result_code = json.getInt("result_code");
								Type type = new TypeToken<List<String>>() {
								}.getType();
								List<String> imgs = new Gson().fromJson(
										json.getString("result"), type);
								LocalUserInfo
										.getInstance(
												AccountSecurityActivity.this)
										.setUserInfo(
												LocalUserInfo.USERPHOTO_FILENAME,
												imgs.get(1));
								com.nostra13.universalimageloader.core.ImageLoader
										.getInstance().displayImage(
												Constant.BASEURL + imgs.get(1),
												iv_photo,
												Options.getListOptions());
							} catch (Exception e) {
								e.printStackTrace();
								myToast("上传失败");
							}
						}
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						myToast("上传失败");
						iv_loading.setVisibility(View.GONE);
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}
				});
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

	private String getNowTime() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS");
		return dateFormat.format(date);
	}

}
