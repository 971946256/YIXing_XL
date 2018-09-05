package com.otitan.model

/**
 * 监测数据类型实体类
 * [valueName]数据名称 [type]0一级菜单 1二级菜单 [searchType] 查询数据时的类型
 */
class MonitorModel() {
    var valueName: String = ""
    var type: Int = 0
    var searchType: String = ""
    var resultCode: Int = 0
    var siteName: String = ""
    var value: String = ""
    var evn: AllForecastModel.Evn? = null

    constructor(valueName: String, type: Int, searchType: String, resultCode: Int, siteName: String,
                value: String) : this() {
        this.valueName = valueName
        this.type = type
        this.searchType = searchType
        this.resultCode = resultCode
        this.siteName = siteName
        this.value = value
    }

}