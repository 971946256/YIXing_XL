package com.otitan.ui.mview

import com.otitan.model.MonitorModel

interface IMain {
    /**
     * 观测站点选择弹窗
     */
    fun showSiteChooseDialgo()

    fun setList(itemList:List<MonitorModel>)

    fun setProgress(progress: Float,title:String)

    fun setImage(url: String)

    fun startRefresh()
}