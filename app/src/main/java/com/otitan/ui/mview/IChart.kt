package com.otitan.ui.mview

import com.github.mikephil.charting.data.Entry
import com.otitan.model.SGYLDataModel
import com.otitan.model.ZDQXZLNDataModel

interface IChart {
    /**
     * 显示时间选择弹窗
     * [type] 0开始时间 1结束时间
     */
    fun showTimePicker(type: Int)

    /**
     * 观测站点选择弹窗
     */
    fun showSiteChooseDialgo()

    fun setLineData(values: List<List<Entry>>?)

    fun setZDQXZTableData(tableData: List<ZDQXZLNDataModel>)

    fun setSGYLTableData(tableData: List<SGYLDataModel>)
}