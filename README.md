# RiccoSelectPictures

## 建议下载源码，根据自己的Glide，和SDK版本进行特定的编译

照片选择库，支持(单一、指定、无限)张图片选择，单张图片剪裁（正方形，长方形），拍照，去重

## 优势
* 支持Android 8.0
* 最低兼容API 19
* Android 6.0 自动申请相机和读写权限
* 去重，去重，去重
* 无限选择张数;

## 样式修改
* 可以直接在自己的主项目里新建名字相同的资源文件，目前只支持，文案，颜色，和部分图片的修改，不建议修改styles

### String
<table>
    <tr>
        <td>名称</td>
        <td>默认内容</td>
        <td>说明</td>
    </tr>
    <tr>
        <td>photo_picker_lib_crop</td>
        <td>裁剪</td>
        <td>选择一张剪裁的图片，底部按钮文字</td>
    </tr>
    <tr>
        <td>photo_picker_lib_cancel</td>
        <td>取消</td>
        <td>选择一张剪裁的图片，底部按钮文字</td>
    </tr>
    <tr>
        <td>photo_picker_lib_finish</td>
        <td>完成</td>
        <td>选择多张图片，顶部按钮文字</td>
    </tr>
    <tr>
        <td>photo_picker_lib_all_photo</td>
        <td>全部照片</td>
        <td>选择多张图片，顶部默认title</td>
    </tr>
    <tr>
        <td>photo_picker_lib_max_hint</td>
        <td>您最多只能选择%1$d张照片！</td>
        <td>选择多张有限的图片，当选择数量达到</td>
    </tr>
    <tr>
        <td>photo_picker_lib_save_hint</td>
        <td>无法保存照片，请检查SD卡是否可用</td>
        <td>文件存储异常是提示</td>
    </tr>
</table>

### Color
<table>
    <tr>
        <td>名称</td>
        <td>默认内容</td>
        <td>说明</td>
    </tr>
    <tr>
        <td>colorRiccoPrimary</td>
        <td>#3F51B5</td>
        <td>相当于 系统colorPrimary，控制页面整体颜色</td>
    </tr>
    <tr>
        <td>colorRiccoPrimaryDark</td>
        <td>#303F9F</td>
        <td>相当于 系统colorPrimaryDark，控制页面整体颜色</td>
    </tr>
    <tr>
        <td>colorRiccoAccent</td>
        <td>#303F9F</td>
        <td>相当于 系统colorAccent，控制页面整体颜色</td>
    </tr>
    <tr>
        <td>color_ricco_btn_normal</td>
        <td>#FFFF4081</td>
        <td>确定按钮，默认背景颜色</td>
    </tr>
    <tr>
        <td>color_ricco_btn_pressed</td>
        <td>#CCFF4081</td>
        <td>确定按钮，按下背景颜色</td>
    </tr>
</table>

### drawbale
<table>
    <tr>
        <td>名称</td>
        <td>说明</td>
    </tr>
    <tr>
        <td>ic_camera_alt_black_24dp.xml</td>
        <td>相机，应用中点击相机，会打开手机拍照</td>
    </tr>
    <tr>
        <td>ic_chevron_left_black_24dp.xml</td>
        <td>返回箭头</td>
    </tr>
    <tr>
        <td>ic_expand_less_black_24dp.xml</td>
        <td>向上箭头</td>
    </tr>
    <tr>
        <td>ic_expand_more_black_24dp.xml</td>
        <td>向下箭头</td>
    </tr>
    <tr>
        <td>ic_photo_library_black_24dp.xml</td>
        <td>图片展位图，使用Glide加载本地文件，基本上看不到</td>
    </tr>
    <tr>
        <td>selector_btn_complete.xml</td>
        <td>确定按钮背景颜色</td>
    </tr>
</table>

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

* 得到一张长方形裁剪的图片

#### static void	selectPic(android.app.Activity context, int cropW, int cropH, PhotoPicker.PicCallBack callBack)

    context -

    cropW - 裁剪宽

    cropW - 裁剪高

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

<img src="https://github.com/feng19960223/RiccoSelectPictures/blob/master/screenshots/%E5%89%AA%E8%A3%81%E6%AD%A3%E6%96%B9%E5%BD%A2.png" width="260" /><img src="https://github.com/feng19960223/RiccoSelectPictures/blob/master/screenshots/%E5%89%AA%E8%A3%81%E9%95%BF%E6%96%B9%E5%BD%A2.png" width="260" /><img src="https://github.com/feng19960223/RiccoSelectPictures/blob/master/screenshots/%E9%80%89%E6%8B%A9%E5%85%B6%E4%BB%96%E7%9B%AE%E5%BD%95.png" width="260" />
<img src="https://github.com/feng19960223/RiccoSelectPictures/blob/master/screenshots/%E9%80%89%E6%8C%87%E5%AE%9A%E5%BC%A0.png" width="260" /><img src="https://github.com/feng19960223/RiccoSelectPictures/blob/master/screenshots/%E9%80%89%E6%97%A0%E9%99%90%E5%BC%A0.png" width="260" />
