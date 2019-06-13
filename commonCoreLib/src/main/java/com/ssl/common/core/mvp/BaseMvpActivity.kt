package com.ssl.common.core.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseMvpActivity<V : IView, P : IAbstractPresenter<V>> : AppCompatActivity(), IView {

    protected var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = getCustomPresenter();
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }


    /**
     * 子类实现
     */
    abstract fun getCustomPresenter(): P?
}