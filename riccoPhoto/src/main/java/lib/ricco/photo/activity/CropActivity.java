package lib.ricco.photo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import lib.ricco.photo.PhotoPicker;
import lib.ricco.photo.R;
import lib.ricco.photo.crop.CropView;
import lib.ricco.photo.pick.PhotoOptions;
import lib.ricco.photo.util.CropUtil;

public class CropActivity extends Activity implements View.OnClickListener {

    /**
     * 开启裁剪
     */
    public static void startSelect(Activity context, String url, PhotoOptions options, PhotoPicker.PicCallBack callBack) {
        mUrl = url;
        photoOptions = options;
        picCallBack = callBack;
        context.startActivity(new Intent(context, CropActivity.class));
    }

    private static String mUrl;
    private static PhotoOptions photoOptions;
    private static PhotoPicker.PicCallBack picCallBack;
    private CropView cropView;
    private TextView tvCancel;
    private TextView tvCrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        initView();
        initEvent();
        initData();
    }

    private void initView() {
        cropView = findViewById(R.id.crop_view);
        tvCancel = findViewById(R.id.tv_cancel);
        tvCrop = findViewById(R.id.tv_crop);
    }

    private void initEvent() {
        tvCrop.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    private void initData() {
        cropView.of(Uri.fromFile(new File(mUrl)))
                .withAspect(photoOptions.cropWidth, photoOptions.cropHeight)
                .withOutputSize(photoOptions.cropWidth, photoOptions.cropHeight)
                .initialize(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tvCancel) {
            finish();
        }
        if (v == tvCrop) {
            Bitmap bitmap = cropView.getOutput();
            String path = CropUtil.getCameraPath() + CropUtil.getSaveImageFullName();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(new File(path));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                picCallBack.onPicSelected(new String[]{path});
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null)
                        fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            finish();
        }
    }
}



