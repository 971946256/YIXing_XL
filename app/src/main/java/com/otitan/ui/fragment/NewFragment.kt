package com.otitan.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.otitan.base.BaseFragment
import com.otitan.ui.viewmodel.NewViewModel
import com.titan.tianqidemo.BR
import com.titan.tianqidemo.R
import com.titan.tianqidemo.databinding.FmNewBinding
import kotlinx.android.synthetic.main.fm_new.*

class NewFragment() : BaseFragment<FmNewBinding, NewViewModel>() {

    private var viewmodel: NewViewModel? = null
    var url: String = "https://mp.weixin.qq.com/s/WPQ449WeN6rXpJNiUaeK-w"

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_new
    }

    override fun initViewModel(): NewViewModel {
        if (viewmodel == null) {
            viewmodel = NewViewModel()
        }
        return viewmodel as NewViewModel
    }


    override fun initParam() {
        super.initParam()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        url = arguments?.getString("url") ?: ""
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { activity?.finish() }

        wb_new.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

                return false
            }
        }
        val setting = wb_new.settings
        // 让WebView能够执行javaScript
        setting.javaScriptEnabled = true
        // 让JavaScript可以自动打开windows
        setting.javaScriptCanOpenWindowsAutomatically = true
        // 设置缓存
        setting.setAppCacheEnabled(true)
        // 设置缓存模式,一共有四种模式
//        setting.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        // 设置缓存路径
//        setting.setAppCachePath("")
        // 支持缩放(适配到当前屏幕)
        setting.setSupportZoom(true)
        setting.builtInZoomControls = true
        // 将图片调整到合适的大小
        setting.useWideViewPort = true
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        setting.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        // 设置可以被显示的屏幕控制
        setting.displayZoomControls = true
        // 设置默认字体大小
        setting.defaultFontSize = 12

        val userAgent = setting.userAgentString
        wb_new.loadUrl(url)
    }


}