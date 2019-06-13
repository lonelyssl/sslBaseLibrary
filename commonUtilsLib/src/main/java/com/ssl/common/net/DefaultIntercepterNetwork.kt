package com.ssl.common.net
import com.ssl.common.utils.LogUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by long on 2017/12/4.
 */
class DefaultIntercepterNetwork : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response? {
        var response = chain?.proceed(chain?.request());
        LogUtil.d("netLog", "DefaultIntercepterNetwork.response=" + response)
        return response;
    }
}