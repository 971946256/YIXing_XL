package com.otitan.model

/**
 * 树干液流
 * {"datatime":"2018/8/1 0:00:00","TDP_FLOW1":"0.257","TDP_FLOW2":"0.112","TDP_FLOW3":"0.061",
 * "TDP_FLOW4":"0.137","TDP_FLOW5":"0.298","TDP_FLOW6":"0","TDP_FLOW7":"0.775","TDP_FLOW8":"0.431",
 * "TDP_FLOW9":"1.902","TDP_FLOW10":"1.253","TDP_FLOW11":"0.016","TDP_FLOW12":"0","Company":"g/hr"}
 */
data class SGYLDataModel(val eigenvalues:String,val datatime: String, val TDP_FLOW1: String, val TDP_FLOW2: String
                         , val TDP_FLOW3: String, val TDP_FLOW4: String, val TDP_FLOW5: String
                         , val TDP_FLOW6: String, val TDP_FLOW7: String, val TDP_FLOW8: String
                         , val TDP_FLOW9: String, val TDP_FLOW10: String, val TDP_FLOW11: String
                         , val TDP_FLOW12: String, val Company: String)