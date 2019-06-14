package com.ssl.module.base.net

import app.sslong.libs.net.NetFactory
import com.ssl.common.net.NetBackListener
import com.ssl.module.base.Constants
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 网络工具类
 */
class NetworkUtils private constructor() {

    val service: INetService

    init {
        service = NetFactory.initNetFactory(Constants.baseUrl, INetService::class.java, null, null)
    }

    companion object {
        val instance = NetworkUtils()
    }

    /**
     * 发送封装处理
     */
    fun <T> send(obs: Observable<T>, listener: NetBackListener<T>?) {
        obs.subscribeOn(Schedulers.io())//切换网络请求在IO线程
                .doOnSubscribe { t ->
                    listener?.onPreNet(t)
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<T> {
                    override fun onSubscribe(d: Disposable) {
                        listener?.onEndNetError(null)
                    }

                    override fun onNext(t: T) {
                        listener?.onEndNetSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener?.onEndNetError(e)
                    }

                    override fun onComplete() {

                    }
                })


    }

}
