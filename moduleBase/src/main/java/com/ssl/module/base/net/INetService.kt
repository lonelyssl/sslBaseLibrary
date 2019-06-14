package com.ssl.module.base.net


import com.ssl.module.base.net.models.UserModel
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * 网络底层接口
 * Created by long on 2017/12/4.
 */
interface INetService {

    @GET(value = "https://dsn.apizza.net/mock/46ccdd8ab39a997022ffe6e5af7662fc/getTest")
    fun getUserInfo(): Observable<NetResponse<UserModel>>


}