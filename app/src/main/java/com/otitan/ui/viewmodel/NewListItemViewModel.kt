package com.otitan.ui.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.os.Bundle
import com.otitan.base.BaseViewModel
import com.otitan.model.ZLkModel
import com.otitan.ui.fragment.NewFragment

class NewListItemViewModel() : BaseViewModel() {

    val model = ObservableField<ZLkModel>()

    constructor(context: Context?) : this() {
        mContext = context
    }

    fun onClick() {
        val bundle = Bundle()
        bundle.putString("url", model.get()?.newsurl)
        startContainerActivity(NewFragment::class.java.canonicalName, bundle)
    }
}