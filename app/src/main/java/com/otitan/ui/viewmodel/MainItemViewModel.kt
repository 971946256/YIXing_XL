package com.otitan.ui.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.os.Bundle
import com.otitan.base.BaseViewModel
import com.otitan.model.AllForecastModel
import com.otitan.ui.fragment.ChartFragment
import com.otitan.ui.mview.IMainItem
import org.jetbrains.anko.toast

class MainItemViewModel(context: Context) : com.otitan.base.BaseViewModel(context) {
    private var mView: IMainItem? = null
    val item = ObservableField<String>("")
    val value = ObservableField<String>("")
    var resultCode: Int? = 0
    var position = "1"
    //是否被选中 false否 true是
    val isSelect = ObservableBoolean(false)
    val evn = ObservableField<AllForecastModel.Evn>()
    var siteName = ""
    var type = ""

    constructor(mView: IMainItem, context: Context) : this(context) {
        this.mView = mView
    }

    fun click() {
//        mView?.setType(type)
//        mView?.setSelect(position)
        val bundle = Bundle()
        bundle.putInt("resultCode", resultCode!!)
        bundle.putString("itemName", item.get())
        bundle.putString("type", position)
        bundle.putString("siteName", siteName)
        startContainerActivity(ChartFragment::class.java.canonicalName, bundle)
    }
}