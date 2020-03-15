package com.otitan.ui.adapter

import android.content.Context
import com.otitan.base.BaseAdapter
import com.otitan.model.CustomerInfo
import com.otitan.model.ZLkModel
import com.otitan.ui.fragment.InfoListFragment
import com.otitan.ui.viewmodel.InfoListItemViewModel
import com.otitan.ui.viewmodel.NewListItemViewModel
import com.titan.tianqidemo.R

/**
 * 用户信息列表适配器
 */
class InfoListAdapter() : BaseAdapter() {
    var context: Context? = null
    var fragment: InfoListFragment? = null
    var items: List<CustomerInfo>? = null

    constructor(context: Context?, items: List<CustomerInfo>?,fragment: InfoListFragment) : this() {
        this.context = context
        this.items = items
        this.fragment = fragment
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_info_list
    }

    override fun getLayoutViewModel(position: Int): Any {
        val viewmodel = InfoListItemViewModel(context,fragment)
        
        viewmodel.model.set(items!![position])
        return viewmodel
    }

    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size
    }
}