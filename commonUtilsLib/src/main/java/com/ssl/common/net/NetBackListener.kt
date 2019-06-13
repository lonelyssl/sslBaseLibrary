package com.ssl.common.net
import io.reactivex.disposables.Disposable

/**
 * Created by long on 2017/12/6.
 * 请求回调
 */
abstract class NetBackListener<T> {
    /**
     * 网络开始前调用；可以进行网络检查与loading显示
     */
    open fun onPreNet(able: Disposable?){

    };

    abstract fun onEndNetSuccess(data: T?);

    abstract fun onEndNetError(e: Throwable?)

}