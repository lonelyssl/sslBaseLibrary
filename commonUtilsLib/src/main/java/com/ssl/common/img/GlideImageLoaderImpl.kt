package com.ssl.common.img


import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

/**
 * Created by long
 * on 2017/12/10.
 */
class GlideImageLoaderImpl : IImageLoader {

    override fun loadImage(url: String, imageView: ImageView, listener: ImageLoaderListener?, config: ImageConfig?): Any {
        var tem = Glide.with(imageView.context)
                .asBitmap()//强制转换类型，转换为Bitmap
                .load(url)
//                .transition(BitmapTransitionOptions.withCrossFade()); //图片过渡动画；淡入
        var options = RequestOptions();
        options.skipMemoryCache(false); //是否跳过内存缓存；false不跳过
        options.diskCacheStrategy(DiskCacheStrategy.ALL); //磁盘缓存级别；ALL缓存原图与处理后的图；SOURCE缓存原图；NONE不缓存；RESULT缓存处理图
        if (config != null) {
            if (config.width > 0 && config.height > 0) {
                options.override(config.width, config.height);
            }
            if (config.emptyResId > 0)
                options.error(config.emptyResId).fallback(config.emptyResId);

            if (config.loadingResId > 0)
                options.placeholder(config.loadingResId)

            if (config.isCricle) {
                options.optionalCircleCrop(); //设置图片为圆形
            }
        }
        if (listener != null) {
            tem.listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                    listener.errorLoaded(imageView);
                    return false
                }

                override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    listener.successLoaded(imageView, resource)
                    return false
                }

            });
        }
        return tem.apply(options).into(imageView);
    }

}