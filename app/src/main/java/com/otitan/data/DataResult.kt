package com.otitan.data

import com.otitan.data.remote.ForecastRequest
import com.otitan.domain.Command
import com.otitan.domain.ForecastDataMapper
import com.otitan.model.ForecastList
import com.otitan.model.MainItemModel

class DataResult{
    companion object {
        class RequestForecastCommand(val id: String) : Command<ForecastList> {
            override fun execute(): ForecastList {
                val forecastRequest = ForecastRequest(id)
                return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
            }
        }

        class RequestWeatherCommand(val id: String) : Command<ForecastRequest.WeatherResult> {
            override fun execute(): ForecastRequest.WeatherResult {
                val request = ForecastRequest(id)
                return request.getWdSd()
            }
        }

        class RequestForecastCommand2(val id:String):Command<ForecastRequest.WeatherResult2>{
            override fun execute(): ForecastRequest.WeatherResult2 {
                val request = ForecastRequest(id)
                return request.getForecast()
            }
        }

        class RequestAllForecastCommand():Command<List<MainItemModel>>{
            override fun execute(): List<MainItemModel> {
                val request = ForecastRequest()
                return ForecastDataMapper().convertFromAllForecast(request.getAllForecast())
            }
        }
    }
}