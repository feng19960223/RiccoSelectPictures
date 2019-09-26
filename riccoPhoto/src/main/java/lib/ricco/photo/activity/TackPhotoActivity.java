package lib.ricco.photo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import lib.ricco.photo.PhotoPicker;
import lib.ricco.photo.R;
import lib.ricco.photo.manager.GlideApp;
import lib.ricco.photo.permission.PermissionListener;
import lib.ricco.photo.permission.PermissionManager;
import lib.ricco.photo.pick.PhotoFolderPopupWindow;
import lib.ricco.photo.pick.PhotoOptions;
import lib.ricco.photo.util.ClickUtils;
import lib.ricco.photo.util.CropUtil;
import lib.ricco.photo.util.PhotoProviderUtil;

public class TackPhotoActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvArrow;
    private GridView mGvPhotos;
    private RelativeLayout mRlBar;
    private HashMap<String, List<String>> mDiskPhotos;
    private List<String> mAllPhotos;
    private List<String> mSelectPhotos = new ArrayList<>();
    private GVAdapter mAdapter;
    private String mCamImageName;
    private static PhotoOptions photoOptions;
    private static PhotoPicker.PicCallBack picCallBack;
    private TextView mBtnComplete;
    private LinearLayout mLlTitleContainer;
    private PermissionManager helper;
    private static final int REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tack_photo);
        if (photoOptions == null) {
            finish();
            return;
        }
        if (photoOptions.paths != null)
            mSelectPhotos.addAll(Arrays.asList(photoOptions.paths)); // 已有数据
        initView();
        initEvent();
        initPermission();
    }

    /**
     * 检查权限
     */
    private void initPermission() {
        helper = PermissionManager.with(this)
                // 添加权限请求码
                .addRequestCode(REQUEST_CODE)
                // 设置权限，可以添加多个权限
                .permissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                // 设置权限监听器
                .setPermissionsListener(new PermissionListener() {
                    @Override
                    public void onGranted() {
                        // 当权限被授予时调用
                        initData();
                    }

                    @Override
                    public void onDenied() {
                        // 用户拒绝该权限时调用
                        finish();
                    }

                    @Override
                    public void onShowRationale(String[] permissions) {
                        helper.setIsPositive(true);
                        helper.request();
                    }
                }).request(); // 请求权限
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                helper.onPermissionResult(permissions, grantResults);
                break;
        }
    }

    /**
     * 开启选择
     */
    public static void startSelect(Activity context, PhotoOptions options, PhotoPicker.PicCallBack callBack) {
        photoOptions = options;
        picCallBack = callBack;
        context.startActivity(new Intent(context, TackPhotoActivity.class));
    }

    private void initData() {
        mAdapter = new GVAdapter();
        mGvPhotos.setAdapter(mAdapter);
        new Thread() {
            @Override
            public void run() {
                mDiskPhotos = PhotoProviderUtil.getDiskPhotos(TackPhotoActivity.this);
                mAllPhotos = PhotoProviderUtil.getAllPhotos(mDiskPhotos);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
    }

    private void initView() {
        mRlBar = findViewById(R.id.photo_picker_rl_bar);
        mIvBack = findViewById(R.id.photo_picker_iv_back);
        mIvArrow = findViewById(R.id.photo_picker_iv_arrow);
        mTvTitle = findViewById(R.id.photo_picker_tv_title);
        mGvPhotos = findViewById(R.id.photo_picker_gv_photos);
        mLlTitleContainer = findViewById(R.id.photo_picker_ll_titlecontainer);
        mBtnComplete = findViewById(R.id.tv_btn_complete);
        mBtnComplete.setVisibility(photoOptions.takeNum == 1 ? View.INVISIBLE : View.VISIBLE);
        mBtnComplete.setText(getText(R.string.photo_picker_lib_finish) + "(" + mSelectPhotos.size() + "/" + photoOptions.takeNum + ")");
        if (photoOptions.takeNum == -1) { // 无限张图片
            if (mSelectPhotos.size() == 0) {
                mBtnComplete.setText(getText(R.string.photo_picker_lib_finish));
            } else {
                mBtnComplete.setText(getText(R.string.photo_picker_lib_finish) + "(" + mSelectPhotos.size() + ")");
            }
        }
    }

    private void initEvent() {
        mIvBack.setOnClickListener(this);
        mBtnComplete.setOnClickListener(this);
        mGvPhotos.setOnItemClickListener(this);
        mLlTitleContainer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (ClickUtils.doubleClick(v)) return;
        if (v == mIvBack)
            finish();
        if (v == mLlTitleContainer)
            // 弹出图片选择文件夹
            showDirPopWindow();
        if (v == mBtnComplete) {
            // 完成图片多选
            String[] p = new String[mSelectPhotos.size()];
            mSelectPhotos.toArray(p);
            picCallBack.onPicSelected(p);
            finish();
        }
    }

    /**
     * 图片文件夹选择
     */
    private void showDirPopWindow() {
        Rect rectangle = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int height = rectangle.bottom - rectangle.top - mRlBar.getMeasuredHeight();
        PhotoFolderPopupWindow popupWindow =
                new PhotoFolderPopupWindow(this, height, new PhotoFolderPopupWindow.Callback() {
                    @Override
                    public void onSelect(PhotoFolderPopupWindow popupWindow, String folder) {
                        popupWindow.dismiss();
                        mAllPhotos = mDiskPhotos.get(folder);
                        if (getString(R.string.photo_picker_lib_all_photo).equals(folder))
                            mAllPhotos = PhotoProviderUtil.getAllPhotos(mDiskPhotos);
                        mTvTitle.setText(folder);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onDismiss() {
                        mIvArrow.setImageResource(R.drawable.ic_expand_more_black_24dp);
                    }

                    @Override
                    public void onShow() {
                        mIvArrow.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    }
                });
        popupWindow.setAdapter(this, mDiskPhotos);
        popupWindow.showAsDropDown(mRlBar);
    }

    /**
     * 图片点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            // 打开相机拍照，并返回相片
            toOpenCamera();
        } else {
            // 点击一张照片
            String path = mAllPhotos.get(position - 1);
            // 单张并开启裁剪
            if (photoOptions.crop && photoOptions.takeNum == 1) {
                CropActivity.startSelect(this, path, photoOptions, picCallBack);
                finish();
            } else if (photoOptions.takeNum == 1) {
                // 单张不开启裁剪
                picCallBack.onPicSelected(new String[]{path});
                finish();
            } else {
                // 多张
                GVAdapter.ViewHolder tag = (GVAdapter.ViewHolder) view.getTag();
                tag.mCbCheck.setChecked(!tag.mCbCheck.isChecked());
            }
        }
    }

    /**
     * 开启相机拍照
     */
    private void toOpenCamera() {
        // 判断是否挂载了SD卡
        mCamImageName = null;
        String savePath = "";
        if (CropUtil.hasSDCard()) {
            savePath = CropUtil.getCameraPath();
            File saveDir = new File(savePath);
            if (!saveDir.exists())
                saveDir.mkdirs();
        }
        // 没有挂载SD卡，无法保存文件
        if (TextUtils.isEmpty(savePath)) {
            Toast.makeText(this, getString(R.string.photo_picker_lib_save_hint), Toast.LENGTH_LONG).show();
            return;
        }
        mCamImageName = CropUtil.getSaveImageFullName();
        File out = new File(savePath, mCamImageName);
        // android N 系统适配
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, out.getAbsolutePath());
            uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            uri = Uri.fromFile(out);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 0x03);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == 0x03) {
            if (mCamImageName == null)
                return;
            // 刷新显示
            String path = CropUtil.getCameraPath() + mCamImageName;
            mAllPhotos.add(0, path);
            mAdapter.notifyDataSetChanged();
            // 通知系统相册更新
            Uri localUri = Uri.fromFile(new File(path));
            Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
            sendBroadcast(localIntent);
            // 需要裁剪
            if (photoOptions.crop && photoOptions.takeNum < 2) {
                CropActivity.startSelect(this, path, photoOptions, picCallBack);
                finish();
            }
        }
    }

    private class GVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mAllPhotos == null ? 0 : mAllPhotos.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return mAllPhotos == null ? null : mAllPhotos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photor, parent, false);
                holder = new ViewHolder(view);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == 0) {
                holder.setCameraPic();
            } else {
                holder.setData(mAllPhotos.get(position - 1));
            }
            return holder.itemView;
        }

        class ViewHolder implements CompoundButton.OnCheckedChangeListener {
            View itemView;
            ImageView mIvPhoto;
            CheckBox mCbCheck;
            String mPath;

            public ViewHolder(View itemView) {
                this.itemView = itemView;
                int screenWidth = CropUtil.getScreenWidth(itemView.getContext());
                int itemSize = (int) ((screenWidth - CropUtil.dip2Px(itemView.getContext(), 4) + 0.5f) / 3);
                ViewGroup.LayoutParams params = itemView.getLayoutParams();
                params.height = itemSize;
                params.width = itemSize;
                itemView.setLayoutParams(params);
                itemView.setTag(this);
                mIvPhoto = itemView.findViewById(R.id.item_iv_photo);
                mCbCheck = itemView.findViewById(R.id.item_cb_check);
                mCbCheck.setOnCheckedChangeListener(this);
            }

            public void setData(String path) {
                mPath = path;
                mCbCheck.setChecked(mSelectPhotos.contains(mPath)); // 勾选框复用问题
                mCbCheck.setVisibility(photoOptions.takeNum == 1 ? View.GONE : View.VISIBLE);
                GlideApp.with(mIvPhoto.getContext()).load(path).centerCrop().into(mIvPhoto);
            }

            public void setCameraPic() {
                mCbCheck.setVisibility(View.GONE);
                mIvPhoto.setImageResource(R.drawable.ic_camera_alt_black_24dp);
            }

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mSelectPhotos.contains(mPath))
                        return;
                    if (photoOptions.takeNum == -1) {
                        mSelectPhotos.add(mPath);
                    } else {
                        if (mSelectPhotos.size() < photoOptions.takeNum) {
                            mSelectPhotos.add(mPath);
                        } else {
                            String text = String.format(getString(R.string.photo_picker_lib_max_hint), photoOptions.takeNum);
                            Toast.makeText(TackPhotoActivity.this, text, Toast.LENGTH_SHORT).show();
                            mCbCheck.setChecked(false);
                        }
                    }
                } else {
                    mSelectPhotos.remove(mPath);
                }
                if (photoOptions.takeNum == -1) {
                    if (mSelectPhotos.size() == 0) {
                        mBtnComplete.setText(getString(R.string.photo_picker_lib_finish));
                    } else {
                        mBtnComplete.setText(getString(R.string.photo_picker_lib_finish) + "(" + mSelectPhotos.size() + ")");
                    }
                } else {
                    mBtnComplete.setText(getString(R.string.photo_picker_lib_finish) + "(" + mSelectPhotos.size() + "/" + photoOptions.takeNum + ")");
                }
            }
        }
    }
}
