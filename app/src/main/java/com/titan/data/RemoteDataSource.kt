package com.titan.data


/**
 * Created by whs on 2017/6/7
 */

interface RemoteDataSource {

    interface mCallback {

        fun onFailure(info: String)

        fun onSuccess(result: Any)
    }

    /**
     * 获取天气信息
     */
    fun getWeather(app: String, weaid: String, appkey: String, sign: String, format: String, callback: mCallback)
}
