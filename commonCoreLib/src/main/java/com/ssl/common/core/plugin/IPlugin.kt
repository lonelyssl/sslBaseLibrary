package com.ssl.common.core.plugin

import android.app.Activity
import android.app.Application


/***
 * 插件父类
 */
public interface IPlugin {


    /**
     * 初始化插件
     */
    fun initPlugin(application: Application)

    /**
     * 启动该模块
     */
    fun startApp(mActivity: Activity);


}