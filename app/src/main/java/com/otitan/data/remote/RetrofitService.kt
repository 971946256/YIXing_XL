package com.otitan.data.remote

import com.otitan.model.AllForecastModel
import com.otitan.model.ResultModel
import com.otitan.model.ZDQXZLNDataModel
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable


/**
 * Created by hanyw on 2018/8/17
 * Retrofit 接口
 */

interface RetrofitService {

    @GET("")
    fun getWeather(@Query("app") app: String, @Query("weaid") weaid: String,
                   @Query("appkey") appkey: String, @Query("sign") sign: String,
                   @Query("format") format: String): Observable<String>

    /**
     * 自动气象站（林内）
     * [site]气象站名称 长坡岭生态监测站 [type] 查询类型 1温度 2湿度 3风速
     * [stime] 开始时间 [etime] 结束时间 yyyy-MM-dd hh:mm:ss
     */
    @GET("GetZDQXZLNData")
    fun getZDQXZLNData(@Query("site") site: String, @Query("type") type: String,
                       @Query("stime") stime: String,
                       @Query("etime") etime: String): Observable<String>

    /**
     * 自动气象站（林外）
     * [site]气象站名称 长坡岭生态监测站 [type] 查询类型 1温度 2湿度 3风速
     * [stime] 开始时间 [etime] 结束时间 yyyy-MM-dd hh:mm:ss
     */
    @GET("GetZDQXZLWData")
    fun getZDQXZLWData(@Query("site") site: String, @Query("type") type: String,
                       @Query("stime") stime: String,
                       @Query("etime") etime: String): Observable<String>

    /**
     * 空气质量数据 PM2.5
     * [site]气象站名称 长坡岭生态监测站
     * [stime] 开始时间 [etime] 结束时间 yyyy-MM-dd hh:mm:ss
     */
    @GET("GetKQZLData")
    fun getKQZLData(@Query("site") site: String, @Query("stime") stime: String,
                    @Query("etime") etime: String): Observable<String>

    /**
     * 负氧离子数据
     * [site]气象站名称 长坡岭生态监测站
     * [stime] 开始时间 [etime] 结束时间 yyyy-MM-dd hh:mm:ss
     */
    @GET("GetFYLZData")
    fun getFYLZData(@Query("site") site: String, @Query("stime") stime: String,
                    @Query("etime") etime: String): Observable<String>

    /**
     * 雨滴谱仪
     * [site]气象站名称 长坡岭生态监测站 [type] 查询类型 1降雨量 2MOR能见度 3降雨强度
     * [stime] 开始时间 [etime] 结束时间 yyyy-MM-dd hh:mm:ss
     */
    @GET("GetYDPYData")
    fun getYDPYData(@Query("site") site: String, @Query("type") type: String,
                    @Query("stime") stime: String,
                    @Query("etime") etime: String): Observable<String>

    /**
     * 土壤水势
     * [site]气象站名称 长坡岭生态监测站 [type] 查询类型 1水势 2温度
     * [stime] 开始时间 [etime] 结束时间 yyyy-MM-dd hh:mm:ss
     */
    @GET("GetTRSSData")
    fun getTRSSData(@Query("site") site: String, @Query("type") type: String,
                    @Query("stime") stime: String,
                    @Query("etime") etime: String): Observable<String>

    /**
     * 树干茎流数据
     * [site]气象站名称 长坡岭生态监测站
     * [stime] 开始时间 [etime] 结束时间 yyyy-MM-dd hh:mm:ss
     */
    @GET("GetStemFlowData")
    fun getStemFlowData(@Query("site") site: String, @Query("stime") stime: String,
                        @Query("etime") etime: String): Observable<String>

    /**
     * 获取所有最新数据
     */
    @GET("GetAllNewestData")
    fun getAllNewestData(): Observable<String>

    /**
     * 获取天气信息
     */
    @GET("weather")
    fun getWeather(@Query("citykey") citykey: String): Observable<AllForecastModel>
}
