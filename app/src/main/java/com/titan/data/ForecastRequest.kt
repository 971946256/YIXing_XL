package com.titan.data

import android.util.Log
import com.google.gson.Gson
import com.titan.domain.model.AllForecastModel
import kotlin.properties.Delegates

class ForecastRequest() {
    private var id: String by Delegates.notNull()

    constructor(id: String) : this() {
        this.id = id
    }

    companion object {
        private const val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private const val URL = "http://api.openweathermap.org/data/2.5/" +
                "forecast/daily?mode=json&units=metric&cnt=7"
        private const val COMPLETE_URL: String = "$URL&APPID=$APP_ID&q="

        private const val URL2 = "http://api.k780.com/?"
        private const val APP_TODAY = "app=weather.today"
        private const val APP_FUTURE = "app=weather.future"
        private const val WEAID = "&weaid="
        private const val APPKEY = "&appkey=35417"
        private const val SIGN = "&sign=d631ba107712cad79dde3bd39c936da5"
        private const val FORMAT = "&format=json"

        private const val URL3 = "http://zhwnlapi.etouch.cn/Ecalender/api/v2/weather?date=20170726&citykey=101220105"
    }


    fun getAllForecast(): AllForecastModel {
        val json = java.net.URL(URL3).readText()
        return Gson().fromJson(json, AllForecastModel::class.java)
    }

    /**
     * 获取当天温度、湿度
     */
    fun getWdSd(): WeatherResult {
        val url = URL2 + APP_TODAY + WEAID + id + APPKEY + SIGN + FORMAT
        val json = java.net.URL(url).readText()
        return Gson().fromJson(json, WeatherResult::class.java)
    }

    /**
     * 获取5-7天天气预报
     */
    fun getForecast(): WeatherResult2 {
        val url = URL2 + APP_FUTURE + WEAID + id + APPKEY + SIGN + FORMAT
        val json = java.net.URL(url).readText()
        return Gson().fromJson(json, WeatherResult2::class.java)
    }

    data class WeatherResult2(val success: String, val msgid: String?, val msg: String?, val result: List<SucResult>) {
        operator fun get(position: Int) = result[position]
        fun size() = result.size
    }

    data class WeatherResult(val success: String, val msgid: String?, val msg: String?, val result: SucResult)

    data class SucResult(val weaid: String, val days: String, val week: String, val cityno: String,
                         val citynm: String, val cityid: String, val temperature: String,
                         val temperature_curr: String, val humidity: String, val aqi: String,
                         val weather: String, val weather_icon: String, val weather_icon1: String,
                         val wind: String, val winp: String, val temp_high: String,
                         val temp_low: String, val humi_high: String, val humi_low: String,
                         val weatid: String, val weatid1: String, val windid: String,
                         val winpid: String, val weather_iconid: String)

    fun execute(): ForecastResult {
        val forecastJsonStr = java.net.URL(COMPLETE_URL + id).readText()
        return Gson().fromJson(forecastJsonStr, ForecastResult::class.java)
    }

    data class ForecastResult(val city: City, val list: List<Forecast>)

    data class City(val id: Long, val name: String, val coord: Coordinates, val country: String,
                    val population: Int)

    data class Coordinates(val lon: Float, val lat: Float)

    data class Forecast(val dt: Long, val temp: Temperature, val pressure: Float, val humidity: Int,
                        val weather: List<Weather>, val speed: Float, val deg: Int, val clouds: Int,
                        val rain: Float)

    data class Temperature(val day: Float, val min: Float, val max: Float, val night: Float,
                           val eve: Float, val morn: Float)

    data class Weather(val id: Long, val main: String, val description: String, val icon: String)
}




