package lib.ricco.photo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hema58.app.join.widget.CustomTouchPhotoView;

import lib.ricco.photo.R;
import lib.ricco.photo.util.ClickUtils;

/**
 * <p>时间：2019/10/23<p>
 * <p>作者：冯国芮<p>
 * <p>描述：查看照片<p>
 */
public class LookPhotoActivity extends Activity implements View.OnClickListener {
    private ImageView mIvBack;
    private CustomTouchPhotoView mCustomTouchPhotoView;
    private static String mUrl;

    /**
     * 开启裁剪
     */
    public static void startSelect(Activity context, String url) {
        mUrl = url;
        context.startActivity(new Intent(context, LookPhotoActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_photo);
        initView();
        initEvent();
        initData();
    }

    private void initView() {
        mIvBack = findViewById(R.id.photo_picker_iv_back);
        mCustomTouchPhotoView = findViewById(R.id.customTouchPhotoView);
    }

    private void initEvent() {
        mIvBack.setOnClickListener(this);
    }

    private void initData() {
        try {
            RequestOptions requestOptions = new RequestOptions();
            //禁用磁盘缓存
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);///不使用磁盘缓存
            requestOptions.skipMemoryCache(true); // 不使用内存缓存
            Glide.with(this).applyDefaultRequestOptions(requestOptions).load(mUrl).into(mCustomTouchPhotoView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (ClickUtils.doubleClick(v)) return;
        if (v == mIvBack)
            finish();
    }
}
