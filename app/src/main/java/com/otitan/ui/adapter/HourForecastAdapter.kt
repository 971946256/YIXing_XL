package com.otitan.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.otitan.model.AllForecastModel
import com.titan.tianqidemo.R
import com.otitan.util.ctx
import kotlinx.android.synthetic.main.item_hour_forecast.view.*
import java.text.DateFormat
import java.util.*

class HourForecastAdapter(val hourfc: List<AllForecastModel.Hourfc>) : RecyclerView.Adapter<HourForecastAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_hour_forecast, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hourfc.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(hourfc[position])
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(result: AllForecastModel.Hourfc) {
            with(result) {
                val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
                itemView.hourDate.text = time.substring(time.length - 4, time.length-2)+":00"
                itemView.hourTemperature.text = wthr.toString()+"℃"
                itemView.hourHumidity.text = shidu
            }
        }
    }
}