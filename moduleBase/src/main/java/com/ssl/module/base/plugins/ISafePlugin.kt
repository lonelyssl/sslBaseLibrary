package com.ssl.module.base.plugins

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.ssl.common.core.plugin.IPlugin


/**
 * 安全插件
 */
interface ISafePlugin : IPlugin {

    fun createGestureUnlock(mContext: Context);
    fun verifyGestureUnlock(mContext: Activity, reqCode: Int);
    fun modifyGestureUnlock(mContext: Context);
    fun isVerify(mContext: Context): Boolean

    fun verityLock(mActivity: Activity, call: VerityCall)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?);


    public interface VerityCall {
        fun success(isSuccess: Boolean)
    }
}
