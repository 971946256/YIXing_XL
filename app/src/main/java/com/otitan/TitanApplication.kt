package com.otitan

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.otitan.location.LocationService
import com.tencent.bugly.Bugly
import kotlin.properties.Delegates

class TitanApplication : Application() {

    companion object {
        var instances: TitanApplication by Delegates.notNull()
        //百度定位监听
        var locationService: com.otitan.location.LocationService by Delegates.notNull()

        private val activityList = ArrayList<AppCompatActivity>()

        var sharedPreferences: SharedPreferences by Delegates.notNull()

        fun addActivity(activity: AppCompatActivity) {
            activityList.add(activity)
        }

        fun removeActivity(activity: AppCompatActivity) {
            activityList.remove(activity)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instances = this
//        CrashReport.initCrashReport(getApplicationContext(), "注册时申请的APPID", false)
        //true表示打开debug模式，false表示关闭调试模式
        Bugly.init(applicationContext, "05a0b171b2", false)
        locationService = LocationService(this.applicationContext)
        sharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE)

        //图片加载初始化
        val imagePipelineConfig = ImagePipelineConfig.newBuilder(this.applicationContext)
                .setBitmapsConfig(Bitmap.Config.ARGB_4444)
                .setDownsampleEnabled(true)
                .build()
        Fresco.initialize(this.applicationContext, imagePipelineConfig)
    }


}