package com.otitan.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * 客户信息
 */
@Entity
class CustomerInfo() {
    constructor(name: String?, phone: String?, address: String?, area: String?,
                usage: String?, yields: String?, price19: String?, price: String?,
                tailMoney: Boolean, remark: String?) : this() {
        this.name = name
        this.phone = phone
        this.address = address
        this.area = area
        this.usage = usage
        this.yields = yields
        this.price19 = price19
        this.price = price
        this.tailMoney = tailMoney
        this.remark = remark
    }

    //数据库id
    @Id
    var id: Long = 0
    //添加时间
    var time: String = ""
    var name: String? = ""
    var phone: String? = ""
    var address: String? = ""
    var area: String? = ""
    var usage: String? = ""
    var yields: String? = ""
    var price19: String? = ""
    var price: String? = ""
    var tailMoney: Boolean = true
    var remark: String? = ""
    //操作员
    var operator: String? = ""
}
/*
//姓名
val name = ObservableField<String>()
//电话
val phone = ObservableField<String>()
//地址
val address = ObservableField<String>()
//面积
val area = ObservableField<String>()
//预计花粉总使用量 g
val usage = ObservableField<String>()
//基地产量 亩/斤
val yield = ObservableField<String>()
//年销售量 万斤
val salesVolume = ObservableField<String>()
//预期果价
val price = ObservableField<String>()
//19年是否收到尾款 true是 false否
val tailMoney = ObservableBoolean(true)
//备注
val remark = ObservableField<String>()*/
