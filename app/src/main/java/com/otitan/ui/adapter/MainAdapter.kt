package com.otitan.ui.adapter

import android.content.Context
import com.otitan.base.BaseAdapter
import com.otitan.model.MonitorModel
import com.titan.tianqidemo.R
import com.otitan.ui.mview.IMainItem
import com.otitan.ui.viewmodel.MainItemViewModel
import kotlin.properties.Delegates

class MainAdapter(val mView: IMainItem, var itemList: List<MonitorModel>, val context: Context,
                  selectList: List<Boolean>?) : com.otitan.base.BaseAdapter() {

    private var selectList: List<Boolean> by Delegates.notNull()

    init {
        selectList?.let {
            this.selectList = it
        }
    }

    fun setList(itemList: List<MonitorModel>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    fun setData(selectList: List<Boolean>?) {
        selectList?.let {
            this.selectList = it
        }
        notifyDataSetChanged()
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return when (itemList[position].type) {
            0 -> R.layout.item_monitor
            1 -> R.layout.item_monitor_sub
            else -> R.layout.item_kqzlzs
        }
    }

    override fun getLayoutViewModel(position: Int): Any {
        val viewmodel = MainItemViewModel(mView, context)
        viewmodel.evn.set(itemList[position].evn)
        viewmodel.item.set(itemList[position].valueName)
        itemList[position].searchType.let {
            viewmodel.position = it
        }
//        viewmodel.isSelect.set(selectList[position])
        viewmodel.resultCode = itemList[position].resultCode
        viewmodel.siteName = itemList[position].siteName
        viewmodel.value.set(itemList[position].value)
        itemList[position].searchType.let {
            viewmodel.type = it
        }
        return viewmodel
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}