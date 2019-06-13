package com.ssl.common.core.plugin

import android.app.Application
import java.util.*

/**
 * 插件管理类
 */
class PluginManager private constructor() {

    /**
     * 插件映射集合
     */
    private val plugins = HashMap<Class<out IPlugin>, IPlugin>()
    private var mApplication: Application? = null


    /**
     *
     * @param boClass boClass 各模块接口的Class类
     * @param impl  对应的实现类
     * @param <T>
     * @param <K>
     * @return
    </K></T> */
    fun <T : IPlugin, K : T> registerPlugin(boClass: Class<T>, impl: K): PluginManager {
        plugins[boClass] = impl
        return this
    }

    /**
     * 必须调用该方法；完成插件的初始化
     * @param app
     * @return
     */
    fun build(app: Application) {
        this.mApplication = app
        for (plugin in plugins.values) {
            plugin.initPlugin(mApplication!!)
        }
    }

    /**
     * 根据底层接口获取对应的插件实现类
     * @param boClass
     * @param <T>
     * @return
    </T> */
    fun <T : IPlugin> getIPlugin(boClass: Class<T>): T? {
        val tem = plugins[boClass]
        return if (tem != null) tem as T? else null
    }

    companion object {
        val instace = PluginManager()
    }
}
