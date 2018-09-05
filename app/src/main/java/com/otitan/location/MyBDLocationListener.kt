package com.otitan.location

import android.graphics.Point
import android.util.Log
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.otitan.TitanApplication
import com.otitan.base.ValueCallBack
import com.otitan.model.CityInfoModel
import kotlin.properties.Delegates

class MyBDLocationListener : BDAbstractLocationListener() {

    companion object {
        fun getInstance() = Holder.instance
    }

    private object Holder {
        val instance = MyBDLocationListener()
    }

    private var callBack: com.otitan.base.ValueCallBack<CityInfoModel> by Delegates.notNull()
    private var mPoint: Point? = null
    private var mAltitude: Double? = null
    private val cityInfo = CityInfoModel()
    override fun onReceiveLocation(p0: BDLocation?) {
        if (p0 != null && p0.locType != BDLocation.TypeServerError) {
            val latitude = p0.latitude    //获取纬度信息
            val longitude = p0.longitude    //获取经度信息
            mAltitude = if (p0.altitude == 4.9E-324) {
                0.0
            } else {
                p0.altitude
            }
            cityInfo.city = p0.city
            cityInfo.district = p0.district
            callBack.onSuccess(cityInfo)
        }
    }

    //获取定位点
    fun getLocPoint() = mPoint

    //获取海拔高度
    fun getAltitude() = mAltitude

    //初始化百度定位
    fun initBDLoc(callBack: com.otitan.base.ValueCallBack<CityInfoModel>) {
        this.callBack = callBack
        com.otitan.TitanApplication.locationService.registerListener(MyBDLocationListener.getInstance())
        com.otitan.TitanApplication.locationService.setLocationOption(com.otitan.TitanApplication.locationService.defaultLocationClientOption)
        com.otitan.TitanApplication.locationService.start()
    }

    //关闭百度定位监听
    fun stop() {
        com.otitan.TitanApplication.locationService.let {
            it.unregisterListener(MyBDLocationListener.getInstance())
            it.stop()
        }
    }

    fun start() {
        com.otitan.TitanApplication.locationService.start()
    }
}