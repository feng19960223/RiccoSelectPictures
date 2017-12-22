# RiccoSelectPictures
照片选择库，支持(单一、指定、无限)张图片选择，单张图片剪裁，拍照，去重

## 优势
* 支持Android 8.0
* 最低兼容API 19
* Android 6.0 自动申请相机和读写权限
* 去重
* 无限选择张数;

## 流程图

![脑图](http://upload-images.jianshu.io/upload_images/4325663-72aa1f8e04ba56a6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240 "脑图")

## 类

### [PhotoPicker](https://github.com/feng19960223/RiccoSelectPictures/blob/master/riccoPhoto/src/main/java/lib/ricco/photo/PhotoPicker.java "class")

* 得到一张无剪裁图片

#### static void	selectPic(android.app.Activity context, PhotoPicker.PicCallBack callBack)

    context -

    callBack - 选择图片回调

* 得到一张正方形裁剪的图片

#### static void	selectPic(android.app.Activity context, int cropWH, PhotoPicker.PicCallBack callBack)

    context -

    cropWH - 裁剪宽高

    callBack - 选择图片回调

* 得到picNum张图片

#### static void	selectPics(android.app.Activity context, int picNum, PhotoPicker.PicCallBack callBack)

    context -

    picNum - 选择图片张数

    callBack - 选择图片回调

* 得到picNum张图片，去重复

#### static void	selectPics(android.app.Activity context, int picNum, java.lang.String[] paths , PhotoPicker.PicCallBack callBack)

    context -

    picNum - 选择图片张数

    paths - 默认要勾选的数据

    callBack - 选择图片回调

* 得到无限张图片

#### static void	selectPics(android.app.Activity context, PhotoPicker.PicCallBack callBack)

    context -

    callBack - 选择图片

* 得到无限张图片，去重复

#### static void	selectPics(android.app.Activity context, java.lang.String[] paths , PhotoPicker.PicCallBack callBack)

    context -

    paths - 默认要勾选的数据

    callBack - 选择图片回调


## 接口

### [PhotoPicker.PicCallBack](https://github.com/feng19960223/RiccoSelectPictures/blob/master/riccoPhoto/src/main/java/lib/ricco/photo/PhotoPicker.java "interface")

#### onPicSelected(java.lang.String[] path)

    path - 所有选中的图片地址

# 效果图

![选择一张照片.png](http://upload-images.jianshu.io/upload_images/4325663-9fd8416f6e7243c9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/310 "选择一张照片")

![选择菜单栏.png](http://upload-images.jianshu.io/upload_images/4325663-ede39c9daabb68de.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/310 "选择菜单栏")

![一张图片剪裁.png](http://upload-images.jianshu.io/upload_images/4325663-46612e4df14dd0fc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/310 "一张图片剪裁")

![指定张图片.png](http://upload-images.jianshu.io/upload_images/4325663-9b0c3d0b71f07a1d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/310 "指定张图片")

![无限张图片.png](http://upload-images.jianshu.io/upload_images/4325663-5c3be6e76b6ae9e3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/310 "无限张图片")
