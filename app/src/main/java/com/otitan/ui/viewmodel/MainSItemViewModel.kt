package com.otitan.ui.viewmodel

import android.content.Context
import android.databinding.ObservableField
import com.otitan.base.BaseViewModel
import com.otitan.ui.mview.ISMain

class MainSItemViewModel(val context: Context) : BaseViewModel(context) {
    var mView: ISMain? = null
    val name = ObservableField<String>("")
    val phone = ObservableField<String>("")
    val address = ObservableField<String>("")
    val remarks = ObservableField<String>("")

    constructor(mView: ISMain, context: Context) : this(context) {
        this.mView = mView
    }
}