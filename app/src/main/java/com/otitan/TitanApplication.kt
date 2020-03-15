package com.otitan

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Environment
import android.support.multidex.MultiDex
import android.support.v7.app.AppCompatActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.otitan.location.LocationService
import com.otitan.model.MyObjectBox
import com.otitan.util.Utli
import com.tencent.bugly.Bugly
import io.objectbox.BoxStore
import java.io.File
import kotlin.properties.Delegates

class TitanApplication : Application() {

    companion object {
        var boxStore: BoxStore by Delegates.notNull()

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

        MultiDex.install(this)
//        CrashReport.initCrashReport(getApplicationContext(), "注册时申请的APPID", false)
        //true表示打开debug模式，false表示关闭调试模式
        Bugly.init(applicationContext, "e7590f6962", false)
        locationService = LocationService(this.applicationContext)
        sharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE)

        boxStore = MyObjectBox.builder().androidContext(this.applicationContext).build()


        //图片加载初始化
        val imagePipelineConfig = ImagePipelineConfig.newBuilder(this.applicationContext)
                .setBitmapsConfig(Bitmap.Config.ARGB_4444)
                .setDownsampleEnabled(true)
                .build()
        Fresco.initialize(this.applicationContext, imagePipelineConfig)

        val path = "${Environment.getExternalStorageDirectory()}/gyyixing"
        val assestList = this.assets.list("") ?: arrayOf("")
        val putPath = path + "/" + "弈星用户数据-" + Utli.getTime() + ".xls"
        if (!File(path).exists()) {
            File(path).mkdirs()
        }
        if (File(putPath).exists()) return
        for (file in assestList) {
            if (file == "customer_info.xls") {
                Utli.copyFileFromAssets(this, file, putPath)
            }
        }
    }


}