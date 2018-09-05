package com.otitan.model

/**
 * 自动气象站（林内）
 * {"datatime":"2018/8/21 0:00:00","Five_m":"0","Ten_m":"22.65","Fifteen_m":"23.09",
 * "Twenty_m":"23.27","Twenty_Five_m":"0","Thirty_m":"23.3","Company":"℃"}
 */
data class ZDQXZLNDataModel(val eigenvalues: String, val datatime: String, val Five_m: String, val Ten_m: String,
                            val Fifteen_m: String, val Twenty_m: String, val Twenty_Five_m: String,
                            val Thirty_m: String, val Company: String)
//class ZDQXZLNDataModel() {
//    var datatime: String = ""
//    var Five_m: String = ""
//    var Ten_m: String = ""
//    var Fifteen_m: String = ""
//    var Twenty_m: String = ""
//    var Twenty_Five_m: String = ""
//    var Thirty_m: String = ""
//    var Company: String = ""
//}