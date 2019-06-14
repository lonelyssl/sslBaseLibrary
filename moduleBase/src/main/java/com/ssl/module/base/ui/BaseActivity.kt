package com.ssl.module.base.ui

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.ssl.common.core.mvp.BaseMvpActivity
import com.ssl.common.core.mvp.IAbstractPresenter
import com.ssl.common.core.mvp.IView
import com.ssl.module.base.R

/**
 * 基类Activity
 */
abstract class BaseActivity<V : IView, P : IAbstractPresenter<V>> : BaseMvpActivity<V, P>() {
    protected lateinit var titleBar: Toolbar;
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.bmodule_activity_base);
        //不显示标题
        supportActionBar?.hide()
        titleBar = findViewById(R.id.titleBar);
        titleBar.setNavigationIcon(R.mipmap.bmodule_ic_left)
        titleBar.setNavigationOnClickListener { finish() }
        var layout = getLayoutId()
        if (layout > 0) {
            val view = View.inflate(this, layout, null)
            val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout
                    .LayoutParams.MATCH_PARENT)
            findViewById<FrameLayout>(R.id.centerLayout).addView(view, params)
        }
        template()
    }

    fun setTitle(title: String?) {
        titleBar.visibility = View.VISIBLE
        titleBar.title = title;
    }

    /**
     * 模板方法
     */
    private fun template() {
        if (!isFinishing) {
            initIntentData();
        }
        if (!isFinishing) {
            initView();
        }
        if (!isFinishing) {
            initEvent();
        }
        if (!isFinishing) {
            initNetData();
        }
    }

    /***
     * 获取布局
     */
    abstract fun getLayoutId(): Int


    /**
     * 处理传递来的数据
     */
    open fun initIntentData() {
    }

    /**
     * 初始化视图
     */
    open fun initView() {
    }

    /**
     * 初始化点击事件
     */
    open fun initEvent() {
    }

    /**
     * 初始化网络数据；请求网络
     */
    open fun initNetData() {
    }


    /**
     * toast弹出
     */
    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}