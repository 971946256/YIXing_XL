package com.otitan.ui.adapter

import android.content.Context
import com.otitan.model.MainItemModel
import com.otitan.model.MonitorModel
import com.titan.tianqidemo.R
import com.otitan.ui.mview.IMainItem
import com.otitan.ui.viewmodel.MainItemViewModel
import kotlin.properties.Delegates

class MainAdapter(val mView: IMainItem, var itemList: List<MainItemModel>, val context: Context) : com.otitan.base.BaseAdapter() {


    fun setList(itemList: List<MainItemModel>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    fun setData(itemList: List<MainItemModel>?) {
        itemList?.let {
            this.itemList = it
        }
        notifyDataSetChanged()
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_main
    }

    override fun getLayoutViewModel(position: Int): Any {
        val viewmodel = MainItemViewModel(mView, context)
        viewmodel.item.set(itemList[position])
        viewmodel.drawable.set(itemList[position].drawable)
        viewmodel.position = position
        return viewmodel
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}