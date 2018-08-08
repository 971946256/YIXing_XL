package com.titan.data

import com.titan.domain.Command
import com.titan.domain.ForecastDataMapper
import com.titan.domain.model.AllForecastModel
import com.titan.domain.model.ForecastList
import com.titan.domain.model.MainItemModel

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