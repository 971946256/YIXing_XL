package com.titan.tianqidemo.main

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.util.Log
import com.squareup.picasso.Picasso
import com.titan.data.DataResult
import com.titan.data.ForecastRequest
import com.titan.domain.Command
import com.titan.domain.ForecastDataMapper
import com.titan.domain.model.AllForecastModel
import com.titan.domain.model.ForecastList
import com.titan.tianqidemo.R
import com.titan.tianqidemo.adapter.ForecastListAdapter
import com.titan.tianqidemo.adapter.HourForecastAdapter
import com.titan.tianqidemo.adapter.MainListAdapter
import com.titan.tianqidemo.adapter.WeatherAdapter
import com.titan.utli.TitanItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_hour_weather.*
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        async {
//            val result1 = DataResult.Companion.RequestForecastCommand("hefei").execute()
//            val result = DataResult.Companion.RequestWeatherCommand("2502").execute()
//            val result2 = DataResult.Companion.RequestWeatherCommand("1256").execute()
//            val result3 = DataResult.Companion.RequestForecastCommand2("2502").execute()
            val result4 = DataResult.Companion.RequestAllForecastCommand().execute()
            val url = (result4[0].obj as AllForecastModel.Observe).day.bgPic
            val bmp = Picasso.with(this@MainActivity).load(url).get()
            val drawable =BitmapDrawable(bmp)
            uiThread {
                try {

                    mainLinear.setBackgroundDrawable(drawable)
                    val manager = LinearLayoutManager(it, OrientationHelper.VERTICAL, false)
                    val mainAdapter = MainListAdapter(result4,this@MainActivity)
                    forecastList.layoutManager = manager
                    forecastList.addItemDecoration(TitanItemDecoration(this@MainActivity,LinearLayoutManager.VERTICAL,15))
                    forecastList.adapter = mainAdapter
                }catch (e:Exception){
                    Log.e("tag","err:$e")
                }
            }
        }
    }
}