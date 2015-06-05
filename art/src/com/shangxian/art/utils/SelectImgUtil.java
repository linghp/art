package com.shangxian.art.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

/**
 * ѡ�񱾵�ͼƬ������
 * @author libo
 * @time 2015/3/26
 */
public class SelectImgUtil {
    public static final int SELECT_PIC = 0x001111;
    
	/**
     * 跳转图片选择界面
     * @param mAc
     */
    public static void toSelectImg(Activity mAc){
        Intent intent;
        // = new Intent(Intent.ACTION_GET_CONTENT);// ACTION_OPEN_DOCUMENT
         //4.4推荐用此方式，4.4以下的API需要再兼容
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            mAc.startActivityForResult(intent, SELECT_PIC);//4.4版本
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            mAc.startActivityForResult(intent, SELECT_PIC);
        }
    }

    /**
     * 跳转图片选择界面
     * @param mAc
     */
    public static void toSelectImg(Fragment mAc){
        Intent intent;
        // = new Intent(Intent.ACTION_GET_CONTENT);// ACTION_OPEN_DOCUMENT
         //4.4推荐用此方式，4.4以下的API需要再兼容
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            mAc.startActivityForResult(intent, SELECT_PIC);//4.4版本
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            mAc.startActivityForResult(intent, SELECT_PIC);//4.4以下版本，先不处理
        }
    }
    
    /**
     * 跳转图片选择界面
     * @param mAc
     */
    public static void toSelectImg(Activity mAc, int toPic){
    	Intent intent;
    	// = new Intent(Intent.ACTION_GET_CONTENT);// ACTION_OPEN_DOCUMENT
    	//4.4推荐用此方式，4.4以下的API需要再兼容
    	if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
    		intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    		intent.addCategory(Intent.CATEGORY_OPENABLE);
    		intent.setType("image/*");
    		mAc.startActivityForResult(intent, toPic);//4.4版本
    	} else {
    		intent = new Intent(Intent.ACTION_GET_CONTENT);
    		intent.addCategory(Intent.CATEGORY_OPENABLE);
    		intent.setType("image/*");
    		mAc.startActivityForResult(intent, toPic);
    	}
    }
    
    /**
     * 跳转图片选择界面
     * @param mAc
     */
    public static void toSelectImg(Fragment mAc, int toPic){
    	Intent intent;
    	// = new Intent(Intent.ACTION_GET_CONTENT);// ACTION_OPEN_DOCUMENT
    	//4.4推荐用此方式，4.4以下的API需要再兼容
    	if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
    		intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    		intent.addCategory(Intent.CATEGORY_OPENABLE);
    		intent.setType("image/*");
    		mAc.startActivityForResult(intent, toPic);//4.4版本
    	} else {
    		intent = new Intent(Intent.ACTION_GET_CONTENT);
    		intent.addCategory(Intent.CATEGORY_OPENABLE);
    		intent.setType("image/*");
    		mAc.startActivityForResult(intent, toPic);//4.4以下版本，先不处理
    	}
    }

    /**
     * 获取图片路径
     * @param context
     * @param intent
     * @return
     */
    public static String getImgPath(Context context, Intent intent){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){
            if (intent.getData() == null){
                return null;
            } else {
                Uri uri = intent.getData();
                return getPath(context, uri);
            }
        } else {
            return getPath(context, intent);
        }
    }
    
    public static Bitmap pathToBitMap(String path){
    	BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap photo = BitmapFactory.decodeFile(path, options);
        return photo;
    }

    /**
     * 获取4.4以后图片绝对路径
     * @param context
     * @param uri
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    
    private static String getPath(Context context, Intent data){
        Uri imageFileUri = data.getData();
        if (imageFileUri != null) {
            String uriStr = imageFileUri.toString();
            String path = uriStr.substring(10, uriStr.length());
            if (path.startsWith("com.sec.android.gallery3d")) {
                /*LogUtils.i("It's auto backup pic path:"
                        + imageFileUri.toString());*/
                return null;
            }
        } else {
            return null;
        }
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(imageFileUri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String localSelectPath = cursor.getString(columnIndex);
        return localSelectPath;
    }

    /**
     * 通过Uri获取图片在数据库表头列表
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
