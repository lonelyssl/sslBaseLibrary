package com.ssl.module.base.ui

import com.ssl.common.core.mvp.IAbstractPresenter
import com.ssl.common.core.mvp.IView

/**
 * 若简单的界面，不适用MVP模式，继承这个基类
 */
open abstract class BaseEmptyActivity : BaseActivity<IView, IAbstractPresenter<IView>>() {

    override fun getCustomPresenter(): IAbstractPresenter<IView>? {
        return null
    }


}
