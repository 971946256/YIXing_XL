package com.otitan.model

data class PMValueModel(val key: String, val content: List<Content>) {
    /**
     * PM2.5数据 [datatime]时间 [val]值 [Company]单位
     */
    data class Content(val datatime: String, val `val`: String, val Company: String)
}