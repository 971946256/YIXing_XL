package com.otitan.ui.fragment

import android.content.Intent
import android.databinding.Observable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.otitan.base.BaseFragment
import com.otitan.model.CustomerInfo
import com.otitan.ui.adapter.InfoListAdapter
import com.otitan.ui.mview.IInfoList
import com.otitan.ui.viewmodel.InfoListViewModel
import com.otitan.util.BoxStoreUtil
import com.otitan.util.TitanItemDecoration
import com.titan.tianqidemo.BR
import com.titan.tianqidemo.R
import com.titan.tianqidemo.databinding.FmInfoListBinding
import java.util.*
import kotlin.collections.ArrayList

class InfoListFragment() : BaseFragment<FmInfoListBinding, InfoListViewModel>(), IInfoList {
    var viewmodel: InfoListViewModel? = null
    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_info_list
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): InfoListViewModel {
        if (viewmodel == null) {
            viewmodel = InfoListViewModel(activity, this)
        }
        return viewmodel as InfoListViewModel
    }

    override fun initViewObservable() {
        viewModel.isFinishRefreshing.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                binding.refreshLayout.finishRefreshing()
            }
        })

        viewModel.isFinishLoading.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                binding.refreshLayout.finishLoadmore()
            }
        })
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { activity?.finish() }

        val layoutManager = LinearLayoutManager(activity, OrientationHelper.VERTICAL, false)
        binding.rvInfoList.layoutManager = layoutManager
        binding.rvInfoList.addItemDecoration(TitanItemDecoration(activity!!, 1, 10))
        val adapter = InfoListAdapter(activity, readInfo(),this)
        binding.rvInfoList.adapter = adapter
    }

    //获取用户信息
    fun readInfo(): ArrayList<CustomerInfo> {
        val list = ArrayList<CustomerInfo>()
//        val path = "${Environment.getExternalStorageDirectory()}/gyyixing"
//        val files = File(path).listFiles()
//        files.forEach {
//            if (it.name.endsWith(".xls") && it.name.contains("弈星用户数据")) {
//                list.addAll(Utli.readCustomerInfo(it))
//            }
//        }

        list.addAll(BoxStoreUtil.getCustomerInfoAll())
        if (list.isEmpty()){
            viewmodel?.hasData?.set(!viewmodel?.hasData?.get()!!)
        }
        Collections.sort(list, object : Comparator<CustomerInfo> {
            override fun compare(p0: CustomerInfo?, p1: CustomerInfo?): Int {
                return p1!!.time.compareTo(p0!!.time)
            }
        })
        return list
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==1){
            refresh()
        }
    }

    override fun refresh() {
        val adapter = binding.rvInfoList.adapter
        if (adapter is InfoListAdapter) {
            adapter.items = readInfo()
            adapter.notifyDataSetChanged()
        }
    }
}