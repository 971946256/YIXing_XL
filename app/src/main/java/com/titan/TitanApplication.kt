package com.titan

import android.app.Application
import kotlin.properties.Delegates

class TitanApplication : Application() {

    companion object {
        var instances: TitanApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instances = this
    }
}