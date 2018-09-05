package com.otitan.ui.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.otitan.base.BaseViewModel
import com.otitan.data.DataRepository
import com.otitan.data.Injection
import com.otitan.data.remote.RemoteDataSource
import com.otitan.model.AllForecastModel
import com.otitan.model.AllNewDataModel
import com.otitan.model.MonitorModel
import com.otitan.ui.mview.IMain
import com.otitan.util.FileUtil
import com.titan.tianqidemo.R
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class MainViewModel() : BaseViewModel() {

    private var mView: IMain by Delegates.notNull()
    var dataRepository: DataRepository = Injection.provideDataRepository()
    //林内数据
    val site = ObservableField<String>("长坡岭生态监测站")
    val evn = ObservableField<AllForecastModel.Evn>()
    val isFinishRefreshing = ObservableBoolean(false)
    var isLocation = false
    //选中状态集合
    val selectList = ArrayList<Boolean>()
    //梯度自动气象站查询类型 1温度 2湿度 3风速
    var type = "1"
    var allNewData: AllNewDataModel? = null
    //城市
    var city = ""
    //区县
    var district = ""
    //城市天气代码
    var citykey = ""

    val array = arrayOf(R.array.monitor_sub_zdqxzln, R.array.monitor_sub_zdqxzlw, R.array.monitor_sub_slst
            , R.array.monitor_sub_ydpy, R.array.monitor_sub_trss, R.array.monitor_sub_sgyl)

    constructor(context: Context, mView: IMain) : this() {
        this.mContext = context
        this.mView = mView
    }

    override fun onCreate() {
        super.onCreate()
        mView.startRefresh()
//        requestNetWork()
    }

    fun requestNetWork() {
//        showDialog("加载中...")
        dataRepository.getAllNewestData(object : RemoteDataSource.mCallback {
            override fun onFailure(info: String) {
                dismissDialog()
                isFinishRefreshing.set(!isFinishRefreshing.get())
                mContext?.toast(info)
            }

            override fun onSuccess(result: Any) {
                dismissDialog()
                allNewData = result as AllNewDataModel
                isFinishRefreshing.set(!isFinishRefreshing.get())
                mView.setList(initMonitorList())
            }
        })
    }

    fun getWeather() {
        mContext?.let {
            if (city != "") {
                citykey = FileUtil.getCityId(city.substring(0, 2), it)
            } else {
                return
            }
        }
        dataRepository.getWeather(citykey, object : RemoteDataSource.mCallback {
            override fun onFailure(info: String) {
                dismissDialog()
                isLocation = false
                mContext?.toast(info)
            }

            override fun onSuccess(result: Any) {
                dismissDialog()
                isLocation = true
                evn.set((result as AllForecastModel).evn)
                mView.setList(initMonitorList())
                mView.setProgress(evn.get()!!.aqi.toFloat(), getAQI(evn.get()!!.aqi))
                val url = if (Date().hours < 18) {
                    result.observe.day.bgPic
                } else {
                    result.observe.night.bgPic
                }
                mView.setImage(url)
            }
        })
    }

    fun initMonitorList(): List<MonitorModel> {
        val resultList = ArrayList<MonitorModel>()
        val model = MonitorModel()
        model.type = 2
        model.evn = evn.get()
        resultList.add(model)
        val list = mContext?.resources?.getStringArray(R.array.monitor_item)?.toList()
        list?.forEachWithIndex { i, v ->
            val model1 = MonitorModel()
            model1.valueName = v
            model1.type = 0
            model1.searchType = "0"
            model1.resultCode = i
            resultList.add(model1)
            selectList.add(false)
            val t = mContext?.resources?.getStringArray(array[i])?.toList()
            t?.forEachWithIndex { j, v1 ->
                resultList.add(MonitorModel(v1, 1, (j + 1).toString(), i, site.get()!!,
                        getAllNewData((j + 1).toString(), i)))
                if (!selectList.contains(true)) {
                    selectList.add(true)
                } else {
                    selectList.add(false)
                }
            }
        }
        return resultList
    }

    fun getAllNewData(searchType: String, resultCode: Int): String {
        var reslut = ""
        if (allNewData == null) {
            return reslut
        }
        when (resultCode) {
            0 -> {
                when (searchType) {
                    "1" -> {
                        reslut = converString(allNewData!!.zdqxz_ln_wd[0], resultCode)
                    }
                    "2" -> {
                        reslut = converString(allNewData!!.zdqxz_ln_sd[0], resultCode)
                    }
                    "3" -> {
                        reslut = converString(allNewData!!.zdqxz_ln_fs[0], resultCode)
                    }
                }
            }
            1 -> {
                when (searchType) {
                    "1" -> {
                        val data = allNewData!!.zdqxz_lw_wd[0]
                        reslut = "${data.lw_wd} ${data.Company}"
                    }
                    "2" -> {
                        val data = allNewData!!.zdqxz_lw_sd[0]
                        reslut = "${data.lw_sd} ${data.Company}"
                    }
                    "3" -> {
                        val data = allNewData!!.zdqxz_lw_fs[0]
                        reslut = "${data.lw_fs} ${data.Company}"
                    }
                }
            }
            2 -> {
                when (searchType) {
                    "1" -> {
                        val data = allNewData!!.kqzl[0]
                        reslut = "${data.PM25} ${data.Company}"
                    }
                    "2" -> {
                        val data = allNewData!!.fylz[0]
                        reslut = "${data.FYLZ} ${data.Company}"
                    }
                }
            }
            3 -> {
                when (searchType) {
                    "1" -> {
                        val data = allNewData!!.ydpy_jyl[0]
                        reslut = "${data.jyl} ${data.Company}"
                    }
                    "2" -> {
                        val data = allNewData!!.ydpy_njd[0]
                        reslut = "${data.njd} ${data.Company}"
                    }
                    "3" -> {
                        val data = allNewData!!.ydpy_jyqd[0]
                        reslut = "${data.jyqd} ${data.Company}"
                    }
                }
            }
            4 -> {
                when (searchType) {
                    "1" -> {
                        val data = allNewData!!.trss_ss[0]
                        reslut = "${data.trss_ss} ${data.Company}"
                    }
                    "2" -> {
                        val data = allNewData!!.trss_wd[0]
                        reslut = "${data.trss_wd} ${data.Company}"
                    }
                }
            }
            5 -> {
                reslut = converString(allNewData!!._ls_sgjl[0], resultCode)
            }
        }
        return reslut
    }

    fun converString(any: Any, resultCode: Int): String {
        val data: Any
        val array: Array<String>
        val company: String
        when (resultCode) {
            0 -> {
                data = any as AllNewDataModel.Zdqxzln
                company = data.Company
                array = arrayOf(data.Five_m, data.Ten_m,
                        data.Fifteen_m, data.Twenty_m,
                        data.Twenty_Five_m, data.Thirty_m)
            }
            else -> {
                data = any as AllNewDataModel.Sgjl
                company = data.Company
                array = arrayOf(data.TDP_FLOW1, data.TDP_FLOW2, data.TDP_FLOW3, data.TDP_FLOW4, data.TDP_FLOW5
                        , data.TDP_FLOW6, data.TDP_FLOW7, data.TDP_FLOW8, data.TDP_FLOW9, data.TDP_FLOW10
                        , data.TDP_FLOW11, data.TDP_FLOW12)
            }
        }

        val max = array.maxBy { it }
        val min = array.minBy { it }
        return "$max/$min $company"
    }

    fun getAQI(aqi: Int): String {
        return when (aqi) {
            in 0..50 -> "优"
            in 51..100 -> "良"
            in 101..150 -> "轻度"
            in 151..200 -> "中度"
            in 201..300 -> "重度"
            else -> "严重"
        }
    }

    /**
     * 设置监测站点
     */
    fun getSiteList() {
        mView.showSiteChooseDialgo()
    }

    /**
     * 查询
     */
    fun search() {
        requestNetWork()
    }
}