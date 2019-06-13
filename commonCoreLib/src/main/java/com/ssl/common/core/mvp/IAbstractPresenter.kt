package com.ssl.common.core.mvp

import android.app.Activity

abstract class IAbstractPresenter<V : IView>(protected var mActivity: Activity?, protected var iview: V?) : IPresenter {


    override fun onDestroy() {
        if (mActivity != null)
            mActivity = null

        if (iview != null)
            iview = null
    }
}
