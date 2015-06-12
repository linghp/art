package com.shangxian.art.photochooser;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.shangxian.art.R;


public class PhotoPickActivity extends ActionBarActivity {

    TextView mFoldName;
    View mListViewGroup;
    ListView mListView;
    GridView mGridView;
    LayoutInflater mInflater;

    public static final String EXTRA_MAX = "EXTRA_MAX";
    public static final String EXTRA_PICKED = "EXTRA_PICKED"; // mPickData
    private int mMaxPick = 6;

    public static DisplayImageOptions optionsImage = new DisplayImageOptions
            .Builder()
            .showImageOnLoading(R.drawable.image_empty)
            .showImageForEmptyUri(R.drawable.image_error)
            .showImageOnFail(R.drawable.image_error)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
            .build();

    private TextView mPreView;

    public static class ImageInfo implements Serializable {
        public String path;
        public long photoId;
        public int width;
        public int height;

        public ImageInfo(String path) {
            this.path = path;
        }
    }

    LinkedHashMap<String, ArrayList<ImageInfo>> mFolders = new LinkedHashMap();
    ArrayList<String> mFoldersName = new ArrayList();

    ArrayList<ImageInfo> mPickData = new ArrayList();

    final String allPhotos = "所有图片";

    final String CameraItem = "CameraItem";

    long lastTime;

    private void displayTime(int pos) {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_pick);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("图片");
        actionBar.setDisplayHomeAsUpEnabled(true);

        mMaxPick = getIntent().getIntExtra(EXTRA_MAX, 6);
        Object extraPicked = getIntent().getSerializableExtra(EXTRA_PICKED);
        if (extraPicked != null) {
            mPickData = (ArrayList<ImageInfo>) extraPicked;
        }

        mInflater = getLayoutInflater();
        mGridView = (GridView) findViewById(R.id.gridView);
        mListView = (ListView) findViewById(R.id.listView);
        mListViewGroup = findViewById(R.id.listViewParent);
        mListViewGroup.setOnClickListener(mOnClickFoldName);
        mFoldName = (TextView) findViewById(R.id.foldName);

        findViewById(R.id.selectFold).setOnClickListener(mOnClickFoldName);

        mPreView = (TextView) findViewById(R.id.preView);
        mPreView.setOnClickListener(onClickPre);

        lastTime = System.currentTimeMillis();

        displayTime(0);

        String[] projection = {MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.WIDTH,
                MediaStore.Images.ImageColumns.HEIGHT,
                MediaStore.Images.ImageColumns.MINI_THUMB_MAGIC};

