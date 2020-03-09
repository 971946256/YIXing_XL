package com.otitan.ui.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.otitan.base.BaseViewModel
import org.jetbrains.anko.toast

class CalculatorViewModel(context: Context) : BaseViewModel() {

    //花粉总价-授粉器使用量
    val totalPollen1 = ObservableField<String>("0.00")
    //花粉总价-蘸取授粉使用量
    val totalPollen2 = ObservableField<String>("0.00")

    //花粉质量-授粉器使用量
    val totalMass1 = ObservableField<String>("0.00")
    //花粉质量-蘸取授粉使用量
    val totalMass2 = ObservableField<String>("0.00")

    //花粉质量单位转换后的值-授粉器使用量
    val totalMassFilter1 = ObservableField<String>()
    //花粉质量单位转换后的值-蘸取授粉使用量
    val totalMassFilter2 = ObservableField<String>()

    //花粉种植面积
    val area = ObservableField<String>()
    //花粉单价 元/g
    val unitPrice = ObservableField<String>()
    //每亩花粉用量/g-授粉器使用量
    val unitPollen1 = ObservableField<String>("25")
    //花粉总价-蘸取授粉使用量
    val unitPollen2 = ObservableField<String>("10")
    //销售花粉总量
    val totalSales = ObservableField<String>()
    //销售花粉总量单位转换后的值
    val totalSalesFilter = ObservableField<String>()
    //提成比例 元/g
    val royalty = ObservableField<String>()
    //总提成
    val totalRoyalty = ObservableField<String>("0.00")
    //提成布局是否显示 false不显示 true显示
    val isRoyalty = ObservableBoolean(false)

    init {
        mContext = context
    }

    //计算
    fun compute() {
        if (!filter()) return
        totalPollen1.set(String.format("%.2f", transform(unitPollen1.get()!!) * transform(area.get()!!) * transform(unitPrice.get()!!)))
        totalPollen2.set(String.format("%.2f", transform(unitPollen2.get()!!) * transform(area.get()!!) * transform(unitPrice.get()!!)))
        totalMass1.set(String.format("%.2f", transform(unitPollen1.get()!!) * transform(area.get()!!)))
        totalMass2.set(String.format("%.2f", transform(unitPollen2.get()!!) * transform(area.get()!!)))

        totalMassFilter1.set((transform(totalMass1.get()) / 1000).toString() + "千克")
        totalMassFilter2.set((transform(totalMass2.get()) / 1000).toString() + "千克")
        if (isRoyalty.get()){
            totalRoyalty.set(String.format("%.2f", transform(totalSales.get()!!) * transform(royalty.get()!!)))
            totalSalesFilter.set((transform(totalSales.get()) / 1000).toString() + "千克")
        }
    }

    //提成计算布局显示与关闭
    fun royaltyOnOrOff() {
        isRoyalty.set(!isRoyalty.get())
    }

    //数值检查
    fun filter(): Boolean {
        when {
            area.get().isNullOrBlank() || transform(area.get()) == 0.0 -> {
                mContext?.toast("栽种面积不能为0")
                return false
            }
            unitPrice.get().isNullOrBlank() || transform(unitPrice.get()) == 0.0 -> {
                mContext?.toast("花粉单价不能为0")
                return false
            }
            (unitPollen1.get().isNullOrBlank() || transform(unitPollen1.get()) == 0.0) &&
                    (unitPollen2.get().isNullOrBlank() || transform(unitPollen2.get()) == 0.0) -> {
                mContext?.toast("花粉用量不能为0")
                return false
            }
            isRoyalty.get() && (totalSales.get().isNullOrBlank() || transform(totalSales.get()) == 0.0) -> {
                mContext?.toast("销售花粉总量不能为0")
                return false
            }
            isRoyalty.get() && (royalty.get().isNullOrBlank() || transform(royalty.get()) == 0.0) -> {
                mContext?.toast("销售提成比例不能为0")
                return false
            }
        }
        return true
    }

    //字符转数字
    fun transform(value: String?): Double {
        if (value.isNullOrBlank()) {
            return 0.0
        }
        return value.toDouble()
    }
}