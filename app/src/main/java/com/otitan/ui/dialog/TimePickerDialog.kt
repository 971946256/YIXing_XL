package com.otitan.ui.dialog

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.otitan.base.ValueCallBack
import com.titan.tianqidemo.R
import com.titan.tianqidemo.databinding.DialogTimepickerBinding
import com.otitan.ui.mview.ITimePicker
import com.otitan.ui.viewmodel.TimePickerViewModel
import org.jetbrains.anko.toast
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class TimePickerDialog() : DialogFragment(), ITimePicker {

    var viewmodel: TimePickerViewModel by Delegates.notNull()
    private var binding: DialogTimepickerBinding by Delegates.notNull()

    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    //获取时间类型 0开始时间 1结束时间
    private var type = 0
    private var stime: String? = null
    private var etime: String? = null
    private var callBack: com.otitan.base.ValueCallBack<String>? = null

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    private object Holder {
        val single = TimePickerDialog()
    }

    companion object {
        fun getInstance() = Holder.single
    }

    @SuppressLint("ValidFragment")
    constructor(callBack: com.otitan.base.ValueCallBack<String>) : this() {
        this.callBack = callBack
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog)
        arguments?.let {
            type = it.getInt("type")
            stime = it.getString("starttime")
            etime = it.getString("endtime")
        }
    }

    override fun onStart() {
        super.onStart()
        //设置dialog样式在底部
        val dialogWindow = dialog.window
        dialog.setCanceledOnTouchOutside(true)
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.BOTTOM)
            val lp = dialogWindow.attributes
            lp.width = resources.displayMetrics.widthPixels
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialogWindow.attributes = lp
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_timepicker, container, false)
        viewmodel = TimePickerViewModel(this)
        binding.viewmodel = viewmodel
        binding.setTimepicker.setIs24HourView(true)
