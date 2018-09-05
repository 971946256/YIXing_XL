package com.otitan.ui.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.table.TableData
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.otitan.base.BaseFragment
import com.otitan.base.ValueCallBack
import com.otitan.model.SGYLDataModel
import com.otitan.model.ZDQXZLNDataModel
import com.titan.tianqidemo.BR
import com.titan.tianqidemo.R
import com.titan.tianqidemo.databinding.FragChartBinding
import com.otitan.ui.dialog.TimePickerDialog
import com.otitan.ui.mview.IChart
import com.otitan.ui.viewmodel.ChartViewModel
import kotlinx.android.synthetic.main.frag_chart.*

/**
 * 图表页
 */
class ChartFragment() : BaseFragment<FragChartBinding, ChartViewModel>(), IChart {
    private var viewmodel: ChartViewModel? = null
    var yDataSet: LineDataSet? = null
    private var xAxis: XAxis? = null                //X轴
    private var leftYAxis: YAxis? = null            //左侧Y轴
    private var rightYaxis: YAxis? = null           //右侧Y轴
    private var legend: Legend? = null              //图例
    private var limitLine: LimitLine? = null        //限制线
    var itemName = ""
    var resultCode = 0
    var type = "1"
    var siteName = ""
    private val colorArray = arrayOf(R.color.colorPrimaryDark, R.color.sysGray, R.color.headcolor, R.color.lightyellow,
            R.color.color_mj_2, R.color.color_mj_4, R.color.color_mj_5, R.color.color_zj_1,
            R.color.color_zj_2, R.color.orange, R.color.color_zj_3, R.color.colorPrimary)


    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.frag_chart
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): ChartViewModel {
        if (viewmodel == null) {
            viewmodel = ChartViewModel(context, this)
        }
        return viewmodel as ChartViewModel
    }

    override fun initParam() {
        super.initParam()
        arguments?.let {
            resultCode = it.getInt("resultCode")
            itemName = it.getString("itemName")
            type = it.getString("type")
            siteName = it.getString("siteName")
        }
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        toolbar?.title = itemName
        (activity!! as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar?.setNavigationOnClickListener { activity?.finish() }

        viewModel.resultCode = resultCode
        viewModel.type = type
        viewModel.itemName = itemName
        viewModel.site.set(siteName)
        // 没有数据的时候显示
        tempLineChart.setNoDataText("暂无数据")
        //是否显示边界
        tempLineChart.setDrawBorders(false)
        //是否显示网格线
        tempLineChart.setDrawGridBackground(false)
        //是否可以拖动
        tempLineChart.isDragEnabled = true
        //是否有触摸事件
        tempLineChart.setTouchEnabled(true)
        //xy轴动画事件
        tempLineChart.animateXY(1500, 1500)
        val desc = Description()
        desc.text = ""
        tempLineChart.description = desc
        tempLineChart.legend.isWordWrapEnabled = true

        /***XY轴的设置***/
        xAxis = tempLineChart.xAxis
        val xAxisFormat = IAxisValueFormatter { value, axis ->
            when {
                viewModel.dataTimeList.isEmpty() -> value.toString()
                value == -1f || value > viewModel.dataTimeList.size - 1 -> ""
                else -> viewModel.dataTimeList[value.toInt()]
            }
        }
        val yAxisFormat = IAxisValueFormatter { value, axis ->
            if (viewModel.company != "") {
                "$value${viewModel.company}"
            } else {
                value.toString()
            }
        }
//        leftYAxis = tempLineChart.axisLeft
//        rightYaxis = tempLineChart.axisRight
//        tempLineChart.axisRight.isEnabled = false
        xAxis?.valueFormatter = xAxisFormat
        tempLineChart.axisLeft.valueFormatter = yAxisFormat
        tempLineChart.axisRight.valueFormatter = yAxisFormat
        xAxis?.labelRotationAngle = -60f
        tempLineChart.extraBottomOffset = -65f
//        tempLineChart.extraLeftOffset = 10f
        tempLineChart.offsetLeftAndRight(10)
        //X轴设置显示位置在底部
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.axisMinimum = 0f
        xAxis?.granularity = 1f
        //保证Y轴从0开始，不然会上移一点
        leftYAxis?.axisMinimum = 0f
        rightYaxis?.axisMinimum = 0f


        /***折线图例 标签 设置***/
        legend = tempLineChart.legend
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend?.form = Legend.LegendForm.LINE
        legend?.textSize = 12f
        //显示位置 左下方
        legend?.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend?.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        legend?.orientation = Legend.LegendOrientation.HORIZONTAL
        //是否绘制在图表里面
        legend?.setDrawInside(false)

        //表格设置 隐藏左边数字、顶上字母
        dataTable.config.isShowXSequence = false
        dataTable.config.isShowYSequence = false
    }

    override fun setLineData(values: List<List<Entry>>?) {
        if (tempLineChart.data != null && tempLineChart.data.dataSetCount > 0 && values?.isNotEmpty()!!) {
            for (i in 0 until tempLineChart.data.dataSetCount) {
                yDataSet = tempLineChart.data.getDataSetByIndex(i) as LineDataSet
                yDataSet?.values = values[i]
                tempLineChart.data.notifyDataChanged()
            }
            tempLineChart.notifyDataSetChanged()
            tempLineChart.invalidate()
        } else if (values?.isNotEmpty()!! && activity != null) {
            val dataSets = ArrayList<ILineDataSet>()
            for (i in 0 until values.size) {
                when (resultCode) {
                    0 -> {
                        val array1 = resources.getStringArray(R.array.zdqxzln_wd)
                        val array2 = resources.getStringArray(R.array.zdqxzln_sd)
                        val array3 = resources.getStringArray(R.array.zdqxzln_fs)
                        when (type) {
                            "1" -> yDataSet = LineDataSet(values[i], array1[i])
                            "2" -> yDataSet = LineDataSet(values[i], array2[i])
                            "3" -> yDataSet = LineDataSet(values[i], array3[i])
                        }
                    }
                    5 -> {
                        val array = resources.getStringArray(R.array.sgyl_yq)
                        yDataSet = LineDataSet(values[i], array[i])
                    }
                    else -> {
                        yDataSet = LineDataSet(values[i], itemName)
                    }
                }
//                if (viewmodel?.resultCode==0||viewmodel?.resultCode==6){
//                    yDataSet = LineDataSet(values[i], "y轴")
//                }
//                yDataSet = LineDataSet(values[i], "y轴")
                //用y轴的集合来设置参数
                yDataSet?.lineWidth = 1.75f // 线宽
                yDataSet?.circleRadius = 2f// 显示的圆形大小
                val color = ContextCompat.getColor(activity!!, colorArray[i])
                yDataSet?.color = color //resources.getColor(colorArray[i])//Color.rgb(89 + i * 10, 194 - i * 15, 240 - i * 25)// 折线显示颜色
                yDataSet?.setCircleColor(color)// 圆形折点的颜色
                yDataSet?.highLightColor = Color.GREEN // 高亮的线的颜色
                yDataSet?.isHighlightEnabled = true
                yDataSet?.valueTextColor = color //数值显示的颜色
                yDataSet?.valueTextSize = 8f     //数值显示的大小
                yDataSet?.setDrawValues(false)
                yDataSet?.let {
                    dataSets.add(it)
                }
            }

            tempLineChart.data = LineData(dataSets)
            tempLineChart.invalidate()
        }

    }

    override fun setZDQXZTableData(tableData: List<ZDQXZLNDataModel>) {
        val columnList = ArrayList<Column<ZDQXZLNDataModel>>()
        val company = itemName.substring(0, 2)
        val eigenvalues = Column<ZDQXZLNDataModel>("特征值", "eigenvalues")
        //固定该列
        eigenvalues.isFixed = true
        val five = Column<ZDQXZLNDataModel>("5米处空气$company", "Five_m")
        val ten = Column<ZDQXZLNDataModel>("10米处空气$company", "Ten_m")
        val fifteen = Column<ZDQXZLNDataModel>("15米处空气$company", "Fifteen_m")
        val twenty = Column<ZDQXZLNDataModel>("20米处空气$company", "Twenty_m")
        val twentyFive = Column<ZDQXZLNDataModel>("25米处空气$company", "Twenty_Five_m")
        val thirty = Column<ZDQXZLNDataModel>("30米处空气$company", "Thirty_m")

        columnList.add(eigenvalues)
        columnList.add(five)
        columnList.add(ten)
        columnList.add(fifteen)
        columnList.add(twenty)
        columnList.add(twentyFive)
        columnList.add(thirty)
        dataTable.tableData = TableData<ZDQXZLNDataModel>("", tableData, columnList as List<Column<Any>>?)
    }

    override fun setSGYLTableData(tableData: List<SGYLDataModel>) {
        val columnList = ArrayList<Column<SGYLDataModel>>()
        val eigenvalues = Column<SGYLDataModel>("特征值", "eigenvalues")
        //固定该列
        eigenvalues.isFixed = true
        val array = resources.getStringArray(R.array.sgyl_yq)
        val flow1 = Column<SGYLDataModel>(array[0].substring(0, 4), "TDP_FLOW1")
        val flow2 = Column<SGYLDataModel>(array[1].substring(0, 4), "TDP_FLOW2")
        val flow3 = Column<SGYLDataModel>(array[2].substring(0, 4), "TDP_FLOW3")
        val flow4 = Column<SGYLDataModel>(array[3].substring(0, 4), "TDP_FLOW4")
        val flow5 = Column<SGYLDataModel>(array[4].substring(0, 4), "TDP_FLOW5")
        val flow6 = Column<SGYLDataModel>(array[5].substring(0, 4), "TDP_FLOW6")
        val flow7 = Column<SGYLDataModel>(array[6].substring(0, 4), "TDP_FLOW7")
        val flow8 = Column<SGYLDataModel>(array[7].substring(0, 4), "TDP_FLOW8")
        val flow9 = Column<SGYLDataModel>(array[8].substring(0, 4), "TDP_FLOW9")
        val flow10 = Column<SGYLDataModel>(array[9].substring(0, 4), "TDP_FLOW10")
        val flow11 = Column<SGYLDataModel>(array[10].substring(0, 4), "TDP_FLOW11")
        val flow12 = Column<SGYLDataModel>(array[11].substring(0, 4), "TDP_FLOW12")
        columnList.add(eigenvalues)
        columnList.add(flow1)
        columnList.add(flow2)
        columnList.add(flow3)
        columnList.add(flow4)
        columnList.add(flow5)
        columnList.add(flow6)
        columnList.add(flow7)
        columnList.add(flow8)
        columnList.add(flow9)
        columnList.add(flow10)
        columnList.add(flow11)
        columnList.add(flow12)
        dataTable.tableData = TableData<SGYLDataModel>("", tableData, columnList as List<Column<Any>>?)
    }

    override fun showTimePicker(type: Int) {
        val timeDialog = TimePickerDialog(object : com.otitan.base.ValueCallBack<String> {
            override fun onSuccess(t: String) {
                when (type) {
                    0 -> viewModel.stime.set(t)
                    1 -> viewModel.etime.set(t)
                }
                viewModel.notifyChange()
            }

            override fun onFail(code: String) {

            }

        })
        val bundle = Bundle()
        bundle.putInt("type", type)
        bundle.putString("starttime", viewModel.stime.get())
        bundle.putString("endtime", viewModel.etime.get())
        timeDialog.arguments = bundle
        timeDialog.show(fragmentManager, "timeDialog")
    }

    override fun showSiteChooseDialgo() {
        activity?.let {
            MaterialDialog.Builder(it).title("观测站点")
                    .items(R.array.site_array)
                    .itemsCallback { dialog, itemView, position, text ->
                        viewModel.site.set(text.toString())
                        viewModel.requestNetWork()
                        dialog.dismiss()
                    }.show()
        }
    }

}