package com.titan.data

import android.content.Context
import com.titan.tianqidemo.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by whs on 2017/2/17
 * Retrofit 初始化
 */
class RetrofitHelper private constructor(private val mContext: Context) {
    private var mRetrofit: Retrofit? = null

    /**
     * 常规接口
     * @return
     */
    val server: RetrofitService
        get() = mRetrofit!!.create(RetrofitService::class.java)

    init {
        resetApp()
    }

    private fun resetApp() {
        val okHttpClientBuilder = OkHttpClient.Builder()
        //5秒超时
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
        mRetrofit = Retrofit.Builder()
                .baseUrl(mContext.resources.getString(R.string.serverhost))
                //设置OKHttpClient
                .client(okHttpClientBuilder.build())
                //Xml转换器
//                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                //Rx
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

    }


    companion object {
        var retrofitHelper: RetrofitHelper? = null
        fun getInstance(context: Context): RetrofitHelper {
            if (retrofitHelper == null) {
                retrofitHelper = RetrofitHelper(context)
            }
            return retrofitHelper as RetrofitHelper
        }
    }
}