        String selection = "";
        String[] selectionArgs = null;
        Cursor mImageExternalCursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, MediaStore.MediaColumns.DATE_ADDED + " DESC");

        displayTime(0);

        ArrayList<ImageInfo> allPhoto = new ArrayList();
        allPhoto.add(new ImageInfo(CameraItem));
        mFoldersName.add(allPhotos);
        

        while (mImageExternalCursor.moveToNext()) {
            String s0 = mImageExternalCursor.getString(0);
            String s1 = mImageExternalCursor.getString(1);
            String s2 = mImageExternalCursor.getString(2);
            int width = mImageExternalCursor.getInt(3);
            int height = mImageExternalCursor.getInt(4);
            long thumbnailId = mImageExternalCursor.getLong(5);

            String s = String.format("%s,%s,%s, %s, %s, %s", s0, s1, s2, width, height, thumbnailId);
            Log.d("", "sss " + s);
            if (isImageUri(s1)) {
                s1 = "file://" + s1;
            }
            ImageInfo imageInfo = new ImageInfo(s1);
            imageInfo.photoId = Long.valueOf(s0);
            imageInfo.width = width;
            imageInfo.height = height;

            ArrayList<ImageInfo> value = mFolders.get(s2);
            if (value == null) {
                value = new ArrayList<ImageInfo>();
                mFolders.put(s2, value);
                mFoldersName.add(s2);
            }
            allPhoto.add(imageInfo);
            value.add(imageInfo);
        }
        
        mFolders.put(allPhotos, allPhoto);

        displayTime(1);

        mPhotoAdapter.setData(mFolders.get(mFoldersName.get(0)));
        mListView.setAdapter(mFoldAdapter);
        mListView.setOnItemClickListener(mOnItemClick);

        displayTime(2);

        mGridView.setAdapter(mPhotoAdapter);
        mGridView.setOnItemClickListener(mOnPhotoItemClick);
        displayTime(3);

        // 必须这么刷一下，否则很卡，也许是ImageLoader某个地方的线程写的有问题，当然了，更有可能是我用的的有问题：），先这样吧
        mGridView.post(new Runnable() {
            @Override
            public void run() {
                mPhotoAdapter.notifyDataSetChanged();
            }
        });

        String folderName = mFoldersName.get(0);
        mFoldName.setText(folderName);
    }
    
    public static boolean isImageUri(String s1) {
        s1 = s1.toLowerCase();
        return s1.endsWith(".png")
                || s1.endsWith(".jpg")
                || s1.endsWith(".jpeg")
                || s1.endsWith(".bmp")
                || s1.endsWith(".gif");
    }

    View.OnClickListener onClickPre = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPickData.size() == 0) {
                return;
            }

            Intent intent = new Intent(PhotoPickActivity.this, PhotoPickDetailActivity.class);
            intent.putExtra(PhotoPickDetailActivity.ALL_DATA, mPickData);
            intent.putExtra(PhotoPickDetailActivity.PICK_DATA, mPickData);
            intent.putExtra(PhotoPickDetailActivity.EXTRA_MAX, mMaxPick);
            startActivityForResult(intent, RESULT_PICK);
        }
    };

    ListView.OnItemClickListener mOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String folderName = mFoldersName.get((int) id);
            mPhotoAdapter.setData(mFolders.get(folderName));
            mPhotoAdapter.notifyDataSetChanged();
            mFoldName.setText(folderName);
            mFoldAdapter.notifyDataSetChanged();
            hideFolderList();
        }
    };

    GridView.OnItemClickListener mOnPhotoItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(PhotoPickActivity.this, PhotoPickDetailActivity.class);
            intent.putExtra(PhotoPickDetailActivity.ALL_DATA, mPhotoAdapter.getData());
            intent.putExtra(PhotoPickDetailActivity.PICK_DATA, mPickData);
            intent.putExtra(PhotoPickDetailActivity.EXTRA_MAX, mMaxPick);
            intent.putExtra(PhotoPickDetailActivity.PHOTO_BEGIN, (int) id);
            startActivityForResult(intent, RESULT_PICK);
        }
    };

    View.OnClickListener mOnClickFoldName = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListViewGroup.getVisibility() == View.VISIBLE) {
                hideFolderList();
            } else {
                showFolderList();
            }
        }

    };

    private void showFolderList() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.listview_up);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.listview_fade_in);

        mListView.startAnimation(animation);
        mListViewGroup.startAnimation(fadeIn);
        mListViewGroup.setVisibility(View.VISIBLE);
    }

    private void hideFolderList() {
        Animation animation = AnimationUtils.loadAnimation(PhotoPickActivity.this, R.anim.listview_down);
        Animation fadeOut = AnimationUtils.loadAnimation(PhotoPickActivity.this, R.anim.listview_fade_out);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mListViewGroup.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        mListView.startAnimation(animation);
        mListViewGroup.startAnimation(fadeOut);
    }

    MenuItem mMenuItem;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_pick, menu);
        mMenuItem = menu.getItem(0);
        updatePickCount();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_finish) {
            send();
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void send() {
        if (mPickData.isEmpty()) {
            setResult(Activity.RESULT_CANCELED);
        } else {
            Intent intent = new Intent();
            intent.putExtra("data", mPickData);
            setResult(Activity.RESULT_OK, intent);
        }

        finish();
    }

    private static final String RESTORE_FILEURI = "fileUri";
    private Uri fileUri;

    public void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = CameraPhotoUtil.getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, RESULT_CAMERA);
    }

    final int RESULT_PICK = 20;
    final int RESULT_CAMERA = 21;


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        fileUri = savedInstanceState.getParcelable(RESTORE_FILEURI);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (fileUri != null) {
            outState.putParcelable(RESTORE_FILEURI, fileUri);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_PICK) {
            if (resultCode == RESULT_OK) {
                ArrayList<ImageInfo> pickArray = (ArrayList<ImageInfo>) data.getSerializableExtra("data");
                mPickData = pickArray;
                mPhotoAdapter.notifyDataSetChanged();
                boolean send = data.getBooleanExtra("send", false);
                if (send) {
                    send();
                }
            }
        } else if (requestCode == RESULT_CAMERA) {
            if (resultCode == RESULT_OK) {
                ArrayList<ImageInfo> pickArray = new ArrayList<>();

                ImageInfo itme = new ImageInfo(fileUri.toString());
                pickArray.add(itme);
                mPickData = pickArray;
                send();
            } else {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isPicked(String path) {
        for (ImageInfo item : mPickData) {
            if (item.path.equals(path)) {
                return true;
            }
        }

        return false;
    }

    private void addPicked(String path) {
        if (!isPicked(path)) {
            mPickData.add(new ImageInfo(path));
        }
    }

    private void removePicked(String path) {
        for (int i = 0; i < mPickData.size(); ++i) {
            if (mPickData.get(i).path.equals(path)) {
                mPickData.remove(i);
                return;
            }
        }
    }

    class GridAdapter extends BaseAdapter {

        ArrayList<ImageInfo> mData = new ArrayList<ImageInfo>();

        public void setData(ArrayList<ImageInfo> data) {
            mData = data;
        }

        public ArrayList<ImageInfo> getData() {
            return mData;
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
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        private final int TYPE_CAMERA = 0;
        private final int TYPE_PHOTO = 1;

        @Override
        public int getItemViewType(int position) {
            ImageInfo imageInfo = (ImageInfo) getItem(position);
            if (imageInfo.path.equals(CameraItem)) {
                return TYPE_CAMERA;
            } else {
                return TYPE_PHOTO;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            int width =  getResources().getDisplayMetrics().widthPixels / 3;
            if (type == TYPE_PHOTO) {
                GridViewHolder holder;
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.photopick_gridlist_item, parent, false);
                    convertView.getLayoutParams().height = width;

                    holder = new GridViewHolder();
                    holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                    holder.iconFore = (ImageView) convertView.findViewById(R.id.iconFore);
                    holder.check = (CheckBox) convertView.findViewById(R.id.check);
                    GridViewCheckTag checkTag = new GridViewCheckTag(holder.iconFore);
                    holder.check.setTag(checkTag);
                    holder.check.setOnClickListener(mClickItem);
                    convertView.setTag(holder);
                } else {
                    holder = (GridViewHolder) convertView.getTag();
                }

                ImageInfo data = (ImageInfo) getItem(position);
                ImageLoader imageLoader = ImageLoader.getInstance();

//                holder.icon.setImageBitmap(MediaStore.Images.Thumbnails.getThumbnail(getContentResolver(), 10797, MediaStore.Images.Thumbnails.MINI_KIND, null));

//                Cursor c = MediaStore.Images.Thumbnails.queryMiniThumbnail(getContentResolver(), data.photoId, MediaStore.Images.Thumbnails.MINI_KIND, new String[]{MediaStore.Images.Thumbnails.DATA});
//                if (c != null && c.moveToNext()) {
//                    imageLoader.displayImage("file://" + c.getString(0), holder.icon, optionsImage);
//                }

                imageLoader.displayImage(data.path, holder.icon, optionsImage);

                ((GridViewCheckTag) holder.check.getTag()).path = data.path;

                boolean picked = isPicked(data.path);
                holder.check.setChecked(picked);
                holder.iconFore.setVisibility(picked ? View.VISIBLE : View.INVISIBLE);

                return convertView;
            } else {
                final GridCameraHolder cameraHolder;
                if (convertView == null) {

                    lastTime = System.currentTimeMillis();

                    convertView = mInflater.inflate(R.layout.photopick_gridlist_item_camera, parent, false);

                    ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
                    layoutParams.height = width;
                    layoutParams.width = width;

                    cameraHolder = new GridCameraHolder();
                    cameraHolder.cameraPreview = (CameraPreview) convertView.findViewById(R.id.cameraPreview);
                    cameraHolder.cameraPreview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cameraHolder.cameraPreview.stopAndReleaseCamera();
                            camera();
                        }
                    });

                    displayTime(5);
                }

                return convertView;
            }
        }

        View.OnClickListener mClickItem = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GridViewCheckTag tag = (GridViewCheckTag) v.getTag();
                if (((CheckBox) v).isChecked()) {
                    if (mPickData.size() >= mMaxPick) {
                        ((CheckBox) v).setChecked(false);
                        String s = String.format("最多只能选择%d张", mMaxPick);
                        Toast.makeText(PhotoPickActivity.this, s, Toast.LENGTH_LONG).show();
                        return;
                    }

                    addPicked(tag.path);
                    tag.iconFore.setVisibility(View.VISIBLE);
                } else {
                    removePicked(tag.path);
                    tag.iconFore.setVisibility(View.INVISIBLE);
                }
                mFoldAdapter.notifyDataSetChanged();

                updatePickCount();
            }
        };
    }

    private void updatePickCount() {
        String format = "完成(%d/%d)";
        mMenuItem.setTitle(String.format(format, mPickData.size(), mMaxPick));

        String formatPreview = "预览(%d/%d)";
        mPreView.setText(String.format(formatPreview, mPickData.size(), mMaxPick));
    }

    GridAdapter mPhotoAdapter = new GridAdapter();

    static class GridViewCheckTag {
        View iconFore;
        String path = "";

        GridViewCheckTag(View iconFore) {
            this.iconFore = iconFore;
        }
    }

    static class GridViewHolder {
        ImageView icon;
        ImageView iconFore;
        CheckBox check;
    }

    static class GridCameraHolder {
        CameraPreview cameraPreview;
    }

    BaseAdapter mFoldAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mFoldersName.size();
        }

        @Override
        public Object getItem(int position) {
            return mFoldersName.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.photopick_list_item, parent, false);
                holder = new ViewHolder();
                holder.foldIcon = (ImageView) convertView.findViewById(R.id.foldIcon);
                holder.foldName = (TextView) convertView.findViewById(R.id.foldName);
                holder.photoCount = (TextView) convertView.findViewById(R.id.photoCount);
                holder.check = convertView.findViewById(R.id.check);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String name = (String) getItem(position);
            ArrayList<ImageInfo> imageInfos = mFolders.get(name);
            String uri = imageInfos.get(0).path;
            int count = imageInfos.size();

            holder.foldName.setText(name);
            holder.photoCount.setText(String.format("%d张", count));

            // 如果是照相机，就用下一张图片
            if (uri.equals(CameraItem)) {
                if (imageInfos.size() >= 2) {
                    uri = imageInfos.get(1).path;
                }
            }

            ImageLoader.getInstance().displayImage(uri, holder.foldIcon, optionsImage);

            if (mFoldName.getText().toString().equals(name)) {
                holder.check.setVisibility(View.VISIBLE);
            } else {
                holder.check.setVisibility(View.INVISIBLE);
            }

            return convertView;
        }

    };

    static class ViewHolder {
        ImageView foldIcon;
        TextView foldName;
        TextView photoCount;
        View check;
    }
}
