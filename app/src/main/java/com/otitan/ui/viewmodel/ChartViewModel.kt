package com.otitan.ui.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.github.mikephil.charting.data.Entry
import com.otitan.base.BaseViewModel
import com.otitan.data.DataRepository
import com.otitan.data.Injection
import com.otitan.data.remote.RemoteDataSource
import com.otitan.model.ContentDataModel
import com.otitan.model.SGYLDataModel
import com.otitan.model.ZDQXZLNDataModel
import com.otitan.ui.mview.IChart
import com.otitan.util.Utils
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.toast
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class ChartViewModel() : com.otitan.base.BaseViewModel() {

    private var mView: IChart by Delegates.notNull()
    val site = ObservableField<String>()
    val stime: ObservableField<String> = ObservableField()
    val etime: ObservableField<String> = ObservableField()
    val lineData = ArrayList<ArrayList<Entry>>()
    val minValue = ObservableField<String>("0.0")
    val maxValue = ObservableField<String>("0.0")
    val aveValue = ObservableField<String>("0.0")
    //是否显示表格 true是 false否
    val isTable = ObservableBoolean(false)
    //是否获取到网络数据 true是 false否
    val hasData = ObservableBoolean(false)
    //时间集合
    val dataTimeList = ArrayList<String>()
    //y轴数据单位
    var company = ""
    //接口请求码 0林内 1林外 2空气质量、负氧离子 3雨滴谱仪 4土壤水势 5树干液流
    var resultCode = 0
    //梯度自动气象站查询类型 1温度 2湿度 3风速
    var type = "1"
    var itemName = ""
    var dataRepository: DataRepository by Delegates.notNull()
    var minArray = Array(12) { "0.00" }
    var maxArray = Array(12) { "0.00" }
    var aveArray = Array(12) { "0.00" }

    constructor(context: Context?, mView: IChart) : this() {
        this.mContext = context
        this.mView = mView
//        val zero = Timestamp(Utils.getZeroTime())
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val zero = format.format(Date(Utils.getZeroTime()))
        stime.set(zero)
        etime.set(format.format(Date()))
//        etime.set(zero)
    }

    override fun onCreate() {
        super.onCreate()
        dataRepository = Injection.provideDataRepository()
        requestNetWork()
        isShowTable()
    }

    fun requestNetWork() {
        showDialog("加载中...")
        hasData.set(false)
        when (resultCode) {
            0 -> getZDQXZLNData(type)
            1 -> getZDQXZLWData(type)
            2 -> {
                when (itemName) {
                    "PM2.5实时监测" -> getKQZLData()
                    "负氧离子实时监测" -> getFYLZData()
                }
            }
            3 -> getYDPYData(type)
            4 -> getTRSSData(type)
            5 -> getStemFlowData()
        }
    }

    fun isShowTable() {
        if (resultCode == 0 || resultCode == 5) {
            isTable.set(true)
        } else {
            isTable.set(false)
        }
    }

    /**
     * 自动气象站 林内
     */
    fun getZDQXZLNData(type: String) {
        dataRepository.getZDQXZLNData(site.get()!!, type, stime.get()!!, etime.get()!!, object : RemoteDataSource.mCallback {
            override fun onFailure(info: String) {
                dismissDialog()
                mContext?.toast(info)
            }

            override fun onSuccess(result: Any) {
                setLNLineData(result as List<ZDQXZLNDataModel>)
                dismissDialog()
                mView.setLineData(lineData)
                mView.setZDQXZTableData(getZDQXZTableData())
            }
        })
    }

    /**
     * 自动气象站 林外
     */
    fun getZDQXZLWData(type: String) {
        dataRepository.getZDQXZLWData(site.get()!!, type, stime.get()!!, etime.get()!!, object : RemoteDataSource.mCallback {
            override fun onFailure(info: String) {
                dismissDialog()
                mContext?.toast(info)
            }

            override fun onSuccess(result: Any) {
                dismissDialog()
                setContentLineData(result as List<ContentDataModel>)
                mView.setLineData(lineData)
            }
        })
    }

    /**
     * 空气质量
     */
    fun getKQZLData() {
        dataRepository.getKQZLData(site.get()!!, stime.get()!!, etime.get()!!, object : RemoteDataSource.mCallback {
            override fun onFailure(info: String) {
                dismissDialog()
                mContext?.toast(info)
            }

            override fun onSuccess(result: Any) {
                dismissDialog()
                setContentLineData(result as List<ContentDataModel>)
                mView.setLineData(lineData)
            }
        })
    }

    /**
     * 负氧离子
     */
    fun getFYLZData() {
        dataRepository.getFYLZData(site.get()!!, stime.get()!!, etime.get()!!, object : RemoteDataSource.mCallback {
            override fun onFailure(info: String) {
                dismissDialog()
                mContext?.toast(info)
            }

            override fun onSuccess(result: Any) {
                dismissDialog()
                setContentLineData(result as List<ContentDataModel>)
                mView.setLineData(lineData)
            }
        })
    }

    /**
     * 雨滴谱仪
     */
    fun getYDPYData(type: String) {
        dataRepository.getYDPYData(site.get()!!, type, stime.get()!!, etime.get()!!, object : RemoteDataSource.mCallback {
            override fun onFailure(info: String) {
                dismissDialog()
                mContext?.toast(info)
            }

            override fun onSuccess(result: Any) {
                dismissDialog()
                setContentLineData(result as List<ContentDataModel>)
                mView.setLineData(lineData)
            }
        })
    }

    /**
     * 土壤水势
     */
    fun getTRSSData(type: String) {
        dataRepository.getTRSSData(site.get()!!, type, stime.get()!!, etime.get()!!, object : RemoteDataSource.mCallback {
            override fun onFailure(info: String) {
                dismissDialog()
                mContext?.toast(info)
            }

            override fun onSuccess(result: Any) {
                dismissDialog()
                setContentLineData(result as List<ContentDataModel>)
                mView.setLineData(lineData)
            }
        })
    }

    /**
     * 树干液流
     */
    fun getStemFlowData() {
        dataRepository.getStemFlowData(site.get()!!, stime.get()!!, etime.get()!!, object : RemoteDataSource.mCallback {
            override fun onFailure(info: String) {
                dismissDialog()
                mContext?.toast(info)
            }

            override fun onSuccess(result: Any) {
                dismissDialog()
                setSGYLLineData(result as List<SGYLDataModel>)
                mView.setLineData(lineData)
                mView.setSGYLTableData(getSGYLTableData())
            }
        })
    }

    /**
     * 树干液流数据
     */
    fun setSGYLLineData(list: List<SGYLDataModel>) {
        lineData.clear()
        val list1 = ArrayList<Entry>()
        val list2 = ArrayList<Entry>()
        val list3 = ArrayList<Entry>()
        val list4 = ArrayList<Entry>()
        val list5 = ArrayList<Entry>()
        val list6 = ArrayList<Entry>()
        val list7 = ArrayList<Entry>()
        val list8 = ArrayList<Entry>()
        val list9 = ArrayList<Entry>()
        val list10 = ArrayList<Entry>()
        val list11 = ArrayList<Entry>()
        val list12 = ArrayList<Entry>()
        if (list.isNotEmpty()) {
            company = list[0].Company
        }
        list.forEachWithIndex { i, v ->
            dataTimeList.add(v.datatime)
            list1.add(Entry(i.toFloat(), v.TDP_FLOW1.toFloat()))
            list2.add(Entry(i.toFloat(), v.TDP_FLOW2.toFloat()))
            list3.add(Entry(i.toFloat(), v.TDP_FLOW3.toFloat()))
            list4.add(Entry(i.toFloat(), v.TDP_FLOW4.toFloat()))
            list5.add(Entry(i.toFloat(), v.TDP_FLOW5.toFloat()))
            list6.add(Entry(i.toFloat(), v.TDP_FLOW6.toFloat()))
            list7.add(Entry(i.toFloat(), v.TDP_FLOW7.toFloat()))
            list8.add(Entry(i.toFloat(), v.TDP_FLOW8.toFloat()))
            list9.add(Entry(i.toFloat(), v.TDP_FLOW9.toFloat()))
            list10.add(Entry(i.toFloat(), v.TDP_FLOW10.toFloat()))
            list11.add(Entry(i.toFloat(), v.TDP_FLOW11.toFloat()))
            list12.add(Entry(i.toFloat(), v.TDP_FLOW12.toFloat()))
        }
        lineData.add(list1)
        lineData.add(list2)
        lineData.add(list3)
        lineData.add(list4)
        lineData.add(list5)
        lineData.add(list6)
        lineData.add(list7)
        lineData.add(list8)
        lineData.add(list9)
        lineData.add(list10)
        lineData.add(list11)
        lineData.add(list12)

        val listArray = arrayOf(list1, list2, list3, list4, list5, list6, list7, list8
                , list9, list10, list11, list12)
        val format = DecimalFormat("0.00")
        listArray.forEachWithIndex { i, arrayList ->
            arrayList.minBy { it.y }?.y?.let {
                minArray[i] = format.format(it)
            }
            arrayList.maxBy { it.y }?.y?.let {
                maxArray[i] = format.format(it)
            }
            var ave = 0.0f
            arrayList.forEach {
                ave += it.y
            }
            aveArray[i] = format.format(ave / arrayList.size.toDouble())
        }
        hasData.set(true)
    }

    /**
     * 通用类型数据
     */
    fun setContentLineData(list: List<ContentDataModel>) {
        lineData.clear()
        val tempList = ArrayList<Entry>()
        if (list.isNotEmpty()) {
            company = list[0].Company
        }
        var ave = 0.0
        list.forEachWithIndex { i, v ->
            dataTimeList.add(v.datatime)
            tempList.add(Entry(i.toFloat(), v.`val`.toFloat()))
            ave += v.`val`.toFloat()
        }
        lineData.add(tempList)
        tempList.minBy { it.y }?.y?.let {
            minValue.set("$it $company")
        }
        tempList.maxBy { it.y }?.y?.let {
            maxValue.set("$it $company")
        }
        val format = DecimalFormat("0.00")
        aveValue.set("${format.format(ave / tempList.size)} $company")

        hasData.set(true)
    }

    /**
     * 林内数据
     */
    fun setLNLineData(list: List<ZDQXZLNDataModel>) {
        lineData.clear()
        val list1 = ArrayList<Entry>()
        val list2 = ArrayList<Entry>()
        val list3 = ArrayList<Entry>()
        val list4 = ArrayList<Entry>()
        val list5 = ArrayList<Entry>()
        val list6 = ArrayList<Entry>()
        if (list.isNotEmpty()) {
            company = list[0].Company
        }
        list.forEachWithIndex { i, v ->
            dataTimeList.add(v.datatime)
            list1.add(Entry(i.toFloat(), v.Five_m.toFloat()))
            list2.add(Entry(i.toFloat(), v.Ten_m.toFloat()))
            list3.add(Entry(i.toFloat(), v.Fifteen_m.toFloat()))
            list4.add(Entry(i.toFloat(), v.Twenty_m.toFloat()))
            list5.add(Entry(i.toFloat(), v.Twenty_Five_m.toFloat()))
            list6.add(Entry(i.toFloat(), v.Thirty_m.toFloat()))
        }
        lineData.add(list1)
        lineData.add(list2)
        lineData.add(list3)
        lineData.add(list4)
        lineData.add(list5)
        lineData.add(list6)

        val listArray = arrayOf(list1, list2, list3, list4, list5, list6)
        val format = DecimalFormat("0.00")
        listArray.forEachWithIndex { i, arrayList ->
            arrayList.minBy { it.y }?.y?.let {
                minArray[i] = it.toString()//format.format(it)
            }
            arrayList.maxBy { it.y }?.y?.let {
                maxArray[i] = it.toString()//format.format(it)
            }
            var ave = 0.0f
            arrayList.forEach {
                ave += it.y
            }
            aveArray[i] = format.format(ave / arrayList.size.toDouble())
        }
        hasData.set(true)
    }

    fun getZDQXZTableData(): List<ZDQXZLNDataModel> {
        val list = ArrayList<ZDQXZLNDataModel>()
        list.add(ZDQXZLNDataModel("最大值", "", maxArray[0], maxArray[1],
                maxArray[2], maxArray[3], maxArray[4], maxArray[5], ""))
        list.add(ZDQXZLNDataModel("最小值", "", minArray[0], minArray[1],
                minArray[2], minArray[3], minArray[4], minArray[5], ""))
        list.add(ZDQXZLNDataModel("平均值", "", aveArray[0], aveArray[1],
                aveArray[2], aveArray[3], aveArray[4], aveArray[5], ""))
        return list
    }

    fun getSGYLTableData(): List<SGYLDataModel> {
        val list = ArrayList<SGYLDataModel>()
        list.add(SGYLDataModel("最大值", "", maxArray[0], maxArray[1],
                maxArray[2], maxArray[3], maxArray[4], maxArray[5], maxArray[6], maxArray[7]
                , maxArray[8], maxArray[9], maxArray[10], maxArray[11], ""))
        list.add(SGYLDataModel("最小值", "", minArray[0], minArray[1],
                minArray[2], minArray[3], minArray[4], minArray[5], minArray[6], minArray[7]
                , minArray[8], minArray[9], minArray[10], minArray[11], ""))
        list.add(SGYLDataModel("平均值", "", aveArray[0], aveArray[1],
                aveArray[2], aveArray[3], aveArray[4], aveArray[5], aveArray[6], aveArray[7]
                , aveArray[8], aveArray[9], aveArray[10], aveArray[11], ""))
        return list
    }

    /**
     * 设置监测站点
     */
    fun getSiteList() {
        mView.showSiteChooseDialgo()
    }

    /**
     * 设置开始时间
     */
    fun startTime() {
        mView.showTimePicker(0)
    }

    /**
     * 设置结束时间
     */
    fun endTime() {
        mView.showTimePicker(1)
    }

    /**
     * 查询
     */
    fun search() {
        requestNetWork()
    }
}