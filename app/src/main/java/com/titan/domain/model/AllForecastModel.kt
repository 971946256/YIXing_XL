package com.titan.domain.model

/**
 * 中华万年历获取的天气数据json bean类
 */
data class AllForecastModel(val indexes: List<Indexes>, val meta: Meta, val weatherUrls: WeatherUrls,
                            val forecast15: List<Forecast15>, val forecast: List<Forecast>,
                            val hourfc: List<Hourfc>, val xianhao: List<String>, val source: Source,
                            val evn: Evn, val observe: Observe) {

//    data class ForecastModel(val indexes: List<Indexes>, val meta: Meta, val weatherUrls: WeatherUrls,
//                             val forecast15: List<Forecast15>, val forecast: List<Forecast>,
//                             val hourfc: List<Hourfc>, val xianhao: List<String>, val source: Source,
//                             val evn: Evn, val observe: Observe)

    //指数
    data class Indexes(val ext: Ext, val valueV2: String, val name: String, val value: String, val desc: String)

    //指数子项
    data class Ext(val icon: String, val statsKey: String)

    //当前城市信息
    data class Meta(val circle_count: Int, val post_id: String, val citykey: String, val city: String,
                    val upper: String, val html_url: String, val wcity: List<String>, val up_time: String,
                    val post_count: Int, val status: Int, val desc: String)

    data class WeatherUrls(val w_life_index_more: String, val w_forecast_90: String, val w_gradual_hour: String)

    //15天天气预报
    data class Forecast15(val date: String, val sunrise: String, val high: Int, val forecastUrl: String,
                          val low: Int, val sunset: String, val night: Night2, val forecastAirUrl: String,
                          val day: Day2, val aqi: Int)

    //5天天气预报
    data class Forecast(val date: String, val sunrise: String, val high: Int, val low: Int,
                        val sunset: String, val night: Night2, val aqi: Int, val day: Day2)

    //白天天气
    data class Day2(val wthr: String, val bgPic: String, val smPic: String, val wp: String,
                    val type: Int, val wd: String, val notice: String)

    //夜晚天气
    data class Night2(val wthr: String, val bgPic: String, val smPic: String, val wp: String,
                      val type: Int, val wd: String, val notice: String)

    //24小时 逐小时天气情况
    data class Hourfc(val wthr: Int, val shidu: String, val hourfcUrl: String, val wp: String,
                      val type_desc: String, val time: String, val type: Int, val wd: String)

    //信息来源
    data class Source(val link: String, val icon: String, val title: String)

    //空气质量信息
    data class Evn(val no2: Int, val mp: String, val pm25: Int, val o3: Int, val so2: Int,
                   val aqi: Int, val pm10: Int, val suggest: String, val time: String, val co: Int,
                   val quality: String)

    //当前时间的天气信息
    data class Observe(val shidu: String, val wthr: String, val temp: Int, val night: Night1,
                       val up_time: String, val wp: String, val tigan: String, val type: Int,
                       val wd: String, val day: Day1)

    data class Day1(val bgPic: String, val smPic: String)

    data class Night1(val bgPic: String, val smPic: String)

}