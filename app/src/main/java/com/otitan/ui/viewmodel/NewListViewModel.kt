package com.otitan.ui.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import com.otitan.base.BaseViewModel
import com.otitan.data.Injection
import com.otitan.data.remote.RemoteDataSource
import com.otitan.model.ZLkModel
import com.otitan.ui.mview.INewList
import org.jetbrains.anko.toast

class NewListViewModel() : BaseViewModel() {

    val dataRepository = Injection.provideDataRepository()
    var mView: INewList? = null
    var type: Int = 1
    val isFinishRefreshing = ObservableBoolean(false)
    val isFinishLoading = ObservableBoolean(false)
    var data = ArrayList<ZLkModel>()



    constructor(context: Context?, mView: INewList) : this() {
        this.mContext = context
        this.mView = mView
    }

    override fun onCreate() {
        super.onCreate()
    }

}