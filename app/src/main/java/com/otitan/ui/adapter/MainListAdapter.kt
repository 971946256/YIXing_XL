package com.otitan.ui.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.otitan.model.AllForecastModel
import com.otitan.model.MainItemModel
import com.titan.tianqidemo.R
import com.otitan.util.ctx
import kotlinx.android.synthetic.main.item_cur_weather.view.*
import kotlinx.android.synthetic.main.item_future_weather.view.*
import kotlinx.android.synthetic.main.item_hour_weather.view.*

class MainListAdapter(val itemList: List<MainItemModel>, val context: Context) : RecyclerView.Adapter<MainListAdapter.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return 0//itemList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = when (viewType) {
            0 -> LayoutInflater.from(parent.ctx).inflate(R.layout.item_cur_weather, parent, false)
            1 -> LayoutInflater.from(parent.ctx).inflate(R.layout.item_hour_weather, parent, false)
            else -> LayoutInflater.from(parent.ctx).inflate(R.layout.item_future_weather, parent, false)
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(itemList[position], context)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(mainItemModel: MainItemModel, context: Context) {
//            when (mainItemModel.type) {
//                0 -> bindCurWeather(mainItemModel.obj as AllForecastModel.Observe)
//                1 -> bindHourForecast(mainItemModel.obj as List<AllForecastModel.Hourfc>, context)
//                2 -> bind5Forecast(mainItemModel.obj as List<AllForecastModel.Forecast>, context)
//            }
        }

        fun bindCurWeather(observe: AllForecastModel.Observe) {
            itemView.temperature.text = observe.temp.toString()+"℃"
            itemView.humidity.text = observe.shidu
        }

        fun bindHourForecast(hourfc: List<AllForecastModel.Hourfc>, context: Context) {
            val manager = LinearLayoutManager(context, OrientationHelper.HORIZONTAL, false)
            val hourAdapter = HourForecastAdapter(hourfc)
            itemView.forecastHourList.layoutManager = manager
            itemView.forecastHourList.adapter = hourAdapter
        }

        fun bind5Forecast(forecast: List<AllForecastModel.Forecast>, context: Context) {
            val linearManager = LinearLayoutManager(context, OrientationHelper.VERTICAL, false)
            val adapter = WeatherAdapter(forecast)
            itemView.forecastList.layoutManager = linearManager
            itemView.forecastList.adapter = adapter
        }
    }
}