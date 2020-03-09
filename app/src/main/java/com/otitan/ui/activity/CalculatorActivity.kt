package com.otitan.ui.activity

import android.os.Bundle
import android.view.View
import com.otitan.base.BaseActivity
import com.otitan.ui.viewmodel.CalculatorViewModel
import com.titan.tianqidemo.BR
import com.titan.tianqidemo.R
import com.titan.tianqidemo.databinding.ActivityCalculatorBinding as ActivityCalculatorBinding1

/**
 * 花粉计算器
 */
class CalculatorActivity() : BaseActivity<ActivityCalculatorBinding1, CalculatorViewModel>() {
    private var viewmodel: CalculatorViewModel? = null

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_calculator
    }

    override fun initViewModel(): CalculatorViewModel {
        if (viewmodel == null) {
            viewmodel = CalculatorViewModel(this)
        }
        return viewmodel as CalculatorViewModel
    }

    override fun initData() {
        super.initData()
        val toolbar = binding.toolbar
        toolbar.title = "属性编辑"
        this.setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { this.finish() }
    }
}