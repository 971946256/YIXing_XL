package com.otitan.ui.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.util.Log
import com.otitan.model.MainItemModel
import com.otitan.ui.activity.CalculatorActivity
import com.otitan.ui.fragment.NewListFragment
import com.otitan.ui.mview.IMainItem

class MainItemViewModel(context: Context) : com.otitan.base.BaseViewModel(context) {
    private var mView: IMainItem? = null
    val item = ObservableField<MainItemModel>()
    val drawable = ObservableField<Int>()
    var position: Int? = 0

    constructor(mView: IMainItem, context: Context) : this(context) {
        this.mView = mView
    }

    fun click() {
        Log.e("tag", "position:$position")
        when {
            item.get()?.title == "花粉计算" -> {
                startActivity(CalculatorActivity::class.java)
            }
            item.get()?.title == "资料库" -> {
                startContainerActivity(NewListFragment::class.java.canonicalName)
            }
        }
    }
}