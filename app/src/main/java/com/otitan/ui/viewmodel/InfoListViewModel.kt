package com.otitan.ui.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import com.otitan.base.BaseViewModel
import com.otitan.base.BindingAction
import com.otitan.base.BindingCommand
import com.otitan.data.Injection
import com.otitan.data.remote.RemoteDataSource
import com.otitan.model.ZLkModel
import com.otitan.ui.mview.IInfoList
import com.otitan.ui.mview.INewList
import org.jetbrains.anko.toast
import kotlin.properties.Delegates

/**
 * 用户信息列表
 */
class InfoListViewModel() : BaseViewModel() {

    var mView : IInfoList by Delegates.notNull()
    val isFinishRefreshing = ObservableBoolean(false)
    val isFinishLoading = ObservableBoolean(false)
    //是否有数据 true有 false没有
    val hasData = ObservableBoolean(true)

    val onRefresh = BindingCommand(object : BindingAction {
        override fun call() {
            mView.refresh()
            isFinishRefreshing.set(!isFinishRefreshing.get())
        }
    })

    val onLoadMore = BindingCommand(object : BindingAction {
        override fun call() {
            isFinishLoading.set(!isFinishLoading.get())
        }
    })

    constructor(context: Context?,mView : IInfoList) : this() {
        this.mContext = context
        this.mView = mView
    }

    override fun onCreate() {
        super.onCreate()
    }

}