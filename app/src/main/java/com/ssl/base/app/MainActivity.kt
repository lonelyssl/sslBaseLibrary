package com.ssl.base.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ssl.common.utils.LogUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LogUtil.e("测试")
    }
}
