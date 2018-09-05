package com.otitan.data.remote


/**
 * Created by hanyw on 2018/8/7
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

    /**
     * 获取自动气象站林内数据
     */
    fun getZDQXZLNData(site: String, type: String, stime: String, etime: String, callback: mCallback)

    /**
     * 获取自动气象站林外数据
     */
    fun getZDQXZLWData(site: String, type: String, stime: String, etime: String, callback: mCallback)

    /**
     * 空气质量数据 PM2.5
     */
    fun getKQZLData(site: String, stime: String, etime: String, callback: mCallback)

    /**
     * 负氧离子数据
     */
    fun getFYLZData(site: String, stime: String, etime: String, callback: mCallback)

    /**
     * 雨滴谱仪
     */
    fun getYDPYData(site: String, type: String, stime: String, etime: String, callback: mCallback)

    /**
     * 土壤水势
     */
    fun getTRSSData(site: String, type: String, stime: String, etime: String, callback: mCallback)

    /**
     * 树干茎流数据
     */
    fun getStemFlowData(site: String, stime: String, etime: String, callback: mCallback)

    /**
     * 获取所有最新数据
     */
    fun getAllNewestData(callback: RemoteDataSource.mCallback)

    /**
     * 获取天气信息
     */
    fun getWeather(citykey: String,callback: RemoteDataSource.mCallback)
}
