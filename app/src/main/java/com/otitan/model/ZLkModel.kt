package com.otitan.model

import java.io.Serializable

/**
 * 资料库
 */
class ZLkModel : Serializable {
    //"“国家政务服务投诉与建议”小程序上线",
    var title: String = ""
    //"http://www.zjly.gov.cn/picture/0/1810161705452067140.jpg",
    var imgurl: String = ""
    //"http://www.gov.cn/xinwen/2018-09/20/content_5323786.htm",
    var newsurl: String = ""
    //"2018-09-20"
    var date: String = ""
    //资料来源
    var source: String = ""
}