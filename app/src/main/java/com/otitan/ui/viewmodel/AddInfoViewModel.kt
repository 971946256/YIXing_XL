package com.otitan.ui.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.otitan.base.BaseViewModel
import com.otitan.model.CustomerInfo
import com.otitan.ui.mview.IAddInfo
import com.otitan.util.FormatUtil
import org.jetbrains.anko.toast
import kotlin.properties.Delegates

class AddInfoViewModel() : BaseViewModel() {


    var mView: IAddInfo by Delegates.notNull()
    //姓名
    val name = ObservableField<String>("")
    //电话
    val phone = ObservableField<String>("")
    //地址
    val address = ObservableField<String>("")
    //面积
    val area = ObservableField<String>("")
    //预计花粉总使用量 g
    val usage = ObservableField<String>("")
    //基地产量 亩/斤
    val yields = ObservableField<String>("")
    //年销售量 万斤 已隐藏
    val salesVolume = ObservableField<String>("")
    //19年果价
    val price19 = ObservableField<String>("")
    //预期果价
    val price = ObservableField<String>("")
    //19年是否收到尾款 true是 false否
    val tailMoney = ObservableBoolean(true)
    //备注
    val remark = ObservableField<String>("")

    constructor(context: Context?, iView: IAddInfo) : this() {
        mContext = context
        this.mView = iView
    }


    //确定
    fun sure() {
        if (!filter()) return
        val customerInfo = CustomerInfo(name.get(), phone.get(), address.get(), area.get(), usage.get(),
                yields.get(), price19.get(), price.get(), tailMoney.get(), remark.get())

        customerInfo.time = FormatUtil.dateFormat()
        mView.addInfo(customerInfo)

    }

    fun initData() {
        name.set("")
        phone.set("")
        address.set("")
        area.set("")
        usage.set("")
        yields.set("")
        salesVolume.set("")
        price19.set("")
        price.set("")
        tailMoney.set(true)
        remark.set("")
    }

    //必填值检查
    fun filter(): Boolean {
        when {
            name.get().isNullOrBlank() -> {
                mContext?.toast("姓名不能为空")
                return false
            }
            phone.get().isNullOrBlank() || phone.get()?.length!! < 11 -> {
                mContext?.toast("电话信息错误")
                return false
            }
            address.get().isNullOrBlank() -> {
                mContext?.toast("地址不能为空")
                return false
            }
            area.get().isNullOrBlank() -> {
                mContext?.toast("基地面积不能为空")
                return false
            }
        }
        return true
    }
}