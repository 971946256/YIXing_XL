package com.titan.data

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable


/**
 * Created by whs on 2017/2/17
 * Retrofit 接口
 */

interface RetrofitService {

    @GET("")
    fun getWeather(@Query("app") app: String, @Query("weaid") weaid: String,
                   @Query("appkey") appkey: String, @Query("sign") sign: String,
                   @Query("format") format: String):Observable<String>
}
