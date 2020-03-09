package com.otitan.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.titan.tianqidemo.R
import kotlin.properties.Delegates

abstract class BaseFragment<V : ViewDataBinding, VM : com.otitan.base.BaseViewModel> : Fragment(), com.otitan.base.IBaseActivity {
    protected var binding: V by Delegates.notNull()
    protected var viewModel: VM by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && activity?.window != null) {
            val window = activity!!.window
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(activity!!, R.color.colorPrimaryDark)
        }
        initParam()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false)
        viewModel = initViewModel()
        binding.setVariable(initVariableId(), viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()

        initViewObservable()

        viewModel.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
        binding.unbind()
    }

    //刷新布局
    fun refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(initVariableId(), viewModel)
        }
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId(): Int

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    abstract fun initViewModel(): VM

    override fun initParam() {}

    override fun initData() {}

    override fun initViewObservable() {}

    fun onBackPressed(): Boolean {
        return false
    }
}