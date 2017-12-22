package lib.ricco.photo.manager;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * 用来生成GlideApp类，如果没有多clean/build几次
 * TgAppGlideModule. Glide为运用程序提供了一个generated API.
 * 运用程序中应该创建一个带有@GlideModule注解的AppGlideModule子类，来使用generated API.GlideApp类，该类能够提供对RequestBuilder和RequestOptions子类的访问。
 * RequestOptions子类包含RequestOptions包含的全部方法和GlideExtensions定义的方法。RequestBuilder子类不需要使用apply方法，来访问RequestBuilder子类中全部方法。
 */
@GlideModule
public class TgAppGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(final Context context, final GlideBuilder builder) {
        // 配置图片池大小   100MB
        builder.setBitmapPool(new LruBitmapPool(1024 * 1024 * 100));
        // 缓存大小 100MB
        int cacheSize = 1024 * 1024 * 100;
        // 内存缓存
        builder.setMemoryCache(new LruResourceCache(cacheSize));
        // 磁盘缓存.私有缓存 (自己本app可以使用,目录在data/data/应用包名 下面)
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSize));
        // 磁盘缓存 自定义路径
        //builder.setDiskCache(new DiskLruCacheFactory(TgSystemConfig.BASE_STORE_PATH, TgSystemConfig.IMG_PATH, cacheSize));
    }
}
