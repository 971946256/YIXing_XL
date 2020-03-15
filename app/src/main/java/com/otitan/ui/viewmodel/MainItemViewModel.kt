package com.otitan.ui.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.os.Environment
import android.util.Log
import com.otitan.model.CustomerInfo
import com.otitan.model.MainItemModel
import com.otitan.ui.activity.CalculatorActivity
import com.otitan.ui.fragment.AddInfoFragment
import com.otitan.ui.fragment.InfoListFragment
import com.otitan.ui.mview.IMainItem
import com.otitan.util.BoxStoreUtil
import com.otitan.util.FormatUtil
import com.otitan.util.MaterialDialogUtil
import com.otitan.util.Utli
import java.io.File
import java.util.*

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
            item.get()?.title == "新增数据" -> {
                startContainerActivity(AddInfoFragment::class.java.canonicalName)
            }
            item.get()?.title == "数据查看" -> {
                startContainerActivity(InfoListFragment::class.java.canonicalName)
            }
            item.get()?.title == "表格导出" -> {
                val path = "${Environment.getExternalStorageDirectory()}/gyyixing"
                val assestList = mContext?.assets?.list("") ?: arrayOf("")
                val putPath = path + "/" + "弈星用户数据-" + FormatUtil.dateFormat() + ".xls"
                copyFile(putPath, assestList)
                val list = BoxStoreUtil.getCustomerInfoAll()
                Collections.sort(list, object : Comparator<CustomerInfo> {
                    override fun compare(p0: CustomerInfo?, p1: CustomerInfo?): Int {
                        return p1!!.time.compareTo(p0!!.time)
                    }
                })
                list.forEach {
                    Utli.writeExcelYX(File(putPath), it)
                }
                mContext?.let {
                    MaterialDialogUtil.showPromptDialog(it, "已导出到：$putPath").show()
                }
            }
            item.get()?.title == "资料库" -> {
                startContainerActivity(InfoListFragment::class.java.canonicalName)
            }
        }
    }

    fun copyFile(putPath: String, assestList: Array<String>) {
        if (File(putPath).exists()) return
        for (file in assestList) {
            if (file == "customer_info.xls") {
                Utli.copyFileFromAssets(mContext!!, file, putPath)
            }
        }
    }
}