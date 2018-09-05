package com.otitan.model

import java.io.Serializable

/**
 * 网络获取数据model
 */
//data class ResultModel<T>(val key: String, val content: T)
class ResultModel<T> {
    var key: String? = null
    var content: T? = null
}