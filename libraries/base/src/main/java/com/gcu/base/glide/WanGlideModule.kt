package com.gcu.base.glide

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

/**
 * Glide配置
 */
@GlideModule
class WanGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        //获取内存计算器
        //setMemoryCacheScreens设置MemoryCache应该能够容纳的像素值的设备屏幕数，
        //说白了就是缓存多少屏图片，默认值是2
        val calculator = MemorySizeCalculator.Builder(context).setMemoryCacheScreens(2f).build()
        //获取Glide默认内存缓存大小
        val cacheSize = calculator.memoryCacheSize
        //获取Glide默认图片池大小
        val poolSize = calculator.bitmapPoolSize
        //指定内存缓存大小(设置为原来的1.5倍)
        builder.setMemoryCache(LruResourceCache((cacheSize * 1.5).toLong()))
        //设置图片池大小(设置为原来的1.5倍)
        builder.setBitmapPool(LruBitmapPool((poolSize * 1.5).toLong()))
        //设置磁盘缓存大小(1GB)
        val diskSize = 1024 * 1024 * 1024
        //私有缓存
        //指定位置在data/data/应用包名/cache/glide_cache,大小为1GB的磁盘缓存
        //如果包名不设置的话，将会使用默认路径data/data/应用包名/cache/image_manager_disk_cache
        builder.setDiskCache(
            InternalCacheDiskCacheFactory(
                context, "glide_cache", diskSize.toLong()
            )
        )

        //外部缓存
        //指定位置在SDCard/Android/data/应用包名/cache/glide_cache
        //如包名不设置，会用默认路径SDCard/Android/data/应用包名/cache/image_manager_disk_cache
        builder.setDiskCache(ExternalCacheDiskCacheFactory(context, "glide_cache", diskSize))

        //自定义磁盘缓存路径Android/glide/cache/glide_cache
        //需要注意的是这里的路径是配置的绝对路径,所以如果没有指定在sd卡目录下的话是无法直接看到的
        builder.setDiskCache(
            DiskLruCacheFactory(
                "Android/glide/cache/", "glide_cache", diskSize.toLong()
            )
        )

        //Glide默认的图片质量是RGB_565,RGB_565是16位图,即每个像素占2byte
        //设置解码格式ARGB_8888,ARGB_8888是指32位图,即每个像素占4byte
        builder.setDefaultRequestOptions(
            RequestOptions().format(DecodeFormat.PREFER_ARGB_8888).disallowHardwareConfig()
        )
    }

    //禁用清单解析，以避免重复添加类似的模块
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}