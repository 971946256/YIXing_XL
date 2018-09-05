package com.otitan.util

import android.content.res.ColorStateList
import android.databinding.BindingAdapter
import android.widget.TextView
import com.otitan.customview.CustomCircleProgressBar

class CustomDataBindingUtil {
        @BindingAdapter("app:textColor")
        fun setTextColor(textView: TextView, color: ColorStateList) {
            textView.setTextColor(color)
        }

        @BindingAdapter("app:size")
        fun setProgress(progressBar: CustomCircleProgressBar, progress: Int) {
            progressBar.setProgress(progress.toFloat())
        }
}