//        initView()
        return binding.root
    }

    fun initView() {
        binding.pickerHour.maxValue = 23
        binding.pickerHour.minValue = 0

        binding.pickerMinute.maxValue = 59
        binding.pickerMinute.minValue = 0

        binding.setDatepicker.init(year, month, day) { view, y, monthOfYear, dayOfMonth ->
            if (y != year || month != monthOfYear || dayOfMonth != day) {
                binding.pickerHour.maxValue = 23
                //                binding.pickerHour.minValue = 0

                binding.pickerMinute.maxValue = 59
                //                binding.pickerMinute.minValue = 0
            } else if (y == year && month == monthOfYear && dayOfMonth == day) {
                binding.pickerHour.maxValue = hour
                binding.pickerMinute.maxValue = minute
                //                when (type) {
                //                    0 -> setMaxTime(stime, etime)
                //                    1 -> {
                //                        setMaxTime(stime, etime)
                //                        setMinTime(stime, etime)
                //                    }
                //                }
            }
        }

        when (type) {
            0 -> setMaxTime(stime, etime)
            1 -> {
                setMaxTime(stime, etime)
                setMinTime(stime, etime)
            }
        }
    }

    override fun sure() {
        val year = binding.setDatepicker.year
        val month = conversion(binding.setDatepicker.month + 1)
        val day = conversion(binding.setDatepicker.dayOfMonth)
        val hour = conversion(binding.setTimepicker.currentHour)
        val minute = conversion(binding.setTimepicker.currentMinute)
        val time = "$year-$month-$day $hour:$minute:00"
        when (type) {
            0 -> {
                if (!compareTime(time, format.parse(etime).time)) {
                    activity?.toast("开始时间不能大于结束时间")
                    return
                }
            }
            1 -> {
                if (compareTime(time, format.parse(stime).time)) {
                    activity?.toast("结束时间不能小于开始时间")
                    return
                }
            }
        }
//        if (type == 1 && !compareTime(time, System.currentTimeMillis())) {
//            activity?.toast("结束时间不能大于开始时间")
//            return
//        }
        callBack?.onSuccess(time)
        this.dismiss()
    }

    private fun conversion(value: Int): String {
        if (value < 10) {
            return DecimalFormat("00").format(value)
        }
        return value.toString()
    }

    /**
     * 时间大小比较
     * @param select 选择的时间
     * @param current 当前系统时间
     * @return 当选择的时间小于等于当前系统时间 返回 true 否则返回 false
     */
    private fun compareTime(select: String?, current: Long?): Boolean {
        var result = false
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        try {
            if (select == null || current == null) {
                return true
            }
            val selectDate = sdf.parseObject(select) as Date
            val currentDate = sdf.parseObject(sdf.format(current)) as Date
            if (selectDate.before(currentDate) || selectDate == currentDate) {
                result = true
            }
        } catch (e: ParseException) {
            Log.e("tag", "timeError:$e")
            activity?.toast("时间转换错误")
        }
        return result
    }

    private fun setMaxTime(startTime: String?, endTime: String?) {
        try {
            Log.e("tag", "maxtime:$endTime")
            val calendar = Calendar.getInstance()
            if (endTime == null) {
                calendar.time = Date(System.currentTimeMillis())
            } else {
                calendar.time = format.parse(endTime)
            }
            val h = calendar.get(Calendar.HOUR_OF_DAY)
            val m = calendar.get(Calendar.MINUTE)
            binding.setDatepicker.maxDate = calendar.time.time
            binding.pickerHour.maxValue = hour
            binding.pickerMinute.maxValue = minute

            val datePicker = binding.setDatepicker
            binding.pickerHour.setOnValueChangedListener { picker, oldVal, newVal ->
                if (datePicker.year != year || month != datePicker.month ||
                        datePicker.dayOfMonth != day) {
                    binding.pickerHour.maxValue = 23
                    binding.pickerMinute.maxValue = 59
                } else if (datePicker.year == year && month == datePicker.month &&
                        datePicker.dayOfMonth == day && newVal >= hour) {
                    binding.pickerHour.maxValue = hour
                    binding.pickerMinute.maxValue = minute
                }
            }
            val c2 = Calendar.getInstance()
            c2.time = format.parse(startTime)
            binding.pickerHour.value = c2.get(Calendar.HOUR_OF_DAY)
            binding.pickerMinute.value = c2.get(Calendar.MINUTE)

        } catch (e: Exception) {
            Log.e("tag", "时间设置错误:$e")
            activity?.toast("时间设置错误:$e")
        }
    }

    private fun setMinTime(startTime: String?, endTime: String?) {
        try {
            Log.e("tag", "mintime:$startTime")
            val calendar = Calendar.getInstance()
            val calendar2 = Calendar.getInstance()
            val minDate = format.parse(startTime)
            calendar2.time = minDate
            binding.setDatepicker.minDate = minDate.time
            val y1 = calendar.get(Calendar.YEAR)
            val y2 = calendar2.get(Calendar.YEAR)
            val month1 = calendar.get(Calendar.MONTH)
            val month2 = calendar2.get(Calendar.MONTH)
            val d1 = calendar.get(Calendar.DAY_OF_MONTH)
            val d2 = calendar2.get(Calendar.DAY_OF_MONTH)
            val h1 = calendar.get(Calendar.HOUR_OF_DAY)
            val h2 = calendar2.get(Calendar.HOUR_OF_DAY)
            val m1 = calendar.get(Calendar.MINUTE)
            val m2 = calendar2.get(Calendar.MINUTE)
            if (y1 != y2 || month1 != month2 || d1 != d2) {
//                binding.pickerHour.maxValue = 23
//                binding.pickerMinute.maxValue = 59
            } else if (y1 == y2 && month1 == month2 && d1 == d2) {
                binding.pickerHour.minValue = if (h1 <= h2) h1 else h2
                binding.pickerMinute.minValue = if (m1 <= m2) m1 else m2
            }
            val c3 = Calendar.getInstance()
            c3.time = format.parse(endTime)
            binding.pickerHour.value = c3.get(Calendar.HOUR_OF_DAY)
            binding.pickerMinute.value = c3.get(Calendar.MINUTE)
        } catch (e: Exception) {
            Log.e("tag", "时间设置错误:$e")
            activity?.toast("时间设置错误:$e")
        }
    }

}