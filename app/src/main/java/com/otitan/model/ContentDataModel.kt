package com.otitan.model

/**
 * 自动气象站（林外）
 * 土壤水势数据
 *
 * {"key":"0","content":[{"datatime":"2018/8/22 0:00:00","val":"14.4","Company":"℃"}]}
 */
data class ContentDataModel(val datatime: String, val `val`: String, val Company: String)