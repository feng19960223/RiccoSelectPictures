package lib.ricco.photo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import lib.ricco.photo.PhotoPicker;
import lib.ricco.photo.R;
import lib.ricco.photo.crop.CropLayout;
import lib.ricco.photo.crop.ZoomImageView;
import lib.ricco.photo.pick.PhotoOptions;
import lib.ricco.photo.util.CropUtil;

public class CropActivity extends Activity implements View.OnClickListener {
    private static PhotoOptions photoOptions;
    private static PhotoPicker.PicCallBack picCallBack;
    private CropLayout mGl;
    private Button btn1;
    private Button btn2;
    private static String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_crop);
        initView();
        initEvent();
        initData();
    }

    /**
     * 开启裁剪
     */
    public static void startSelect(Activity context, String url, PhotoOptions options, PhotoPicker.PicCallBack callBack) {
        mUrl = url;
        photoOptions = options;
        picCallBack = callBack;
        context.startActivity(new Intent(context, CropActivity.class));
    }

    private void initView() {
        mGl = findViewById(R.id.gl);
        btn1 = findViewById(R.id.take_btn1);
        btn2 = findViewById(R.id.take_btn2);
    }

    private void initEvent() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn1) {
            Bitmap bitmap = mGl.cropBitmap();
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
        if (v == btn2)
            finish();
    }

    private void initData() {
        ZoomImageView imageView = mGl.getImageView();
        new BitmapWorkerTask(imageView).execute(mUrl);
        mGl.setCropWidth(photoOptions.cropWidth);
        mGl.setCropHeight(photoOptions.cropHeight);
        mGl.start();
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private String data = null;

        public BitmapWorkerTask(ImageView imageView) {
            // 使用弱引用来确保图像视图可以被垃圾收集
            imageViewReference = new WeakReference<>(imageView);
        }

        /**
         * 解码图像在后台
         */
        @Override
        protected Bitmap doInBackground(String... params) {
            data = params[0];
            return CropUtil.decodeSampledBitmapFromFile(data, CropUtil.getScreenWidth(CropActivity.this), CropUtil.getScreenWidth(CropActivity.this));
        }

        /**
         * 一旦完成，请查看ImageView是否还在，并设置位图
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null)
                    imageView.setImageBitmap(bitmap);
            }
        }
    }
}



