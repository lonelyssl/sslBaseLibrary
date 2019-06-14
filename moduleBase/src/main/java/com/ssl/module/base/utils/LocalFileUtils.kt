package com.ssl.module.base.utils

import android.content.Context
import java.io.File


class LocalFileUtils {
    companion object {

        /**
         * 获取sd/Android下的项目路径
         */
        fun getAppSdPath(mContext: Context): String {
            return mContext.getExternalFilesDir("").absolutePath
        }


        /**
         * 获取备份文件路径
         */
        fun getBackupPath(mContext: Context): String {
            return getAppSdPath(mContext) + File.separatorChar + "backups";
        }

    }
}