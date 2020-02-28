package com.otitan.ui.adapter

import android.content.Context
import com.otitan.base.BaseAdapter
import com.otitan.model.AddressModel
import com.otitan.ui.mview.ISMain
import com.otitan.ui.viewmodel.MainSItemViewModel
import com.titan.tianqidemo.R

class MainSAdapter(val mView: ISMain, val itemList: List<AddressModel>, val context: Context) : BaseAdapter() {

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_address
    }

    override fun getLayoutViewModel(position: Int): Any {
        val viewmodel = MainSItemViewModel(mView, context)
        viewmodel.name.set(itemList[position].name)
        viewmodel.phone.set(itemList[position].phone)
        viewmodel.address.set(itemList[position].address)
        viewmodel.remarks.set(itemList[position].remarks)
        return viewmodel
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}
