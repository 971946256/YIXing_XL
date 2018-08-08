package com.titan.domain

import com.titan.data.ForecastRequest
import com.titan.domain.model.AllForecastModel
import com.titan.domain.model.ForecastList
import com.titan.domain.model.MainItemModel
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.titan.domain.model.Forecast as ModelForecast

class ForecastDataMapper {

    fun convertFromAllForecast(allForecastModel: AllForecastModel): List<MainItemModel> {
        val list = ArrayList<MainItemModel>()
        list.add(MainItemModel(0, allForecastModel.observe))
        list.add(MainItemModel(1, allForecastModel.hourfc))
        list.add(MainItemModel(2, allForecastModel.forecast))
        return list
    }

    fun convertFromDataModel(forecastResult: ForecastRequest.ForecastResult): ForecastList {
        return ForecastList(forecastResult.city.name, forecastResult.city.country
                , convertForecastListToDomain(forecastResult.list))
    }

    private fun convertForecastListToDomain(list: List<ForecastRequest.Forecast>): List<ModelForecast> {
        return list.map { convertForecastItemToDomain(it) }
    }

    private fun convertForecastItemToDomain(forecast: ForecastRequest.Forecast): ModelForecast {
        return ModelForecast(convertDate(forecast.dt), forecast.weather[0].description,
                forecast.temp.max.toInt(), forecast.temp.min.toInt(), generateIconUrl(forecast.weather[0].icon))
    }

    private fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return df.format(date * 1000)
    }

    private fun generateIconUrl(iconCode: String): String = "http://openweathermap.org/img/w/$iconCode.png"
}