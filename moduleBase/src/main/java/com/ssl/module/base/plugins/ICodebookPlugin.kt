package com.ssl.module.base.plugins

import android.app.Activity
import com.ssl.common.core.plugin.IPlugin

/**
 * 密码本插件
 */
interface ICodebookPlugin : IPlugin {

    fun exportInfo(mActivity: Activity)
    fun restoreInfo(mActivity: Activity)
}
