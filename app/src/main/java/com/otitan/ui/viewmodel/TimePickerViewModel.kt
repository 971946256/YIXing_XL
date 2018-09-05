package com.otitan.ui.viewmodel

import com.otitan.base.BaseViewModel
import com.otitan.ui.mview.ITimePicker
import kotlin.properties.Delegates

class TimePickerViewModel() : com.otitan.base.BaseViewModel() {

    private var mView: ITimePicker by Delegates.notNull()

    constructor(mView: ITimePicker) : this() {
        this.mView = mView
    }

    fun sure() {
        mView.sure()
    }
}