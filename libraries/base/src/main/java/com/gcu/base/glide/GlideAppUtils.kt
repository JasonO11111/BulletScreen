package com.gcu.base.glide

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.gcu.base.R
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.ColorFilterTransformation
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import jp.wasabeef.glide.transformations.GrayscaleTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 *
 * @author: zoulongsheng
 * @date: 2023/5/5
 */
object GlideAppUtils {
    /**
     * 加载图片
     */
    fun loadImage(context: Context, url: String, imageView: ImageView) {
        val options = RequestOptions().placeholder(R.drawable.shape_image_placeholder)
            .error(R.drawable.shape_image_placeholder)
        GlideApp.with(context).load(url).apply(options).into(imageView)
    }

    /**
     * 加载图片--中间裁剪
     */
    fun loadCenterCropImage(context: Context, url: String, imageView: ImageView) {
        GlideApp.with(context).load(url).centerCrop().into(imageView)
    }

    /**
     * 加载图片--圆形
     */
    fun loadCircleCropImage(context: Context, url: String, imageView: ImageView) {
        GlideApp.with(context).load(url).circleCrop().into(imageView)
    }

    /**
     * 加载图片--圆形带边框（可自定义边框宽度和颜色）
     */
    fun loadCropCircleWithBorderImage(
        context: Context, url: String, imageView: ImageView, borderSize: Int, borderColor: Int
    ) {
        val options = RequestOptions().centerCrop().transform(
            CropCircleWithBorderTransformation(
                borderSize, borderColor
            )
        )
        GlideApp.with(context).load(url).apply(options).into(imageView)
    }

    /**
     * 加载图片--圆角--四边全圆角 （自定义弧度半径）
     * 注：centerCrop() 与 RoundedCornersTransformatin存在冲突，如果同时使用的话这里采用 MultiTransformation
     */
    fun loadRoundImage(
        context: Context, url: String, imageView: ImageView, radius: Int
    ) {

        val options = RequestOptions().transform(
            MultiTransformation(
                CenterCrop(), RoundedCorners(radius)
            )
        )
        GlideApp.with(context).load(url).apply(options).into(imageView)
    }

    /**
     * 加载图片--圆角--指定圆角(上下左右可任意指定) （自定义弧度半径和边距）
     */
    fun loadRoundImage(
        context: Context,
        url: String,
        imageView: ImageView,
        radius: Int,
        margin: Int,
        cornerType: RoundedCornersTransformation.CornerType
    ) {

        val options =
            RequestOptions().transform(RoundedCornersTransformation(radius, margin, cornerType))
        GlideApp.with(context).load(url).apply(options).into(imageView)
    }

    /**
     * 加载图片--灰度-黑白
     */
    fun loadGrayscaleImage(
        context: Context, url: String, imageView: ImageView
    ) {

        val options = RequestOptions().centerCrop().transform(GrayscaleTransformation())
        GlideApp.with(context).load(url).apply(options).into(imageView)
    }

    /**
     * 加载图片--模糊效果（毛玻璃效果）
     */
    fun loadBlurImage(
        context: Context, url: String, imageView: ImageView
    ) {
        val options = RequestOptions().centerCrop().transform(BlurTransformation())
        GlideApp.with(context).load(url).apply(options).into(imageView)
    }

    /**
     * 加载图片--颜色滤镜
     */
    fun loadColorFilterImage(
        context: Context, url: String, imageView: ImageView, color: Int
    ) {
        val options = RequestOptions().centerCrop().transform(ColorFilterTransformation(color))
        GlideApp.with(context).load(url).apply(options).into(imageView)
    }
}