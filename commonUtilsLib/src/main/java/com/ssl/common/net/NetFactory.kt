package app.sslong.libs.net

import com.ssl.common.net.DefaultIntercepterApplication
import com.ssl.common.net.DefaultIntercepterNetwork
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

/**
 * Created by long on 2017/12/4.
 * 网络框架
 */
public class NetFactory {
    private constructor() {}

    companion object {
        private val DEF_OUTTIME = 30L; //超时时间

        /***
         * 初始化网络工具类
         */
        fun <T> initNetFactory(baseUrl: String, serviceClass: Class<T>,certificates: Map<String, InputStream>? = null, newInterceptor: Array<Interceptor>? = null, appInterceptor: DefaultIntercepterApplication = DefaultIntercepterApplication() ): T {
            return initNet(baseUrl, serviceClass, appInterceptor, newInterceptor, certificates)
        }


        /**
         *初始化信息
         */
        private fun <T> initNet(baseUrl: String, mClass: Class<T>, appInterceptor: Interceptor = DefaultIntercepterApplication(), newInterceptor: Array<Interceptor>?, certificates: Map<String, InputStream>?): T {

            var builder = OkHttpClient.Builder();
            builder.readTimeout(DEF_OUTTIME, TimeUnit.SECONDS)
                    .addInterceptor(appInterceptor)
                    .addNetworkInterceptor(DefaultIntercepterNetwork());

            if (newInterceptor != null) {
                for (tem in newInterceptor) {
                    builder.addNetworkInterceptor(tem);
                }
            }
            initHttpsConfig(builder, certificates)
            var reBuilder = Retrofit.Builder();
            reBuilder
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl);
            return reBuilder.callFactory(builder.build()).build().create(mClass);
        }

        /***
         * https证书
         */
        private fun initHttpsConfig(builder: OkHttpClient.Builder, certificates: Map<String, InputStream>?) {
            try {
                if (builder != null && certificates != null) {
                    val KEY_STORE_TYPE_P12 = "PKCS12"//证书类型
                    val certificateFactory = CertificateFactory.getInstance("X.509")
                    val keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12)
                    keyStore.load(null)
                    for ((key, value) in certificates) {
                        keyStore.setCertificateEntry(key, certificateFactory.generateCertificate(value));
                    }


                    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                    trustManagerFactory.init(keyStore)

                    val sslContext = SSLContext.getInstance("TLS")
                    sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())
                    builder.sslSocketFactory(sslContext.socketFactory)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}


//        /*get请求*/
//        fun <T : Any> request(url: String, mClass: Class<T>?, backListener: NetBackListener<T>? = null) {
//            request<T>(url, mClass, true, null, null, backListener)
//        }
//
//        /*Post请求*/
//        fun <T : Any> requestPost(url: String, params: Map<String, String>? = HashMap(), mClass: Class<T>?, backListener: NetBackListener<T>? = null) {
//            request<T>(url, mClass, false, params, null, backListener)
//        }
//
//
//        /*****
//         *
//         */
//        fun <T : Any> request(url: String, mClass: Class<T>?, isGet: Boolean, params: Map<String, String>?, header: Map<String, String>?, backListener: NetBackListener<T>?) {
//            var obj: Observable<ResponseBody>?;
//            if (!isGet) { //post请求
//                if (params == null) {
//                    obj = netService.requestPost(url);
//                } else if (header == null) {
//                    obj = netService.requestPost(url, params);
//                } else {
//                    obj = netService.requestPost(url, params, header);
//                }
//            } else {
//                if (params == null) {//Get请求
//                    obj = netService.requestGet(url);
//                } else if (header == null) {
//                    obj = netService.requestGet(url, params);
//                } else {
//                    obj = netService.requestGet(url, params, header);
//                }
//            }
//            obj
//                    .subscribeOn(Schedulers.io())/*设置上面网络请求的线程*/
//                    .doOnSubscribe { t: Disposable? ->
//                        isMainThread("doOnSubscribe")
//                        backListener?.onPreNet(t)
//                        if (t != null && t.isDisposed) {
//                            backListener?.onEndNetError(null)
//                        }
//                    }//主线程执行
//                    .subscribeOn(AndroidSchedulers.mainThread())/*设置上面的方法在主线程执行*/
//                    .observeOn(Schedulers.io())/*设置下面的map操作在io线程*/
//                    .map { body: ResponseBody -> isMainThread("map"); return@map body?.string() }/*子线程执行*/
//                    .map { str ->
//                        if (!TextUtils.isEmpty(str)) {
//                            var errData: SystemErrData? = null;
//                            try {
//                                errData = Gson().fromJson(str, SystemErrData::class.java);
//                            } catch (e: Exception) {
//                            }
//                            if (errData != null && SystemErrData.SYSTEM_CODE_NETERROR == errData.code) {
//                                throw  NullPointerException("系统内部网络错误"); //进入onError方法
//                            }
//                        }
//                        return@map if (TextUtils.isEmpty(str) || mClass == null) str else Gson().fromJson(str, mClass)
//                    }
//                    .observeOn(AndroidSchedulers.mainThread())/*切换下面的subscribe在主线程执行*/
//                    .subscribe(object : Observer<Any> {
//                        override fun onNext(t: Any) {
//                            Log.e("myTag", "onNext=" + t)
//                            backListener?.onEndNetSuccess(t as T);
//                        }
//
//                        override fun onSubscribe(d: Disposable) {
//                            Log.e("myTag", "onSubscribe=")
//                            backListener?.onEndNetError(NullPointerException("onSubscribe：请求被取消"));
//                        }
//
//
//                        override fun onError(e: Throwable) {
//                            Log.e("myTag", "onError=")
//                            backListener?.onEndNetError(e);
//                            e?.printStackTrace()
//                        }
//
//                        override fun onComplete() {
//                            Log.e("myTag", "onComplete=")
//                        }
//                    })
//        }
//
//        fun isMainThread(string: String) {
//            Log.e("myTag", "$string isMainThread=" + (Looper.getMainLooper() == Looper.myLooper()))
//        }



