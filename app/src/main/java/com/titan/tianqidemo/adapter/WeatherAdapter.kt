package com.titan.tianqidemo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.titan.domain.model.AllForecastModel
import com.titan.tianqidemo.R
import com.titan.utli.ctx
import kotlinx.android.synthetic.main.item_forecast.view.*
import java.io.File

class WeatherAdapter(val forecast: List<AllForecastModel.Forecast>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_forecast, parent
                , false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return forecast.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(forecast[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindForecast(forecast: AllForecastModel.Forecast) {
            with(forecast) {
                //Picasso.with(itemView.ctx).load(weather_icon).into(itemView.icon)
                itemView.date.text = date
                itemView.description.text = day.wthr + File.separator + night.wthr
                itemView.maxTemperature.text = "$high℃"
                itemView.minTemperature.text = "$low℃"
            }
        }
    }
}