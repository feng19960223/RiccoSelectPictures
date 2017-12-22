package lib.ricco.photo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CropUtil {

    public static boolean hasSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getCameraPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/"; // filePath:/sdcard/
    }

    public static String getSaveImageFullName() {
        return "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg"; // 照片命名
    }


    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * dip-->px
     */
    public static int dip2Px(Context context, int dip) {
        // 取得当前手机px和dp的倍数关系
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + .5f);
        return px;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 图像的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }

        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFile(String filename, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
    }

}
