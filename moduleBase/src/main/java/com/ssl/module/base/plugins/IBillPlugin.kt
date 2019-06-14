package com.ssl.module.base.plugins

import android.app.Activity
import com.ssl.common.core.plugin.IPlugin

/**
 * 账单插件
 */
interface IBillPlugin : IPlugin {

    fun exportInfo(mActivity: Activity)
    fun restoreInfo(mActivity: Activity)
}
