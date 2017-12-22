package lib.ricco.photo;

import android.app.Activity;
import android.support.annotation.NonNull;

import lib.ricco.photo.activity.TackPhotoActivity;
import lib.ricco.photo.pick.PhotoOptions;


public class PhotoPicker {

    /**
     * 基本用法
     *
     * @param context
     * @param picNum     选择图片张数 大于1位多选，1 为单选
     * @param crop       是否裁剪 ，只对单选有效
     * @param cropWidth  裁剪宽度，只对单选有效
     * @param cropHeight 裁剪高度，只对单选有效
     * @param callBack   选择图片回调
     */
    private static void selectPic(Activity context, int picNum, boolean crop, int cropWidth, int cropHeight, String[] paths, @NonNull PicCallBack callBack) {
        PhotoOptions options = new PhotoOptions();
        options.crop = crop;
        options.takeNum = picNum;
        options.cropWidth = cropWidth;
        options.cropHeight = cropHeight;
        options.paths = paths;
        TackPhotoActivity.startSelect(context, options, callBack);
    }

    /**
     * 得到一张无剪裁图片
     *
     * @param context
     * @param callBack 选择图片回调
     */
    public static void selectPic(Activity context, @NonNull PicCallBack callBack) {
        selectPic(context, 1, false, 0, 0, null, callBack);
    }

    /**
     * 得到一张正方形裁剪的图片
     *
     * @param context
     * @param cropWH   裁剪宽高
     * @param callBack 选择图片回调
     */
    public static void selectPic(Activity context, int cropWH, @NonNull PicCallBack callBack) {
        selectPic(context, 1, true, cropWH, cropWH, null, callBack);
    }

    /**
     * 得到picNum张图片
     *
     * @param context
     * @param picNum   选择图片张数
     * @param callBack 选择图片回调
     */
    public static void selectPics(Activity context, int picNum, @NonNull PicCallBack callBack) {
        selectPic(context, picNum, false, 0, 0, null, callBack);
    }

    /**
     * 得到picNum张图片，去重复
     *
     * @param context
     * @param picNum   选择图片张数
     * @param paths    默认要勾选的数据
     * @param callBack 选择图片回调
     */
    public static void selectPics(Activity context, int picNum, String[] paths, @NonNull PicCallBack callBack) {
        selectPic(context, picNum, false, 0, 0, paths, callBack);
    }

    /**
     * 得到无限张图片
     *
     * @param context
     * @param callBack 选择图片回调
     */
    public static void selectPics(Activity context, @NonNull PicCallBack callBack) {
        selectPic(context, -1, false, 0, 0, null, callBack);
    }

    /**
     * 得到无限张图片，去重复
     *
     * @param context
     * @param paths    默认要勾选的数据
     * @param callBack 选择图片回调
     */
    public static void selectPics(Activity context, String[] paths, @NonNull PicCallBack callBack) {
        selectPic(context, -1, false, 0, 0, paths, callBack);
    }

    public interface PicCallBack {
        void onPicSelected(String[] path);
    }
}
